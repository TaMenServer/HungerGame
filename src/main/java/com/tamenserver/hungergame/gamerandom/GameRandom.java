package com.tamenserver.hungergame.gamerandom;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class GameRandom {
    private static Material[] mlist=new Material[]{Material.APPLE,Material.MUSHROOM_SOUP,Material.BREAD,Material.PORK,Material.RAW_FISH,Material.COOKED_FISH,Material.CAKE,Material.COOKIE,Material.MELON,Material.RAW_BEEF,Material.COOKED_BEEF,Material.RAW_CHICKEN,Material.COOKED_CHICKEN,Material.RABBIT,Material.COOKED_RABBIT};
    private Inventory tmpIn;
    public GameRandom(Inventory i){
        this.tmpIn=i;
        int nums=(int) (new Random().nextInt(20));
        for(int thisnum=1;thisnum<=nums;thisnum++){
            int num=(int) (new Random().nextInt(15));
            i.setItem(thisnum, new ItemStack(mlist[num],1));
        }
        
    }
}
