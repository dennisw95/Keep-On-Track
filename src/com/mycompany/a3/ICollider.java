/**
 * 
 */
package com.mycompany.a3;

/**
 * @author Dennis
 *
 */
public interface ICollider {
	boolean collidesWith(ICollider otherObject);
	void handleCollision(ICollider otherObject);
}
