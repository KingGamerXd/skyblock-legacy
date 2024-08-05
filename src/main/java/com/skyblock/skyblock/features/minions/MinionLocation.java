package com.skyblock.skyblock.features.minions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
@SerializableAs(value = "MinionLocation")
public class MinionLocation implements ConfigurationSerializable {
    private final double x;
    private final double y;
    private final double z;
    private final String worldName;

    @Override
    public Map<String, Object> serialize() {
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        result.put("x" , x);
        result.put("y" , y);
        result.put("z" , z);
        result.put("world_name" , worldName);
        return result;
    }

    public static MinionLocation deserialize(Map<String, Object> args) {
        double x = (double) args.get("x");
        double y = (double) args.get("y");
        double z = (double) args.get("z");
        String worldName = (String) args.get("world_name");
        return new MinionLocation(x , y , z , worldName);
    }

    public Location toBukkitLocation(){
        return new Location(Bukkit.getWorld(worldName) , x , y , z);
    }
    public static MinionLocation fromBukkitLocation(Location location){
        return new MinionLocation(location.getX(), location.getY(), location.getZ() , location.getWorld().getName());
    }
}
