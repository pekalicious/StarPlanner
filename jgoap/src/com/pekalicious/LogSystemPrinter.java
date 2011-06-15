package com.pekalicious;

public class LogSystemPrinter implements LogPrinter {

	@Override
	public void printMessage(String str) {
		System.out.print(str);
	}
}
