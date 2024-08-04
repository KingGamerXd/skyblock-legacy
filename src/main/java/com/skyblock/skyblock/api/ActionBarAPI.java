package com.skyblock.skyblock.api;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionBarAPI {

    /**
     * Sends an action bar message to the specified player.
     *
     * @param player  the player to send the action bar message to
     * @param message the message to be displayed in the action bar
     */
    public static void sendActionBar(Player player, String message) {
        if (player == null) return;
        if (!player.isOnline()) return;
        ChatComponentText chatBaseComponents = new ChatComponentText(message);
        PacketPlayOutChat packet = new PacketPlayOutChat(chatBaseComponents, (byte) 2);
        CraftPlayer craftPlayer = (CraftPlayer) player;
        EntityPlayer entityPlayer = craftPlayer.getHandle();
        PlayerConnection connection = entityPlayer.playerConnection;
        connection.sendPacket(packet);
    }
}
