package com.keanunichols.wormhole;


import java.util.Random;
import java.lang.Math;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
//import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;



public class Main extends JavaPlugin {
	
	public long randomNumberTime(){
		long leftLimit = 1200L;
	    long rightLimit = 1300L;
	    long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
		return generatedLong;
	}
	
	
	public void getWormHole(Object [] plrs, Random random_Location, int size,final boolean type, JavaPlugin plugin){
		if(size > 0){
        	for(Player plr: Bukkit.getServer().getOnlinePlayers()){
        		Wormhole wh = new Wormhole();
        		//Player plr = (Player)plrs[n];
        		final Location[] loca = wh.generateWormhole(plr, random_Location, type);
        		//final Player person = (Player)plrs[n];
        		final Player person = plr;
        		if(type){
	        		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
	
	
	        			public void run() {
	                        // Send the message
	                    	//System.out.println("Delayed");
	                    	//person.sendMessage(""+loca[0].getBlockY());
	                    	person.teleport(loca[0]);
	                    }
	                }, 10L);
        		}
        		
        		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {


        			public void run() {
                        // Send the message
                    	//System.out.println("removed wormhole");
                    	Wormhole wh = new Wormhole();
                    	if(type){
                    		//person.sendMessage(""+loca[1].getBlockY());
                    		wh.removeWormHole(loca[1]);
                    	}
                    	else{
                    		//person.sendMessage(""+loca[0].getBlockY());
                    		wh.removeWormHole(loca[0]);
                    		
                    	}
                    }
                }, 300L);
        	}
    	}
		
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
            	Random rd = new Random(); // creating Random object
            	if(rd.nextBoolean()){
            		/*
            		for (Player plr : Bukkit.getServer().getOnlinePlayers()) {
                		plr.sendMessage("teleporting player");
                	}
                	*/
            		getWormHole(plrs, random_Location, size,true, plugin);
            	}
            	else{
            		/*
            		for (Player plr : Bukkit.getServer().getOnlinePlayers()) {
                		plr.sendMessage("spawning enemies");
                	}
                	*/
            		getWormHole(plrs, random_Location, size, false, plugin);
            	}
            	
            }
        //}, 1200, randomNumberTime());
        }, 1100, randomNumberTime());
    }
    @Override
    public void onDisable() {
    }

}
