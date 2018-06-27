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

public class PSAInformationDialog extends Stage {
	
	public static final String INFORMATION = "Information";
	public static final String WARNING = "Warning";
	public static final String ERROR = "Error";
	
	private static PSAInformationDialog psaInformationDialog;
	
	private PSAInformationDialog( String type , String message ) {
		
		super();
		setTitle( type );
		HBox hbox = new HBox();
	    hbox.setPadding(new Insets(15, 12, 15, 12));
	    hbox.setSpacing(10);
		initModality(Modality.APPLICATION_MODAL);
		Text text = new Text( message );
		Button button = new Button( "OK" );
		
		button.setOnAction( new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {

				close();
				event.consume();
			}
		} );

		hbox.getChildren().addAll( text , button );
		setScene( new Scene( hbox , 400 , 100 ) );
		centerOnScreen();
		setResizable( false );
		
	}
	
	public void openDialog() {
		
		showAndWait();
		
	}
	
	public static PSAInformationDialog getInstance( String type , String message ) {
		 
		if ( psaInformationDialog == null ) {
			
			psaInformationDialog = new PSAInformationDialog( type , message );
		}
		
		return psaInformationDialog;
	}
}
