package com.victor.multiUtilityToolkit.logging.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;

import com.victor.multiUtilityToolkit.bean.StaticBean;
import com.victor.multiUtilityToolkit.jms.constants.JMSConstants;
import com.victor.multiUtilityToolkit.jms.constants.JMSStatusLabelConstants;
import com.victor.multiUtilityToolkit.jms.supportMethods.FileMethods;
import com.victor.multiUtilityToolkit.jms.supportMethods.GenericMethods;
import com.victor.multiUtilityToolkit.jms.ui.MonitorMain;
import com.victor.multiUtilityToolkit.logging.supportMethods.LogTableModel;

public class LogWindow extends JPanel{

	private static final long serialVersionUID = -3032313116186577867L;
	
	public static JTable logTable;
	public JScrollPane scroller;
	LogTableModel dtm;
	Dimension screenSize = null;
	GridBagConstraints position = null;
	JPanel buttonPanel = null;
	JButton saveAllButton = null;
	JButton clearAllButton = null;
	public LogWindow() throws Exception{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		position = new GridBagConstraints();
		buttonPanel = new JPanel(new GridBagLayout());
		saveAllButton = new JButton("Save All Logs");
		clearAllButton = new JButton("Clear All Logs");
		
		position.gridx=0;
		position.gridy=0;
		buttonPanel.add(saveAllButton, position);
		position.gridx=0;
		position.gridy=1;
		buttonPanel.add(clearAllButton, position);
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		setLayout(new GridBagLayout());
		
		dtm = new LogTableModel();
		logTable = new JTable(dtm);
		scroller = new JScrollPane(logTable);
		scroller.setPreferredSize(new Dimension((int)((screenSize.getWidth()*3/4)+15 - clearAllButton.getPreferredSize().getWidth()),(int)screenSize.getHeight()/10));
		position.gridx=0;
		position.gridy=0;
		add(scroller, position);
		position.gridx=1;
		position.gridy=0;
		add(buttonPanel, position);
		dtm.setDataVector(null, GenericMethods.getLogTitlesAsArray());
		logTable.setBackground(Color.WHITE);		
		scroller.setForeground(Color.WHITE);
		scroller.setBackground(Color.WHITE);
		setForeground(Color.WHITE);
		setBackground(Color.WHITE);
		clearAllButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					setLogVector(null);
					for(int i=0;i < StaticBean.getLogContents().size();i++){
						StaticBean.getLogContents().remove(i);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		saveAllButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				List<String> logs = StaticBean.getLogContents();
				if(logs!=null && logs.size() > 0){
					JFileChooser saveAllWindow = new JFileChooser();
					int rVal = saveAllWindow.showSaveDialog(LogWindow.this);
					if (rVal == JFileChooser.APPROVE_OPTION) {
						MonitorMain.statusLabel.setText(JMSStatusLabelConstants.SAVING_ALL);
						String dir=saveAllWindow.getCurrentDirectory().toString()+JMSConstants.DOUBLE_BACK_SLASH+saveAllWindow.getSelectedFile().getName();
						try {
							FileMethods.saveAllLogsToFiles(logs, dir);
						} catch (Exception e1) {
							MonitorMain.statusLabel.setText(JMSStatusLabelConstants.SAVE_ALL_FAIL);
						}
						MonitorMain.statusLabel.setText(JMSStatusLabelConstants.SAVE_ALL_COMPLETE);
					}
				}else{
					MonitorMain.statusLabel.setText("No Logs Found");
				}
			}
		});		
		logTable.addMouseListener(new DoubleClickListener());
	}
	public void setLogVector(Object[][] rows) throws Exception{
		StaticBean.setLogs(rows);
		dtm.setDataVector(rows, GenericMethods.getLogTitlesAsArray());
	}
	public void addLog(Object[] row, String logDetail) throws Exception{
		Object[][] logs = StaticBean.getLogs();
		Object[][] newLogs = null;	
		if(logs!=null){
			newLogs = new Object[logs.length+1][3];
			for(int index=0;index<logs.length;index++){
				newLogs[index][0] = logs[index][0];
				newLogs[index][1] = logs[index][1];
				newLogs[index][2] = logs[index][2];
			}			
			newLogs[logs.length][0] = row[0];
			newLogs[logs.length][1] = row[1];
			newLogs[logs.length][2] = row[2];
		}else{
			newLogs = new Object[1][3];
			newLogs[0][0] = row[0];
			newLogs[0][1] = row[1];
			newLogs[0][2] = row[2];
		}
		if(StaticBean.isActiveLoggingEnabled()){
			setLogVector(newLogs);
			StaticBean.addLogContents(logDetail);
		}
		if(StaticBean.isFileLoggingEnabled()){
			new FileLog(logDetail);
		}
	}

}
class DoubleClickListener implements MouseListener{

	@Override
	public void mouseClicked(MouseEvent e) {
		new LogDetailDialog(StaticBean.getLogContents(LogWindow.logTable.getSelectedRow()),"Action Log");
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
	
}


