package com.psa.entities.gui.controls;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;

public class EntityLink extends Polyline {
	
	private String sourceStructureId;
	private String targetStructureId;
	private int sourceRowIndex;
	private int targetRowIndex;
	private Point2D start , end;
	
	public EntityLink( String sourceStructureId , String targetStructureId ,
			int sourceRowIndex , int targetRowIndex, Point2D start, Point2D end ) {
		
		super();
		
		this.sourceStructureId = sourceStructureId;
		this.targetStructureId = targetStructureId;
		this.sourceRowIndex = sourceRowIndex;
		this.targetRowIndex = targetRowIndex;
		this.start = start;
		this.end = end;
		
		setOnMouseClicked( new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
				PSADesigner.getInstance().setFocussedNode( getActiveLine() );
				setStroke( Color.RED );
			}
		} );
		
	}
	
	private EntityLink getActiveLine() {
		
		return this;
	}
	
	public String getSourceStructureId() {
		return sourceStructureId;
	}
	public void setSourceStructureId(String sourceStructureId) {
		this.sourceStructureId = sourceStructureId;
	}
	public String getTargetStructureId() {
		return targetStructureId;
	}
	public void setTargetStructureId(String targetStructureId) {
		this.targetStructureId = targetStructureId;
	}
	public int getSourceRowIndex() {
		return sourceRowIndex;
	}
	public void setSourceRowIndex(int sourceRowIndex) {
		this.sourceRowIndex = sourceRowIndex;
	}
	public int getTargetRowIndex() {
		return targetRowIndex;
	}
	public void setTargetRowIndex(int targetRowIndex) {
		this.targetRowIndex = targetRowIndex;
	}
	public Point2D getStart() {
		return start;
	}
	public void setStart(Point2D start) {
		this.start = start;
	}
	public Point2D getEnd() {
		return end;
	}
	public void setEnd(Point2D end) {
		this.end = end;
	}
}
