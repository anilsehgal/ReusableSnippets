package com.victor.superbugkiller.action;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import com.victor.superbugkiller.bean.SuperBugKillerBean;

public class SuperBugKiller extends JFrame implements DataLabels,Runnable{
	private static final long serialVersionUID = 1447106579198410770L;
	public static boolean callUltimate = false,showShield = false,pauseGame = false,showMainEnemy = false;
	public static int score = 0,powersTaken = 0, levelScore = 0,mainEnemyHits = 0;
	public static int ultimatesRemaining = 1;
	public static Image rocketImg = Toolkit.getDefaultToolkit().getImage(SuperBugKiller.class.getResource("/conf/rocket_1.gif"));
	public static Image missleImg = Toolkit.getDefaultToolkit().getImage (SuperBugKiller.class.getResource("/conf/Missile_1.gif"));
	public static Image bugImg = Toolkit.getDefaultToolkit().getImage (SuperBugKiller.class.getResource("/conf/spider.gif"));
	public static Image meteorImg = Toolkit.getDefaultToolkit().getImage (SuperBugKiller.class.getResource("/conf/th_fire.gif"));
	public static Image greatScore = Toolkit.getDefaultToolkit().getImage (SuperBugKiller.class.getResource("/conf/good.jpg"));
	public static Image poorScore = Toolkit.getDefaultToolkit().getImage (SuperBugKiller.class.getResource("/conf/poor.jpg"));
	public static Image orb = Toolkit.getDefaultToolkit().getImage (SuperBugKiller.class.getResource("/conf/pow_2.gif"));
	public static Image startImage = Toolkit.getDefaultToolkit().getImage (SuperBugKiller.class.getResource("/conf/startImage.jpg"));
	public static Image shieldImage = Toolkit.getDefaultToolkit().getImage (SuperBugKiller.class.getResource("/conf/shield.png"));
	public static Image burstImage = Toolkit.getDefaultToolkit().getImage (SuperBugKiller.class.getResource("/conf/blast1.gif"));
	public static Image waveImage = Toolkit.getDefaultToolkit().getImage (SuperBugKiller.class.getResource("/conf/ulti_1.gif"));
	public static Image mEnemyImage = Toolkit.getDefaultToolkit().getImage (SuperBugKiller.class.getResource("/conf/spider_2_tr.gif"));

	
	public static JLabel ultimate = new JLabel(new ImageIcon(waveImage));
	public static JLabel plane = new JLabel(new ImageIcon(rocketImg));
	
	public static JLabel bug = new JLabel(new ImageIcon(meteorImg));
	public static JLabel startImageLabel = new JLabel(new ImageIcon(startImage));
	public static JLabel bug2 = new JLabel(new ImageIcon(bugImg));
	public static JLabel power = new JLabel(new ImageIcon(orb));
	public static JLabel missle = new JLabel(new ImageIcon(missleImg));
	public static JLabel greatScoreLabel = new JLabel(new ImageIcon(greatScore));
	public static JLabel poorScoreLabel = new JLabel(new ImageIcon(poorScore));
	public static JLabel shieldLabel = new JLabel(new ImageIcon(shieldImage));
	public static JLabel burstLabel = new JLabel(new ImageIcon(burstImage));

