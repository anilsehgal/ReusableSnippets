package com.victor.multiUtilityToolkit.jms.supportMethods;

import javax.swing.table.DefaultTableModel;

public class JMSHeaderTableModel extends DefaultTableModel{

	private static final long serialVersionUID = -354875272457474936L;
	public JMSHeaderTableModel(){
		super();
	}
    public JMSHeaderTableModel(Object[][] data, Object[] columnNames) {
        super.setDataVector(data, columnNames);
    }
	
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

}
