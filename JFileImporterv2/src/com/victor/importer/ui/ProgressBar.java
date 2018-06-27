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

package com.victor.importer.ui;

import java.awt.*;

import javax.swing.*;

import com.victor.importer.constants.ImporterBean;


public class ProgressBar extends JPanel
                             implements Runnable {

	private static final long serialVersionUID = 7398340914569151274L;
	private JProgressBar progressBar;
    int noOfRows = 0;
    
    public int getNoOfRows() {
		return noOfRows;
	}
	public void setNoOfRows(int noOfRows) {
		this.noOfRows = noOfRows;
	}
	
	public float getRowsTimePercent(){
		float f = 0;
		int rowsToImport = this.getNoOfRows();
		f = rowsToImport/10f;
		return f;
	}
    public ProgressBar(int i) {
        super();
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "Error setting Look & Feel "+e1, "Error setting Look & Feel", JOptionPane.ERROR_MESSAGE);
		} 
        this.setNoOfRows(i);
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        JPanel panel = new JPanel();
        panel.add(progressBar);
        panel.setBackground(Color.WHITE);
        panel.setForeground(Color.WHITE);
        setBackground(Color.WHITE);
        setForeground(Color.WHITE);  
        System.out.println("test");
        add(panel, BorderLayout.CENTER);
        setPreferredSize(new Dimension(200,40));
        System.out.println("test2");
        
//        Point screenPt = JImporterMain.jImporterMain.getLocation();
//        Dimension screen = JImporterMain.jImporterMain.getPreferredSize();
  //      int x = (int) ((screen.getWidth()-200)/2f);
 //       int y = (int) ((screen.getHeight()-40)/2f);
        System.out.println("test3");
  //      setLocation((int)(screenPt.getX())+x,(int)(screenPt.getY()+y));
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
  /*      setAlwaysOnTop(true);
        pack();*/
        setVisible(true);
    }
	@Override
	public void run() {
        //Initialize progress property.
        progressBar.setValue(0);
        float f = 0f;
        while (f < 100f) {
        	f = ImporterBean.getRowsImported()*100/this.getNoOfRows();      	
        	progressBar.setValue((int)f);
            try {
				Thread.currentThread().sleep(10);
			} catch (InterruptedException e) {

				f = 103f;
			} catch (Exception e1) {
				progressBar.setValue(100);
				f = 103f;

				e1.printStackTrace();
			}finally{
				
				if(f == 103f){
					progressBar.setValue(0);
					//dispose();
				}
			}
        }
	        try {
	        	progressBar.setValue(100);
			} catch (Exception e1) {
				progressBar.setValue(0);
				e1.printStackTrace();
			} finally{
				if(this!=null && this.isVisible()){
					progressBar.setValue(100);
				}
			}
	}
}
