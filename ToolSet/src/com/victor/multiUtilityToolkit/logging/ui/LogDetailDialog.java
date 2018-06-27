package com.victor.multiUtilityToolkit.logging.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class LogDetailDialog extends JDialog{

	private static final long serialVersionUID = 4575857032993254195L;
	JTextArea label;
	JScrollPane scroller;
	public LogDetailDialog(String e, String title){
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		label = new JTextArea(e,100,50);
		label.setEditable(false);
		setResizable(false);
		setTitle(title);
		scroller = new JScrollPane(label);
		scroller.setPreferredSize(new Dimension(800,400));
		setPreferredSize(new Dimension(800,400));
		setLocation((int)dim.getWidth()/2-400,(int)dim.getHeight()/2-200);
		pack();
		setModal(true);
		add(scroller, BorderLayout.CENTER);
		setVisible(true);
	}
}
