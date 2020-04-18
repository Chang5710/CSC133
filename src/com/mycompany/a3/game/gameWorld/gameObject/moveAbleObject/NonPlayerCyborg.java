package com.mycompany.a3.game.gameWorld.gameObject.moveAbleObject;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.mycompany.a3.game.gameWorld.GameObjectCollection;
import com.mycompany.a3.game.gameWorld.IDrawable;

public class NonPlayerCyborg extends Cyborg implements IDrawable{
	
	private IStrategy curStrategy;
	private int targetBase;
	private double SD;

	

	public NonPlayerCyborg(double initialX, double initialY) {
		super(ColorUtil.rgb(0, 191, 255), 30, 0, initialX, initialY); //Color,speed,heading(90 degree to the northward),xLocation,yLocation
		this.setLife(3);
		this.setLastBaseReached(0);
		this.setDamageLevel(0);
		this.setEnergyLevel(40);
		this.setSize(40);
		this.setMaxDamageLevel(80);
		this.setMaxEnergyLevel(80);
		this.setSteeringDirection(0);
		this.targetBase=this.getLastBaseReached()+1;
	}
	
	//getter and setter
	public int getTargetBase() {
		return targetBase;
	}

	public void setTargetBase(int targetBase) {
		this.targetBase = targetBase;
	}
	
	public double getSD() {
		return SD;
	}

	public void setSD(double sD) {
		SD = sD;
	}

	public IStrategy getCurStrategy() {
		return curStrategy;
	}

	//setStrategy and invokeStrategy
	public void setStrategy(IStrategy s) {
		curStrategy = s;
	}
	
	public void invokeStrategy(GameObjectCollection gameObjects) {
		curStrategy.setSteeringDirection(this, gameObjects);
	}
	
	public String toStringCurStrategy() {
		String Strategy;
		if(this.getCurStrategy() instanceof BaseStrategy) {
			Strategy = "BaseStrategy";
		}else {
			Strategy = "AttackStrategy";
		}
		return Strategy;
	}
	
	public String toString() {
		return (
				"NonPlayerCyborg: loc=" + Math.round(this.getX()*10)/10 + "," + Math.round(this.getY()*10)/10 +
				" color=" + this.getColorToString() + " heading=" + this.getHeading() + 
				" speed=" + this.getSpeed() + " TargetBase=" + this.getTargetBase()+ " LastBase=" + this.getLastBaseReached() +"\n       size=" + this.getSize() + 
				" maxSpeed=" +this.getMaximumSpeed() + "  steeringDegree="+
				" energyLevel=" + this.getEnergyLevel() + " damageLevel=" + this.getDamageLevel() +
				" currStrategy=" + 	toStringCurStrategy()
				);
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		g.setColor(ColorUtil.BLACK);
		int x = (int)this.getX() + (int)pCmpRelPrnt.getX();
		int y = (int)this.getY() + (int)pCmpRelPrnt.getY();
		g.drawRect(x, y, 50, 50);
	}


	
}
