package com.charredsoftware.governmentsimulator;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;

import com.charredsoftware.governmentsimulator.stocks.Stock;
import com.charredsoftware.governmentsimulator.util.Controller;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Apr 10, 2014
 */
public class QuickHUD {

	public int state = 1, textSize = 24, height, width;
	public float x, y;
	public final int maxState = 5;
	public boolean visible = false;
	private Paint paint;
	private int background = 0xFF6E6E6E, text = 0xffFFFFFF;
	
	public QuickHUD(float x, float y, int height, int width){
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		paint = new Paint();
	}
	
	public boolean clickInRange(float xP, float yP){
		if(!visible) return false;
		if(xP < x || yP < y) return false;
		if(xP > x + width || yP > y + height) return false;
		return true;
	}

	public void incrementState(){
		state ++;
		if(state > maxState) state = 1;
	}
	
	public void draw(Canvas canvas){
		paint.setColor(background);
		paint.setStyle(Style.FILL);
		canvas.drawRect(x, y, x + width, y + height, paint);
		paint.setColor(text);
		paint.setStyle(Style.STROKE);
		if(state == 1) drawQuickStats(canvas);
		else if(state == 2) drawQuickStocks(canvas);
		else canvas.drawText(state + "", x + 5, y + textSize, paint);
	}
	
	private void drawQuickStats(Canvas canvas){
		paint.setTextSize(textSize);
		paint.setColor(text);
		paint.setStyle(Style.STROKE);
		canvas.drawText(Controller.player.job.title + " " + Controller.player.lastName, x + 5, y + textSize, paint);
		canvas.drawText("(D) Support: " + Controller.player.dSupport + "%", x + 5, y + textSize * 2 + 5, paint);
		canvas.drawText("(R) Support: " + Controller.player.rSupport + "%", x + 5, y + textSize * 3 + 5, paint);
	}
	
	private void drawQuickStocks(Canvas canvas){
		paint.setTextSize(textSize);
		paint.setColor(text);
		paint.setStyle(Style.STROKE);
		ArrayList<Stock> stocks = MainActivity.market.getBestStocks(4);
		
		Stock s = stocks.get(0);
		paint.setColor((s.weekChange() >= 0) ? 0xFF42EB6F : 0xFFFF4242);
		canvas.drawText(s.symbol + ": " + Controller.convertLongToMoney(s.price, false), x + 5, y + 30, paint);
		s = stocks.get(1);
		paint.setColor((s.weekChange() >= 0) ? 0xFF42EB6F : 0xFFFF4242);
		canvas.drawText(s.symbol + ": " + Controller.convertLongToMoney(s.price, false), x + 5, y + 70, paint);
		s = stocks.get(2);
		paint.setColor((s.weekChange() >= 0) ? 0xFF42EB6F : 0xFFFF4242);
		canvas.drawText(s.symbol + ": " + Controller.convertLongToMoney(s.price, false), x + 200, y + 30, paint);
		s = stocks.get(3);
		paint.setColor((s.weekChange() >= 0) ? 0xFF42EB6F : 0xFFFF4242);
		canvas.drawText(s.symbol + ": " + Controller.convertLongToMoney(s.price, false), x + 200, y + 70, paint);
	}
	
}
