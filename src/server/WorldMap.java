/**
 * 
 */
package server;

import java.util.*;

import common.*;

/**
 *-1
 *
 */
public class WorldMap {
	private static WorldMap wm = new WorldMap();
//	private List<Tile> map;
//	private List<Coordinate> loaded;
	private Hashtable<Coordinate, Tile> map; 
	private Hashtable<Coordinate, MapObject> thingsOnMap;
	private List<Player> players;
	
	private WorldMap()
	{
		map=DataBase.GetMap();
		thingsOnMap=DataBase.GetMonsters();
		players=new ArrayList<Player>();
	}
	
	/**
	 * Returns singleton instance
	 * @return the instance
	 */
	public static WorldMap getInstance()
	{
		return wm;
	}
	
	/**
	 * 
	 * @param c
	 * @param player
	 * @return
	 */
	public Tile get_tile_at(Coordinate c, boolean player)
	{
		Tile req=map.get(c);
		
		if(req==null)
		{
			if(player)
			{
				/* GENERATE NEW MAP */
				List<Tile> neighbors = new ArrayList<Tile>();
				neighbors.add(new Tile(0,0,FloorType.GRASS,MapObjectType.BUSH));
				//Will now look at the...
				//Bottom Left
				Tile check=get_tile_at(new Coordinate(c.X()-1,c.Y()-1), false);
				if(check!=null) neighbors.add(check); 
				//Left
				check=get_tile_at(new Coordinate(c.X()-1,c.Y()), false);
				if(check!=null) neighbors.add(check); 
				//Top Left
				check=get_tile_at(new Coordinate(c.X()-1,c.Y()+1), false);
				if(check!=null) neighbors.add(check); 
				//Top
				check=get_tile_at(new Coordinate(c.X(),c.Y()+1), false);
				if(check!=null) neighbors.add(check); 
				//Top Right
				check=get_tile_at(new Coordinate(c.X()+1,c.Y()+1), false);
				if(check!=null) neighbors.add(check); 
				//Right
				check=get_tile_at(new Coordinate(c.X()+1,c.Y()), false);
				if(check!=null) neighbors.add(check); 
				//Bottom Right
				check=get_tile_at(new Coordinate(c.X()+1,c.Y()-1), false);
				if(check!=null) neighbors.add(check); 
				//Bottom
				check=get_tile_at(new Coordinate(c.X(),c.Y()-1), false);
				if(check!=null) neighbors.add(check);
				/* Generate */
				Tile generated = generate_tile(c,neighbors);
				/* Update DB */
				map.put(c,generated);
				DataBase.SetTile(generated);
				/* Return generated Tile */
				return generated;
			}
			else return null;
		}
		return map.get(c);//Correct floor?
	}
	
	/**
	 *  Simple wrapper for get_floor_at(Coordinate c, boolean player)
	 * @param x
	 * @param y
	 * @param player
	 * @return
	 */
	public Tile get_tile_at(long x, long y, boolean player)
	{
		return get_tile_at(new Coordinate(x,y),player);
	}
	
	private Tile generate_tile(Coordinate c,List<Tile> neighbors)
	{
		List<FloorType> ftl = new ArrayList<FloorType>();
		List<MapObjectType> mol = new ArrayList<MapObjectType>();
		Random rndgen = new Random();
		for(int i=0;i<neighbors.size();i++)
		{
			ftl.addAll(DataBase.get_possible_neighbors(neighbors.get(i)));
		}
		int index = rndgen.nextInt(ftl.size());
		Tile nt = new Tile(c);
		nt.setFloorType(ftl.get(index));
		mol = DataBase.get_possible_content(nt);
		index = rndgen.nextInt(mol.size());
		nt.setMapObjectType(mol.get(index));
		return nt;
	}
	
	/**
	 * Updates or adds Tile, non-generated.
	 * @param t
	 */
	public void update_tile(Tile t)
	{
		map.put(t.getCoordinate(), t);
		DataBase.SetTile(t); // Maybe ignore monsters and players?
		for(int i=0;i<players.size();i++)
		{
			if(players.get(i).see_Tile(t.getCoordinate())) 
				players.get(i).add_event_to_stack(new Update(UpdateType.TILE,t));
		}
	}
	
	/**
	 * 
	 * @param co
	 * @return
	 */
	public Resource get_resource(Coordinate co, int effi)
	{
		Tile req=map.get(co);
		if(req.getMapObjectType()!=null && req.getMapObjectType().canHarvest())
		{
			MapItem mi=(MapItem) thingsOnMap.get(co);
			if(mi==null)thingsOnMap.put(co,new MapItem(co,req.getMapObjectType()));
			else if(mi.Health()<2) 
				{
					thingsOnMap.remove(co);
					MapObjectType gotten=req.getMapObjectType();
					req.setMapObjectType(null);
					update_tile(req);
					return gotten.resource();
				}
			else mi.Damage(effi);
		}
		return null;
	}
	
	/**
	 * 
	 * @param co
	 * @return
	 */
	public Resource get_floor_resource(Coordinate co)
	{
		Tile req=map.get(co);
		if(req.getFloorType()!=null)
		{
			FloorType gotten=req.getFloorType();
			req.setFloorType(FloorType.DIRT);
			update_tile(req);
			return gotten.resource();
		}
		return null;
	}
	
	public void login(Player P)
	{
		//thingsOnMap.put(P.Coordinates(), P);
		Tile PT = get_tile_at(P.Coordinates(),!P.admin);
		PT.setMapObjectType(MapObjectType.PLAYER);
		update_tile(PT);
		players.add(P);
	}
	
	public void logout(Player P)
	{
		//thingsOnMap.remove(P.Coordinates());
		Tile PT = get_tile_at(P.Coordinates(),false);
		PT.setMapObjectType(null);
		players.remove(P);
		update_tile(PT);
	}
	
	public int addmonster()
	{
		Random rndgen = new Random();
		Coordinate co = null;
		int i,s=0;
		while(co == null && s < 500) //500 is arbitrary number of cycles to prevent infinite loop
		{
			s++;
			int rnd = rndgen.nextInt(map.size());
			Enumeration<Tile> e = map.elements();
			Tile t=new Tile(0,0);
			i=0;
			while(i<rnd) t=e.nextElement();
			if(t.canMoveOn()) co=t.getCoordinate();
		}
		int ID = thingsOnMap.size();
		Monster m = new Monster(ID,co);
		thingsOnMap.put(co, m);
		return ID;
	}
	
	public void monsterattack(Coordinate co,int amount)
	{
		for(int i=0;i<players.size();i++)
		{
			if(players.get(i).adjacent(co)) 
				players.get(i).Damage(amount);
		}
	}
}
