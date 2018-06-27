package com.victor.superbugkiller.action;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class SuperBugKillerMenuBar extends JMenuBar implements DataLabels{

	private static final long serialVersionUID = -5244172180750797189L;
	public SuperBugKillerMenuBar(){
		JMenu fileMenu = new JMenu("File");
		JMenu helpMenu = new JMenu("Help");
		JMenuItem exitItem = new JMenuItem("Exit");
		JMenuItem aboutItem = new JMenuItem("About");
		fileMenu.add(exitItem);
		helpMenu.add(aboutItem);
		add(fileMenu);
		add(helpMenu);
		fileMenu.setBackground(Color.WHITE);
		helpMenu.setBackground(Color.WHITE);
		setBackground(Color.WHITE);
		setForeground(Color.WHITE);
		exitItem.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		aboutItem.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				new AboutUI();
			}
		});
	}
}
