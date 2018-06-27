package com.psa.entities.gui.menus;

import com.psa.entities.gui.dialogs.PSAConfirmDialog;
import com.psa.entities.gui.utils.GUIUtils;
import com.psa.entities.resolvers.TableResolver;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

public class FileMenu extends Menu {
	
	private static FileMenu fileMenu;
	
	private FileMenu() {
		super( "File" );
		MenuItem newItem = new MenuItem( "New" );
		newItem.setAccelerator( KeyCombination.keyCombination("Ctrl+N") );
		newItem.setOnAction( new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				PSAMenuFunctionWrapper.getInstance().newMapping();
			}
		} );
		
		MenuItem saveItem = new MenuItem( "Save" );
		saveItem.setAccelerator( KeyCombination.keyCombination("Ctrl+S") );
		saveItem.setOnAction( new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {

				PSAMenuFunctionWrapper.getInstance().saveMapping();
			}
		} );
		
		MenuItem closeItem = new MenuItem( "Close" );
		closeItem.setAccelerator( KeyCombination.keyCombination("Ctrl+E") );
		closeItem.setOnAction( new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {

				PSAMenuFunctionWrapper.getInstance().closeMapping();
			}
		} );
		
		MenuItem exitItem = new MenuItem( "Exit" );
		closeItem.setAccelerator( KeyCombination.keyCombination("Ctrl+E") );
		closeItem.setOnAction( new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {

				boolean result = PSAConfirmDialog.getInstance( "Remove Mapping Backups? " ).openDialog();

				if ( result ) {
					
					TableResolver.removeMappingBackups();
				}
				
				System.exit( 0 );
			}
		} );
		
		GUIUtils.getInstance().iconifyItem( newItem , "/images/add_48.png" );
		GUIUtils.getInstance().iconifyItem( saveItem , "/images/floppy_disk_48.png" );
		GUIUtils.getInstance().iconifyItem( closeItem , "/images/cancel_48.png" );
		GUIUtils.getInstance().iconifyItem( exitItem , "/images/computer_48.png" );
		
		getItems().addAll( newItem , saveItem , closeItem , exitItem );
	}
	
	public static FileMenu getInstance () {
		
		if ( fileMenu == null ) {
			
			fileMenu = new FileMenu();
		}
		
		return fileMenu;
	}
	
}
