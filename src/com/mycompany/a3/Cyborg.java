package com.mycompany.a3;

/**
 * @author Dennis
 * 
 */
public abstract class Cyborg extends Moveable implements ISteerable {

	private int steeringDirection;
	private int initialMaximumSpeed; // Can reach max speed if damage level is 0
	private int energyLevel; // Current energy, if 0 cyborg speed will also be 0 and cannot move
	private int energyConsumptionRate; // How much energy spent per tick
	private int damageLevel;
	private int lastBaseReached;
	private int maximumDamage;
	private int currentMaximumSpeed;
	/**
	 * Energy Level is set to 50, because 50 is the max a Energy Station can have.
	 * 
	 */
	public Cyborg() {
		setSteeringDirection(0);
		setInitialMaximumSpeed(10);
		setEnergyLevel(500);
		setEnergyConsumptionRate(2);
		setDamageLevel(0);
		setMaximumDamage(10);
		setLastBaseReached(1);
		super.setHeading(0);
		super.setSize(130);
		super.setSpeed(5);
		
		// Cyborgs color is red
		setColor(255,0,0);		
	}
	
	
	/**
	 * Puts boundaries on steering where -40 < steeringDirection < 40
	 * 
	 * Makes sure you are always heading within 0 to 360 degrees
	 * 
	 * Resets steering at the very end
	 */
	public void setHeading(int steeringDirection) {
		if(steeringDirection < -40) {
			System.out.println("You cannot turn " + steeringDirection + " degrees. That is past allow limit of -40");
			steeringDirection = -40;
			System.out.println("Set to max steering allowed: " + steeringDirection);
		}else if ( steeringDirection > 40) {
			System.out.println("You cannot turn " + steeringDirection + " degrees. That is past allow limit of 40");
			steeringDirection = 40;
			System.out.println("Set to max steering allowed: " + steeringDirection);
		}
		// if you are trying to turn below 0 degrees or above 360 degrees, this makes sure you stay in those bounds.
		int desiredHeading = getHeading() + steeringDirection;
		if (desiredHeading < 0) {
			super.setHeading(360 + desiredHeading);
		}else if (desiredHeading > 360) {
			super.setHeading(desiredHeading - 360);
		}else {
			super.setHeading(desiredHeading);
		}
		// resets steering direction since this method is only invoked for every tick the user issues
		setSteeringDirection(0);
		
	}
	/**
	 * @return the maximumSpeed
	 */
	public int getCurrentMaximumSpeed() {
		return currentMaximumSpeed;
	}
	/**
	 * @param maximumSpeed the maximumSpeed to set
	 */
	public void setInitialMaximumSpeed(int maximumSpeed) {
		this.initialMaximumSpeed = maximumSpeed;
	}
	/**
	 * @return the energyLevel
	 */
	public int getEnergyLevel() {
		return energyLevel;
	}
	/**
	 * @param energyLevel the energyLevel to set
	 */
	public void setEnergyLevel(int energyLevel) {
		this.energyLevel = energyLevel;
	}
	/**
	 * @return the energyConsumptionRate
	 */
	public int getEnergyConsumptionRate() {
		return energyConsumptionRate;
	}
	/**
	 * @param energyConsumptionRate the energyConsumptionRate to set
	 */
	public void setEnergyConsumptionRate(int energyConsumptionRate) {
		this.energyConsumptionRate = energyConsumptionRate;
	}
	/**
	 * @return the damageLevel
	 */
	public int getDamageLevel() {
		return damageLevel;
	}
	/**
	 * @param damageLevel the damageLevel to set
	 */
	public void setDamageLevel(int damageLevel) {
		this.damageLevel = damageLevel;
	}
	/**
	 * @return the lastBaseReached
	 */
	public int getLastBaseReached() {
		return lastBaseReached;
	}
	/**
	 * @param lastBaseReached the lastBaseReached to set
	 */
	public void setLastBaseReached(int lastBaseReached) {
		this.lastBaseReached = lastBaseReached;
	}
	/**
	 * @param steeringDirection the steeringDirection to set
	 */
	public void setSteeringDirection(int steeringDirection) {
		this.steeringDirection = steeringDirection;
	}
	/**
	 * @return the steeringDirection
	 */
	public int getSteeringDirection() {
		return steeringDirection;
	}

	/**
	 * User can turn as much as they want, but once tick() is called, we make sure its not out of bounds, which is handled by setHeading
	 */
	@Override
	public void turn(char lr){
		if (lr == 'l'  ) {
			//changing steering direction of cyborg by 5 to the left
			setSteeringDirection(steeringDirection - 5);
			System.out.println("Steering direction changed to the left by 5");
		}else if (lr == 'r' ) {
			//changing steering direction of cyborg by 5 to the right
			setSteeringDirection(steeringDirection + 5);
			System.out.println("Steering direction changed to the right by 5");
		}else
			System.out.println("Failed steering direction change");
	}
	
	public int getMaximumDamage() {
		return maximumDamage;
	}



	/**
	 * Heading needs to be updated before the cyborg can move to allow the users turn to update
	 */
	public void move(int gwWidth, int gwHeight, int rate) {
		this.setHeading(this.getSteeringDirection());
		super.move(gwWidth, gwHeight, rate);
		//super.move(gwWidth, gwHeight);
	}

	public void setMaximumDamage(int maximumDamage) {
		this.maximumDamage = maximumDamage;
	}

	// cannot change size after it is created
	public void setSize(int size) {
		
	}
	
	// overriding to take into account speed restrictions
	public void setSpeed(int speed) {
		double numerator = this.maximumDamage - this.damageLevel;
		double dmgPercent = numerator / this.maximumDamage;
		int newMaxSpeed = (int) (getInitialMaximumSpeed() * dmgPercent) ;		
		setCurrentMaximumSpeed(newMaxSpeed);		
		
		if(speed <= this.currentMaximumSpeed && speed > 0) {
			super.setSpeed(speed  );
		}else if (speed > this.currentMaximumSpeed) {
			super.setSpeed(this.currentMaximumSpeed);
		}else if (speed <= 0 ) {
			System.out.println("Speed at 0, cannot brake anymore");
			super.setSpeed(0);
		}
			
	}

	public void setCurrentMaximumSpeed(int currentMaximumSpeed) {
		this.currentMaximumSpeed = currentMaximumSpeed;
	}


	public int getInitialMaximumSpeed() {
		return this.initialMaximumSpeed;
	}


	@Override
	public String toString() {
		
		return super.toString() + " heading = " + getHeading() + " speed = " + getSpeed() + " maxSpeed = " + 
		getCurrentMaximumSpeed() + " steeringDirection = " + getSteeringDirection() + " energyLevel = " + getEnergyLevel() + " damageLevel = "
		+ getDamageLevel();
		
	}

	
	
}
