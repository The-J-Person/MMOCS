package common;

/**
*
*
*/
public enum FloorType {
	GRASS,
	DIRT,
	WATER,
	MUD,
	SAND,
	STONE,
	WOOD,
	STONE_BRICK,
	DOOR;
	
	public boolean canMoveOn(){
		return this != WATER;
	}
}


