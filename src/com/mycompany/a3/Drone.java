package com.mycompany.a3;

import java.util.Random;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;

/**
 * @author Dennis
 * Drones act as 'enemies' to the Cyborg in the game
 * If a drone and cyborg intersect, it either causes damage to the cyborg or slows it down  
 */
public class Drone extends Moveable{
	
	public Drone() {
		Random randomNumber = new Random();
		
		// sets a random location,heading,speed, and size for the drone to enter the game world
		setLocation(randomNumber.nextFloat() * (1000 - 1) + 1 , randomNumber.nextFloat() * (1000 - 1) + 1 );
		setHeading(randomNumber.nextInt(359));
		setSpeed(randomNumber.nextInt(100)+200);
		super.setSize(randomNumber.nextInt(110) + 65);
		
		// Drones are set to color blue and cannot be changed
		super.setColor(0,0,255);
	}
	
	// Doesnt allow the programmer to change the color after it is created
	public void setColor(int r, int b, int g) {
		
	}
	
	// cannot change size after it is created
	public void setSize(int size) {
		
	}
	
	/**
	 * To allow the drone to randomly head left or right after every move()
	 *  We must change the heading before move gets called
	 *  Thus, I overrode this method from Moveable
	 */
	public void setHeading() {
		Random randomNumber = new Random();
		int random = randomNumber.nextInt(2);
		if(random == 0) {
			setHeading(getHeading() + 5);
		}else {
			setHeading(getHeading() - 5);
		}		
	}
	
	/**
	 * As described above, we call setHeading before moving to allow for the left or right heading randomness of the drone
	 */
	public void move(int gwWidth, int gwHeight, int rate) {
		this.setHeading();
		super.move(gwWidth, gwHeight, rate);
	}
	
	@Override
	public String toString() {
		
		return "Drone: " + super.toString() + " heading = " + getHeading() + " speed = " + getSpeed();
		
	}

	
	@Override
	public boolean collidesWith(ICollider otherObject) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void handleCollision(ICollider otherObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		// calculate upperPoint
		float x = pCmpRelPrnt.getX() + super.getLocation().getX();
		float y = pCmpRelPrnt.getY() + super.getLocation().getY() + (super.getSize() / 2);
		Point upperPoint = new Point(x, y);

		// calculate leftLowerPoint
		x = (pCmpRelPrnt.getX() + super.getLocation().getX() - (super.getSize() / 2));
		y = (pCmpRelPrnt.getY() + super.getLocation().getY() - (super.getSize() / 2));
		Point leftLowerPoint = new Point(x, y);

		// calculate rightLowerPoint
		x = (pCmpRelPrnt.getX() + super.getLocation().getX() + (super.getSize() / 2));
		y = (pCmpRelPrnt.getY() + super.getLocation().getY() - (super.getSize() / 2));
		Point rightLowerPoint = new Point(x, y);

		// put x and y points in the array
		int xPoints[] = new int[] { (int) upperPoint.getX(), (int) leftLowerPoint.getX(),
				(int) rightLowerPoint.getX() }; // array of integers that has x coordinates of the corners
		int yPoints[] = new int[] { (int) upperPoint.getY(), (int) leftLowerPoint.getY(),
				(int) rightLowerPoint.getY() }; // array of integers that has y coordinates of the corners
		int nPoints = 3; // number of corners of the polygon
		g.setColor(getColor());
		g.drawPolygon(xPoints, yPoints, nPoints);
		
	}
	public boolean isSelected() {
		return false;
	}

}
