/**
 * 
 */
package common;

/**
 * A class to represent coordinates. 
 * Used by the WorldMap class.
 */
public class Coordinate {
	/*
	 * the Coordinates are in x,y format (horizontal then vertical)
	 */
	long x,y;
	
	public String toString()
	{
		return Long.toString(x) + " " + Long.toString(y);
	}
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
	public long X() { return x; }
	
	/**
	 * 
	 * @return the Y-axis coordinate
	 */
	public long Y() { return y; }
	
	@Override
	public boolean equals(Object o)
	{
		if(!(o instanceof Coordinate)) return false;
		if(((Coordinate)o).X()!=x) return false;
		if(((Coordinate)o).Y()!=y) return false;
		return true;
	}
		
}
