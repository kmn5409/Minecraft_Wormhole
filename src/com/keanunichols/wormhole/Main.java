package com.keanunichols.wormhole;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class Main extends JavaPlugin {
	
	public static List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<Block>();
        for(int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for(int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for(int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                   blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }
	
	public int randomInt(int min, int max){
		//int min = 0;
		//int max = 2;
		int number = min + (int) (Math.random() * (max - min));
		return number;
	}
	
	public void moveToward(Entity entity, Location to, double speed){
        Location loc = entity.getLocation();
        double x = loc.getX() - to.getX();
        double y = loc.getY() - to.getY();
        double z = loc.getZ() - to.getZ();
        Vector velocity = new Vector(x, y, z).normalize().multiply(-speed);
        entity.setVelocity(velocity);   
    }
	
	
	public long randomNumberTime(){
		long leftLimit = 100L;
	    long rightLimit = 600L;
	    long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
		return generatedLong;
	}
	
	@Override
    public void onEnable() {
		JavaPlugin plugin = this;
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable()
        {
            public void run()
            {			
            	for (Player online : Bukkit.getServer().getOnlinePlayers()){
            		Player plr = Bukkit.getPlayer(online.getName());
            		World wld = plr.getWorld();
	            	plr.sendMessage(Float.toString(randomNumberTime()));
	            	Location loc = plr.getLocation();
	    			loc = loc.add(3,0,0);
	    			loc.getBlock().setType(Material.OBSIDIAN);
	    			List<Block> blocks = getNearbyBlocks(loc, 3);
	    			for(Block blk:blocks){
	    				Location nloc = blk.getLocation();
	    				nloc.setY(nloc.getY()*1.2);
	    				FallingBlock fblk = nloc.getWorld().spawnFallingBlock(nloc, blk.getBlockData());
	    				fblk.setDropItem(false);
	    				//nloc.getWorld().spawnFallingBlock(nloc, blk.getBlockData());
	    			}
	    			for(Entity ent: wld.getNearbyEntities(loc,3,3,3)){
	    				moveToward(ent,loc,3);
	    			}
	    			
	    			//moveToward(plr, loc, 5);
	    			/*
	    			for(Entity ent: wld.getNearbyEntities(loc,10,10,10)){
	    				int x = loc.getBlockX() + randomInt(20,70);
	    				int y = loc.getBlockY() + randomInt(20,70);
	    				int z = loc.getBlockZ() + randomInt(20,70);
	    				ent.teleport(new Location(wld, x, y, z));
	    			}
	    			*/
            	}
            	
            	//Bolt();
            }
        }, 20L, randomNumberTime());
    }
    @Override
    public void onDisable() {
    }

}
