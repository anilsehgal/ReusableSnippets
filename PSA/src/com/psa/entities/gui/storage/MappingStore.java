package com.psa.entities.gui.storage;

import java.util.HashMap;
import java.util.Map;

import com.psa.entities.gui.controls.EndPointTableStructure;
import com.psa.entities.gui.controls.EntityLink;

public class MappingStore {
	
	public static Map< String , EndPointTableStructure > userStructures = new HashMap<String, EndPointTableStructure>();
	public static Map< String , EntityLink > userLinks = new HashMap< String , EntityLink >();
}
