package com.charredsoftware.governmentsimulator.legal;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Apr 8, 2014
 */
public enum Party {

	DEMOCRAT("Democrat", "D"), REPUBLICAN("Republican", "R"), INDEPENDANT("Independant", "I");
	
	public String name, abbr;
	
	private Party(String name, String abbr){
		this.abbr = abbr;
		this.name = name;
	}
	
	public static Party getPartyByString(String str){
		if(str.toLowerCase().contains("d")) return Party.DEMOCRAT;
		return Party.REPUBLICAN;
	}
	
}
