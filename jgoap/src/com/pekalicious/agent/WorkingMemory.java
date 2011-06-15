package com.pekalicious.agent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class WorkingMemory {
	private List<WorkingMemoryFact> facts;
	
	public WorkingMemory() {
		this.facts = new ArrayList<WorkingMemoryFact>();
	}

	public void addFact(WorkingMemoryFact fact) {
		facts.add(fact);
	}
	
    public WorkingMemoryFact[] getFacts(int type, Comparator<WorkingMemoryFact> sortBy) {
    	List<WorkingMemoryFact> validFactsList = new ArrayList<WorkingMemoryFact>();
    	for (WorkingMemoryFact fact : this.facts)
    		if (fact.getType() == type)
    			validFactsList.add(fact);
    	
    	WorkingMemoryFact[] validFactsArrray = new WorkingMemoryFact[validFactsList.size()];
    	for (int i = 0; i < validFactsList.size(); i++)
    		validFactsArrray[i] = validFactsList.get(i);
    	
    	if (sortBy != null)
    		Arrays.sort(validFactsArrray, sortBy);
    	
        return validFactsArrray;
    }
    
    public void clearFactsByType(int type) {
    	List<WorkingMemoryFact> factsToRemove = new ArrayList<WorkingMemoryFact>();
    	for (WorkingMemoryFact fact : this.facts)
    		if (fact.getType() == type)
    			factsToRemove.add(fact);
    	
    	for (WorkingMemoryFact fact : factsToRemove)
    		this.facts.remove(fact);
    }

	public void removeFact(WorkingMemoryFact fact) {
		this.facts.remove(fact);
	}
}
