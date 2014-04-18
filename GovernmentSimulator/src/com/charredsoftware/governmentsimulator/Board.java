package com.charredsoftware.governmentsimulator;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.View;

import com.charredsoftware.governmentsimulator.stocks.Stock;
import com.charredsoftware.governmentsimulator.util.Controller;
import com.charredsoftware.governmentsimulator.util.GameState;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Apr 6, 2014
 */
public class Board extends View{

	private Paint paint;
	public ArrayList<FakeButton> buttons = new ArrayList<FakeButton>();
	public QuickHUD hud;
	private int height, standardHeight = -1;
	public boolean changed = true;
	
	public Board(Context context) {
		super(context);
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		standardHeight = MainActivity.standardHeight;
		height = standardHeight;
	}
	
	public void draw(){
		invalidate();
	}

	public void onDraw(Canvas canvas){
		super.onDraw(canvas);
		for(FakeButton b : buttons) b.visible = false;
		buttons = new ArrayList<FakeButton>();
		if(hud == null) hud = new QuickHUD(0, getHeight() - 125, 125, getWidth());
		hud.visible = false;
		if(MainActivity.state == GameState.HOME) loadHome(canvas);
		if(MainActivity.state == GameState.MAIN_GAME) loadMainGame(canvas);
		if(MainActivity.state == GameState.PAUSED) loadPaused(canvas);
		if(MainActivity.state == GameState.STOCKS) loadStocks(canvas);
		
		if(changed) {
			this.requestLayout();
			changed = false;
		}
	}
	
	private void loadHome(Canvas canvas){
		height = standardHeight;
		canvas.drawColor(Color.WHITE);
		paint.setTextSize(56);
		paint.setColor(0xff319DA3);
		canvas.drawText("Government", 40, 66, paint);
		paint.setColor(0xffE0563A);
		canvas.drawText("Simulator", 160, 112, paint);
		
		drawButtonSet(canvas, Controller.homeButtons);
	}
	
	private void loadMainGame(Canvas canvas){
		height = standardHeight;
		int standardText = 24;
		hud.visible = true;
		hud.draw(canvas);
		
		//Draw top portion of screen
		paint.setTextSize(standardText);
		paint.setColor(0xFF000000);
		canvas.drawText(MainActivity.time.getDate(), 5, 30, paint);
		canvas.drawText(Controller.player.getMoneyString(), getWidth() - paint.measureText(Controller.player.getMoneyString()) - 5, 30, paint);
	}
	
	private void loadPaused(Canvas canvas){
		height = standardHeight;
		loadMainGame(canvas);
		drawButtonSet(canvas, Controller.pauseButtons);
	}
	
	private void loadStocks(Canvas canvas){
		int xS;
		int yS = 0;
		int boxH = 75;
		int num = 0;
		for(Stock s : MainActivity.market.getAllStocks()){
			num ++;
			xS = 0;
			if(num % 2 == 0) paint.setColor(0xFFC4C4C4);
			else paint.setColor(0xFFA8A8A8);
			paint.setStyle(Style.FILL);
			canvas.drawRect(0, yS, getWidth(), yS + boxH, paint);
			paint.setStyle(Style.STROKE);
			paint.setColor(0xFF000000);
			paint.setTextSize(24);
			canvas.drawText(s.symbol + ": " + Controller.convertLongToMoney(s.price) + " (" + Controller.convertLongToMoney(s.weekChange()) + ")", xS + 5, yS + 30, paint);
			canvas.drawText("52-high: " + Controller.convertLongToMoney(s.get52High()) + ", 52-low: " + Controller.convertLongToMoney(s.get52Low()), xS + 5, yS + 60, paint);
			
			yS += boxH;
			height = (yS > standardHeight) ? yS : height;
		}
	}
	
	private void loadStockPage(Canvas canvas, Stock stock){
		
	}
	
	private void drawButtonSet(Canvas canvas, ArrayList<FakeButton> newButtons){
		for(FakeButton b : buttons) b.visible = false;
		buttons = newButtons;
		for(FakeButton b : buttons) drawButton(canvas, b);
	}
	
	private void drawButton(Canvas canvas, FakeButton b){
		b.visible = true;
		paint.setColor(0xFFA8A8A8);
		paint.setTextSize(b.textSize);
		float textWidth = paint.measureText(b.text);
		if(b.x < 0) b.x = (b.minWidth <= 0) ? ((getWidth() - textWidth) / 2 + 2) : ((getWidth() - b.minWidth) / 2);
		if(b.y < 0) b.y = (getHeight() - b.textSize) / 2 + 2;
		float boxWidth = (b.minWidth < 0) ?  textWidth + 4 : (textWidth > b.minWidth) ? textWidth : b.minWidth;
		paint.setStyle(Style.FILL);
		canvas.drawRect(b.x, b.y, b.x + boxWidth, b.y + b.textSize + 4, paint);
		paint.setStyle(Style.STROKE);
		paint.setColor(0xFF3A3A3B);
		canvas.drawText(b.text, b.x + ((boxWidth - textWidth) / 2), b.y + b.textSize + 1, paint);
	}
	
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		 super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		 setMeasuredDimension(widthMeasureSpec, height);
	}
	
}
