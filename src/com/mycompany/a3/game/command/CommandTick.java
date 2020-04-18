package com.mycompany.a3.game.command;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.game.gameWorld.GameWorld;

public class CommandTick extends Command{
	private GameWorld gw;
	
	public CommandTick(GameWorld gw) {
		super("Tick");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent e) {
		gw.tick();
	}
}