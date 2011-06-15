package com.pekalicious.starplanner;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultCaret;

import net.miginfocom.swing.MigLayout;

import org.bwapi.bridge.model.Game;

import com.pekalicious.LogPrinter;
import com.pekalicious.Logger;
import com.pekalicious.goap.PlannerGoal;
import com.pekalicious.starplanner.actions.StarAction;
import com.pekalicious.starplanner.goals.StrategicGoal;
import com.pekalicious.starplanner.managers.GoalManager;
import com.pekalicious.starplanner.managers.GoalManager.GoalType;

@SuppressWarnings("serial")
public class JStarPlanner extends JFrame implements LogPrinter {
	private JTextArea text;
	private StarPlanner starPlanner;
	private StarPlannerBridgeBot bridgeBot;
	
	boolean gameExists = false;
	
	JLabel speedLabel = new JLabel("100");
	JCheckBox loggingCheck = new JCheckBox("Enable Logging", true);
	JPanel planManager = new JPanel(new MigLayout());
	JTabbedPane rootTab = new JTabbedPane();
	
	public JStarPlanner(StarPlanner starPlanner) {
		super("JStarPlanner");
		setSize(500,600);
		if (starPlanner.game == null)
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		else
			setDefaultCloseOperation(HIDE_ON_CLOSE);
		
		this.starPlanner = starPlanner;
		gameExists = this.starPlanner.game != null;
		
		rootTab.add(getMainPanel(), "Main");
		rootTab.add(getManagerPanel(), "StarPlanner Manager");
		setContentPane(rootTab);
		
		if (!gameExists)
			setLocationRelativeTo(null);
	}
	
	public JStarPlanner(StarPlannerBridgeBot bridgeBot) {
		this(bridgeBot.getStarPlanner());
		this.bridgeBot = bridgeBot;
	}
	
