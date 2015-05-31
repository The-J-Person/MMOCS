/**
 * 
 */
package server;

import common.*;
import java.util.*;

/**
 *
 *
 */
public class Player implements MapObject {

	public static final int distance=5;
	public static final int BaseHealth=10; //Randomly picked
	Coordinate C;
	Hashtable<Resource,Integer> Inventory = new Hashtable<Resource,Integer>();
	boolean admin;
	int Health;
	int MaxHealth;
	int ID;
	int Power;
	
	public Player(int id, boolean admn)
	{
		ID=id;
		admin=admn;
		MaxHealth=BaseHealth;
		Health=BaseHealth; 
		Power=1;
		C=DataBase.GetPlayerCoordinate(id);
		//TODO add inventory
	}
	
	/* (non-Javadoc)
	 * @see server.MapObject#Coordinates()
	 */
	@Override
	public Coordinate Coordinates() {
		// TODO Auto-generated method stub
		return C;
	}
	
	/**
	 * Attempts to move this player to a new location.
	 * @param co Coordinate to move into
	 * @return Success if moved, Failure if destination is unreachable or occupied.
	 */
	public boolean Move(Coordinate co)
	{
		if(admin)
		{
			C = co;
			return true;
		}
		Tile dest = WorldMap.getInstance().get_tile_at(co, true);
		if(dest.canMoveOn())
		{
			C = co;
			dest.setMapObjectType(MapObjectType.PLAYER);
			WorldMap.getInstance().update_tile(dest);
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see server.MapObject#Resource()
	 */
	@Override
	public common.Resource Resource() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see server.MapObject#Health()
	 */
	@Override
	public int Health() {
		return Health;
	}
	
	public void setHealth(int nHealth)
	{
		Health=nHealth;
	}
	
	public int view_distance()
	{
		return distance;
	}

	@Override
	public void Damage(int amount) {
		Health=Health-amount;
	}
	
	public boolean isAdmin()
	{
		return admin;
	}

	/**
	 * Checks if player can see the Tile he asked to see.
	 * @return
	 */
	public boolean see_Tile(Coordinate Co)
	{
		if(!admin && Co.distance(C)>distance) return false;
		return true;
	}
	
	/**
	 * Checks if player can see the Tile he asked to see.
	 * @return
	 */
	public boolean change_Tile(Tile Ti)
	{
		if(!admin && Ti.getCoordinate().distance(C)>distance) return false;
		//TODO Check inventory
		WorldMap.getInstance().update_tile(Ti);
		return true;
	}
	
	/**
	 * Attack a monster or mapobject
	 * @param Co
	 * @return
	 */
	public boolean attack(Coordinate Co)
	{
		if(!admin && Co.distance(C)>distance) return false;
		Resource r = WorldMap.getInstance().get_resource(Co,Power);
		if(r!=null)
		{
			if(Inventory.get(r)!=null) Inventory.put(r,Inventory.get(r)+1);
			else Inventory.put(r,1);
		}
		return true;
	}
	
	public boolean gather_ground(Coordinate Co)
	{
		if(!admin && Co.distance(C)>distance) return false;
		Resource r = WorldMap.getInstance().get_floor_resource(Co);
		if(r!=null)
		{
			if(Inventory.get(r)!=null) Inventory.put(r,Inventory.get(r)+1);
			else Inventory.put(r,1);
		}
		return true;
	}
}
