package com.pekalicious.agent;

import java.io.Serializable;

public class WorldStateProperty<T> implements Serializable {
	private static final long serialVersionUID = 8881405513791722695L;

	public String key;
	public WorldStateValue<T> value;

	public WorldStateProperty(String aKey, WorldStateValue<T> aValue) {
		key = aKey;
		value = aValue;
	}

	public WorldStateProperty() {
		this("",null);
	}

	@SuppressWarnings("unchecked")
	public WorldStateProperty<T> clone() {
		WorldStateProperty<T> newProp = new WorldStateProperty<T>();
		newProp.key = new String(key);
		newProp.value = (WorldStateValue<T>)value.clone();
		return newProp;
	}
	
	public String toString() {
		return (value.getValue() == null ? "NULL" : value.toString());
	}
}