package common;

/**
*
*
*/
public enum FloorType {
	GRASS(1),
	DIRT(2),
	WATER(3),
	MUD(4),
	SAND(5),
	STONE(6),
	WOOD(7),
	STONE_BRICK(8),
	DOOR(9);
	
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


