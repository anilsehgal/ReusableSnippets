package com.victor.importer.main;
import java.awt.*;
import java.awt.geom.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import com.sun.awt.AWTUtilities;


public class RotatePanel2 extends JPanel {

	private static final long serialVersionUID = -888756636859554445L;
	private Image image;
	static JWindow f;
	boolean themeAvailable;
	JPanel componentPanel, mainPanel, statusPanel;
	String helpDir;
	JLabel titleLabel, copyLabel;
	Font titleFont, copyFont,copyFont2;
	private double currentAngle;
	static int i = 80; 
	JProgressBar statusBar;
	public RotatePanel2(Image image) {
		this.image = image;
		MediaTracker myMedia = new MediaTracker(this);
		myMedia.addImage(image, 0);
		try {
			myMedia.waitForID(0);
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Exception: "+e, "Exception caught while Initializing Loader", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void rotate() {
		//rotate 5 degrees at a time
		currentAngle+=5.0;
		if (currentAngle >= 360.0) {
			currentAngle = 0;
		}
		repaint();
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		AffineTransform origXform = g2d.getTransform();
		AffineTransform newXform = (AffineTransform)(origXform.clone());
		//center of rotation is center of the panel
		int xRot = this.getWidth()/2;
		int yRot = this.getHeight()/2;
		newXform.rotate(Math.toRadians(currentAngle), xRot, yRot);
		g2d.setTransform(newXform);
		//draw image centered in panel
		int x = (getWidth() - image.getWidth(this))/2;
		int y = (getHeight() - image.getHeight(this))/2;
		g2d.drawImage(image, x, y, this);
		g2d.setTransform(origXform);
	}

	public Dimension getPreferredSize() {
		return new Dimension (image.getWidth(this), image.getHeight(this));
	}
	public RotatePanel2(){
		verifyTheme();
		//titleFont = new Font("Arial Rounded MT Bold", Font.BOLD, 24);
		//titleFont = new Font("Berlin Sans FB", Font.BOLD, 24);
		//
		titleFont = new Font("Copperplate Gothic Bold", Font.BOLD, 24);
		copyFont = new Font("Berlin Sans FB", Font.PLAIN, 14);
		copyFont2 = new Font("Berlin Sans FB", Font.PLAIN, 12);
		mainPanel = new JPanel(new GridBagLayout());
		statusPanel = new JPanel(new GridBagLayout());
		copyLabel = new JLabel();
		titleLabel = new JLabel("JFileImporter");
		titleLabel.setFont(titleFont);
		String text = "The standalone File To Database Import Utility with an Inbuilt Scheduler";
		copyLabel.setFont(copyFont);
		copyLabel.setText(text);
		
		componentPanel = new JPanel(new GridBagLayout());
		statusBar = new JProgressBar();
		statusBar.setMinimum(0);
		statusBar.setMaximum(100);
		statusBar.setStringPainted(true);
		

		Border border = BorderFactory.createLineBorder(Color.ORANGE);
		statusBar.setBorder(border);

		
		//statusBar.setForeground(Color.WHITE);
		//statusBar.setBackground(Color.BLACK);
		
		f = new JWindow();
		//f.setLayout(new GridBagLayout());
		GridBagConstraints loc = new GridBagConstraints();
		f.setForeground(Color.WHITE);
		f.setBackground(Color.WHITE);
		
		Container cp = f.getContentPane();
		//cp.setLayout(new GridBagLayout());
		Image testImage = null;
		try {
			testImage = ImageIO.read(RotatePanel2.class.getResource("/config/mut3.png"));
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "Exception: "+e1, "Exception caught while loading image", JOptionPane.ERROR_MESSAGE);
		}
		final RotatePanel2 rotatePanel = new RotatePanel2(testImage);
		
		//rotatePanel.setForeground(Color.WHITE);
		//rotatePanel.setBackground(Color.WHITE);
		
		AWTUtilities.setWindowOpacity(f, 0.8f);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		loc.gridx=0;
		loc.gridy=0;
		componentPanel.add(rotatePanel, loc);		
		loc.gridx=1;
		loc.gridy=0;
		componentPanel.add(titleLabel, loc);
		
		loc.gridx=0;
		loc.gridy=0;
		mainPanel.add(componentPanel,loc);
		loc.gridx=0;
		loc.gridy=1;
		mainPanel.add(copyLabel,loc);
		JLabel cpLbl = new JLabel("Copyright (c) 2011, 2012, Anil Sehgal. All rights reserved.", JLabel.LEFT);
		cpLbl.setFont(copyFont2);
		
		loc.gridx=0;
		loc.gridy=0;
		statusPanel.add(cpLbl ,loc);
		loc.gridx=0;
		loc.gridy=1;
		statusPanel.add(statusBar,loc);
		
		cp.add(mainPanel, BorderLayout.CENTER);//Copyright (c) 2011, 2012, Anil Sehgal. All rights reserved.
		cp.add(statusPanel, BorderLayout.SOUTH);
		statusBar.setPreferredSize(new Dimension(450,(int)statusBar.getPreferredSize().getHeight()));
		Point newDim = new Point((int)screenSize.getWidth()/2-225, (int)(screenSize.getHeight()/2-110));
		f.setPreferredSize(new Dimension(450, 220));
		f.setAlwaysOnTop(true);
		f.pack();
		f.setLocation(newDim);
		f.setVisible(true);

		while(i > 0){
			rotatePanel.rotate(); 
			if(i > 60){
				statusBar.setString("Starting..");
			}else if(i > 58 && i <= 60){
				statusBar.setValue(20);
				statusBar.setString("Validating Help..");
				if(!isHelpDirectoryAvailable()){
					statusBar.setString("Help Directory Not Found!!");
					//JOptionPane.showMessageDialog(null, "Help File Set not found at: "+helpDir.replaceAll("\\", "/"), "HelpSet not Found",JOptionPane.ERROR_MESSAGE);
					//System.exit(1);
				} else {
					statusBar.setString("HelpSet Found..");
				}
			}else if(i > 45 && i <= 58){
				statusBar.setValue(35);
				
			}else if(i > 43 && i <= 45){
				statusBar.setValue(45);
				statusBar.setString("Verifying Theme..");
				verifyTheme();
			}else if(i > 30 && i <= 43){
				if(themeAvailable){
					statusBar.setValue(65);
					statusBar.setString("Theme verified..");
				}else{
					statusBar.setValue(65);
					statusBar.setString("Theme not found..");
				}
			}else if(i > 20 && i <= 30){
				statusBar.setValue(85);
				statusBar.setString("Preparing for Launch..");
			}else{
				statusBar.setValue(100);
				statusBar.setString("Launching Application..");
			}
			try {
				Thread.sleep(80);
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(null, "Exception: "+e, "Thread Interrupted!!", JOptionPane.ERROR_MESSAGE);
			}
			i--;
		} 
	}
	public static void main(String[] args) throws Exception {
		new RotatePanel2();
	}
	public void closeF(){
		f.setVisible(false);
	}
	public boolean isHelpDirectoryAvailable(){
		boolean helpPresent = false;
	//	File file = new File(helpDir);
		String helpsetPath="C:\\JFileImporter\\JFIHelp\\HelpSet.hs";		
		File file = new File(helpsetPath);

		if(file.exists()){
			helpPresent = true;
		}
		return helpPresent;
	}
	public void verifyTheme(){
		themeAvailable = true;
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e1) {
			themeAvailable = false;
		} 
	}
}

