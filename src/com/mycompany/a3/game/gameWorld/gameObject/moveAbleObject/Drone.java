package com.mycompany.a3.game.gameWorld.gameObject.moveAbleObject;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.mycompany.a3.game.gameWorld.IDrawable;

public class Drone extends MoveableObject implements IDrawable{
	
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
	public void draw(Graphics g, Point pCmpRelPrnt) {
		  g.setColor(ColorUtil.GREEN);
		  int x = (int)this.getX() + pCmpRelPrnt.getX();
		  int y = (int)this.getY() + pCmpRelPrnt.getY();
		  int[] xPoints = { x, (x - 20), (x + 20), x };
		  int[] yPoints = { (y + 30), (y - 30), (y- 30), (y + 30) };
		  int nPoints = 3;
		  g.drawPolygon(xPoints, yPoints, nPoints);
	}

}
