package com.victor.multiUtilityToolkit.jms.ui;

import java.awt.Rectangle;

import javax.swing.JComboBox;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.plaf.metal.MetalComboBoxUI;

class JScrollingComboBox extends JComboBox{       

	private static final long serialVersionUID = 3593833135964170257L;
	public JScrollingComboBox(){           
		super();           
		setUI(new scrollingComboBoxUI());               
	}              
	public class scrollingComboBoxUI extends MetalComboBoxUI{                                   
		protected ComboPopup createPopup(){               
			BasicComboPopup popup = new BasicComboPopup(comboBox){                      

				private static final long serialVersionUID = -6971530901859353210L;
				protected Rectangle computePopupBounds(int x, int y, int width, int height){                       // Check whether scrollbar must be displayed                       
					if(list.getPreferredSize().getWidth() > width)                           
						height += (int)new JScrollBar().getPreferredSize().getWidth();                                              
					return super.computePopupBounds(x, y, width, height);                   
				}                     
				protected JScrollPane createScroller() {                       
					JScrollPane pane =  new JScrollPane( list, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,                                               
							ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED );                                                                  
					return pane;                   
				}               
			};                 
			return popup;           
		}       
	}   
}
