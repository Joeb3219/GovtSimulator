package com.charredsoftware.governmentsimulator.util;

import java.text.DateFormatSymbols;

import com.charredsoftware.governmentsimulator.MainActivity;
import com.charredsoftware.governmentsimulator.legal.NationalIndicator;
import com.charredsoftware.governmentsimulator.people.Politician;

public class Time {

	public int week, month, year;
	
	public Time(int week, int month, int year){
		this.week = week;
		this.month = month;
		this.year = year;
	}
	
	public Time(){
		this.week = 1;
		this.month = 1;
		this.year = 2024;
	}
	
	public void advanceWeek(){
		MainActivity.indicators.setLevel(NationalIndicator.TECHNOLOGY, MainActivity.indicators.getLevel(NationalIndicator.TECHNOLOGY) + 1);
		for(Politician p : Controller.politicians) p.pay();
		MainActivity.market.advanceWeek();
		week += 1;
		if(week < 5) return;
		week = 1;
		advanceMonth();
	}
	
	private void advanceMonth(){
		month += 1;
		if(month < 13) return;
		month = 1;
		advanceYear();
	}
	
	private void advanceYear(){
		if(year > 2125) return;
		year += 1;
	}
	
	public String getDate(){
		return "Week " + week + " of " + new DateFormatSymbols().getMonths()[month-1] + ", " + year;
	}
	
}
