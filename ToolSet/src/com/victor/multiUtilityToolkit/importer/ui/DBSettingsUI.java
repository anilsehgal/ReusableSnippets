package com.victor.multiUtilityToolkit.importer.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.victor.multiUtilityToolkit.importer.constants.DBTableColumnBean;
import com.victor.multiUtilityToolkit.importer.constants.ImporterBean;
import com.victor.multiUtilityToolkit.importer.supportMethods.DBSettingMethods;
import com.victor.multiUtilityToolkit.importer.supportMethods.ImportValidator;

public class DBSettingsUI extends JPanel{
	
	private static final long serialVersionUID = 6901441288618799393L;
	JComboBox driverUrl;
	JTextField dburlBox, userNameBox, passwordBox;
	JButton fetchButton, importButton, validateButton;
	JTable schemaTable, mappingTable;
	DefaultTableModel dtmSchema;
	DefaultTableModel dtmMapping;
	JScrollPane schemaPane, mappingPane;
	JPanel credButtonPanel, buttonPanel;
	GridBagConstraints position;
	DBSettingMethods dbSettingMethods;
	public DBSettingsUI(){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		dbSettingMethods = new DBSettingMethods();
		driverUrl = new JComboBox();
		driverUrl.addItem("com.microsoft.jdbc.sqlserver.SQLServerDriver");
		driverUrl.addItem("oracle.jdbc.driver.OracleDriver");
		dtmSchema = new DefaultTableModel();
		dtmMapping = new DefaultTableModel();
		position = new GridBagConstraints();
		credButtonPanel = new JPanel(new GridBagLayout());
		buttonPanel = new JPanel(new GridBagLayout());
		dburlBox = new JTextField(50);
		userNameBox = new JTextField(25);
		passwordBox = new JTextField(25);
		fetchButton = new JButton("FETCH TABLES");
		importButton = new JButton("START IMPORT");
		validateButton = new JButton("VALIDATE IMPORT");
		schemaTable = new JTable(dtmSchema);
		mappingTable = new JTable(dtmMapping);
		schemaPane = new JScrollPane(schemaTable);
		mappingPane = new JScrollPane(mappingTable);
		schemaPane.setPreferredSize(new Dimension(200,150));
		mappingPane.setPreferredSize(new Dimension(400,150));
		/////////////////
		dburlBox.setText("jdbc:microsoft:sqlserver://peserver:1433");
		userNameBox.setText("TransferDB");
		passwordBox.setText("Tra-inf-123");
		///////////////////
		importButton.setEnabled(false);
		validateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Vector dataVector = dtmMapping.getDataVector();
				
				//to get to the cell at row 1, column 5: 
				Object[][] fileMapping = new Object[dataVector.size()][5];
				for(int i=0;i < dataVector.size();i++){
					for(int j=0;j < 5;j++){
						fileMapping[i][j] = ((Vector)dataVector.elementAt(i)).elementAt(j);
					}
				}
				String validateStatus = ImportValidator.validateImport(fileMapping,ImporterBean.getFiledata(), ImporterBean.getFileFlavor());
				if(validateStatus.equals("Complete")){
					JOptionPane.showMessageDialog(validateButton, "Validation Completed Successfully!!", "Validation Status", JOptionPane.INFORMATION_MESSAGE);
					importButton.setEnabled(true);
				}else{
					JOptionPane.showMessageDialog(validateButton, validateStatus, "Validation Status", JOptionPane.ERROR_MESSAGE);
					importButton.setEnabled(false);
				}
			}
		});
		
		fetchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int index = 0;
					String query = null;
					if(driverUrl.getSelectedItem().toString().equals("com.microsoft.jdbc.sqlserver.SQLServerDriver")){
						query = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE'";
					}else if(driverUrl.getSelectedItem().toString().equals("oracle.jdbc.driver.OracleDriver")){
						query = "SELECT * FROM dba_tables WHERE owner = '"+userNameBox.getText()+"'";
					}
					List<String> tables = dbSettingMethods.executeQueryforTables(query, driverUrl.getSelectedItem().toString(), dburlBox.getText(), userNameBox.getText(), passwordBox.getText());
					Object[][] tableArr = new Object[tables.size()][1];
					Object[] titleTable= {"Tables"};
					for(String table : tables){
						tableArr[index][0] = table;
						index++;
					}
					dtmSchema.setDataVector(tableArr, titleTable);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(dburlBox, ""+e1, "JDBC Exception", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});	
		
		schemaTable.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				String query = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.columns WHERE TABLE_NAME = '"+schemaTable.getValueAt(schemaTable.getSelectedRow(), 0)+"'";
				try {
					 
					int index = 0;
					if(driverUrl.getSelectedItem().toString().equals("com.microsoft.jdbc.sqlserver.SQLServerDriver")){
						query = "SELECT ORDINAL_POSITION,COLUMN_NAME,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH,IS_NULLABLE FROM INFORMATION_SCHEMA.columns WHERE TABLE_NAME = '"+schemaTable.getValueAt(schemaTable.getSelectedRow(), 0)+"'";
					}else if(driverUrl.getSelectedItem().toString().equals("oracle.jdbc.driver.OracleDriver")){
						query = "SELECT COLUMN_ID,COLUMN_NAME,DATA_TYPE,DATA_LENGTH,NULLABLE from user_tab_columns  where TABLE_NAME='"+schemaTable.getValueAt(schemaTable.getSelectedRow(), 0)+"'";
					}
					List<DBTableColumnBean> columns = dbSettingMethods.executeQueryForColumns(query, driverUrl.getSelectedItem().toString(), dburlBox.getText(), userNameBox.getText(), passwordBox.getText());
					
					ImporterBean.setDbTableColumnBeans(columns);

					Object[][] colArr = new Object[columns.size()][5];
					Object[] titleTable= {"Table Column","Column Data Type", "Max Length", "Nullable","File Columns"};
					JComboBox box = new JComboBox();
					
					for(Object colName : ImporterBean.getFileColumnNames()){
						if(colName instanceof String){
							box.addItem(colName.toString());
						}
					}

					for(DBTableColumnBean column : columns){
						colArr[index][0] = column.getColumnName();
						colArr[index][1] = column.getDataType();
						colArr[index][2] = column.getCharacterMaximumLength();
						colArr[index][3] = column.getIsNullable();
						index++;
					}
					dtmMapping.setDataVector(colArr, titleTable);
					TableColumn sportColumn = mappingTable.getColumnModel().getColumn(4);
					sportColumn.setCellEditor(new DefaultCellEditor(box));
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(dburlBox, ""+e1, "JDBC Exception", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		
		position.gridx = 0;
		position.gridy = 0;
		buttonPanel.add(validateButton, position);
		position.gridx = 1;
		position.gridy = 0;
		buttonPanel.add(importButton, position);
		
		
		position.gridx = 0;
		position.gridy = 0;
		credButtonPanel.add(userNameBox, position);
		position.gridx = 0;
		position.gridy = 1;
		credButtonPanel.add(passwordBox, position);
		position.gridx = 1;
		position.gridy = 1;
		credButtonPanel.add(fetchButton, position);
		position.gridx = 0;
		position.gridy = 2;
		credButtonPanel.add(schemaPane, position);

		position.gridx = 0;
		position.gridy = 0;
		add(driverUrl, position);
		position.gridx = 0;
		position.gridy = 1;
		add(dburlBox, position);
		position.gridx = 0;
		position.gridy = 2;
		add(credButtonPanel, position);
		position.gridx = 0;
		position.gridy = 3;
		add(mappingPane, position);
		position.gridx = 0;
		position.gridy = 4;
		add(buttonPanel, position);	
	}
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setPreferredSize(new Dimension(500,600));
		frame.add(new DBSettingsUI());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
}

