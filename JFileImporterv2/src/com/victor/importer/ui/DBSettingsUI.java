package com.victor.importer.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.table.TableColumn;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.victor.importer.constants.DBTableColumnBean;
import com.victor.importer.constants.ImporterBean;
import com.victor.importer.constants.StringConstants;
import com.victor.importer.container.FrameContainer;
import com.victor.importer.profiles.CreateXMLProfile;
import com.victor.importer.supportMethods.DBImporter;
import com.victor.importer.supportMethods.ImportValidator;


public class DBSettingsUI extends FrameContainer implements Runnable{
	static Logger logger = Logger.getLogger(DBSettingsUI.class.getName());
	public static ProgressBar bar = new ProgressBar(100);
	static{
		DOMConfigurator.configure(DBImporter.class.getResource(StringConstants.LOG4J_PATH));	
	}
	private static final long serialVersionUID = 6901441288618799393L;
	
	public DBSettingsUI(){
		
		try {
			UIManager.setLookAndFeel(StringConstants.NIMBUS_LOOK);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "Error setting Look & Feel "+e1, "Error setting Look & Feel", JOptionPane.ERROR_MESSAGE);
		} 
		
		schemaPane.setPreferredSize(new
				Dimension((int)screenSize.getWidth()/6,(int)(screenSize.getHeight()/5)));
		mappingPane.setPreferredSize(new
				Dimension((int)screenSize.getWidth()/3,(int)(screenSize.getHeight()/5)));

