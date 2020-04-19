package com.mycompany.a3.game.gameWorld.gameObject;

public interface ICollider {
	boolean collidesWith (GameObject otherObject);
	
	void handleCollision (GameObject otherObject);
	
	public void setCollisionFlag();
	
	public boolean getCollisionFlag();
}
