package com.mycompany.a3.game.gameWorld.gameObject.moveAbleObject;

import com.codename1.charts.util.ColorUtil;
import com.mycompany.a3.game.gameWorld.gameObject.GameObject;

public class Drone extends MoveableObject{
	
	//set a non-player Drone black color and size 10
	public Drone() {
		super(ColorUtil.BLACK);
		this.setSize(10);
		
	}
	
	@Override
	public void setColor(int Color){ //to prevent changed in color for drone
	}
	
	//to string information about Drone
	public String toString() {
		return (
				"Drone: loc=" + Math.round(this.getX()*10)/10 + "," + Math.round(this.getY()*10)/10 +
				" color=" + this.getColorToString() + " heading=" + this.getHeading() +
				" speed=" + this.getSpeed() + " size=" + this.getSize()
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
