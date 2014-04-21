package com.charredsoftware.governmentsimulator.stocks;

import java.util.ArrayList;
import java.util.Random;

import android.widget.Toast;

import com.charredsoftware.governmentsimulator.MainActivity;
import com.charredsoftware.governmentsimulator.legal.Indicators;
import com.charredsoftware.governmentsimulator.legal.NationalIndicator;
import com.charredsoftware.governmentsimulator.util.Controller;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Apr 10, 2014
 */
public class Stock {

	public long price;
	public ArrayList<Long> prices = new ArrayList<Long>();
	public String name, symbol;
	public StockExchange exchange;
	public Random rand = new Random();
	public Indicators indicators;
	public int owned = 0;
	
	public Stock(StockExchange exchange, String name, String symbol, long price){
		this.name = name;
		this.symbol = symbol;
		this.exchange = exchange;
		this.price = price;
		exchange.stocks.add(this);
		indicators = new Indicators(false);
	}
	
	private void calculateNewWeek(){
		long diff = 0;
		for(NationalIndicator ind : Controller.indicators){
			int influence = indicators.getInfluence(ind);
			if(influence <= 0) continue;
			int change = MainActivity.indicators.getChange(ind);
			//Further away from ideal == bad. Negative Change < No Change < Positive Change.
			int idealModifier = (100 - Math.abs(indicators.getIdeal(ind) - MainActivity.indicators.getLevel(ind)));
			if(idealModifier <= 0) idealModifier = 1;
			int idealMultiplier = 1;
			double influenceModifier = influence / 100.00;
			double changeModifier = (change < 0) ? (rand.nextInt(2) == 0) ? -1 : -1.25 : (change == 0) ? (idealModifier < 15) ? (rand.nextInt(4) == 0) ? -1.1 : 1.1 : (rand.nextInt(2) == 0) ? -1 : 1 : (rand.nextInt(2) == 0) ? 1 : 1.15;
			
			diff += (influenceModifier * (rand.nextInt(idealModifier) * idealMultiplier) * changeModifier);
		}

		//diff = (long) ((Math.abs(diff) > 500) ? (rand.nextInt(10) < 2) ? diff * .9 : diff : diff);
		price += diff; //* rand.nextInt();
		if(price < 1) price = 1;
	}
	
	public void advanceWeek(){
		calculateNewWeek();
		if(prices.size() == 52) prices.remove(0);
		prices.add(price);
	}
	
	public long get52High(){
		if(prices.size() == 0) return 0;
		long high = prices.get(0);
		for(long l : prices){
			if(l > high) high = l;
		}
		return high;
	}
	
	public long get52Low(){
		if(prices.size() == 0) return 0;
		long low = prices.get(0);
		for(long l : prices){
			if(l < low) low = l;
		}
		return low;
	}
	
	public long weekChange(){
		if(prices.size() < 2) return 0;
		return price - prices.get(prices.size() - 2);
	}
	
	public int calculateMaxShares(){
		return (int) (Controller.player.money / price);
	}
	
	public long calculateValue(int shares){
		return price * shares;
	}
	
	public void purchase(int shares){
		if(Controller.player.money < calculateValue(shares)) return;
		Controller.player.money -= (shares * price);
		this.owned += shares;
	}
	
	public void sell(int shares){
		if(owned < shares) return;
		Controller.player.money += (shares * price);
		owned -= shares;
	}
	
}
