/**
 * 
 */
package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;

/**
 * @author Dennis
 *
 */
public class PlayerCyborg extends Cyborg {
	private int fadeColor = 10;
	private static PlayerCyborg instance;
	private GameWorld gw;
	/**
	 * 
	 */
	public PlayerCyborg() {
			super();	
			super.setSpeed(50);
			super.setInitialMaximumSpeed(200);
			super.setMaximumDamage(20);
			this.gw = GameWorld.getGameWorld();
	}
	public static PlayerCyborg getInstance() {
		if(instance == null) {
			instance = new PlayerCyborg();
		}
		return instance;
	}
	
	public void fadePlayerCyborgColor() {
		
		if (fadeColor < 250) {
			setColor(255 - fadeColor,0,0);
			fadeColor += 10;
		}
	}
	
	@Override
	public String toString() {
		
		return "PlayerCyborg " +  super.toString() ;
		
	}

	@Override
	public void handleCollision(ICollider otherObject) {
		if (otherObject instanceof Base) {
			Base b = (Base) otherObject;
			gw.baseCollide(b);
		}else if (otherObject instanceof Drone) {
			gw.droneCollide();
		}else if (otherObject instanceof NonPlayerCyborg) {
			gw.npcCollide();
		}else if (otherObject instanceof EnergyStation) {
			EnergyStation energyS = (EnergyStation) otherObject;
			gw.energyCollide(energyS);
		}
		
	}
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		int x = (int) (pCmpRelPrnt.getX() + super.getLocation().getX());
		int y = (int) (pCmpRelPrnt.getY() + super.getLocation().getY());
		int width = super.getSize();
		int height = super.getSize();
		g.setColor(getColor());		
		g.fillRect(x, y, width, height);
		
	}
	@Override
	public boolean collidesWith(ICollider otherObject) {
		// TODO Auto-generated method stub
		return false;
	}

}