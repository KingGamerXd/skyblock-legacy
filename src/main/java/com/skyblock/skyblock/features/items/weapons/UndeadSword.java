package com.skyblock.skyblock.features.items.weapons;

import com.skyblock.skyblock.Skyblock;
import com.skyblock.skyblock.SkyblockPlayer;
import com.skyblock.skyblock.features.entities.SkyblockEntity;
import com.skyblock.skyblock.features.items.SkyblockItem;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class UndeadSword extends SkyblockItem {

    public UndeadSword() {
        super(plugin.getItemHandler().getItem("UNDEAD_SWORD.json"), "undead_sword");
    }

    @Override
    public double getModifiedDamage(SkyblockPlayer player, EntityDamageByEntityEvent e, double damage) {
        SkyblockEntity entity = Skyblock.getPlugin().getEntityHandler().getEntity(e.getEntity());

        return entity == null || !entity.isUndead() ? damage : damage * 2;
    }
}
