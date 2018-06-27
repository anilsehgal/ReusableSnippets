package com.psa.entities.serializable;

import java.io.Serializable;
import java.util.Map;


public class PSAStructure implements Serializable {

	private static final long serialVersionUID = 8629084946844852064L;
	
	private String className;
	private double positionX;
	private double positionY;
	private String structureType;
	private Map< Integer , EndPointBase > transformerMap;
	
	
	
	public PSAStructure( String className, double positionX,
			double positionY, String structureType,
			Map<Integer, EndPointBase> transformerMap) {
		super();
		this.className = className;
		this.positionX = positionX;
		this.positionY = positionY;
		this.structureType = structureType;
		this.transformerMap = transformerMap;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public double getPositionX() {
		return positionX;
	}
	public void setPositionX(double positionX) {
		this.positionX = positionX;
	}
	public double getPositionY() {
		return positionY;
	}
	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}
	public Map<Integer, EndPointBase> getTransformerMap() {
		return transformerMap;
	}
	public void setTransformerMap(Map<Integer, EndPointBase> transformerMap) {
		this.transformerMap = transformerMap;
	}
	public String getStructureType() {
		return structureType;
	}
	public void setStructureType(String structureType) {
		this.structureType = structureType;
	}
}
