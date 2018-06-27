package com.psa.entities.serializable;

import java.io.Serializable;

public class PSALink implements Serializable {

	private static final long serialVersionUID = 7059985279393040439L;
	private String sourceTableId;
	private String targetTableId;
	private int sourceTableRowIndex;
	private int targetTableRowIndex;
	private double[] points;
	
	
	public PSALink(String sourceTableId, String targetTableId,
			int sourceTableRowIndex, int targetTableRowIndex , double[] points) {
		super();
		this.sourceTableId = sourceTableId;
		this.targetTableId = targetTableId;
		this.sourceTableRowIndex = sourceTableRowIndex;
		this.targetTableRowIndex = targetTableRowIndex;
		this.points = points;
	}
	public String getSourceTableId() {
		return sourceTableId;
	}
	public void setSourceTableId(String sourceTableId) {
		this.sourceTableId = sourceTableId;
	}
	public String getTargetTableId() {
		return targetTableId;
	}
	public void setTargetTableId(String targetTableId) {
		this.targetTableId = targetTableId;
	}
	public int getSourceTableRowIndex() {
		return sourceTableRowIndex;
	}
	public void setSourceTableRowIndex(int sourceTableRowIndex) {
		this.sourceTableRowIndex = sourceTableRowIndex;
	}
	public int getTargetTableRowIndex() {
		return targetTableRowIndex;
	}
	public void setTargetTableRowIndex(int targetTableRowIndex) {
		this.targetTableRowIndex = targetTableRowIndex;
	}
	public double[] getPoints() {
		return points;
	}
	public void setPoints(double[] points) {
		this.points = points;
	}
}
