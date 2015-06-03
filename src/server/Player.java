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
	Hashtable<Resource,Integer> Inventory;
	Stack<Update> updateLog;
	
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
		Inventory = new Hashtable<Resource,Integer>();
		updateLog = new Stack<Update>();
		if(!admin)WorldMap.getInstance().login(this);
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
		Tile dest = WorldMap.getInstance().get_tile_at(co, false);
		if(dest.canMoveOn())
		{
			Tile orig=WorldMap.getInstance().get_tile_at(C, false);
			orig.setMapObjectType(null);
			C = co;
			dest.setMapObjectType(MapObjectType.PLAYER);
			WorldMap.getInstance().update_tile(orig);
			WorldMap.getInstance().update_tile(dest);
			DataBase.UpdatePlayerLocation(ID, C);
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
		add_event_to_stack(new Update(UpdateType.HIT_POINTS,Health));
	}
	
	public int view_distance()
	{
		return distance;
	}
	
	@Override
	public void Damage(int amount) {
		Health=Health-amount;
		add_event_to_stack(new Update(UpdateType.HIT_POINTS,Health));
		//TODO something something database hitpoints after recovery is implemented...
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
	 * True if there's a MapObject in the target Tile
	 * @return
	 */
	public boolean object_in_tile(Coordinate co)
	{
		return WorldMap.getInstance().get_tile_at(co, false).getMapObjectType()!=null;
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
			//TODO Database Update inventory
			add_event_to_stack(new Update(UpdateType.RESOURCES,r));
		}
		return true;
	}
	
	/**
	 * Attempts to gather ground
	 * @param Co
	 * @return
	 */
	public boolean gather_ground(Coordinate Co)
	{
		if(!admin && Co.distance(C)>distance) return false;
		Resource r = WorldMap.getInstance().get_floor_resource(Co);
		if(r!=null)
		{
			if(Inventory.get(r)!=null) Inventory.put(r,Inventory.get(r)+1);
			else Inventory.put(r,1);
			//TODO Database Update inventory
			add_event_to_stack(new Update(UpdateType.RESOURCES,r));
		}
		return true;
	}
	
	/**
	 * Removes the player from the map
	 */
	public void logout()
	{
		if(!admin)WorldMap.getInstance().logout(this);
	}
	
	/**
	 * Used by WorldMap to let player know something near them happened.
	 * @param Up
	 */
	public void add_event_to_stack(Update Up)
	{
		updateLog.push(Up);
	}
	
	/**
	 * Returns ONE event if there are any in stack.
	 * If the stack is empty, null is returned.
	 * @return an Update object event, if any.
	 */
	public Update getEvents()
	{
		try
		{
			return updateLog.pop();
		}
		catch (EmptyStackException e)
		{
			return null;
		}
	}
	
	/**
	 * 
	 * @param Crafted
	 * @return
	 */
	public boolean craft(Resource Crafted)
	{
		Hashtable<Resource,Integer> ingrid = Crafted.recipe();
		Enumeration<common.Resource> ing=ingrid.keys();
		while(ing.hasMoreElements())
		{
			Resource R=ing.nextElement();
			if(!Inventory.containsKey(R) && Inventory.get(R) < ingrid.get(R)) return false;
		}
		ing = ingrid.keys();
		while(ing.hasMoreElements())
		{
			Resource R=ing.nextElement();
			Inventory.put(R, Inventory.get(R)-ingrid.get(R));
			DataBase.RemoveItemFromInventory(ID, R, ingrid.get(R));
		}
		if(Inventory.get(Crafted)!=null) Inventory.put(Crafted,Inventory.get(Crafted)+1);
		else Inventory.put(Crafted,1);

		add_event_to_stack(new Update(UpdateType.RESOURCES,Crafted));
		return true;
	}
}
