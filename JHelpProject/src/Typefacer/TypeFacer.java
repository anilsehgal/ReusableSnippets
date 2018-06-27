package Typefacer;

/*
 * TypeFacer.java
 * A simple application for styling sample text.
 *
 * This application serves as the basis for JavaHelp demonstrations:
 * screen-level help, field-level help, and embedded help
 */

// ....................................... imports
import java.awt.event.*;
import java.awt.*;
import java.util.Hashtable;
import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.border.*;
 import javax.help.*;

// ....................................... TypeFacer (frame)
 
public class TypeFacer extends JFrame {

  // JavaHelp items

  HelpSet hs;
  HelpBroker hb;

  // screen components
  JButton    helpButton;
  JButton    qButton;

  // menu components
  JMenu helpMenu;
  JMenuItem helpItem;
  JMenuItem helpItemTOC;


  // frame will contain a panel using the CardLayout manager

  CardLayout manager;
  JPanel cards;

  // ....................................... constructor

  public TypeFacer() {


        // create and size a JFrame; set up content pane

    super("Typeface Tester: Choose Typeface");
    //!!in next line, change "250" to "500"
    setSize(500, 250);

    JPanel contentPane = (JPanel) getContentPane();
    contentPane.setLayout(new FlowLayout());
    contentPane.setBorder(
      BorderFactory.createEmptyBorder(10,10,10,10));

    // exit the program if the user closes the window

    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent we) {System.exit(0);}
    });

     // open HelpSet, send console message
     // hardcoded location: "HelpSet.hs" in "TFHelp" subdirectory
 
     try {
       URL hsURL = TypeFacer.class.getResource(("/TypeFacer/TFHelp/HelpSet.hs"));
       hs = new HelpSet(null, hsURL);
       System.out.println("Found help set at " + hsURL);
     }
     catch (Exception ee) {
       System.out.println("Here HelpSet not found");
       System.exit(0);
     }
 
     // create HelpBroker from HelpSet
     hb = hs.createHelpBroker();

     // enable function key F1
     hb.enableHelpKey(getRootPane(), "overview", hs);

    // set up top-most panel containing text-input field




        // set up styled output panel

    JPanel displayPanel = new JPanel(new FlowLayout());
    contentPane.add(displayPanel);



        // set up button panel

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
    contentPane.add(buttonPanel);

    helpButton = new JButton("Help");
    qButton = new JButton(new ImageIcon(TypeFacer.class.getResource("/Typefacer/TFHelp/Images/help.gif")));  // make this an Icon


    buttonPanel.add(helpButton);
    buttonPanel.add(qButton);


    



    helpMenu = new JMenu("Help");
    helpItemTOC = new JMenuItem("Contents");
    helpMenu.add(helpItemTOC);
    CSH.setHelpIDString(helpItemTOC, "overview");
    //!!in next line, change "Contents" to "For This Screen"
    helpItem = new JMenuItem("Contents...");
    helpMenu.add(helpItem);


    JMenuBar menuBar = new JMenuBar();

    menuBar.add(helpMenu);

    setJMenuBar(menuBar);

    // Fill the fonts and colors hashtables



    // activate the "Clear" button


    // activate the "Exit" menu item




    // activate the "Colors" menu item



    // activate the "Embedded Help" toggle menu item

 


    // activate the field-level help button

    qButton.addActionListener(
      new CSH.DisplayHelpAfterTracking(hb)
    );
//
    // activate the Help menu item and Help button

    ActionListener helper = new CSH.DisplayHelpFromSource(hb);
    helpItem.addActionListener(helper);
    helpButton.addActionListener(helper);
    helpItemTOC.addActionListener(helper);

    // assign map IDs for field-level context-sensitive help


    CSH.setHelpIDString(helpButton, "help");
    CSH.setHelpIDString(qButton, "whats_this");


  }

  public static void main(String args[]) {
    (new TypeFacer()).setVisible(true);
  }
}
