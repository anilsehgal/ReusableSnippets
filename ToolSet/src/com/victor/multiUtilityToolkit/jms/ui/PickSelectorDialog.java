package com.victor.multiUtilityToolkit.jms.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.toedter.calendar.JDateChooser;
import com.victor.multiUtilityToolkit.jms.constants.JMSConstants;
import com.victor.multiUtilityToolkit.jms.supportMethods.GenericMethods;

public class PickSelectorDialog extends JDialog{
	private static final long serialVersionUID = 1799736647253114686L;
	JComboBox selectCriterionBox = null;
	JComboBox operatorBox = null;
	JTextField valueBox = null;
	JTextField userDefinedSelector = null;
	JButton submitButton = null;
	String selector = null;
	JLabel fromLabel = null;
	JLabel toLabel = null;
	JDateChooser datePicker1 = null;
	JDateChooser datePicker2 = null;
	GridBagConstraints position  = null;
	public PickSelectorDialog(){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
		userDefinedSelector = new JTextField(35);
		submitButton = new JButton(JMSConstants.SUBMIT);
		datePicker1 = new JDateChooser();
		datePicker2 = new JDateChooser();
		setLayout(new GridBagLayout());
		position  = new GridBagConstraints();
		valueBox = new JTextField(10);
		operatorBox = new JComboBox();
		fromLabel = new JLabel(JMSConstants.FROM);
		toLabel = new JLabel(JMSConstants.TO);
		operatorBox.addItem("=");
		operatorBox.addItem(">");
		operatorBox.addItem("<");
		operatorBox.addItem(">=");
		operatorBox.addItem("<=");
		selectCriterionBox = new JComboBox();
		

		List<String> optionList = GenericMethods.getSelectorOptions();
		for(int optIndex=0;optIndex < optionList.size();optIndex++){
			selectCriterionBox.addItem(optionList.get(optIndex));
		}
		selectCriterionBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				String criterion = (String) selectCriterionBox.getSelectedItem();
				if(criterion.equals(JMSConstants.JMS_TIME_STAMP)){
					if((operatorBox.getItemAt(5))==null){
						operatorBox.addItem(JMSConstants.SPACE+JMSConstants.BETWEEN+JMSConstants.SPACE);
					}
					valueBox.setVisible(false);
					fromLabel.setVisible(true);
					toLabel.setVisible(true);
					datePicker1.setVisible(true);
					datePicker2.setVisible(true);
				}else{

					
					operatorBox.removeAllItems();
					operatorBox.addItem("=");
					operatorBox.addItem(">");
					operatorBox.addItem("<");
					operatorBox.addItem(">=");
					operatorBox.addItem("<=");
					valueBox.setVisible(true);
					fromLabel.setVisible(false);
					toLabel.setVisible(false);
					datePicker1.setVisible(false);
					datePicker2.setVisible(false);
				}
			}
		});
		submitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(((String)selectCriterionBox.getSelectedItem()).equals(JMSConstants.JMS_TIME_STAMP)){
					try {
					if(userDefinedSelector.getText().trim().length()==0){
						setSelector(GenericMethods.buildSelector((String)selectCriterionBox.getSelectedItem(), (String)operatorBox.getSelectedItem(), datePicker1.getDate(), datePicker2.getDate()));
					}else{
						setSelector(userDefinedSelector.getText());
					}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}else{
					try {
						if(userDefinedSelector.getText().trim().length()==0){
							setSelector(GenericMethods.buildSelector((String)selectCriterionBox.getSelectedItem(), (String)operatorBox.getSelectedItem(), valueBox.getText()));
						}else{
							setSelector(userDefinedSelector.getText());
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}					
				}
				setVisible(false);
			}
		});
		position.gridx=0;
		position.gridy=0;		
		add(new JLabel("Pick Selector:"), position);
		position.gridx=1;
		position.gridy=0;
		add(selectCriterionBox, position);
		position.gridx=2;
		position.gridy=0;
		add(operatorBox, position);
		position.gridx=3;
		position.gridy=0;
		add(valueBox, position);
		position.gridx=4;
		position.gridy=0;
		add(fromLabel, position);
		position.gridx=5;
		position.gridy=0;
		add(datePicker1, position);
		position.gridx=6;
		position.gridy=0;
		add(toLabel, position);
		position.gridx=7;
		position.gridy=0;
		add(datePicker2, position);
		position.gridx=0;
		position.gridy=1;		
		add(new JLabel("Define Selector:"), position);
		position.gridx=1;
		position.gridy=1;		
		add(userDefinedSelector, position);
		position.gridx=4;
		position.gridy=1;
		add(submitButton, position);
		fromLabel.setVisible(false);
		toLabel.setVisible(false);
		datePicker1.setVisible(false);
		datePicker2.setVisible(false);
		
		setPreferredSize(new Dimension(700, 200));
		setResizable(false);
		setModal(true);
		pack();
		setVisible(true);
	}
	public String getSelector() {
		return selector;
	}
	public void setSelector(String selector) {
		this.selector = selector;
	}
	
}
