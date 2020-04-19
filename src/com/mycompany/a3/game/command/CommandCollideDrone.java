package com.mycompany.a3.game.command;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.game.gameWorld.GameWorld;

public class CommandCollideDrone extends Command{
	private GameWorld gw;
	
	public CommandCollideDrone(GameWorld gw) {
		super("Collide with Drone");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent e) {
		gw.droneCollision();
	}

}
