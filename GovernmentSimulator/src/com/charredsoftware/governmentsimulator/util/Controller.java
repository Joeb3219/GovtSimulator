package com.charredsoftware.governmentsimulator.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.charredsoftware.governmentsimulator.FakeButton;
import com.charredsoftware.governmentsimulator.MainActivity;
import com.charredsoftware.governmentsimulator.legal.NationalIndicator;
import com.charredsoftware.governmentsimulator.legal.State;
import com.charredsoftware.governmentsimulator.people.Politician;
import com.charredsoftware.governmentsimulator.stocks.Stock;


/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Apr 6, 2014
 */
public class Controller {

	public static int height, width;
	public static ArrayList<FakeButton> homeButtons = new ArrayList<FakeButton>();
	public static ArrayList<FakeButton> pauseButtons = new ArrayList<FakeButton>();
	public static ArrayList<FakeButton> stockMenuButtons = new ArrayList<FakeButton>();
	public static ArrayList<State> states = new ArrayList<State>();
	public static ArrayList<String> stateNames = new ArrayList<String>();
	public static ArrayList<Politician> politicians = new ArrayList<Politician>();
	public static ArrayList<NationalIndicator> indicators = new ArrayList<NationalIndicator>();
	public static Politician player;
	
	@SuppressLint("NewApi")
	public static void reset(View view){
		if(MainActivity.state != GameState.LOADING) return;
		MainActivity.state = GameState.HOME;
		
		WindowManager wm = (WindowManager) view.getContext().getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		try { 
			Point size = new Point();
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
		if(NationalIndicator.BIGBUSINESS instanceof NationalIndicator);
		homeButtons = new ArrayList<FakeButton>();
		homeButtons.add(new FakeButton("New Game", 36, -1, height / 2, 185, 0));
		homeButtons.add(new FakeButton("Load Game", 36, -1, height / 2 + 55, 185, 0));
		homeButtons.add(new FakeButton("Help", 36, -1, height / 2 + 110, 185, 0));
		homeButtons.add(new FakeButton("About", 36, -1, height / 2 + 165, 185, 0));
		
		pauseButtons = new ArrayList<FakeButton>();
		pauseButtons.add(new FakeButton("Stocks", 36, 0, 40, 185, 0));
		
		FakeButton time = new FakeButton("Time", 36, 0, 0 + 85, 185, 0);
		pauseButtons.add(time);
		time.addSubButton("Next Week", 185, 0);
		time.addSubButton("Next Month", 185, 0);
		time.addSubButton("Next Year", 185, 0);
		
		pauseButtons.add(new FakeButton("Exit", 36, 0, 0 + 130, 185, 0));
		
		stockMenuButtons = new ArrayList<FakeButton>();
		stockMenuButtons.add(new FakeButton("Back", 36, 10, 10, 185, 0));
		stockMenuButtons.add(new FakeButton("Next Week", 36, width - 195, 10, 185, 0));
		
	}
	
	public static void addState(State state){
		states.add(state);
		stateNames.add(state.name);
	}
	
	public static String convertLongToMoney(long l){
		DecimalFormat f = new DecimalFormat("#,##0.00");
		String sign = "+";
		if(l < 0) sign = "-";
		if(l == 0) sign = "";
		return sign + "$" + f.format(Math.abs(l / 100.00));
	}
	
	//simpleFormat code borrowed from StackOverflow user @Elijah Saounkine
	
	private static char[] c = new char[]{'k', 'm', 'b', 't'};

	public static String simpleFormat(double n, int iteration) {
	    double d = ((long) n / 100) / 10.0;
	    boolean isRound = (d * 10) %10 == 0;
	    return (d < 1000?
	        ((d > 99.9 || isRound || (!isRound && d > 9.99)? 
	         (int) d * 10 / 10 : d + ""
	         ) + "" + c[iteration]) 
	        : simpleFormat(d, iteration+1));

	}
	
	public static void newGame(Context context){
		MainActivity.market.exchanges.get(0).stocks = new ArrayList<Stock>();
		politicians = new ArrayList<Politician>();
		loadStocks(context);
		loadPoliticians(context);
		MainActivity.time = new Time();
	}
	
	public static void loadStocks(Context context){
		AssetManager assets = context.getAssets();
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = null;
		try {
			xmlFile = getFileFromStream(context, "stocks", assets.open("default/stocks.cgf"));
			
		try {
			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			
			List<Element> list = rootNode.getChildren("stock");
			for (int i = 0; i < list.size(); i++) {				
				Element ele = (Element) list.get(i);
				Stock stock = new Stock(MainActivity.market.exchanges.get(0), 
						ele.getChildText("name"), ele.getChildText("symbol"), Long.parseLong(ele.getChildText("price")));
				
				List<Element> factors = ele.getChild("factors").getChildren("factor");
				for(int a = 0; a < factors.size(); a ++){
					NationalIndicator ind = NationalIndicator.getIndicatorByString(factors.get(a).getAttributeValue("name"));
					stock.indicators.setInfluence(ind, Integer.parseInt(factors.get(a).getAttributeValue("influence")));
					stock.indicators.setLevel(ind, Integer.parseInt(factors.get(a).getAttributeValue("ideal")));
				}
			}
		  } catch (IOException e) {e.printStackTrace();} catch (JDOMException e) {e.printStackTrace();  }} catch (IOException e1) {
			e1.printStackTrace();
		}finally{
			if(xmlFile != null) xmlFile.delete();
		}
	}
	
	public static void loadPoliticians(Context context){
		
	}
	
	private static File getFileFromStream(Context context, String prefix, InputStream in){
		File file = null;
		try{
			file = File.createTempFile(prefix, ".cgf", context.getCacheDir());
			file.deleteOnExit();
			OutputStream os = new FileOutputStream(file);
            
            byte[] buffer = new byte[1024];
            int bytesRead;
            while((bytesRead = in.read(buffer)) !=-1){
                os.write(buffer, 0, bytesRead);
            }
            in.close();
            os.flush();
            os.close();
		}catch(Exception e){e.printStackTrace();}
		if(file == null) file = new File("p");
		return file;
	}
	
}
