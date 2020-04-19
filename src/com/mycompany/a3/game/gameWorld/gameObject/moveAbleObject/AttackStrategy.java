package com.mycompany.a3.game.gameWorld.gameObject.moveAbleObject;

import com.codename1.util.MathUtil;
import com.mycompany.a3.game.gameWorld.GameObjectCollection;
import com.mycompany.a3.game.gameWorld.IIterator;
import com.mycompany.a3.game.gameWorld.gameObject.GameObject;
import com.mycompany.a3.game.gameWorld.gameObject.fixedObject.Base;

public class AttackStrategy implements IStrategy{
	
	private int lastSD = -999;
	private double SD = -99.9;

	@Override
	public void apply(NonPlayerCyborg npc, GameObjectCollection gameObjects) {
		setSteeringDirection(npc,gameObjects);
	}
	
	public void setSteeringDirection(NonPlayerCyborg npc, GameObjectCollection gameObjects) {
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			GameObject obj = iter.getNext();
			if(obj instanceof PlayerCyborg) {
				double dx = Math.abs(obj.getX() - npc.getX());
				double dy = Math.abs(obj.getY() - npc.getY());
				
				double newSD; //because use integer to calculate angle off too much, I create another double SteeringDirection to calculate angle
				int newSteeringDirection;
				newSD = Math.toDegrees(MathUtil.atan(dy/dx));
				newSteeringDirection = (int)MathUtil.floor(Math.toDegrees(MathUtil.atan(dy/dx)));
				if(npc.getY() > obj.getY()) { //NPC on top of Player
					
					//if Player is on the right side 
					if(obj.getX() > npc.getX()) {
						newSD += 90;
						newSteeringDirection += 90;
					}else {//if Player is on the left side
						 newSD = 90 - newSD;
						 newSteeringDirection = (int) (90 - newSD);
						 
						 newSD = 180 - newSD;
						 newSteeringDirection = 180 - newSteeringDirection;
					}
					
					
				}else if(npc.getY() < obj.getY()) {//Player on top of NPC 
					
						
					//Player on the right side 
					if(obj.getX() > npc.getX()) {
						newSD = 90-newSD;
						newSteeringDirection = 90 - newSteeringDirection; 
					}else { //Player on left side
						
						newSD = -1*(90 - newSD);
						newSteeringDirection = -1*(90 - newSteeringDirection);
						
					}
				}else { //NPC is 90 degree above Player or Player is 90 degree above NPC
					if(npc.getY()< obj.getY()) { //Player on top of NPC
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
				
				//when the distance between NPC and PlayerCyborg is least then 40
				if(distance <= 40) {
					double speed = Math.sqrt(distance/2); //keep up with PlayerCyborg within two Tick
					npc.setSpeed((int)speed);
				}else {
					npc.setSpeed(30); //set speed to Full speed
				}
				
			}
		}
		
	}
}




