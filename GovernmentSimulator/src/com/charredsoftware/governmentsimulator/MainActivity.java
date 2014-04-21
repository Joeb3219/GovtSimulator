package com.charredsoftware.governmentsimulator;

import java.util.ArrayList;
import java.util.Arrays;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.charredsoftware.governmentsimulator.legal.Indicators;
import com.charredsoftware.governmentsimulator.legal.Party;
import com.charredsoftware.governmentsimulator.legal.State;
import com.charredsoftware.governmentsimulator.people.Politician;
import com.charredsoftware.governmentsimulator.stocks.Stock;
import com.charredsoftware.governmentsimulator.stocks.StockExchange;
import com.charredsoftware.governmentsimulator.stocks.StockMarket;
import com.charredsoftware.governmentsimulator.util.Controller;
import com.charredsoftware.governmentsimulator.util.GameState;
import com.charredsoftware.governmentsimulator.util.Time;

public class MainActivity extends Activity {

	public static GameState state = GameState.LOADING, previous = GameState.LOADING;
	private static Board board;
	private static LinearLayout secondary;
	private static EditText firstField, lastField;
	private static Spinner stateField, partyField;
	public static Time time;
	private static long lastClick = 0;
	public static StockMarket market;
	public static Indicators indicators;
	public static int standardHeight = -1;
	private static boolean exitDialogOpen = false;
	private static float touchDownX, touchDownY;
	private final static float TOUCH_THRESHHOLD = 10;
	private static boolean isOnClick = false;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);

		Display display = getWindowManager().getDefaultDisplay();
		try{
			Point p = new Point();
			display.getSize(p);
			standardHeight = p.y;
		}catch(Exception e){
			standardHeight = display.getHeight();
		}
		
		board = new Board(this);
		FrameLayout frame = (FrameLayout) findViewById(R.id.frame);
		frame.addView(board, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		Controller.reset(board);
		
		secondary = new LinearLayout(this);
		secondary.setOrientation(LinearLayout.VERTICAL);

		frame.addView(secondary, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		market = new StockMarket();
		market.exchanges.add(new StockExchange("United Exchange of America", "UEA"));
		indicators = new Indicators(true);
		
		if(market.getAllStocks().size() == 0) Controller.newGame(board.getContext());
		
		firstField = new EditText(this);
		firstField.setText("Alexander");
		lastField = new EditText(this);
		lastField.setText("Hamilton");
		stateField = new Spinner(this);
		ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Controller.stateNames);
		stateField.setAdapter(stateAdapter);
		partyField = new Spinner(this);
		ArrayAdapter<String> partyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new ArrayList<String>(Arrays.asList("Democrat", "Republican")));
		partyField.setAdapter(partyAdapter);
		
		frame.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				return touch(event);
			}
		});
	
		reload();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean touch(MotionEvent event) {
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
	        case MotionEvent.ACTION_DOWN:
	            touchDownX = event.getX();
	            touchDownY = event.getY();
	            isOnClick = true;
	            break;
	        case MotionEvent.ACTION_CANCEL:
	        case MotionEvent.ACTION_UP:
	            if (isOnClick && ((System.nanoTime() / 1000000) - event.getDownTime()) > 60) return executeTouchChecks(event);
	            break;
	        case MotionEvent.ACTION_MOVE:
	            if (isOnClick && (Math.abs(touchDownX - event.getX()) > TOUCH_THRESHHOLD || Math.abs(touchDownY - event.getY()) > TOUCH_THRESHHOLD)) {
	                isOnClick = false;
	            }
	            break;
	        default:
	        	break;
		}	
	    
	    return true;
	}
	
	private boolean executeTouchChecks(MotionEvent event){
		long nano = System.nanoTime();
		float x = event.getX();
		float y = event.getY();
		boolean somethingClicked = false;

		if(nano - lastClick < 250000000) return false;
	    lastClick = nano;
	    
	    if(state == GameState.STOCKS){
	    	if(y >= 60){
	    		ArrayList<Stock> stocks = market.getAllStocks();
	    		int index = (int) ((y - 60) / 75);
	    		Controller.selectedStock = stocks.get(index);
	    		state = GameState.STOCKS_SPECIFIC;
	    		reload();
	    	}
	    }
	    
	    if(board.hud.clickInRange(x, y)){
	    	if(state == GameState.MAIN_GAME){
		    	board.hud.incrementState();
		    	somethingClicked = true;
		    	reload();
	    	}
	    }
	    
	    for(FakeButton b : board.getShownButtons()){
	    	if(!b.clickInRange(x, y)) continue;
	    	if(b.text.equals("New Game")){
    			previous = state;
	    		state = GameState.NEW_GAME;
	    		loadNewGameSecondary();
	    	}else if(b.text.equals("Stocks")){
	    		previous = state;
	    		state = GameState.STOCKS;
	    	}else if(b.text.equals("Next Week")){
	    		time.advanceWeek();
	    	}else if(b.text.equals("Next Month")){
	    		for(int i = 1; i <= 4; i ++) time.advanceWeek();
	    	}else if(b.text.equals("Next Year")){
	    		for(int i = 1; i <= 4 * 12; i ++) time.advanceWeek();
	    	}else if(b.text.equals("Exit")){
	    		if(!exitDialogOpen) invokeSave();
	    	}else if(b.text.equals("Back")){
	    		state = previous;
	    	}else if(b.text.equals("Time")){
	    		board.toggleSubMenu("Time");
	    	}else if(b.text.equals("Buy Stock")){
	    		state = GameState.STOCKS_BUY;
	    	}else if(b.text.equals("Sell Stock")){
	    		state = GameState.STOCKS_SELL;
	    	}
	    	somethingClicked = true;
	    	reload();
	    }
	    
	    if(!somethingClicked){
	    	if(state == GameState.MAIN_GAME){
    			previous = state;
	    		state = GameState.PAUSED;
	    		reload();
	    	}
	    	else if(state == GameState.PAUSED){
    			previous = state;
	    		state = GameState.MAIN_GAME;
	    		reload();
	    	}
	    }
	    
	    return true;
	}

	private void loadNewGameSecondary(){
		secondary.removeAllViews();
		secondary.addView(firstField, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		secondary.addView(lastField, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		secondary.addView(stateField, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		secondary.addView(partyField, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		Button submit = new Button(this);
		submit.setText("Play");
		LayoutParams p = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		submit.setGravity(Gravity.CENTER_HORIZONTAL);
		secondary.addView(submit, p);
		submit.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Controller.newGame(board.getContext());
				Controller.player = new Politician(firstField.getText().toString(), lastField.getText().toString(), 35, 1000000L, 
						State.getStateByString(stateField.getSelectedItem().toString()), Party.getPartyByString(partyField.getSelectedItem().toString()));
				//Toast.makeText(v.getContext(), "Hello, " + Controller.player.firstName + ". You are a " + Controller.player.party.name + " from " + 
				//		Controller.player.state.name + ". You have " + Controller.player.getMoneyString(), Toast.LENGTH_LONG).show();
				state = GameState.MAIN_GAME;
				secondary.removeAllViews();
				reload();
			}
		});
	}
	
	private void reload(){
		board.changed = true;
		board.invalidate();
    	secondary.invalidate();
    	FrameLayout f = ((FrameLayout)findViewById(R.id.frame));
    	f.removeAllViews();
    	f.addView(board);
    	f.addView(secondary);
    	f.invalidate();
    	((ScrollView) findViewById(R.id.scroll)).scrollTo(0, 0);
    	
	}
	
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        	if(state == GameState.STOCKS){
        		state = GameState.MAIN_GAME;
	        	secondary.removeAllViews();
				reload();
	            return true;
        	}else if(state == GameState.STOCKS_SPECIFIC){
        		state = GameState.STOCKS;
        		Controller.selectedStock = null;
        		secondary.removeAllViews();
        		reload();
        		return true;
        	} else if(state == GameState.MAIN_GAME) {
        		if(!exitDialogOpen) invokeSave();
        		return false;
        	}
        	else if(state == GameState.PAUSED){
        		previous = state;
        		state = GameState.MAIN_GAME;
        	}else if(state == GameState.LOAD_GAME || state == GameState.NEW_GAME) {
	        	state = GameState.HOME;
	        	secondary.removeAllViews();
				reload();
	            return true;
        	}else return true;
        }
        return super.onKeyUp(keyCode, event);
    }
	
    private void invokeSave(){
    	AlertDialog.Builder dBuilder = new AlertDialog.Builder(board.getContext());
    	dBuilder.setTitle("Exit Game");
    	dBuilder
    	.setMessage("Would you like to save before exiting?")
    	.setCancelable(false)
    	.setPositiveButton("Save & Exit", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface arg0, int arg1) {
				exitDialogOpen = false;
				save();
				previous = GameState.HOME;
				state = GameState.HOME;
				reload();
			}
    	})
    	.setNegativeButton("No Save & Exit", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which) {
				exitDialogOpen = false;
				previous = GameState.HOME;
				state = GameState.HOME;
				reload();
			}
    		
    	})
    	.setNeutralButton("Don't Exit", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				exitDialogOpen = false;
				//Good game
			}
		});
    	
    	AlertDialog alert = dBuilder.create();
    	if(alert != null) alert.show();
    	exitDialogOpen = true;
    }
    
    private void save(){
    	
    }
    
}
