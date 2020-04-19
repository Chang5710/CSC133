package com.mycompany.a3.game.gameWorld.gameObject.fixedObject;

import java.util.Random;

import com.codename1.charts.util.ColorUtil;
import com.mycompany.a3.game.gameWorld.gameObject.GameObject;
import com.mycompany.a3.game.gameWorld.gameObject.moveAbleObject.NonPlayerCyborg;
import com.mycompany.a3.game.gameWorld.gameObject.moveAbleObject.PlayerCyborg;

public class EnergyStation extends FixedObject{
	private int capacity;
	private int size;
	private boolean collisionFlag;
	
	//set capacity and size for EnergyStation
	public EnergyStation() {
		super(ColorUtil.GREEN);
		Random rn = new Random();
		this.capacity = rn.nextInt((40-10)+1)+10; //range between 10 and 40
		this.size = this.capacity;
		setSize(this.capacity);
	}
	
	//getter and setter
	public int getCapacity() {
		return capacity;
	}
	
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public int getSize() {
		return size;
	}
	
	//to string information about EnergyStation
	public String toString() {
		return (
				"EnergyStation: loc=" + Math.round(this.getX()*10)/10 + "," + Math.round(this.getY()*10)/10+ 
				" color=" + getColorToString() + " size=" + getSize() + " capacity=" + capacity	
		);
	}

	@Override
	public boolean collidesWith(GameObject otherObject) {
		// TODO Auto-generated method stub
		boolean result = false;
		double thisCenterX = this.getX();
		double thisCenterY = this.getY();
		
		double otherCenterX = (otherObject).getX();
		double otherCenterY = (otherObject).getY();
		
		double dx = thisCenterX - otherCenterX;
		double dy = thisCenterY - otherCenterY;
		
		double distBetweenCentersSqr = (dx * dx + dy * dy);
		
		// find square of sum of radii
		int thisRadius= this.getSize() / 2;
		int otherRadius= (otherObject).getSize() / 2;
		
		int radiiSqr= (thisRadius * thisRadius + 2 * thisRadius * otherRadius + otherRadius * otherRadius);
		
		if (distBetweenCentersSqr <= radiiSqr) { result = true ; }
		
		return result;
	}

	@Override
	public void handleCollision(GameObject otherObject) {
		// TODO Auto-generated method stub
		if(otherObject instanceof PlayerCyborg || otherObject instanceof NonPlayerCyborg) {
		this.setCapacity(0);
		this.setColor(ColorUtil.rgb(162, 255, 159)); //set Empty EnergyStation color to light green
		}
	}
	
	@Override
	public void setCollisionFlag() {
		// TODO Auto-generated method stub
		collisionFlag = true;
		
	}

	@Override
	public boolean getCollisionFlag() {
		// TODO Auto-generated method stub
		return collisionFlag;
	}
	

}
