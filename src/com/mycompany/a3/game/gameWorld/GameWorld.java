package com.mycompany.a3.game.gameWorld;

import java.util.Observable;
import java.util.Random;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Display;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.game.gameWorld.gameObject.GameObject;
import com.mycompany.a3.game.gameWorld.gameObject.fixedObject.Base;
import com.mycompany.a3.game.gameWorld.gameObject.fixedObject.EnergyStation;
import com.mycompany.a3.game.gameWorld.gameObject.moveAbleObject.AttackStrategy;
import com.mycompany.a3.game.gameWorld.gameObject.moveAbleObject.BaseStrategy;
import com.mycompany.a3.game.gameWorld.gameObject.moveAbleObject.Cyborg;
import com.mycompany.a3.game.gameWorld.gameObject.moveAbleObject.Drone;
import com.mycompany.a3.game.gameWorld.gameObject.moveAbleObject.IStrategy;
import com.mycompany.a3.game.gameWorld.gameObject.moveAbleObject.MoveableObject;
import com.mycompany.a3.game.gameWorld.gameObject.moveAbleObject.NonPlayerCyborg;
import com.mycompany.a3.game.gameWorld.gameObject.moveAbleObject.PlayerCyborg;

public class GameWorld extends Observable{
	
	private int gameClock = 0;
	private int numberOfDrone;
	private int numberOfEnergyStation;
	private int numberOfTurnLeft;
	private int numberOfTurnRight;
	private int numberOfBase = 4;
	private GameObjectCollection gameObjects;
	private String Sound = "ON";
	Random rn = new Random();
	private PlayerCyborg cyborg; //to hold player cyborg
	NonPlayerCyborg cyborgNPC; //to hold temporary NPC cyborg
	EnergyStation energyStation;
	Base base;
	IStrategy strategy; 
	
	//setter and getter
	public int getGameClock() {
		return gameClock;
	}
	public void setGameClock(int gameClock) {
		this.gameClock = gameClock;
	}
	
	public PlayerCyborg getPlayerCyborg() {
		return cyborg;
	}
	
	public GameObjectCollection getGameObjects() {
		return gameObjects;
	}

	//Singleton design pattern 
	private volatile static GameWorld gw;
	private GameWorld(){};
	public static GameWorld getInstance() {
		if(gw == null) {
			synchronized(GameWorld.class) {
				if(gw == null)
					gw = new GameWorld();
			}
		}
		return gw;
	}
	
	//initialize the game. Set up base,drone,energyStation and create player Cyborg
	public void init() {
		gameObjects = new GameObjectCollection();
		gameObjects.add(new Base(1,200,200));
		gameObjects.add(new Base(2,800,200));
		gameObjects.add(new Base(3,500,500));
		gameObjects.add(new Base(4,800,800));
		gameObjects.add(cyborg = PlayerCyborg.getInstance());
		gameObjects.add(cyborgNPC = new NonPlayerCyborg(230,200));
		cyborgNPC.setStrategy(new BaseStrategy());
		gameObjects.add(cyborgNPC = new NonPlayerCyborg(200,170));
		cyborgNPC.setStrategy(new BaseStrategy());
		gameObjects.add(cyborgNPC = new NonPlayerCyborg(170,200));
		cyborgNPC.setStrategy(new AttackStrategy());
		this.numberOfDrone = 2;
		for(int i =0;i<numberOfDrone;i++) {
			gameObjects.add(new Drone());
		}
		this.numberOfEnergyStation = rn.nextInt((4-2)+1)+2;//range between 2 and 4
		for(int i =0;i<numberOfEnergyStation;i++) {
			gameObjects.add(new EnergyStation());
		}
		this.map();
		
		this.setChanged();
		this.notifyObservers(this);
	}
	
	//check if cyborg have enough life to respawn, if end the game
	public void respawnCyborg(Cyborg cyborgT){ 
			if(cyborgT instanceof PlayerCyborg) {
				if(cyborgT.getLife()>1) {
					Base base = findBase(cyborgT);
					cyborgT.respawn(base.getX(),base.getY());
				}else {
					System.out.println("PlayerCyborg Lose! \n");
					System.exit(0);				
			    }
			}
	}
	
