package common;

public enum Resource {
	GRASS,
	DIRT,
	WATER,
	MUD,
	SAND,
	STONE,
	WOOD,
	LEATHER,
	STONE_BRICK,
	DOOR,
	BERRY,
	MEAT;
	
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
}
