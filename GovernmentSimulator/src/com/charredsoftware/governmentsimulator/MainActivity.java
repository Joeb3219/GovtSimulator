package com.charredsoftware.governmentsimulator;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.charredsoftware.governmentsimulator.legal.Party;
import com.charredsoftware.governmentsimulator.legal.State;
import com.charredsoftware.governmentsimulator.people.Politician;
import com.charredsoftware.governmentsimulator.util.Controller;
import com.charredsoftware.governmentsimulator.util.FakeButton;
import com.charredsoftware.governmentsimulator.util.GameState;
import com.charredsoftware.governmentsimulator.util.Time;

public class MainActivity extends Activity {

	public static GameState state = GameState.LOADING;
	private static Board board;
	private static LinearLayout secondary;
	private static EditText nameField;
	private static Spinner stateField, partyField;
	public static Time time;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);

		time = new Time();
		
		board = new Board(this);
		addContentView(board, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		Controller.reset(board);
		
		secondary = new LinearLayout(this);
		secondary.setOrientation(LinearLayout.VERTICAL);

		addContentView(secondary, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		nameField = new EditText(this);
		nameField.setHint("Alexander Hamilton");
		stateField = new Spinner(this);
		ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Controller.stateNames);
		stateField.setAdapter(stateAdapter);
		partyField = new Spinner(this);
		ArrayAdapter<String> partyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new ArrayList<String>(Arrays.asList("Democrat", "Republican")));
		partyField.setAdapter(partyAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onTouchEvent(MotionEvent event) {
	    float x = event.getX();
	    float y = event.getY();
	    
	    for(FakeButton b : board.buttons){
	    	if(!b.clickInRange(x, y)) continue;
	    	//Toast.makeText(board.getContext(), "You clicked " + b.text, Toast.LENGTH_SHORT).show();
	    	if(b.text.equals("New Game")){
	    		state = GameState.NEW_GAME;
	    		loadNewGameSecondary();
	    	}
	    	
	    	reload();
	    }
	    return true;
	}

	private void loadNewGameSecondary(){
		secondary.removeAllViews();
		secondary.addView(nameField, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		secondary.addView(stateField, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		secondary.addView(partyField, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		Button submit = new Button(this);
		submit.setText("Play");
		LayoutParams p = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		p.gravity = 0x01;
		secondary.addView(submit, p);
		submit.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Controller.player = new Politician(nameField.getText().toString(), nameField.getText().toString(), 35, 1000000L, 
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
		board.invalidate();
    	secondary.invalidate();
	}
	
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        	state = GameState.HOME;
        	secondary.removeAllViews();
			reload();
        	//finish();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
	
}
