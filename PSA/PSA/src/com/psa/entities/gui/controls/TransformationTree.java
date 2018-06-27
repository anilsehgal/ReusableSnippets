package com.psa.entities.gui.controls;

import java.util.List;

import com.psa.constants.PSAConstants;
import com.psa.entities.resolvers.TableResolver;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.util.Callback;

public class TransformationTree extends TabPane implements PSAConstants {
	
	private static TransformationTree transformationTree;
	
	private TransformationTree() {
		
		setSide( Side.TOP );
	    Tab tab1 = new Tab();
	    
	    tab1.setText( "Transformations" ); //$NON-NLS-1$
	    
	    TreeItem<String> topItem = new TreeItem<String>( "Transformations" ); //$NON-NLS-1$
	    topItem.setExpanded( true );
	    final TreeView< String > treeView = new TreeView< String >();
	    treeView.setRoot(topItem);
	    try {
			
	    	List<Class> classList = TableResolver.getClassesImplementingInterface( CONVERTER_INTERFACE , "com.tables" );
	    	for ( Class clazz : classList ) {

	    		TreeItem<String> tgtItem = new TreeItem < String > ( clazz.getSimpleName() );
	    		topItem.getChildren().add( tgtItem );
	    	}
	    
	    } catch (Exception e) {

			e.printStackTrace();
		}
	   
	    treeView.setCellFactory(new Callback<TreeView< String > , TreeCell< String > >() {
            @Override
            public TreeCell< String > call( TreeView< String > stringTreeView ) {
                
            	final TreeCell< String > treeCell = new TreeCell< String >() {
                    
                	protected void updateItem( String item, boolean empty ) {
                        
                    	super.updateItem( item, empty );
                        
                        if ( item != null ) {
                            
                        	setText( item );
                        }
                    }
                };
 
                treeCell.setOnDragDetected(new EventHandler< MouseEvent >() {
        		    public void handle(MouseEvent event) {

        		        Dragboard db = treeView.startDragAndDrop( TransferMode.ANY );
        		        ClipboardContent content = new ClipboardContent();
        		        content.putString( treeCell.getTreeItem().getValue() );
        		        db.setContent( content );
        		        
        		        event.consume();
        		    }
        		});	
                
                return treeCell;
            }
        });
	    
	    
	    tab1.setContent( treeView );
	    tab1.setClosable( true );
	    
	    tab1.setOnClosed( new EventHandler< Event >() {
			
			@Override
			public void handle( Event arg0 ) {
				
				transformationTree.setVisible( false );
			}
		} );
	    
	    getTabs().add( tab1 );
	}
	
	public static TransformationTree getInstance() {
		
		if ( transformationTree == null ) {
			
			transformationTree = new TransformationTree();
		}
		
		return transformationTree;
	}
}
