package com.mycompany.a3.game;

import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.mycompany.a3.game.command.CommandAboutInfo;
import com.mycompany.a3.game.command.CommandAccelerate;
import com.mycompany.a3.game.command.CommandBrake;
import com.mycompany.a3.game.command.CommandChangeStrategies;
import com.mycompany.a3.game.command.CommandCollideBase;
import com.mycompany.a3.game.command.CommandCollideDrone;
import com.mycompany.a3.game.command.CommandCollideEnergyStation;
import com.mycompany.a3.game.command.CommandCollideNPC;
import com.mycompany.a3.game.command.CommandExit;
import com.mycompany.a3.game.command.CommandHelpInfo;
import com.mycompany.a3.game.command.CommandLeftTurn;
import com.mycompany.a3.game.command.CommandRightTurn;
import com.mycompany.a3.game.command.CommandSound;
import com.mycompany.a3.game.command.CommandTick;
import com.mycompany.a3.game.gameWorld.GameWorld;
import com.mycompany.a3.game.gameWorld.MapView;
import com.mycompany.a3.game.gameWorld.ScoreView;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent; 
import java.lang.String;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form;

public class Game extends Form{
	private GameWorld gw;
	private MapView mv;
	private ScoreView sv;
	
	//set up Command Patterns
	private CommandAccelerate cmdAccelerate;
	private CommandBrake cmdBrake;
	private CommandLeftTurn cmdLeftTurn;
	private CommandRightTurn cmdRightTurn;
	private CommandCollideNPC cmdCollideNPC;
	private CommandCollideBase cmdCollideBase;
	private CommandCollideEnergyStation cmdCollideEnergyStation;
	private CommandCollideDrone cmdCollideDrone;
	private CommandTick cmdTick;
	private CommandExit cmdExit;
	private CommandChangeStrategies cmdChangeStrategies;
	private CommandSound cmdSound;
	private CommandAboutInfo cmdAboutInfo;
	private CommandHelpInfo cmdHelpInfo;
	
	public Game() {
		gw = GameWorld.getInstance(); //Create observable 
		gw.init();
		
		mv = new MapView(); //create observer 
		sv = new ScoreView(gw); //create observer
		
		gw.addObserver(mv); //register observer 
		gw.addObserver(sv); //register observer 
		
		//cmdAccelerate = new CommandAccelerate(gw);
		
		
		
		//Game Form 
		setLayout(new BorderLayout());
		
		
		/**
		 * West Container 
		 */
		Container westContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		
			//Components
			Button EmptyButton = new Button(" "); //make it looks more close to the simple GUI
			EmptyButton.getAllStyles().setPadding(TOP, 5);
			EmptyButton.getAllStyles().setPadding(BOTTOM, 5);
			
			Button AccelerateButton = new Button("Accelerate");
			AccelerateButton = makePretty(AccelerateButton);
			cmdAccelerate = new CommandAccelerate(gw);
			AccelerateButton.setCommand(cmdAccelerate);
			addKeyListener('a' , cmdAccelerate);
		 	
			Button LeftButton = new Button("Left");
			LeftButton = makePretty(LeftButton);
			cmdLeftTurn = new CommandLeftTurn(gw);
			LeftButton.setCommand(cmdLeftTurn);
			addKeyListener('l' , cmdLeftTurn);
		 	
			Button ChangeStrategiesButton = new Button("Change Strategies");
			ChangeStrategiesButton = makePretty(ChangeStrategiesButton);
			cmdChangeStrategies = new CommandChangeStrategies(gw);
			ChangeStrategiesButton.setCommand(cmdChangeStrategies);
			
			westContainer.add(EmptyButton);
			westContainer.add(AccelerateButton);
			westContainer.add(LeftButton);
			westContainer.add(ChangeStrategiesButton);
			
			/**
			 * Add containers to contentpane
			 */
			 add(BorderLayout.WEST , westContainer);
		 
		 
		 /**
		  * South Container
		  */
		 Container southContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
		 
		 	Button CollideNPCButton = new Button("Collide with NPC");
		 	CollideNPCButton = makePretty(CollideNPCButton);
		 	cmdCollideNPC = new CommandCollideNPC(gw);
		 	CollideNPCButton.setCommand(cmdCollideNPC);
		 	
		 	Button CollideBaseButton = new Button("Collide with Base");
		 	CollideBaseButton = makePretty(CollideBaseButton);
		 	cmdCollideBase = new CommandCollideBase(gw);
		 	CollideBaseButton.setCommand(cmdCollideBase);
		 	
		 	Button CollideEnergyStationButton = new Button("Collide with Energy Station");
		 	CollideEnergyStationButton = makePretty(CollideEnergyStationButton);
		 	cmdCollideEnergyStation = new CommandCollideEnergyStation(gw);
		 	CollideEnergyStationButton.setCommand(cmdCollideEnergyStation);
		 	addKeyListener('e' , cmdCollideEnergyStation);
		 	
		 	Button CollideDroneButton = new Button("Collide with Drone");
		 	CollideDroneButton = makePretty(CollideDroneButton);
		 	cmdCollideDrone = new CommandCollideDrone(gw);
		 	CollideDroneButton.setCommand(cmdCollideDrone);
		 	addKeyListener('g' , cmdCollideDrone);
		 	
		 	Button TickButton = new Button("Tick");
		 	TickButton = makePretty(TickButton);
		 	cmdTick = new CommandTick(gw);
		 	TickButton.setCommand(cmdTick);
		 	addKeyListener('t' , cmdTick);
		 	
		 	southContainer.add(CollideNPCButton);
		 	southContainer.add(CollideBaseButton);
		 	southContainer.add(CollideEnergyStationButton);
		 	southContainer.add(CollideDroneButton);
		 	southContainer.add(TickButton);
		 	
		 	
		 	
		 	Container wrapper = new Container(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
		 	wrapper.getAllStyles().setBorder(Border.createLineBorder(1));
		 	wrapper.add(CENTER,southContainer);
		 	
		 	add(BorderLayout.SOUTH,wrapper);
		 	
		 /**
		  * East Container
		  */
		 Container eastContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));

