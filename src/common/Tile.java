/**
 * 
 */
package common;

/**
 * Represents single tile within map.
 *
 */
public class Tile {
	FloorType f;
	MapObjectType mo;
	Coordinate c;
	
	Tile(){
		f = null;
		mo = null;
		c = new Coordinate(0,0);
	}

	Tile(long x, long y){
		f = null;
		mo = null;
		c = new Coordinate(x,y);
	}
	
	Tile(long x, long y, FloorType ft, MapObjectType mot){
		f = ft;
		mo = mot;
		c = new Coordinate(x,y);
	}
	
	void setFloorType(FloorType ft)
	{
		f=ft;
	}
	
	void setMapObjectType(MapObjectType mot)
	{
		mo=mot;
	}
	
	MapObjectType getMapObjectType()
	{
		return mo;
	}
	
	FloorType getFloorType()
	{
		return f;
	}
}
