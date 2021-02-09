/**
 * 
 */
package com.mycompany.a3;
import java.util.Collection;
import java.util.HashMap;
import java.util.Observable;
import java.util.Random;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.TextField;



/**
 * @author Dennis
 *
 */
public class GameWorld extends Observable {


	private boolean sound;
	private int playerLives ;
	private int clockTickCount;
	private GameObjectCollection gameObjectsCollection;
	private int width;
	private int height;
	private boolean position;
	PlayerCyborg playerCyborg;
	private static GameWorld gw;
	private ICollider lastCollideObject;
	
	private Sound npcCollisionSound;
	private Sound energyCollisionSound;
	private Sound droneCollisionSound;
	
	public GameWorld() {
		this.playerLives = 3;
		this.clockTickCount = 0;
		this.sound = false;		
		this.position = false;
	}

	/**
	 * @return the position
	 */
	public boolean getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(boolean position) {
		this.position = position;
	}

	public void init() {
		
		gameObjectsCollection = new GameObjectCollection();
		
		Base baseOne = new Base(200,200);
		baseOne.setSequenceNumber(1);
		Base baseTwo = new Base(500,300);
		baseTwo.setSequenceNumber(2);
		Base baseThree = new Base(700,600);
		baseThree.setSequenceNumber(3);
		Base baseFour = new Base(900,400);
		baseFour.setSequenceNumber(4);
		
		lastCollideObject = baseOne;
		
		playerCyborg = PlayerCyborg.getInstance();
		playerCyborg.setLocation(baseOne.getLocation().getX(), baseOne.getLocation().getY());
		
		NonPlayerCyborg npcOne = new NonPlayerCyborg(new AttackStrategy());
		npcOne.setLocation(baseOne.getLocation().getX() + 200, baseOne.getLocation().getY());
		NonPlayerCyborg npcTwo = new NonPlayerCyborg(new DirectStrategy());
		npcTwo.setLocation(baseOne.getLocation().getX()+100, baseOne.getLocation().getY()+200);
		NonPlayerCyborg npcThree = new NonPlayerCyborg(new AttackStrategy());
		npcThree.setLocation(baseOne.getLocation().getX()+300, baseOne.getLocation().getY()-300);

		
		
		Drone droneOne = new Drone();
		Drone droneTwo = new Drone();
		
		EnergyStation stationOne = new EnergyStation();
		EnergyStation stationTwo = new EnergyStation();
		

		
		//adding to collection
		gameObjectsCollection.add(stationOne);
		gameObjectsCollection.add(stationTwo);
		gameObjectsCollection.add(baseOne);
		gameObjectsCollection.add(baseTwo);
		gameObjectsCollection.add(baseThree);
		gameObjectsCollection.add(baseFour);		
		gameObjectsCollection.add(droneOne);
		gameObjectsCollection.add(droneTwo);
		gameObjectsCollection.add(npcOne);
		gameObjectsCollection.add(npcTwo);
		gameObjectsCollection.add(npcThree);		
		gameObjectsCollection.add(playerCyborg);
				
		this.updateObservers();
		
	}
	
	public void exit() {
		Display.getInstance().exitApplication();
	}
	
	/**
	 * Increments cyborg speed by 1
	 * Speed restrictions are implmented in setSpeed in Cyborg class
	 */
	public void accelerate() {
		PlayerCyborg tempCyborg = findPlayerCyborg();
		int currentSpeed = tempCyborg.getSpeed();
		// increase speed by 1
		tempCyborg.setSpeed(currentSpeed + 1);
		System.out.println("Speed set to " + tempCyborg.getSpeed());	
		this.updateObservers();
	}
	
	/**
	 * Decrements cyborg speed by 1
	 * Speed restrictions are implmented in setSpeed in Cyborg class
	 */
	public void brake() {
		PlayerCyborg tempCyborg = findPlayerCyborg();
		int currentSpeed = tempCyborg.getSpeed();
		// decrease speed by 1
		tempCyborg.setSpeed(currentSpeed - 1);
		System.out.println("Speed set to " + tempCyborg.getSpeed());	
		this.updateObservers();
	}

