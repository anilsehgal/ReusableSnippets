package com.victor.superbugkiller.action;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DetailPanel extends JPanel{

	private static final long serialVersionUID = 6392310193052515879L;
	public DetailPanel(){
		setBackground(Color.WHITE);
		setForeground(Color.WHITE);
		ImageIcon bugI = new ImageIcon (Toolkit.getDefaultToolkit().getImage(DetailPanel.class.getResource("/conf/spider.gif")));
		ImageIcon meteorI = new ImageIcon (Toolkit.getDefaultToolkit().getImage(DetailPanel.class.getResource("/conf/th_fire.gif")));
		ImageIcon orbI = new ImageIcon (Toolkit.getDefaultToolkit().getImage(DetailPanel.class.getResource("/conf/pow_2.gif")));
		ImageIcon shieldI = new ImageIcon (Toolkit.getDefaultToolkit().getImage(DetailPanel.class.getResource("/conf/shield.png")));
		JLabel label1 = new JLabel("Kill Aliens to gain score and proceed to higher levels, one missed parasite will end the game",bugI,JLabel.LEFT);
		JLabel label2 = new JLabel("Meteors can be Destroyed with missiles OR dodged",meteorI,JLabel.LEFT);
		JLabel label3 = new JLabel("Power Orbs when collected make game easier, first orb makes ship faster, second orb restores ULTIMATE, rest restore ULTIMATE or shoot one if ultimate is already present",orbI, JLabel.LEFT);
		JLabel label4 = new JLabel("SHIELD has the capability of absorbing one hostile, collect power orbs to power it up",shieldI,JLabel.LEFT);
		label1.setBackground(Color.WHITE);
		label2.setBackground(Color.WHITE);
		label3.setBackground(Color.WHITE);
		label4.setBackground(Color.WHITE);
		setLayout(new GridLayout(13,1));
		add(new JLabel("<html><b>Ya thinkin... 'm good at this', try scoring 150</b></html>",JLabel.LEFT));
		add(new JLabel("<html><b>Use Keys 'A' and 'D' to tilt the missile LEFT or RIGHT respectively</b></html>",JLabel.LEFT));
		add(new JLabel("<html><b>Use LEFT ARROW and RIGHT ARROW Keys to tilt the PLANE LEFT or RIGHT respectively, try pulling plane in one direction!!</b></html>",JLabel.LEFT));
		add(new JLabel("Press 'P' to pause and 'R' to resume the game",JLabel.LEFT));
		add(new JLabel("Every 15 score raise your level thereby raising the game difficulty",JLabel.LEFT));
		add(label1);
		add(label2);
		add(label3);
		add(label4);
		add(new JLabel("ULTIMATE can be fired by pressing SPACE button, destroys every hostile on the screen momentarily",JLabel.LEFT));
		add(new JLabel("BOSS requires 10 hits to be destroyed",JLabel.LEFT));
		add(new JLabel("<html><i>Got any Game Suggestions, Don't hesitate to pass on to me</i></html>",JLabel.LEFT));
		add(new JLabel("ENJOY!!"));
	}
}
