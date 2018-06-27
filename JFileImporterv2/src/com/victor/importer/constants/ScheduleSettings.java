package com.victor.importer.constants;

import java.util.Date;

public class ScheduleSettings {
	public static Date startDate;
	public static Date endDate;
	public static Date jobRunDate;
	public static long intervalInSecs;
	public static int jobStatus=1;
	public static boolean deleteFileAfterImport;
	
	
	public static Date getJobRunDate() {
		return jobRunDate;
	}
	public static void setJobRunDate(Date jobRunDate) {
		ScheduleSettings.jobRunDate = jobRunDate;
	}
	public static boolean isDeleteFileAfterImport() {
		return deleteFileAfterImport;
	}
	public static void setDeleteFileAfterImport(boolean deleteFileAfterImport) {
		ScheduleSettings.deleteFileAfterImport = deleteFileAfterImport;
	}
	public static int getJobStatus() {
		return jobStatus;
	}
	public static void setJobStatus(int jobStatus) {
		ScheduleSettings.jobStatus = jobStatus;
	}
	public static Date getStartDate() {
		return startDate;
	}
	public static void setStartDate(Date startDate) {
		ScheduleSettings.startDate = startDate;
	}
	public static Date getEndDate() {
		return endDate;
	}
	public static void setEndDate(Date endDate) {
		ScheduleSettings.endDate = endDate;
	}
	public static long getIntervalInSecs() {
		return intervalInSecs;
	}
	public static void setIntervalInSecs(long intervalInSecs) {
		ScheduleSettings.intervalInSecs = intervalInSecs;
	}
	
}
