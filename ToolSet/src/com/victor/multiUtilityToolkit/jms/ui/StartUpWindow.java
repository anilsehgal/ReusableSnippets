package com.victor.multiUtilityToolkit.jms.ui;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class StartUpWindow extends JPanel {

	private static final long serialVersionUID = -1420973722719087731L;
	BufferedImage image;

    public StartUpWindow(BufferedImage image) {
        this.image = image;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw image centered.
        int x = (getWidth() - image.getWidth())/2;
        int y = (getHeight() - image.getHeight())/2;
        g.drawImage(image, x, y, this);
    }
}