package com.tamenserver.hungergame.border;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Border {
    JavaPlugin plugin;
    public Border(JavaPlugin plugin) {
        this.plugin = plugin;
        BukkitRunnable runnable = new BukkitRunnable(){
            @Override
            public void run(){
                count++;
                if (count > 40 && border >= 20) {
                    border--;
                }
                if(border == 20){
                    this.cancel();
                }
            }
        };
        runnable.runTaskTimer(plugin, 0L, 300L); // 15s
    }
    
    private int border = 100;
    private int count = 0;
    
    public boolean isOutOfBorder(Location spawnLoc, Location myLoc) {
        if (Math.abs(myLoc.getBlockX() - spawnLoc.getBlockX()) < border || Math.abs(myLoc.getBlockZ() - spawnLoc.getBlockZ()) < border) {
            return false;
        }
        return true;
    }
}
