package com.pekalicious.starplanner.util;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

/**
 * An utility class that contains various windows methods (such as listing
 * running processes).
 * 
 * @author Panagiotis Peikidis
 */
public class WindowsUtils {
	/**
	 * Returns the list of running processes.
	 * 
	 * @return
	 */
	public static List<String> listRunningProcesses() {
		List<String> processes = new ArrayList<String>();
		try {
			String line;
			Process p = Runtime.getRuntime().exec("tasklist.exe /fo csv /nh");
			BufferedReader input = new BufferedReader(
					new InputStreamReader(p.getInputStream()));
			while ((line = input.readLine()) != null) {
				if (!line.trim().equals("")) {
					// keep only the process name
					line = line.substring(1);
					processes.add(line.substring(0, line.indexOf("\"")));
				}

			}
			input.close();
		} catch (Exception err) {
			err.printStackTrace();
		}
		return processes;
	}

	/**
	 * Tests the running list method. Displays all running processes.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> processes = listRunningProcesses();
		String result = "";

		// display the result
		Iterator<String> it = processes.iterator();
		int i = 0;
		while (it.hasNext()) {
			result += it.next() + ",";
			i++;
			if (i == 10) {
				result += "\n";
				i = 0;
			}
		}
		JOptionPane.showConfirmDialog((Component) null,
				"Running processes : " + result, "WindowsUtils",
				JOptionPane.DEFAULT_OPTION);
	}
}