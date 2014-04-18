package com.charredsoftware.governmentsimulator.people;

import java.util.HashMap;
import java.util.Map;

import com.charredsoftware.governmentsimulator.legal.Bill;
import com.charredsoftware.governmentsimulator.legal.Party;
import com.charredsoftware.governmentsimulator.legal.State;
import com.charredsoftware.governmentsimulator.util.Controller;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Apr 8, 2014
 */
public class Politician extends Person{

	public int dSupport, rSupport;
	public Map<Bill, Integer> history = new HashMap<Bill, Integer>();

	public Politician(String first, String last, int age, long money, State state, Party party) {
		super(first, last, age, money, state, party);
		if(party == Party.DEMOCRAT){
			dSupport = 50;
			rSupport = 25;
		}else{
			rSupport = 50;
			dSupport = 25;
		}
		job = Job.REPRESENTATIVE;
		Controller.politicians.add(this);
	}
	
	
	
}
