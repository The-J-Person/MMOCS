/**
 * 
 */
package server;

import common.*;
import java.util.*;

/**
 *
 *
 */
public class Player implements MapObject {
	
	public static final int distance=5;
	Coordinate C;
	List<Resource> Inventory = new ArrayList<Resource>();
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
	
	public void setHealth(int nHealth)
	{
		Health=nHealth;
	}
	
	public int view_distance()
	{
		return distance;
	}

	@Override
	public int Damage() {
		// TODO Auto-generated method stub
		return 1;
	}

}