	private JPanel mainPanel = null;
	private JPanel getMainPanel() {
		if (mainPanel != null)
			return mainPanel;
		
		// Top
		JCheckBox usePlannerCheck = new JCheckBox("Use StarPlanner", true);
		usePlannerCheck.setEnabled(gameExists);
		
		// Center
		text = new JTextArea();
		text.setFont(new Font("Verdana", Font.PLAIN, 14));
		text.setEditable(false);
		JScrollPane scroll = new JScrollPane(text);
		scroll.setAutoscrolls(true);

		// Bottom
		JCheckBox autoScroll = new JCheckBox("Auto Scroll", true);
		((DefaultCaret)text.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		JSpinner debugSpinner = new JSpinner();
		debugSpinner.setValue(Logger.DEBUG_LEVEL);
		debugSpinner.setPreferredSize(new Dimension(40, 20));
		JLabel gameSpeedBeforeLabel = new JLabel("Game Speed");
		gameSpeedBeforeLabel.setEnabled(gameExists);
		JSlider speedSlider = new JSlider(0, 100);
		speedSlider.setPreferredSize(new Dimension(100, 20));
		speedSlider.setValue(100);
		speedSlider.setEnabled(gameExists);
		speedLabel.setEnabled(gameExists);
		
		// Bottom bottom
		JButton clearButton = new JButton("Clear");
		String exitString = "Close";
		if (gameExists) exitString = "Hide";
		JButton exitButton = new JButton(exitString);
		JButton saveButton = new JButton("Save log");
		
		mainPanel = new JPanel(new MigLayout());
		mainPanel = new JPanel(new MigLayout());
		mainPanel.add(usePlannerCheck, "split 2");
		mainPanel.add(loggingCheck, "wrap");
		mainPanel.add(scroll, "span,dock center,wrap");
		mainPanel.add(autoScroll, "span,split 6");
		mainPanel.add(new JLabel("Debug Level:"));
		mainPanel.add(debugSpinner);
		mainPanel.add(gameSpeedBeforeLabel);
		mainPanel.add(speedSlider);
		mainPanel.add(speedLabel, "wrap");
		mainPanel.add(clearButton, "align right, split 3");
		mainPanel.add(saveButton);
		mainPanel.add(exitButton, "dock south");
		
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				text.setText("");
			}
		});
		
		speedSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent event) {
				try {
					int speed = ((JSlider)event.getSource()).getValue();
					Game.getInstance().setLocalSpeed(speed);
					//Logger.Debug("Game speed set to " + speed + "\n", 1);
					speedLabel.setText(speed + "");
				}catch(Exception e) {
					Logger.Debug("Could not change game speed: " + e.getMessage() + "\n", 1);
				}
			}
		});
		
		debugSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent event) {
				try {
					int level = Integer.parseInt(((JSpinner)event.getSource()).getValue().toString());
					Logger.DEBUG_LEVEL = level;
					Logger.Debug("Debug level set to " + level + "\n", 1);
				}catch(Exception e) {
					Logger.Debug("Could not change debug level: " + e.getMessage() + "\n", 1);
				}
			}
		});
		
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.showSaveDialog(null);
				if (fc.getSelectedFile() != null) {
					byte[] b = text.getText().getBytes();
					try {
						FileOutputStream out = new FileOutputStream(fc.getSelectedFile());
						out.write(b);
						out.close();
						JOptionPane.showMessageDialog(null, "Saved!");
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
				}
			}
		});
		
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
		
		text.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) { update(); }
			@Override
			public void insertUpdate(DocumentEvent e) { update(); }
			@Override
			public void changedUpdate(DocumentEvent e) { update(); }
			private void update() {
				if (((DefaultCaret)text.getCaret()).getUpdatePolicy() == DefaultCaret.ALWAYS_UPDATE)
					text.setCaretPosition(text.getText().length());
			}
		});
		
		autoScroll.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				try {
					if (((JCheckBox)event.getItemSelectable()).isSelected()) {
						((DefaultCaret)text.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
					}else{
						((DefaultCaret)text.getCaret()).setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
					}
				}catch(Exception e) {
					Logger.Debug("Could not change carret:" + e.getMessage() + "\n", 1);
				}
			}
		});
		
		usePlannerCheck.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				boolean usePlanner = ((JCheckBox)e.getItemSelectable()).isSelected();
				Logger.Debug("Using StarPlanner = " + usePlanner + "\n", 1);
				JStarPlanner.this.bridgeBot.setPlannerStatus(usePlanner);
			}
		});
		
		return mainPanel;
	}
	
	private JPanel managerPanel = null;
	private JPanel getManagerPanel() {
		if (managerPanel != null)
			return managerPanel;
		
		JButton exitButton = new JButton("Exit");

		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
		
		managerPanel = new JPanel(new MigLayout("fill"));
		managerPanel.add(getPlannerManager(GoalType.BuildOrder), "growx,wrap");
		managerPanel.add(getPlannerManager(GoalType.Strategic), "growx,wrap");
		managerPanel.add(exitButton, "dock south");
		return managerPanel;
	}
	
	private JPanel getPlannerManager(final GoalType type) {
		JPanel planPanel = new JPanel(new MigLayout("fill, wrap 2"));
		planPanel.setBorder(BorderFactory.createTitledBorder(type + " Manager"));
		planPanel.add(new JLabel("Goals"));
		planPanel.add(new JLabel("Actions"));
		planPanel.add(getGoalPanel(type));
		planPanel.add(getActionPanel(type));
		return planPanel;
	}
	
	private JScrollPane getActionPanel(GoalType type) {
		JPanel actionPanel = new JPanel(new MigLayout("wrap 1"));
		JScrollPane actionScroll = new JScrollPane(actionPanel);
		actionScroll.setPreferredSize(new Dimension(200, 200));
		for (StarAction action : type == GoalType.BuildOrder 
				? GoalManager.getAllBuildOrderActions() 
				: GoalManager.getAllStrategicActions()) {
			actionPanel.add(new JActionCheck(action, starPlanner.goalManager,type));
		}
		
		return actionScroll;
	}
	
	private JScrollPane getGoalPanel(GoalType type) {
		JPanel goalPanel = new JPanel(new MigLayout("wrap 1"));
		JScrollPane goalScroll = new JScrollPane(goalPanel);
		goalScroll.setPreferredSize(new Dimension(200, 200));
		if (type == GoalType.BuildOrder)
			for (PlannerGoal goal : GoalManager.getAllBuildOrderGoals())
				goalPanel.add(new JGoalCheck(goal, starPlanner.goalManager,type));
		else
			for (StrategicGoal goal : GoalManager.getAllStrategicGoals())
				goalPanel.add(new JGoalCheck(goal, starPlanner.goalManager,type));
		
		return goalScroll;
	}
	
	public void exit() {
		if (getDefaultCloseOperation() == JFrame.DISPOSE_ON_CLOSE)
			dispose();
		else if (getDefaultCloseOperation() == JFrame.HIDE_ON_CLOSE)
			setVisible(false);
	}
	
	@Override
	public void printMessage(String str) {
		if (loggingCheck.isSelected())
			text.append(str);
	}
}

@SuppressWarnings("serial")
class JActionCheck extends JCheckBox implements ItemListener {
	private StarAction action;
	private GoalManager goalManager;
	private GoalType type;
	
	public JActionCheck(StarAction action, GoalManager goalManager, GoalType type) {
		super(action.toString());
		this.action = action;
		this.goalManager = goalManager;
		this.type = type;

		setSelected(goalManager.hasAction(action));
		setEnabled(action.canBeDisabled());

		addItemListener(this);
	}

	@Override
	public void itemStateChanged(ItemEvent event) {
		if (isSelected())
			goalManager.addAction(action, type);
		else
			goalManager.removeAction(action, type);
	}
}

@SuppressWarnings("serial")
class JGoalCheck extends JCheckBox implements ItemListener {
	private PlannerGoal goal;
	private GoalManager goalManager;
	private GoalType type;
	
	public JGoalCheck(PlannerGoal goal, GoalManager goalManager, GoalType type) {
		super(goal.toString());
		this.goal = goal;
		this.goalManager = goalManager;
		this.type = type;

		setSelected(goalManager.hasGoal(goal));

		addItemListener(this);
	}

	@Override
	public void itemStateChanged(ItemEvent event) {
		if (isSelected())
			goalManager.addGoal(goal, type);
		else
			goalManager.removeGoal(goal, type);
	}
}
