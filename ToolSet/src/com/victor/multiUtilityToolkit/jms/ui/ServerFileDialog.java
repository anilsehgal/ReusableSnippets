package com.victor.multiUtilityToolkit.jms.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.victor.multiUtilityToolkit.jms.constants.JMSConstants;
import com.victor.multiUtilityToolkit.jms.supportMethods.FileMethods;

public class ServerFileDialog extends JDialog{

	private static final long serialVersionUID = 5732704172486177677L;
	static JTextArea label;
	JScrollPane scroller;
	public ServerFileDialog(boolean editable) throws Exception{
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		label = new JTextArea(getServers(),100,50);
		label.setEditable(editable);
		setResizable(false);
		if(editable){
			setTitle("serverConfig.txt (View & Edit)");
			setJMenuBar(getServerEditorMenuBar());
		}else{
			setTitle("serverConfig.txt (View Only)");
		}
		scroller = new JScrollPane(label);
		scroller.setPreferredSize(new Dimension(800,400));
		setPreferredSize(new Dimension(800,400));
		setLocation((int)dim.getWidth()/2-400,(int)dim.getHeight()/2-200);
		pack();
		setModal(true);
		add(scroller, BorderLayout.CENTER);
		setVisible(true);
	}
	public static String getServers() throws Exception{
		String returnString = "";
		List<String> servers = FileMethods.readServerFile();
		for(String server:servers){
			returnString=returnString+server+"\n";
		}
		return returnString;
	}public static JMenuBar getServerEditorMenuBar(){
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem saveItem = new JMenuItem("Save");
		saveItem.setMnemonic(KeyEvent.VK_S);
		fileMenu.add(saveItem);
		saveItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					modifyEntireServerFile(label.getText());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		menuBar.add(fileMenu);
		return menuBar;
	}
	public static void modifyEntireServerFile(String servers) throws Exception{
		FileWriter wr = new FileWriter(JMSConstants.JMS_SERVER_FILE_PATH, false);
		wr.write(servers);
		wr.close();
		MonitorMain.serverBox.removeAllItems();
		List<String> serverList = FileMethods.readServerFile();
		for(String server:serverList){
			MonitorMain.serverBox.addItem(server);
		}
	}
}
