package com.psa.entities.gui.controls;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import com.psa.entities.gui.menus.MappingOptionsMenu;
import com.psa.entities.resolvers.TableResolver;
import com.psa.entities.serializable.Mapping;

public class MappingTree extends TabPane {
	
	private  static MappingTree mappingTree;
	private static TreeView< String > treeView;
	private TreeItem< String > mappingReference;
	private boolean isMappingEdited;
	private final Node rootIcon = 
            new ImageView(new Image( getClass().getResourceAsStream("/images/conv_48.png") , 24 , 24 , true , true ));
	
	private MappingTree () {
		
		setSide( Side.TOP );
	    Tab tab1 = new Tab();

	    tab1.setText( "Mappings" ); //$NON-NLS-1$
	    tab1.setGraphic( rootIcon );
	    
	    TreeItem<String> topItem = new TreeItem<String>( "Mappings" , rootIcon ); //$NON-NLS-1$
	    treeView = new TreeView< String >( topItem );
	    topItem.setExpanded( true );
	    
	    for ( String mappingName : TableResolver.getMappings() ) {
	    	
	    	topItem.getChildren().add( new TreeItem<String>( mappingName ) );
	    }
	    
	    topItem.setExpanded( true );
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
                
                treeCell.setOnMouseClicked( new EventHandler<MouseEvent>() {

					@Override
					public void handle( MouseEvent event ) {
						
						if( event.getClickCount() == 2 ) {
						
							if( ! MappingTree.getInstance().isMappingEdited() ) {
									
								Mapping.loadAndActivateMapping( treeCell.getTreeItem().getValue() );
								MappingTree.getInstance().setMappingEdited( treeCell.getTreeItem() );
							} else {
								
								System.out.println( "Mapping not saved" );
							}
						}
					}
                } );
                
                treeCell.setContextMenu( MappingOptionsMenu.getInstance( treeCell.getTreeItem() ) );
                
                return treeCell;
            }
        });
	    
	    
	    tab1.setContent(treeView);
	    tab1.setClosable(true);
	    
	    tab1.setOnClosed( new EventHandler<Event>() {
			
			@Override
			public void handle(Event arg0) {
				
				mappingTree.setVisible( false );
			}
		} );
	    
	    getTabs().add(tab1);
	}
	
	public static MappingTree getInstance() {
		
		if ( mappingTree == null ) {
			
			mappingTree = new MappingTree();
		}
		
		return mappingTree;
	}
	
	public TreeView< String > getTree() {
		
		return treeView;
	}
	
	public void setMappingClosed() {
		
		mappingReference = null;
	}
	
	public void addNewMapping() {
		
		isMappingEdited = true;
		mappingReference = new TreeItem<String>( "New_Mapping" );
		treeView.getRoot().getChildren().add( mappingReference );
		treeView.getRoot().setExpanded( true );
	}
	
	public void updateActiveMappingName( String mappingName ) {
		
		if( mappingReference != null ) {
			
			mappingReference.setValue( mappingName );
			isMappingEdited = false;
		}
	}
	
	public void removeStarFromName() {
		
		if( mappingReference != null ) {
			
			mappingReference.setValue( mappingReference.getValue().substring( 0 , mappingReference.getValue().length() - 1 ) );
			isMappingEdited = false;
		}
	}
	
	public String getEditedMappingName() {
		
		return mappingReference.getValue().substring( 0 , mappingReference.getValue().length() - 1 );
	}
	
	public void setMappingEdited( TreeItem< String > treeItem ) {
		
		isMappingEdited = true;
		String value = null;
		if( treeItem != null ) 
			mappingReference = treeItem;
		
		System.out.println( "mappingReference = " + mappingReference );
		
		if ( mappingReference!= null && ! mappingReference.getValue().contains( "*" ) ) {
			
			value = mappingReference.getValue() + "*"; 
		}
		
		mappingReference.setValue( value );
	}
	
	public boolean isMappingEdited() {
		
		return isMappingEdited;
	}
}
