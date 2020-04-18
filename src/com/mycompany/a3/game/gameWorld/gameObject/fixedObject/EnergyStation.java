package com.mycompany.a3.game.gameWorld.gameObject.fixedObject;

import java.util.Random;

import com.codename1.charts.util.ColorUtil;
import com.mycompany.a3.game.gameWorld.gameObject.GameObject;

public class EnergyStation extends FixedObject{
	private int capacity;
	private int size;
	
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
		return false;
	}

	@Override
	public void handleCollision(GameObject otherObject) {
		// TODO Auto-generated method stub
		
	}
	

}
