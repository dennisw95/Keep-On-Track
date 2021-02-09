package com.mycompany.a3;

import java.util.ArrayList;
import java.util.List;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
/**
 * 
 * @author Dennis
 * 
 */
public abstract class GameObject implements IDrawable, ICollider {

	private int size; 
	private Point location = new Point();
	private int color; 
	List<ICollider> collidedList = new ArrayList<ICollider>();

	
	public GameObject() {
		
	}

	public GameObject(float x, float y, int r, int g, int b) {
		setLocation(x, y);
		setColor(r, g, b);	
		setSize(0);
	}
	
	public abstract void draw(Graphics g, Point pCmpRelPrnt);
	


//	public boolean collidesWith(ICollider otherObject) {
//
//		GameObject otherCollider = (GameObject) otherObject;
//		if (otherCollider == this)
//			return false;
//		if (Math.abs(otherCollider.location.getX() - this.location.getX()) < (otherCollider.size / 2 + this.size / 2)
//				&& (Math.abs(otherCollider.location.getY() - this.location.getY()) < (otherCollider.size / 2
//						+ this.size / 2))) {
//			if (!collidedList.contains(otherObject)) {
//				this.handleCollision(otherObject);
//			}
//			collidedList.add(otherObject);
//			return true;
//		} else {
//			collidedList.remove(otherObject);
//			return false;
//		}
//	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public Point getLocation() {
		return new Point(this.location.getX(),this.location.getY());
	}
	public void setLocation(float x, float y) {
		this.location.setX(x);
		this.location.setY(y);
	}
	public int getColor() {
		return color;
	}
	public void setColor(int r, int g, int b) {
		color = ColorUtil.rgb(r, g, b);

	}
	
	public String toString() {
		double locationX = Math.round(location.getX()*10)/10.0;
		double locationY = Math.round(location.getY()*10)/10.0;
		String retrieval = " Location = (" + locationX + ", " + locationY + ") color = [" +
							ColorUtil.red(getColor()) + "," + ColorUtil.green(getColor()) + "," + ColorUtil.blue(getColor()) +
							"]" + " Size=" + getSize() ;
		return retrieval;
		
	}

}
