package com.skyblock.skyblock.listeners;

import com.skyblock.skyblock.Skyblock;
import com.skyblock.skyblock.features.island.IslandManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.hamcrest.core.Is;

public class VisitMenuListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) throws Exception {
        if (event.getClickedInventory() == null || event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR)) return;

        if (!event.getClickedInventory().getTitle().startsWith("Visit ")) return;

        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();

        if (event.getCurrentItem().getType().equals(Material.BARRIER)) event.getWhoClicked().closeInventory();
        else if (event.getCurrentItem().getType().equals(Material.SKULL_ITEM)) {
            event.getWhoClicked().closeInventory();

            String name = ChatColor.stripColor(event.getView().getTitle().replace("Visit ", ""));

            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(name);
            if (IslandManager.exists(offlinePlayer.getUniqueId()) && IslandManager.getIsland(offlinePlayer.getUniqueId()) == null){
                event.getWhoClicked().closeInventory();
                event.getWhoClicked().sendMessage(Skyblock.getPlugin().getPrefix()+  "Loading island...");
                IslandManager.loadWorld(offlinePlayer.getUniqueId());
                player.sendMessage(Skyblock.getPlugin().getPrefix() +  "Successfully loaded " + name + " island");
            }
            event.getWhoClicked().teleport(new Location(IslandManager.getIsland(offlinePlayer.getUniqueId()), 0, 100, 0));
        }
    }
}
