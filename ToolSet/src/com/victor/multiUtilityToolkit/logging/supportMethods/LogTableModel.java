package com.victor.multiUtilityToolkit.logging.supportMethods;

import javax.swing.table.DefaultTableModel;




public class LogTableModel extends DefaultTableModel{

	private static final long serialVersionUID = -5748134070149613474L;

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
}
