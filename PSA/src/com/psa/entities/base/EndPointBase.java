package com.psa.entities.base;

public class EndPointBase {

	private String name;
	private String dataType;
	
	public EndPointBase(String name, String dataType) {
		
		super();
		this.name = name;
		this.dataType = dataType;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public static String getNameDescriptor() {
		
		return "name";
	}
	
	public static String getDataTypeDescriptor() {
		
		return "dataType";
	}
	
	
}
