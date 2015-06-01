package common;

public enum Resource {
	GRASS(0),
	DIRT(1),
	WATER(2),
	MUD(3),
	SAND(4),
	STONE(5),
	WOOD(6),
	LEATHER(7),
	STONE_BRICK(8),
	DOOR(9),
	BERRY(10),
	MEAT(11);
	
	private final int ID;
	
	private Resource(int ID)
	{
		this.ID = ID;
	}
	
	public int getID()
	{
		return ID;
	}
	
	public MapObjectType place_object()
	{
		switch(this){
			case STONE:
				return MapObjectType.ROCK;
			case WOOD:
				return MapObjectType.WALL_WOOD;
			case STONE_BRICK:
				return MapObjectType.WALL_STONE;
			default:
				return null;
		}
	}
	
	public FloorType place_floor()
	{
		switch(this){
			case GRASS:
				return FloorType.GRASS;
			case DIRT:
				return FloorType.DIRT;
			case WATER:
				return FloorType.WATER;
			case MUD:
				return FloorType.MUD;
			case SAND:
				return FloorType.SAND;
			case STONE:
				return FloorType.STONE;
			case WOOD:
				return FloorType.WOOD;
			case STONE_BRICK:
				return FloorType.STONE_BRICK;
			case DOOR:
				return FloorType.DOOR;
			default:
				return null;
		}
	}
	static final long serialVersionUID = 52013438;
}
