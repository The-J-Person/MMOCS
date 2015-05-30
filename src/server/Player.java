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
	Coordinate C;
	Hashtable<Resource,Integer> Inventory = new Hashtable<Resource,Integer>();
	boolean admin;
	int Health;
	int ID;
	
	public Player(int id, boolean admn)
	{
		ID=id;
		admin=admn;
		Health=10; //Randomly picked
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
	public int Damage() {
		// TODO Auto-generated method stub
		return 1;
	}
	
	public boolean isAdmin()
	{
		return admin;
	}

}
