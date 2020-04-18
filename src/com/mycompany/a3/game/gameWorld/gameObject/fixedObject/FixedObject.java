package com.mycompany.a3.game.gameWorld.gameObject.fixedObject;

import com.codename1.ui.geom.Point2D;
import com.mycompany.a3.game.gameWorld.gameObject.GameObject;

public abstract class FixedObject extends GameObject{
	
	//update color for EnergyStation
	public FixedObject(int Color) {
		super(Color);
	}

	//update color and location for Base
	public FixedObject(int Color, double x, double y) {
		super(Color, x, y);
		
	}
	
	@Override
	public void setLocation(Point2D location) {
	}

}
