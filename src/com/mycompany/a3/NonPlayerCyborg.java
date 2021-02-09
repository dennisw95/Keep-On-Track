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
public class NonPlayerCyborg extends Cyborg {
	private GameWorld gw;
	private IStrategy currStrategy;
	/**
	 * 
	 */
	public NonPlayerCyborg(IStrategy strategy) {
		super();
		super.setMaximumDamage(100);
		super.setEnergyLevel(10000);
		super.setEnergyConsumptionRate(5);
		super.setSpeed(150);
		super.setInitialMaximumSpeed(1000);
		this.currStrategy = strategy;
		this.gw = GameWorld.getGameWorld();
	}
	
	public void switchStrategy(IStrategy strategy) {
		currStrategy = strategy;
	}

	public IStrategy getCurrStrategy() {
		return this.currStrategy;
	}
	
	
	
	public void move(int gwWidth, int gwHeight, GameWorld gw, int rate) {
		currStrategy.apply(this, gw);
		super.move(gwWidth, gwHeight, rate);
	}
	
	public String toString() {
		if(currStrategy instanceof DirectStrategy) {
			return "Non-Player Cyborg " + super.toString() + " Strategy: " + "Direct " + "Last Base Reached: " + this.getLastBaseReached();
		}else
			return "Non-Player Cyborg " + super.toString() + " Strategy: " + "Attack " + "Last Base Reached: " + this.getLastBaseReached();
		
	}



	@Override
	public void handleCollision(ICollider otherObject) {
		if (otherObject instanceof Base) {
			Base b = (Base) otherObject;
			gw.baseCollide(b);
		}else if (otherObject instanceof Drone) {
			gw.droneCollide();
		}else if (otherObject instanceof NonPlayerCyborg) {
			
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
		g.drawRect(x, y, width, height);
		
	}

	@Override
	public boolean collidesWith(ICollider otherObject) {
		// TODO Auto-generated method stub
		return false;
	}

}
