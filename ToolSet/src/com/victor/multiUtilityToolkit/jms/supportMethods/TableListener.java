package com.victor.multiUtilityToolkit.jms.supportMethods;

import java.util.List;

import javax.jms.Message;
import javax.jms.TextMessage;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TableListener implements ListSelectionListener {
	JTable headerTable;
	JTextArea messageArea;
	List<Message> messages;
	
	public TableListener(JTable headerTable, JTextArea messageArea, List<Message> messages){
		this.headerTable = headerTable;
		this.messageArea = messageArea;
		this.messages = messages;
	}
	
	public void valueChanged(ListSelectionEvent e) {
		ListSelectionModel rowSM = (ListSelectionModel)e.getSource();
		int selectedIndex = rowSM.getMinSelectionIndex();
		JMSHeaderTableModel dtm = null;
		if(selectedIndex >= 0 && selectedIndex < messages.size()){
			try {
				dtm = new JMSHeaderTableModel(GenericMethods.getHeadersAsArray(messages.get(selectedIndex)), GenericMethods.getHeaderNamesAsArray());
				TextMessage tm = (TextMessage)messages.get(selectedIndex);
				headerTable.setModel(dtm);
				headerTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
				messageArea.setText(tm.getText());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
}

