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
	int Health;
	
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
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see server.MapObject#Health()
	 */
	@Override
	public int Health() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int Damage() {
		// TODO Auto-generated method stub
		return 0;
	}

}
