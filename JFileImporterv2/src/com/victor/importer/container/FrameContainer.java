package com.victor.importer.container;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.victor.importer.supportMethods.DBSettingMethods;
import com.victor.importer.supportMethods.RunCommand;
import com.victor.importer.ui.FixedLengthFieldDescriptorTable;


public class FrameContainer extends JPanel{

	private static final long serialVersionUID = 763178120328483059L;
	public static JFileChooser fileInput;
	public static JButton browseFileButton, loadProfileButton;
	public static JTextField filePath, delimiter,startRow, dateFormat, endRow;
	public static JTable fileTable;	
	public static JComboBox flavor;
	public static JComboBox sheetNo;
	public static JPanel xlfields,delimFields,fixedLenFields,filePathPanel,settingPanel,allFileSettingPanel;
	public static JLabel flavorText,filePathText,sheetNoText,delimiterText,startRowText,endRowText;
	public static JScrollPane scroller;
	public static JTabbedPane tabs;
	public static Dimension screenSize;
	public static Object[][] data;
	
	public static JComboBox driverUrl, duplicateAction, defaultStrategy;
	public static JTextField dburlBox, userNameBox, passwordBox;
	public static JButton fetchButton, importButton, validateButton,saveButton;
	public static JTable schemaTable, mappingTable;
	public static boolean importDone = false;
	public static RunCommand commandRunner;
	public static DefaultTableModel dtmSchema;
	public static DefaultTableModel dtmMapping;
	public static JScrollPane schemaPane, mappingPane;
	public static Thread thread1;
	public static Thread thread2;
	public static JPanel credButtonPanel, buttonPanel,duplicateActionPanel;
	public static DBSettingMethods dbSettingMethods;
	public static JLabel dupLabel, defLabel;	
	public static GridBagConstraints position;
	public static FixedLengthFieldDescriptorTable fieldDescriptorTable;
	
