package com.mycompany.a3.game.gameWorld;

import com.mycompany.a3.game.gameWorld.gameObject.GameObject;

public interface IIterator {
	public boolean hasNext();
	public GameObject getNext();
	
}
