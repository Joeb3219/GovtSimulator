package com.charredsoftware.governmentsimulator.legal;

import java.util.HashMap;
import java.util.Map;

import com.charredsoftware.governmentsimulator.people.Job;
import com.charredsoftware.governmentsimulator.people.Politician;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Apr 13, 2014
 */
public class VoteCard {

	public int yay, nay, abstain;
	public Map<Politician, Integer> votes = new HashMap<Politician, Integer>();
	public Job voters;
	
	public VoteCard(Job voters){
		this.voters = voters;
		yay = 0;
		nay = 0;
		abstain = 0;
	}
	
	public void vote(Politician politician, int vote){
		votes.put(politician, vote);
		if(vote == 0) nay += 1;
		else if(vote == 1) yay += 1;
		else abstain += 1;
	}
	
	public int getYayMargin(){
		int totalVoters = yay + nay + abstain;
		return (int) (totalVoters / yay * 1.00) * 100;
	}
	
	public boolean passedToOverride(){
		if(getYayMargin() >= 66) return true;
		return false;
	}
	
	public boolean passed(){
		if(voters == Job.REPRESENTATIVE || voters == Job.SENATOR){
			if(getYayMargin() > 50) return true;
		}else{
			if(yay == 1) return true;
		}
		return false;
	}
	
	public boolean votingStarted(){
		if(yay + nay + abstain > 0) return true;
		return false;
	}
	
}
