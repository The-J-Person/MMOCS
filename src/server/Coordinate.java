/**
 * 
 */
package server;

/**
 *
 *
 */
public class Coordinate {
	long x,y;
	
	/**
	 * Creates a coordinate 
	 * @param X axis, and
	 * @param Y axis
	 */
	public Coordinate(long X, long Y)
	{
		x=X;
		y=Y;
	}
	
	/**
	 * Sets a coordinate 
	 * @param X axis, and
	 * @param Y axis
	 */
	public void Set(long X, long Y)
	{
		x=X;
		y=Y;
	}
	
	/**
	 * 
	 * @return the X-axis coordinate
	 */
	public long X()
	{
		return x;
	}
	
	/**
	 * 
	 * @return the Y-axis coordinate
	 */
	public long Y()
	{
		return y;
	}
		
}
