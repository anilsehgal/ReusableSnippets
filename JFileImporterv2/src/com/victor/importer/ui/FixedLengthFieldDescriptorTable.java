package com.victor.importer.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import javax.swing.table.DefaultTableModel;

public class FixedLengthFieldDescriptorTable extends JPanel{

	private static final long serialVersionUID = 3307183636637767749L;
	private int[] fields;
	private Object[] fieldNames;
	private boolean isFinalized;
	JTable mainTable; 
	JPanel buttons;
	JTextArea defaultFileArea;
	JButton addFieldButton,finalizeButton,removeFieldButton, modifyButton;
	JScrollPane scroller;
	DefaultTableModel model;
	GridBagConstraints position;
	static int index;
	

	Object[] columns = {"FIELD","LENGTH"};
	
	
	
	public int[] getFields() {
		return fields;
	}
	public void setFields(int[] fields) {
		this.fields = fields;
	}
	public Object[] getFieldNames() {
		return fieldNames;
	}
	public void setFieldNames(Object[] fieldNames) {
		this.fieldNames = fieldNames;
	}
	
	public boolean isFinalized() {
		return isFinalized;
	}
	public void setFinalized(boolean isFinalized) {
		this.isFinalized = isFinalized;
	}
	public FixedLengthFieldDescriptorTable(){
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "Error setting Look & Feel "+e1, "Error setting Look & Feel", JOptionPane.ERROR_MESSAGE);
		}
		setLayout(new GridBagLayout());
		position = new GridBagConstraints();
		buttons = new JPanel(new GridBagLayout());
		model = new DefaultTableModel(new Object[0][2],columns);
		mainTable = new JTable(model);
		scroller = new JScrollPane(mainTable);
		defaultFileArea = new JTextArea();
		
		scroller.setPreferredSize(new Dimension(200,100));
		finalizeButton = new JButton("FINALIZE");
		finalizeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(model!=null && model.getRowCount() > 0){	
					try{
						for(int i=0;i < model.getRowCount();i++){
							model.setValueAt(i, i, 0);
						}
						
						Object[] fieldName = new Object[model.getRowCount()];
						int[] fieldSizes = new int[model.getRowCount()];
						for(int i=0;i < model.getRowCount();i++){
							fieldName[i]=model.getValueAt(i, 0);
							fieldSizes[i]=Integer.parseInt((String) model.getValueAt(i, 1));
						}
						setFieldNames(fieldName);
						setFields(fieldSizes);
						setFinalized(true);
						finalizeButton.setEnabled(false);
					}catch(Exception e){
						JOptionPane.showMessageDialog(finalizeButton, e, "Error in Finalising Fields", JOptionPane.ERROR_MESSAGE);
					}
				}else if(model!=null && model.getRowCount() == 0){
					JOptionPane.showMessageDialog(finalizeButton, "No Fields Described!!", "Error in Finalising Fields", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		addFieldButton = new JButton("ADD FIELD");
		addFieldButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				finalizeButton.setEnabled(true);
				setFinalized(false);
				Object[] row = {index,""};
				model.addRow(row);
				index++;
			}
		});
		removeFieldButton = new JButton("REMOVE FIELD");
		removeFieldButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				finalizeButton.setEnabled(true);
				setFinalized(false);
				if(mainTable.getSelectedRow() >= 0){
					model.removeRow(mainTable.getSelectedRow());
				}else{
					JOptionPane.showMessageDialog(removeFieldButton, "Please select a row to be removed");
				}
			}
		});
		modifyButton = new JButton("MODIFY");
		modifyButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setFinalized(false);
				finalizeButton.setEnabled(true);
			}
		});
		position.gridx=0;
		position.gridy=0;
		buttons.add(addFieldButton, position);
		position.gridx=1;
		position.gridy=0;
		buttons.add(removeFieldButton, position);
		position.gridx=2;
		position.gridy=0;
		buttons.add(modifyButton, position);
		position.gridx=3;
		position.gridy=0;
		buttons.add(finalizeButton, position);
		position.gridx=0;
		position.gridy=0;
		add(scroller, position);
		position.gridx=0;
		position.gridy=1;
		add(buttons, position);
		
		//setting colors
		setForeground(Color.WHITE);
		setBackground(Color.WHITE);
		
		buttons.setForeground(Color.WHITE);
		buttons.setBackground(Color.WHITE);
		scroller.setForeground(Color.WHITE);
		scroller.setBackground(Color.WHITE);
	}
	public void setPreferredSize(Dimension dim){
		super.setPreferredSize(dim);
	}
}
