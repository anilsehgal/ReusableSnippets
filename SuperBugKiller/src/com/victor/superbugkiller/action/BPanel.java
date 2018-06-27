package com.victor.superbugkiller.action;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class BPanel extends JPanel{

	private static final long serialVersionUID = 6413947119434493459L;
	public static Image backgroundImg = Toolkit.getDefaultToolkit().getImage (SuperBugKiller.class.getResource("/conf/space_2.jpg"));
	public BPanel(){
		super();
	}
	public void paintComponent(Graphics g) 
     {
         g.drawImage(backgroundImg, 0, 0, null);
     } 
}