	public void findCyborg() {
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {//update cyborg information on GameWorld
			GameObject obj = iter.getNext();
			if(obj instanceof PlayerCyborg) {
				this.cyborg= (PlayerCyborg)obj;		
			}else if(obj instanceof NonPlayerCyborg) {
				this.cyborgNPC = (NonPlayerCyborg) obj;
				if(cyborgNPC.getCurStrategy() instanceof AttackStrategy) {
					if( GetDistance(obj,cyborgNPC) <= 5) { 	
						cyboryCollision();
					}
				}
			}
		}
	}
	
	//find the last base reached and update the location
	public Base findBase(Cyborg cyborgT) {;
		Base base = null;
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			GameObject obj = iter.getNext();
			if(obj instanceof Base) {
				base = (Base) obj;
				if(cyborgT instanceof PlayerCyborg) {
					if(base.getBaseID()==cyborgT.getLastBaseReached()) {
						this.base = base;
						cyborgT.setX(base.getX());
						cyborgT.setY(base.getY());
						break;
					}
				}else { //check if NPC location
					cyborgNPC = (NonPlayerCyborg) cyborgT;
					if(base.getBaseID()==(cyborgNPC).getTargetBase()) {
						if( GetDistance(obj,cyborgNPC) <= 10) { 	
							baseCollision(base.getBaseID(),cyborgNPC);
						}
					}
				}
			}
		}
		return base;
	}
	
	public double GetDistance(GameObject obj, NonPlayerCyborg cyborgNPC) {
		
		double dx = Math.abs(obj.getX() - cyborgNPC.getX());
		double dy = Math.abs(obj.getY() - cyborgNPC.getY());
		double distance = Math.sqrt(dx*dx+dy*dy);
		return distance;
	}
	
	//check energy level after each move
	public Boolean checkEnergy(int currEnergy, Cyborg cyborgT){
		if(currEnergy>0) {
			return true;
		}else {
			System.out.println("You run out of Energy\n");
			respawnCyborg(cyborgT);
			return false;
		}
	}
	
	//check Damage level and change color
	public Boolean checkDamage(int currDamage, Cyborg cyborgT) {
		if(currDamage<cyborgT.getMaxDamageLevel()) {
			if(currDamage>cyborgT.getMaxDamageLevel()*0.3 && currDamage<cyborgT.getMaxDamageLevel()*0.7) {
				cyborgT.setColor(ColorUtil.rgb(255, 204, 203)); //light red
			}else if(currDamage>cyborgT.getMaxDamageLevel()*0.7) {
				cyborgT.setColor(ColorUtil.rgb(255, 64, 64)); //more close to red rgb(255,0,0)
			}
			cyborgT.setDamageLevel(currDamage);
			return true;
		}
		else {
			System.out.println("You Cybory is breaked\n");
			respawnCyborg(cyborgT);
			return false;
		}
	}
	
	//exit the game
	public void exit() {
		Display.getInstance().exitApplication();
	}
	
	//increase speed
	public void accelerate() {
		cyborg.increaseSpeed();
	}
	
	//decrease speed
	public void brake() {
		cyborg.decreaseSpeed();
	}
	
	//save number of time turn left and execute when gameClock increase
	public void turnLeft() {
		if(numberOfTurnLeft >= 8) {
			System.out.println("Only allow to turn left maximum 40 degree\n");
		}else {
			cyborg.turnLeft();
			numberOfTurnLeft++;
			numberOfTurnRight--;
			System.out.println("turn left by 5 degree\n");
		}
		
	}
	
	//save number of time turn right and execute when gameClock increase
	public void turnRight() {
		if(numberOfTurnRight >= 8) {
			System.out.println("Only allow to turn Right maximum 40 degree\n");
		}else {
			cyborg.turnRight();
			numberOfTurnLeft--;
			numberOfTurnRight++;
			System.out.println("turn Right by 5 degree\n");
		}
	}
	
	//when player cyborg collided with another cyborg that cause 2 damage
	public void cyboryCollision() {
		System.out.println("Collsion with another cyborg cause 2 damage\n");
		checkDamage(cyborg.getDamageLevel() + 2, cyborg);	
		checkDamage(cyborgNPC.getDamageLevel() + 1, cyborgNPC);
		setNewSpeed(cyborg);
		setNewSpeed(cyborgNPC);
		
		//reset NPC set Heading and SteeringDirection
		cyborgNPC.setHeading(0);
		cyborgNPC.setSD(0);
		cyborgNPC.setSteeringDirection(0);
	}
	
	public void setNewSpeed(Cyborg cyborgT){ //update speed after DamageLevel too high
		double ratio = cyborgT.getDamageLevel() / cyborgT.getMaxDamageLevel();
		if(ratio != 0) {
		cyborgT.setSpeed((int)(cyborgT.getSpeed()*ratio)); //set Speed limit needs to be limited by cyborg's damage.
		checkDamage(cyborgT.getDamageLevel(),cyborgT); //set check Damage Level
		}
	}
	
	//when player cyborg collided with base
	public void baseCollision(int BaseID,Cyborg cyborgT) {
		if(cyborgT instanceof PlayerCyborg) {
			System.out.println("You reach to base " + BaseID + "\n");
			findBase(cyborg);
			if(cyborg.getLastBaseReached()+1 ==BaseID) {//check if the base in sequential
				cyborg.setLastBaseReached(BaseID); //update lastBaseReached
				cyborgT.setX(base.getX());
				cyborgT.setY(base.getY());
				if(BaseID==numberOfBase) {
					System.out.println("You Won!!! You had reach to the last Base!\n");
					System.exit(0);
				}
			}else {
				System.out.println("Please collide base in sequential!\n" + 
								   "The next sequential is " + (cyborg.getLastBaseReached()+1) + "\n");
			}
		}else { //when NPC collide with target base
			cyborgNPC = (NonPlayerCyborg) cyborgT;
			if(BaseID == numberOfBase) {
				System.out.println("NonPlayerCyborg Won!!! NonPlayerCyborg had reach to the last Base!\n");
				System.exit(0);
			}else {
				cyborgNPC.setLastBaseReached(cyborgNPC.getTargetBase());  //update last Base Reached
				cyborgNPC.setTargetBase(cyborgNPC.getTargetBase()+1); //update new Target Base
				cyborgNPC.setHeading(0);
				cyborgNPC.setSD(0);
				cyborgNPC.setSteeringDirection(0);
			}
		}
	}
	
	//when player cyborg collided with EnergSation
	public void energyStationCollision() {
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			GameObject obj = iter.getNext();
			if(obj instanceof EnergyStation) {
				this.energyStation = (EnergyStation) obj;
				if(energyStation.getCapacity()!=0) {
					int beforeRuel = cyborg.getEnergyLevel();
					if(cyborg.getEnergyLevel()+energyStation.getCapacity()<cyborg.getMaxEnergyLevel()) {
						cyborg.setEnergyLevel(cyborg.getEnergyLevel()+energyStation.getCapacity());
					}else {
						cyborg.setEnergyLevel(cyborg.getMaxEnergyLevel()); //over fuel the Energy
					}
					System.out.println("Collsion with a EnergyStation refuel " + Math.abs(cyborg.getEnergyLevel()-beforeRuel) + " Energy\n");
					energyStation.setCapacity(0);
					energyStation.setColor(ColorUtil.rgb(162, 255, 159)); //set Empty EnergyStation color to light green
					gameObjects.add(new EnergyStation());
					break;
				}
			}
		}
	}
	
	//when player cyborg collided with drone
	public void droneCollision() {
		System.out.println("Collsion with a Drone cause 1 damage\n");
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			GameObject obj = iter.getNext();
			if(obj instanceof Drone) {
				checkDamage(cyborg.getDamageLevel()+1,cyborg);
				double ratio = cyborg.getDamageLevel() / cyborg.getMaxDamageLevel();
				cyborg.setSpeed((int)(cyborg.getSpeed()*ratio)); //set Speed limit needs to be limited by cyborg's damage.
				break;
			}
		}
	}
	
	//check if cyborg have enough Energy for next Tick
	public void consumeEnergy(Cyborg cyborgT) {
		int currentEnergy = cyborgT.getEnergyLevel() - cyborgT.getEnergyConsumptionRate();
		if(checkEnergy(currentEnergy,cyborgT)) {
			cyborgT.setEnergyLevel(currentEnergy);
		}
		if(cyborgT instanceof NonPlayerCyborg) {
			if(cyborgT.getEnergyLevel()<10) {
				cyborgT.setEnergyLevel(30);
			}
		}
	}
	
	//check Heading within 360 degree and set Heading 
	public void checkHeading(Cyborg cyborgT) {
		if(cyborgT.getSteeringDirection() >= 0) {
			cyborgT.setHeading((cyborgT.getHeading() + cyborgT.getSteeringDirection()) % 360);
		}else {
			if(cyborgT.getHeading() == 0) {
				cyborgT.setHeading(360 + cyborgT.getSteeringDirection());
			}else {
				cyborgT.setHeading(cyborgT.getHeading() + cyborgT.getSteeringDirection());
			}
		}
	}
	
	//game clock has ticked
	public void tick() {
		gameClock++;
				
		//check Energy
		consumeEnergy(cyborg);
		
		//check heading
		checkHeading(cyborg);
		
		IIterator iter2 = gameObjects.getIterator();
		while(iter2.hasNext()){
			GameObject obj = iter2.getNext();
			if(obj instanceof NonPlayerCyborg) {
				cyborgNPC = (NonPlayerCyborg) obj;
				if (cyborgNPC.getCurStrategy() instanceof BaseStrategy) {
					cyborgNPC.invokeStrategy(gameObjects);
					findBase(cyborgNPC);
				}else {
					cyborgNPC.invokeStrategy(gameObjects);
					findCyborg();
				}
				checkHeading(cyborgNPC);
				setNewSpeed(cyborgNPC);
				consumeEnergy(cyborgNPC);
				((NonPlayerCyborg) obj).move();
			}else if(obj instanceof PlayerCyborg) {
				((MoveableObject) obj).move();
			}else if(obj instanceof Drone) {
				((MoveableObject) obj).move();
			}
		
		}
		
		numberOfTurnLeft = 0;
		numberOfTurnRight = 0;
		cyborg.setSteeringDirection(0);
		System.out.println("gameClock increase by 1\n");
		
		this.setChanged();
		this.notifyObservers(this);
	}
	
	//if NPC's Energy running low add some more
	public void checkNPCEnergy() {
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			GameObject obj = iter.getNext();
			if(obj instanceof NonPlayerCyborg) {
				cyborgNPC = (NonPlayerCyborg) obj;
				if(cyborgNPC.getEnergyLevel()<10) {
					cyborgNPC.setEnergyLevel(30);
				}
			}
		}
	}
	
	//display player-cyborg state:life, gameClock,last reached base, energy level, and damage  level
	public void displayStates() {
		if( checkEnergy(cyborg.getEnergyLevel(),cyborg) && checkDamage(cyborg.getDamageLevel(),cyborg) == true) {//check if energyLevel>0 and DamageLevel<5
			System.out.println("Life=" + cyborg.getLife() + " GameClock=" + this.gameClock + " LastBaseReached=" + cyborg.getLastBaseReached() +
							   " EnergLevel=" + cyborg.getEnergyLevel() + " DamageLevel=" + cyborg.getDamageLevel() +"\n"
			);
		}
	}
	
	//show a set of lines describing the current object in the world 
	public void map() {
			
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			System.out.println(iter.getNext().toString());
		}
		System.out.println("\n");
	}
	
	//Sound on/off
	public void Sound(ActionEvent e) {
		if(((CheckBox)e.getComponent()).isSelected()) {
			Sound = "OFF";
		}else {
			Sound = "ON";
		}
	}
	
	public String getSound() {
		return Sound;
	}
	public void setSound(String sound) {
		Sound = sound;
	}
	
	//Change starategy for each NPC
	public void ChangeStrategies() {
		System.out.println("All NPC changed strategy");
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			GameObject obj = iter.getNext();
			if(obj instanceof NonPlayerCyborg) {
				cyborgNPC = (NonPlayerCyborg) obj;
				if(cyborgNPC.getCurStrategy() instanceof BaseStrategy) {
					cyborgNPC.setStrategy(new AttackStrategy());
				}else {// not BaseStrategy must be AttackStrategy
					cyborgNPC.setStrategy(new BaseStrategy());
				}
			}
		}
	}
}
 
