package com.victor.multiUtilityToolkit.jms.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;

import java.util.List;
import java.util.Date;

import javax.jms.Message;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.victor.multiUtilityToolkit.bean.StaticBean;
import com.victor.multiUtilityToolkit.jms.constants.JMSConstants;
import com.victor.multiUtilityToolkit.jms.constants.JMSStatusLabelConstants;
import com.victor.multiUtilityToolkit.jms.jmsMethods.GenericJMSMethods;
import com.victor.multiUtilityToolkit.jms.supportMethods.FileMethods;
import com.victor.multiUtilityToolkit.jms.supportMethods.GenericMethods;
import com.victor.multiUtilityToolkit.jms.supportMethods.JMSHeaderTableModel;
import com.victor.multiUtilityToolkit.jms.supportMethods.JMSMessageTableModel;
import com.victor.multiUtilityToolkit.jms.supportMethods.TableListener;
import com.victor.multiUtilityToolkit.jms.supportMethods.ValidationMethods;
import com.victor.multiUtilityToolkit.main.MultiUtilityToolkitMain;

public class MonitorMain extends JPanel{

	private static final long serialVersionUID = -46362995230930491L;
	static Object[] row;
	JPanel mainPanel = null;
	JPanel topButtonPanel = null;
	JPanel rightButtonPanel = null;
	JPanel serverPanel = null;
	JPanel userPanel = null;
	JPanel passPanel = null;
	JPanel queuePanel = null;
	JPanel fieldPanel = null;
	public static String logDetail = "";
	JPanel selPanel = null;
	static JScrollingComboBox serverBox = null;
	static JTextField userId = null;
	List<Message> messages;
	static JLabel selectorLabel = null;
	JTabbedPane tabbedPane = null;
	JPanel statusPanel = null;
	public static JLabel statusLabel = null;
	static JTextField password = null;
	static JScrollingComboBox queueBox = null;
	JButton getQButton = null;
	JButton getDButton = null;
	JButton saveButton = null;
	JButton saveAllButton = null;
	static JButton browseButton = null;
	static JButton selectButton = null;
	JTable messageTable = null;
	static JTable headerTable = null;
	JMSMessageTableModel model = null;
	JMSHeaderTableModel headerModel = null;
	static JTextArea messageArea = null;
	JScrollPane pane = null;
	JScrollPane messagePane = null;
	JScrollPane headerScroller = null;
	static Dimension screenSize = null;
	static GenericJMSMethods genericJMSMethods = null;
	GridBagConstraints position = null;
	public MonitorMain() throws Exception {

		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		row = new Object[3];
		model = new JMSMessageTableModel();	
		model.setDataVector(null, GenericMethods.getTitlesAsArray());
		headerModel = new JMSHeaderTableModel(GenericMethods.getEmptyHeadersAsArray(), GenericMethods.getHeaderNamesAsArray());

		
		statusPanel = new JPanel(new GridBagLayout());
		statusPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
		statusLabel = new JLabel();

		serverPanel = new JPanel(new GridBagLayout());
		serverPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
		userPanel = new JPanel(new GridBagLayout());
		userPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
		passPanel = new JPanel(new GridBagLayout());
		passPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
		queuePanel = new JPanel(new GridBagLayout());
		queuePanel.setBorder(new EmptyBorder(12, 12, 12, 12));
		fieldPanel = new JPanel(new GridBagLayout());
		fieldPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
		selPanel = new JPanel(new GridBagLayout());
		selPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
		messageTable = new JTable(model);
		headerTable = new JTable(headerModel);
		messageArea = new JTextArea();
		saveAllButton = new JButton(JMSConstants.SAVE_ALL); 
		saveButton = new JButton(JMSConstants.SAVE_AS);
		headerScroller = new JScrollPane(headerTable);
		messagePane = new JScrollPane(messageArea);
		messageArea.setEditable(false);
		selectorLabel = new JLabel();
		pane  = new JScrollPane(messageTable);
		messageTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		genericJMSMethods = new GenericJMSMethods();
		position = new GridBagConstraints();
		mainPanel = new JPanel(new GridBagLayout());
		mainPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
		topButtonPanel = new JPanel(new GridBagLayout());
		topButtonPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
		rightButtonPanel = new JPanel(new GridBagLayout());
		rightButtonPanel.setBorder(new EmptyBorder(12, 12, 12, 12));		

		serverBox = new JScrollingComboBox();
		//adding serverList
		List<String> serverList = FileMethods.readServerFile();
		for(int i=0;i<serverList.size();i++){
			serverBox.addItem(serverList.get(i));
		}

		userId = new JTextField();
		password = new JTextField();
		queueBox = new JScrollingComboBox();
		getQButton = new JButton(JMSConstants.GET_QUEUES);

		getQButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(ValidationMethods.areValidFields((String)serverBox.getSelectedItem(),userId.getText(),password.getText())){
					List<String> queues = null;
					row[0]=(new Date()).toString();
					row[1]="Get Queues";
					try {
						statusLabel.setText(JMSStatusLabelConstants.FETCHING);
						logDetail += row[1]+"\n"+JMSStatusLabelConstants.FETCHING;
						queues = genericJMSMethods.getQueuesOnProvider((String) serverBox.getSelectedItem(),userId.getText(),password.getText());
						
						queueBox.removeAllItems();
						for(int i =0;i<queues.size();i++){
							queueBox.addItem(queues.get(i));
						}
						row[2]="Success";
						
						statusLabel.setText(JMSStatusLabelConstants.QUEUES_FETCHED);
						logDetail = logDetail+"\n"+JMSStatusLabelConstants.QUEUES_FETCHED;
					} catch (Exception e1) {
						row[2]="Failure";
						queueBox.removeAllItems();
						logDetail = logDetail+row[2]+"\nException Detail:\n"+e1;
						statusLabel.setText(JMSStatusLabelConstants.QUEUES_NOT_FETCHED);
					}finally{
						try {
							StaticBean.getLogWindow().addLog(row, logDetail);logDetail="";
						} catch (Exception e2) {
							try {
								UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
								JOptionPane.showMessageDialog(MultiUtilityToolkitMain.multiUtilityToolkitMain, e2+"\n"+"Please contact Technical Support", "Exception caught while trying to create logs", JOptionPane.ERROR_MESSAGE);
							} catch (Exception e1) {
								e1.printStackTrace();
							}							
						}
					}
				}else{
					statusLabel.setText(JMSStatusLabelConstants.VERIFY_INPUTS_FOR_FETCH);
				}
			}
		});

		getDButton = new JButton(JMSConstants.GET_DESTINATIONS);
		getDButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(ValidationMethods.areValidFields((String)serverBox.getSelectedItem(),userId.getText(),password.getText())){
					statusLabel.setText(JMSStatusLabelConstants.FETCHING);
					List<String> queues = null;
					row[0]=(new Date()).toString();
					row[1]="Get Destinations";
					logDetail +=row[1]+"\n";
					try {
						logDetail=logDetail+JMSStatusLabelConstants.FETCHING+"\n";
						queues = genericJMSMethods.getDestsOnProvider((String) serverBox.getSelectedItem(),userId.getText(),password.getText());
						
						queueBox.removeAllItems();
						for(int i =0;i<queues.size();i++){
							queueBox.addItem(queues.get(i));
						}
						row[2]="Success";
						logDetail+=JMSStatusLabelConstants.DESTS_FETCHED;
						statusLabel.setText(JMSStatusLabelConstants.DESTS_FETCHED);
					} catch (Exception e1) {
						queueBox.removeAllItems();
						statusLabel.setText(JMSStatusLabelConstants.DESTS_NOT_FETCHED);
						row[2]="Failure";
						logDetail+=row[2]+"\nException Detail:\n"+e1;
					}finally{
						try {
							StaticBean.getLogWindow().addLog(row, logDetail);logDetail="";
						} catch (Exception e1) {
							try {
								UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
								JOptionPane.showMessageDialog(MultiUtilityToolkitMain.multiUtilityToolkitMain, e1+"\n"+"Please contact Technical Support", "Exception caught while trying to create logs", JOptionPane.ERROR_MESSAGE);
							} catch (Exception e4) {
								e4.printStackTrace();
							}	
						}
					}
				}else{
					statusLabel.setText(JMSStatusLabelConstants.VERIFY_INPUTS_FOR_FETCH);
				}
			}		
		});
		browseButton = new JButton(JMSConstants.BROWSE_ENTIRE_QUEUE);
		browseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(ValidationMethods.areValidFields((String)serverBox.getSelectedItem(),userId.getText(),password.getText()) && queueBox.getSelectedItem().toString().trim().length() > 0){
					row[0]=(new Date()).toString();
					row[1]="Browse Queue";
					logDetail +=row[1]+"\n";
					try {
						messages = null;
						statusLabel.setText(JMSStatusLabelConstants.BROWSING);
						logDetail+=JMSStatusLabelConstants.BROWSING+"\n";
						messageArea.setText(JMSConstants.EMPTY_STRING);
						selectorLabel.setText(JMSConstants.EMPTY_STRING);
						messages = genericJMSMethods.browseEntireQueue((String) serverBox.getSelectedItem(),userId.getText(),password.getText(), (String) queueBox.getSelectedItem());
						
						Object[][] data = GenericMethods.getDataAsArray(messages);
						Object[] titles = GenericMethods.getTitlesAsArray();
						model.setDataVector(null, titles);
						headerModel.setDataVector(GenericMethods.getEmptyHeadersAsArray(), GenericMethods.getHeaderNamesAsArray());
						headerTable.setModel(headerModel);
						messageTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
						if(messages!=null && messages.size()>0){
							logDetail+="Messages Found!!\n";
							model.setDataVector(data, titles);
							messageTable.getSelectionModel().addListSelectionListener(new TableListener(headerTable, messageArea, messages));
						}else{
							logDetail+="No Messages Found!!\n";
						}
						row[2]="Success";
						statusLabel.setText(JMSStatusLabelConstants.BROWSING_COMPLETE);	
						logDetail+=JMSStatusLabelConstants.BROWSING_COMPLETE;
					} catch (Exception e1) {
						
						row[2]="Failure";
						logDetail+=row[2]+"\nException Detail:\n"+e1+"\n"+JMSStatusLabelConstants.BROWSING_NOT_COMPLETE;
						statusLabel.setText(JMSStatusLabelConstants.BROWSING_NOT_COMPLETE);
					}finally{
						try {
							StaticBean.getLogWindow().addLog(row, logDetail);logDetail="";
						} catch (Exception e1) {
							try {
								UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
								JOptionPane.showMessageDialog(MultiUtilityToolkitMain.multiUtilityToolkitMain, e1+"\n"+"Please contact Technical Support", "Exception caught while trying to create logs", JOptionPane.ERROR_MESSAGE);
							} catch (Exception e2) {
								e2.printStackTrace();
							}	
						}						
					}
				}else{
					statusLabel.setText(JMSStatusLabelConstants.VERIFY_INPUTS_FOR_BROWSE);
				}					
			}
		});

		selectButton = new JButton(JMSConstants.BROWSE_USING_SELECTOR);
		selectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(ValidationMethods.areValidFields((String)serverBox.getSelectedItem(),userId.getText(),password.getText()) && queueBox.getSelectedItem().toString().trim().length() > 0){
					messageArea.setText(JMSConstants.EMPTY_STRING);
					selectorLabel.setText((new PickSelectorDialog()).getSelector());
					if(selectorLabel.getText()!=null){
						statusLabel.setText(JMSStatusLabelConstants.SEARCHING);
						row[0]=(new Date()).toString();
						row[1]="Browse Queue with Selector";
						logDetail +=row[1]+"\n"+JMSStatusLabelConstants.SEARCHING;
						try {
							messages = null;
							messages = genericJMSMethods.browseWithSelector((String) serverBox.getSelectedItem(),userId.getText(),password.getText(), (String) queueBox.getSelectedItem(), selectorLabel.getText());
							
							Object[][] data = GenericMethods.getDataAsArray(messages);
							Object[] titles = GenericMethods.getTitlesAsArray();
							model.setDataVector(null, titles);
							headerModel.setDataVector(GenericMethods.getEmptyHeadersAsArray(), GenericMethods.getHeaderNamesAsArray());
							headerTable.setModel(headerModel);
							messageTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
							if(messages!=null && messages.size()>0){
								logDetail+="Messages Found!!\n";
								model.setDataVector(data, titles);
								messageTable.getSelectionModel().addListSelectionListener(new TableListener(headerTable, messageArea, messages));
							}else{
								logDetail+="No Messages Found!!";
							}
							row[2]="Success";
							statusLabel.setText(JMSStatusLabelConstants.SEARCH_COMPLETE);
							logDetail+=row[2]+"\n"+JMSStatusLabelConstants.SEARCH_COMPLETE;
						} catch (Exception e1) {
							row[2]="Failure";
							statusLabel.setText(JMSStatusLabelConstants.SEARCH_NOT_COMPLETE);
							logDetail+=row[2]+"\nException Detail:\n"+e1+"\n"+JMSStatusLabelConstants.SEARCH_NOT_COMPLETE;
						}finally{
							try {
								StaticBean.getLogWindow().addLog(row, logDetail);logDetail="";
							} catch (Exception e1) {
								try {
									UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
									JOptionPane.showMessageDialog(MultiUtilityToolkitMain.multiUtilityToolkitMain, e1+"\n"+"Please contact Technical Support", "Exception caught while trying to create logs", JOptionPane.ERROR_MESSAGE);
								} catch (Exception e2) {
									e2.printStackTrace();
								}	
							}	
						}
					}
				}else{
					statusLabel.setText(JMSStatusLabelConstants.VERIFY_INPUTS_FOR_BROWSE);
				}
			}
		});
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(messageArea.getText().trim().length() > 0){
					JFileChooser saveWindow = new JFileChooser();
					int rVal = saveWindow.showSaveDialog(MonitorMain.this);
					if (rVal == JFileChooser.APPROVE_OPTION) {
						statusLabel.setText(JMSStatusLabelConstants.SAVING);
						String file=saveWindow.getCurrentDirectory().toString()+JMSConstants.DOUBLE_BACK_SLASH+saveWindow.getSelectedFile().getName();
						row[0]=(new Date()).toString();
						row[1]="Save Message";
						logDetail +=row[1]+"\n"+JMSStatusLabelConstants.SAVING+"\n";
						try {
							GenericMethods.saveContentsToFile(file, messageArea.getText());
							row[2]="Success";
							logDetail+=row[2];
						} catch (Exception e1) {
							row[2]="Failure";
							statusLabel.setText(JMSStatusLabelConstants.SAVE_NOT_COMPLETED);
							logDetail+=row[2]+"\nException Detail:\n"+e1+"\n"+JMSStatusLabelConstants.SAVE_NOT_COMPLETED;
						}finally{
							try {
								StaticBean.getLogWindow().addLog(row, logDetail);logDetail="";
							} catch (Exception e1) {
								try {
									UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
									JOptionPane.showMessageDialog(MultiUtilityToolkitMain.multiUtilityToolkitMain, e1+"\n"+"Please contact Technical Support", "Exception caught while trying to create logs", JOptionPane.ERROR_MESSAGE);
								} catch (Exception e2) {
									e2.printStackTrace();
								}
							}
						}
						statusLabel.setText(JMSStatusLabelConstants.SAVE_COMPLETE);
					}
				}else{
					statusLabel.setText(JMSStatusLabelConstants.NO_CONTENT_TO_SAVE);
				}
			}
		});
		saveAllButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(messages!=null && messages.size() > 0){
					JFileChooser saveAllWindow = new JFileChooser();
					int rVal = saveAllWindow.showSaveDialog(MonitorMain.this);
					if (rVal == JFileChooser.APPROVE_OPTION) {
						row[0]=(new Date()).toString();
						row[1]="Save All";
						statusLabel.setText(JMSStatusLabelConstants.SAVING_ALL);
						String dir=saveAllWindow.getCurrentDirectory().toString()+JMSConstants.DOUBLE_BACK_SLASH+saveAllWindow.getSelectedFile().getName();
						logDetail +=row[1]+"\n"+JMSStatusLabelConstants.SAVING_ALL+"\n";
						try {
							FileMethods.saveAllToFiles(messages, dir);
							logDetail+=row[2];
							row[2]="Success";
						} catch (Exception e1) {
							row[2]="Failure";
							statusLabel.setText(JMSStatusLabelConstants.SAVE_ALL_FAIL);
							logDetail+=row[2]+"\nException Detail:\n"+e1+"\n"+JMSStatusLabelConstants.SAVE_ALL_FAIL;
						}finally{
							try {
								StaticBean.getLogWindow().addLog(row, logDetail);logDetail="";
							} catch (Exception e1) {
								try {
									UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
									JOptionPane.showMessageDialog(MultiUtilityToolkitMain.multiUtilityToolkitMain, e1+"\n"+"Please contact Technical Support", "Exception caught while trying to create logs", JOptionPane.ERROR_MESSAGE);
								} catch (Exception e2) {
									e2.printStackTrace();
								}
							}
						}
						statusLabel.setText(JMSStatusLabelConstants.SAVE_ALL_COMPLETE);
					}
				}else{
					statusLabel.setText(JMSStatusLabelConstants.NO_MESSAGES_FOUND);
				}
			}
		});
		position.gridx = 0;
		position.gridy = 0;
		topButtonPanel.add(getQButton, position);
		position.gridx = 1;
		position.gridy = 0;
		topButtonPanel.add(getDButton, position);	

		position.gridx = 0;
		position.gridy = 0;
		rightButtonPanel.add(browseButton, position);
		position.gridx = 1;
		position.gridy = 0;
		rightButtonPanel.add(selectButton, position);

		tabbedPane = new JTabbedPane();
		tabbedPane.addChangeListener(new ChangeListener() {	
			public void stateChanged(ChangeEvent e) {
				if(tabbedPane.getSelectedIndex()==1){
					messageArea.setEditable(true);
					messageArea.setText(JMSConstants.EMPTY_STRING);
				}else if(tabbedPane.getSelectedIndex()==0){
					messageArea.setEditable(false);
					messageArea.setText(JMSConstants.EMPTY_STRING);
				}
			}
		});		
		tabbedPane.addTab(JMSConstants.BROWSE, rightButtonPanel);
		tabbedPane.addTab(JMSConstants.SEND_DELETE, new SendPanel());
		tabbedPane.addTab(JMSConstants.ADD_SERVER, new AddServerDialog());
		//setting sizes
		userId.setPreferredSize(new Dimension((int)screenSize.getWidth()/6 ,20));
		password.setPreferredSize(new Dimension((int)screenSize.getWidth()/6,20));
		serverBox.setPreferredSize(new Dimension((int)screenSize.getWidth()/6,20));
		setPreferredSize(new Dimension((int)screenSize.getWidth()*3/4,(int)screenSize.getHeight()*3/4));
		queueBox.setPreferredSize(new Dimension((int)screenSize.getWidth()/5,20));
		messagePane.setPreferredSize(new Dimension((int)screenSize.getWidth()/3,(int)screenSize.getHeight()*2/5));
		pane.setPreferredSize(new Dimension((int)screenSize.getWidth()/3, (int)screenSize.getHeight()*2/9));
		headerScroller.setPreferredSize(new Dimension((int)screenSize.getWidth()/3, (int)screenSize.getHeight()*2/9));
		//
		position.gridx = 0;
		position.gridy = 0;
		serverPanel.add(new JLabel(JMSConstants.SERVER_TEXT), position);
		position.gridx = 1;
		position.gridy = 0;
		serverPanel.add(serverBox, position);
		position.gridx = 0;
		position.gridy = 0;
		userPanel.add(new JLabel(JMSConstants.USER_TEXT), position);
		position.gridx = 1;
		position.gridy = 0;
		userPanel.add(userId, position);
		position.gridx = 0;
		position.gridy = 0;
		passPanel.add(new JLabel(JMSConstants.PASS_TEXT), position);
		position.gridx = 1;
		position.gridy = 0;
		passPanel.add(password, position);
		position.gridx = 0;
		position.gridy = 0;
		queuePanel.add(new JLabel(JMSConstants.QUEUE_TEXT), position);
		position.gridx = 1;
		position.gridy = 0;
		queuePanel.add(queueBox, position);
		position.gridx = 0;
		position.gridy = 0;
		selPanel.add(new JLabel(JMSConstants.SELECTOR_TEXT), position);
		position.gridx = 1;
		position.gridy = 0;
		selPanel.add(selectorLabel, position);

		position.gridx = 0;
		position.gridy = 0;
		statusPanel.add(new JLabel(JMSConstants.STATUS_TEXT), position);
		position.gridx = 1;
		position.gridy = 0;
		statusPanel.add(statusLabel, position);
		
		//adding 0
		position.gridx = 0;
		position.gridy = 0;
		fieldPanel.add(topButtonPanel, position);
		position.gridx = 0;
		position.gridy = 1;
		fieldPanel.add(serverPanel, position);
		position.gridx = 0;
		position.gridy = 2;
		fieldPanel.add(userPanel, position);
		position.gridx = 0;
		position.gridy = 3;
		fieldPanel.add(passPanel, position);
		position.gridx = 0;
		position.gridy = 4;
		fieldPanel.add(queuePanel, position);	
		position.gridx = 0;
		position.gridy = 5;
		fieldPanel.add(selPanel, position);
		position.gridx = 0;
		position.gridy = 6;
		fieldPanel.add(tabbedPane, position);
		position.gridx = 0;
		position.gridy = 7;
		fieldPanel.add(statusPanel, position);

		position.gridx = 0;
		position.gridy = 0;		
		mainPanel.add(fieldPanel, position);
		position.gridx = 0;
		position.gridy = 1;
		mainPanel.add(saveAllButton, position);
		position.gridx = 0;
		position.gridy = 2;
		mainPanel.add(pane, position);		
		//adding 1
		position.gridx = 1;
		position.gridy = 1;
		mainPanel.add(saveButton, position);
		position.gridx = 1;
		position.gridy = 2;
		mainPanel.add(headerScroller, position);	
		position.gridx = 1;
		position.gridy = 0;
		mainPanel.add(messagePane, position);
		add(mainPanel);
		
		//setting color 

		messageTable.setBackground(Color.WHITE);
		headerTable.setBackground(Color.WHITE);
		pane.setForeground(Color.WHITE);
		pane.setBackground(Color.WHITE);		
		headerScroller.setForeground(Color.WHITE);
		headerScroller.setBackground(Color.WHITE);
		messagePane.setForeground(Color.WHITE);
		messagePane.setBackground(Color.WHITE);
		
		mainPanel.setForeground(Color.WHITE);
		mainPanel.setBackground(Color.WHITE);
		serverPanel.setForeground(Color.WHITE);
		serverPanel.setBackground(Color.WHITE);
		userPanel.setForeground(Color.WHITE);
		userPanel.setBackground(Color.WHITE);
		
		
		passPanel.setForeground(Color.WHITE);
		passPanel.setBackground(Color.WHITE);
		queuePanel.setForeground(Color.WHITE);
		queuePanel.setBackground(Color.WHITE);
		fieldPanel.setForeground(Color.WHITE);
		fieldPanel.setBackground(Color.WHITE);
		selPanel.setForeground(Color.WHITE);
		selPanel.setBackground(Color.WHITE);
		
		setForeground(Color.WHITE);
		setBackground(Color.WHITE);
		
		topButtonPanel.setForeground(Color.WHITE);
		topButtonPanel.setBackground(Color.WHITE);
		rightButtonPanel.setForeground(Color.WHITE);
		rightButtonPanel.setBackground(Color.WHITE);
		statusLabel.setBackground(Color.WHITE);
		statusPanel.setForeground(Color.WHITE);
		statusPanel.setBackground(Color.WHITE);
		tabbedPane.setBackground(Color.WHITE);
		//
		
	}
}
class SendPanel extends JPanel{
	private static final long serialVersionUID = -1367862328541369651L;
	JButton browseFileButton;
	JButton sendButton;
	JButton deleteButton;
	JFileChooser xmlFileInput;
	JTextField filePath;
	GridBagConstraints position;
	public SendPanel(){
		filePath = new JTextField();
		setLayout(new GridBagLayout());
		position = new GridBagConstraints();
		browseFileButton = new JButton(JMSConstants.SELECT_FILE_TEXT);
		sendButton = new JButton(JMSConstants.SEND_TEXT);
		deleteButton = new JButton(JMSConstants.DELETE_TEXT);
		xmlFileInput = new JFileChooser();
		browseFileButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int returnVal = xmlFileInput.showOpenDialog(getParent());
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					filePath.setText(xmlFileInput.getSelectedFile().getAbsolutePath());

				}			
			}
		});
		filePath.setEditable(false);
		filePath.setPreferredSize(new Dimension((int)MonitorMain.screenSize.getWidth()/5,20));
		sendButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String str = null;
				String trimmedXML = JMSConstants.EMPTY_STRING;
				MonitorMain.row[0]=(new Date()).toString();
				MonitorMain.row[1]="Send Message";
				try {
					if(filePath.getText().trim().length() > 0){
						BufferedReader in = new BufferedReader(
								new FileReader(filePath.getText()));
						while ((str = in.readLine()) != null) {
							String str1 = str;
							if (str1.length() > 0) {
								str1 = str1.trim();
								if (str1.charAt(str1.length() - 1) == '>') {
									trimmedXML =
										trimmedXML + str.trim();
								} else {
									trimmedXML = trimmedXML + str;
								}
							}
						}
						in.close();
					}else if(filePath.getText().trim().length()==0 && MonitorMain.messageArea.getText().trim().length() > 0){
						trimmedXML=MonitorMain.messageArea.getText();
					}
					if(trimmedXML.trim().length() > 0){
						if(ValidationMethods.areValidFields((String)MonitorMain.serverBox.getSelectedItem(),MonitorMain.userId.getText(),MonitorMain.password.getText()) && MonitorMain.queueBox.getSelectedItem().toString().trim().length() > 0){
							MonitorMain.statusLabel.setText(JMSStatusLabelConstants.MESSAGE_SENDING);
							MonitorMain.logDetail +=MonitorMain.row[1]+"\n"+JMSStatusLabelConstants.MESSAGE_SENDING+"\n";
							MonitorMain.genericJMSMethods.sendToQueue((String) MonitorMain.serverBox.getSelectedItem(),MonitorMain.userId.getText(),MonitorMain.password.getText(), (String) MonitorMain.queueBox.getSelectedItem(), trimmedXML); 
							filePath.setText(JMSConstants.EMPTY_STRING);
							MonitorMain.row[2]="Success";
							MonitorMain.statusLabel.setText(JMSStatusLabelConstants.MESSAGE_SENT);
							MonitorMain.logDetail+=MonitorMain.row[2]+"\n"+JMSStatusLabelConstants.MESSAGE_SENT;
						}else{
							MonitorMain.row[2]="Incomplete";
							MonitorMain.statusLabel.setText(JMSStatusLabelConstants.VERIFY_INPUTS_FOR_BROWSE);
							MonitorMain.logDetail+=MonitorMain.row[2]+JMSStatusLabelConstants.VERIFY_INPUTS_FOR_BROWSE;
						}
					}else{
						MonitorMain.row[2]="Failure";
						MonitorMain.statusLabel.setText(JMSStatusLabelConstants.INVALID_MESSAGE);
						MonitorMain.logDetail+=MonitorMain.row[2]+JMSStatusLabelConstants.INVALID_MESSAGE;
					}
				}catch(Exception e2){
					MonitorMain.row[2]="Failure";
					MonitorMain.statusLabel.setText(JMSStatusLabelConstants.MESSAGE_NOT_SENT);
					MonitorMain.logDetail+=MonitorMain.row[2]+"\nException Detail:\n"+e2+"\n"+JMSStatusLabelConstants.MESSAGE_NOT_SENT;
				}finally{
					try {
						StaticBean.getLogWindow().addLog(MonitorMain.row, MonitorMain.logDetail);
						MonitorMain.logDetail="";
					} catch (Exception e1) {
						try {
							UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
							JOptionPane.showMessageDialog(MultiUtilityToolkitMain.multiUtilityToolkitMain, e1+"\n"+"Please contact Technical Support", "Exception caught while trying to create logs", JOptionPane.ERROR_MESSAGE);
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				}
			}
		});
		deleteButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(MonitorMain.headerTable.getValueAt(0, 1).toString().trim().length()>0){
					MonitorMain.row[0]=(new Date()).toString();
					MonitorMain.row[1]="Delete Message";
					try {
						MonitorMain.statusLabel.setText(JMSStatusLabelConstants.MESSAGE_DELETING);
						MonitorMain.logDetail +=MonitorMain.row[1]+"\n"+JMSStatusLabelConstants.MESSAGE_DELETING+"\n";
						MonitorMain.genericJMSMethods.deleteMessage((String) MonitorMain.serverBox.getSelectedItem(),MonitorMain.userId.getText(),MonitorMain.password.getText(), (String) MonitorMain.queueBox.getSelectedItem(), (String) MonitorMain.headerTable.getValueAt(0, 1));
						MonitorMain.row[2]="Success";
						MonitorMain.statusLabel.setText(JMSStatusLabelConstants.MESSAGE_DELETED);
						MonitorMain.logDetail+=MonitorMain.row[2]+"\n"+JMSStatusLabelConstants.MESSAGE_DELETED;						
					} catch (Exception e1) {
						MonitorMain.row[2]="Failure";
						MonitorMain.statusLabel.setText(JMSStatusLabelConstants.MESSAGE_NOT_DELETED);
						MonitorMain.logDetail+=MonitorMain.row[2]+"\nException Detail:\n"+e1+"\n"+JMSStatusLabelConstants.MESSAGE_NOT_DELETED;
					}finally{
						try {
							if(MonitorMain.selectorLabel.getText().length()==0){
								MonitorMain.browseButton.doClick();
							}else{
								MonitorMain.selectButton.doClick();
							}
							StaticBean.getLogWindow().addLog(MonitorMain.row, MonitorMain.logDetail);
							MonitorMain.logDetail="";
						} catch (Exception e1) {
							try {
								UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
								JOptionPane.showMessageDialog(MultiUtilityToolkitMain.multiUtilityToolkitMain, e1+"\n"+"Please contact Technical Support", "Exception caught while trying to create logs", JOptionPane.ERROR_MESSAGE);
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}
					}
				}else{
					MonitorMain.statusLabel.setText(JMSStatusLabelConstants.NO_MESSAGE_SELECTED);
				}
			}
		});
		position.gridx = 0;
		position.gridy = 0;
		add(filePath, position);
		position.gridx = 1;
		position.gridy = 0;
		add(browseFileButton, position);
		position.gridx = 0;
		position.gridy = 1;
		add(sendButton, position);
		position.gridx = 1;
		position.gridy = 1;
		add(deleteButton, position);
		
		filePath.setBackground(Color.WHITE);
		browseFileButton.setBackground(Color.WHITE);
		sendButton.setBackground(Color.WHITE);
		deleteButton.setBackground(Color.WHITE);
		xmlFileInput.setBackground(Color.WHITE);
		setForeground(Color.WHITE);
		setBackground(Color.WHITE);
	}
}
class AddServerDialog extends JPanel{
	private static final long serialVersionUID = 4824623038028772069L;
	JButton addButton;
	JTextField serverField;
	GridBagConstraints position;
	public AddServerDialog() throws Exception{

		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		position = new GridBagConstraints();
		setLayout(new GridBagLayout());
		addButton = new JButton(JMSConstants.ADD_SERVER);
		serverField = new JTextField(25);
		position.gridx=0;
		position.gridy=0;
		add(new JLabel(JMSConstants.SERVER_NAME_TEXT), position);
		position.gridx=1;
		position.gridy=0;
		add(serverField, position);
		position.gridx=2;
		position.gridy=0;
		add(addButton, position);
		

		addButton.setBackground(Color.WHITE);
		serverField.setBackground(Color.WHITE);
		setForeground(Color.WHITE);
		setBackground(Color.WHITE);
		
		addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					MonitorMain.row[0]=(new Date()).toString();
					MonitorMain.row[1]="Add Server";
					if(ValidationMethods.isValidServerUrl(serverField.getText())){
						if(GenericMethods.srvrExists(serverField.getText())){
							MonitorMain.row[2]="Incomplete";
							MonitorMain.statusLabel.setText(JMSStatusLabelConstants.SERVER_PRESENT);
							MonitorMain.logDetail +=MonitorMain.row[1]+"\n"+JMSStatusLabelConstants.SERVER_PRESENT;
						}else{
							MonitorMain.statusLabel.setText(JMSStatusLabelConstants.ADDING_SERVER);
							MonitorMain.logDetail=MonitorMain.row[1]+"\n"+JMSStatusLabelConstants.ADDING_SERVER;
							FileMethods.addServer(serverField.getText());
							MonitorMain.serverBox.removeAllItems();
							List<String> serverList = FileMethods.readServerFile();
							for(int i=0;i<serverList.size();i++){
								MonitorMain.serverBox.addItem(serverList.get(i));
							}
							MonitorMain.row[2]="Success";
							MonitorMain.statusLabel.setText(JMSStatusLabelConstants.ADD_SERVER_SUCCESS);
							MonitorMain.logDetail+="\n"+MonitorMain.row[2]+"\n"+JMSStatusLabelConstants.ADD_SERVER_SUCCESS;
						}
					}else{
						MonitorMain.row[2]="Incomplete";
						MonitorMain.statusLabel.setText(JMSStatusLabelConstants.URL_NOT_VALID);
						MonitorMain.logDetail+="\n"+MonitorMain.row[2]+"\n"+JMSStatusLabelConstants.URL_NOT_VALID;
					}
				} catch (Exception e1) {
					MonitorMain.row[2]="Failure";
					MonitorMain.statusLabel.setText(JMSStatusLabelConstants.SERVER_NOT_ADDED);
					MonitorMain.logDetail+=MonitorMain.row[2]+"\nException Detail:\n"+e1+"\n"+JMSStatusLabelConstants.SERVER_NOT_ADDED;
				}finally{
					try {
						StaticBean.getLogWindow().addLog(MonitorMain.row, MonitorMain.logDetail);
						MonitorMain.logDetail="";
					} catch (Exception e1) {
						try {
							UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
							JOptionPane.showMessageDialog(MultiUtilityToolkitMain.multiUtilityToolkitMain, e1+"\n"+"Please contact Technical Support", "Exception caught while trying to create logs", JOptionPane.ERROR_MESSAGE);
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				}
			}
		});
	}
}

