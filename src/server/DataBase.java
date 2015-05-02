/**
 * 
 */
package server;

import java.util.*;

import common.*;

/**
 * 
 *
 */
public class DataBase {
	
	public static List<FloorType> get_possible_neighbors(Tile t)
	{
		//TODO Stub for map generation
		List<FloorType> lst = new ArrayList<FloorType>();
		lst.add(FloorType.GRASS);
		lst.add(FloorType.DIRT);
		lst.add(FloorType.STONE);
		return lst;
	}
	
	public static List<MapObjectType> get_possible_content(Tile t)
	{
		//TODO Stub for map generation
		List<MapObjectType> lst = new ArrayList<MapObjectType>();
		lst.add(MapObjectType.BUSH);
		lst.add(MapObjectType.TREE);
		lst.add(MapObjectType.ROCK);
		lst.add(null);
		return lst;
	}
}
