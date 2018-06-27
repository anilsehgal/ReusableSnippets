package com.psa.entities.gui.dnd;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.util.Callback;

import com.psa.application.MapperApplication;
import com.psa.entities.gui.controls.EntityLink;
import com.psa.entities.gui.menus.AppMenuBar;
import com.psa.entities.gui.menus.AppToolBar;
import com.psa.entities.gui.storage.MappingStore;
import com.psa.entities.gui.utils.GUIUtils;

public class DNDFactory {
	
	public static final String DRAG_SOURCE = "source";
	public static final String DROP_TARGET = "target";
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void makeDragSource ( final TableView tableView ) {
		
		tableView.setRowFactory( new Callback<TableView, TableRow>() {

			@Override
			public TableRow call(TableView param) {
				
				final TableRow tableRow = new TableRow(){

					@Override
					public void updateIndex(int index) {
						
						super.updateIndex( index );
					}
				};
								
				tableRow.setOnDragDetected( new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						
						Dragboard dragboard = tableView.startDragAndDrop( TransferMode.ANY );
						ClipboardContent clipboardContent = new ClipboardContent();
						clipboardContent.putString( Integer.toString( tableRow.getIndex() ) );
						dragboard.setContent( clipboardContent );
						tableView.setUserData( tableRow );
						event.consume();
					}
				} );
				
				
				
				return tableRow;
			}
		} );
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void makeDropTarget ( final TableView tableView ) {

		tableView.setRowFactory( new Callback<TableView, TableRow>() {

			@Override
			public TableRow call(TableView param) {
				
				final TableRow tableRow = new TableRow(){

					@Override
					public void updateIndex(int index) {
						
						super.updateIndex( index );
					}
				};
				
				tableRow.setOnDragOver( new EventHandler<DragEvent>() {

					@Override
					public void handle(DragEvent event) {

						event.acceptTransferModes( TransferMode.LINK );
						event.consume();
					}
				
				} );
				
				tableRow.setOnDragDropped( new EventHandler<DragEvent>() {

					@Override
					public void handle(DragEvent event) {

						Dragboard dragboard = event.getDragboard();

						TableRow sourceTableRow = ( TableRow ) ( ( TableView ) event.getGestureSource() ).getUserData();
						TableRow targetTableRow = tableRow;

						Point2D start = sourceTableRow.localToScene( sourceTableRow.getLayoutBounds().getMaxX() - MapperApplication.LEFT_TREE_WIDTH - 8 , sourceTableRow.getLayoutBounds().getMaxY() - AppMenuBar.MENU_HEIGHT - AppToolBar.TOOL_HEIGHT - ( sourceTableRow.getHeight() / 2 ) );
						Point2D end = targetTableRow.localToScene( targetTableRow.getLayoutBounds().getMinX() - MapperApplication.LEFT_TREE_WIDTH - 8 , targetTableRow.getLayoutBounds().getMinY() - AppMenuBar.MENU_HEIGHT - AppToolBar.TOOL_HEIGHT + ( targetTableRow.getHeight() / 2 ) );
						
						EntityLink entityLink = GUIUtils.getInstance().drawLine( start , end , ( ( TableView ) event.getGestureSource() ).getParent().getParent().getId() , tableView.getParent().getParent().getId() , Integer.parseInt( dragboard.getString() ) , tableRow.getIndex() );
						MappingStore.userLinks.put( entityLink.getId() , entityLink );
						event.consume();
					}
				
				} );
				
				return tableRow;
			}

		} );
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void makeDNDControl ( final TableView tableView ) {
		
		tableView.setRowFactory( new Callback<TableView, TableRow>() {

			@Override
			public TableRow call(TableView param) {
				
				final TableRow tableRow = new TableRow(){

					@Override
					public void updateIndex(int index) {
						
						super.updateIndex( index );
					}
				};
								
				tableRow.setOnDragDetected( new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						
						Dragboard dragboard = tableView.startDragAndDrop( TransferMode.ANY );
						ClipboardContent clipboardContent = new ClipboardContent();
						clipboardContent.putString( Integer.toString( tableRow.getIndex() ) );
						dragboard.setContent( clipboardContent );
						tableView.setUserData( tableRow );
						event.consume();
					}
				} );
				
				tableRow.setOnDragOver( new EventHandler<DragEvent>() {

					@Override
					public void handle(DragEvent event) {

						event.acceptTransferModes( TransferMode.LINK );
						event.consume();
					}
				
				} );
				
				tableRow.setOnDragDropped( new EventHandler<DragEvent>() {

					@Override
					public void handle(DragEvent event) {

						Dragboard dragboard = event.getDragboard();

						TableRow sourceTableRow = ( TableRow ) ( ( TableView ) event.getGestureSource() ).getUserData();
						TableRow targetTableRow = tableRow;

						Point2D start = sourceTableRow.localToScene( sourceTableRow.getLayoutBounds().getMaxX() - MapperApplication.LEFT_TREE_WIDTH - 8 , sourceTableRow.getLayoutBounds().getMaxY() - AppMenuBar.MENU_HEIGHT - AppToolBar.TOOL_HEIGHT - ( sourceTableRow.getHeight() / 2 ) );
						Point2D end = targetTableRow.localToScene( targetTableRow.getLayoutBounds().getMinX() - MapperApplication.LEFT_TREE_WIDTH - 8 , targetTableRow.getLayoutBounds().getMinY() - AppMenuBar.MENU_HEIGHT - AppToolBar.TOOL_HEIGHT + ( targetTableRow.getHeight() / 2 ) );
						
						EntityLink entityLink = GUIUtils.getInstance().drawLine( start , end , ( ( TableView ) event.getGestureSource() ).getParent().getParent().getId() , tableView.getParent().getParent().getId() , Integer.parseInt( dragboard.getString() ) , tableRow.getIndex() );
						MappingStore.userLinks.put( entityLink.getId() , entityLink );
						event.consume();
					}
				
				} );
				
				return tableRow;
			}
		} );
	}
}
