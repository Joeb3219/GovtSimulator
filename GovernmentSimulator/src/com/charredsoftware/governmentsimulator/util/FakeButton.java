package com.charredsoftware.governmentsimulator.util;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Apr 6, 2014
 */
public class FakeButton {

	public String text;
	public float x, y;
	public float height, width, minWidth, maxWidth, textSize;
	public boolean visible = false;
	private static Paint paint = new Paint();
	
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
	
}
