package com.mycompany.a3.game.gameWorld.gameObject.moveAbleObject;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.mycompany.a3.game.gameWorld.IDrawable;
import com.mycompany.a3.game.gameWorld.gameObject.GameObject;

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
		  g.setColor(this.getColor());
		  int x = (int)this.getX() + pCmpRelPrnt.getX();
		  int y = (int)this.getY() + pCmpRelPrnt.getY();
		  int[] xPoints = { x, (x - 30), (x + 30), x };
		  int[] yPoints = { (y + 30), (y - 30), (y- 30), (y + 30) };
		  int nPoints = 3;
		  g.drawPolygon(xPoints, yPoints, nPoints);
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

}
