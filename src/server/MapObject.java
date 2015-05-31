/**
 * 
 */
package server;

import common.*;


public interface MapObject {
	Coordinate Coordinates();
	common.Resource Resource();
	int Health();
	void Damage(int amount);
}
