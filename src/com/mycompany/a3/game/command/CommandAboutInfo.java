package com.mycompany.a3.game.command;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.game.gameWorld.GameWorld;

public class CommandAboutInfo extends Command {
	private GameWorld gw;
	
	public CommandAboutInfo(GameWorld gw) {
		super("About Information");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent e) {
		Dialog.show("About Infornation","Name: ChanglongLi\n" + 
					"CourseName: CSC133-06 \n" + 
					"VersionNumber: 2","OK","Cancel");
	}

}