	/*
	The method cyborgCollision print to the console letting the player know a collision with another 
	cyborg has occurred. This collision should increase the damage level attribute on the cyborg by
	a small amount. The cyborg's color should also fade by a small amount. The speed of the cyborg 
	should be adjusted if needed to make sure it stays within the speed limitation rule. If the cyborg's 
	damage level is greater than the speed of the cyborg then the player loses a life; and if the player 
	only has one life left then it is game over and the exit method should be called.
	 */
	public void cyborgCollision() {
		PlayerCyborg tempCyborg = findPlayerCyborg();
		NonPlayerCyborg npcCyborg = findNonPlayerCyborg();
		System.out.println("A collision with another cyborg has occured");
		
		//increase damage level by 2 
		int initialDamage = tempCyborg.getDamageLevel();
		int damageTaken = initialDamage + 2;
		tempCyborg.setDamageLevel(damageTaken);		
		npcCyborg.setDamageLevel(damageTaken);
		
		if (damageTaken < tempCyborg.getSpeed()) {
			System.out.println("Cyborg damage level has increased from " + initialDamage + " to " + damageTaken);
			//fade the color of the cyborg
			tempCyborg.fadePlayerCyborgColor();
		}else if(damageTaken > tempCyborg.getSpeed()) {
			if (playerLives > 0) {
				playerLives--;
				System.out.println("Player has lost a life, lives remaining = " + playerLives);
				reinitialize();
			}else {
				System.out.println("You have no lives remaining. You lose!!");
				System.exit(0);
			}
		}		
		this.updateObservers();
		
	}
	
	public NonPlayerCyborg findNonPlayerCyborg() {
		IIterator it = getCollection().getIterator();
		while(it.hasNext()) {
			if(it.getNext() instanceof NonPlayerCyborg) {
				return (NonPlayerCyborg)it.getCurr();
			}
		}
		System.out.println("No NPC Cyborg found");
		return null;
	}

	/**
	 * When a player loses a life, this method is called to reinitialize the game world
	 */
	public void reinitialize() {		
		init();		
	}



	
	// when cyborg collides with a base
	public void baseCollide(Base b) {
		int baseNum = b.getSequenceNumber();
		// if cyborg reaches last base, it wins
		if (baseNum == playerCyborg.getLastBaseReached() + 1) {
			IIterator it = gameObjectsCollection.getIterator();
			while (it.hasNext()) {
				GameObject ob = it.getNext();
				if (ob instanceof Base) {
					Base newB = (Base) ob;
					if (newB.getSequenceNumber() == baseNum) {
						GameObject o = it.getNext();
						if (o instanceof PlayerCyborg) {
							playerCyborg.setLastBaseReached(baseNum);
							playerCyborg.setLocation(newB.getLocation().getX(), newB.getLocation().getY());
							if (playerCyborg.getLastBaseReached() == 4) {
								System.out.println("Player's Base reached: " + playerCyborg.getLastBaseReached());
								System.out.println("YOU WON! Total time: " + clockTickCount);
								System.exit(0);
							} else if (o instanceof NonPlayerCyborg) { // if non-player reaches last base, you loose
								NonPlayerCyborg nonPlaya = (NonPlayerCyborg) o;
								if (nonPlaya.getLastBaseReached() == 4) {
									System.out.println("Non-Player's Base reached: " + nonPlaya.getLastBaseReached());
									System.out.println("Game over, a non-player cyborg wins!");
									System.exit(0);
								}
							}
						}
					}
				}
			}
		}
		this.updateObservers();

	}
	// when cyborg collides with drone
	public void droneCollide() {
		// play sound when colliding with cyborg
	   //droneCollisionSound.play();
		IIterator it = gameObjectsCollection.getIterator();
		while (it.hasNext()) {
			GameObject o = it.getNext();
			if (o instanceof Cyborg) {
				Cyborg borg = (Cyborg) o;
				borg.setDamageLevel(borg.getDamageLevel()+1);
				int affectColor = playerCyborg.getDamageLevel() * 10;
				borg.setColor(155 + affectColor, 0, 0);
				if (borg.getDamageLevel() >= borg.getMaximumDamage())
					damageLives();
				System.out.println("Drone Collision, Damage: " + borg.getDamageLevel());
				this.updateObservers();
			}
		}
	}

