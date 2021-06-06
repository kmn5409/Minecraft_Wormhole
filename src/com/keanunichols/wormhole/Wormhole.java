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
	
	protected void spawnEnemies(Player plr, Location loc, World wld, Random rand){
		Chunk chunk = plr.getLocation().getChunk();
		int y = plr.getLocation().getBlockY();
		Location randLoc = chunk.getBlock(rand.nextInt(16), y+2,rand.nextInt(16)).getLocation();
		wld.spawnEntity(randLoc, EntityType.ENDERMAN);
		wld.spawnEntity(randLoc, EntityType.WITHER_SKELETON);
		wld.spawnEntity(randLoc.add(0,7,0), EntityType.GHAST);
	}
	
	protected void pyramid(Location loc, int r) {
		Material[] wormholeBlockTypes = new Material[] {Material.CYAN_STAINED_GLASS,Material.RED_STAINED_GLASS, Material.AIR};
	    int px = loc.getBlockX();
	    int py = loc.getBlockY();
	    int pz = loc.getBlockZ();
	    World w = loc.getWorld();
	    
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
	}
	
	protected void removeWormHole(Location loc){
		int px = loc.getBlockX();
	    int py = loc.getBlockY();
	    int pz = loc.getBlockZ();
	    World w = loc.getWorld();

		for(int x=px-1; x<=px+1; x++){
			for(int z=pz-1; z<=pz+1; z++){
				if(x == px && z==pz){
					continue;
				}
				w.getBlockAt(x, py, z).setType(Material.AIR);
			}
		}
		
		py+=1;
		for(int x=px-2; x<=px+2; x++){
			for(int z=pz-2; z<=pz+2; z++){
				if(x == px && z==pz){
					continue;
				}
				w.getBlockAt(x, py, z).setType(Material.AIR);
			}
		}
		
	}
	
	protected Location[] generateWormhole(Player plr,Random rand, boolean wormHolePlayer){
		//plr.sendMessage("Spawned block");
		Location pLocation = plr.getLocation();
		World wld = plr.getWorld();
		for(int i=0;i<8;i++){
			Location nLocation = pLocation.add(0,1,0);
			//System.out.println(nLocation.toString());
			nLocation.getBlock().setType(Material.AIR);
		}
		if(wormHolePlayer){
			pyramid(pLocation,2);
			moveToward(plr,pLocation,5);
			int randomY = randomInt(15,40);
			int randomX = randomInt(5,10);
			int randomZ = randomInt(5,10);
			Location loc = new Location(wld, plr.getLocation().getBlockX() + randomX, plr.getLocation().getBlockY() + randomY, plr.getLocation().getBlockZ() + randomZ);
			Location[] locations = {loc, pLocation};
			return locations;
		}else{
			spawnEnemies(plr, pLocation,wld, rand);
			
			//System.out.println("spawned block\n\n");
			pyramid(pLocation, 2);
		}
		Location[] locations = {pLocation};
		return locations;
	}

}
