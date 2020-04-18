package com.mycompany.a3.game.gameWorld;

import com.mycompany.a3.game.gameWorld.gameObject.GameObject;

public interface ICollection {
	/**
	 * Add GameObject to game collection 
	 */
	void add(GameObject newObject);
	
	/**
	 * Returns iterator 
	 */
	IIterator getIterator();
}
