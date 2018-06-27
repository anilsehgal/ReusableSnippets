package com.psa.entities.gui.menus;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

import com.psa.entities.gui.utils.GUIUtils;

public class MappingMenu extends Menu {
	
	private static MappingMenu mappingMenu;
	
	private MappingMenu() {
		
		super( "Mapping" );
		
		MenuItem deleteItem = new MenuItem( "Delete" );
		MenuItem backupItem = new MenuItem( "Backup" );
		MenuItem restoreItem = new MenuItem( "Restore" );
		MenuItem saveItem = new MenuItem( "Save" );
		MenuItem execItem = new MenuItem( "Execute" );
		
		GUIUtils.getInstance().iconifyItem( deleteItem , "/images/cross_48.png" );
		GUIUtils.getInstance().iconifyItem( backupItem , "/images/box_download_48.png" );
		GUIUtils.getInstance().iconifyItem( restoreItem , "/images/box_upload_48.png" );
		GUIUtils.getInstance().iconifyItem( saveItem , "/images/floppy_disk_48.png" );
		GUIUtils.getInstance().iconifyItem( execItem , "/images/circle_green.png" );
		
		getItems().addAll( deleteItem , backupItem , restoreItem , saveItem , execItem );
	}
	
	public static MappingMenu getInstance() {
		
		if ( mappingMenu == null ) {
			
			mappingMenu = new MappingMenu();
		}
		
		return mappingMenu;
	}
}
