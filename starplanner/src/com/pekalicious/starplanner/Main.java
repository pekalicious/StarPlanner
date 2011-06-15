package com.pekalicious.starplanner;

import javax.swing.JFrame;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class Main extends JFrame {
	public static void main(String[] args) {
		try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }catch(Exception e) {} 
		new JMainMenu().setVisible(true);
	}
}
