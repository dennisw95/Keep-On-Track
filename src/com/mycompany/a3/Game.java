package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.UITimer;

/** a
 * @author Dennis
 *
 */
public class Game extends Form implements Runnable{

	private GameWorld gw;
	private MapView mv;
	private ScoreView sv;
	private boolean pauseGame = false;
	private UITimer timer;
	private int updateTime;
	private BGSound bgSound;

	AccelerateCommand accelerate;
	BrakeCommand brake;
	LeftCommand turnLeft;
	RightCommand turnRight;
	ExitCommand exit;
	ChangeStrategyCommand changeStrategy;
	SoundCommand sound;
	AboutCommand about;
	PauseCommand pause;
	PositionCommand position;
	HelpCommand help;
	
	Toolbar myToolbar;
	CustomButton sideAccelerate;
	CustomButton aboutButton;
	CustomButton exitButton;
	CustomButton accelerateButton;
	CustomButton leftButton;
	CustomButton changeStrategies;
	CustomButton brakeButton;
	CustomButton rightButton;
	CustomButton positionButton;
	CustomButton pauseButton;
	
	public Game() {
		timer = new UITimer(this);
		updateTime = 20;
		//gw = GameWorld.getGameWorld();
		gw = new GameWorld(); //observable
		mv = new MapView(this,gw); // GameWorld Observer
		sv = new ScoreView(); // GameWorld Observer
		gw.addObserver(mv);
		gw.addObserver(sv);
		myGui();
		this.show();
		gw.setWidth(mv.getWidth());
		gw.setHeight(mv.getHeight());
		gw.init();
		timer.schedule(updateTime,true, this);
	}
	
	@Override
	public void run() {
		gw.clockTick(updateTime);		
	}

	public boolean isPaused() {
		return pauseGame;
	}
	
	public void resetSelection() {
		IIterator iterator = gw.getCollection().getIterator();
		while (iterator.hasNext()) {
			GameObject ob = iterator.getNext();
			if (ob instanceof Fixed) {
				Fixed fix = (Fixed) ob;
				fix.setSelected(false);
			}
		}
		gw.updateObservers();
	}
	
	private void myGui() {

		setLayout(new BorderLayout());		
		// Top tool bar (Part of north area)
		myToolbar = new Toolbar();
		setToolbar(myToolbar);
		
		bgSound = new BGSound("borg_flyby.mp3");
		if(gw.getSound() == true) {
			bgSound.play();
		}
		
		
		//instantiate commands here
		 accelerate = new AccelerateCommand(gw);
		 brake = new BrakeCommand(gw);
		 turnLeft = new LeftCommand(gw);
		 turnRight = new RightCommand(gw);
		 exit = new ExitCommand();
		 changeStrategy = new ChangeStrategyCommand(gw);
		 sound = new SoundCommand(gw, myToolbar, bgSound);
		 about = new AboutCommand();
		 pause = new PauseCommand(this);
		 position = new PositionCommand(gw,mv);
		
		// keybindings here
		addKeyListener('a', accelerate);
		addKeyListener('b', brake);
		addKeyListener('l', turnLeft);
		addKeyListener('r', turnRight);
		
		// NORTH
		// Title
		Label title = new Label("Keep On Track Game");
		title.getAllStyles().setFgColor(ColorUtil.BLACK);
		myToolbar.setTitleComponent(title);
		// Side Menu Accelerate Button
		sideAccelerate = new CustomButton("Accelerate", accelerate);
		myToolbar.addComponentToSideMenu(sideAccelerate);
		// Side Menu Sound Check Box
		CheckBox soundCheckBox = new CheckBox("Sound");
		soundCheckBox.getAllStyles().setBgColor(ColorUtil.GRAY);
		soundCheckBox.getAllStyles().setBgTransparency(255);
		soundCheckBox.setCommand(sound);
		myToolbar.addComponentToSideMenu(soundCheckBox);
		// Side Menu About Button
		aboutButton = new CustomButton("About", about);
		myToolbar.addComponentToSideMenu(aboutButton);
		// Side Menu Exit button
		exitButton = new CustomButton("Exit",exit);
		myToolbar.addComponentToSideMenu(exitButton);
		// Help Button on right of title
		help = new HelpCommand();
		myToolbar.addCommandToRightBar(help);
		add(BorderLayout.NORTH,sv);
		this.show();
		// CENTER
		add(BorderLayout.CENTER, mv);
		
		// WEST
		// Container Designing 
		Container westContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		westContainer.getAllStyles().setPadding(Component.TOP, 50);
		westContainer.getAllStyles().setPadding(Component.BOTTOM, 50);
		westContainer.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.BLACK));
		// Accelerate Button
		accelerateButton = new CustomButton("Accelerate", accelerate);
		westContainer.add(accelerateButton);
		// Turn Left Button
		leftButton = new CustomButton("Left", turnLeft);
		westContainer.add(leftButton);
		// Change Strategies Button
		changeStrategies = new CustomButton("Change Strategies", changeStrategy);
		westContainer.add(changeStrategies);		
		this.add(BorderLayout.WEST, westContainer);
		
		// EAST container with Box Layout
		// Container Designing
		Container eastContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		eastContainer.getAllStyles().setPadding(Component.TOP, 50);
		eastContainer.getAllStyles().setPadding(Component.BOTTOM, 50);
		eastContainer.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.BLACK));		
		// Brake Button
		brakeButton = new CustomButton("Brake", brake);
		eastContainer.add(brakeButton);
		// Turn Right Button
		rightButton = new CustomButton("Right", turnRight);
		eastContainer.add(rightButton);
		this.add(BorderLayout.EAST, eastContainer);
		
		// SOUTH container with Flow Layout
		// Container designing 
		Container southContainer = new Container(new FlowLayout(Component.CENTER));
		southContainer.getAllStyles().setPadding(Component.LEFT, 10);
		southContainer.getAllStyles().setPadding(Component.RIGHT, 10);
		southContainer.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.BLACK));
		// Position Button
		positionButton = new CustomButton("Position", position);
		southContainer.add(positionButton);
		positionButton.setEnabled(false);
		//Pause Button
		pauseButton = new CustomButton("Pause", pause);
		southContainer.add(pauseButton);			
		this.add(BorderLayout.SOUTH, southContainer);
		this.show();
	}
	
	

	public void pause() {
		this.pauseGame = !pauseGame;
		if (pauseGame) {
			bgSound.pause();
			pauseButton.setText("Play");
			timer.cancel();
			accelerateButton.setEnabled(false);
			brakeButton.setEnabled(false);
			leftButton.setEnabled(false);
			rightButton.setEnabled(false);
			changeStrategies.setEnabled(false);
			myToolbar.setEnabled(false);
			positionButton.setEnabled(true);
			removeKeyListener('a', new AccelerateCommand(gw));
			removeKeyListener('b', brake);
			removeKeyListener('l', turnLeft);
			removeKeyListener('r', turnRight);
			mv.setSelectable(true);
		} else {
			bgSound.play();
			pauseButton.setText("Pause");
			timer.schedule(updateTime, true, this);
			accelerateButton.setEnabled(true);
			brakeButton.setEnabled(true);
			leftButton.setEnabled(true);
			rightButton.setEnabled(true);
			myToolbar.setEnabled(true);
			changeStrategies.setEnabled(true);
			positionButton.setEnabled(false);
			addKeyListener('a', new AccelerateCommand(gw));
			addKeyListener('b', brake);
			addKeyListener('l', turnLeft);
			addKeyListener('r', turnRight);
			mv.setSelectable(false);
			this.resetSelection();
		}
		
	}
}
