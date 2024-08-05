package com.skyblock.skyblock.commands.player;

import com.skyblock.skyblock.Skyblock;
import com.skyblock.skyblock.features.island.IslandManager;
import com.skyblock.skyblock.utilities.command.Command;
import com.skyblock.skyblock.utilities.command.TrueAlias;
import com.skyblock.skyblock.utilities.command.annotations.Description;
import com.skyblock.skyblock.utilities.command.annotations.RequiresPlayer;
import com.skyblock.skyblock.utilities.command.annotations.Usage;
import org.bukkit.entity.Player;

@RequiresPlayer
@Usage(usage = "/sb island")
@Description(description = "Teleports you to your island")
public class IslandCommand implements Command, TrueAlias<IslandCommand> {

    @Override
    public void execute(Player player, String[] args, Skyblock plugin) {
        if (IslandManager.getIsland(player) == null){
            player.sendMessage(plugin.getPrefix() +  " Loading island...");
            IslandManager.createIsland(player);
            player.sendMessage(plugin.getPrefix()+  " Successfully loaded your island");
        }
        player.performCommand("sb warp home");
    }

}
