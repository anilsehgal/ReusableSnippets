package com.victor.multiUtilityToolkit.logging.ui;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;



public class FileLog {
	static Logger logger;
	public FileLog(String logContent){
		logger = Logger.getLogger("FileLog.class");
		DOMConfigurator.configure("D:/log4j.xml");
		logger.setLevel((Level) Level.DEBUG);
		logger.info(logContent);
	}
}
