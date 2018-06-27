package com.victor.importer.ui;

import java.awt.Color;
import java.awt.Dimension;

import java.awt.GridBagLayout;


import javax.swing.JOptionPane;

import javax.swing.UIManager;

import com.victor.importer.container.FrameContainer;

public class AdvancedSettingsUI extends FrameContainer{

	private static final long serialVersionUID = -3118333890566567608L;
	

	public AdvancedSettingsUI(){
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "Error setting Look & Feel "+e1, "Error setting Look & Feel", JOptionPane.ERROR_MESSAGE);
		} 
		this.setLayout(new GridBagLayout());
		
		preSessionCommandScroller.setPreferredSize(new Dimension(200,150));
		postSessionCommandScroller.setPreferredSize(new Dimension(200, 150));
		position.gridx=0;
		position.gridy=0;
		commandPanel.add(preSessionCommandLabel, position);
		position.gridx=1;
		position.gridy=0;
		commandPanel.add(preSessionCommandChooser, position);
		position.gridx=2;
		position.gridy=0;
		commandPanel.add(preSessionCommandScroller, position);
		position.gridx=0;
		position.gridy=1;
		commandPanel.add(postSessionCommandLabel, position);
		position.gridx=1;
		position.gridy=1;
		commandPanel.add(postSessionCommandChooser, position);
		position.gridx=2;
		position.gridy=1;
		commandPanel.add(postSessionCommandScroller, position);
		
		position.gridx=0;
		position.gridy=0;
		add(commandPanel, position);
		position.gridx=0;
		position.gridy=1;
		add(continueImportOnPreCmdFailureBox, position);
		position.gridx=0;
		position.gridy=2;
		add(truncateOverflowingValuesBox, position);
		
		
		setForeground(Color.WHITE);
		setBackground(Color.WHITE);
	}
}
