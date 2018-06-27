package com.victor.importer.model;

import javax.swing.table.DefaultTableModel;

import com.victor.importer.constants.ImporterBean;
import com.victor.importer.container.FrameContainer;

public class SetterModel2 extends DefaultTableModel{
	private static final long serialVersionUID = -8184078541219531842L;
	
	public SetterModel2(Object[][] data, Object[] columnNames){
		super(data,columnNames);
	}
	public int getColumnCount() {
		return super.getColumnCount();
	}
	public int getRowCount() {
		return super.getRowCount();
	}
	public Object getValueAt(int rowIndex, int columnIndex) {
		return super.getValueAt(rowIndex, columnIndex);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		
		return true;
	}
	public void setValueAt(Object value, int row, int col) {
        super.setValueAt(value, row, col);
		Object[][] data = new Object[getRowCount()][getColumnCount()];
		for(int i=0;i < getRowCount();i++){
			for(int j=0;j < getColumnCount();j++){
			  	data[i][j] = getValueAt(i, j);
			}
		}
		ImporterBean.setFiledata(data);
		FrameContainer.importButton.setEnabled(false);
    }
}
