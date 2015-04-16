/**
 * 
 */
package server;

import java.util.List;

/**
 *
 *
 */
public class WorldMap {
	private static WorldMap wm = new WorldMap();
	private List<Floor> map;
	private List<Coordinate> loaded;
	
	private WorldMap()
	{
		//TODO: Do we need a ctor?
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param player
	 * @return
	 */
	public static Floor get_floor_at(Coordinate c, boolean player)
	{
		int index=wm.loaded.indexOf(c);
		if(index==-1)
		{
			if(player)
			{
				/* GENERATE NEW MAP */
				/* return new floor */
			}
			else return null;
		}
		return (Floor)(wm.map.toArray()[index]);//Correct floor?
	}
	
	/**
	 *  Simple wrapper for get_floor_at(Coordinate c, boolean player)
	 * @param x
	 * @param y
	 * @param player
	 * @return
	 */
	public static Floor get_floor_at(long x, long y, boolean player)
	{
		return get_floor_at(new Coordinate(x,y),player);
	}
	
	
}
