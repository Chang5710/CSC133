package com.mycompany.a3.game.gameWorld.gameObject.fixedObject;

import java.util.Vector;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.mycompany.a3.game.gameWorld.IDrawable;
import com.mycompany.a3.game.gameWorld.gameObject.GameObject;

public class Base extends FixedObject implements IDrawable {
	private int baseID;
	
	//set up Base details information
	public Base(int baseID, double x,double y) {
		super(ColorUtil.LTGRAY, x, y);
		this.baseID=baseID;
		setSize(30);
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
	public void draw(Graphics g, Point pCmpRelPrnt) {
		  g.setColor(this.getColor());
		  int xLoc = (int)this.getLocation().getX() + pCmpRelPrnt.getX();
		  int yLoc = (int)this.getLocation().getY() + pCmpRelPrnt.getY();
		  int[] xPoints = { xLoc, (xLoc - 30), (xLoc + 30), xLoc };
		  int[] yPoints = { (yLoc + 30), (yLoc - 30), (yLoc - 30), (yLoc + 30) };
		  int nPoints = 4;
		  g.drawPolygon(xPoints, yPoints, nPoints);

		  if(!isSelected()) {
			  g.fillPolygon(xPoints, yPoints, nPoints);
		  }
		  
		  //draw Base ID on the Base
		  g.setColor(ColorUtil.BLACK);
		  g.drawString(Integer.toString(baseID), 
				  		(int)Math.round(getX()-10) + pCmpRelPrnt.getX(), 
				  			(int)Math.round(getY()-30) + pCmpRelPrnt.getY());
		  
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
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		// TODO Auto-generated method stub
		int px = pPtrRelPrnt.getX();
		int py = pPtrRelPrnt.getY();
		
		int xLoc = (int) (pCmpRelPrnt.getX() + getX());
		int yLoc = (int) (pCmpRelPrnt.getY() + getY());
		
		if ( (px >= xLoc) && (px <= xLoc+30) && (py >= yLoc) && (py <= yLoc+30) ) {
			return true;
		}else {
			return false;
		}
	}

}
