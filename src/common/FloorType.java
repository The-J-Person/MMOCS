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
	STONE_BRICK;
	
	static final long serialVersionUID = 3222322;
	
	public boolean canMoveOn(){
		return this != WATER;
	}
}


