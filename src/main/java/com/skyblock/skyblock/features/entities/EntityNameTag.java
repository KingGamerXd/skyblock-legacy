package com.skyblock.skyblock.features.entities;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftSlime;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EntityNameTag {

    private final Entity entity;
    private final ArmorStand stand;
    private final Slime slime;

    public EntityNameTag(SkyblockEntity e) {
        entity = e.getVanilla();

        slime = entity.getWorld().spawn(entity.getLocation() , Slime.class);

        slime.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, true, true));
        slime.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 10, true, true));
        slime.setSize(1);

        ((CraftSlime) slime).getHandle().ai = false;
        slime.setCustomName("name_slime_" + entity.getEntityId());
        slime.setCustomNameVisible(false);


        stand = (ArmorStand) entity.getWorld().spawnEntity(entity.getLocation() , EntityType.ARMOR_STAND);
        stand.setMarker(true);
        stand.setGravity(false);
        stand.setVisible(false);
        stand.setCustomNameVisible(true);


        slime.setPassenger(stand);
        entity.setPassenger(slime);
    }

    public void tick(String name) {
        stand.setCustomName(name);

        Slime sbukkit = slime;

        if (sbukkit.getSize() == 1) {
            int slimeSize = 1;

            if (entity.getType().equals(EntityType.ZOMBIE) && ((Zombie) entity).isVillager()) {
                slimeSize = 2;
            }

            if (entity.getType().equals(EntityType.ENDERMAN)) slimeSize = 2;

            sbukkit.setSize(slimeSize);
        }
    }

    public void death() {
        stand.remove();
        slime.remove();
    }
}
