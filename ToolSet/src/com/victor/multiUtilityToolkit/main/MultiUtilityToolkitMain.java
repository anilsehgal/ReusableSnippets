package com.victor.multiUtilityToolkit.main;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JWindow;
import javax.swing.UIManager;

import com.victor.multiUtilityToolkit.bean.StaticBean;
import com.victor.multiUtilityToolkit.importer.ui.ImporterUI;
import com.victor.multiUtilityToolkit.jms.constants.JMSConstants;
import com.victor.multiUtilityToolkit.jms.ui.MonitorMain;
import com.victor.multiUtilityToolkit.jms.ui.StartUpWindow;
import com.victor.multiUtilityToolkit.logging.supportMethods.FileLogSupportMethods;
import com.victor.multiUtilityToolkit.logging.ui.LogWindow;

public class MultiUtilityToolkitMain extends JFrame{
	private static final long serialVersionUID = -5147216618372411451L;
	JTabbedPane tabs;
	JPanel mainPanel;
	JPanel mainLogPanel;
	public static MultiUtilityToolkitMain multiUtilityToolkitMain;
	JScrollPane monitorScroller = null;
	public MultiUtilityToolkitMain() throws Exception{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		GridBagConstraints pos = new GridBagConstraints();

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		mainPanel = new JPanel();
		monitorScroller = new JScrollPane(new MonitorMain());
		mainLogPanel = new JPanel(new GridBagLayout());
		mainPanel.add(monitorScroller);
		tabs = new JTabbedPane();
		tabs.addTab(JMSConstants.JMS_MODULES, mainPanel);
		setPreferredSize(new Dimension((int)dim.getWidth(), (int)(dim.getHeight()-27)));
		pos.gridx=0;
		pos.gridy=0;
		mainLogPanel.add(tabs,pos);
		pos.gridx=0;
		pos.gridy=1;
		mainLogPanel.add(StaticBean.getLogWindow(),pos);
		//Setting Colors
		mainPanel.setForeground(Color.WHITE);
		mainPanel.setBackground(Color.WHITE);
		mainLogPanel.setForeground(Color.WHITE);
		mainLogPanel.setBackground(Color.WHITE);
		tabs.setBackground(Color.WHITE);
		setJMenuBar(new ToolKitMenuBar());
		////////////////////////
		ToolKitTrayIcon.startTrayIcon();
		Image im = ToolKitTrayIcon.createImage("/images/mut3.png","ToolKit");
		setIconImage(im);
		
		////////////////////////
		setContentPane(mainLogPanel);
		setResizable(false);
		setTitle(JMSConstants.TOOLKIT_TEXT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	public static void main(String[] args){
		try{
	        String path = JMSConstants.TOOLKIT_IMAGE_PATH;
	        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	        BufferedImage image = ImageIO.read(MultiUtilityToolkitMain.class.getResource(path));
	        StartUpWindow contentPane = new StartUpWindow(image);
	        contentPane.setOpaque(true);
	        contentPane.setLayout(new GridBagLayout());
	        JWindow preWindow = new JWindow();
	        preWindow.setAlwaysOnTop(true);
	        preWindow.setContentPane(contentPane);
	        preWindow.setSize(399,199);
	        preWindow.setLocation((int)dim.getWidth()/2-200,(int)dim.getHeight()/2-100);
	        preWindow.setVisible(true);
	        
	        LogWindow window = new LogWindow();
	        StaticBean.setLogWindow(window); 
	        FileLogSupportMethods.generateLogXML();//generates log4j.xml file if not found
	        Thread.sleep(2000);
	        multiUtilityToolkitMain = new MultiUtilityToolkitMain();
	        Thread.sleep(1000);
	        preWindow.setVisible(false);
		}catch(Exception e){
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				JOptionPane.showMessageDialog(multiUtilityToolkitMain, e+"\n"+"Please contact Technical Support", "Exception caught while starting MultiUtility Toolkit", JOptionPane.ERROR_MESSAGE);
			} catch (Exception e1) {
				e1.printStackTrace();
			}			
		}
	}
	public static int taskbarHeight(){
		org.swiftgantt.Config config = new org.swiftgantt.Config();
		return config.getTaskBarHeight();
	}

	public static void maximize(){
		multiUtilityToolkitMain.setState(Frame.NORMAL);
	}
	public static void minimize(){
		multiUtilityToolkitMain.setState(Frame.ICONIFIED);
	}
	public static void enableResize(){
		multiUtilityToolkitMain.setResizable(true);
	}
	public static void disableResize(){
		multiUtilityToolkitMain.setResizable(false);
	}
}

