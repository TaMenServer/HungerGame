package com.tamenserver.hungergame;

import org.bukkit.plugin.java.JavaPlugin;

import com.tamenserver.hungergame.command.CommandHungerGame;

public class Main extends JavaPlugin {
    @Override
    public void onEnable(){
        getCommand("hungergame").setExecutor(new CommandHungerGame(this));
        this.getLogger().info("HungerGame加载完成！");
    }
    
    @Override
    public void onDisable(){
        this.getServer().getScheduler().cancelTasks(this);
        this.getLogger().info("HungerGame卸载完成！");
    }
}
