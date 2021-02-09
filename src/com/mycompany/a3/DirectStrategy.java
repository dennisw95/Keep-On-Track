package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.util.MathUtil;

/*
 * Implements the direct strategy where NPC goes for the next base in order to complete the race
 */


public class DirectStrategy implements IStrategy{

	public void apply(NonPlayerCyborg nPC, GameWorld gw) {
		int newBase = nPC.getLastBaseReached() + 1;
		Point newBaseLocation = new Point();
		IIterator it = gw.getCollection().getIterator();
		while (it.hasNext()) {
			GameObject ob = it.getNext();
			if (ob instanceof Base) {
				Base b = (Base) ob;
				if (b.getSequenceNumber() == newBase) {
					newBaseLocation = b.getLocation();
				}
			}
		}
		
		Point difference = new Point(); 
		difference.setX(newBaseLocation.getX() - nPC.getLocation().getX());
		difference.setY(newBaseLocation.getY() - nPC.getLocation().getY());
		int angle = (int) MathUtil.atan(difference.getY() / difference.getX());
		nPC.setHeading(angle);
	}

	

}
