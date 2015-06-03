package common;

/**
*
*
*/
public enum FloorType {
	NONE(404),
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
	
	public Resource resource()
	{
		switch(this)
		{
		case GRASS:
			return Resource.GRASS;
		case DIRT:
			return Resource.DIRT;
		case WATER:
			return Resource.WATER;
		case MUD:
			return Resource.MUD;
		case SAND:
			return Resource.SAND;
		case STONE:
			return Resource.STONE;
		case WOOD:
			return Resource.WOOD;
		case STONE_BRICK:
			return Resource.STONE_BRICK;
		case DOOR:
			return Resource.DOOR;
		default:
			return null;
		}
	}
}


