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

public class BaseViewTree extends TabPane implements PSAConstants {
	
	private static BaseViewTree baseViewTree;
	
	@SuppressWarnings("rawtypes")
	private BaseViewTree () {
		
	    
	    setSide( Side.TOP );
	    Tab tab1 = new Tab();

	    tab1.setText( "End-Points" );
	    
	    TreeItem<String> topItem = new TreeItem<String>( "End-Points" ); //$NON-NLS-1$
	    topItem.setExpanded( true );
	    final TreeView< String > treeView = new TreeView< String >();
	    treeView.setRoot(topItem);
	    	    
	    TreeItem<String> srcViewItem = new TreeItem < String > ( "Sources" ); //$NON-NLS-1$ 
	    TreeItem<String> tgtViewItem = new TreeItem < String > ( "Targets" ); //$NON-NLS-1$
	    topItem.getChildren().add( srcViewItem );
	    
	    try {
			
	    	List<Class> classList = TableResolver.getClassesImplementingInterface( SOURCE_INTERFACE , "com.tables" );
	    	for ( Class clazz : classList ) {
	    		
	    		TreeItem<String> srcItem = new TreeItem < String > ( clazz.getSimpleName() );
	    		srcViewItem.getChildren().add( srcItem );
	    	}
	    
	    } catch (Exception e) {

			e.printStackTrace();
		}
	    
	    topItem.getChildren().add( tgtViewItem );
	    
	    try {
			
	    	List<Class> classList = TableResolver.getClassesImplementingInterface( TARGET_INTERFACE , "com.tables" );
	    	for ( Class clazz : classList ) {
	    		
	    		TreeItem<String> tgtItem = new TreeItem < String > ( clazz.getSimpleName() );
	    		tgtViewItem.getChildren().add( tgtItem );
	    	}
	    
	    } catch (Exception e) {

			e.printStackTrace();
		}
	    
	    treeView.setCellFactory(new Callback<TreeView<String> , TreeCell<String>>() {
            @Override
            public TreeCell<String> call(TreeView<String> stringTreeView) {
                
            	final TreeCell<String> treeCell = new TreeCell<String>() {
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item);
                        }
                    }
                };
 
                treeCell.setOnDragDetected(new EventHandler<MouseEvent>() {
        		    public void handle(MouseEvent event) {

        		        Dragboard db = treeCell.startDragAndDrop( TransferMode.ANY );
        		        ClipboardContent content = new ClipboardContent();
        		        content.putString ( treeCell.getTreeItem().getValue() );
        		        db.setContent( content );

        		        event.consume();
        		    }
        		});
                
                return treeCell;
            }
        });
	    
	    
	    tab1.setContent(treeView);
	    tab1.setClosable(true);
	    getTabs().add(tab1);
	    
	    tab1.setOnClosed( new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				
				baseViewTree.setVisible( false );
			}
		} );

	}
	
	public static BaseViewTree getInstance() {
		
		if ( baseViewTree == null ) {
			
			baseViewTree = new BaseViewTree();
		}
		
		return baseViewTree;
	}
}
