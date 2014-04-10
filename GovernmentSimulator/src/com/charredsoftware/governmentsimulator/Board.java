package com.charredsoftware.governmentsimulator;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.View;

import com.charredsoftware.governmentsimulator.util.Controller;
import com.charredsoftware.governmentsimulator.util.FakeButton;
import com.charredsoftware.governmentsimulator.util.GameState;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Apr 6, 2014
 */
public class Board extends View{

	private Paint paint;
	public ArrayList<FakeButton> buttons = new ArrayList<FakeButton>();
	
	public Board(Context context) {
		super(context);
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	}
	
	public void draw(){
		invalidate();
	}

	public void onDraw(Canvas canvas){
		super.onDraw(canvas);
		for(FakeButton b : buttons) b.visible = false;
		buttons = new ArrayList<FakeButton>();
		if(MainActivity.state == GameState.HOME) loadHome(canvas);
		if(MainActivity.state == GameState.MAIN_GAME) loadMainGame(canvas);
	}
	
	private void loadHome(Canvas canvas){
		canvas.drawColor(Color.WHITE);
		paint.setTextSize(56);
		paint.setColor(0xff319DA3);
		canvas.drawText("Government", 40, 66, paint);
		paint.setColor(0xffE0563A);
		canvas.drawText("Simulator", 160, 112, paint);
		
		drawButtonSet(canvas, Controller.homeButtons);
	}
	
	private void loadMainGame(Canvas canvas){
		int standardText = 24;
		paint.setColor(0xFF448844);
		paint.setStyle(Style.FILL);
		int top = getHeight() - 100;
		canvas.drawRect(0, top, getWidth(), getHeight(), paint);
		paint.setColor(0xFFFFFFFF);
		paint.setTextSize(standardText);
		canvas.drawText(MainActivity.time.getDate(), 0, top + 30, paint);
		
		//Draw top portion of screen
		paint.setTextSize(standardText);
		paint.setColor(0xFF000000);
		canvas.drawText(MainActivity.time.getDate(), 5, 30, paint);
		canvas.drawText(Controller.player.getMoneyString(), getWidth() - paint.measureText(Controller.player.getMoneyString()) - 5, 30, paint);
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
	
}
