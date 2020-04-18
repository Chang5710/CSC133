package com.mycompany.a3.game.command;

import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.game.gameWorld.GameWorld;

public class CommandSound extends Command{
	private GameWorld gw;
	
	public CommandSound(GameWorld gw) {
		super("Sound");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent e) {
		gw.Sound(e);
	}
}
