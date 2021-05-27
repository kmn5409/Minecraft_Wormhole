package com.keanunichols.wormhole;


//import org.bukkit.Bukkit;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
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
	
	protected void spawnCreeper(Player plr, Location loc, World wld, Random rand){
		Chunk chunk = plr.getLocation().getChunk();
		int y = plr.getLocation().getBlockY();
		Location randLoc = chunk.getBlock(rand.nextInt(16), y,rand.nextInt(16)).getLocation();
		wld.spawnEntity(randLoc, EntityType.ENDERMAN);
		wld.spawnEntity(randLoc, EntityType.WITHER_SKELETON);
		wld.spawnEntity(randLoc.add(0,7,0), EntityType.GHAST);
	}
	
	protected void pyramid(Location loc, Material mat, int r) {
		Material[] wormholeBlockTypes = new Material[] {Material.CYAN_STAINED_GLASS,Material.RED_STAINED_GLASS, Material.AIR};
	    int px = loc.getBlockX();
	    int py = loc.getBlockY();
	    int pz = loc.getBlockZ();
	    World w = loc.getWorld();
	    /*
	    int upper_x = px+2;
	    int upper_z = pz+2;
	    int lower_x = px-2;
	    int lower_z = pz-2;
	    */
		for(int x=px-1; x<=px+1; x++){
			for(int z=pz-1; z<=pz+1; z++){
				if(x == px && z==pz){
					continue;
				}
				w.getBlockAt(x, py, z).setType(wormholeBlockTypes[0]);
			}
		}
		
		py+=1;
		for(int x=px-2; x<=px+2; x++){
			for(int z=pz-2; z<=pz+2; z++){
				if(x == px && z==pz){
					continue;
				}
				w.getBlockAt(x, py, z).setType(wormholeBlockTypes[1]);
			}
		}
		
		for(int x=px-1; x<=px+1; x++){
			for(int z=pz-1; z<=pz+1; z++){
				if(x == px && z==pz){
					continue;
				}
				w.getBlockAt(x, py, z).setType(wormholeBlockTypes[2]);
			}
		}

		    
	    /*
	    for (int x = cx - r; x <= cx +r; x++) {
	        for (int z = cz - r; z <= cz +r; z++) {
	            if ((cx - x) * (cx - x) + (cz - z) * (cz - z) <= rSquared) {
	                w.getBlockAt(x, cy, z).setType(mat);
	            }
	        }
	    }*/
	}
	
	//for five height create the glass (3,5,7,9,11)(top to bottom)
	//                                 (green,cyan,blue,black,red)
	protected Location generateWormhole(Player plr,Random rand){
		plr.sendMessage("Spawned block");
		Location pLocation = plr.getLocation();
		World wld = plr.getWorld();
		spawnCreeper(plr, pLocation,wld, rand);
		for(int i=0;i<8;i++){
			Location nLocation = pLocation.add(0,1,0);
			System.out.println(nLocation.toString());
			nLocation.getBlock().setType(Material.AIR);
		}
		System.out.println("spawned block\n\n");
		pyramid(pLocation,Material.OBSIDIAN,2);
		//pLocation = pLocation.add(0,1,0);
		moveToward(plr,pLocation,5);
		int randomNum = randomInt(15,40);
		Location loc = new Location(wld, plr.getLocation().getBlockX(), plr.getLocation().getBlockY() + randomNum, plr.getLocation().getBlockZ());
		return loc;
		
		//System.out.println("After");
	}

}
