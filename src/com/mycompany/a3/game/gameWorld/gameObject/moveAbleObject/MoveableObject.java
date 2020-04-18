package com.mycompany.a3.game.gameWorld.gameObject.moveAbleObject;

import java.util.Random;

import com.mycompany.a3.game.gameWorld.gameObject.GameObject;

public abstract class MoveableObject extends GameObject{
	private int speed;
	private int heading;
	
	//Add speed and steeringDirection for non-player like Drone
	public MoveableObject(int Color) {
		super(Color); //update color
		randomized();
	}
	
	//randomize the speed and direction for Drone
	public void randomized() {
		Random rn = new Random();
		this.speed= rn.nextInt((10-5)+1)+5; //range between 5 and 10
		this.heading= rn.nextInt(360); //range within 360 degree
	}
	
	//Add speed and steeringDirection for player Cybory
	public MoveableObject(int Color, int speed, int heading, double x, double y) {
		super(Color,x,y); //update color and location
		this.speed=speed;
		this.heading=  heading;
	}


	//getter and setter
	public void setHeading(int x) {
		this.heading= x;
	}
	
	public void setSpeed(int x) {
		this.speed = x;
	}

	public int getSpeed() {
		return this.speed;
	}

	public int getHeading() {
		return heading;
	}

	//to get new location once the tick is increase
	public void move() {
		double radian = Math.toRadians(90-this.heading); //90-heading to get the direction as 0 to the north
		double newX = this.getX()+ Math.cos(radian) * this.speed;
		double newY = this.getY()+ Math.sin(radian) * this.speed;
		
		//testing
//		if(this instanceof NonPlayerCyborg) {
//			System.out.println("Heading : " + this.heading);
//			System.out.println(" X : " + this.getX() + " dx : " + Math.cos(radian) * this.speed);
//			System.out.println(" Y : " + this.getY() + " dy : " + Math.sin(radian) * this.speed);
//			System.out.println("newX : " + newX);
//			System.out.println("newY : " + newY);
//		}
		if(newX>getGameWorldWidth()) {//if object is moving our of x boundary
			if(this instanceof PlayerCyborg || this instanceof NonPlayerCyborg)
				newX = getGameWorldWidth();
			else if(this instanceof Drone) {
				randomized();
			}
		}
		if(newY>getGameWorldHigh()) {//if object is moving our of y boundary
			if(this instanceof PlayerCyborg || this instanceof NonPlayerCyborg)
				newY = getGameWorldHigh();
			else if(this instanceof Drone) {
				randomized();
			}
		}
//		if(this instanceof NonPlayerCyborg && (90-this.heading) < 0) {
//			setX(newY);
//			setY(newX);
//			return;
//		}
		setX(newX);
		setY(newY); //set new Location
	}

}
