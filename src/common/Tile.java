package common;

/**
 * Represents single tile within map.
 *
 */
public class Tile {
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
		return (f.canMoveOn() && mo.canMoveOn());
	}
	
	public boolean canPickUp(){
		return mo.canPickUp();
	}
	
	public boolean canHarvest(){ 
		return mo.canHarvest();
	}
	
	public boolean canAttack(){
		return mo.canAttack();
	}
}