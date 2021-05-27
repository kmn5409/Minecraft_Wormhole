package com.keanunichols.wormhole;


import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
//import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;



public class Main extends JavaPlugin {
	
	public long randomNumberTime(){
		long leftLimit = 400L;
	    long rightLimit = 600L;
	    long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
		return generatedLong;
	}
	
	@Override
    public void onEnable() {
		final JavaPlugin plugin = this;
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable()
        {
            public void run()
            {			
            	Object[] plrs = Bukkit.getOnlinePlayers().toArray();
            	Random random_Location = new Random();
            	int size = plrs.length;
            	if(size > 0){
            		Random rand = new Random(); 
                	int n = rand.nextInt(plrs.length); 
            		//int random = new Random().nextInt(numPlayers);
            		//Player player = (Player)Bukkit.getOnlinePlayers().toArray()[random];
            		Wormhole wh = new Wormhole();
            		final Location loca = wh.generateWormhole((Player)plrs[n], random_Location);
            		final Player plr = (Player)plrs[n];
            		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {


            			public void run() {
                            // Send the message
                        	System.out.println("Delayed");
                        	plr.sendMessage(""+loca.getBlockY());
                        	plr.teleport(loca);
                        }
                    }, 10L);
            	}
            }
        }, 300L, randomNumberTime());
    }
    @Override
    public void onDisable() {
    }

}
