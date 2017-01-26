package com.tamenserver.hungergame.world;


import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import com.tamenserver.hungergame.gamerandom.GameRandom;

public class WorldGenerator {
    private final String worldname;
    private JavaPlugin plugin;
    private boolean isWorldInitialized;
    private World w;
    private Location spawnLocation;
    public WorldGenerator(JavaPlugin plugin,String worldname){
        this.plugin = plugin;
        this.worldname = worldname;
        isWorldInitialized = false;
        worldInit();
    }
    private void worldInit(){
        WorldCreator wc = new WorldCreator(worldname);
        w = wc.createWorld();
        isWorldInitialized = true;
        int[][] next = new int[][]{
            {-2,0},
            {2,0},
            {0,-2},
            {0,2},
            {1,1},
            {1,-1},
            {-1,-1},
            {-1,1}
        };
        Location l = w.getSpawnLocation();
        for(int i = 0; i < 8; i++){
            Location tmpl = w.getHighestBlockAt(new Location(l.getWorld(),l.getBlockX()+next[i][0],l.getBlockY(),l.getBlockZ()+next[i][1])).getLocation();
            Block tmpb = tmpl.getBlock();
            tmpb.setType(Material.CHEST);
            Inventory tmpi = ((Chest) tmpb.getState()).getInventory();
            GameRandom gr = new GameRandom(tmpi);
        }
        
        spawnLocation = w.getHighestBlockAt(w.getSpawnLocation()).getLocation();
    }
    
    public boolean isWorldInitialized(){
        return isWorldInitialized;
    }
    
    public Location getSpawnLocation(){
        return spawnLocation;
    }
    
    public World getWorld(){
        return w;
    }
}