	public static JLabel mainEnemy = new JLabel(new ImageIcon(mEnemyImage));
	public SuperBugKiller(){

	    try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "Ohh!! Something seems to be wrong with System Graphics","Graphics not Supported!!",JOptionPane.ERROR_MESSAGE);
		}
		mainEnemy.setForeground(Color.WHITE);
		
		final JScrollPane pane = new JScrollPane(detailPanel);
	
		pane.setPreferredSize(new Dimension(550,200));
		pane.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
		pane.setAlignmentY(JScrollPane.TOP_ALIGNMENT);

		setTitle("Parasite Attack");
		setJMenuBar(new SuperBugKillerMenuBar());
		mainPanel.setLayout(new GridBagLayout());
		position.gridx = 0;
		position.gridy = 0;
		mainPanel.add(reStartButton, position);
		position.gridx = 0;
		position.gridy = 1;
		mainPanel.add(startButton , position);
		position.gridx = 0;
		position.gridy = 2;
		mainPanel.add(input, position);
		position.gridx = 0;
		position.gridy = 3;
		mainPanel.add(gameTitle, position);
		position.gridx = 0;
		position.gridy = 4;
		mainPanel.add(burstLabel, position);
		position.gridx = 0;
		position.gridy = 5;
		mainPanel.add(power, position);
		position.gridx = 0;
		position.gridy = 6;
		mainPanel.add(ultimate, position);
		position.gridx = 0;
		position.gridy = 7;
		mainPanel.add(shieldLabel, position);
		position.gridx = 0;
		position.gridy = 8;
		mainPanel.add(greatScoreLabel, position);
		position.gridx = 0;
		position.gridy = 9;
		mainPanel.add(poorScoreLabel, position);
		position.gridx = 0;
		position.gridy = 10;
		mainPanel.add(scoreLabel, position);
		position.gridx = 0;
		position.gridy = 11;
		mainPanel.add(bug, position);
		position.gridx = 0;
		position.gridy = 12;
		mainPanel.add(startImageLabel, position);
		position.gridx = 0;
		position.gridy = 13;
		mainPanel.add(pane, position);
		position.gridx = 0;
		position.gridy = 14;
		mainPanel.add(plane, position);
		position.gridx = 0;
		position.gridy = 15;
		mainPanel.add(bug2, position);
		position.gridx = 0;
		position.gridy = 16;
		mainPanel.add(missle, position);
		//testing main enemy
		position.gridx = 0;
		position.gridy = 17;
		mainPanel.add(mainEnemy, position);
		
		
		reStartButton.setVisible(true);
		mainPanel.setBackground(Color.BLACK);
		mainPanel.setForeground(Color.WHITE);
		scorePanel.setBackground(Color.WHITE);
		scorePanel.setForeground(Color.WHITE);
		pane.setBackground(Color.WHITE);
		pane.setForeground(Color.WHITE);
		add(mainPanel, BorderLayout.CENTER);
		add(scorePanel, BorderLayout.SOUTH);
		Font titleFont = new Font("Berlin Sans FB", Font.BOLD, 48);
		gameTitle.setFont(titleFont);
		scorePanel.add(new JLabel("Meteors Destroyed:"));
		scorePanel.add(aKills);
		scorePanel.add(new JLabel("Aliens Destroyed:"));
		scorePanel.add(bKills);
		scorePanel.add(new JLabel("Game Level:"));
		scorePanel.add(gameLevel);
		scorePanel.add(new JLabel("Score:"));
		scorePanel.add(scoreCountLabel);
		scoreCountLabel.setText("0");
		gameLevel.setText("1");
		aKills.setText("0");
		bKills.setText("0");
		aKills.setFocusable(false);
		bKills.setFocusable(false);
		aKills.setEditable(false);
		bKills.setEditable(false);
		input.setVisible(false);
		plane.setVisible(false);
		bug.setVisible(false);
		shieldLabel.setVisible(false);
		bug2.setVisible(false);
		power.setVisible(false);
		gameLevel.setFocusable(false);
		gameLevel.setEditable(false);
		ultimate.setVisible(false);
		missle.setVisible(false);
		greatScoreLabel.setVisible(false);
		poorScoreLabel.setVisible(false);
		scoreCountLabel.setFocusable(false);
		scoreCountLabel.setEditable(false);
		reStartButton.setVisible(false);
		mainEnemy.setVisible(false);
		
		burstLabel.setVisible(false);
		startButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				SuperBugKillerBean.powerFallSpeed = 2;
				SuperBugKillerBean.meteorFallSpeed = 4;
				SuperBugKillerBean.bugFallSpeed = 3;
				SuperBugKillerBean.missleSpeed = 18;
				SuperBugKillerBean.planeSpeed = 15;
				SuperBugKillerBean.gameSlowness = 50;
				detailPanel.setVisible(false);
				pane.setVisible(false);
				startImageLabel.setVisible(false);
				gameTitle.setVisible(false);
				input.setVisible(true); 
				power.setVisible(true);
				shieldLabel.setVisible(true); 
				startButton.setVisible(false);
				bug2.setVisible(true);
				plane.setVisible(true);
				bug.setVisible(true);
				ultimate.setVisible(true);
				missle.setVisible(true);
				gameLevel.setVisible(true);
				scoreCountLabel.setVisible(true);
				greatScoreLabel.setVisible(false);
				poorScoreLabel.setVisible(false);
				
				burstLabel.setVisible(true);
				
				
				mainEnemy.setVisible(true);
				startThis();
			}
		});
		reStartButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//reset all
				SuperBugKillerBean.powerFallSpeed = 2;
				SuperBugKillerBean.meteorFallSpeed = 4;
				SuperBugKillerBean.bugFallSpeed = 3;
				SuperBugKillerBean.missleSpeed = 18;
				SuperBugKillerBean.planeSpeed = 15;
				SuperBugKillerBean.gameSlowness = 50;
				score = 0;
				levelScore = 0;
				ultimatesRemaining = 1;
				powersTaken = 0;
				scoreCountLabel.setText("0");
				gameLevel.setText("1");
				aKills.setText("0");
				bKills.setText("0");
				input.setVisible(true); 
				shieldLabel.setVisible(true); 
				power.setVisible(true);
				startButton.setVisible(false);
				bug2.setVisible(true);
				plane.setVisible(true);
				bug.setVisible(true);
				ultimate.setVisible(true);
				missle.setVisible(true);
				gameLevel.setVisible(true);
				scoreLabel.setVisible(false);
				greatScoreLabel.setVisible(false);
				poorScoreLabel.setVisible(false);
				reStartButton.setVisible(false);
				
				burstLabel.setVisible(true);
				
				mainEnemy.setVisible(true);
				startThis();
			}
		});
		input.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				// do nothing
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// do nothing
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==37){
					plane.setLocation(plane.getX()-SuperBugKillerBean.planeSpeed,plane.getY());
					if(plane.getX()<=0){
						plane.setLocation(560,plane.getY());
					}
				}
				if(e.getKeyCode()==39){
					plane.setLocation(plane.getX()+SuperBugKillerBean.planeSpeed,plane.getY());
					if(plane.getX()>=560){
						plane.setLocation(0,plane.getY());
					}					
				}else{
					if(e.getKeyCode() == 32){
						 if(ultimatesRemaining > 0){
							 callUltimate = true;
							 ultimatesRemaining--;
						 }
					}
				}
				if(e.getKeyCode()==65){
					missle.setLocation(missle.getX()-15,missle.getY());				
				}
				if(e.getKeyCode()==68){
					missle.setLocation(missle.getX()+15,missle.getY());					
				}
				if(e.getKeyCode()==80){
					pauseGame = true;
				}
				if(e.getKeyCode()==82){
					pauseGame = false;		
				}
				//testing
