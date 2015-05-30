package common;

import java.io.Serializable;

/**
 * A class to represent coordinates. 
 * Used by the WorldMap class.
 */
public class Coordinate implements Serializable{
	static final long serialVersionUID = 12301132;
	/*
	 * the Coordinates are in x,y format (horizontal then vertical)
	 */
	long x,y;
	
	/**
	 * Creates a coordinate 
	 * @param X axis, and
	 * @param Y axis
	 */
	public Coordinate()
	{
		x=0;
		y=0;
	}
	
	public Coordinate(long X, long Y)
	{
		x=X;
		y=Y;
	}
	
	public Coordinate(Coordinate other) {
		x=other.X();
		y=other.Y();
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
	
	public void setX(long x) { this.x = x;}
	public void setY(long y) { this.y = y;}
	@Override
	public boolean equals(Object o)
	{
		if(!(o instanceof Coordinate)) return false;
		if(((Coordinate)o).X()!=x) return false;
		if(((Coordinate)o).Y()!=y) return false;
		return true;
	}
		
	@Override
	public int hashCode()
	{
		return (int)(( y << 16 ) ^ x); //According to the internet, this is the best way to hash two numbers
	}
	
	public int distance(Coordinate other)
	{
		long d = Math.max(Math.abs(x-other.x),Math.abs(y-other.y));
		if(Integer.MAX_VALUE<d) return Integer.MAX_VALUE;
		return (int)d;
	}
}
