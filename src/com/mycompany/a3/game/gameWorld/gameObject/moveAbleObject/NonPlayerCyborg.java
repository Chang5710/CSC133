package com.mycompany.a3.game.gameWorld.gameObject.moveAbleObject;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.mycompany.a3.game.gameWorld.GameObjectCollection;
import com.mycompany.a3.game.gameWorld.IDrawable;
import com.mycompany.a3.game.gameWorld.gameObject.GameObject;
import com.mycompany.a3.game.gameWorld.gameObject.fixedObject.Base;
import com.mycompany.a3.game.gameWorld.gameObject.fixedObject.EnergyStation;


public class NonPlayerCyborg extends Cyborg implements IDrawable{
	
	private IStrategy curStrategy;
	private int targetBase;
	private double SD;
	private boolean collisionFlag;

	

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
		curStrategy.apply(this, gameObjects);
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

		g.drawString(Integer.toString(getTargetBase()) , x ,y);
	}
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
		if(otherObject instanceof Drone) {
			System.out.println("NPC collided with a Drone cause 2 damage\n");
			this.setDamageLevel(this.getDamageLevel()+2);
		}
		else if(otherObject instanceof PlayerCyborg) {
			System.out.println("NPC collided with PlayerCyborg cause 4 damage\n");
			this.setDamageLevel(this.getDamageLevel()+4);
		}
		else if(otherObject instanceof NonPlayerCyborg) {
			System.out.println("NPC collided with another NPC cause 4 damage\n");
			this.setDamageLevel(this.getDamageLevel()+4);
		}
		else if(otherObject instanceof Base) {
			int BaseID = ((Base) otherObject).getBaseID();
			if(this.getLastBaseReached()+1 == BaseID) {
				int newBaseID = this.getLastBaseReached()+1;
				this.setLastBaseReached(newBaseID);
			}
		}
		else if(otherObject instanceof EnergyStation) {
			if(((EnergyStation) otherObject).getCapacity()!=0) {
				int beforeRuel = this.getEnergyLevel();
				if(this.getEnergyLevel()+((EnergyStation) otherObject).getCapacity()<this.getMaxEnergyLevel()) {
					this.setEnergyLevel(this.getEnergyLevel()+((EnergyStation) otherObject).getCapacity());
				}else {
					this.setEnergyLevel(this.getMaxEnergyLevel()); //over fuel the Energy
				}
				System.out.println("NPC collided with a EnergyStation refuel " + Math.abs(this.getEnergyLevel()-beforeRuel) + " Energy\n");
			}
		}
		this.setHeading(0);
		this.setSD(0);
		this.setSteeringDirection(0);
		this.setCollisionFlag();
		otherObject.setCollisionFlag();
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
