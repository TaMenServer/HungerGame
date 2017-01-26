package com.tamenserver.hungergame.gamerandom;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class GameRandom {
    private static Material[] mlist=new Material[]{Material.LOG,Material.BREAD,Material.ROTTEN_FLESH,Material.WOOD_AXE,Material.WOOD_SWORD,Material.LEATHER_HELMET,Material.LEATHER_CHESTPLATE,Material.LEATHER_BOOTS,Material.LEATHER_LEGGINGS};
    private Inventory tmpIn;
    public GameRandom(Inventory i){
        this.tmpIn=i;
        int nums=(int) (new Random().nextInt(20));
        for(int thisnum=1;thisnum<=nums;thisnum++){
            int num=(int) (new Random().nextInt(this.mlist.length));
            if(num==1) i.setItem(thisnum, new ItemStack(mlist[num],1));
            else    i.setItem(thisnum, new ItemStack(mlist[num],16));
        }
        
    }
}
