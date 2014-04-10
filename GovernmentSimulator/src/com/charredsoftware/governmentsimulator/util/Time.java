package com.charredsoftware.governmentsimulator.util;

import java.text.DateFormatSymbols;

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
		week += 1;
		if(week < 5) return;
		week = 1;
		advanceMonth();
	}
	
	public void advanceMonth(){
		month += 1;
		if(month < 13) return;
		month = 1;
		advanceYear();
	}
	
	public void advanceYear(){
		year += 1;
	}
	
	public String getDate(){
		return "Week " + week + " of " + new DateFormatSymbols().getMonths()[month-1] + ", " + year;
	}
	
}