		importButton.setEnabled(false);
		importButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String returned = StringConstants.SUCCESS;		
				try{	
					if(preSessionCommand!=null && preSessionCommand.getText().trim().length()>0){
						
						returned=commandRunner.executeCommand(preSessionCommandChooser.getSelectedItem().toString(), driverUrl.getSelectedItem().toString(), dburlBox.getText(), userNameBox.getText(), passwordBox.getText(), preSessionCommand.getText(), preSessionCommand.getText());
					}
					if(!continueImportOnPreCmdFailureBox.isSelected()){
						if(returned.equals(StringConstants.SUCCESS)){
							runThreads();
						}else{
							JOptionPane.showMessageDialog(null, "Import Halted as PreImport Command Failed and \"Continue Import if Pre Import Command Fails:\" is not Checked", "Import Halted", JOptionPane.WARNING_MESSAGE);
						}
					}else{
						runThreads();
					}
				}catch(Exception ee){
					
					ee.printStackTrace();
					
				}
			}
		});




		validateButton.addActionListener(new ActionListener() {

			@Override
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				if(dtmMapping!=null && dtmMapping.getRowCount() > 0){
					
					Vector dataVector = dtmMapping.getDataVector();
					boolean someMappingPresent = false, nonNullColmnNotMapped = false;
					//to get to the cell at row 1, column 5:
					Object[][] fileMapping = new Object[dataVector.size()][6];
					//this created mapping
					for(int i=0;i < dataVector.size();i++){
						for(int j=0;j < 6;j++){
							if(((Vector)dataVector.elementAt(i)).elementAt(5)!=null){
								
								someMappingPresent = true;
							}		
							fileMapping[i][j] = ((Vector)dataVector.elementAt(i)).elementAt(j);
							
						}
					}
					if(!someMappingPresent){
						JOptionPane.showMessageDialog(validateButton, "No Mappings found to Validate!!", "Validation Status", JOptionPane.ERROR_MESSAGE);
						importButton.setEnabled(false);		
					}else{
						//here check if primary key is mapped
						for(int primVar = 0;primVar < fileMapping.length;primVar++){
							if(fileMapping[primVar][3]!=null && fileMapping[primVar][3].toString().contains("N") && fileMapping[primVar][5]==null){
								
								nonNullColmnNotMapped = true;
								JOptionPane.showMessageDialog(null, "No Mapping found for: "+fileMapping[primVar][0], "Non Nullable Column not mapped!!", JOptionPane.ERROR_MESSAGE);
								break;
							}
						}
					}
					if(dateFormat!=null ){
						if(dateFormat.getText().trim().equals(StringConstants.ENTER_DATE)){
							ImporterBean.setDateFormat(StringConstants.FULL_DATE_PATTERN);
							dateFormat.setText(StringConstants.FULL_DATE_PATTERN);
						}else{
							ImporterBean.setDateFormat(dateFormat.getText().trim());				
						}
						
					}
					
					if(someMappingPresent && !nonNullColmnNotMapped){
						
						String validateStatus =
							ImportValidator.validateImport(fileMapping,ImporterBean.getFiledata());
						if(validateStatus.equals(StringConstants.COMPLETE)){
							JOptionPane.showMessageDialog(validateButton, "Validation Completed Successfully!!", "Validation Status", JOptionPane.INFORMATION_MESSAGE);
							importButton.setEnabled(true);
						}else{
							JOptionPane.showMessageDialog(validateButton, validateStatus, "Validation Status", JOptionPane.ERROR_MESSAGE);
							importButton.setEnabled(false);
						}
					}	
				}else{
					JOptionPane.showMessageDialog(validateButton, "No Mappings found to Validate!!", "Validation Status", JOptionPane.ERROR_MESSAGE);
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
						query = "SELECT TABLE_NAME FROM dba_tables WHERE owner = '"+userNameBox.getText()+"'";
					}
					List<String> tables =
						dbSettingMethods.executeQueryforTables(query,
								driverUrl.getSelectedItem().toString(), dburlBox.getText(), userNameBox.getText(), passwordBox.getText());
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
		saveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int size =0;
				Vector dataVector = dtmMapping.getDataVector();
				for(int i=0;i < dataVector.size();i++){
					if(((Vector)dataVector.elementAt(i)).elementAt(5)!=null && ((Vector)dataVector.elementAt(i)).elementAt(5).toString().trim().length() > 0){
						size++;
					}
				}
				
				Object[][] fileColumnMapping = new Object[size][6];
				int j=0;
				for(int i=0;i < dataVector.size();i++){
					
					if(((Vector)dataVector.elementAt(i)).elementAt(5)!=null && !((Vector)dataVector.elementAt(i)).elementAt(5).toString().trim().equals("")){
						
						fileColumnMapping[j][0]=(String) ((Vector)dataVector.elementAt(i)).elementAt(0);
						fileColumnMapping[j][1]=(String) ((Vector)dataVector.elementAt(i)).elementAt(1);
						fileColumnMapping[j][2]=Integer.parseInt(((Vector)dataVector.elementAt(i)).elementAt(2).toString());
						fileColumnMapping[j][3]=(String) ((Vector)dataVector.elementAt(i)).elementAt(3);
						fileColumnMapping[j][4]=(String) ((Vector)dataVector.elementAt(i)).elementAt(4);
						fileColumnMapping[j][5]=(String) ((Vector)dataVector.elementAt(i)).elementAt(5);	
						j++;
					}
				}
						
				CreateXMLProfile creatXMLFile = new CreateXMLProfile();
				
				String profileName = null;
				if(!flavor.getSelectedItem().toString().equals("FIXED LENGTH")){	
					
					JFileChooser saveWindow = new JFileChooser();
					int rVal = saveWindow.showSaveDialog(null);
					if (rVal == JFileChooser.APPROVE_OPTION) {
						
						profileName=saveWindow.getSelectedFile().toString();
					}
					
					
			
					if(profileName!=null && !profileName.trim().equals("")){
						if(fileColumnMapping.length > 0 && (schemaTable.getRowCount() > 0 && schemaTable.getSelectedRow() != -1)){
							try {
								creatXMLFile.saveProfile(fileColumnMapping, delimiter.getText(), startRow.getText(), profileName, flavor.getSelectedItem().toString(), filePath.getText(),
										schemaTable.getValueAt(schemaTable.getSelectedRow(), 0).toString(),dburlBox.getText().toString(),
										driverUrl.getSelectedItem().toString(),userNameBox.getText(),passwordBox.getText(),
										duplicateAction.getSelectedItem().toString(), dateFormat.getText(), sheetNo.getSelectedItem().toString(),endRow.getText(), defaultStrategy.getSelectedItem().toString(),
										preSessionCommand.getText()==null?"":preSessionCommand.getText(), postSessionCommand.getText()==null?"":postSessionCommand.getText(), preSessionCommandChooser.getSelectedItem().toString(), postSessionCommandChooser.getSelectedItem().toString(),continueImportOnPreCmdFailureBox.isSelected()?"Y":"N",truncateOverflowingValuesBox.isSelected());
								JOptionPane.showMessageDialog(saveButton, "Profile "+profileName+" Successfully Saved!!", "save profile", JOptionPane.INFORMATION_MESSAGE);	
							} catch (Exception e1) {
								JOptionPane.showMessageDialog(saveButton, e1+" Profile Could not be saved!!", "Error in Saving", JOptionPane.ERROR_MESSAGE);
							}
						}else{
							JOptionPane.showMessageDialog(saveButton, "The profile information schema is invalid!!", "invalid profile", JOptionPane.ERROR_MESSAGE);
						}
					}else{
						JOptionPane.showMessageDialog(saveButton, "The profile name is invalid!!", "invalid profile", JOptionPane.ERROR_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(null, "Save Profile Option is not applicable for Fixed Length files!!", "Profile can not be saved", JOptionPane.ERROR_MESSAGE);
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
						query =
							"Select C.ORDINAL_POSITION,C.COLUMN_NAME, C.DATA_TYPE, C.CHARACTER_MAXIMUM_LENGTH     , C.IS_NULLABLE "
							+" , Case When Z.CONSTRAINT_NAME Is Null Then 'null' Else 'P' End As IsPartOfPrimaryKey From INFORMATION_SCHEMA.COLUMNS As C "
							+ "    Outer Apply (                 Select CCU.CONSTRAINT_NAME                 From INFORMATION_SCHEMA.TABLE_CONSTRAINTS As TC "                    
							+"Join INFORMATION_SCHEMA.CONSTRAINT_COLUMN_USAGE As CCU                         On CCU.CONSTRAINT_NAME = TC.CONSTRAINT_NAME "               
							+"Where TC.TABLE_SCHEMA = C.TABLE_SCHEMA                     And TC.TABLE_NAME = C.TABLE_NAME "                   
							+"And TC.CONSTRAINT_TYPE = 'PRIMARY KEY'                     And CCU.COLUMN_NAME = C.COLUMN_NAME  ) "
							+"As Z Where C.TABLE_NAME = "			
							+"'"+schemaTable.getValueAt(schemaTable.getSelectedRow(), 0)+"'";
					}else if(driverUrl.getSelectedItem().toString().equals("oracle.jdbc.driver.OracleDriver")){
						query =

							"SELECT columns.COLUMN_ID,columns.COLUMN_NAME,columns.DATA_TYPE,columns.DATA_LENGTH,columns.NULLABLE,test.PRIM "+
							"from user_tab_columns columns, "+
							"(SELECT cons.constraint_type as PRIM, cols.column_name as COL_NM "+
							"FROM all_constraints cons, all_cons_columns cols "+
							"WHERE cols.table_name ='"+schemaTable.getValueAt(schemaTable.getSelectedRow(), 0)+"' "+
							"AND cons.constraint_type = 'P' "+
							"AND cons.constraint_name = cols.constraint_name "+
							"AND cons.owner = cols.owner)   test  where columns.TABLE_NAME='"+schemaTable.getValueAt(schemaTable.getSelectedRow(),0)+"' "+
							"AND test.COL_NM(+) = columns.COLUMN_NAME";



					}
					List<DBTableColumnBean> columns =
						dbSettingMethods.executeQueryForColumns(query,
								driverUrl.getSelectedItem().toString(), dburlBox.getText(), userNameBox.getText(), passwordBox.getText());

					ImporterBean.setDbTableColumnBeans(columns);

					Object[][] colArr = new Object[columns.size()][6];
					Object[] titleTable= {"Table Column","Column Data Type", "Max Length", "Nullable", "Primary Key Column","File Columns"};
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
						colArr[index][4] = column.getPrimaryKey();
						index++;
					}
					dtmMapping.setDataVector(colArr, titleTable);
					TableColumn sportColumn = mappingTable.getColumnModel().getColumn(5);
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
		position.gridx = 2;
		position.gridy = 0;
		buttonPanel.add(saveButton, position);
		dupLabel = new JLabel("Duplicate Primary Key Action: ");
		position.gridx = 0;
		position.gridy = 0;
		duplicateActionPanel.add(dupLabel, position);
		position.gridx = 1;
		position.gridy = 0;
		duplicateActionPanel.add(duplicateAction, position);		
		position.gridx = 0;
		position.gridy = 1;
		duplicateActionPanel.add(defLabel, position);
		position.gridx = 1;
		position.gridy = 1;
		duplicateActionPanel.add(defaultStrategy, position);

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
		position.gridx = 1;
		position.gridy = 2;
		credButtonPanel.add(mappingPane, position);
		position.gridx = 0;
		position.gridy = 3;
		credButtonPanel.add(duplicateActionPanel, position);

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
		add(buttonPanel, position);
		position.gridx = 0;
		position.gridy = 4;
		add(bar, position);
	


		
		
//		Border border = BorderFactory.createTitledBorder("Schema Pane");
//		schemaPane.setBorder(border);

		setForeground(Color.WHITE);
		setBackground(Color.WHITE);

		dburlBox.setText("(Enter DB URL Here)");
		dburlBox.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				if(dburlBox.getText().trim().equals("")){
					dburlBox.setText("(Enter DB URL Here)");
				}	
			}

			@Override
			public void focusGained(FocusEvent e) {
				if(dburlBox.getText().equals("(Enter DB URL Here)")){
					dburlBox.setText("");
				}

			}
		});

		userNameBox.setText("(Enter DB UserName Here)");
		userNameBox.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				if(userNameBox.getText().trim().equals("")){
					userNameBox.setText("(Enter DB UserName Here)");
				}	
			}

			@Override
			public void focusGained(FocusEvent e) {
				if(userNameBox.getText().equals("(Enter DB UserName Here)")){
					userNameBox.setText("");
				}

			}
		});

		passwordBox.setText("(Enter DB Password Here)");
		passwordBox.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				if(passwordBox.getText().trim().equals("")){
					passwordBox.setText("(Enter DB Password Here)");
				}	
			}

			@Override
			public void focusGained(FocusEvent e) {
				if(passwordBox.getText().equals("(Enter DB Password Here)")){
					passwordBox.setText("");
				}

			}
		});
	}
	public void runThreads(){
		try{
			bar.setNoOfRows(ImporterBean.getFiledata().length);
			thread1 = new Thread(bar);
			thread2 = new Thread(this);
			thread2.start();
			thread1.start();
		}catch(Exception e){
			e.printStackTrace();
		
		}
	}
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setPreferredSize(new Dimension(500,600));
		frame.add(new DBSettingsUI());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	@Override
	public void run(){
		try {
			doImport();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Exception while Importing: "+e, "Import Failed", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	public void doImport() throws Exception{
		Vector dataVector = dtmMapping.getDataVector();
		List<DBTableColumnBean> columnBeans = new ArrayList<DBTableColumnBean>();
		DBTableColumnBean columnBean = null;
		//to get to the cell at row 1, column 5:
		importButton.setEnabled(false);
		boolean validMapping = true;
		for(int i=0;i < dataVector.size();i++){
			if(((Vector)dataVector.elementAt(i)).elementAt(5)!=null){
				columnBean = new DBTableColumnBean();
				
				columnBean.setColumnName( (String) ((Vector)dataVector.elementAt(i)).elementAt(0));
				columnBean.setDataType((String)
						((Vector)dataVector.elementAt(i)).elementAt(1));
				columnBean.setCharacterMaximumLength(
						Integer.parseInt( ((Vector)dataVector.elementAt(i)).elementAt(2).toString()));
				columnBean.setIsNullable((String)
						((Vector)dataVector.elementAt(i)).elementAt(3));
				columnBean.setPrimaryKey((String)
						((Vector)dataVector.elementAt(i)).elementAt(4));				
				columnBean.setFileColumn((String)
						((Vector)dataVector.elementAt(i)).elementAt(5));
				columnBeans.add(columnBean);

			}else{
				if(((Vector)dataVector.elementAt(i)).elementAt(3)!=null && ((Vector)dataVector.elementAt(i)).elementAt(3).toString().contains("N")){
					
					validMapping = false;
					importButton.setEnabled(false);
					JOptionPane.showMessageDialog(importButton,
							((Vector)dataVector.elementAt(i)).elementAt(0)+" Column is not nullable and has to be mapped!!", "Non Nullable Column not Mapped!!", JOptionPane.ERROR_MESSAGE);
					break;
				}
			}
		}

		if(validMapping){
			String returned = null, query = null;
			logger.info("**********************************STARTING IMPORT*****************************************");
			
			final Object[][] fileData = ImporterBean.getFiledata();
			int importedRows = 0;
			//importing
			try{
				ImporterBean.setInporting(true);
				//you can get the connection here then in try block
				DBImporter.connectToDatabase(driverUrl.getSelectedItem().toString(),
						dburlBox.getText().trim(), userNameBox.getText().trim(), passwordBox.getText().toString());
				for(Object[] row : fileData){
					if(defaultStrategy.getSelectedItem().toString().contains("1")){
						query = DBImporter.createInsertQuery((String)dtmSchema.getValueAt(schemaTable.getSelectedRow(),0),
								columnBeans, row, dateFormat.getText().trim(), driverUrl.getSelectedItem().toString(),truncateOverflowingValuesBox.isSelected());
					}else if(defaultStrategy.getSelectedItem().toString().contains("2")){
						query = DBImporter.createUpdateQuery((String)dtmSchema.getValueAt(schemaTable.getSelectedRow(),0),
								columnBeans, row, dateFormat.getText().trim(), driverUrl.getSelectedItem().toString(),truncateOverflowingValuesBox.isSelected());
					}
					returned =
						DBImporter.importRow( query,
								duplicateAction.getSelectedItem().toString());
					if(returned.equals("Stop Import")){
						String errorRow = "{";
						for(Object col : row){
							errorRow+="'"+col.toString().trim()+"'"+",";			
						}
						errorRow = errorRow.substring(0, errorRow.lastIndexOf(","));
						errorRow += "}";
						JOptionPane.showMessageDialog(null, "Import Stopped when importing Row Data: "+errorRow+". View Application Log for more details", "Import Failure!!", JOptionPane.ERROR_MESSAGE);
						importDone = false;
						break;
					}else if(returned.equals("Reject Row")){
						importDone = true;
						importedRows--;
					}else if(returned.equals("Fire Second")){
						if(defaultStrategy.getSelectedItem().toString().contains("1")){
	
							query = DBImporter.createUpdateQuery((String)dtmSchema.getValueAt(schemaTable.getSelectedRow(),0),
									columnBeans, row, dateFormat.getText().trim(), driverUrl.getSelectedItem().toString(),truncateOverflowingValuesBox.isSelected());
						}else{
	
							query = DBImporter.createInsertQuery((String)dtmSchema.getValueAt(schemaTable.getSelectedRow(),0),
									columnBeans, row, dateFormat.getText().trim(), driverUrl.getSelectedItem().toString(),truncateOverflowingValuesBox.isSelected());
						}
						returned =
							DBImporter.importRow(query,
									duplicateAction.getSelectedItem().toString());
						importDone = true;
					}
					importedRows++;
					ImporterBean.setRowsImported(importedRows);
				}
			}catch(Exception e){
				logger.error("Error in importing Row: "+ e);
			}finally{
			//finally close the connection
				DBImporter.closeDatabaseConnection();
				ImporterBean.setInporting(false);
				logger.info("**********************************IMPORT DONE*****************************************");
				JOptionPane.showMessageDialog(null, "Rows Imported: "+importedRows+"!!", "Import Completed!!", JOptionPane.INFORMATION_MESSAGE);
				ImporterBean.setRowsImported(0);
			}
			if(postSessionCommand!=null && postSessionCommand.getText().trim().length()>0){
				try {
					returned=commandRunner.executeCommand(postSessionCommandChooser.getSelectedItem().toString(), driverUrl.getSelectedItem().toString(), dburlBox.getText(), userNameBox.getText(), passwordBox.getText(), postSessionCommand.getText(), postSessionCommand.getText());
				} catch (Exception e) {
					throw new Exception("Exception occured in Running Post Session Command");
				}
			}
			
		}
	}

}
