package com.victor.multiUtilityToolkit.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.victor.multiUtilityToolkit.jms.ui.ServerFileDialog;

public class ToolKitMenuBar extends JMenuBar{

	private static final long serialVersionUID = -4680986779240115945L;
	JMenu fileMenu;
	JMenu editMenu;
	JMenu helpMenu;
	JMenuItem exitItem;
	JMenuItem editServerFileItem,
	viewServerFileItem;
	ServerFileDialog serverFileDialog;
	JMenuItem aboutItem;
	public ToolKitMenuBar(){
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		exitItem = new JMenuItem("Exit");
		exitItem.setMnemonic(KeyEvent.VK_X);
		fileMenu.add(exitItem);
		add(fileMenu);
		editMenu = new JMenu("Edit");
		editMenu.setMnemonic(KeyEvent.VK_E);
		editServerFileItem = new JMenuItem("Edit Server(s)");
		editServerFileItem.setMnemonic(KeyEvent.VK_S);
		editMenu.add(editServerFileItem);
		viewServerFileItem = new JMenuItem("View Server(s)");
		viewServerFileItem.setMnemonic(KeyEvent.VK_W);
		editMenu.add(viewServerFileItem);
		add(editMenu);
		helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		aboutItem = new JMenuItem("About");
		aboutItem.setMnemonic(KeyEvent.VK_B);
		helpMenu.add(aboutItem);
		add(helpMenu);
		exitItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);			
			}
		});
		aboutItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(MultiUtilityToolkitMain.multiUtilityToolkitMain, "MultiUtility Toolkit\nVersion: 1.00\nDeveloped By: Anil Sehgal","About", JOptionPane.INFORMATION_MESSAGE);			
			}
		});
		editServerFileItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {			
				try {
					serverFileDialog = new ServerFileDialog(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		viewServerFileItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {			
				try {
					serverFileDialog = new ServerFileDialog(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
