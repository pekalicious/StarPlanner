package com.pekalicious;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogFilePrinter implements LogPrinter {

	@Override
	public void printMessage(String str) {
		try {
			FileWriter fWriter = new FileWriter(new File("starplanner.log"));
			BufferedWriter writer = new BufferedWriter(fWriter);
		    writer.append(str);
		    writer.close();
		    fWriter.close();
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}
	}

}
