package com.psa.entities.gui.menus;

import com.psa.entities.gui.controls.MappingTree;
import com.psa.entities.gui.dialogs.PSAPromptDialog;
import com.psa.entities.gui.utils.GUIUtils;
import com.psa.entities.serializable.Mapping;

public class PSAMenuFunctionWrapper {

	private static PSAMenuFunctionWrapper psaMenuFunctionWrapper;
	protected void saveMapping() {
		String mappingName = null;

		if( MappingTree.getInstance().getEditedMappingName() != null && ! (MappingTree.getInstance().getEditedMappingName().contains( "New_Mapping" ) ) ) {
			
			mappingName = MappingTree.getInstance().getEditedMappingName();
		} else {
			
			mappingName = PSAPromptDialog.getInstance( "Please provide a mapping name: " ).showDialog();
		}
		
		if ( mappingName != null ) {
			
			GUIUtils.getInstance().saveMappingParameters();
			Mapping.saveActiveMapping( mappingName );
			MappingTree.getInstance().updateActiveMappingName(mappingName);
		}
	}
	
	protected void newMapping() {
		if( ! MappingTree.getInstance().isMappingEdited() ) {
			
			GUIUtils.getInstance().initNewMapping();
		} else {
			
			System.out.println( "mapping not saved" );
		}
	}
	
	protected static PSAMenuFunctionWrapper getInstance() {
		
		if ( psaMenuFunctionWrapper == null ) {
			
			psaMenuFunctionWrapper = new PSAMenuFunctionWrapper();
		}
		
		return psaMenuFunctionWrapper;
	}

	protected void closeMapping() {
		
		GUIUtils.getInstance().clearMappingCanvas();
		GUIUtils.getInstance().setViewMode();
		Mapping.getInstance().clear();
		MappingTree.getInstance().removeStarFromName();
	}
}
