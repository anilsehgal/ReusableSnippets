package com.victor.importer.constants;

public class ProfileBean {
	Object[][] columnMapping;
	Object[][] optionMapping;
	String delimiter;
	String flavor;
	String filePath;
	String startLine;
	String tableName;
	String dburl;
	String driverName;
	String userName;
	String password;
	String dupRowAction;
	String dateFormat;
	String sheetNo;
	String endRow;
	String defaultStrategy;
	String preImportCmd;
	String postImportCmd;
	String preImportCmdType;
	String postImportCmdType;
	String continueImportOnPreCmdFailure;
	boolean truncateOverflowingValues;
	
	public boolean isTruncateOverflowingValues() {
		return truncateOverflowingValues;
	}
	public void setTruncateOverflowingValues(boolean truncateOverflowingValues) {
		this.truncateOverflowingValues = truncateOverflowingValues;
	}
	public String getContinueImportOnPreCmdFailure() {
		return continueImportOnPreCmdFailure;
	}
	public void setContinueImportOnPreCmdFailure(
			String continueImportOnPreCmdFailure) {
		this.continueImportOnPreCmdFailure = continueImportOnPreCmdFailure;
	}
	public String getPreImportCmd() {
		return preImportCmd;
	}
	public void setPreImportCmd(String preImportCmd) {
		this.preImportCmd = preImportCmd;
	}
	public String getPostImportCmd() {
		return postImportCmd;
	}
	public void setPostImportCmd(String postImportCmd) {
		this.postImportCmd = postImportCmd;
	}
	public String getPreImportCmdType() {
		return preImportCmdType;
	}
	public void setPreImportCmdType(String preImportCmdType) {
		this.preImportCmdType = preImportCmdType;
	}
	public String getPostImportCmdType() {
		return postImportCmdType;
	}
	public void setPostImportCmdType(String postImportCmdType) {
		this.postImportCmdType = postImportCmdType;
	}
	public String getDefaultStrategy() {
		return defaultStrategy;
	}
	public void setDefaultStrategy(String defaultStrategy) {
		this.defaultStrategy = defaultStrategy;
	}
	public String getEndRow() {
		return endRow;
	}
	public void setEndRow(String endRow) {
		this.endRow = endRow;
	}
	public String getSheetNo() {
		return sheetNo;
	}
	public void setSheetNo(String sheetNo) {
		this.sheetNo = sheetNo;
	}
	public Object[][] getOptionMapping() {
		return optionMapping;
	}
	public void setOptionMapping(Object[][] optionMapping) {
		this.optionMapping = optionMapping;
	}
	public Object[][] getColumnMapping() {
		return columnMapping;
	}
	public void setColumnMapping(Object[][] columnMapping) {
		this.columnMapping = columnMapping;
	}
	public String getDelimiter() {
		return delimiter;
	}
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}
	public String getFlavor() {
		return flavor;
	}
	public void setFlavor(String flavor) {
		this.flavor = flavor;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getStartLine() {
		return startLine;
	}
	public void setStartLine(String startLine) {
		this.startLine = startLine;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getDburl() {
		return dburl;
	}
	public void setDburl(String dburl) {
		this.dburl = dburl;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDupRowAction() {
		return dupRowAction;
	}
	public void setDupRowAction(String dupRowAction) {
		this.dupRowAction = dupRowAction;
	}
	public String getDateFormat() {
		return dateFormat;
	}
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	
}
