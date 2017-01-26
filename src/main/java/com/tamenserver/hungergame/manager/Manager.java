package com.tamenserver.hungergame.manager;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Manager {
    private JavaPlugin plugin;
    private Location spawnLocation;
    
    public Manager(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    
    public ArrayList<Player> survivingPlayerList = new ArrayList<Player>();
    
    public void onGameSetUp(Location spawnLocation){
        this.spawnLocation = spawnLocation;
    }
    
    public void onGameStart() {
        for (Player player : survivingPlayerList) {
            player.sendMessage("游戏开始！");
            player.teleport(spawnLocation);
        }
    }
    
    public void playerQuit(Player player) {
        survivingPlayerList.remove(player);
    }
    
    public void onGameStop() {
        plugin.getServer().broadcastMessage(survivingPlayerList.get(0).getName()+"赢得了游戏。");
    }
}
