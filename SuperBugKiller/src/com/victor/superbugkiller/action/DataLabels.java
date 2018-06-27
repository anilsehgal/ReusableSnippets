package com.victor.superbugkiller.action;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



public interface DataLabels {
//	public static JLabel ultimate = new JLabel("=================================================================================================================================================");
	public static JTextField input = new JTextField();
	public static GridBagConstraints position = new GridBagConstraints();
	public static BPanel mainPanel = new BPanel();
	public static JPanel scorePanel = new JPanel(new FlowLayout());
	public static JButton startButton = new JButton("Start Killing");
	public static JButton reStartButton = new JButton("Kill Again");
	public static JLabel scoreLabel = new JLabel();
	public static JLabel gameTitle = new JLabel("Parasite Attack");
	public static DetailPanel detailPanel = new DetailPanel();
	public static JTextField aKills = new JTextField(4);
	public static JTextField bKills = new JTextField(4);
	public static JTextField gameLevel = new JTextField(2);
	public static JTextField scoreCountLabel = new JTextField(4);
	
	
}
