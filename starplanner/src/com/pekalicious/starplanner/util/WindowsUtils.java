package com.pekalicious.starplanner.util;
import java.io.*;
import java.util.*;

public class WindowsUtils {
  public static List<String> listRunningProcesses() {
    List<String> processes = new ArrayList<String>();
    try {
      String line;
      Process p = Runtime.getRuntime().exec("tasklist.exe /fo csv /nh");
      BufferedReader input = new BufferedReader
          (new InputStreamReader(p.getInputStream()));
      while ((line = input.readLine()) != null) {
          if (!line.trim().equals("")) {
              // keep only the process name
              line = line.substring(1);
              processes.add(line.substring(0, line.indexOf("\"")));
          }

      }
      input.close();
    }
    catch (Exception err) {
      err.printStackTrace();
    }
    return processes;
  }

  public static void main(String[] args){
      List<String> processes = listRunningProcesses();
      String result = "";

      // display the result 
      Iterator<String> it = processes.iterator();
      int i = 0;
      while (it.hasNext()) {
         result += it.next() +",";
         i++;
         if (i==10) {
             result += "\n";
             i = 0;
         }
      }
      msgBox("Running processes : " + result);
  }

  public static void msgBox(String msg) {
    javax.swing.JOptionPane.showConfirmDialog((java.awt.Component)
       null, msg, "WindowsUtils", javax.swing.JOptionPane.DEFAULT_OPTION);
  }
}