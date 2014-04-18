package com.charredsoftware.governmentsimulator.people;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Apr 8, 2014
 */
public enum Job {
	
	NON_POLITICAL("Civilian", 4800000), REPRESENTATIVE("Representative", 17000000), SENATOR("Senator", 17000000), PRESIDENT("President", 40000000);
	
	public String title;
	public long sallary;
	
	private Job(String title, long sallary){
		this.title = title;
		this.sallary = sallary;
	}
	
}
