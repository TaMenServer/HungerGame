package com.tamenserver.hungergame.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.tamenserver.hungergame.border.Border;
import com.tamenserver.hungergame.command.CommandHungerGame;
import com.tamenserver.hungergame.listener.HungerGameListener;
import com.tamenserver.hungergame.world.WorldGenerator;

public class Game {
    private JavaPlugin plugin;
    private Location spawnLocation;
    private Situation situation;
    private String gameName;
    private WorldGenerator generator;
    private HungerGameListener listener;
    private Border border;
    private HashMap<UUID,Location> oldloc = new HashMap<UUID,Location>();
    private ArrayList<Player> survivals = new ArrayList<Player>();
    private ArrayList<Player> deaths = new ArrayList<Player>();
    
    public Game(JavaPlugin plugin,String gameName,String worldName) {
        this.plugin = plugin;
        this.gameName = gameName;
        setUpGame(worldName);
    }
    
    private void setUpGame(String worldName) {
        generator = new WorldGenerator(plugin,worldName);
        situation = Situation.SetUp;
        spawnLocation = generator.getSpawnLocation();
        listener = new HungerGameListener(plugin,this);
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }
    
    public void joinPlayer(Player p){
        survivals.add(p);
        p.sendMessage("你成功加入了游戏"+gameName);
        if(survivals.size() >= 10){
            startGame();
        }
    }
    
    public void startGame() {
        for (Player player : survivals) {
            player.sendMessage("游戏开始！");
            oldloc.put(player.getUniqueId(), player.getLocation());
            player.teleport(spawnLocation);
            player.getInventory().clear();
        }
        situation = Situation.Start;
        if(survivals.size() == 1){
            finishGame();
        }
        BukkitRunnable br1 = new BukkitRunnable(){
          @Override
          public void run(){
              situation = Situation.Doing;
          }
        };
        br1.runTaskLater(plugin,6000L);
        BukkitRunnable br2 = new BukkitRunnable(){
            @Override
            public void run(){
                for(Player p:survivals){
                    p.teleport(spawnLocation);
                    p.sendMessage("最终对决开始！");
                }
                for(Player p:deaths){
                    p.teleport(spawnLocation);
                }
            }
        };
        br2.runTaskLater(plugin, 30000L);
    }
    
    public void quitPlayer(Player p){
        switch(situation){
            case SetUp:
            case Start:{
                survivals.remove(p);
                for(Player player : survivals){
                    player.sendMessage(p.getName()+"退出了游戏，现在剩余人数"+survivals.size());
                }
                break;
            }
            case Doing:
            case Finish:{
                if(survivals.contains(p)){
                    survivals.remove(p);
                    for(Player player : survivals){
                        player.sendMessage(p.getName()+"退出了游戏，现在剩余人数"+survivals.size());
                    }
                }else if(deaths.contains(p)){
                    deaths.remove(p);
                    p.setGameMode(GameMode.SURVIVAL);
                    p.teleport(oldloc.get(p.getUniqueId()));
                }
                break;
            }
        }
        if(survivals.size() == 1){
            finishGame();
        }
    }
    
    public void deathPlayer(Player death,Player killer){
        deaths.add(death);
        survivals.remove(death);
        death.setGameMode(GameMode.SPECTATOR);
        death.sendMessage("你被"+killer.getName()+"杀死了！切换为旁观模式");
        killer.sendMessage("你杀死了"+death.getName());
        for(Player p : survivals){
            if(p.equals(killer)){
                continue;
            }
            p.sendMessage(death.getName()+"被"+killer.getName()+"杀死了！当前剩余人数"+String.valueOf(survivals.size()));
        }
        if(survivals.size() == 1){
            finishGame();
        }
    }

    public void deathPlayer(Player death){
        deaths.add(death);
        survivals.remove(death);
        death.setGameMode(GameMode.SPECTATOR);
        death.sendMessage("你死了！切换为旁观模式");
        for(Player p : survivals){
            p.sendMessage(death.getName()+"死了！当前剩余人数"+String.valueOf(survivals.size()));
        }
        if(survivals.size() == 1){
            finishGame();
        }
    }
    public void finishGame() {
        plugin.getServer().broadcastMessage(survivals.get(0).getName()+"赢得了游戏"+gameName);
        PlayerQuitEvent.getHandlerList().unregister(listener);
        PlayerDeathEvent.getHandlerList().unregister(listener);
        PlayerMoveEvent.getHandlerList().unregister(listener);
        EntityDamageEvent.getHandlerList().unregister(listener);
        for(Player p : survivals){
            p.setGameMode(GameMode.SURVIVAL);
            p.teleport(oldloc.get(p.getUniqueId()));
            p.sendMessage("游戏结束，你是胜利者！");
        }
        for(Player p : deaths){
            p.setGameMode(GameMode.SURVIVAL);
            p.teleport(oldloc.get(p.getUniqueId()));
            p.sendMessage("游戏结束，"+survivals.get(0).getName()+"是胜利者！");
        }
        CommandHungerGame.games.remove(this);
        situation = Situation.Finish;
    }
    
    public Situation getSituation(){
        return situation;
    }
    
    public void setSituation(Situation s){
        this.situation = s;
    }
    
    public boolean isContainsPlayer(Player p){
        return isContainsSurvivalPlayer(p) || isContainsDeathPlayer(p);
    }
    
    public boolean isContainsSurvivalPlayer(Player p){
        return survivals.contains(p);
    }
    
    public boolean isContainsDeathPlayer(Player p){
        return deaths.contains(p);
    }
    
    public Location getSpawnLocation(){
        return spawnLocation;
    }
    
    public boolean isWorldInitialized(){
        return generator.isWorldInitialized();
    }
    
    public boolean isOutOfBorder(Location loc){
        return border.isOutOfBorder(spawnLocation,loc);
    }
}
