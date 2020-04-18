package com.mycompany.a3.game.gameWorld.gameObject.fixedObject;

import java.util.Random;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.mycompany.a3.game.gameWorld.IDrawable;

public class EnergyStation extends FixedObject implements IDrawable{
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
	public void draw(Graphics g, Point pCmpRelPrnt) {

		g.setColor(ColorUtil.MAGENTA);
		int xLoc = (int)this.getLocation().getX() + pCmpRelPrnt.getX() ;
		int yLoc = (int)this.getLocation().getY() + pCmpRelPrnt.getY();
		g.drawArc(xLoc, yLoc, size,size, 0, 360);		
		g.fillArc(xLoc, yLoc, size,size, 0, 360);
	}

}
