package com.charredsoftware.governmentsimulator;

import java.util.ArrayList;

import android.graphics.Paint;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Apr 6, 2014
 */
public class FakeButton {

	public String text;
	public float x, y;
	public float height, width, minWidth, maxWidth, textSize;
	public boolean visible, subMenuOpen, isASubMenu = false;
	private static Paint paint = new Paint();
	public ArrayList<FakeButton> subMenu = new ArrayList<FakeButton>();
	
	public FakeButton(String text, float textSize, float x, float y, float minWidth, float maxWidth){
		this.text = text;
		this.textSize = textSize;
		this.x = x;
		this.y = y;
		this.minWidth = minWidth;
		this.maxWidth = maxWidth;
		calculateDimensions();
	}
	
	private void calculateDimensions(){
		paint.setTextSize(textSize);
		float textWidth = paint.measureText(text);
		width = (minWidth < 0) ?  textWidth + 4 : (textWidth > minWidth) ? textWidth : minWidth;
		if(maxWidth > 0 && maxWidth < width) width = maxWidth;
		height = textSize;
	}

	public boolean clickInRange(float xP, float yP){
		if(!visible) return false;
		if(xP < x || yP < y) return false;
		if(xP > x + width || yP > y + height) return false;
		return true;
	}

	public void addSubButton(String text, float minWidth, float maxWidth){
		int spacingHeight = (int) ((subMenu.size() == 0) ? 0 : 9);
		FakeButton subButton = new FakeButton(text, this.textSize, this.x + width + 1, y + (subMenu.size() * (height + spacingHeight)), minWidth, maxWidth);
		subButton.isASubMenu = true;
		subMenu.add(subButton);
	}
	
	public void toggleVisibility(boolean val){
		visible = val;
		if(!val){
			//subMenuOpen = false;
			//toggleSubMenu(false);
		}
	}
	
	public void toggleSubMenu(boolean val){
		for(FakeButton b : subMenu) b.toggleVisibility(val);
		subMenuOpen = val;
	}
	
}
