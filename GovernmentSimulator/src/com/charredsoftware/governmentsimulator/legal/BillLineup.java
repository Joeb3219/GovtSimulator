package com.charredsoftware.governmentsimulator.legal;

import java.util.ArrayList;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Apr 13, 2014
 */
public class BillLineup {

	public ArrayList<Bill> bills = new ArrayList<Bill>();
	public Indicators indicators;
	
	public BillLineup(ArrayList<Bill> bills, Indicators indicators){
		this.bills = bills;
		this.indicators = indicators;
	}
	
	public Bill getNextBill(){
		if(bills.size() == 0) return new Bill();
		
		int highestPriority = bills.get(0).calculatePriority(indicators);
		Bill highestBill = bills.get(0);
		for(Bill b : bills){
			int a = b.calculatePriority(indicators);
			if(a > highestPriority) highestPriority = a;
			highestBill = b;
		}
		
		return highestBill;
	}
	
}
