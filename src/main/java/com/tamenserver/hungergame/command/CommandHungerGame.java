package com.tamenserver.hungergame.command;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.tamenserver.hungergame.game.Game;
import com.tamenserver.hungergame.game.Situation;

public class CommandHungerGame implements CommandExecutor {
    private JavaPlugin plugin;
    public static HashMap<String,Game> games = new HashMap<String,Game>();
    public CommandHungerGame(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    
    private void onCommandSetUp(String gameName,String worldName) {
        Game game = new Game(plugin,gameName,worldName);
        games.put(gameName, game);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command,
            String label, String[] args) {
        if (command.getName().equalsIgnoreCase("HungerGame")) {
            switch (args[0].toLowerCase()) {
                case "setup": {
                    if (args.length > 3) {
                        sender.sendMessage("你输入的指令错误。");
                        return true;
                    }
                    if (!sender.isOp()) {
                        sender.sendMessage("您没有令游戏开始的权限。");
                        return true;
                    }
                    if(games.containsKey(args[1])){
                        sender.sendMessage("该游戏已经设置完成了！");
                        return true;
                    }
                    onCommandSetUp(args[1],args[2]);
                    return true;
                }
                case "join": {
                    if(!games.containsKey(args[1])){
                        sender.sendMessage("没有名字为"+args[1]+"的游戏");
                        return true;
                    }
                    if (!games.get(args[1]).isWorldInitialized()) {
                        sender.sendMessage("世界尚未生成，请稍后再试。");
                        return true;
                    }
                    if(games.get(args[1]).isContainsPlayer((Player)sender)){
                        sender.sendMessage("你已经加入了这个游戏！");
                        return true;
                    }
                    if(!games.get(args[1]).getSituation().equals(Situation.SetUp)){
                        sender.sendMessage("游戏已经开始了，你无法关闭！");
                        return true;
                    }
                    games.get(args[1]).joinPlayer((Player)sender);
                    return true;
                }
                case "start": {
                    if (!sender.isOp()) {
                        sender.sendMessage("您没有令游戏开始的权限。");
                        return true;
                    }
                    if(!games.containsKey(args[1])){
                        sender.sendMessage("没有名字为"+args[1]+"的游戏");
                        return true;
                    }
                    if(!games.get(args[1]).getSituation().equals(Situation.SetUp)){
                        sender.sendMessage("游戏已经开始了！");
                    }else{
                        games.get(args[1]).startGame();
                    } 
                    return true;
                }
            }
        }
            
        return false;
    }
    
}
