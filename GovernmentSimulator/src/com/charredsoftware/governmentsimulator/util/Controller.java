package com.charredsoftware.governmentsimulator.util;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.charredsoftware.governmentsimulator.MainActivity;
import com.charredsoftware.governmentsimulator.legal.State;
import com.charredsoftware.governmentsimulator.people.Politician;


/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Apr 6, 2014
 */
public class Controller {

	public static int height, width;
	public static ArrayList<FakeButton> homeButtons = new ArrayList<FakeButton>();
	public static ArrayList<State> states = new ArrayList<State>();
	public static ArrayList<String> stateNames = new ArrayList<String>();
	public static Politician player;
	
	@SuppressLint("NewApi")
	public static void reset(View view){
		if(MainActivity.state != GameState.LOADING) return;
		MainActivity.state = GameState.HOME;
		
		WindowManager wm = (WindowManager) view.getContext().getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		try { 
			display.getSize(size); 
			width = size.x; 
			height = size.y; 
		} catch (NoSuchMethodError e) { 
			width = display.getWidth(); 
			height = display.getHeight(); 
		}
	
		setup();
	}
	
	public static void setup(){
		if(State.alabama instanceof State);
		homeButtons = new ArrayList<FakeButton>();
		homeButtons.add(new FakeButton("New Game", 36, -1, height / 2, 185, 0));
		homeButtons.add(new FakeButton("Load Game", 36, -1, height / 2 + 55, 185, 0));
		homeButtons.add(new FakeButton("Help", 36, -1, height / 2 + 110, 185, 0));
		homeButtons.add(new FakeButton("About", 36, -1, height / 2 + 165, 185, 0));
	}
	
	public static void addState(State state){
		states.add(state);
		stateNames.add(state.name);
	}
	
}
