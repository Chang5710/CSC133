package com.mycompany.a3.game.gameWorld.gameObject.fixedObject;

import com.codename1.charts.util.ColorUtil;

public class Base extends FixedObject {
	private int baseID;
	
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

}
