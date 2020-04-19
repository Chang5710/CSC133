package com.mycompany.a3.game.gameWorld.gameObject.fixedObject;

import com.codename1.charts.util.ColorUtil;
import com.mycompany.a3.game.gameWorld.gameObject.GameObject;

public class Base extends FixedObject {
	private int baseID;
	private boolean collisionFlag;
	
	//set up Base details information
	public Base(int baseID, double x,double y) {
		super(ColorUtil.LTGRAY, x, y);
		this.baseID=baseID;
		setSize(10);
		//this.x = x;
		//this.y = y;
	}
	
	//get BaseID
	public int getBaseID() {
		return baseID;
	}
	
	public void setColor(int Color) {}// don't allow base be change color
	
	//to string information about Base
	public String toString() {
		return (
				"Base: loc=" + getX() +","+getY()+ " color=" + this.getColorToString() +
				" size=" + getSize() + " seqNum=" + baseID	
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
