package com.victor.multiUtilityToolkit.jms.supportMethods;

import javax.swing.table.DefaultTableModel;

public class JMSMessageTableModel extends DefaultTableModel{
	
	private static final long serialVersionUID = -2516361998355277484L;
	public JMSMessageTableModel(){
		super();
	}
    public JMSMessageTableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

}
