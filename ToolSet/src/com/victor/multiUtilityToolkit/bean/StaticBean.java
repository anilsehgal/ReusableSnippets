package com.victor.multiUtilityToolkit.bean;

import java.util.ArrayList;
import java.util.List;

import com.victor.multiUtilityToolkit.logging.ui.LogWindow;

public class StaticBean {
	static LogWindow logWindow;
	static Object[][] logs;
	static String serviceLog;
	public static boolean activeLoggingEnabled = true;
	public static boolean fileLoggingEnabled;
	public static List<String> logContents = new ArrayList<String>();

	
	public static boolean isFileLoggingEnabled() {
		return fileLoggingEnabled;
	}

	public static void setFileLoggingEnabled(boolean fileLoggingEnabled) {
		StaticBean.fileLoggingEnabled = fileLoggingEnabled;
	}

	public static boolean isActiveLoggingEnabled() {
		return activeLoggingEnabled;
	}

	public static void setActiveLoggingEnabled(boolean activeLoggingEnabled) {
		StaticBean.activeLoggingEnabled = activeLoggingEnabled;
	}

	public static String getServiceLog() {
		return serviceLog;
	}

	public static void setServiceLog(String serviceLog) {
		StaticBean.serviceLog = serviceLog;
	}

	public static List<String> getLogContents() {
		return logContents;
	}

	public static void setLogContents(List<String> logContents) {
		StaticBean.logContents = logContents;
	}

	public static LogWindow getLogWindow() {
		return logWindow;
	}

	public static void setLogWindow(LogWindow logWindow) {
		StaticBean.logWindow = logWindow;
	}

	public static Object[][] getLogs() {
		return logs;
	}

	public static void setLogs(Object[][] logs) {
		StaticBean.logs = logs;
	}
	public static void addLogContents(String log) {
		StaticBean.logContents.add(log);
	}	
	public static String getLogContents(int index) {
		return StaticBean.logContents.get(index);
	}	
}
