package com.psa.entities.gui.utils;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

import com.psa.application.MapperApplication;
import com.psa.constants.PSAConstants;
import com.psa.entities.gui.controls.BaseViewTree;
import com.psa.entities.gui.controls.EndPointTableStructure;
import com.psa.entities.gui.controls.EntityLink;
import com.psa.entities.gui.controls.MappingTree;
import com.psa.entities.gui.controls.PSADesigner;
import com.psa.entities.gui.controls.TransformationTree;
import com.psa.entities.gui.dnd.DNDFactory;
import com.psa.entities.gui.menus.AppMenuBar;
import com.psa.entities.gui.menus.AppToolBar;
import com.psa.entities.gui.storage.MappingStore;
import com.psa.entities.serializable.EndPointBase;
import com.psa.entities.serializable.Mapping;
import com.psa.entities.serializable.PSALink;
import com.psa.entities.serializable.PSAStructure;

public class GUIUtils implements PSAConstants {

	private static GUIUtils guiUtils;
	
	public void iconifyNode( Labeled object , String imagePath ) {

		Image image  = new Image( getClass().getResourceAsStream( imagePath ) , 24 , 24 , true , true );
		object.setGraphic( new ImageView( image ) );
	}
	
	public void iconifyTreeItem( TreeItem< String > object , String imagePath ) {

		Image image  = new Image( getClass().getResourceAsStream( imagePath ) , 16 , 16 , true , true );
		object.setGraphic( new ImageView( image ) );
		
	}
	
	public void setViewMode() {
		
		BaseViewTree.getInstance().setDisable( true );
		PSADesigner.getInstance().setDisable( true );
		TransformationTree.getInstance().setDisable( true );
	}
	
	public void setEditMode() {

		BaseViewTree.getInstance().setDisable( false );
		PSADesigner.getInstance().setDisable( false );
		TransformationTree.getInstance().setDisable( false );
	}
	
	public void initNewMapping() {
		
		clearMappingCanvas();
		Mapping.initInstance();
		MappingTree.getInstance().addNewMapping();
		MappingTree.getInstance().setMappingEdited( null );
		setEditMode();
	}
	
	public EntityLink drawLine( Point2D start , Point2D end , String sourceId , String targetId , int sourceRowIndex , int targetRowIndex ) {
		
		EntityLink entityLink = new EntityLink( sourceId , targetId , sourceRowIndex , targetRowIndex , start , end );
		entityLink.getPoints().addAll( start.getX() , start.getY() , start.getX() + 30 , start.getY() , end.getX() - 30 , end.getY() , end.getX() , end.getY() );
		entityLink.setStroke( Color.GREEN );
		entityLink.setStrokeWidth( 2 );
		entityLink.setStrokeLineCap( StrokeLineCap.ROUND );
		entityLink.setSmooth( true );
		entityLink.setStrokeLineJoin( StrokeLineJoin.ROUND );
		PSADesigner.getInstance().getActiveCanvas().getChildren().add( entityLink );
		entityLink.setId( sourceId + UNDERSCORE + targetId + UNDERSCORE + sourceRowIndex + UNDERSCORE + targetRowIndex );
		MappingStore.userLinks.put( entityLink.getId() , entityLink );
		
		return entityLink;
	}
	
	public static GUIUtils getInstance() {
		
		if ( guiUtils == null ) {
			
			guiUtils = new GUIUtils();
		}
		
		return guiUtils;
	}

	public static void reCalculateLinesForEndPoints ( EndPointTableStructure endPointTableStructure ) {
		
		Point2D start = null , end = null;
		
		for( String linkId : MappingStore.userLinks.keySet() ) {
			EntityLink entityLink = MappingStore.userLinks.get( linkId );
			if ( entityLink.getSourceStructureId().equals( endPointTableStructure.getId() ) || entityLink.getTargetStructureId().equals( endPointTableStructure.getId() ) ) {
				
				TableView<EndPointBase> sourceTableView = MappingStore.userStructures.get( entityLink.getSourceStructureId() ).getTableView();
				TableView<EndPointBase> targetTableView = MappingStore.userStructures.get( entityLink.getTargetStructureId() ).getTableView();
		
				 for (Node rowNode : sourceTableView.lookupAll( INDEXED_CELL_STYLE_CLASS ) ) {

					 if( rowNode instanceof TableRow ) {
						 
						 TableRow tableRow = (TableRow) rowNode;
						 if( tableRow.getIndex() == entityLink.getSourceRowIndex() ) {
							 
							 start = tableRow.localToScene( tableRow.getLayoutBounds().getMaxX() - MapperApplication.LEFT_TREE_WIDTH - 8 , tableRow.getLayoutBounds().getMaxY() - AppMenuBar.MENU_HEIGHT - AppToolBar.TOOL_HEIGHT - ( tableRow.getHeight() / 2 ) );
							 break;
						 }
					 }
				 }
				 
				 for (Node rowNode : targetTableView.lookupAll( INDEXED_CELL_STYLE_CLASS )) {

					 if( rowNode instanceof TableRow ) {
						 
						 TableRow tableRow = (TableRow) rowNode;
						 if( tableRow.getIndex() == entityLink.getTargetRowIndex() ) {
							 
							 end = tableRow.localToScene( tableRow.getLayoutBounds().getMinX() - MapperApplication.LEFT_TREE_WIDTH - 8 , tableRow.getLayoutBounds().getMinY() - AppMenuBar.MENU_HEIGHT - AppToolBar.TOOL_HEIGHT + ( tableRow.getHeight() / 2 ) );
							 break;
						 }
					 }
				 }
				 
				 entityLink.getPoints().clear();
				 entityLink.getPoints().addAll( start.getX() , start.getY() , start.getX() + 30 , start.getY() , end.getX() - 30 , end.getY() , end.getX() , end.getY() );
				 entityLink.setStart( start );
				 entityLink.setEnd( end );
			}
		}
	}
	
