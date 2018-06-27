package com.psa.entities.gui.menus;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;

import com.psa.entities.gui.utils.GUIUtils;

public class AppToolBar extends ToolBar {

	public static final int TOOL_HEIGHT = 40;
	private static AppToolBar appToolBar;
	private AppToolBar( Stage primaryStage ) {
		
		super();
		
		Button newButton = new Button();
		Button saveButton = new Button();
		Button closeButton = new Button();
		Button deleteButton = new Button();
		Button backupButton = new Button();
		Button restoreButton = new Button();
		Button execButton = new Button();
		
		GUIUtils.getInstance().iconifyNode( newButton , "/images/add_48.png" );
		GUIUtils.getInstance().iconifyNode( saveButton , "/images/floppy_disk_48.png" );
		GUIUtils.getInstance().iconifyNode( closeButton , "/images/cancel_48.png" );
		GUIUtils.getInstance().iconifyNode( deleteButton , "/images/cross_48.png" );
		GUIUtils.getInstance().iconifyNode( backupButton , "/images/box_download_48.png" );
		GUIUtils.getInstance().iconifyNode( restoreButton , "/images/box_upload_48.png" );
		GUIUtils.getInstance().iconifyNode( execButton , "/images/circle_green.png" );
		
		newButton.setOnAction( new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
			
				PSAMenuFunctionWrapper.getInstance().newMapping();
			}
		} );
		
		
		
		saveButton.setOnAction( new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
			
				PSAMenuFunctionWrapper.getInstance().saveMapping();
			}
		} );
		
		closeButton.setOnAction( new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
			
				PSAMenuFunctionWrapper.getInstance().closeMapping();
			}
		} );
		
		getItems().addAll( newButton , saveButton , closeButton , deleteButton , backupButton , restoreButton , execButton );
		prefWidthProperty().bind(primaryStage.widthProperty());
		setTranslateY( AppMenuBar.MENU_HEIGHT );
		setMinHeight( TOOL_HEIGHT );
		setMaxHeight( TOOL_HEIGHT );
	}
	
	public static AppToolBar getInstance( Stage primaryStage ) {
		
		if ( appToolBar == null ) {
			
			appToolBar = new AppToolBar( primaryStage );
		}
		
		return appToolBar;
	}
}
