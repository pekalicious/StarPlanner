package com.pekalicious.starplanner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class JMapDialog extends JDialog {
	private static final long serialVersionUID = 2854907917178293855L;

	public String map;

	private static final String[] maps = new String[]{ "starplanner.scm", "ICCup andromeda 1.0.scx",
		"ICCup andromeda1.0_ob.scx", "ICCup destination 1.1_ob.scx", "ICCup destination 1.1.scx",
		"iCCup heartbreak ridge1.1.scx", "iCCup heartbreak ridge1.1ob.scx", "ICCup python 1.3_ob.scm",
		"ICCup python 1.3.scx", "ICCup tau cross_ob.scx", "ICCup tau cross.scx"};
	
	public JMapDialog(JFrame frame) {
		super(frame, true);
		setTitle("Select map");
		
		final JComboBox mapCombo = new JComboBox(maps);
		JButton okButton = new JButton("OK");
		JButton cancelButton = new JButton("Cancel");
		JPanel root = new JPanel(new MigLayout("wrap 1"));
		root.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		root.add(new JLabel("Select map:"));
		root.add(mapCombo);
		root.add(okButton, "split 2, align right");
		root.add(cancelButton);
		setContentPane(root);
		pack();
		setLocationRelativeTo(frame);
		
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				map = (String)mapCombo.getSelectedItem();
				dispose();
			}
		});
	}
}
