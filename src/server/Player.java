/**
 * 
 */
package server;

import common.*;

/**
 *
 *
 */
public class Player implements MapObject {
	
	public static final int distance=5;
	Coordinate C;
	int Health;
	int ID;
	
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
	public int Resource() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see server.MapObject#Health()
	 */
	@Override
	public int Health() {
		return Health;
	}

	@Override
	public int Damage() {
		// TODO Auto-generated method stub
		return 1;
	}

}
