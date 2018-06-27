package com.victor.importer.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;



public class ImportSchedulerSplash extends JPanel implements Runnable{

	private static final long serialVersionUID = -8839586841050548775L;
	public static JLabel secondsToNextImport;
	GridBagConstraints position;
	static int secs;
	JPanel secondsToNextImportPanel;
	
	public static JTextArea importStatusArea;
	JScrollPane statusPane;
	public ImportSchedulerSplash(){
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error setting Look & Feel "+e, "Error setting Look & Feel", JOptionPane.ERROR_MESSAGE);
		}
		secondsToNextImportPanel = new JPanel(new GridBagLayout());
		setLayout(new GridBagLayout());
		position = new GridBagConstraints();
		secondsToNextImport = new JLabel("0");
		importStatusArea = new JTextArea(20,200);
		statusPane = new JScrollPane(importStatusArea);
		statusPane.setPreferredSize(new Dimension(500, 150));
		importStatusArea.setEditable(false);
		
		position.gridx=0;
		position.gridy=0;
		secondsToNextImportPanel.add(new JLabel("Next Import in (Seconds): "),position);
		position.gridx=1;
		position.gridy=0;
		secondsToNextImportPanel.add(secondsToNextImport,position);
		
		position.gridx=0;
		position.gridy=0;
		add(secondsToNextImportPanel,position);
		position.gridx=0;
		position.gridy=1;
		add(statusPane,position);
		statusPane.setBackground(Color.WHITE);
		statusPane.setForeground(Color.WHITE);
		secondsToNextImportPanel.setBackground(Color.WHITE);
		secondsToNextImportPanel.setForeground(Color.WHITE);
		setBackground(Color.WHITE);
		setForeground(Color.WHITE);
	}
	public void run() {
		while(true){
			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(null, "Counter Interrupted: "+e, "Error setting counter!!", JOptionPane.ERROR_MESSAGE);
			}
			if(Integer.parseInt(secondsToNextImport.getText()) > 0){
				secs = (Integer.parseInt(secondsToNextImport.getText())-1);
				secondsToNextImport.setText(Integer.toString(secs));
			}
		}
	}
	
}
