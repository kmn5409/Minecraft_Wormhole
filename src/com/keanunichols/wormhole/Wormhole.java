package com.keanunichols.wormhole;


//import org.bukkit.Bukkit;
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
	
	protected void spawnCreeper(Location loc, World wld){
		wld.spawnEntity(loc, EntityType.ENDERMAN);
		wld.spawnEntity(loc, EntityType.GHAST);
		wld.spawnEntity(loc, EntityType.WITHER_SKELETON);
	}
	
	protected void pyramid(Location loc, Material mat, int r) {
		Material[] wormholeBlockTypes = new Material[] {Material.GREEN_STAINED_GLASS,Material.RED_STAINED_GLASS};
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
				w.getBlockAt(x, py, z).setType(wormholeBlockTypes[1]);
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
	protected Location generateWormhole(Player plr){
		plr.sendMessage("Spawned block");
		Location pLocation = plr.getLocation();
		for(int i=0;i<8;i++){
			Location nLocation = pLocation.add(0,1,0);
			System.out.println(nLocation.toString());
			nLocation.getBlock().setType(Material.AIR);
		}
		System.out.println("spawned block\n\n");
		pyramid(pLocation,Material.OBSIDIAN,2);
		//pLocation = pLocation.add(0,1,0);
		World wld = plr.getWorld();
		moveToward(plr,pLocation,5);
		spawnCreeper(pLocation,wld);
		int randomNum = randomInt(15,40);
		Location loc = new Location(wld, plr.getLocation().getBlockX(), plr.getLocation().getBlockY() + randomNum, plr.getLocation().getBlockZ());
		return loc;
		
		//System.out.println("After");
	}

}
