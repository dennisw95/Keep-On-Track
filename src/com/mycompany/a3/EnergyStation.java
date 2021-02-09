package com.mycompany.a3;

import java.util.Random;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

/**
 * @author Dennis
 * Energy stations allow the Cyborg consume their stored energy as a resource to keep the Cyborg moving
 */
public class EnergyStation extends Fixed {

	private int capacity; 
	
	public EnergyStation() {
		Random randomNumber = new Random();
		// set Random location
	   super.setLocation(randomNumber.nextFloat() * (1000 - 1) + 1 , randomNumber.nextFloat() * (1000 - 1) + 1 );
		
		// Set random size between 10 and 50 
		super.setSize(randomNumber.nextInt(110) +100);
		
		// The capacity of an energy station is equal to its size
		setCapacity(getSize());
		
		// EnergyStations color Green
		setColor(0,255,0);
		
		
	}
	/**
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}
	/**
	 * @param capacity the capacity to set
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
		
	// cannot change size after it is created
	public void setSize(int size) {
		
	}
	
	@Override
	public String toString() {
		
		return "EnergyStation: " + super.toString() + " capacity = " + getCapacity();
		
	}
	@Override
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		System.out.println(
				"Energy Station! This is my pointer location: " + pPtrRelPrnt.getX() + " " + pPtrRelPrnt.getY());
		int px = (int) pPtrRelPrnt.getX();
		int py = (int) pPtrRelPrnt.getY();

		//get location of object
		
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
		int x = (int) (pCmpRelPrnt.getX() + super.getLocation().getX());
		int y = (int) (pCmpRelPrnt.getY() + super.getLocation().getY());
		int r = super.getSize() / 2; // size indicates diameter, so divide it by 2 to get the radius
		g.setColor(getColor());
		if (isSelected()) {
			g.drawArc(x, y, 2 * r, 2 * r, 0, 360);
		} else {
			g.fillArc(x, y, 2 * r, 2 * r, 0, 360);
		}
		g.setColor(ColorUtil.BLACK);
		Point textLocation = new Point(
				(int) (this.getLocation().getX() + pCmpRelPrnt.getX() - (g.getFont().getPixelSize() / 2)),
				(int) (this.getLocation().getY() + pCmpRelPrnt.getY() - g.getFont().getPixelSize() / 2));
		g.drawString(Integer.toString(this.capacity), (int) textLocation.getX(), (int) textLocation.getY() - 15);
		
	}

}
