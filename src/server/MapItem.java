/**
 * 
 */
package server;

import common.*;

/**
 *
 *
 */
public class MapItem implements MapObject {
	
	Coordinate C;
	MapObjectType type;
	int Health;
	
	MapItem(Coordinate co, MapObjectType t)
	{
		C=co;
		type=t;
		Health=2*t.ordinal();
	}
	
	/* (non-Javadoc)
	 * @see server.MapObject#Coordinates()
	 */
	@Override
	public Coordinate Coordinates() {
		// TODO Auto-generated method stub
		return C;
	}

	/* (non-Javadoc)
	 * @see server.MapObject#Resource()
	 */
	@Override
	public common.Resource Resource() {
		return type.resource();
	}

	/* (non-Javadoc)
	 * @see server.MapObject#Health()
	 */
	@Override
	public int Health() {
		return Health;
	}

	@Override
	public void Damage(int amount) {
		Health=Health-amount;
	}

}
