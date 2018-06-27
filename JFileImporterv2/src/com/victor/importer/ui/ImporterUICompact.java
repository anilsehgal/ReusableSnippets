package com.victor.importer.ui;

import java.awt.Dimension;

import java.awt.GridBagLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.UIManager;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener; 

import javax.swing.table.TableColumn;


import com.victor.importer.constants.ImporterBean;
import com.victor.importer.constants.ProfileBean;
import com.victor.importer.container.FrameContainer;
import com.victor.importer.model.SetterModel2;
import com.victor.importer.profiles.ReadXMLProfile;
import com.victor.importer.supportMethods.AFileReader;

public class ImporterUICompact extends FrameContainer{

	private static final long serialVersionUID = -7014701300325849725L;
	public static SetterModel2 model;
	public ImporterUICompact(){ 
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "Error setting Look & Feel "+e1, "Error setting Look & Feel", JOptionPane.ERROR_MESSAGE);
		} 
		
		for(int i=0;i < 21;i++){
			sheetNo.addItem(i);
		}
		
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		xlfields.setLayout(new GridBagLayout());
		setLayout(new GridBagLayout());

		
		sheetNo.setPreferredSize(new Dimension(140,25));
		delimiter.setPreferredSize(new Dimension(150,25));
		filePath.setPreferredSize(new Dimension(150,25));
		flavor.setPreferredSize(new Dimension(150,25));
		dateFormat.setPreferredSize(new Dimension(150,25));
		startRow.setPreferredSize(new Dimension(150,25));
		endRow.setPreferredSize(new Dimension(150,25));
		tabs.setPreferredSize(new Dimension((int)screenSize.getWidth()*2/3,(int)(screenSize.getHeight()*2/4)));
		filePath.setEditable(false);
		
		browseFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = fileInput.showOpenDialog(getParent());
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					filePath.setText(fileInput.getSelectedFile().getAbsolutePath());
					
					try {
						if(flavor.getSelectedItem().toString().equals("DELIMITED")){
							data = (new
							AFileReader()).readDelimitedFile(fileInput.getSelectedFile().getAbsolutePath(),
									delimiter.getText(), Integer.parseInt(startRow.getText()), Integer.parseInt(endRow.getText()));
							Object[] colNames = new Object[data[0].length];
							for(int index=0;index < data[0].length;index++){
								colNames[index]=index;
							}
							model = new SetterModel2(data, colNames);
						}else if(flavor.getSelectedItem().toString().equals("EXCEL")){
							data = (new
							AFileReader()).readExcelFile(fileInput.getSelectedFile().getAbsolutePath(),
									sheetNo.getSelectedIndex(), Integer.parseInt(startRow.getText()), Integer.parseInt(endRow.getText()));
							
							Object[] colNames = new Object[data[0].length];
							for(int index=0;index < data[0].length;index++){
								colNames[index]=index;
							}
							model = new SetterModel2(data, colNames);
							dateFormat.setText("EEE MMM dd HH:mm:ss ZZZ yyyy");
							
						}else if(flavor.getSelectedItem().toString().equals("FIXED LENGTH")){
							if(fieldDescriptorTable.isFinalized()){
								ImporterBean.setFixedLengthFileColumnNames(fieldDescriptorTable.getFieldNames());
								int[] sizes = fieldDescriptorTable.getFields();
								data = (new
								AFileReader()).readFixedWidthFile(fileInput.getSelectedFile().getAbsolutePath(),
								sizes, Integer.parseInt(startRow.getText()), Integer.parseInt(endRow.getText()));
								model = new SetterModel2(data, fieldDescriptorTable.getFieldNames());
							}else{
								data = new Object[0][0];
								JOptionPane.showMessageDialog(fieldDescriptorTable, "Please finalise the input field Names/Lengths", "Exception caught while opening file for import", JOptionPane.ERROR_MESSAGE);
							}
						}
						ImporterBean.setFileFlavor(flavor.getSelectedItem().toString());
						ImporterBean.setDateFormat(dateFormat.getText().trim());
						fileTable.setModel(model);
						
						
						
						
						ImporterBean.setFiledata(data);
						if(data.length > 0){					
							int width = 100;
							fileTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
							for(int i =0; i<fileTable.getColumnCount();i++){
								TableColumn col = fileTable.getColumnModel().getColumn(i);
								col.setMinWidth(width);
							}
							
							
							
							JOptionPane.showMessageDialog(browseFileButton, "File opened successfully!!", "Read File", JOptionPane.INFORMATION_MESSAGE);
							tabs.setSelectedIndex(2);
						}else{
							JOptionPane.showMessageDialog(browseFileButton, "File could not be opened OR No rows found!!", "Read File", JOptionPane.ERROR_MESSAGE);							
						}
						Object[] columnNames = new Object[fileTable.getColumnCount()];
						for(int i=0;i < fileTable.getColumnCount();i++){
							columnNames[i]=fileTable.getColumnName(i);
						}
						ImporterBean.setFileColumnNames(columnNames);
					}catch(ArrayIndexOutOfBoundsException ae){
						JOptionPane.showMessageDialog(browseFileButton, "Array Index Exception: "+ae, "Exception caught while opening file", JOptionPane.ERROR_MESSAGE);
					}catch (Exception e1) {
						JOptionPane.showMessageDialog(browseFileButton, "Exception: "+e1,  "Exception caught while opening file", JOptionPane.ERROR_MESSAGE);
					}
				}			
			}
		});
		
		
		startRow.addKeyListener(new KeyListener() {
			
			public void keyTyped(KeyEvent e) {

			}
			public void keyReleased(KeyEvent e) {

			}
			public void keyPressed(KeyEvent e) {
				
				if((e.getKeyCode() >= 48 && e.getKeyCode() <= 57) ||
e.getKeyCode() == 8 || e.getKeyCode() == 37 || e.getKeyCode() == 127){
					
				}else{
					JOptionPane.showMessageDialog(startRow, "Only Numeric Keys are allowed!!", "", JOptionPane.ERROR_MESSAGE);
					startRow.setText(startRow.getText().replace(e.getKeyChar(), ' ').trim().replace(" ", ""));
					
				}
			}
		});
		endRow.addKeyListener(new KeyListener() {
			
			public void keyTyped(KeyEvent e) {

			}
			public void keyReleased(KeyEvent e) {

			}
			public void keyPressed(KeyEvent e) {
				
				if((e.getKeyCode() >= 48 && e.getKeyCode() <= 57) ||
e.getKeyCode() == 8 || e.getKeyCode() == 37 || e.getKeyCode() == 127){
					
				}else{
					JOptionPane.showMessageDialog(endRow, "Only Numeric Keys are allowed!!", "", JOptionPane.ERROR_MESSAGE);
					endRow.setText(endRow.getText().replace(e.getKeyChar(), ' ').trim().replace(" ", ""));
					
				}
			}
		});
		dateFormat.setText("(Enter Date Format Here)");
		dateFormat.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				if(dateFormat.getText().trim().equals("")){
					dateFormat.setText("(Enter Date Format Here)");
				}	
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				if(dateFormat.getText().equals("(Enter Date Format Here)")){
					dateFormat.setText("");
				}
				
			}
		});
		/*************************************************************/
		loadProfileButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String xmlFile = null;
				AFileReader fileReader = null;
				ProfileBean profileBean = null;
				JFileChooser xmlFileOpener = new JFileChooser();
				int returnVal = xmlFileOpener.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION){
					try {
						xmlFile = xmlFileOpener.getSelectedFile().getAbsolutePath();
						fileReader = new AFileReader();
						profileBean = ReadXMLProfile.openProfile(xmlFile);
						ImporterBean.setLoadedProfile(profileBean);
						Object[] titleTable= {"Tables"};
						Object[][] table= new Object[1][1];
						table[0][0]=profileBean.getTableName();
						Object[] titleTable2= {"Table Column","Column Data Type", "Max Length", "Nullable", "Primary Key Column","File Columns"};
						filePath.setText(profileBean.getFilePath());
						//read the file, if success then fill all
						if(profileBean.getFlavor().equalsIgnoreCase("EXCEL")){
							data=fileReader.readExcelFile(profileBean.getFilePath(), Integer.parseInt(profileBean.getSheetNo()), Integer.parseInt(profileBean.getStartLine()), Integer.parseInt(profileBean.getEndRow()));
						}else if(profileBean.getFlavor().equalsIgnoreCase("DELIMITED")){
							data=fileReader.readDelimitedFile(profileBean.getFilePath(), profileBean.getDelimiter(), Integer.parseInt(profileBean.getStartLine()), Integer.parseInt(profileBean.getEndRow()));
						}
						Object[] colNames = new Object[data[0].length];
						for(int index=0;index < data[0].length;index++){
							colNames[index]=index;
						}
						model = new SetterModel2(data, colNames);
						fileTable.setModel(model);
						
						int width = 100;
						fileTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
						for(int i =0; i<fileTable.getColumnCount();i++){
							TableColumn col = fileTable.getColumnModel().getColumn(i);
							col.setMinWidth(width);
						}
						
						ImporterBean.setFiledata(data);
						
						
						delimiter.setText(profileBean.getDelimiter());
						startRow.setText(profileBean.getStartLine());
						dateFormat.setText(profileBean.getDateFormat()); 
						endRow.setText(profileBean.getEndRow());
						
						flavor.setSelectedItem(profileBean.getFlavor());	
						sheetNo.setSelectedItem(profileBean.getSheetNo());
						driverUrl.setSelectedItem(profileBean.getDriverName());
						duplicateAction.setSelectedItem(profileBean.getDupRowAction());
						defaultStrategy.setSelectedItem(profileBean.getDefaultStrategy());
						dburlBox.setText(profileBean.getDburl());
						userNameBox.setText(profileBean.getUserName());
						passwordBox.setText(profileBean.getPassword());
						dtmSchema.setDataVector(table, titleTable);
						schemaTable.selectAll();
						dtmMapping.setDataVector(profileBean.getColumnMapping(), titleTable2);
						
						JComboBox box = new JComboBox();
						Object[] columnNames = new Object[fileTable.getColumnCount()];
						for(int i=0;i < fileTable.getColumnCount();i++){
							columnNames[i]=fileTable.getColumnName(i);
						}
						ImporterBean.setFileColumnNames(columnNames);
						for(Object colName : ImporterBean.getFileColumnNames()){
							if(colName instanceof String){
								box.addItem(colName.toString());
							}
						}
						TableColumn sportColumn = mappingTable.getColumnModel().getColumn(5);
						sportColumn.setCellEditor(new DefaultCellEditor(box));
						
						
						
						preSessionCommandChooser.setSelectedItem(profileBean.getPreImportCmdType());
						postSessionCommandChooser.setSelectedItem(profileBean.getPostImportCmdType());
						continueImportOnPreCmdFailureBox.setSelected(profileBean.getContinueImportOnPreCmdFailure().equals("Y")?true:false);
						preSessionCommand.setText(profileBean.getPreImportCmd());
						postSessionCommand.setText(profileBean.getPostImportCmd());
						truncateOverflowingValuesBox.setSelected(profileBean.isTruncateOverflowingValues());
						
						JOptionPane.showMessageDialog(null, "Profile opened Successfully!!", "Open Existing Profile", JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Profile Could not be opened!! "+e1, "File Open Exception", JOptionPane.ERROR_MESSAGE);
					}
				}
				
			}
		});
		/*************************************************************/
		position.gridx=0;
		position.gridy=0;
		xlfields.add(sheetNoText, position);		
		position.gridx=0;
		position.gridy=2;
		delimFields.add(delimiterText, position);	
		position.gridx=1;
		position.gridy=0;
		xlfields.add(sheetNo, position);		
		position.gridx=1;
		position.gridy=2;
		delimFields.add(delimiter, position);
		position.gridx=0;
		position.gridy=0;
		fixedLenFields.add(fieldDescriptorTable, position);	


		position.gridx=0;
		position.gridy=0;
		filePathPanel.add(flavorText, position);		
		position.gridx=1;
		position.gridy=0;
		filePathPanel.add(flavor, position);
		position.gridx=0;
		position.gridy=1;
		filePathPanel.add(startRowText, position);
		position.gridx=1;
		position.gridy=1;
		filePathPanel.add(startRow, position);
		position.gridx=0;
		position.gridy=2;
		filePathPanel.add(endRowText, position);
		position.gridx=1;
		position.gridy=2;
		filePathPanel.add(endRow, position);
		position.gridx=0;
		position.gridy=3;
		filePathPanel.add(new JLabel("DATE FORMAT: "), position);
		position.gridx=1;
		position.gridy=3;
		filePathPanel.add(dateFormat, position);
		position.gridx=0;
		position.gridy=4;
		filePathPanel.add(filePathText, position);
		position.gridx=1;
		position.gridy=4;
		filePathPanel.add(filePath, position);
		position.gridx=2;
		position.gridy=4;
		filePathPanel.add(browseFileButton, position);

		
		position.gridx=0;
		position.gridy=0;
		allFileSettingPanel.add(xlfields, position);	
		position.gridx=0;
		position.gridy=1;
		allFileSettingPanel.add(delimFields, position);	
		position.gridx=0;
		position.gridy=2;
		allFileSettingPanel.add(fixedLenFields, position);	
		position.gridx=0;
		position.gridy=3;
		allFileSettingPanel.add(filePathPanel, position);
		position.gridx=0;
		position.gridy=4;
		allFileSettingPanel.add(loadProfileButton, position);
		ImageIcon tab1Icon = new ImageIcon(LoadProfileUI.class.getResource("/config/settings.jpg"));
		ImageIcon tab2Icon = new ImageIcon(LoadProfileUI.class.getResource("/config/db.jpg"));
		ImageIcon tab3Icon = new ImageIcon(LoadProfileUI.class.getResource("/config/file.jpg"));
		ImageIcon tab4Icon = new ImageIcon(LoadProfileUI.class.getResource("/config/advanced.jpg"));
		
		tabs.addTab("FILE SETTINGS",tab1Icon, allFileSettingPanel,"Settings for the file to be imported");
		tabs.addTab("DB SETTINGS",tab2Icon, new DBSettingsUI(),"Settings for the Database in which the file is to be imported");
		tabs.addTab("FILE DISPLAY",tab3Icon, scroller,"Editable File Display");	
		tabs.addTab("ADVANCED",tab4Icon, new AdvancedSettingsUI(), "Advanced Import Settings");	


		
		
		

		position.gridx=0;
		position.gridy=0;
		add(tabs, position);		
		
		

		tabs.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				
				if(tabs.getSelectedIndex()==1){
					if(model==null || (model!=null && model.getRowCount() == 0)){
						JOptionPane.showMessageDialog(tabs, "Please open a file before accessing this tab!!", "No File Selected", JOptionPane.ERROR_MESSAGE);
						tabs.setSelectedIndex(0);
					}
				}
			}
		});
		
		delimiter.setText(";");
		startRow.setText("0");
		endRow.setText("0");

//		position.gridx=1;
//		position.gridy=0;
//		add(scroller, position);
		
		
		//setting colors
		
	}
	
/*	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//frame.setPreferredSize(new Dimension((int)(screenSize.getWidth()*0.65),(int)(screenSize.getHeight()*0.9)));
		frame.add(new ImporterUICompact(),BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}*/
}