		 	Button BreakButton = new Button();
		 	BreakButton = makePretty(BreakButton);
		 	cmdBrake = new CommandBrake(gw);
		 	BreakButton.setCommand(cmdBrake);
		 	addKeyListener('b' , cmdBrake);
		 	
		 	Button RightButton = new Button();
		 	RightButton = makePretty(RightButton);
		 	cmdRightTurn = new CommandRightTurn(gw);
		 	RightButton.setCommand(cmdRightTurn);
		 	addKeyListener('r', cmdRightTurn);
		 	
		 	EmptyButton = new Button(" ");
		 	EmptyButton.getAllStyles().setPadding(TOP, 5);
			EmptyButton.getAllStyles().setPadding(BOTTOM, 5);
		 	eastContainer.add(EmptyButton); //make it looks more close to the simple GUI
		 	eastContainer.add(BreakButton);
		 	eastContainer.add(RightButton);
		 	
		 	
		 	add(BorderLayout.EAST,eastContainer);
		 	
		 /**
		  * Adding Title Bar
		  */
		 	
		 Toolbar titleBar = new Toolbar();
		 setToolbar(titleBar);
		 titleBar.setTitle("Sili-Challenge Game");
		 cmdHelpInfo = new CommandHelpInfo(gw); 
		 titleBar.addCommandToRightBar(cmdHelpInfo); //add help to the Right side
		 
		 Toolbar.setOnTopSideMenu(true);
		 
		 // adding Command to the SideMenu
		 titleBar.addCommandToSideMenu(cmdAccelerate);
		 
		 CheckBox SoundCB = new CheckBox();
		 cmdSound = new CommandSound(gw);
		 SoundCB.setCommand(cmdSound);
		 SoundCB.getAllStyles().setBgTransparency(255);
		 SoundCB.getUnselectedStyle().setBgColor(ColorUtil.BLUE);
		 SoundCB.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
		 titleBar.addComponentToSideMenu(SoundCB);
		 
		 cmdAboutInfo = new CommandAboutInfo(gw);
		 titleBar.addCommandToSideMenu(cmdAboutInfo);
		 
		 titleBar.addCommandToSideMenu(cmdHelpInfo);
		 cmdExit = new CommandExit(gw);
		 titleBar.addCommandToSideMenu(cmdExit);
		 
		 /**
		  * North Container 
		  */
		 Container wrapper2 = new Container(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
		 wrapper2.add(CENTER,sv);
		 add(BorderLayout.NORTH , wrapper2);
		 
		 /**
		  * Center Container 
		  */
		 add(BorderLayout.CENTER, mv);
		 
		 this.show();


	}
	

	private Button makePretty(Button obj) {
		obj.getAllStyles().setBgTransparency(255);
		obj.getUnselectedStyle().setBgColor(ColorUtil.BLUE);
		obj.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
		obj.getAllStyles().setPadding(TOP, 5);
		obj.getAllStyles().setPadding(BOTTOM, 5);
		obj.getAllStyles().setMargin(BOTTOM, 2);
		obj.getAllStyles().setMargin(LEFT,2);
		return obj;
	}
}