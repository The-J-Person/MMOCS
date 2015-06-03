package common;

import java.io.Serializable;

/**
 * Represents single tile within map.
 *
 */
public class Tile implements Serializable {
	static final long serialVersionUID = 328328312;
	FloorType f;
	MapObjectType mo;
	Coordinate c;
	
	public Tile(){
		f = null;
		mo = null;
		c = new Coordinate(0,0);
	}

	public Tile(long x, long y){
		f = null;
		mo = null;
		c = new Coordinate(x,y);
	}
	
	public Tile(Coordinate co){
		f = null;
		mo = null;
		c = co;
	}
	
	public Tile(long x, long y, FloorType ft, MapObjectType mot){
		f = ft;
		mo = mot;
		c = new Coordinate(x,y);
	}
	
	public Tile(Coordinate co, FloorType ft, MapObjectType mot){
		f = ft;
		mo = mot;
		c = new Coordinate(co);
	}
	
	public Tile(Tile other){
		f=other.f;
		mo=other.mo;
		c = new Coordinate(other.c);
	}
	
	public void setFloorType(FloorType ft)
	{
		f=ft;
	}
	
	public void setMapObjectType(MapObjectType mot)
	{
		mo=mot;
	}
	
	public MapObjectType getMapObjectType()
	{
		return mo;
	}
	
	public FloorType getFloorType()
	{
		return f;
	}
	
	public Coordinate getCoordinate()
	{
		return c;
	}
	
	public boolean equals(Object o)
	{
		if(!(o instanceof Tile)) return false;
		if(((Tile)o).c.X()!=c.X()) return false;
		if(((Tile)o).c.Y()!=c.Y()) return false;
		return true;
	}
	
	public boolean canMoveOn(){
		return f != null && f.canMoveOn();
	}
	
	public boolean canHarvest(){ 
		return (mo != null && mo.canHarvest());
	}
	
	public boolean canAttack(){
		return (mo != null && mo.canAttack());
	}
}