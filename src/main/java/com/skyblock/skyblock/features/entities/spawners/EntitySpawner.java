package com.skyblock.skyblock.features.entities.spawners;

import com.skyblock.skyblock.Skyblock;
import com.skyblock.skyblock.features.entities.SkyblockEntity;
import com.skyblock.skyblock.features.entities.SkyblockEntityType;
import com.skyblock.skyblock.utilities.Util;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Getter
public class EntitySpawner {

    private final List<SkyblockEntity> spawned;
    private final Location pos1;
    private final Location pos2;
    private final long delay;
    private final int limit;
    private final int amount;
    private final SkyblockEntityType type;
    private final String subType;
    private final List<Block> blocks;
    private final Material mustSpawnOn;
    private int locationRequests = 0;
    @Setter
    private boolean hasPlayers = false;

    public EntitySpawner(ConfigurationSection section) {
        this.pos1 = (Location) section.get("pos1");
        this.pos2 = (Location) section.get("pos2");
        this.delay = section.getLong("delay");
        this.limit = section.getInt("limit");
        this.amount = section.getInt("amount");
        this.subType = section.getString("subType");
        this.mustSpawnOn = Material.valueOf(section.getString("block"));
        this.type = SkyblockEntityType.valueOf(section.getString("type"));

        this.spawned = new ArrayList<>();
        this.blocks = getAvailableBlocksFromTwoPoints(pos1 , pos2 , mustSpawnOn);
    }

    public void start() {
        new BukkitRunnable() {
            @Override
            public void run() {
                spawn();
            }
        }.runTaskTimer(Skyblock.getPlugin(), 5L, delay);
    }

    public void spawn() {
        spawned.removeIf((entity) -> entity.getVanilla().isDead() || entity.getLifeSpan() <= 0);

        if (spawned.size() >= limit) return;

        for (int i = 0; i < amount; i++) {
            if (Bukkit.getOnlinePlayers().isEmpty()) break;

            if (!hasPlayers) break;

            Location random = random();

            if (random == null) continue;

            SkyblockEntity entity = type.getNewInstance(subType);

            if (entity == null) continue;

            entity.spawn(random);
            entity.setLifeSpan((int) delay);
            spawned.add(entity);
        }
    }

    public Location random() {
        if (locationRequests > 500) {
            locationRequests = 0;
            return null;
        }

        Block block = blocks.get(Util.random(0, blocks.size() - 1));

        if (block.getType() != Material.AIR && block.getLocation().clone().add(0, 1, 0).getBlock().getType() == Material.AIR &&
                block.getLocation().clone().add(0, 2, 0).getBlock().getType() == Material.AIR && block.getType().isSolid() && block.getLocation().getBlock().getType().equals(mustSpawnOn)) {
            return block.getLocation().add(0.5, 1, 0.5);
        }

        locationRequests++;
        return random();
    }

    public List<Block> getAvailableBlocksFromTwoPoints(Location loc1 , Location loc2 , Material expectedBlockType){
        List<Block> blocks = new ArrayList<>();
        int topBlockX = (Math.max(loc1.getBlockX(), loc2.getBlockX()));
        int bottomBlockX = (Math.min(loc1.getBlockX(), loc2.getBlockX()));

        int topBlockY = (Math.max(loc1.getBlockY(), loc2.getBlockY()));
        int bottomBlockY = (Math.min(loc1.getBlockY(), loc2.getBlockY()));

        int topBlockZ = (Math.max(loc1.getBlockZ(), loc2.getBlockZ()));
        int bottomBlockZ = (Math.min(loc1.getBlockZ(), loc2.getBlockZ()));

        for(int x = bottomBlockX; x <= topBlockX; x++)
        {
            for(int z = bottomBlockZ; z <= topBlockZ; z++)
            {
                for(int y = bottomBlockY; y <= topBlockY; y++)
                {
                    Block block = loc1.getWorld().getBlockAt(x, y, z);
                    Material blockType = block.getType();
                    if (!blockType.equals(expectedBlockType)) continue;
                    if (block.getLocation().add(0 , 1 , 0).getBlock().getType() != Material.AIR) continue; // if upper block is not air then we just ignore it
                    blocks.add(block);
                }
            }
        }

        return blocks;
    }
}
