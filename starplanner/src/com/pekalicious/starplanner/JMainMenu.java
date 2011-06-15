package com.pekalicious.starplanner;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

import com.pekalicious.Logger;
import com.pekalicious.goap.PlannerGoal;
import com.pekalicious.starplanner.actions.StarAction;
import com.pekalicious.starplanner.goals.StrategicGoal;
import com.pekalicious.starplanner.managers.GoalManager;
import com.pekalicious.starplanner.tests.StarPlannerUnit;
import com.pekalicious.starplanner.util.WindowsUtils;

public class JMainMenu extends JFrame {
	private static final long serialVersionUID = -9215231435396147243L;

	private static JStarPlanner testPlanner; 
	
	public JMainMenu() {
		super("StarPlanner v1.3");

		setSize(400, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		JLabel titleLabel = new JLabel();
		titleLabel.setIcon(new ImageIcon(Main.class.getResource("res/img/logo.png")));
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
		
		JButton runStarcraft = new JButton("<html>Run StarPlanner<br><small>(in StarCraft)</small></html>");
		runStarcraft.setFont(new Font("Verdana", Font.PLAIN, 18));
		runStarcraft.setPreferredSize(new Dimension(0, 60));
		runStarcraft.setIcon(new ImageIcon(Main.class.getResource("res/img/uac.png")));
		runStarcraft.setVerticalAlignment(SwingConstants.CENTER);
		
		JButton runPlanner = new JButton("<html>Test StarPlanner<br><small>(without StarCraft)</small></html>");
		runPlanner.setFont(new Font("Verdana", Font.PLAIN, 18));
		runPlanner.setPreferredSize(new Dimension(0, 60));
		runPlanner.setIcon(new ImageIcon(Main.class.getResource("res/img/gears.png")));
		runPlanner.setVerticalAlignment(SwingConstants.CENTER);
		//runPlanner.setEnabled(false);
		
		JButton closeButton = new JButton("Close");
		
		JPanel root = new JPanel(new MigLayout("fill","","[]60[][]"));
		root.add(titleLabel, "align center,wrap");
		root.add(runStarcraft, "growx, wrap");
		root.add(runPlanner, "growx, wrap");
		root.add(closeButton, "align right");
		setContentPane(root);
		
		setLocationRelativeTo(null);
		
		runStarcraft.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (WindowsUtils.listRunningProcesses().contains("StarCraft.exe")) {
	                    JOptionPane.showMessageDialog(null, "StarCraft already running.", "Error", JOptionPane.ERROR_MESSAGE, null);
						return;
					}
					final JMapDialog mapDialog = new JMapDialog(JMainMenu.this);
					mapDialog.setVisible(true);
					if (mapDialog.map != null) {
						//dispose();
						new Thread(new Runnable() {
							public void run() {
								StarPlannerUnit unit = new StarPlannerUnit();
								try {
									unit.runTest(mapDialog.map);
								} catch (Exception e) {
								    try {
										PrintWriter writer = new PrintWriter(new File("starplanner_error.log"));
										e.printStackTrace(writer);
										writer.flush();
										writer.close();
									} catch (FileNotFoundException ex) { 
										JOptionPane.showMessageDialog(null, "Could not save log.", "Exception (Main)", JOptionPane.ERROR_MESSAGE, null);
									}
									JOptionPane.showMessageDialog(null, "Exception: " + e + " : " + e.getStackTrace()[0].toString(), "Exception (Main)", JOptionPane.ERROR_MESSAGE, null);
				                } catch (Error e) {
				                    JOptionPane.showMessageDialog(null, "Error: " + e + " : " + e.getStackTrace()[0].toString(), "Error (Main)", JOptionPane.ERROR_MESSAGE, null);
				                }
							}
						}).start();
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Exception: " + e + " : " + e.getStackTrace()[0].toString(), "Exception (Main)", JOptionPane.ERROR_MESSAGE, null);
                } catch (Error e) {
                    JOptionPane.showMessageDialog(null, "Error: " + e + " : " + e.getStackTrace()[0].toString(), "Error (Main)", JOptionPane.ERROR_MESSAGE, null);
                }
			}
		});
		
		runPlanner.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				if (testPlanner == null || !testPlanner.isDisplayable()) {
					File dataFile = new File("..\\..\\starplanner.data");
					if (!dataFile.exists()) {
						try {
							StarPlannerData newData = new StarPlannerData();
							newData.buildOrderActions = new ArrayList<StarAction>(Arrays.asList(GoalManager.getAllBuildOrderActions()));
							newData.buildOrderGoals = new ArrayList<PlannerGoal>(Arrays.asList(GoalManager.getAllBuildOrderGoals()));
							newData.strategicActions = new ArrayList<StarAction>(Arrays.asList(GoalManager.getAllStrategicActions()));
							newData.strategicGoals = new ArrayList<StrategicGoal>(Arrays.asList(GoalManager.getAllStrategicGoals()));
							FileOutputStream fOut = new FileOutputStream(dataFile.getPath());
							ObjectOutputStream objOut = new ObjectOutputStream(fOut);
							objOut.writeObject (newData);
						}catch(Exception e) {
							JOptionPane.showMessageDialog(null, "Could not create file.", "Error", JOptionPane.ERROR_MESSAGE, null);
							return;
						}
					}else{
						try {
							FileInputStream fIn = new FileInputStream(dataFile);
							ObjectInputStream objIn = new ObjectInputStream (fIn);
							@SuppressWarnings("unused")
							StarPlannerData data = (StarPlannerData)objIn.readObject();
						}catch(Exception e) {
							JOptionPane.showMessageDialog(null, "Could not load file.", "Error", JOptionPane.ERROR_MESSAGE, null);
							return;
						}
					}

	                try {
	                    testPlanner = new JStarPlanner(new StarPlanner(dataFile.getPath()));
	                    Logger.DEBUG_LEVEL = 1;
	                    Logger.printer = testPlanner;
	                    testPlanner.setVisible(true);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Exception: " + e + " : " + e.getStackTrace()[0].toString(), "Exception", JOptionPane.ERROR_MESSAGE, null);
	                } catch (Error e) {
	                    JOptionPane.showMessageDialog(null, "Error: " + e + " : " + e.getStackTrace()[0].toString(), "Error", JOptionPane.ERROR_MESSAGE, null);
	                }
				}else{
                    JOptionPane.showMessageDialog(null, "JStarPlanner already running.", "Error", JOptionPane.ERROR_MESSAGE, null);
				}
			}
		});
		
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
	}
}
