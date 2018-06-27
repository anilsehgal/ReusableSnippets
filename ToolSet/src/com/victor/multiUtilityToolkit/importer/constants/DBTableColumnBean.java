package com.victor.multiUtilityToolkit.importer.constants;

public class DBTableColumnBean {
	private int columnPosition;
	private String columnName;
	private String dataType;
	private int characterMaximumLength;
	private String isNullable;
	public int getColumnPosition() {
		return columnPosition;
	}
	public void setColumnPosition(int columnPosition) {
		this.columnPosition = columnPosition;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public int getCharacterMaximumLength() {
		return characterMaximumLength;
	}
	public void setCharacterMaximumLength(int characterMaximumLength) {
		this.characterMaximumLength = characterMaximumLength;
	}
	public String getIsNullable() {
		return isNullable;
	}
	public void setIsNullable(String isNullable) {
		this.isNullable = isNullable;
	}
}
