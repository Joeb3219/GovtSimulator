package com.charredsoftware.governmentsimulator.legal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.charredsoftware.governmentsimulator.util.Controller;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Apr 12, 2014
 */
public class Indicators {

	public Map<NationalIndicator, ArrayList<Integer>> indicators = new HashMap<NationalIndicator, ArrayList<Integer>>();
	
	public Indicators(boolean def){
		loadIndicators(def);
	}
	
	private void loadIndicators(boolean def){
		ArrayList<Integer> values;
		for(NationalIndicator i : Controller.indicators){
			values = new ArrayList<Integer>();
			values.add(0);
			if(def) values.add(i.level);
			else values.add(0);
			values.add(-1);
			values.add(0);
			indicators.put(i, values);
		}
	}
	
	public int getInfluence(NationalIndicator i){
		if(!indicators.containsKey(i)) return 0;
		return indicators.get(i).get(0);
	}
	
	public int getLevel(NationalIndicator i){
		if(!indicators.containsKey(i)) return 0;
		return indicators.get(i).get(1);
	}
	
	public int getPrevious(NationalIndicator i){
		if(!indicators.containsKey(i)) return 0;
		return (indicators.get(i).get(2) < 0) ? 0 : indicators.get(i).get(2);
	}
	
	public int getChange(NationalIndicator i){
		if(!indicators.containsKey(i)) return 0;
		if(indicators.get(i).get(2) < 0) return 0;
		return getLevel(i) - getPrevious(i);
	}
	
	public int getIdeal(NationalIndicator i){
		if(!indicators.containsKey(i)) return 0;
		return indicators.get(i).get(3);
	}

	public void setInfluence(NationalIndicator i, int val){
		if(!indicators.containsKey(i)) return;
		if(val > 100) val = 100;
		int level = indicators.get(i).get(1);
		int previous = indicators.get(i).get(2);
		int ideal = indicators.get(i).get(3);
		indicators.put(i, new ArrayList<Integer>(Arrays.asList(val, level, previous, ideal)));
	}
	
	public void setLevel(NationalIndicator i, int val){
		if(!indicators.containsKey(i)) return;
		if(val > 100) val = 100;
		int influence = indicators.get(i).get(0);
		int previous = indicators.get(i).get(1);
		int ideal = indicators.get(i).get(3);
		indicators.put(i, new ArrayList<Integer>(Arrays.asList(influence, val, previous, ideal)));
	}
	
	public void setIdeal(NationalIndicator i, int val){
		if(!indicators.containsKey(i)) return;
		if(val > 100) val = 100;
		int influence = indicators.get(i).get(0);
		int level = indicators.get(i).get(1);
		int previous = indicators.get(i).get(2);
		indicators.put(i, new ArrayList<Integer>(Arrays.asList(influence, level, previous, val)));
	}
	
}
