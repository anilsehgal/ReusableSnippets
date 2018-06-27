package com.psa.entities.gui.controls;

import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import com.psa.entities.resolvers.TableResolver;

public class UtilityStack extends TabPane {

	private static final String PACKAGE_NAME = "Provide Package";
	private TextField searchField;
	private ListView< String > resultList;
	
	private UtilityStack() {
		
		searchField = new TextField();
		searchField.setText( PACKAGE_NAME );
		resultList = new ListView< String >();

		Tab searchTab = new Tab( "Utilities" );
		
		searchField.setOnMousePressed( new EventHandler< MouseEvent >() {
			
			@Override
			public void handle( MouseEvent mouseEvent ) {
				
				if ( PACKAGE_NAME.equalsIgnoreCase( searchField.getText() ) ) {
					
					searchField.setText( "" );
				}
			}
		} );
		
		searchField.setOnKeyPressed(  new EventHandler<KeyEvent>() {

			@Override
			public void handle( KeyEvent keyEvent ) {
				
				if( keyEvent.getCode().equals( KeyCode.ENTER ) ) {
					
					System.out.println( "Here" );
					List<Class<?>> classes = null;
					String searchKey = searchField.getText();
					try {
						
						Package package1 = Package.getPackage( searchKey );
						classes = TableResolver.getClassesForPackage( package1 );
						System.out.println( "classes: " + classes.size() );
					} catch (Exception e) {
						
						System.out.println( "Exception" );
						e.printStackTrace();
					}
					
					resultList.getItems().remove( 0, resultList.getItems().size() );
					for ( Class clazz : classes ) {
						
						System.out.println( clazz.getName() );
						resultList.getItems().add( clazz.getName() );
					}
				}
			}
		} );

		VBox vBox = new VBox();
		vBox.getChildren().addAll( searchField, resultList );
		searchTab.setContent( vBox );
		getTabs().add( searchTab );
	}
	
	
	
	private static UtilityStack utilityStack = new UtilityStack();
	
	public static UtilityStack getInstance() {
		
		return utilityStack;
	}
}
