package com.charredsoftware.governmentsimulator.legal;

import com.charredsoftware.governmentsimulator.util.Controller;

public class State {

	public String name, symbol;
	public int population, representatives, electoralVotes;
	private int republicanSupport, democratSupport, independentSupport;
	
	public static State alabama = new State("Alabama","AL",4800000,7,9);
	public static State alaska = new State("Alaska","AK",700000,1,3);
	public static State arizona = new State("Arizona","AZ",6400000,9,11);
	public static State arkansas = new State("Arkansas","AR",29000000,4,6);
	public static State california = new State("California","CA",37200000,53,55);
	public static State colorado = new State("Colorado","CO",5000000,7,9);
	public static State connecticut = new State("Connecticut","CT",3600000,5,7);
	public static State deleware = new State("Deleware","DE",900000,1,3);
	public static State florida = new State("Florida","FL",18800000,27,29);
	public static State georgia = new State("Georgia","GA",9700000,14,16);
	public static State hawaii = new State("Hawaii","HI",1400000,2,4);
	public static State idaho = new State("Idaho","ID",1600000,2,4);
	public static State illinois = new State("Illinois","IL",12800000,18,20);
	public static State indiana = new State("Indiana","IN",6500000,9,11);
	public static State iowa = new State("Iowa","IA",3000000,4,6);
	public static State kansas = new State("Kansas","KS",2800000,4,6);
	public static State kentucky = new State("Kentucky","KY",4300000,6,8);
	public static State lousiana = new State("Lousiana","LA",4500000,6,8);
	public static State maine = new State("Maine","ME",1300000,2,4);
	public static State maryland = new State("Maryland","MD",5800000,8,10);
	public static State massachusetts = new State("Massachusetts","MA",6600000,9,11);
	public static State michiga  = new State("Michigan","MI",9900000,14,16);
	public static State minnesota = new State("Minnesota","MN",5300000,8,10);
	public static State mississippi = new State("Mississippi","MS",3000000,4,6);
	public static State missouri = new State("Missouri","MO",6000000,8,10);
	public static State montana = new State("Montana","MT",1000000,1,3);
	public static State nebraska = new State("Nebraska","NE",1800000,3,5);
	public static State nevada = new State("Nevada","NV",2700000,3,5);
	public static State new_hampshire = new State("New Hampshire","NH",1300000,2,4);
	public static State new_jersey = new State("New Jersey","NJ",8800000,12,14);
	public static State new_mexico = new State("New Mexico","NM",2000000,3,5);
	public static State new_york = new State("New York","NY",19400000,27,29);
	public static State north_carolina = new State("North Carolina","NC",9500000,13,15);
	public static State north_dakota = new State("North Dakota","ND",700000,1,3);
	public static State ohio = new State("Ohio","OH",11500000,16,18);
	public static State oklahoma = new State("Oklahoma","OK",3800000,5,7);
	public static State oregon = new State("Oregon","OR",3900000,5,7);
	public static State pennsylvania = new State("Pennsylvania","PA",12700000,18,20);
	public static State rhode_island = new State("Rhode Island","RI",1100000,2,4);
	public static State south_carolina = new State("South Carolina","SC",4600000,7,9);
	public static State south_dakota = new State("South Dakota","SD",800000,1,3);
	public static State tennessee = new State("Tennessee","TN",6300000,9,11);
	public static State texas = new State("Texas","TX",25100000,36,38);
	public static State utah = new State("Utah","UT",2800000,4,6);
	public static State vermont = new State("Vermont","VT",600000,1,3);
	public static State virginia = new State("Virginia","VA",8000000,11,13);
	public static State washington = new State("Washington","WA",6700000,10,12);
	public static State west_virginia = new State("West Virginia","WV",1900000,3,5);
	public static State wisconsin = new State("Wisconsin","WI",5700000,8,10);
	public static State wyoming = new State("Wyoming","AL",600000,1,3);
	
	
	public State(String name, String symbol, int population, int representatives, int electoralVotes){
		this.name = name;
		this.symbol = symbol;
		this.population = population;
		this.representatives = representatives;
		this.electoralVotes = electoralVotes;
		Controller.addState(this);
	}
	
	public static State getStateByString(String str){
		for(State state : Controller.states){
			if(state.name.equalsIgnoreCase(str)) return state;
		}
		return State.new_jersey;
	}
	
}
