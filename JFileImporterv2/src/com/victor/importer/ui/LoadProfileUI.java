package com.victor.importer.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;


import com.victor.importer.constants.ImporterBean;
import com.victor.importer.constants.ProfileBean;
import com.victor.importer.profiles.ReadXMLProfile;


public class LoadProfileUI extends JPanel{

	private static final long serialVersionUID = -1025497815134063922L;
	JTable columnMappingTable,optionTable;
	ViewOnlyModel columnMappingModel,optionModel;
	JScrollPane columnPane, optionPane;
	Dimension screenSize;
	JButton openProfileButton;
	ProfileBean profileBean;
	JTabbedPane tabs;
	Thread importSchedulerSplashThr;
	ImportSchedulerSplash schedulerSplash;
	static JDialog frame;
	GridBagConstraints bagConstraints;
	static int i = 0;
	public LoadProfileUI() throws Exception{	
		
		schedulerSplash = new ImportSchedulerSplash();
		if(i==0){
			i++;
			importSchedulerSplashThr = new Thread(schedulerSplash, "Splash");
			importSchedulerSplashThr.start();
		}
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		tabs = new JTabbedPane();
		bagConstraints = new GridBagConstraints();
		setLayout(new GridBagLayout());
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		openProfileButton = new JButton("Open Profile");
		columnMappingModel = new ViewOnlyModel();
		columnMappingTable = new JTable(columnMappingModel);
		columnPane = new JScrollPane(columnMappingTable);
		optionModel = new ViewOnlyModel();
		optionTable = new JTable(optionModel);
		optionPane = new JScrollPane(optionTable);
		columnPane.setPreferredSize(new Dimension((int)(screenSize.getWidth()*2/4),(int)screenSize.getHeight()/4));
		optionPane.setPreferredSize(new Dimension((int)(screenSize.getWidth()*2/4),(int)screenSize.getHeight()/5));
		//tabs.setPreferredSize(new Dimension((int)(screenSize.getWidth()*3/4),(int)screenSize.getHeight()/5));
		openProfileButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String xmlFile = null;
				JFileChooser xmlFileOpener = new JFileChooser();
				int returnVal = xmlFileOpener.showOpenDialog(openProfileButton);
				if(returnVal == JFileChooser.APPROVE_OPTION){
					try {
						xmlFile = xmlFileOpener.getSelectedFile().getAbsolutePath();
						Object[] titleTable= {"Table Column","Column Data Type", "Max Length", "Nullable", "Primary Key Column","File Columns"};
						Object[] optionTable= {"Property","Value"};
						profileBean = ReadXMLProfile.openProfile(xmlFile);
						ImporterBean.setLoadedProfile(profileBean);
						columnMappingModel.setDataVector(profileBean.getColumnMapping(), titleTable);
						optionModel.setDataVector(profileBean.getOptionMapping(), optionTable);
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(openProfileButton, e1, "File Open Exception", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		tabs.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {

				if(tabs.getSelectedIndex()==3){
					if(columnMappingModel==null || (columnMappingModel!=null && columnMappingModel.getRowCount() == 0)){
						JOptionPane.showMessageDialog(tabs, "Please open a profile before accessing this tab!!", "No profile Selected", JOptionPane.ERROR_MESSAGE);
						tabs.setSelectedIndex(0);
					}
				}
			}
		});
		tabs.addTab("Splash",schedulerSplash);
		tabs.addTab("Column Mapping",columnPane);
		tabs.addTab("Import Options",optionPane);
		tabs.addTab("Schedule Settings",new ScheduleSettingsUI());
		
		bagConstraints.gridx=0;
		bagConstraints.gridy=0;
		add(openProfileButton,bagConstraints);
		bagConstraints.gridx=0;
		bagConstraints.gridy=1;
		add(tabs,bagConstraints);
		//setting colors
		setForeground(Color.WHITE);
		setBackground(Color.WHITE);		 
		
		columnPane.setForeground(Color.WHITE);
		columnPane.setBackground(Color.WHITE);
		optionPane.setForeground(Color.WHITE);
		optionPane.setBackground(Color.WHITE);
		optionTable.setBackground(Color.WHITE);
		columnMappingTable.setBackground(Color.WHITE);
	}
	public void createAndShowGUI() {
		frame = new JDialog();
		Image im = Toolkit.getDefaultToolkit().getImage(LoadProfileUI.class.getResource("/config/mut3.png"));
		frame.setIconImage(im);
		frame.setTitle("Import Scheduler v1.0");
		frame.setForeground(Color.WHITE);
		frame.setBackground(Color.WHITE);	
		//frame.setPreferredSize(new Dimension(600,600));
		try {
			frame.add(new LoadProfileUI());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Scheduler Initialization Error: "+e, "Error Starting Scheduler!!", JOptionPane.ERROR_MESSAGE);
		}	

		frame.pack();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		frame.setVisible(true);
		
	}
	public static void main(String[] args) {
		try {
			(new LoadProfileUI()). createAndShowGUI();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error Starting Scheduler: "+e, "Error Initializing Scheduler", JOptionPane.ERROR_MESSAGE);
		}
	}
	public void runSplash(){
		schedulerSplash.run();
	}
	public static String showScheduler(){
		String opened = "success";
		try{	
			frame.setVisible(true);
			
		}catch(NullPointerException npe){
			opened = "npe";
		}catch(Exception e){
			opened = "ge";
		}
		return opened;
	}
}
class ViewOnlyModel extends DefaultTableModel{

	private static final long serialVersionUID = 1L;
	
	public ViewOnlyModel(){
		super();
	}
	public ViewOnlyModel(Object[][] data, Object[] columnNames){
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
		return false;
	}
	public void setValueAt(Object value, int row, int col) {
		super.setValueAt(value, row, col);
	}
}