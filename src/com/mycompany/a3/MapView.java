/**
 * 
 */
package com.mycompany.a3;
import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;



/**
 * @author Dennis
 *
 */
public class MapView extends Container implements Observer{

	/**
	 * 
	 */
	private Game myGame;
	private boolean selected = false;
	private GameWorld gw;
	public MapView(Game myGame) {
		setLayout(new FlowLayout());
		getAllStyles().setBorder(Border.createLineBorder(3, ColorUtil.rgb(255, 0, 0)));
		this.myGame = myGame;
	}
	public MapView(Game myGame, GameWorld gw) {
		setLayout(new FlowLayout());
		getAllStyles().setBorder(Border.createLineBorder(3, ColorUtil.rgb(255, 0, 0)));
		this.myGame = myGame;
		this.gw = gw;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof GameWorld) {
			GameWorld gw = (GameWorld) o;
			IIterator it = gw.getCollection().getIterator();
			while (it.hasNext()) {
				System.out.println(it.getNext().toString());
			}
			System.out.println();
		}
		repaint();
	}

	@Override
	public void pointerPressed(int x, int y) {
		int newX = x - this.getAbsoluteX();
		int newY = y - this.getAbsoluteY();
		Point newPoint = new Point(newX, newY);
		x = x - getParent().getAbsoluteX();
		y = y - getParent().getAbsoluteY();
		Point pPtrRelPrnt = new Point(x, y);
		Point pCmpRelPrnt = new Point(getX(), getY());
		Fixed fixedOb = null;

		// use while loop instead of for loop in the lecture
		IIterator it = gw.getCollection().getIterator();
		while (it.hasNext()) {
			GameObject ob = it.getNext();
			if (ob instanceof Fixed) {
				Fixed fix = (Fixed) ob;
				if (fix.isSelected()) {
					fixedOb = fix;
				}
			}
		}
		
		boolean myFixedOb = (fixedOb == null);
		boolean myGamePaused = myGame.isPaused();
		boolean worldPost = gw.getPosition();
		if (myFixedOb && myGamePaused && worldPost) {
			gw.setPosition();
		}
		if ( fixedOb != null && myGame.isPaused() && fixedOb.isSelected() && gw.getPosition()) {
			fixedOb.setLocation(newPoint.getX(), newPoint.getY());
			gw.setPosition();
			repaint();
		}

		IIterator iter = gw.getCollection().getIterator();
		if (myGame.isPaused()) {
			while (iter.hasNext()) {
				GameObject ob = iter.getNext();
				if (ob instanceof Fixed) {
					Fixed fix = (Fixed) ob;
					if (fix.contains(pPtrRelPrnt, pCmpRelPrnt)) {						
						fix.setSelected(true);
					} else {
						fix.setSelected(false);
					}
				}
			}
		}
		repaint();
	}
	

	@Override
	public void paint(Graphics g) {
		super.paint(g);
//		int w = getWidth();
//		int h = getHeight();
		Point pCmpRelPrnt = new Point(getX(), getY());
		IIterator it = gw.getCollection().getIterator();
		while (it.hasNext()) {
			it.getNext().draw(g, pCmpRelPrnt);				
		}
			
		
	}
	
public void setSelectable(boolean select) {
		this.selected = select;		
	}

}
