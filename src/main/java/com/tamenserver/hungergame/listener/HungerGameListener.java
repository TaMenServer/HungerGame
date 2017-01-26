package com.tamenserver.hungergame.listener;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class HungerGameListener implements Listener{
    private JavaPlugin plugin;
    public HungerGameListener(JavaPlugin plugin){
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent evt){
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent evt){
        
    }
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent evt){
        
    }
}
