package com.psa.entities.gui.dialogs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PSAConfirmDialog extends Stage {

	private static PSAConfirmDialog psaConfirmDialog;
	private boolean value;
	
	private PSAConfirmDialog( String message ) {
		
		super();
		setTitle( "Confirm" );
		HBox hbox = new HBox();
	    hbox.setPadding(new Insets(15, 12, 15, 12));
	    hbox.setSpacing(10);
		initModality(Modality.APPLICATION_MODAL);
		Text text = new Text( message );
		Button button = new Button( "Yes" );
		Button button2 = new Button( "No" );
		
		button.setOnAction( new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				value = true;
				close();
				event.consume();
			}
		} );
		
		button2.setOnAction( new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {

				value = false;
				close();
				event.consume();
			}
		} );
		
		hbox.getChildren().addAll( text , button , button2 );
		setScene( new Scene( hbox , 400 , 100 ) );
		centerOnScreen();
		setResizable( false );
		
	}
	
	public boolean openDialog() {
		
		showAndWait();

			
		return value;	
		
	}
	
	public static PSAConfirmDialog getInstance( String message ) {
		
		if ( psaConfirmDialog == null ) {
			
			psaConfirmDialog = new PSAConfirmDialog( message );
		}
		
		return psaConfirmDialog;
	}
}
