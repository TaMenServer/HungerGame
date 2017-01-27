package com.tamenserver.hungergame.listener;


import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.tamenserver.hungergame.game.Game;
import com.tamenserver.hungergame.game.Situation;

public class HungerGameListener implements Listener{
    @SuppressWarnings("unused")
    private JavaPlugin plugin;
    private Game game;
    private ConcurrentHashMap<UUID, Integer> cooldown = new ConcurrentHashMap<UUID,Integer>();
    public HungerGameListener(JavaPlugin plugin, Game game){
        this.plugin = plugin;
        this.game = game;
        BukkitRunnable br = new BukkitRunnable(){
            @Override
            public void run(){
                for(Entry<UUID,Integer> e : cooldown.entrySet()){
                    if(e.getValue()>0){
                        cooldown.put(e.getKey(),e.getValue()-1);
                    }else{
                        cooldown.remove(e.getKey());
                    }
                }
            }
        };
        br.runTaskTimer(plugin,0L, 20L);
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent evt){
        if(game.isContainsPlayer(evt.getPlayer())){
            game.quitPlayer(evt.getPlayer());
        }
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent evt){
        if(!game.isContainsSurvivalPlayer(evt.getEntity()) || game.getSituation().equals(Situation.SetUp)){
            return;
        }
        if(evt.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent){
            Entity killer = ((EntityDamageByEntityEvent)evt.getEntity().getLastDamageCause()).getDamager();
            if(killer instanceof Player){
                game.deathPlayer(evt.getEntity(),(Player) killer);
            }else{
                game.deathPlayer(evt.getEntity());
            }
        }else{
            game.deathPlayer(evt.getEntity());
        }
        
    }
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent evt){
        if(game.isContainsPlayer(evt.getPlayer()) && (game.getSituation().equals(Situation.Doing) || game.getSituation().equals(Situation.Start))){
            if(game.isOutOfBorder(evt.getPlayer().getLocation())){
                if(!cooldown.containsKey(evt.getPlayer().getUniqueId())){
                    if(game.isContainsSurvivalPlayer(evt.getPlayer())){
                        evt.getPlayer().setHealth(Math.max(evt.getPlayer().getHealth()-2,0));
                        evt.getPlayer().sendMessage("你已超过边界，请快速向出生点方向移动！");
                        cooldown.put(evt.getPlayer().getUniqueId(), 5);
                    }else{
                        evt.getPlayer().teleport(game.getSpawnLocation());
                    }
                }
            }
        }
    }
    
    @EventHandler
    public void onPlayerDamage(EntityDamageEvent evt){
        if(evt.getEntity() instanceof Player && game.isContainsDeathPlayer((Player)evt.getEntity())){
            evt.setCancelled(true);
            evt.getEntity().teleport(game.getSpawnLocation());
            return;
        }
        if(evt.getEntity() instanceof Player && game.getSituation().equals(Situation.Start) && game.isContainsPlayer((Player)evt.getEntity())){
            evt.setCancelled(true);
            return;
        }
    }
}
