package com.tamenserver.hungergame.gamerandom;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class GameRandom {
    private static Material[] mlist=new Material[]{Material.LOG,Material.LOG,Material.BREAD,Material.BREAD,Material.ROTTEN_FLESH,Material.WOOD_AXE,Material.WOOD_SWORD,Material.LEATHER_HELMET,Material.LEATHER_CHESTPLATE,Material.LEATHER_BOOTS,Material.LEATHER_LEGGINGS};
    public GameRandom(Inventory i){
        int nums=(int) (new Random().nextInt(6)+4);
        for(int thisnum=1;thisnum<=nums;thisnum++){
            int num=(int) (new Random().nextInt(GameRandom.mlist.length));
            if(num!=0&&num!=1) i.setItem((int) (new Random().nextInt(27)), new ItemStack(mlist[num],1));
            else if(mlist[num]==Material.LEATHER_HELMET||mlist[num]==Material.LEATHER_CHESTPLATE||mlist[num]==Material.LEATHER_BOOTS||mlist[num]==Material.LEATHER_LEGGINGS){
                int tmpran=(int) (new Random().nextInt(4));
                if(tmpran==0)   i.setItem((int) (new Random().nextInt(27)), new ItemStack(mlist[num],(int) (new Random().nextInt(10))));
            }else i.setItem((int) (new Random().nextInt(27)), new ItemStack(mlist[num],(int) (new Random().nextInt(10))));
        }
        
    }
}
    