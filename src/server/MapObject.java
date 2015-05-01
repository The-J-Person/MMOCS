/**
 * 
 */
package server;

import common.*;


public interface MapObject {
	Coordinate Coordinates();
	int Resource();
	int Health();
	int Damage();
}
