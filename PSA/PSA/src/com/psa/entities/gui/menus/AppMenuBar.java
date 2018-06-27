package com.psa.entities.gui.menus;

import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

public class AppMenuBar extends MenuBar {
	
	private static AppMenuBar appMenuBar;
	public static final int MENU_HEIGHT = 25;
	private AppMenuBar ( Stage primaryStage ) {
		
		super();
		
		getMenus().addAll( FileMenu.getInstance() , MappingMenu.getInstance() );
		prefWidthProperty().bind(primaryStage.widthProperty());
		setMinHeight( MENU_HEIGHT );
		setMaxHeight( MENU_HEIGHT );
	}
	
	public static AppMenuBar getInstance( Stage primaryStage ) {
		
		if ( appMenuBar == null ) {
			
			appMenuBar = new AppMenuBar( primaryStage );
		}
		
		return appMenuBar;
	}
}
