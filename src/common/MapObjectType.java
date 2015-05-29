package common;

/**
 * 
 * References non-floor objects
 *
 */
public enum MapObjectType {
	PLAYER, //Identification of player's own self and other players.
	PILE, //Group of objects occupying same tile
	MONSTER,
	TREE,
	BUSH,
	ROCK,
	WALL_WOOD,
	WALL_STONE;
	
	static final long serialVersionUID = 8975595;
	
	public boolean canMoveOn(){
		return this == PILE;
	}
	public boolean canPickUp(){
		return this == PILE;
	}
	public boolean canAttack(){
		return this == MONSTER;
	}
	public boolean canHarvest(){
		return (this == TREE || this == BUSH || this == ROCK);
	}
	
}
