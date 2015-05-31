/**
 * 
 */
package server;

import common.*;

/**
 *
 *
 */
public class Floor implements MapObject {
	
	Coordinate C;
	
	@Override
	public Coordinate Coordinates() {
		// TODO Auto-generated method stub
		return C;
	}

	@Override
	public common.Resource Resource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int Health() {
		return 0;
	}

	@Override
	public void Damage(int amount) {
		//Do nothing
	}

}
