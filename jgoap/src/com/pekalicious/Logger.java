package com.pekalicious;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Logger {
	public static int DEBUG_LEVEL = 0;
	public static LogPrinter printer;
	
	public static void Debug(String msg, int level) {
		if (level <= DEBUG_LEVEL)
			printer.printMessage(msg);
	}
	
	public static <T> void Debug(List<T> list, String delimiter, int level) {
			Debug(toString(list, delimiter), level);
	}

	public static <T> void Debug(List<T> list, int level) {
		Debug(list, ",", level);
	}
	
	public static <K, V> void Debug(Map<K, V> map, String delimiter, int level) {
		Debug(toString(map, delimiter), level);
	}
	
	public static <K, V> void Debug(Map<K, V> map, int level) {
		Debug(map, ",", level);
	}
	public static <T> String toString(List<T> list, String delimiter) {
		String str = "";
		int i = 0;
		for (T item : list)
			str += item.toString() + (++i != list.size() ? delimiter : "");
		return str;
	}
	
	public static <T> String toString(List<T> list) {
		return toString(list, ",");
	}
	
	public static <K, V> String toString(Map<K, V> map, String delimiter) {
		String str = "";
		Set<Map.Entry<K, V>> set = map.entrySet();
		int i = 0;
		for (Map.Entry<K, V> entry : set) {
			str += entry.getKey() + "=" + entry.getValue() + (++i != set.size() ? delimiter : "");
		}
		return str;
	}
	
	public static <K, V> String toString(Map<K, V> map) {
		return toString(map, ",");
	}

	public static <T> String toString(Collection<T> collection, String delimiter) {
		String str = "";
		int i = 0;
		for (T item : collection)
			str += item.toString() + (++i != collection.size() ? delimiter : "");
		return str;
	}
	
	public static <T> String toString(Collection<T> collection) {
		return toString(collection, ",");
	}
	
	public static String toString(StackTraceElement[] stackTrace) {
		String str = "";
		int counter = 0;
		for (StackTraceElement element : stackTrace) {
			for (int i = 0; i < counter; i++)
				str += " ";
			
			str += "at " + element + "\n";
			counter++;
		}
		
		return str;
	}
}
