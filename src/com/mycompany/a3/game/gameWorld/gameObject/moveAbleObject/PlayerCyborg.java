package com.mycompany.a3.game.gameWorld.gameObject.moveAbleObject;

import com.codename1.charts.util.ColorUtil;
import com.mycompany.a3.game.gameWorld.GameWorld;

public class PlayerCyborg extends Cyborg implements ISteerable{
	
	private static double initialX=200;
	private static double initialY=200;
	
	
	
	//Singleton design pattern 
	private volatile static PlayerCyborg playerCyborg;
	public static PlayerCyborg getInstance() {
		if(playerCyborg == null) {
			synchronized(GameWorld.class) {
				if(playerCyborg == null)
					playerCyborg = new PlayerCyborg();
			}
		}
		return playerCyborg;
	}
	
	private PlayerCyborg() {
		super(ColorUtil.BLUE,0,0,initialX,initialY); //Color,speed,heading(90 degree to the northward),xLocation,yLocation
		this.setLife(3);
		this.setLastBaseReached(1);
		this.setDamageLevel(0);
		this.setEnergyLevel(40);
		this.setSize(40);
		this.setMaxDamageLevel(40);
		this.setMaxEnergyLevel(40);
		this.setSteeringDirection(0);
	}
	
	//turn left by number amount of degree
	public void turnLeft() {
		this.setSteeringDirection(getSteeringDirection()-5);
		}
	
	//turn right by number amount of degree
	public void turnRight() {
		this.setSteeringDirection(getSteeringDirection()+5);
	}
	
	@Override
	public void respawn(double newX,double newY) {
		this.setX(newX);
		this.setY(newY);
		this.setSteeringDirection(90); //90=0 to the north
		this.setSpeed(0);
		this.setEnergyLevel(20);
		this.setDamageLevel(0);
		this.setColor(ColorUtil.BLUE);
		this.setLife(getLife()-1);
	}
	
	//call by accelerate function to increase Speed
	public void increaseSpeed() {
		
		if(this.getSpeed() < this.getMaximumSpeed()) {
			this.setSpeed(this.getSpeed() + 5);
			System.out.println("increase speed by 5 \n");
		}else {
			System.out.println("You already reach at the max speed " + this.getMaximumSpeed() +"\n");
		}

	}
	
	//call by brake function to decrease speed
	public void decreaseSpeed() {
		if(this.getSpeed() > 0) {
			this.setSpeed(this.getSpeed() - 5);
			System.out.println("decrease speed by 5\n");
		}else {
			System.out.println("You already reach at the mimimum speed 0\n");
		}
	}
	
	//to string information about player Cyborg
	public String toString() {
		return (
				"PlayerCyborg: loc=" + Math.round(this.getX()*10)/10 + "," + Math.round(this.getY()*10)/10 +
				" color=" + this.getColorToString() + " heading=" + this.getHeading() + 
				" speed=" + this.getSpeed() + "\n        size=" + this.getSize() + 
				" maxSpeed=" +this.getMaximumSpeed() + "  steeringDegree="+ this.getSteeringDirection() + 
				" energyLevel=" + this.getEnergyLevel() + " damageLevel=" + this.getDamageLevel()
				);
	}
	

}
