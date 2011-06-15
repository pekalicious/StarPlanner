package com.pekalicious.agent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pekalicious.Logger;

public class WorldState implements Cloneable, Serializable {
	private static final long serialVersionUID = -5619778399623655804L;

	@SuppressWarnings("unchecked")
	private Map<String, WorldStateProperty> properties;

	@SuppressWarnings("unchecked")
	public WorldState() {
		properties = new HashMap<String, WorldStateProperty>();
	}

	@SuppressWarnings("unchecked")
	public WorldState(WorldState other) {
		properties = new HashMap<String, WorldStateProperty>();
		copyWorldState(other);
	}

	@SuppressWarnings("unchecked")
	public List<WorldStateProperty> getValues() {
		return new ArrayList<WorldStateProperty>(properties.values());
	}

	public boolean containsKey(String key) {
		return properties.containsKey(key);
	}

	@SuppressWarnings("unchecked")
	public void setProperty(String key, WorldStateValue value) {
		properties.put(key, new WorldStateProperty(key, value));
	}

	@SuppressWarnings("unchecked")
	public <T> WorldStateValue<T> getPropertyValue(String key) {
		if (!properties.containsKey(key))
			Logger.Debug("WorldState:\tThere is no property " + key + "!", 1);
		return properties.get(key).value;
	}
	
	@SuppressWarnings("unchecked")
	public <T> void setValue(String key, T newValue) {
		this.properties.get(key).value.setValue(newValue);
	}
	
	public void removeProperty(String key) {
		if (properties.containsKey(key)) properties.remove(key);
	}

	@SuppressWarnings("unchecked")
	public Map<String, WorldStateProperty> getProperties() {
		return properties;
	}

	public void resetWorldState() {
		properties.clear();
	}

	@SuppressWarnings("unchecked")
	public int getNumWorldStateDifferences(WorldState otherState) {
		int count = 0;
		Collection<WorldStateProperty> myProps = properties.values();
		for (WorldStateProperty myProp : myProps) {
			if (!(otherState.containsKey(myProp.key)))
				count++;
			else
				if (!(otherState.getPropertyValue(myProp.key).equals(myProp.value)))
					count++;
		}

		Collection<WorldStateProperty> otherProps = otherState.properties.values();
		for (WorldStateProperty otherProp : otherProps)
			if (!(properties.containsKey(otherProp.key)))
				count++;

		return count;
	}
	
	@SuppressWarnings("unchecked")
	public int getNumUnsatisfiedWorldStateProperties(WorldState otherState) {
		int count = 0;
		Collection<WorldStateProperty> myProps = properties.values();
		for (WorldStateProperty myProp : myProps) {
			if (!otherState.containsKey(myProp.key))
				count++;
			else if (!myProp.value.equals(otherState.getPropertyValue(myProp.key)))
				count++;
		}
		return count;
	}

	@SuppressWarnings("unchecked")
    public
	void copyWorldState(WorldState otherState) {
		resetWorldState();
		Collection<WorldStateProperty> otherProps = otherState.properties.values();
		for (WorldStateProperty prop : otherProps)
			properties.put(prop.key, prop.clone());
	}

	public WorldState clone() {
		WorldState newState = new WorldState();
		newState.copyWorldState(this);
		return newState;
	}
	
	public String toString() {
		return Logger.toString(properties, ",");
	}
}