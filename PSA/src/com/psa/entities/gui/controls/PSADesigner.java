package com.psa.entities.gui.controls;

import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;

import com.psa.constants.PSAConstants;
import com.psa.entities.gui.dnd.DNDFactory;
import com.psa.entities.gui.storage.MappingStore;
import com.psa.entities.gui.utils.GUIUtils;
import com.psa.entities.resolvers.TableResolver;

public class PSADesigner extends ScrollPane implements PSAConstants {

	private Node focussedNode;
	private static PSADesigner psaDesigner;
	private Pane canvas;

	
	private PSADesigner() {
		
		super();
		canvas = new Pane();
		
		canvas.setOnDragOver( new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				
				if ( ! ( event.getGestureSource() instanceof TableView ) ) {
				
					event.acceptTransferModes( TransferMode.COPY_OR_MOVE );
				}
				if ( event.getGestureSource() instanceof EndPointTableStructure ) {
					
					( ( EndPointTableStructure ) event.getGestureSource() ).setTranslateX( event.getX() );
					( ( EndPointTableStructure ) event.getGestureSource() ).setTranslateY( event.getY() );
					GUIUtils.reCalculateLinesForEndPoints( ( EndPointTableStructure ) event.getGestureSource() );
				}
				event.consume();
			}
		
		
		} );
		
		canvas.setOnDragDropped( new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				
				if ( ! MappingTree.getInstance().isMappingEdited() ) 
					MappingTree.getInstance().setMappingEdited( null );
				
				Dragboard dragboard = event.getDragboard();
				
				if ( dragboard.getString().equals( "Sources" ) || dragboard.getString().equals( "Targets" ) ) {
					
					event.consume();
				}
				
				if ( ! dragboard.getString().equals( "table_moved" ) ) {
				
					try {	
						
						String className = "com.tables" + DOT + dragboard.getString();
						List< String > interfaces = TableResolver.getInterfaces( className );
						String structureId = null;
						EndPointTableStructure endPointTableStructure = null;
						if ( interfaces.contains( SOURCE_INTERFACE ) ) {
							
							structureId = SOURCE + UNDERSCORE + dragboard.getString();
							endPointTableStructure = new EndPointTableStructure( className , structureId , SOURCE );
							DNDFactory.makeDragSource( endPointTableStructure.getTableView() );
							
						} else if ( interfaces.contains( TARGET_INTERFACE ) ) {
							
							structureId = TARGET + UNDERSCORE + dragboard.getString();
							endPointTableStructure = new EndPointTableStructure( className , structureId , TARGET );
							DNDFactory.makeDropTarget( endPointTableStructure.getTableView() );
							
						} else if ( interfaces.contains( CONVERTER_INTERFACE ) ) {
							
							structureId = CONVERTER + UNDERSCORE + dragboard.getString();
							endPointTableStructure = new EndPointTableStructure( className , structureId , CONVERTER );
							DNDFactory.makeDNDControl( endPointTableStructure.getTableView() );
						}
						
						if ( endPointTableStructure != null ) {
							
							canvas.getChildren().add( endPointTableStructure );
							endPointTableStructure.setIdOnTab( structureId );
							endPointTableStructure.setTranslateX( event.getX() );
							endPointTableStructure.setTranslateY( event.getY() );
							MappingStore.userStructures.put( endPointTableStructure.getId() , endPointTableStructure );
						}
						
					} catch (Exception e) {
						
						e.printStackTrace();
					}
				} else {
					
					EndPointTableStructure endPointTableStructure = ( EndPointTableStructure ) event.getGestureSource();
					endPointTableStructure.setTranslateX( event.getX() );
					endPointTableStructure.setTranslateY( event.getY() );
					GUIUtils.reCalculateLinesForEndPoints( ( EndPointTableStructure ) event.getGestureSource() );
				}
				
				event.setDropCompleted( true );
				event.consume();
			}
		
		
		} );
		
		canvas.setMinSize( 4000 , 4000 );
		setContent( canvas );
	}
	
	public Pane getActiveCanvas() {
		
		return canvas;
	}

	public static PSADesigner getInstance() {
		
		if ( psaDesigner == null ) {
			
			psaDesigner = new PSADesigner();
		}
		
		return psaDesigner;
	}

	public Node getFocussedNode() {
		return focussedNode;
	}

	public void setFocussedNode(Node focussedNode) {
		this.focussedNode = focussedNode;
	}
	
	
}
