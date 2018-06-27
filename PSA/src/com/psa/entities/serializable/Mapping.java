package com.psa.entities.serializable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.psa.constants.PSAConstants;
import com.psa.entities.gui.utils.GUIUtils;

public class Mapping implements Serializable, PSAConstants {

	private static final long serialVersionUID = 6631435181659112379L;
	private static Mapping mapping;
	private String mappingName;
	private Map< String , PSALink > linkMaps = new HashMap< String , PSALink >();;
	private Map< String , PSAStructure > structureMaps = new HashMap< String , PSAStructure >();
	
	public void clear() {
		
		linkMaps.clear();
		structureMaps.clear();
		mappingName = null;
	}
	
	public String getMappingName() {
		return mappingName;
	}
	public void setMappingName(String mappingName) {
		this.mappingName = mappingName;
	}
	public Map<String, PSALink> getLinkMaps() {
		return linkMaps;
	}
	public Map<String, PSAStructure> getStructureMaps() {
		return structureMaps;
	}

	public static void initInstance() {

		mapping = new Mapping();
	}
	public static void destroy() {
		
		mapping = null;
	}
	public static Mapping getInstance() {
		
		return mapping;
	}
	
	public static void saveActiveMapping( String mappingName ) {
		
		File file = new File( "D:\\mappings" );
		if( ! file.exists() ) {
			
			file.mkdir();
		}
		
		File f = new File( "D:\\mappings\\" +mappingName + EXT_MAPPING );
		
		if( f.exists() ) {

			File f2 = new File( "D:\\mappings\\" +mappingName + EXT_MAPPING + UNDERSCORE + BAK );
			f.renameTo( f2 );
			try {
				
				f.createNewFile();
			} catch (IOException e1) {

				e1.printStackTrace();
				return;
			}
		}

		try {
			
			mapping.setMappingName( mappingName );
			ObjectOutput ObjOut = new ObjectOutputStream(new FileOutputStream(f));
			ObjOut.writeObject( mapping );
			ObjOut.close();
		 }
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void loadAndActivateMapping( String mappingName ) {
		
		File f = new File( "D:\\mappings\\" + mappingName + EXT_MAPPING );
		try {
	 
			ObjectInputStream obj = new ObjectInputStream(new FileInputStream(f));
			mapping = ( Mapping ) obj.readObject();
			obj.close();
			if ( mapping != null ) {
				
				GUIUtils.getInstance().setEditMode();
				GUIUtils.getInstance().renderMapping();
			}
			
		}catch(ClassNotFoundException e){
		
			e.printStackTrace();
		} catch(FileNotFoundException fe){
  
			fe.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
