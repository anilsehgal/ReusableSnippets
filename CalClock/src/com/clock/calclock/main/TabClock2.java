package com.clock.calclock.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



import org.jdesktop.swingx.JXPanel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.experimental.chart.plot.dial.DialBackground;
import org.jfree.experimental.chart.plot.dial.DialCap;
import org.jfree.experimental.chart.plot.dial.DialPlot;
import org.jfree.experimental.chart.plot.dial.DialPointer;
import org.jfree.experimental.chart.plot.dial.SimpleDialFrame;
import org.jfree.experimental.chart.plot.dial.StandardDialScale;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.StandardGradientPaintTransformer;

import com.sun.awt.AWTUtilities;
import com.toedter.calendar.JCalendar;


public class TabClock2 {
	static int hr;
	static int hr2;
	static int min;
	static int sec;
	static Date date;
	static TimeZone timeZone;
	static Color newForegroundColor;
	public static void main(String args[]) throws Exception{
		SimpleDateFormat dateFormatter=new SimpleDateFormat("hh:mm:ss");
		Date sdate = new Date(); // get the system date
		String sysDate =
		dateFormatter.format(sdate);
		sec=(new Integer(sysDate.substring(6, 8))).intValue();
		min=(new Integer(sysDate.substring(3, 5))).intValue();
		hr2=(new Integer(sysDate.substring(0, 2))).intValue()*5;


		final DefaultValueDataset dataset1 = new DefaultValueDataset(sec);
		final DefaultValueDataset dataset2 = new DefaultValueDataset(min);
		final DefaultValueDataset dataset3 = new DefaultValueDataset(hr2);
		initializeLookAndFeels();
		DialPlot plot = new DialPlot();
		plot.setView(0.0, 0.0, 1.0, 1.0);
		plot.setDataset(0, dataset1);
		plot.setDataset(1, dataset2);
		plot.setDataset(2, dataset3);
		final SimpleDialFrame dialFrame = new SimpleDialFrame();
		dialFrame.setBackgroundPaint(Color.lightGray);
		dialFrame.setForegroundPaint(Color.darkGray);

		plot.setDialFrame(dialFrame);

		GradientPaint gp = new GradientPaint(new Point(), new Color(255, 255, 255), new Point(), new Color(220, 208, 171));

		DialBackground db = new DialBackground();

		db.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.HORIZONTAL));
		plot.setBackgroundPaint(gp);

		final StandardDialScale scale = new StandardDialScale(0, 60, 90, -360);
		scale.setTickRadius(0.90);
		scale.setTickLabelOffset(5.0);
		scale.setTickLabelFont(new Font("Dialog", Font.PLAIN, 14));
		plot.addScale(0, scale);
		DialCap dialcap = new DialCap();                          
		dialcap.setRadius(0.10000000000000001D);                          
		plot.setCap(dialcap);

		DialPointer needle2 = new DialPointer.Pin(0);
		needle2.setRadius(0.85);
		plot.addLayer(needle2);
		DialPointer needle = new DialPointer.Pointer(1);
		plot.addLayer(needle);
		DialPointer needle3 = new DialPointer.Pointer(2);
		needle3.setRadius(0.65);
		plot.addLayer(needle3);
		plot.setForegroundAlpha(0.1f);
		final JFreeChart chart1 = new JFreeChart(plot);

		final ChartPanel cp1 = new ChartPanel(chart1); 

		cp1.setPreferredSize(new Dimension(170, 170));
		final JFrame frame = new JFrame();
		frame.setResizable(false);
	//	AWTUtilities.setWindowOpacity(frame, 0.99f);



		final JTextField jTextField = new JTextField();
		jTextField.setToolTipText("Current Time");
		Font f = new Font("Arial", 1, 14);
		jTextField.setFont(f);
		jTextField.setHorizontalAlignment(JTextField.CENTER);
		jTextField.setText((new Date()).toString());
		jTextField.setEditable(false);
		final JComboBox jComboBox = new JComboBox();
		jComboBox.setMaximumSize(new Dimension(frame.getWidth()/2, 25));
		jComboBox.addItem("IST");
		jComboBox.addItem("GMT");
		jComboBox.addItem("CST");
		jComboBox.addItem("MST");
		jComboBox.addItem("PST");
		jComboBox.addItem("AST");
		jComboBox.addItem("HST");
		jComboBox.addItem("EST");
		String[] ids = TimeZone.getAvailableIDs();
		for(int i=0;i<ids.length;i++){
			jComboBox.addItem(ids[i]);
		}
		jComboBox.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String timeZn = (String) jComboBox.getSelectedItem();
				timeZone = TimeZone.getTimeZone(timeZn);
				SimpleDateFormat dateFormat=new SimpleDateFormat("hh:mm:ss");
				dateFormat.setTimeZone(timeZone);
				Date date = new Date();
				String sysDate2 = dateFormat.format(date);
				sec=(new Integer(sysDate2.substring(6, 8))).intValue();
				min=(new Integer(sysDate2.substring(3, 5))).intValue();
				hr2=(new Integer(sysDate2.substring(0, 2))).intValue()*5;
				dataset1.setValue(sec);
				dataset2.setValue(min);
				dataset3.setValue(hr2);
			}
		});


		final JRadioButton jRadioButton4 = new JRadioButton();
		final JRadioButton jRadioButton5 = new JRadioButton();
		jRadioButton5.setSelected(true);
		final ButtonGroup buttonGroup2 = new ButtonGroup();
		buttonGroup2.add(jRadioButton4);
		buttonGroup2.add(jRadioButton5);

		final JSlider jSlider = new JSlider(40, 100, 100);
		jSlider.setMajorTickSpacing((int) 5);

		final JCheckBox checkBox1 = new JCheckBox();
		checkBox1.setSelected(true);
		checkBox1.addItemListener(new ItemListener() {


			public void itemStateChanged(ItemEvent e) {
				if(checkBox1.isSelected()){
					dialFrame.setVisible(true);
				}else{
					dialFrame.setVisible(false);
				}
			}
		});

		final JCheckBox checkBox2 = new JCheckBox();
		checkBox2.setSelected(true);
		checkBox2.addItemListener(new ItemListener() {


			public void itemStateChanged(ItemEvent e) {
				if(checkBox2.isSelected()){
					scale.setVisible(true); 
				}else{
					scale.setVisible(false); 
				}
			}
		});

		JCalendar jCalendar = new JCalendar();


		final JComboBox jComboBox2 = new JComboBox();
		jComboBox2.setMaximumSize(new Dimension(frame.getWidth()/2, 25));
		jComboBox2.addItem("None");
		jComboBox2.addItem("Mickey Mouse");
		jComboBox2.addItem("Blue Fish");
		jComboBox2.addItem("Windows");
		jComboBox2.addItem("Analog");
		jComboBox2.addItem("Roman");
		jComboBox2.addItem("Wall");
		jComboBox2.addItem("Nature");
		jComboBox2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Image img = null;
				try {
					if(!jComboBox2.getSelectedItem().equals("None")){
						img = Toolkit.getDefaultToolkit().getImage(getClass().getResource(getThemeFile((String) jComboBox2.getSelectedItem()))); 
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				chart1.setBackgroundImage(img);

			}
		});
		final JLabel label = new JLabel("Color Themes", JLabel.CENTER);
		final JColorChooser colorChooser2 = new JColorChooser(label.getBackground());
		final JButton button33 = new JButton("Pick Color");

		//    colorChooserPanel
		final JXPanel colorChooserPanel = new JXPanel();
		colorChooserPanel.add(button33);

		final JXPanel calendarPanel = new JXPanel();
		calendarPanel.add(jCalendar);

		final JXPanel digitalClockModePanel = new JXPanel();	
		final JLabel l14 = new JLabel("12 Hour:");
		digitalClockModePanel.add(l14);
		digitalClockModePanel.add(jRadioButton4);

		final JLabel l15 = new JLabel("24 Hour:");
		digitalClockModePanel.add(l15);
		digitalClockModePanel.add(jRadioButton5);	      

		final JXPanel comboPanel = new JXPanel();
		final JLabel l1 = new JLabel("Select Time Zone:");
		comboPanel.add(l1);
		comboPanel.add(jComboBox);

		final JXPanel textPanel = new JXPanel();
		final JLabel l2 = new JLabel("Current Time:");
		textPanel.add(l2);
		textPanel.add(jTextField);	

		final JXPanel titlePanel = new JXPanel();
		final JLabel l8 = new JLabel("PC Clock");
		l8.setFont(new Font("Arial", Font.BOLD, 14));
		titlePanel.add(l8);

		final JXPanel aboutPanel = new JXPanel();
		final JLabel l13 = new JLabel();
		String text = "Developed By: Ritika (Roll No: 0818CS121110)";

		l13.setText(text);

		aboutPanel.add(l13);
		final JXPanel verPanel = new JXPanel();
		final JLabel l16 = new JLabel();
		String ver = "v1.0";

		l16.setText(ver);

		verPanel.add(l16);

		final JXPanel clockPanel = new JXPanel();
		clockPanel.add(cp1);

		final JXPanel opacPanel = new JXPanel();
		final JLabel l3 = new JLabel("Adjust Translucency:");
		opacPanel.add(l3);
		opacPanel.add(jSlider);

		final JXPanel dialPanel = new JXPanel();
		final JLabel l4 = new JLabel("Show Dial:");
		dialPanel.add(l4);
		dialPanel.add(checkBox1);
		final JLabel l5 = new JLabel("Show Scale:");
		dialPanel.add(l5);
		dialPanel.add(checkBox2);

		final JXPanel themePanel = new JXPanel();
		final JLabel l6 = new JLabel("Pick Theme:");
		themePanel.add(l6);
		themePanel.add(jComboBox2);

		jSlider.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();  // get the slider
				if (!source.getValueIsAdjusting()) {
					int val = source.getValue();  // get slider value
					AWTUtilities.setWindowOpacity(frame, val/100f);
				}
			}
		});
		GridBagConstraints pos = new GridBagConstraints();
		final JXPanel content = new JXPanel(new GridBagLayout());
		content.setBorder(BorderFactory.createEmptyBorder(12, 12, 
				12, 12));

		final JXPanel content2 = new JXPanel(new GridBagLayout());
		content2.setBorder(BorderFactory.createEmptyBorder(12, 12, 
				12, 12));

		final JXPanel content3 = new JXPanel(new GridBagLayout());
		content3.setBorder(BorderFactory.createEmptyBorder(12, 12, 
				12, 12));
		final JXPanel content4 = new JXPanel(new GridBagLayout());
		content4.setBorder(BorderFactory.createEmptyBorder(12, 12, 
				12, 12));
		final JXPanel content5 = new JXPanel(new BorderLayout());
		
		
		/////////////////////////////
		Image im = Toolkit.getDefaultToolkit().getImage(TabClock2.class.getResource("/pics/clock.png"));
		frame.setIconImage(im);
		/////////////////////////////

		pos.gridx = 0;
		pos.gridy = 0;
		content.add(clockPanel,pos);
		pos.gridx = 0;
		pos.gridy = 1;
		content.add(textPanel,pos);
		pos.gridx = 0;
		pos.gridy = 2;
		content2.add(comboPanel,pos);
		pos.gridx = 0;
		pos.gridy = 3;
		content2.add(opacPanel,pos);
		pos.gridx = 0;
		pos.gridy = 4;
		content2.add(digitalClockModePanel,pos);
		pos.gridx = 0;
		pos.gridy = 0;
		content2.add(dialPanel,pos);
		pos.gridx = 0;
		pos.gridy = 1;
		content2.add(themePanel,pos);
		pos.gridx = 0;
		pos.gridy = 0;
		content3.add(calendarPanel,pos);
		pos.gridx = 0;
		pos.gridy = 6;
		content2.add(colorChooserPanel,pos);

		content5.add(titlePanel,BorderLayout.NORTH);
		content5.add(aboutPanel,BorderLayout.CENTER);
		content5.add(verPanel,BorderLayout.PAGE_END);

		final JTabbedPane tabbedPane = new JTabbedPane();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final ActionListener okListener = new ActionListener() {
		    // Called when user clicks ok
		    public void actionPerformed(ActionEvent evt) {
		        // Note: The source is an internal button in the dialog
		        // and should not be used

		        // Get selected color
		    	newForegroundColor = colorChooser2.getColor();
				label.setForeground(newForegroundColor);
				tabbedPane.setBackground(newForegroundColor);
				aboutPanel.setBackground(newForegroundColor);
				titlePanel.setBackground(newForegroundColor);
				chart1.setBackgroundPaint(newForegroundColor);
				clockPanel.setBackground(newForegroundColor);
				textPanel.setBackground(newForegroundColor);
				comboPanel.setBackground(newForegroundColor);
				digitalClockModePanel.setBackground(newForegroundColor);
				opacPanel.setBackground(newForegroundColor);
				dialPanel.setBackground(newForegroundColor);
				verPanel.setBackground(newForegroundColor);
				themePanel.setBackground(newForegroundColor);
				calendarPanel.setBackground(newForegroundColor);
				colorChooserPanel.setBackground(newForegroundColor);
				l1.setBackground(newForegroundColor);
				l2.setBackground(newForegroundColor);
				l3.setBackground(newForegroundColor);
				l4.setBackground(newForegroundColor);
				l5.setBackground(newForegroundColor);
				l6.setBackground(newForegroundColor);
				l8.setBackground(newForegroundColor);
				l13.setBackground(newForegroundColor);
				l14.setBackground(newForegroundColor);
				cp1.setBackground(newForegroundColor);
				button33.setBackground(newForegroundColor);
				checkBox1.setBackground(newForegroundColor);
				checkBox2.setBackground(newForegroundColor);
				jRadioButton4.setBackground(newForegroundColor);
				jRadioButton5.setBackground(newForegroundColor);
				jSlider.setBackground(newForegroundColor);
				frame.setBackground(newForegroundColor);
				content.setBackground(newForegroundColor);
				content2.setBackground(newForegroundColor);
				content3.setBackground(newForegroundColor);
				content5.setBackground(newForegroundColor);
		    }
		};


		final ActionListener cancelListener = new ActionListener() {

		    public void actionPerformed(ActionEvent evt) {

		    	newForegroundColor = frame.getForeground();
		    }
		};

		

		button33.addActionListener(new ActionListener() {


			public void actionPerformed(ActionEvent e) {
				Dialog dialog = JColorChooser.createDialog(null, "choose a color", true,colorChooser2,okListener,cancelListener);
				dialog.setVisible(true);
				dialog.addWindowListener(new WindowAdapter() {

				    public void windowClosing(WindowEvent evt) {

				    	newForegroundColor = frame.getForeground();
				    }
				}) ;
			}
		});
		tabbedPane.addTab("Main", content);
		tabbedPane.addTab("Calendar", content3);
		tabbedPane.addTab("Settings", content2);
		tabbedPane.addTab("About", content5);
		frame.setContentPane(tabbedPane);
		frame.pack();
		frame.setVisible(true);
		
		while(sec <= 60){
			String timeZn = (String) jComboBox.getSelectedItem();
			timeZone = TimeZone.getTimeZone(timeZn);
			SimpleDateFormat dateFormat=new SimpleDateFormat("hh:mm:ss");
			dateFormat.setTimeZone(timeZone);
			SimpleDateFormat dateFormat2=new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
			dateFormat2.setTimeZone(timeZone);
			SimpleDateFormat dateFormat3=new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
			dateFormat3.setTimeZone(timeZone);
			Date date = new Date();
			String sysDate2 = dateFormat.format(date);
			String sysDate3 = dateFormat2.format(date);
			String sysDate4 = dateFormat3.format(date);
			sec=(new Integer(sysDate2.substring(6, 8))).intValue();
			min=(new Integer(sysDate2.substring(3, 5))).intValue();
			hr=(new Integer(sysDate2.substring(0, 2))).intValue();
			dataset1.setValue(sec);
			dataset2.setValue(min);
			dataset3.setValue(hr*5);
			
			if(jRadioButton4.isSelected()){
				jTextField.setText(sysDate3);	
				frame.setTitle(sysDate3.substring(0,6)+" "+sysDate3.substring(12,17)+sysDate3.substring(21,23)+" "+timeZone.getDisplayName());
			}else{
				jTextField.setText(sysDate4);
				frame.setTitle(sysDate4.substring(0,6)+" "+sysDate4.substring(12,17)+" "+timeZone.getDisplayName());
			}
			Thread.sleep(1000);  	  
		}
	}
	public final static void initializeLookAndFeels() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}  catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static String appendZero(int number){
		String returnString = number+"";
		if(number < 10){
			returnString = "0"+returnString;
		}
		return returnString;
	}
	public static String getThemeFile(String themeName){
		String themeImg = null;
		if(themeName.equals("Mickey Mouse")){
			themeImg = "/pics/clock.png";
		}
		else if(themeName.equals("Blue Fish")){
			themeImg = "/pics/fish1.gif";
		}else if(themeName.equals("None")){
			themeImg = null;
		}else if(themeName.equals("Windows")){
			themeImg = "/pics/1.jpg";
		}else if(themeName.equals("Analog")){
			themeImg = "/pics/2.png";
		}else if(themeName.equals("Roman")){
			themeImg = "/pics/3.jpg";
		}else if(themeName.equals("Wall")){
			themeImg = "/pics/4.gif";
		}else if(themeName.equals("Nature")){
			themeImg = "/pics/5.jpg";
		}else if(themeName.equals("None")){
			themeImg = null;
		}
		return themeImg;
	}
}
