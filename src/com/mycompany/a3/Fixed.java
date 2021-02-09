package com.mycompany.a3;
/**
 * 
 * @author Dennis
 * This class is the parent to Base and EnergyStation
 * 	
 */
public abstract class Fixed extends GameObject implements ISelectable{

	private boolean selected;
	
	public Fixed() {
		
	}
	
	public void setSelected(boolean value) {
		selected = value;
	}
	
	public boolean getSelected() {
		return selected;
	}
	
	public boolean isSelected() {
		Boolean b = selected;
		return b;
	}

	
	
	



}
