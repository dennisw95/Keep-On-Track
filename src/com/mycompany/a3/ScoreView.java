/**
 * 
 */
package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;

/**
 * @author Dennis
 *
 */
public class ScoreView extends Container implements Observer {

	private Label timeVal, livesVal, lastBaseVal, energyVal, damageVal, soundVal;
	public ScoreView() {
		this.setLayout(new FlowLayout(Component.CENTER));
		// make the labels
		Label time = new Label("Time: ");
		timeVal = new Label("000");
		Label lives = new Label("Lives Left: ");
		livesVal = new Label("3");
		Label lastBaseReached = new Label("Player Last Base Reached: ");
		lastBaseVal = new Label("1");
		Label energyLevel = new Label("Player Energy Level: ");
		energyVal = new Label("10000");
		Label damageLevel = new Label("Player Damage Level: ");
		damageVal = new Label("1000");
		Label sound = new Label("Sound: ");
		soundVal = new Label("On");
		
		// add styling to the labels
		time.getAllStyles().setFgColor(ColorUtil.BLUE);
		timeVal.getAllStyles().setFgColor(ColorUtil.BLUE);
		lives.getAllStyles().setFgColor(ColorUtil.BLUE);
		livesVal.getAllStyles().setFgColor(ColorUtil.BLUE);
		lastBaseReached.getAllStyles().setFgColor(ColorUtil.BLUE);
		lastBaseVal.getAllStyles().setFgColor(ColorUtil.BLUE);
		energyLevel.getAllStyles().setFgColor(ColorUtil.BLUE);
		energyVal.getAllStyles().setFgColor(ColorUtil.BLUE);
		damageLevel.getAllStyles().setFgColor(ColorUtil.BLUE);
		damageVal.getAllStyles().setFgColor(ColorUtil.BLUE);
		sound.getAllStyles().setFgColor(ColorUtil.BLUE);
		soundVal.getAllStyles().setFgColor(ColorUtil.BLUE);

		// add them to scoreView
		add(time);
		add(timeVal);
		add(lives);
		add(livesVal);
		add(lastBaseReached);
		add(lastBaseVal);
		add(energyLevel);
		add(energyVal);
		add(damageLevel);
		add(damageVal);
		add(sound);
		add(soundVal);
		
		//Add padding to all Labels
		timeVal.getAllStyles().setPadding(LEFT, 2);
		timeVal.getAllStyles().setPadding(RIGHT, 2);
			
		energyLevel.getAllStyles().setPadding(LEFT, 2);
		energyLevel.getAllStyles().setPadding(RIGHT, 2);
		
		damageVal.getAllStyles().setPadding(LEFT, 2);
		damageVal.getAllStyles().setPadding(RIGHT, 2);
	}

	@Override
	public void update (Observable o, Object arg) {
		if (o instanceof GameWorld) {
			GameWorld ob = (GameWorld) o;
			String soundValue = (ob.getSound()) ? "OFF" : "ON";
			timeVal.setText("" + ob.getClockTickCount()/1000);
			livesVal.setText("" + ob.getPlayerLives());
			lastBaseVal.setText("" + ob.findPlayerCyborg().getLastBaseReached());
			energyVal.setText("" + ob.findPlayerCyborg().getEnergyLevel() + " ");
			damageVal.setText("" + ob.findPlayerCyborg().getDamageLevel() + " ");
			soundVal.setText(soundValue);
			this.repaint();
			this.revalidate();
			this.forceRevalidate();
			this.getParent().repaint();
			this.getParent().revalidate();
			this.getParent().forceRevalidate();
			
		}
	}

}