/*				if(e.getKeyCode()==38){
					showShield = true;		
				}
				if(e.getKeyCode()==40){
					showShield = false;				
				}*/
	
			}
		});
		setPreferredSize(new Dimension(600,780));
		setResizable(false);
		pack();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);	
	}
	public static void main(String[] args) {
		new SuperBugKiller();
	}
	public void startThis(){
		Thread t1 = new Thread(this);
		t1.start();
	}
	@Override
	public void run() {
		boolean neverEnd = true;
		int showBug2 = 0,showPower = 0,levelCounter = 0;
		mainEnemy.setLocation(-100, -500);
		bug2.setLocation(-100, 0);
		power.setLocation(-100, 0); 
		missle.setLocation(plane.getX()+SuperBugKillerBean.missileX, 550);
		ultimate.setLocation( ultimate.getX(), 750);
		burstLabel.setLocation( bug2.getX(), 850);
		while(neverEnd){
			while(pauseGame){
				try {
					Thread.currentThread().sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}		
			}
			try {
				Thread.currentThread().sleep(SuperBugKillerBean.gameSlowness);
			}catch (Exception e1) {
				e1.printStackTrace();
			}
			input.setLocation(- 100, -100); 
			
				
			
			plane.setLocation(plane.getX(), 540);
			
			if(showMainEnemy){
				mainEnemy.setLocation(230, 50);
			}else{
				mainEnemy.setLocation(-100, -500);
			}
			if(showShield){
				shieldLabel.setLocation(plane.getX()-70, 500);
			}else{
				shieldLabel.setLocation(plane.getX()-70, 750);
			}
			power.setLocation(power.getX(), power.getY()+SuperBugKillerBean.powerFallSpeed);
			bug.setLocation(bug.getX(), bug.getY()+SuperBugKillerBean.meteorFallSpeed);
			bug2.setLocation(bug2.getX(), bug2.getY()+SuperBugKillerBean.bugFallSpeed);	
			missle.setLocation(missle.getX(), missle.getY()-SuperBugKillerBean.missleSpeed);
			burstLabel.setLocation( bug2.getX(), 850);
			if(missle.getY()<=0){
				missle.setLocation(plane.getX()+SuperBugKillerBean.missileX,550);
			}
			//fallen bugs
			if(bug.getY()>=520 && (bug.getX()>=plane.getX()-30 && bug.getX()<=plane.getX()+40)){
				break;
			}
			if(bug.getY()>=580){
				bug.setLocation(getNewX(),0);
			}
			if(bug2.getY()>=554){
				if(bug2.getX() > 0){
					break;
				}
			}
			if(showBug2%500 == 0){
				bug2.setLocation(100, 0);
			}
			if(showBug2%1500 == 0 ){
				bug2.setLocation(-100, 0);
			}
			if(showPower>=1000 && showPower%1000 == 0){
				power.setLocation(getNewX(), 0); 
			}
			if(power.getY() >= 554){
				power.setLocation(-100, 0); 
			}
			
			if(callUltimate){			
				ultimate.setLocation(ultimate.getX(), ultimate.getY()-10);
				if(ultimate.getY() <= 0){
					callUltimate = false;
					ultimate.setLocation( ultimate.getX(), 750);
				}
			}else{
				ultimate.setLocation( ultimate.getX(), 750);
			}
			//collision with ultimate
			if(ultimate.getY() <= (bug.getY()+30)){
				score++;
				levelScore++;
				
				bug.setLocation( getNewX(), 0);
				aKills.setText(Integer.toString(Integer.parseInt(aKills.getText())+1));
				
			}
			if(ultimate.getY() <= (bug2.getY()+30)){
				score++;
				levelScore++;
				bug2.setLocation( getNewX(), 0);
				bKills.setText(Integer.toString(Integer.parseInt(bKills.getText())+1));
			}
			//missle collision

			if((missle.getX() <= (bug.getX() + 20) && missle.getX()>=(bug.getX()-20)) && (missle.getY() <= (bug.getY() + 25) && missle.getY()>=(bug.getY()-25))){
				score++;
				levelScore++;
				bug.setLocation(getNewX(), 0);
				missle.setLocation(plane.getX()+SuperBugKillerBean.missileX, 548);
				aKills.setText(Integer.toString(Integer.parseInt(aKills.getText())+1));
			}
			if((missle.getX() <= (bug2.getX() + 65) && missle.getX() >= (bug2.getX()-20)) && (missle.getY() <= (bug2.getY() + 45) && missle.getY() >= (bug2.getY()-15))){
				score++;
				levelScore++;
				burstLabel.setLocation( bug2.getX(), bug2.getY());
				try {
					Thread.currentThread().sleep(150);
				} catch (InterruptedException e) {					
					e.printStackTrace();
				}
				burstLabel.setLocation( bug2.getX(), 850);
				bug2.setLocation(getNewX(), 0);
				missle.setLocation(plane.getX()+SuperBugKillerBean.missileX, 548);
				bKills.setText(Integer.toString(Integer.parseInt(bKills.getText())+1));
			}
			//missle collision with main enemy
			if((missle.getX() <= (mainEnemy.getX() + 100) && missle.getX()>=(mainEnemy.getX()-20)) && (missle.getY() <= (mainEnemy.getY() + 120) && missle.getY()>=(mainEnemy.getY()-120))){
				missle.setLocation(plane.getX()+SuperBugKillerBean.missileX, 548);
				burstLabel.setLocation( mainEnemy.getX(), mainEnemy.getY());
				try {
					Thread.currentThread().sleep(150);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				burstLabel.setLocation( mainEnemy.getX(), 850);
				mainEnemyHits++;
			}
			if(mainEnemyHits == 10){
				mainEnemyHits = 0;
				burstLabel.setLocation( mainEnemy.getX(), mainEnemy.getY());
				try {
					Thread.currentThread().sleep(350);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				burstLabel.setLocation( mainEnemy.getX()+50, mainEnemy.getY());
				try {
					Thread.currentThread().sleep(350);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				burstLabel.setLocation( mainEnemy.getX()+50,  mainEnemy.getY()+50);
				try {
					Thread.currentThread().sleep(350);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				burstLabel.setLocation( mainEnemy.getX(), mainEnemy.getY()+100);
				try {
					Thread.currentThread().sleep(350);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				burstLabel.setLocation( mainEnemy.getX(), 850);
				score++;
				levelScore++;
				showMainEnemy = false;
			}
			
			
			//shield collision
			if((shieldLabel.getX() <= (bug2.getX()+30) && shieldLabel.getX() >= (bug2.getX()-120)) && (shieldLabel.getY() - bug2.getY() < 40)){
				score++;
				levelScore++;
				bug2.setLocation(getNewX(), 0);
				showShield = false;
			}
			if((shieldLabel.getX() <= (bug.getX()) && shieldLabel.getX() >= (bug.getX()-120)) && (shieldLabel.getY() - bug.getY() < 40)){
				score++;
				levelScore++;
				bug.setLocation(getNewX(), 0);
				showShield = false;
			}
			//when power is taken
			if((plane.getY() - power.getY() <= 30) && (plane.getX() >= power.getX() - 30 && plane.getX() <= power.getX() + 30)){
				score++;
				levelScore++;
				if(powersTaken==0){
					SuperBugKillerBean.planeSpeed++;
					SuperBugKillerBean.meteorFallSpeed--;
					SuperBugKillerBean.bugFallSpeed--;
					SuperBugKillerBean.missleSpeed++;
					showShield = true;
					if(ultimatesRemaining==0){
						ultimatesRemaining++;
					}
				}else if(powersTaken==1 && ultimatesRemaining==0){
					SuperBugKillerBean.planeSpeed++;
					SuperBugKillerBean.missleSpeed+=2;
					showShield = true;
					ultimatesRemaining++;
				}else if(powersTaken==1 && ultimatesRemaining==1){
					SuperBugKillerBean.planeSpeed++;
					SuperBugKillerBean.missleSpeed+=2;
					callUltimate = true;
					showShield = true;
				}else if(powersTaken>=2){
					showShield = true;
					SuperBugKillerBean.planeSpeed++;
					SuperBugKillerBean.missleSpeed++;
					if(ultimatesRemaining==0){
						ultimatesRemaining++;
					}else{
						callUltimate = true;
					}
					
				}
				power.setLocation(-100, 0);
				powersTaken++;
			}
			if(levelScore%15==0 && levelScore >= 15){
				levelScore=0;
				levelCounter++;
				SuperBugKillerBean.gameSlowness--;
				SuperBugKillerBean.planeSpeed++;
				SuperBugKillerBean.meteorFallSpeed++;
				SuperBugKillerBean.bugFallSpeed++;
				SuperBugKillerBean.missleSpeed++;
				gameLevel.setText(Integer.toString(Integer.parseInt(gameLevel.getText())+1));
				
			}
			showBug2++;
			showPower++;
			if(score%50==0 && score >=1){
				showMainEnemy = true;
			}
			scoreCountLabel.setText(Integer.toString(score));
		}
		//game over
		input.setVisible(false);
		plane.setVisible(false);
		bug.setVisible(false);
		bug2.setVisible(false);
		power.setVisible(false);
		ultimate.setVisible(false);
		shieldLabel.setVisible(false); 
		burstLabel.setVisible(false); 
		missle.setVisible(false);
		mainEnemy.setVisible(false);
		scoreLabel.setVisible(true);
		reStartButton.setVisible(true);
		scoreLabel.setForeground(Color.WHITE);
		if(score > 100){
			scoreLabel.setText("<html>" +
					"<b>Ouchhhhhhh..... Please!! No More..</b>" +
					"<br>" +
					"Your Score is: "+ score +
					"<br>" +
					"Any Score above 100 is good!!" +
					"</html>");
			greatScoreLabel.setVisible(true);
			
		}else{
			scoreLabel.setText("<html>" +
					"<b>HA HA!! You just try n squash me!</b>" +
					"<br>" +
					"Your Score is: "+ score +
					"</html>");
			poorScoreLabel.setVisible(true);
		}
	}
	public int getNewX(){			
		String xStr = Integer.toString((int)( Math.random()*10000000));
		xStr = xStr.substring(0,3);
		int x = (new Integer(xStr)).intValue();
		if(x > 540){
			x = 1000 - x;
		}
		return x;
	}

	
}
