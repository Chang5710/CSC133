package com.mycompany.a3.game.gameWorld.gameObject.fixedObject;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.mycompany.a3.game.gameWorld.IDrawable;

public class Base extends FixedObject implements IDrawable {
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

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		  g.setColor(ColorUtil.BLUE);
		  int xLoc = (int)this.getLocation().getX() + pCmpRelPrnt.getX();
		  int yLoc = (int)this.getLocation().getY() + pCmpRelPrnt.getY();
		  int[] xPoints = { xLoc, (xLoc - 20), (xLoc + 20), xLoc };
		  int[] yPoints = { (yLoc + 30), (yLoc - 30), (yLoc - 30), (yLoc + 30) };
		  int nPoints = 4;
		  g.drawPolygon(xPoints, yPoints, nPoints);
		  g.fillPolygon(xPoints, yPoints, nPoints);
	}

}
