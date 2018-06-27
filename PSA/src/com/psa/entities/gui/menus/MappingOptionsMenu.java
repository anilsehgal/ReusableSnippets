package com.psa.entities.gui.menus;

import com.psa.entities.gui.utils.GUIUtils;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItemBuilder;
import javafx.scene.control.TreeItem;

public class MappingOptionsMenu extends ContextMenu {

	private static MappingOptionsMenu mappingOptionsMenu;
	
	private MappingOptionsMenu( TreeItem< String > treeItem ) {
		
		super();
		
		MenuItem openItem = new MenuItem( "Open" );
		MenuItem viewItem = new MenuItem( "Open ( View Only )" );
		MenuItem closeItem = new MenuItem( "Close" );
		MenuItem deleteItem = new MenuItem( "Delete" );
		MenuItem backupItem = new MenuItem( "Backup" );
		MenuItem restoreItem = new MenuItem( "Restore" );
		MenuItem renameItem = new MenuItem( "Rename" );
		MenuItem saveItem = new MenuItem( "Save" );
		MenuItem execItem = new MenuItem( "Execute" );
		
		GUIUtils.getInstance().iconifyItem( openItem , "/images/box_upload_48.png" );
		GUIUtils.getInstance().iconifyItem( viewItem , "/images/box_upload_48.png" );
		GUIUtils.getInstance().iconifyItem( closeItem , "/images/cancel_48.png" );
		GUIUtils.getInstance().iconifyItem( deleteItem , "/images/cross_48.png" );
		GUIUtils.getInstance().iconifyItem( backupItem , "/images/box_download_48.png" );
		GUIUtils.getInstance().iconifyItem( restoreItem , "/images/box_upload_48.png" );
		GUIUtils.getInstance().iconifyItem( renameItem , "/images/pencil_48.png" );
		GUIUtils.getInstance().iconifyItem( saveItem , "/images/floppy_disk_48.png" );
		GUIUtils.getInstance().iconifyItem( execItem , "/images/circle_green.png" );
		
		getItems().addAll( openItem , viewItem , closeItem , SeparatorMenuItemBuilder.create().build() , deleteItem , backupItem , restoreItem , SeparatorMenuItemBuilder.create().build() , renameItem , saveItem , execItem );
	}
	
	public static MappingOptionsMenu getInstance( TreeItem< String > treeItem ) {
		
		if( mappingOptionsMenu == null ) {
			
			mappingOptionsMenu = new MappingOptionsMenu( treeItem );
		}
		
		return mappingOptionsMenu;
	}
}
