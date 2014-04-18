package com.charredsoftware.governmentsimulator.legal;

import com.charredsoftware.governmentsimulator.people.Job;
import com.charredsoftware.governmentsimulator.people.Politician;
import com.charredsoftware.governmentsimulator.util.Controller;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Apr 8, 2014
 */

public class Bill {

	public int id;
	public String name, description;
	public Indicators indicators;
	public VoteCard house, senate, president;
	public boolean voted = false, alreadyShotDown = false;
	
	public Bill(){
		house = new VoteCard(Job.REPRESENTATIVE);
		senate = new VoteCard(Job.SENATOR);
		president = new VoteCard(Job.PRESIDENT);
	}
	
	public boolean hasPassed(){
		if(!voted || alreadyShotDown) return false;
		if(house.passed() && senate.passed() && president.passed()) return true;
		if(house.passedToOverride() && senate.passedToOverride() && !president.passed()) return true;
		return false;
	}
	
	public int calculatePriority(Indicators real){
		int priority = 0;
		for(NationalIndicator ind : Controller.indicators){
			int influence = indicators.getInfluence(ind);
			int netLevel = (100 - Math.abs(indicators.getIdeal(ind) - real.getLevel(ind))); //When closer to ideal, higher integer.
			priority += netLevel * (influence / 100.00);
		}
		return priority;
	}
	
	public int getVote(Politician politician){

		return 0;
	}
	
}