	public void damageLives() {
		System.out.println("Went to damageLives method to subtract a live!");
		this.setPlayerLives(getPlayerLives()-1);
		if (getPlayerLives() == 0) {
			System.out.println("GAME OVER!");
			System.exit(0);
		}
		reinitialize();
	}
	public void clockTick(int rate) {
		IIterator it = gameObjectsCollection.getIterator();
		
		
		while(it.hasNext()) {
			if(it.getNext() instanceof PlayerCyborg) {
				PlayerCyborg playerCyborg = (PlayerCyborg)it.getCurr();
				if(playerCyborg.getEnergyLevel() != 0 && playerCyborg.getDamageLevel() != playerCyborg.getMaximumDamage() && playerCyborg.getSpeed() != 0) {
					playerCyborg.move(getWidth(), getHeight(), rate);
					if(this.getClockTickCount()%1000 ==0) {
						playerCyborg.setEnergyLevel(playerCyborg.getEnergyLevel() - playerCyborg.getEnergyConsumptionRate());
					}
					
				}else if(playerCyborg.getEnergyLevel() <= 0) {
					setPlayerLives(getPlayerLives()-1);
					System.out.println("In clockTick() , lost all energy, reinitialize");
					reinitialize();
					if(getPlayerLives() <= 0) {
						System.out.println("You lose, maximum lives lost!");
						System.exit(0);
					}
			}
		} else if(it.getCurr() instanceof Drone) {
				Drone drone = (Drone)it.getCurr();
				drone.move(getWidth(), getHeight(), rate);
		}else if(it.getCurr() instanceof NonPlayerCyborg) {
				NonPlayerCyborg npc = (NonPlayerCyborg)it.getCurr();
				npc.move(getWidth(),getHeight(), this, rate);
				if(this.clockTickCount % 1000 == 0) {
					npc.setEnergyLevel(npc.getEnergyLevel() - npc.getEnergyConsumptionRate());
					
				}
		}
			
		}
		
		// increment the game clock
		int newClock = this.getClockTickCount() + rate;
		this.setClockTickCount(newClock);
		
		// look for a collision
				IIterator iter = gameObjectsCollection.getIterator();
				while (iter.hasNext()) {
					GameObject ob = iter.getNext();
					if (ob instanceof Cyborg) {
						Cyborg c = (Cyborg) ob;
						IIterator otherObs = gameObjectsCollection.getIterator();
						while (otherObs.hasNext()) {
							ICollider collide = otherObs.getNext();
							if (c.collidesWith(collide) && collide != lastCollideObject) {
								//c.handleCollision(collide);
								lastCollideObject = collide;
							}
						}
					}
				}		
		this.updateObservers();
	}
	
	/*
	The method timeTick should print to the console the game clock has ticked. If the player's cyborg moves then it
	increase or decrease their heading by the amount of their steering direction. The drones update their heading. The
	cyborg's energy level should decrease by the amount of the consumption rate.
	 */

	/**
	 * Allows the user to turn the cyborg left or right
	 * @param lr
	 */
	public void turn(char lr) {
		findPlayerCyborg().turn(lr);
		this.updateObservers();
	}
	
	public static GameWorld getGameWorld() {
		if (gw == null)
			gw = new GameWorld();
		return gw;
	}
	
	public void changeStrategy() {
		IIterator it = gameObjectsCollection.getIterator();
		while (it.hasNext()) {
			GameObject ob = it.getNext();
			if (ob instanceof NonPlayerCyborg) {
				NonPlayerCyborg nonPlaya = (NonPlayerCyborg) ob;
				if (nonPlaya.getCurrStrategy() instanceof DirectStrategy) {
					System.out.println("Strategy changed to Attack!");
					nonPlaya.switchStrategy(new AttackStrategy());
				} else {
					System.out.println("Strategy changed to Direct!");
					nonPlaya.switchStrategy(new DirectStrategy());
				}
			}
		}
	}
	
	public PlayerCyborg findPlayerCyborg() {
		IIterator it = getCollection().getIterator();
		while(it.hasNext()) {
			if(it.getNext() instanceof PlayerCyborg) {
				return (PlayerCyborg)it.getCurr();
			}
		}
		System.out.println("No Cyborg found");
		return null;
	}	
	
