package common;

/**
 * 
 * References non-floor objects
 *
 */
public enum MapObjectType {
	PLAYER(0), //Identification of player's own self and other players.
	MONSTER(1),
	BUSH(2),
	TREE(3),
	ROCK(4),
	WALL_WOOD(5),
	WALL_STONE(6);
	
	static final long serialVersionUID = 8975595;
	private final int ID;
	
	private MapObjectType(int ID)
	{
		this.ID = ID;
	}
	
	public int getID()
	{
		return ID;
	}
	public boolean canAttack(){
		return this == MONSTER;
	}
	public boolean canHarvest(){
		//return (this == TREE || this == BUSH || this == ROCK);
		return (this != PLAYER && this != MONSTER);
	}
	
	public Resource resource()
	{
		switch(this)
		{
		case TREE:
			return Resource.WOOD;
		case BUSH:
			return Resource.BERRY;
		case ROCK:
			return Resource.STONE;
		case WALL_WOOD:
			return Resource.WOOD;
		case WALL_STONE:
			return Resource.STONE_BRICK;
		default:
			return null;
		}
	}
}
