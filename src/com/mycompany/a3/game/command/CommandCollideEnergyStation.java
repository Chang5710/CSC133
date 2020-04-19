package com.mycompany.a3.game.command;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.game.gameWorld.GameWorld;

public class CommandCollideEnergyStation extends Command{
	private GameWorld gw;
	
	public CommandCollideEnergyStation(GameWorld gw) {
		super("Collide with Energy Station");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent e) {
		gw.energyStationCollision();
	}

}
