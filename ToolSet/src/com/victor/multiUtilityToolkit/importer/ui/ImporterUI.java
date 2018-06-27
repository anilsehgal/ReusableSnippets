package com.victor.multiUtilityToolkit.importer.ui;


import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;


import com.victor.multiUtilityToolkit.importer.constants.ImporterBean;
import com.victor.multiUtilityToolkit.importer.supportMethods.AFileReader;

public class ImporterUI extends JPanel{
	JFileChooser fileInput;
	JButton browseFileButton;
	JTextField filePath, delimiter,startRow, dateFormat;
	static JTable fileTable;
	JComboBox flavor;
	JComboBox sheetNo;
	JPanel xlfields,delimFields,fixedLenFields,filePathPanel,settingPanel;
	JLabel flavorText,filePathText,sheetNoText,delimiterText,startRowText;
	JScrollPane scroller;
	JTabbedPane tabs;
	Dimension screenSize;
	Object[][] data;
	SetterModel model;
	GridBagConstraints position;
	FixedLengthFieldDescriptorTable fieldDescriptorTable;
	private static final long serialVersionUID = 8524822848313856080L;
	public ImporterUI(){
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
		sheetNo = new JComboBox();
		for(int i=0;i < 21;i++){
			sheetNo.addItem(i);
		}
		
		fieldDescriptorTable = new FixedLengthFieldDescriptorTable();
		
		flavorText =   new JLabel("FILE FLAVOR:  ");
		filePathText = new JLabel("  FILE PATH:  ");
		sheetNoText = new JLabel(" EXCEL SHEET:  ");
		delimiterText = new JLabel(" DELIMITER:  ");
		startRowText = new JLabel (" START ROW:  ");
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		flavor = new JComboBox();
		flavor.addItem("EXCEL");
		flavor.addItem("DELIMITED");
		flavor.addItem("FIXED LENGTH");
		delimFields =    new JPanel(new GridBagLayout());
		fixedLenFields = new JPanel(new GridBagLayout());
		filePathPanel =  new JPanel(new GridBagLayout());
		settingPanel =   new JPanel(new GridBagLayout());
		xlfields = new JPanel();
		dateFormat = new JTextField(20);
		delimiter = new JTextField(2);
		startRow = new JTextField(5);
		startRow.setHorizontalAlignment(JTextField.RIGHT);
		fileTable = new JTable();
		scroller = new JScrollPane(fileTable);
		xlfields.setLayout(new GridBagLayout());
		setLayout(new GridBagLayout());
		position = new GridBagConstraints();
		filePath = new JTextField();
		tabs = new JTabbedPane();
		
		filePath.setPreferredSize(new Dimension((int)screenSize.getWidth()/9,20));
		flavor.setPreferredSize(new Dimension((int)screenSize.getWidth()/14,20));
		tabs.setPreferredSize(new Dimension((int)screenSize.getWidth()*3/8,(int)(screenSize.getHeight()/2)-20));
		scroller.setPreferredSize(new Dimension((int)screenSize.getWidth()*3/8,(int)(screenSize.getHeight()/2)));
		
		filePath.setEditable(false);
		browseFileButton = new JButton("Open File");
		fileInput = new JFileChooser();
		browseFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = fileInput.showOpenDialog(getParent());
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					filePath.setText(fileInput.getSelectedFile().getAbsolutePath());
					
					try {
						if(flavor.getSelectedItem().toString().equals("DELIMITED")){
							data = (new AFileReader()).readDelimitedFile(fileInput.getSelectedFile().getAbsolutePath(), delimiter.getText(), Integer.parseInt(startRow.getText()));
							Object[] colNames = new Object[data[0].length];
							for(int index=0;index < data[0].length;index++){
								colNames[index]=index;
							}
							model = new SetterModel(data, colNames);
						}else if(flavor.getSelectedItem().toString().equals("EXCEL")){
							data = (new AFileReader()).readExcelFile(fileInput.getSelectedFile().getAbsolutePath(), sheetNo.getSelectedIndex(), Integer.parseInt(startRow.getText()));
							
							Object[] colNames = new Object[data[0].length];
							for(int index=0;index < data[0].length;index++){
								colNames[index]=index;
							}
							model = new SetterModel(data, colNames);
						}else if(flavor.getSelectedItem().toString().equals("FIXED LENGTH")){
							if(fieldDescriptorTable.isFinalized()){
								ImporterBean.setFixedLengthFileColumnNames(fieldDescriptorTable.getFieldNames());
								int[] sizes = fieldDescriptorTable.getFields();
								data = (new AFileReader()).readFixedWidthFile(fileInput.getSelectedFile().getAbsolutePath(), sizes, Integer.parseInt(startRow.getText()));
								model = new SetterModel(data, fieldDescriptorTable.getFieldNames());
							}else{
								JOptionPane.showMessageDialog(fieldDescriptorTable, "Please finalise the input field Names/Lengths", "Exception caught while opening file for import", JOptionPane.ERROR_MESSAGE);
							}
						}
						ImporterBean.setFileFlavor(flavor.getSelectedItem().toString());
						ImporterBean.setDateFormat(dateFormat.getText().trim());
						fileTable.setModel(model);
						
						
						
						
						ImporterBean.setFiledata(data);
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
				
				if((e.getKeyCode() >= 48 && e.getKeyCode() <= 57) || e.getKeyCode() == 8 || e.getKeyCode() == 37 || e.getKeyCode() == 127){
					
				}else{
					JOptionPane.showMessageDialog(startRow, "Only Numeric Keys are allowed!!", "", JOptionPane.ERROR_MESSAGE);
					startRow.setText(startRow.getText().replace(e.getKeyChar(), ' ').trim().replace(" ", ""));
					
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
		tabs.addTab("EXCEL", xlfields);
		tabs.addTab("DELIMITED", delimFields);
		tabs.addTab("FIXED LENGTH", fixedLenFields);
		tabs.addTab("DB SETTINGS", new DBSettingsUI());
		
		delimiter.setText(";");
		startRow.setText("0");
		
		position.gridx=0;
		position.gridy=0;
		filePathPanel.add(flavorText, position);		
		position.gridx=1;
		position.gridy=0;
		filePathPanel.add(flavor, position);
		position.gridx=2;
		position.gridy=0;
		filePathPanel.add(startRowText, position);
		position.gridx=3;
		position.gridy=0;
		filePathPanel.add(startRow, position);
		position.gridx=4;
		position.gridy=0;
		filePathPanel.add(dateFormat, position);
		position.gridx=5;
		position.gridy=0;
		filePathPanel.add(filePathText, position);
		position.gridx=6;
		position.gridy=0;
		filePathPanel.add(filePath, position);
		position.gridx=7;
		position.gridy=0;
		filePathPanel.add(browseFileButton, position);
		position.gridx=0;
		position.gridy=1;
		settingPanel.add(filePathPanel, position);
		position.gridx=0;
		position.gridy=0;
		settingPanel.add(tabs, position);	
		position.gridx=0;
		position.gridy=0;
		add(settingPanel, position);
		position.gridx=1;
		position.gridy=0;
		add(scroller, position);
		
	}
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		frame.add(new ImporterUI());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
class SetterModel extends DefaultTableModel{
	private static final long serialVersionUID = -8184078541219531842L;
	
	public SetterModel(Object[][] data, Object[] columnNames){
		super(data,columnNames);
	}
	public int getColumnCount() {
		return super.getColumnCount();
	}
	public int getRowCount() {
		// TODO Auto-generated method stub
		return super.getRowCount();
	}
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return super.getValueAt(rowIndex, columnIndex);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
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
    }

}
//Object[][] data = new Object[model.getRowCount()][model.getColumnCount()];
//for(int i=0;i < model.getRowCount();i++){
//for(int j=0;j < model.getColumnCount();j++){
//	data[i][j] = model.getValueAt(i, j);
//}
//}
//System.out.println("table has been changed");
//ImporterBean.setFiledata(data);


