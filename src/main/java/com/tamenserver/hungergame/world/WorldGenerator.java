package com.tamenserver.hungergame.world;


import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class WorldGenerator {
    private final String worldname;
    private JavaPlugin plugin;
    
    public WorldGenerator(JavaPlugin plugin,String worldname){
        this.plugin = plugin;
        this.worldname = worldname;
        worldInit();
    }
    private void worldInit(){
        WorldCreator wc = new WorldCreator(worldname);
        wc.createWorld();
        plugin.getServer().getPluginManager().registerEvents(new Listener(){
            @EventHandler
            public void onWorldCreate(WorldInitEvent evt){
                if(evt.getWorld().getName().equals(worldname)){
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
                    World w = evt.getWorld();
                    Location l = w.getSpawnLocation();
                    for(int i = 0; i < 8; i++){
                        Location tmpl = w.getHighestBlockAt(new Location(l.getWorld(),l.getBlockX()+next[i][0],l.getBlockY(),l.getBlockZ()+next[i][1])).getLocation();
                        Block tmpb = tmpl.getBlock();
                        tmpb.setType(Material.CHEST);
                        Inventory tmpi = (Inventory) tmpb.getState();
                    }
                }
            }
        }, plugin);
    }
}
