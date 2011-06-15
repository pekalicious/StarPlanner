package com.pekalicious.agent;

import java.io.Serializable;

public class WorldStateValue<T> implements Cloneable, Serializable {
	private static final long serialVersionUID = -6372208672101986201L;

	protected T value;
	
	public WorldStateValue(T value) {
		this.value = value;
	}
	
	public WorldStateValue() {
		this(null);
	}
	
	public T getValue() {
		return this.value;
	}
	
	public void setValue(T newValue) {
		this.value = newValue;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorldStateValue other = (WorldStateValue) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String toString() {
		return value.toString();
	}
}
