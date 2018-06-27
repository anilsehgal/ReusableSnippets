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

package com.victor.multiUtilityToolkit.main;  
/*
 * TrayIconDemo.java
 */

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

import com.victor.multiUtilityToolkit.bean.StaticBean;

public class ToolKitTrayIcon {
    public static void startTrayIcon() {
        /* Use an appropriate Look and Feel */
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ex) {
            ex.printStackTrace();
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
            System.out.println("SystemTray is not supported");
            return;
        }
        final PopupMenu popup = new PopupMenu();
        final TrayIcon trayIcon =
                new TrayIcon(createImage("/images/mut3.png", "MultiUtility Toolkit"));
        final SystemTray tray = SystemTray.getSystemTray();
        
        // Create a popup menu components
        MenuItem aboutItem = new MenuItem("About");
        MenuItem maxItem = new MenuItem("Maximize");
        MenuItem minItem = new MenuItem("Minimize");
        MenuItem exitItem = new MenuItem("Exit");
        CheckboxMenuItem cb1 = new CheckboxMenuItem("File Logging Enabled");
        CheckboxMenuItem cb2 = new CheckboxMenuItem("Active Logging Enabled");
        cb2.setState(true);
        //Add components to popup menu
        popup.add(aboutItem);
        popup.addSeparator();
        popup.add(maxItem);
        popup.add(minItem);
        popup.addSeparator();
        popup.add(cb1);
        popup.add(cb2);
        popup.addSeparator();
        popup.add(exitItem);
        
        trayIcon.setPopupMenu(popup);
        
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            return;
        }
        cb1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                int cb1Id = e.getStateChange();
                if (cb1Id == ItemEvent.SELECTED){
                	StaticBean.setFileLoggingEnabled(true);
                	trayIcon.displayMessage("MultiUtility Toolkit", "File Logging Enabled", TrayIcon.MessageType.INFO);
                } else {
                	StaticBean.setFileLoggingEnabled(false);
                	trayIcon.displayMessage("MultiUtility Toolkit", "File Logging Disabled", TrayIcon.MessageType.INFO);
                }
            }
        });
        cb2.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                int cb1Id = e.getStateChange();
                if (cb1Id == ItemEvent.SELECTED){
                	StaticBean.setActiveLoggingEnabled(true);
                	trayIcon.displayMessage("MultiUtility Toolkit", "Active Logging Enabled", TrayIcon.MessageType.INFO);
                } else {
                	StaticBean.setActiveLoggingEnabled(false);
                	trayIcon.displayMessage("MultiUtility Toolkit", "Active Logging Disabled", TrayIcon.MessageType.INFO);
                }
            }
        });
        trayIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	MultiUtilityToolkitMain.maximize();
            }
        });
        
        maxItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	MultiUtilityToolkitMain.maximize();
            }
        });
        minItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	trayIcon.displayMessage("MultiUtility Toolkit", "ToolKit Still Running! Right Click for more options", TrayIcon.MessageType.INFO);
            	MultiUtilityToolkitMain.minimize();
            	
            }
        });
        
        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	trayIcon.displayMessage("About", "MultiUtility Toolkit\nVersion\t1.00\nDeveloped By:\tAnil Sehgal", TrayIcon.MessageType.INFO);
            }
        });
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("MultiUtility Toolkit");    
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
                System.exit(0);
            }
        });
    }
    
    //Obtain the image URL
    protected static Image createImage(String path, String description) {
        URL imageURL = ToolKitTrayIcon.class.getResource(path);
        
        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }
}
