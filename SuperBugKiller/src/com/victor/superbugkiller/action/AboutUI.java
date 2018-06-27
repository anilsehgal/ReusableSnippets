package com.victor.superbugkiller.action;

import java.awt.Color;

import javax.swing.JDialog;
import javax.swing.JLabel;

public class AboutUI extends JDialog{

	private static final long serialVersionUID = 3367733741211242267L;
	public AboutUI(){
		String text = "<html>" +
				"<center><b>Parasite Attack</b></center>" +
				"<br>" +
				"<table>" +
		/**		"<tr>" +
				"<td>Designed & Developed By:</td><td> Anil Sehgal (anil.sehgal10@gmail.com)</td>" +
				"</tr>" + **/
				"<tr>" +
				"<td>Programming Interfaces Used:</td><td> Core Java and Swings</td>" +
				"</tr>" +
				"<tr>" +
				"<td>Minimum Requirements:</td><td> Java with Windows XP</td>" +
				"</tr>" +
				"<tr>" +
				"<td>Java Version Used:</td><td> JDK 1.6.0_22</td>" +
				"</tr>" +
				"</table>" +
				"</html>";
		JLabel about = new JLabel(text);
		add(about);
		setTitle("About the author");
		setResizable(false);
		about.setBackground(Color.WHITE);
		setBackground(Color.WHITE);
		setForeground(Color.WHITE);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setModal(true);
		setSize(420,200);
		setVisible(true);
	}
	public static void main(String[] args) {
		new AboutUI();
	}
}
