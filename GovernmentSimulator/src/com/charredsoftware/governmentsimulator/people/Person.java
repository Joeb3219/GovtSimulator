package com.charredsoftware.governmentsimulator.people;

import com.charredsoftware.governmentsimulator.legal.Party;
import com.charredsoftware.governmentsimulator.legal.State;

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
	
	public Person(String first, String last, int age, int money, State state, Party party){
		firstName = first;
		lastName = last;
		this.age = age;
		this.money = money;
		this.party = party;
		this.state = state;
	}
	
	public void alterBalance(double val){
		money -= val * 100.00;
	}
	
	public double getMoney(){
		return money / 100.00;
	}
	
	public String getMoneyString(){
		return "$" + getMoney();
	}
	
}
