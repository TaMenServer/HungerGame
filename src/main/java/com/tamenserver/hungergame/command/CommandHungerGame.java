package com.tamenserver.hungergame.command;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.tamenserver.hungergame.manager.Manager;
import com.tamenserver.hungergame.world.WorldGenerator;

public class CommandHungerGame implements CommandExecutor {
    private JavaPlugin plugin;
    
    public CommandHungerGame(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    
    private Manager manager;
    
    private WorldGenerator worldGenerator;
    private Location spawnLocation;
    
    private void onCommandStart(String worldName) {
        worldGenerator = new WorldGenerator(plugin, worldName);
        this.spawnLocation = worldGenerator.getSpawnLocation();
        manager = new Manager(plugin);
        manager.onGameSetUp(spawnLocation);
    }
    
    private void onCommandJoin(Player player) {
        manager.survivingPlayerList.add(player);
        player.teleport(spawnLocation);
        player.sendMessage("您已加入游戏。");
        if (manager.survivingPlayerList.size() >= 10) {
            manager.onGameStart();
        }
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command,
            String label, String[] args) {
        if (command.getName().equalsIgnoreCase("HungerGame")) {
            switch (args[0].toLowerCase()) {
                case "start": {
                    if (args.length > 1) {
                        sender.sendMessage("你输入的指令错误。");
                        return true;
                    }
                    if (!sender.isOp()) {
                        sender.sendMessage("您没有令游戏开始的权限。");
                        return true;
                    }
                    onCommandStart("hunger_game_world");
                    return true;
                }
                case "join": {
                    if (!worldGenerator.isWorldInitialized()) {
                        sender.sendMessage("世界尚未生成，请稍后再试。");
                        return true;
                    }
                    onCommandJoin((Player)sender);
                    return true;
                }
            }
        }
            
        return false;
    }
    
}
