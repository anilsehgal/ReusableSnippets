package com.victor.importer.ui;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import javax.help.CSH;
import javax.help.HelpBroker;
import javax.help.HelpSet;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;


import com.victor.importer.container.FrameContainer;
import com.victor.importer.main.JImporterMain;

public class ImporterMenuBar extends JMenuBar{

	private static final long serialVersionUID = 1080762803902797981L;
	JMenu fileMenu, viewMenu, openMenu;
	JMenuItem exitItem, minimizeItem, openSchedulerItem, aboutItem,openFileItem,openProfileItem;
	  HelpSet hs;
	  HelpBroker hb;

	  // menu components
	  JMenu helpMenu;
	  JMenuItem helpItemTOC;
		static{
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			UIManager.put("Menu.selectionBackground", Color.white);
			UIManager.put("ToolTip.background", Color.white);
			UIManager.put("ImageIcon.background", Color.white);
			UIManager.put("Menu.foreground", Color.white);
			
		}
	public ImporterMenuBar(){
		//initialization
/*		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");


		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "Error setting Look & Feel "+e1, "Error setting Look & Feel", JOptionPane.ERROR_MESSAGE);
		}*/
		
		ImageIcon fileIcon = new ImageIcon(LoadProfileUI.class.getResource("/config/fileMb.jpg"));
		ImageIcon viewIcon = new ImageIcon(LoadProfileUI.class.getResource("/config/viewic.jpg"));
		ImageIcon schedIcon = new ImageIcon(LoadProfileUI.class.getResource("/config/sched.jpg"));
		ImageIcon helpIcon = new ImageIcon(LoadProfileUI.class.getResource("/config/help.jpg"));
		ImageIcon aboutIcon = new ImageIcon(LoadProfileUI.class.getResource("/config/about.jpg"));
		ImageIcon exitIcon = new ImageIcon(LoadProfileUI.class.getResource("/config/exit.jpg"));
		ImageIcon profileIcon = new ImageIcon(LoadProfileUI.class.getResource("/config/pro.jpg"));
		
		
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		
		
		setBackground(Color.WHITE);
		setForeground(Color.WHITE);
		
		fileMenu = new JMenu("File");
		viewMenu = new JMenu("View");
		openMenu = new JMenu("Schedule");
		
		exitItem = new JMenuItem("Exit",exitIcon);
		openFileItem = new JMenuItem("Open File",fileIcon);
		openProfileItem = new JMenuItem("Open Profile",profileIcon);
		minimizeItem = new JMenuItem("Hide to Tray",viewIcon);
		openSchedulerItem = new JMenuItem("Open Scheduler",schedIcon);


		String helpsetPath="C:\\JFileImporter\\JFIHelp\\HelpSet.hs";
		helpMenu = new JMenu("Help");
	    
		try {
			//	InputStream stream = this.getClass().getClassLoader().getResourceAsStream("/JFileImporter/JFIHelp/HelpSet.hs");
			File file = new File(helpsetPath);
				
			    URL hsURL = file.toURL();
		        hs = new HelpSet(null, hsURL);
		        helpItemTOC = new JMenuItem("Contents",helpIcon);
			    helpMenu.add(helpItemTOC);
			    hb = hs.createHelpBroker();
			    ActionListener helper = new CSH.DisplayHelpFromSource(hb);
			    helpItemTOC.addActionListener(helper);
		     }
		     catch (Exception ee) {
		    	 
		    	// JOptionPane.showMessageDialog(null, "Help File not found at "+helpsetPath+": Exception: "+ee, "Help will not work", JOptionPane.ERROR_MESSAGE);
		    	 //System.exit(0);
		     }
			aboutItem = new JMenuItem("About",aboutIcon);
			helpMenu.add(aboutItem);
		    
		    
		    
		//action listeners
		exitItem.addActionListener(new ActionListener() {
			
			@Override
            public void actionPerformed(ActionEvent e) {
            	int option = JOptionPane.showConfirmDialog(null, "If You Quit the Application, the scheduled job will terminate!", "Exit Confirmation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            	if(option == JOptionPane.OK_OPTION){
	                System.exit(0);
            	}
            }
		});
		minimizeItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JImporterMain.minimize();		
			}
		});
		openSchedulerItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					(new LoadProfileUI()).createAndShowGUI();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error Starting Scheduler "+e, "Scheduler Could not be Started", JOptionPane.ERROR_MESSAGE);
				}		
			}
		});
		aboutItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new AboutUI();
			}
		});
		openFileItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				FrameContainer.browseFileButton.doClick();
			}
		});
		
		openProfileItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				FrameContainer.loadProfileButton.doClick();
			}
		});
		
		
		//adding items
		fileMenu.add(openFileItem);
		fileMenu.add(openProfileItem);
		fileMenu.add(exitItem);
		openMenu.add(openSchedulerItem);
		viewMenu.add(minimizeItem);
		
		//adding menus
		add(fileMenu);
		add(openMenu);
		add(viewMenu);
		add(helpMenu);
		//com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel		
	}
}
