package com.victor.importer.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
public class LineNumberTable extends JTable
{
	private JTable mainTable;
	public LineNumberTable(JTable table)
	{
		super();
		mainTable = table;
		setAutoCreateColumnsFromModel( false );
		setModel( mainTable.getModel() );
		setSelectionModel( mainTable.getSelectionModel() );
		setAutoscrolls( false );
		addColumn( new TableColumn() );
		getColumnModel().getColumn(0).setCellRenderer(
				mainTable.getTableHeader().getDefaultRenderer() );
		getColumnModel().getColumn(0).setPreferredWidth(50);
		setPreferredScrollableViewportSize(getPreferredSize());
	}
	public boolean isCellEditable(int row, int column)
	{
		return false;
	}
	public Object getValueAt(int row, int column)
	{
		return new Integer(row + 1);
	}
	public int getRowHeight(int row)
	{
		return mainTable.getRowHeight();
	}
	public static void main(String[] args)
	throws Exception
	{
		DefaultTableModel model = new DefaultTableModel(100, 5);
		JTable table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane( table );
		JTable lineTable = new LineNumberTable( table );
		scrollPane.setRowHeaderView( lineTable );
		JFrame frame = new JFrame( "Line Number Table" );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		frame.setSize(400, 300);
		frame.setVisible(true);
	}
}
