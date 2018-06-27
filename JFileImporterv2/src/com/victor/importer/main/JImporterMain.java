package com.victor.importer.main;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;


import com.victor.importer.ui.ImporterMenuBar;
import com.victor.importer.ui.ImporterTray;
import com.victor.importer.ui.ImporterUICompact;


public class JImporterMain extends JFrame{
	private static final long serialVersionUID = -5147216618372411451L;
	static JPanel mainPanel;
	static ImporterUICompact importerUICompact;
	static GridBagConstraints pos;
	public static JImporterMain jImporterMain;
	static Dimension dim;

	public JImporterMain() throws Exception{

		importerUICompact = new ImporterUICompact();
		
		pos = new GridBagConstraints();
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		mainPanel = new JPanel(new GridBagLayout());
		setJMenuBar(new ImporterMenuBar());
		//setPreferredSize(new Dimension(1000,800));
		pos.gridx=0;
		pos.gridy=0;
		mainPanel.add(importerUICompact,pos);
		ImporterTray.startTrayIcon();
		Image im = ImporterTray.createImage("/config/mut3.png","Importer");
		setIconImage(im);
		//Setting Colors
		setTitle("JFileImporter v1.0");
		mainPanel.setForeground(Color.WHITE);
		mainPanel.setBackground(Color.WHITE);
		setForeground(Color.WHITE);
		setBackground(Color.WHITE);
		setContentPane(mainPanel);
		setResizable(false);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		pack();
		setVisible(true);
	}
	/////////////////
	//		dburlBox.setText("jdbc:microsoft:sqlserver://ANIL-LAPTOP:1433;DatabaseName=LTPSQLSDB");
	//		userNameBox.setText("sa");
	//		passwordBox.setText("bakuryu");
	///////////////////
	public static void main(String[] args){
		try{
			RotatePanel2 rotatePanel = new RotatePanel2();
			rotatePanel.closeF();
			jImporterMain = new JImporterMain();

		}catch(Exception e){
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
				JOptionPane.showMessageDialog(jImporterMain, e+"\n"+"Please contact Technical Support", "Exception caught while starting MultiUtility Toolkit", JOptionPane.ERROR_MESSAGE);
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(jImporterMain, e1+"\n"+"Please contact Technical Support", "Exception caught while starting MultiUtility Toolkit", JOptionPane.ERROR_MESSAGE);
			}			
		}
	}
	public static void maximize(){
		jImporterMain.setState(Frame.NORMAL);
	}
	public static void minimize(){
		jImporterMain.setVisible(false);
	}
	public static void showImporter(){
		jImporterMain.setVisible(true);
	}
}
