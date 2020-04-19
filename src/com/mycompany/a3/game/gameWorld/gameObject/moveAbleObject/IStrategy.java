package com.mycompany.a3.game.gameWorld.gameObject.moveAbleObject;

import com.mycompany.a3.game.gameWorld.GameObjectCollection;

public interface IStrategy{
	
	public abstract void apply(NonPlayerCyborg npc, GameObjectCollection gameObjects);
}
