package com.charredsoftware.governmentsimulator.stocks;

import java.util.ArrayList;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Apr 10, 2014
 */
public class StockExchange {

	//public static StockExchange UEA = new StockExchange("United Exchange of America", "UEA");
	//public static StockExchange LSE = new StockExchange("Liverpool Stock Exchange", "LSE");
	//public static StockExchange OMT = new StockExchange("Old Man Trades", "OMT");
	//public static StockExchange TEW = new StockExchange("Technology Exchange Wrapper", "TEW");
	//public static StockExchange BBPE = new StockExchange("Big Business Priority Exchange", "BBPE");
	
	public String name, symbol;
	public ArrayList<Stock> stocks = new ArrayList<Stock>();
	
	public StockExchange(String name, String symbol){
		this.name = name;
		this.symbol = symbol;
	}
	
	public void advanceWeek(){
		for(Stock s : stocks) s.advanceWeek();
	}
	
}
