package common;

/**
*
*
*/
public enum FloorType {
	GRASS(0),
	DIRT(1),
	WATER(2),
	MUD(3),
	SAND(4),
	STONE(5),
	WOOD(6),
	STONE_BRICK(7),
	DOOR(8);
	
	static final long serialVersionUID = 3222322;
	private final int ID;
	
	private FloorType(int ID)
	{
		this.ID = ID;
	}
	
	public int getID()
	{
		return ID;
	}
	
	public boolean canMoveOn(){
		return this != WATER;
	}
}


