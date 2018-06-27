package com.psa.entities.gui.controls;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

import com.psa.constants.PSAConstants;
import com.psa.entities.gui.storage.MappingStore;
import com.psa.entities.resolvers.TableResolver;
import com.psa.entities.serializable.EndPointBase;

public class EndPointTableStructure extends TabPane implements PSAConstants {
	
	private TableView<EndPointBase> tableView;
	private Tab tab;
	private String className;
	private String structureType;
	public static final int TABLE_WIDTH = 205;
	
	@SuppressWarnings("unchecked")
	public EndPointTableStructure( String className , String type , String structureType ) throws SecurityException, ClassNotFoundException {

		tab = new Tab( type );
		tableView = new TableView<EndPointBase>();
		TableColumn<EndPointBase, String> tableColumn1 = new TableColumn<EndPointBase, String>( "Name" );
		TableColumn<EndPointBase, String> tableColumn2 = new TableColumn<EndPointBase, String>( "Type" );
		tableColumn1.setCellValueFactory(new PropertyValueFactory<EndPointBase, String>( EndPointBase.getNameDescriptor() ));
		tableColumn2.setCellValueFactory(new PropertyValueFactory<EndPointBase, String>( EndPointBase.getDataTypeDescriptor() ));
		tableColumn1.setMinWidth(100);
		tableColumn2.setMinWidth(100);
		tableView.getColumns().addAll( tableColumn1 , tableColumn2 );
		
		if( ! structureType.equals( CONVERTER ) ) {
		
			for ( EndPointBase endPointBase : TableResolver.getDataAsObservableList( className ) ) {
				
				tableView.getItems().add( endPointBase );
				
			}
		} else {	
			
			try {
				for ( EndPointBase endPointBase : TableResolver.getMethodsAsList( className ) ) {
					
					tableView.getItems().add( endPointBase );
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		tableView.setPrefWidth( TABLE_WIDTH );
		tableView.setPrefHeight( tableView.getItems().size() * 25 + 25 );
		tab.setContent( tableView );
		setClassName( className );
		setStructureType( structureType );
		getTabs().add( tab );
			
		setOnDragDetected( new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
				Dragboard dragboard = startDragAndDrop( TransferMode.ANY );
				ClipboardContent clipboardContent = new ClipboardContent();
				clipboardContent.putString( "table_moved" );
				dragboard.setContent( clipboardContent );
			}
		} );
		
		tab.setOnClosed( new EventHandler<Event>() {
			
			@Override
			public void handle(Event event) {

				MappingStore.userStructures.remove( tab.getText() );
				
			}
		} );
		
	}
	public void setIdOnTab( String id ) {
		
		tab.setText( id );
		setId( id );
	}
	
	public TableView<EndPointBase> getTableView() {
		return tableView;
	}
	
	public String getClassName() {
		
		return className;
	}
	
	private void setClassName( String className ) {
		
		this.className = className;
	}
	public String getStructureType() {
		return structureType;
	}
	private void setStructureType(String structureType) {
		this.structureType = structureType;
	}
	
	
}