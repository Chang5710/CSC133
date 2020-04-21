package com.mycompany.a3.game.command;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.game.gameWorld.GameWorld;

public class CommandHelpInfo extends Command{
	
	public CommandHelpInfo(GameWorld gw) {
		super("Help");
	}
	
	public void actionPerformed(ActionEvent e) {
		Dialog.show("Help","Accelerate Key: a \n"
				+ "Brake Key: b \n"
				+ "Left Turn key: l \n"
				+ "Right Turn key: r \n"
				+ "ChangeStrategies key: c \n"
				+ "Pause key: s", "Ok","Cancel");
	}
}
