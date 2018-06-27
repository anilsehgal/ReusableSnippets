package com.victor.importer.constants;

import java.util.List;

public class ImporterBean {
	public static Object[] fileColumnNames;
	public static List<DBTableColumnBean> dbTableColumnBeans;
	public static Object[][] filedata;
	public static Object[] fixedLengthFileColumnNames;
	public static String fileFlavor;
	public static String dateFormat;
	public static ProfileBean loadedProfile;
	public static int rowsImported;
	public static boolean inporting;
	
	
	public static boolean isInporting() {
		return inporting;
	}

	public static void setInporting(boolean inporting) {
		ImporterBean.inporting = inporting;
	}

	public static int getRowsImported() {
		return rowsImported;
	}

	public static void setRowsImported(int rowsImported) {
		ImporterBean.rowsImported = rowsImported;
	}

	public static ProfileBean getLoadedProfile() {
		return loadedProfile;
	}

	public static void setLoadedProfile(ProfileBean loadedProfile) {
		ImporterBean.loadedProfile = loadedProfile;
	}

	public static String getDateFormat() {
		return dateFormat;
	}

	public static void setDateFormat(String dateFormat) {
		ImporterBean.dateFormat = dateFormat;
	}

	public static String getFileFlavor() {
		return fileFlavor;
	}

	public static void setFileFlavor(String fileFlavor) {
		ImporterBean.fileFlavor = fileFlavor;
	}

	public static Object[] getFixedLengthFileColumnNames() {
		return fixedLengthFileColumnNames;
	}

	public static void setFixedLengthFileColumnNames(
			Object[] fixedLengthFileColumnNames) {
		ImporterBean.fixedLengthFileColumnNames = fixedLengthFileColumnNames;
	}

	public static Object[][] getFiledata() {
		return filedata;
	}

	public static void setFiledata(Object[][] filedata) {
		ImporterBean.filedata = filedata;
	}

	public static List<DBTableColumnBean> getDbTableColumnBeans() {
		return dbTableColumnBeans;
	}

	public static void setDbTableColumnBeans(
			List<DBTableColumnBean> dbTableColumnBeans) {
		ImporterBean.dbTableColumnBeans = dbTableColumnBeans;
	}

	public static Object[] getFileColumnNames() {
		return fileColumnNames;
	}

	public static void setFileColumnNames(Object[] fileColumnNames) {
		ImporterBean.fileColumnNames = fileColumnNames;
	}
}
