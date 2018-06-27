package com.victor.importer.ui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JDialog;

import javax.swing.JTextPane;


public class AboutUI extends JDialog{

	private static final long serialVersionUID = -3852794527653909691L;
	static String NEW_LINE = "\n";

	public AboutUI(){

		Image im = Toolkit.getDefaultToolkit().getImage(AboutUI.class.getResource("/config/mut3.png"));
		setIconImage(im);
        JTextPane textPane = new JTextPane(); // creates an empty text pane
        textPane.setContentType("text/html"); // lets Java know it will be HTML                  
        textPane.setText(
        		"<font face=\"Bookman Old Style\">" +
        		"<table>" +
        		"<tr><td colspan=\"2\" align=\"center\">" +
        		"<u><span style=\"font-size: 20pt\">JFileImporter</span></u>" +
        		"</td></tr>" +
        		"<tr><td>" +
        		"<b>Developed By:</b></td><td>"+ "Anil Sehgal<br>" +
        		"Anil.Sehgal10@Gmail.com" +
        		"</td></tr>" +
        		"<tr><td>"+ 
        		"<b>Technologies: </b>" +
        		"</td><td>" +
        		"Java SE jdk1.6.0_22<br>" +
        		"Swings<br>" +
        		"POI<br>" +
        		"JDBC<br>" +
        		"Nimbus Theme For Swings</td></tr>" +
        		"<tr><td>" +
        		"<b>Best Runs On:</b></td><td>" +
        		"<strong>Resolution: </strong>1024X768 and Above<br>" +
        		"<strong>Java Runtime Environment: </strong>Java(TM) SE Runtime Environment (build 1.6.0_22-b04)</td></tr>" +
        		"<tr><td><b>Version</b>" +
        		"</td><td>1.0" +
        		"</td></tr>" +
        		"</table>" +
        		"<br>" +
        		"<center>Copyright (c) 2011, 2012, Anil Sehgal. All rights reserved.</center>" +
        		"</font>"
        ); // sets its text


        textPane.setEditable(false);
        getContentPane().add(textPane); // adds the text pane to the window


		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		setModal(true);
		setResizable(false);
		setPreferredSize(new Dimension(400, 350));
		Point point = new Point((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2-200,(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2 - 200);
		setLocation(point);
		pack();
		setVisible(true);
	} 
	public static void main(String[] args) {
		new AboutUI();
	}
}