	public Drone findDrone() {
		IIterator it = getCollection().getIterator();
		while(it.hasNext()) {
			if(it.getNext() instanceof Drone) {
				return (Drone)it.getCurr();
			}
		}
		System.out.println("No Drone found");
		return null;
	}	
	
	public void updateObservers() {
		this.setChanged();
		this.notifyObservers();
	}

	public void toggleSound() {
		this.sound = !this.sound;
		this.updateObservers();
	}	
	
	/**
	 * @return the playerLives
	 */
	public int getPlayerLives() {
		return playerLives;
	}

	/**
	 * @param playerLives the playerLives to set
	 */
	public void setPlayerLives(int playerLives) {
		this.playerLives = playerLives;
	}

	/**
	 * @return the clockTickCount
	 */
	public int getClockTickCount() {
		return clockTickCount;
	}

	/**
	 * @param clockTickCount the clockTickCount to set
	 */
	public void setClockTickCount(int clockTickCount) {
		this.clockTickCount = clockTickCount;
	}

	public boolean getSound() {
		return sound;
	}
	
	public GameObjectCollection getCollection() {
		if(gameObjectsCollection == null) {
			System.out.println("list is empty?");
			return gameObjectsCollection;
		}
		return gameObjectsCollection;
		
	}
	public int getWidth() {
		return this.width;
	}
	public int getHeight() {
		return this.height;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setPosition() {
		this.position = !this.position;
		
	}	
	
	public void createSounds() {
		npcCollisionSound = new Sound("CRASH1.wav");
		energyCollisionSound = new Sound("electric1.wav");
		droneCollisionSound = new Sound("explosio.wav");
	}

	// when cyborg collides with another cyborg
	public void npcCollide() {
		//npcCollisionSound.play();
		// affect on player cyborg
		playerCyborg.setDamageLevel(playerCyborg.getDamageLevel()+2);
		int affectColorPc = playerCyborg.getDamageLevel() * 10;
		playerCyborg.setColor(155 + affectColorPc, 0, 0);
		if (playerCyborg.getDamageLevel() >= playerCyborg.getMaximumDamage())
			damageLives();
		System.out.println("NPC Collision, Cyborg's damage: " + playerCyborg.getDamageLevel());

		// affect on non player cyborg
		IIterator it = gameObjectsCollection.getIterator();
		Random rand = new Random();
		int counter = 1;
		int npcNum = rand.nextInt(3) + 1;
		while (it.hasNext()) {
			GameObject ob = it.getNext();
			if (ob instanceof NonPlayerCyborg) {
				if (counter == npcNum) {
					NonPlayerCyborg nonPlaya = (NonPlayerCyborg) ob;
					nonPlaya.setDamageLevel(nonPlaya.getDamageLevel()+2); // NPC also sustain a damage
					int affectColorNpc = nonPlaya.getDamageLevel() * 10;
					nonPlaya.setColor(155 + affectColorNpc, 0, 0);
					System.out.println("NPC Collision, NPC's damage: " + nonPlaya.getDamageLevel());
				}
				// counter that increments and whenever it is equal to the random number
				// generated then that is the NPC to get damaged
				// generate a random number between 1-3
				counter++;
			}
		}
		this.updateObservers();
	}
	
	
	public void energyCollide(EnergyStation eStation) {
		Random rand = new Random();
		if (eStation.getCapacity() > 0) {
			// add code to play sound of colliding with energy station
		     //energyCollisionSound.play();
			// add energy stations capacity to cyborgs energy level
			int newEnergyLevel = playerCyborg.getEnergyLevel() + eStation.getCapacity();
			IIterator it = gameObjectsCollection.getIterator();
			while (it.hasNext()) {
				GameObject o = it.getNext();
				if (o instanceof Cyborg) {
					Cyborg borg = (Cyborg) o;
					borg.setEnergyLevel(newEnergyLevel);
					// set energy level's capacity to 0
					eStation.setCapacity(0);
					// fade stations color to light green
					eStation.setColor(152, 251, 152);
					// make new energy station
					int size = rand.nextInt(40) + 10;
					EnergyStation newStation = new EnergyStation();
					gameObjectsCollection.add(newStation);
					System.out.println("Energy: " + borg.getEnergyLevel());
				}
			}
		}
		this.updateObservers();
	}
	
}