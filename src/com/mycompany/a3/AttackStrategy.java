package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.util.MathUtil;

/*
 * implements the strategy where the NPC looks for the player cyborg, and heads towards it.
 */

public class AttackStrategy implements IStrategy{



	@Override
	public void apply(NonPlayerCyborg nPC, GameWorld gw) {
		Point newCyborgLocation = new Point();
		PlayerCyborg pCyborg = PlayerCyborg.getInstance();
		newCyborgLocation = pCyborg.getLocation();
		Point difference = new Point(); 
		difference.setX(newCyborgLocation.getX() - nPC.getLocation().getX());
		difference.setY(newCyborgLocation.getY() - nPC.getLocation().getY());
		int angle = (int) MathUtil.atan(difference.getY() / difference.getX());
		nPC.setHeading(angle);
		
	}

		
	
}
