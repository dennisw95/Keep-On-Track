/**
 * 
 */
package com.mycompany.a3;

import com.codename1.charts.models.Point;

/**
 * @author Dennis
 *
 */
public abstract class Moveable extends GameObject{

	private int heading; 
	private int speed;
	
	/**
	 * 
	 */
	public Moveable() {
	}
	/**
	 * 
	 */

	
	public void move(int gwWidth, int gwHeight, int rate) {
		//axes is tilted since 90 degree is east on screen which is horizontal not vertical 
		float dist = (float) (getSpeed() * (rate/1000.0));
		float xCoordinate = (float) (super.getLocation().getX()) +(dist * (float) Math.cos(Math.toRadians((double) 90.0 - getHeading())));
		float yCoordinate = (float) (super.getLocation().getY()) +(dist * (float) Math.sin(Math.toRadians((double) 90.0 - getHeading())));
		
		if ((xCoordinate + this.getSize()/2) > gwWidth) {
			xCoordinate = gwWidth - this.getSize()/2;
		}else if ((xCoordinate - this.getSize()/2)  < 0) {
			xCoordinate = this.getSize()/2;
		}
		
		if ((yCoordinate + this.getSize()/2)  > gwHeight) {
			yCoordinate = gwHeight - this.getSize();
		}else if ((yCoordinate - this.getSize()/2) < 0) {
			yCoordinate = this.getSize()/2;
		}	
		
		Point newLocation = new Point(xCoordinate, yCoordinate);
		this.setLocation(newLocation.getX(), newLocation.getY());

	}


	/**
	 * @return the speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getHeading() {
		return heading;
	}
	public void setHeading(int heading) {
		this.heading = heading;
	}

}
