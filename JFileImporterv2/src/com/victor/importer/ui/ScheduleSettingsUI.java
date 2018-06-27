package com.victor.importer.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;


import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;

import com.toedter.calendar.JDateChooser;
import com.victor.importer.constants.ScheduleSettings;

public class ScheduleSettingsUI extends JPanel{

	private static final long serialVersionUID = 3140500311586391229L;
	JTextField repeatInterval;
	JDateChooser startDatePicker, endDatePicker;
	JPanel datePanel,delFilePanel;
	GridBagConstraints position;
	JButton startJobButton;
	JCheckBox deleteFileBox;
	
	public ScheduleSettingsUI(){
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, "Error setting Look & Feel "+e2, "Error setting Look & Feel", JOptionPane.ERROR_MESSAGE);
		}
		deleteFileBox = new JCheckBox("Delete the Imported File");
		setLayout(new GridBagLayout());
		ImageIcon icon = new ImageIcon(ScheduleSettingsUI.class.getResource("/config/mut3.png"));
		startJobButton = new JButton(icon);
		delFilePanel = new JPanel(new GridBagLayout());
		datePanel = new JPanel(new GridBagLayout());
		position = new GridBagConstraints();
		repeatInterval = new JTextField();
		startDatePicker = new JDateChooser();
		endDatePicker = new JDateChooser();
		startDatePicker.setPreferredSize(new Dimension(200,25));
		endDatePicker.setPreferredSize(new Dimension(200,25));
		repeatInterval.setPreferredSize(new Dimension(200,25));
		repeatInterval.setText("(Enter Repeat Interval in Seconds)");
		repeatInterval.setHorizontalAlignment(JTextField.RIGHT);
		repeatInterval.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				if(repeatInterval.getText().trim().equals("")){
					repeatInterval.setText("(Enter Repeat Interval in Seconds)");
				}	
			}

			@Override
			public void focusGained(FocusEvent e) {
				if(repeatInterval.getText().equals("(Enter Repeat Interval in Seconds)")){
					repeatInterval.setText("");
				}

			}
		});
		startJobButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{  
					ScheduleSettings.setJobRunDate(new Date());
					(new ImportScheduler()).scheduleImport("ImportJob",Scheduler.DEFAULT_GROUP,"ImportTrigger",
							startDatePicker.getDate(),endDatePicker.getDate(),SimpleTrigger.REPEAT_INDEFINITELY, Long.parseLong(repeatInterval.getText()));  
					startJobButton.setEnabled(false);
					repeatInterval.setEditable(false);
					endDatePicker.setEnabled(false);
					ScheduleSettings.setStartDate(startDatePicker.getDate());
					ScheduleSettings.setEndDate(endDatePicker.getDate());
					ScheduleSettings.setIntervalInSecs(Long.parseLong(repeatInterval.getText()));
					if(deleteFileBox.isSelected()){
						ScheduleSettings.setDeleteFileAfterImport(true);
					}else{
						ScheduleSettings.setDeleteFileAfterImport(false);
					}
					
					ImportSchedulerSplash.secondsToNextImport.setText(Integer.toString((int)ScheduleSettings.getIntervalInSecs()));
				
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, e1, "Error in Scheduling Job", JOptionPane.ERROR_MESSAGE);
				} 
				
			}
		});
		position.gridx=0;
		position.gridy=0;
		datePanel.add(new JLabel("Start Date  "), position);
		position.gridx=0;
		position.gridy=1;
		datePanel.add(new JLabel("End Date"), position);
		position.gridx=1;
		position.gridy=0;
		datePanel.add(startDatePicker, position);
		position.gridx=1;
		position.gridy=1;
		datePanel.add(endDatePicker, position);
		
		position.gridx=0;
		position.gridy=0;
		add(datePanel, position);
		position.gridx=0;
		position.gridy=1;
		add(repeatInterval, position);
		position.gridx=0;
		position.gridy=2;
		add(deleteFileBox, position);
		position.gridx=0;
		position.gridy=3;
		add(startJobButton, position);
		
		//setting colors
		setForeground(Color.WHITE);
		setBackground(Color.WHITE);		 
		deleteFileBox.setBackground(Color.WHITE);	
		datePanel.setForeground(Color.WHITE);
		datePanel.setBackground(Color.WHITE);	
		repeatInterval.setBackground(Color.WHITE);
		startDatePicker.setBackground(Color.WHITE);
		endDatePicker.setBackground(Color.WHITE);
	}
}
