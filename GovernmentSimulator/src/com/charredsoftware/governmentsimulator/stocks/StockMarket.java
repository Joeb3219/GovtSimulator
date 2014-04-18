package com.charredsoftware.governmentsimulator.stocks;

import java.util.ArrayList;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Apr 10, 2014
 */
public class StockMarket {

	public ArrayList<StockExchange> exchanges = new ArrayList<StockExchange>();
	
	public StockMarket(){
		
	}
	
	public void advanceWeek(){
		for(StockExchange exchange : exchanges) exchange.advanceWeek();
	}
	
	public ArrayList<Stock> getBestStocks(int num){
		ArrayList<Stock> stocks = new ArrayList<Stock>();
		for(StockExchange exchange : exchanges){
			for(Stock s : exchange.stocks){
				if(stocks.size() < num) stocks.add(s);
			}
		}
		return stocks;
	}
	
	public ArrayList<Stock> getAllStocks(){
		ArrayList<Stock> stocks = new ArrayList<Stock>();
		for(StockExchange e : exchanges){
			for(Stock s : e.stocks) stocks.add(s);
		}
		return stocks;
	}
	
}
