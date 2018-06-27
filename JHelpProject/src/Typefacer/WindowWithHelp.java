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
 import javax.help.*;

// ....................................... TypeFacer (frame)
 
public class WindowWithHelp extends JFrame {

	private static final long serialVersionUID = 7142571727877117864L;
// JavaHelp items

  HelpSet hs;
  HelpBroker hb;

  // menu components
  JMenu helpMenu;
  JMenuItem helpItemTOC;


  // frame will contain a panel using the CardLayout manager

  CardLayout manager;
  JPanel cards;

  // ....................................... constructor

  public WindowWithHelp() {


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
 
     try {
       URL hsURL = WindowWithHelp.class.getResource(("/TypeFacer/TFHelp/HelpSet.hs"));
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
    helpMenu = new JMenu("Help");
    helpItemTOC = new JMenuItem("Contents");
    helpMenu.add(helpItemTOC);
    CSH.setHelpIDString(helpItemTOC, "overview");
    //!!in next line, change "Contents" to "For This Screen"


    JMenuBar menuBar = new JMenuBar();

    menuBar.add(helpMenu);

    setJMenuBar(menuBar);

    // activate the Help menu item and Help button

    ActionListener helper = new CSH.DisplayHelpFromSource(hb);
    helpItemTOC.addActionListener(helper);
  }

  public static void main(String args[]) {
    (new WindowWithHelp()).setVisible(true);
  }
}
