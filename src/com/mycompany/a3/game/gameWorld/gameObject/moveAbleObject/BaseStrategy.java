package com.mycompany.a3.game.gameWorld.gameObject.moveAbleObject;

import com.codename1.util.MathUtil;
import com.mycompany.a3.game.gameWorld.GameObjectCollection;
import com.mycompany.a3.game.gameWorld.IIterator;
import com.mycompany.a3.game.gameWorld.gameObject.GameObject;
import com.mycompany.a3.game.gameWorld.gameObject.fixedObject.Base;

public class BaseStrategy implements IStrategy {
	
	private int lastSD = -999;
	private double SD = -99.9;


	@Override
	public void setSteeringDirection(NonPlayerCyborg npc , GameObjectCollection gameObjects) {
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) { //find the Target Base
			GameObject obj = iter.getNext();
			if(obj instanceof Base && ((Base) obj).getBaseID() == npc.getTargetBase()) {
				double dx = Math.abs(obj.getX() - npc.getX());
				double dy = Math.abs(obj.getY() - npc.getY());
				
				double newSD; //because use integer to calculate angle off too much, I create another double SteeringDirection to calculate angle
				int newSteeringDirection;
				newSD = Math.toDegrees(MathUtil.atan(dy/dx));
				newSteeringDirection = (int)MathUtil.floor(Math.toDegrees(MathUtil.atan(dy/dx)));
				if(npc.getY() > obj.getY()) { //NPC on top of BASE
					
					//if base is on the right side 
					if(obj.getX() > npc.getX()) {
						newSD += 90;
						newSteeringDirection += 90;
					}else {//if base is on the left side
						 newSD = 90 - newSD;
						 newSteeringDirection = (int) (90 - newSD);
						 
						 newSD = 180 - newSD;
						 newSteeringDirection = 180 - newSteeringDirection;
					}
					
					
				}else if(npc.getY() < obj.getY()) {//BASE on top of NPC 
					
						
					//Base on the right side 
					if(obj.getX() > npc.getX()) {
						newSD = 90-newSD;
						newSteeringDirection = 90 - newSteeringDirection; 
					}else { //Base on left side
						
						newSD = -1*(90 - newSD);
						newSteeringDirection = -1*(90 - newSteeringDirection);
						
					}
				}else { //NPC is 90 degree above Base or Base is 90 degree above NPC
					if(npc.getY()< obj.getY()) { //Base on top of NPC
						newSD = 180;
						newSteeringDirection = 180;
					}
				}
				
				//first time
				if(lastSD == -999) {
					lastSD = newSteeringDirection;
					 SD = newSD;
				}
				else if (Math.abs(newSD - SD) <= 5) { //here is the Angular deviation, I allow 5 degree
					newSteeringDirection = 0; //newSteeringDirection should show 0, here I ignore the deviation
					npc.setHeading((int)newSD); //set the precise double SteeringDirection to NPC heading
				}else { //if the Angular deviation > 5, update both of SteeringDirection
					lastSD = newSteeringDirection;
					SD = newSD;
				}
				
				//setSteeringDirection
				npc.setSteeringDirection(newSteeringDirection);
				npc.setSD(SD);
				
				//calculate the distance
				double distance = Math.sqrt(dx*dx+dy*dy); 
				
				System.out.println("Distance: " + distance);
				
				//when the distance between NPC and base is least then 60
				if(distance <= 60) {
					double speed = Math.sqrt(distance/2); //NPC will keep update speed make sure it run pass the base
					npc.setSpeed((int)speed);
				}else {
					npc.setSpeed(30); //once NPC leave the Last Reached Base set full speed to next Base
				}
			}
		}
	}
	


}