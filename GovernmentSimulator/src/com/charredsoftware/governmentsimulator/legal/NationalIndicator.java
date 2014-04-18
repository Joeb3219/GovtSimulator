package com.charredsoftware.governmentsimulator.legal;

import com.charredsoftware.governmentsimulator.util.Controller;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Apr 11, 2014
 */
public enum NationalIndicator {

	TECHNOLOGY(40, "technology"), ENVIRONMENT(35, "environment"), EDUCATION(40, "education"), ECONOMY(20, "economy"),
	ENERGY(40, "energy"), UTILITIES(50, "utilities"), BIGBUSINESS(60, "bigbusiness"), SMALLBUSINESS(20, "smallbusiness"),
	HEALTHCARE(30, "healthcare"), COMMUNICATIONS(50, "communications"), TRANSPORTATION(45, "transportation"),
	FOREIGNRELATIONS(15, "foreignrelations"), SCIENCE(25, "science"), CIVILRIGHTS(75, "civilrights"), WAR(0, "war");
	
	public int level, previous = -1;
	public String label;
	
	private NationalIndicator(int level, String label){
		this.level = level;
		this.label = label;
		Controller.indicators.add(this);
	}

	public static NationalIndicator getIndicatorByString(String s){
		for(NationalIndicator i : Controller.indicators){
			if(i.label.toLowerCase().contains(s.toLowerCase())) return i;
		}
		return TECHNOLOGY;
	}
	
}