	public static JTextArea preSessionCommand, postSessionCommand;
	public static JLabel preSessionCommandLabel, postSessionCommandLabel;
	public static JComboBox preSessionCommandChooser, postSessionCommandChooser;
	public static JCheckBox continueImportOnPreCmdFailureBox, truncateOverflowingValuesBox;
	public static JPanel commandPanel;
	public static JScrollPane preSessionCommandScroller, postSessionCommandScroller;
	public static String SUCCESS = "success";
	static{
		fieldDescriptorTable = new FixedLengthFieldDescriptorTable();
		loadProfileButton = new JButton("OPEN EXISTING PROFILE");
		flavorText =   new JLabel("FILE FLAVOR:  ");
		filePathText = new JLabel("  FILE PATH:  ");
		sheetNoText = new JLabel("EXCEL SHEET: ");
		delimiterText = new JLabel(" DELIMITER:  ");
		startRowText = new JLabel (" START ROW:  ");
		endRowText = new JLabel ("   END ROW:  ");
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		flavor = new JComboBox();
		flavor.addItem("EXCEL");
		flavor.addItem("DELIMITED");
		flavor.addItem("FIXED LENGTH");
		allFileSettingPanel = new JPanel(new GridBagLayout());
		delimFields =    new JPanel(new GridBagLayout());
		fixedLenFields = new JPanel(new GridBagLayout());
		filePathPanel =  new JPanel(new GridBagLayout());
		settingPanel =   new JPanel(new GridBagLayout());
		xlfields = new JPanel();
		dateFormat = new JTextField();
		delimiter = new JTextField();
		startRow = new JTextField();
		startRow.setHorizontalAlignment(JTextField.RIGHT);
		endRow = new JTextField();
		endRow.setHorizontalAlignment(JTextField.RIGHT);
		fileTable = new JTable();
		sheetNo = new JComboBox();
		scroller = new JScrollPane(fileTable);
		position = new GridBagConstraints();
		filePath = new JTextField();
		tabs = new JTabbedPane();
		browseFileButton = new JButton("Open File");
		fileInput = new JFileChooser();
		flavorText.setBackground(Color.WHITE);
		flavor.setBackground(Color.WHITE);
		startRowText.setBackground(Color.WHITE);
		startRow.setBackground(Color.WHITE);
		dateFormat.setBackground(Color.WHITE);
		filePathText.setBackground(Color.WHITE);
		filePath.setBackground(Color.WHITE);
		filePathPanel.setForeground(Color.WHITE);
		filePathPanel.setBackground(Color.WHITE);
		settingPanel.setForeground(Color.WHITE);
		settingPanel.setBackground(Color.WHITE);
		
		scroller.setForeground(Color.WHITE);
		scroller.setBackground(Color.WHITE);
		tabs.setBackground(Color.WHITE);
		xlfields.setForeground(Color.WHITE);
		xlfields.setBackground(Color.WHITE);
		delimFields.setForeground(Color.WHITE);
		delimFields.setBackground(Color.WHITE);
		fixedLenFields.setForeground(Color.WHITE);
		fixedLenFields.setBackground(Color.WHITE);
		fileTable.setBackground(Color.WHITE);	
		allFileSettingPanel.setForeground(Color.WHITE);
		allFileSettingPanel.setBackground(Color.WHITE);
		
		
		
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		dbSettingMethods = new DBSettingMethods();
		commandRunner = new RunCommand();
		driverUrl = new JComboBox();
		driverUrl.addItem("com.microsoft.jdbc.sqlserver.SQLServerDriver");
		driverUrl.addItem("oracle.jdbc.driver.OracleDriver");
		duplicateAction = new JComboBox();
		duplicateAction.addItem("Reject Row");
		duplicateAction.addItem("Stop Import");
		duplicateAction.addItem("Fire Second");
		defaultStrategy = new JComboBox();
		defaultStrategy.addItem("1. Insert else Update");
		defaultStrategy.addItem("2. Update else Insert");
		dtmSchema = new DefaultTableModel();
		dtmMapping = new DefaultTableModel();
		position = new GridBagConstraints();
		credButtonPanel = new JPanel(new GridBagLayout());
		buttonPanel = new JPanel(new GridBagLayout());
		duplicateActionPanel = new JPanel(new GridBagLayout());
		dburlBox = new JTextField(50);
		userNameBox = new JTextField(25);
		passwordBox = new JTextField(25);
		fetchButton = new JButton("FETCH TABLES");
		importButton = new JButton("START IMPORT");
		saveButton = new JButton("SAVE PROFILE");
		validateButton = new JButton("VALIDATE IMPORT");
		dupLabel = new JLabel("Duplicate Primary Key Action: ");
		defLabel = new JLabel("     Default Import Strategy: ");
		schemaTable = new JTable(dtmSchema);
		mappingTable = new JTable(dtmMapping);
		schemaPane = new JScrollPane(schemaTable);
		mappingPane = new JScrollPane(mappingTable);
		//setting colors
		dburlBox.setBackground(Color.WHITE);
		driverUrl.setBackground(Color.WHITE);
		passwordBox.setBackground(Color.WHITE);
		userNameBox.setBackground(Color.WHITE);
		duplicateAction.setBackground(Color.WHITE);
		dupLabel.setBackground(Color.WHITE);
		schemaPane.setForeground(Color.WHITE);
		schemaPane.setBackground(Color.WHITE);
		duplicateActionPanel.setForeground(Color.WHITE);
		duplicateActionPanel.setBackground(Color.WHITE);
		credButtonPanel.setForeground(Color.WHITE);
		credButtonPanel.setBackground(Color.WHITE);
		mappingPane.setForeground(Color.WHITE);
		mappingPane.setBackground(Color.WHITE);
		buttonPanel.setForeground(Color.WHITE);
		buttonPanel.setBackground(Color.WHITE);

		buttonPanel.setForeground(Color.WHITE);
		buttonPanel.setBackground(Color.WHITE);
		
		commandPanel = new JPanel(new GridBagLayout());
		
		
		preSessionCommand  = new JTextArea(); 
		postSessionCommand = new JTextArea();
		continueImportOnPreCmdFailureBox = new JCheckBox("Continue Import If PreSession Command Fails",true);
		truncateOverflowingValuesBox =     new JCheckBox("                 Truncate Varchar values if overflowing",false);
		preSessionCommandScroller = new JScrollPane(preSessionCommand); 
		postSessionCommandScroller = new JScrollPane(postSessionCommand);
		
		preSessionCommandLabel = new JLabel("Pre Import Session Command: "); 
		postSessionCommandLabel = new JLabel("Post Import Session Command: ");
		preSessionCommandChooser = new JComboBox();
		preSessionCommandChooser.addItem("SQL COMMAND");
		preSessionCommandChooser.addItem("RUNTIME COMMAND");
		postSessionCommandChooser = new JComboBox();
		postSessionCommandChooser.addItem("SQL COMMAND");
		postSessionCommandChooser.addItem("RUNTIME COMMAND");
		commandPanel.setForeground(Color.WHITE);
		commandPanel.setBackground(Color.WHITE);
	}
}
