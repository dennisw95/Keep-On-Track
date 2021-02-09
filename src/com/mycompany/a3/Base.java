package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

/**
 * @author Dennis
 * This class 'Base' are considered waypoints for the cyborg to reach and progress through the game
 */
public class Base extends Fixed {
	
	// Since bases are considered waypoints, the player must reach them in sequential order
	private int sequenceNumber;
	
	/**
	 * Constructor
	 * Color sets to black, and cannot be changed
	 * base size 10
	 * 
	 */
	public Base(float x, float y) {
		super.setLocation(x, y);
		super.setColor(0, 0, 255);
		super.setSize(150);
		
	}
	
	/**
	 * @return the sequenceNumber
	 */
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	
	/**
	 * @param sequenceNumber the sequenceNumber to set
	 */
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	
	// Doesnt allow the programmer to change the color after it is created
	public void setColor(int r, int g, int b) {
		
	}

	
	@Override
	public String toString() {
		
		return "Base: " + super.toString() + " seqNum = " + getSequenceNumber();
		
	}

	@Override
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		System.out.println("Base! This is my pointer location: " + pPtrRelPrnt.getX() + " " + pPtrRelPrnt.getY());
		
		// get pointer location
		int px = (int) pPtrRelPrnt.getX();
		int py = (int) pPtrRelPrnt.getY();

    	// get location of object

		int xLoc = (int) (pCmpRelPrnt.getX() + getLocation().getX());
		int yLoc = (int) (pCmpRelPrnt.getY() + getLocation().getY());

		// check to see if the pointer is in the object
		if ((px >= xLoc) && (px <= xLoc + this.getSize()) && (py >= yLoc) && (py <= yLoc + this.getSize())) {
			return true;
		}
		return false;
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
				// System.out.println("upperPoint.x: " + upperPoint.getX());
				// System.out.println("upperPoint.y: " + upperPoint.getY());

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
				if (isSelected()) {
					g.drawPolygon(xPoints, yPoints, nPoints);
				} else {
					g.fillPolygon(xPoints, yPoints, nPoints);
				}
				g.setColor(ColorUtil.BLACK);
				Point textLocation = new Point(
						(int) (this.getLocation().getX() + pCmpRelPrnt.getX() - (g.getFont().getPixelSize() / 2)),
						(int) (this.getLocation().getY() + pCmpRelPrnt.getY() - g.getFont().getPixelSize() / 2));
				g.drawString(Integer.toString(this.sequenceNumber), (int) textLocation.getX() + 13,
						(int) textLocation.getY() + 17);
		
	}
	
	

	
}
