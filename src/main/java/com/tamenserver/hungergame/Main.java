package com.tamenserver.hungergame;


import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.tamenserver.hungergame.command.CommandHungerGame;

public class Main extends JavaPlugin {
    private Location spawn;
    @Override
    public void onEnable(){
        getCommand("hungergame").setExecutor(new CommandHungerGame(this));
        this.getLogger().info("HungerGame加载完成！");
        spawn = this.getServer().getWorld("world").getHighestBlockAt(this.getServer().getWorld("world").getSpawnLocation()).getLocation();
        this.getServer().getPluginManager().registerEvents(new Listener(){
            @EventHandler
            public void onPlayerLogin(PlayerJoinEvent evt){
                Player p = evt.getPlayer();
                p.teleport(spawn);
                p.setGameMode(GameMode.SURVIVAL);
            }
        }, this);
    }
    
    @Override
    public void onDisable(){
        this.getServer().getScheduler().cancelTasks(this);
        this.getLogger().info("HungerGame卸载完成！");
    }
}
