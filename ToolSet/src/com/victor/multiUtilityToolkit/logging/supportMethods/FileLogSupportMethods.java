package com.victor.multiUtilityToolkit.logging.supportMethods;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileLogSupportMethods {
	
	public static void generateLogXML() throws Exception{
		BufferedReader br = null;
		String line = null;
		FileWriter logConfigFileWriter = null;
		List<String> list = new ArrayList<String>();
		File logFile = new File("C://log4j.xml");
		if(!logFile.exists()){
			logFile.createNewFile();
			InputStream is = FileLogSupportMethods.class.getResourceAsStream("/config/log4j.xml");
		    br = new BufferedReader(new InputStreamReader(is));
		    while (null != (line = br.readLine())) {
		    	list.add(line);
		    }
		    logConfigFileWriter = new FileWriter(logFile, false);
		    for(String readLine:list){
		    	logConfigFileWriter.write(readLine);
		    	logConfigFileWriter.write("\n");
		    }
		    logConfigFileWriter.close();
		    is.close();
		}			
	}
}