	public void clearMappingCanvas() {
		
		ObservableList<Node> canvasChildren = PSADesigner.getInstance().getActiveCanvas().getChildren();
		PSADesigner.getInstance().getActiveCanvas().getChildren().removeAll( canvasChildren );
		MappingStore.userStructures.clear();
		MappingStore.userLinks.clear();
	}
	
	public void renderMapping() {
		
		clearMappingCanvas();
		Map< String , PSAStructure > structures = Mapping.getInstance().getStructureMaps();
		Map< String , PSALink > links = Mapping.getInstance().getLinkMaps();
		
		EndPointTableStructure endPointTableStructure = null;
		for ( String structureId : structures.keySet() ) {
			
			PSAStructure structure = structures.get( structureId );
			if ( structure.getStructureType().equals( SOURCE ) ) {
				
				try {
					
					endPointTableStructure = new EndPointTableStructure( structure.getClassName() , structureId , SOURCE );
					DNDFactory.makeDragSource( endPointTableStructure.getTableView() );
					
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			} else if ( structure.getStructureType().equals( TARGET ) ) {
				
				try {
					
					endPointTableStructure = new EndPointTableStructure( structure.getClassName() , structureId , TARGET );
					DNDFactory.makeDropTarget( endPointTableStructure.getTableView() );
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				
			} else if ( structure.getStructureType().equals( CONVERTER ) ) {
				
				try {
					
					endPointTableStructure = new EndPointTableStructure( structure.getClassName() , structureId , CONVERTER );
					DNDFactory.makeDNDControl( endPointTableStructure.getTableView() );
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				
			}
			
			if ( endPointTableStructure != null ) {
			
				PSADesigner.getInstance().getActiveCanvas().getChildren().add( endPointTableStructure );
				endPointTableStructure.setIdOnTab( structureId );
				endPointTableStructure.setTranslateX( structure.getPositionX() );
				endPointTableStructure.setTranslateY( structure.getPositionY() );
				MappingStore.userStructures.put( endPointTableStructure.getId() , endPointTableStructure );
			}
		}
		
		for( String linkId : links.keySet() ) {
			
			PSALink link = links.get( linkId );
			double[] points = link.getPoints();
			drawLine( new Point2D( points[0] , points[1] ) , new Point2D( points[2] , points[3] ) , link.getSourceTableId() , link.getTargetTableId() , link.getSourceTableRowIndex() , link.getTargetTableRowIndex() );
		}
		
	}
	
	public void saveMappingParameters() {

		for ( String structureId : MappingStore.userStructures.keySet() ) {
			
			EndPointTableStructure endPointTableStructure = MappingStore.userStructures.get( structureId );
			
			Map< Integer , EndPointBase > transformerMap = new HashMap< Integer , EndPointBase >();
			for( EndPointBase endPointBase : endPointTableStructure.getTableView().getItems() ) {
				
				transformerMap.put( endPointTableStructure.getTableView().getItems().indexOf( endPointBase ) , endPointBase );
			}
			
			PSAStructure psaStructure = new PSAStructure( endPointTableStructure.getClassName() , endPointTableStructure.getTranslateX() , endPointTableStructure.getTranslateY() , endPointTableStructure.getStructureType() , transformerMap);
			Mapping.getInstance().getStructureMaps().put( structureId , psaStructure );
		}

		for ( String lineId : MappingStore.userLinks.keySet() ) {
			
			EntityLink entityLink = MappingStore.userLinks.get( lineId );
			double[] points = { entityLink.getStart().getX() , entityLink.getStart().getY() , entityLink.getEnd().getX() , entityLink.getEnd().getY() };
			PSALink psaLink = new PSALink( entityLink.getSourceStructureId() , entityLink.getTargetStructureId() , entityLink.getSourceRowIndex() , entityLink.getTargetRowIndex() , points);
			Mapping.getInstance().getLinkMaps().put( lineId , psaLink );
		}
	}

	public void iconifyItem(MenuItem item, String path) {
		
		Image image  = new Image( getClass().getResourceAsStream( path ) , 16 , 16 , true , true );
		item.setGraphic( new ImageView( image ) );
	}
}
