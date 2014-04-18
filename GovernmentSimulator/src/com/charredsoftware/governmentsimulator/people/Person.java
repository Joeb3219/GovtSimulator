package com.charredsoftware.governmentsimulator.people;

import com.charredsoftware.governmentsimulator.legal.Party;
import com.charredsoftware.governmentsimulator.legal.State;
import com.charredsoftware.governmentsimulator.util.Controller;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Apr 8, 2014
 */
public class Person {

	public String firstName, lastName;
	public int age;
	public long money; //Money is stored in cents to avoid rounding errors
	public Party party;
	public State state;
	public Job job;
	
	public Person(String first, String last, int age, long money, State state, Party party){
		firstName = first;
		lastName = last;
		this.age = age;
		this.money = money;
		this.party = party;
		this.state = state;
		this.job = Job.NON_POLITICAL;
	}

	public void pay(){
		money += job.sallary / (12 * 4);
	}
	
	public void alterBalance(double val){
		money -= val * 100.00;
	}
	
	public double getMoney(){
		if(money >= 100000000000000000L) money = 10000000000000000L;
		return money / 100.00;
	}
	
	public String getMoneyString(){
		return "$" + Controller.simpleFormat(getMoney(), 0);
	}

}
