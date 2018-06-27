package com.psa.application;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.psa.entities.gui.controls.BaseViewTree;
import com.psa.entities.gui.controls.MappingTree;
import com.psa.entities.gui.controls.PSADesigner;
import com.psa.entities.gui.controls.TransformationTree;
import com.psa.entities.gui.dialogs.PSAConfirmDialog;
import com.psa.entities.gui.menus.AppMenuBar;
import com.psa.entities.gui.menus.AppToolBar;
import com.psa.entities.gui.storage.MappingStore;
import com.psa.entities.gui.utils.GUIUtils;
import com.psa.entities.resolvers.TableResolver;
import com.sun.glass.ui.Screen;

public class MapperApplication extends Application {

	private int screenX , screenY;
    public static final int LEFT_TREE_WIDTH = 150;
    private static final int SCREEN_WIDTH_ALPHA = 10;
    private static final int SCREEN_HEIGHT_ALPHA = 40;

	@Override
	public void start(final Stage primaryStage) {
		
    	 screenX = Screen.getMainScreen().getVisibleWidth() - SCREEN_WIDTH_ALPHA;
    	 screenY = Screen.getMainScreen().getVisibleHeight() - SCREEN_HEIGHT_ALPHA;
		 primaryStage.setTitle( "PSA" );
		 primaryStage.getIcons().add( new Image( getClass().getResourceAsStream("/images/process_48.png") , 24 , 24 , true , true ) );
		 //$NON-NLS-1$
		 Group primaryGroup = new Group();         
		 Scene scene = new Scene(primaryGroup, screenX , screenY , Color.BLUEVIOLET);
		 
		 scene.setOnKeyPressed( new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {

				if( event.getCode().getName().equalsIgnoreCase( "Delete" ) ) {
			 
					PSADesigner.getInstance().getActiveCanvas().getChildren().remove( PSADesigner.getInstance().getFocussedNode() );
					MappingStore.userLinks.remove( PSADesigner.getInstance().getFocussedNode().getId() );
				}
			}
		} );
		 //buttonAccept.getStyleClass().add("button1");
	//	 primaryStage.setFullScreen(true);
		 primaryStage.setResizable(false);
		 
		 SplitPane mainSplitPane = new SplitPane();   
		 mainSplitPane.setPrefSize( scene.getWidth() , scene.getHeight() );       
		 mainSplitPane.setOrientation(Orientation.HORIZONTAL);
		 
		 SplitPane componentSplitPane = new SplitPane();
		 componentSplitPane.setOrientation(Orientation.VERTICAL);
		 componentSplitPane.setMinWidth( LEFT_TREE_WIDTH );       
		 componentSplitPane.setMaxWidth( LEFT_TREE_WIDTH );
		 
		 StackPane stackPaneMappingTree = initControlStack( MappingTree.getInstance() , LEFT_TREE_WIDTH );
		 StackPane stackPaneBaseView = initControlStack( BaseViewTree.getInstance() , null );
		 StackPane stackPaneTransformation = initControlStack( TransformationTree.getInstance() , null );
		 componentSplitPane.getItems().addAll( stackPaneBaseView , stackPaneTransformation );
		 
		 mainSplitPane.getItems().addAll( stackPaneMappingTree , PSADesigner.getInstance() , componentSplitPane );
		 primaryGroup.getChildren().addAll( AppMenuBar.getInstance( primaryStage ) , AppToolBar.getInstance( primaryStage ) , mainSplitPane );
		 mainSplitPane.setTranslateY( AppMenuBar.MENU_HEIGHT + AppToolBar.TOOL_HEIGHT );
		 
		 primaryStage.setOnCloseRequest( new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {

				boolean result = PSAConfirmDialog.getInstance( "Remove Mapping Backups? " ).openDialog();

				if ( result ) {
					
					TableResolver.removeMappingBackups();
				}
				
			}
		} );
		 
		 
		 
		 primaryStage.setScene(scene);        
		 primaryStage.show();
		 
		 
		 GUIUtils.getInstance().setViewMode();
	}
	
	private StackPane initControlStack( Control control , Integer width ) {
		
		StackPane stackPane = new StackPane();
		stackPane.getChildren().add( control );
		
		if( width != null ) {
			
			stackPane.setMinWidth( width );       
			stackPane.setMaxWidth( width );
		}
		control.setPrefSize( stackPane.getPrefWidth() , stackPane.getPrefHeight() );
		return stackPane;
	}

	public static void main(String[] args) {
		
		launch(args);
	}
}
