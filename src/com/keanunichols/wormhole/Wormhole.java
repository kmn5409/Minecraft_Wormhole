package com.keanunichols.wormhole;


//import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
//import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;


public class Wormhole{



	public void moveToward(Entity entity, Location to, double speed){
        Location loc = entity.getLocation();
        double x = loc.getX() - to.getX();
        double y = loc.getY() - to.getY();
        double z = loc.getZ() - to.getZ();
        Vector velocity = new Vector(x, y, z).normalize().multiply(-speed);
        entity.setVelocity(velocity);   
    }
	
	protected int randomInt(int min, int max){
		int number = min + (int) (Math.random() * (max - min));
		return number;
	}
	
	
	
	protected Location generateWormhole(Player plr){
		plr.sendMessage("Spawned block");
		Location pLocation = plr.getLocation();
		for(int i=0;i<8;i++){
			Location nLocation = pLocation.add(0,1,0);
			System.out.println(nLocation.toString());
			nLocation.getBlock().setType(Material.AIR);
		}
		System.out.println("spawned block\n\n");
		pLocation = pLocation.add(0,1,0);
		pLocation.getBlock().setType(Material.OBSIDIAN);
		moveToward(plr,pLocation,5);
		World wld = plr.getWorld();
		int randomNum = randomInt(15,40);
		Location loc = new Location(wld, plr.getLocation().getBlockX(), plr.getLocation().getBlockY() + randomNum, plr.getLocation().getBlockZ());
		return loc;
		
		//System.out.println("After");
	}

}
