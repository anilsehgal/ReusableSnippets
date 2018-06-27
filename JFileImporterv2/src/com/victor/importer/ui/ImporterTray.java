package com.victor.importer.ui;

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 


import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

import com.victor.importer.main.JImporterMain;


public class ImporterTray {
    public static void startTrayIcon() {
        /* Use an appropriate Look and Feel */
        try {
        	UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {
        	JOptionPane.showMessageDialog(null, "Error setting Look & Feel "+ex, "Error setting Look & Feel", JOptionPane.ERROR_MESSAGE);
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        //Schedule a job for the event-dispatching thread:
        //adding TrayIcon.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    private static void createAndShowGUI() {
        //Check the SystemTray support
        if (!SystemTray.isSupported()) {
            return;
        }
        final PopupMenu popup = new PopupMenu();
        final TrayIcon trayIcon =
                new TrayIcon(createImage("/config/mut3.png", "JFileImporter"));
        final SystemTray tray = SystemTray.getSystemTray();
        Font itemFont = new Font("Ariel",Font.BOLD,12);
        // Create a popup menu components
        MenuItem aboutItem = new MenuItem("About");
        MenuItem maxItem = new MenuItem("Maximize");
        MenuItem minItem = new MenuItem("Minimize");
        MenuItem showSchedulerItem = new MenuItem("Show Scheduler");
        MenuItem showImporterItem = new MenuItem("Show Importer");
        MenuItem exitItem = new MenuItem("Exit");

        aboutItem.setFont(itemFont);
        maxItem.setFont(itemFont);
        minItem.setFont(itemFont);
        showSchedulerItem.setFont(itemFont);
        showImporterItem.setFont(itemFont);
        exitItem.setFont(itemFont);
        //Add components to popup menu
        popup.add(aboutItem);
        popup.addSeparator();
        popup.add(maxItem);
        popup.add(minItem);
        popup.addSeparator();
        popup.add(showSchedulerItem);
        popup.add(showImporterItem);        
        popup.addSeparator();
        popup.add(exitItem);
        
        trayIcon.setPopupMenu(popup);
        
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
        	JOptionPane.showMessageDialog(null, "Tray Icon could not be added "+e, "Error creating Tray Icon", JOptionPane.ERROR_MESSAGE);
            return;
        }
        trayIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	JImporterMain.maximize();
            }
        });
        
        maxItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	JImporterMain.maximize();
            }
        });
        minItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	trayIcon.displayMessage("JFileImporter", "Importer Still Running! Right Click for more options", TrayIcon.MessageType.INFO);
            	JImporterMain.minimize();
            	
            }
        });
        
        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	trayIcon.displayMessage("About", "JFileImporter\nVersion\t1.00\nDeveloped By:\tAnil Sehgal", TrayIcon.MessageType.INFO);
            }
        });
        
        showImporterItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	JImporterMain.showImporter();
            }
        });
        showSchedulerItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String schedulerShown = LoadProfileUI.showScheduler();
            	if(schedulerShown.equals("npe")){
            		trayIcon.displayMessage("Scheduler", "Please initialize the Scheduler from Menu", TrayIcon.MessageType.WARNING);
            	}else if(schedulerShown.equals("ge")){
            		trayIcon.displayMessage("Scheduler", "Error Launching Scheduler, Please contact technical support", TrayIcon.MessageType.ERROR);
            	}
            }
        });
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("JFileImporter");    
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int option = JOptionPane.showConfirmDialog(null, "If You Quit the Application, the scheduled job will terminate!", "Exit Confirmation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            	if(option == JOptionPane.OK_OPTION){
	                tray.remove(trayIcon);
	                System.exit(0);
            	}
            }
        });
    }
    
    //Obtain the image URL
    public static Image createImage(String path, String description) {
        URL imageURL = ImporterTray.class.getResource(path);
        
        if (imageURL == null) {
        	JOptionPane.showMessageDialog(null, "Tray Icon not found at "+path+"!!", "Error setting Tray Icon", JOptionPane.ERROR_MESSAGE);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }
}
