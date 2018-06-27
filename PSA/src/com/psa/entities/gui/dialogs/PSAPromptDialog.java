package com.psa.entities.gui.dialogs;

import java.util.UUID;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PSAPromptDialog extends Stage {

	private static PSAPromptDialog psaConfirmDialog;
	TextField textField;
	private String value;
	private PSAPromptDialog( String message ) {
		
		super();
		setTitle( "Prompt" );
		HBox hbox = new HBox();
	    hbox.setPadding(new Insets(15, 12, 15, 12));
	    hbox.setSpacing(10);
		initModality(Modality.WINDOW_MODAL);
		Text text = new Text( message );
		textField = new TextField();
		Button button = new Button( "OK" );
		Button button2 = new Button( "Cancel" );
		
		button.setOnAction( new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				value = textField.getText().trim().length() == 0 ? UUID.randomUUID().toString().substring( 0 , 8 ) : textField.getText();
				close();
				event.consume();
			}
		} );
		
		button2.setOnAction( new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				value = null;
				close();
				event.consume();
			}
		} );
		
		hbox.getChildren().addAll( text , textField , button , button2 );
		setScene( new Scene( hbox , 500 , 100 ) );
		centerOnScreen();
		setResizable( false );
		
	}
	
	public static PSAPromptDialog getInstance( String message ) {
		
		if( psaConfirmDialog == null ) {
			
			psaConfirmDialog = new PSAPromptDialog( message );
		}
		
		return psaConfirmDialog;
	}
	
	public String showDialog() {
		
		showAndWait();
		return value;
	}
}
