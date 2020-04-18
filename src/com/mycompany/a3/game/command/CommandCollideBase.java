package com.mycompany.a3.game.command;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.game.gameWorld.GameWorld;
import com.mycompany.a3.game.gameWorld.gameObject.moveAbleObject.Cyborg;

public class CommandCollideBase extends Command{
	private GameWorld gw;
	
	public CommandCollideBase(GameWorld gw) {
		super("Collide with Base");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent e) {
		TextField inputTF = new TextField();
		Command cOk = new Command("Ok");
		Command cCancel = new Command ("Cancel");
		Command[] cmds = new Command[] {cOk,cCancel};
		Command input = Dialog.show("Enter Base Number:", inputTF, cmds);
		
		if(input ==cOk) {
			gw.baseCollision(Integer.parseInt(inputTF.getText()),(Cyborg)gw.getPlayerCyborg());
		}
		
	}

}
