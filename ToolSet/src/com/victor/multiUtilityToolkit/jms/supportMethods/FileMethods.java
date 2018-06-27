package com.victor.multiUtilityToolkit.jms.supportMethods;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.jms.Message;
import javax.jms.TextMessage;

import com.victor.multiUtilityToolkit.jms.constants.JMSConstants;
import com.victor.multiUtilityToolkit.jms.ui.MonitorMain;


public class FileMethods {
	public static List<String> readServerFile() throws Exception{
		List<String> serverList = new ArrayList<String>();

		File file = new File(JMSConstants.JMS_SERVER_FILE_PATH);
		if(!file.exists()){
			file.createNewFile();
		}
		FileInputStream fstream = new FileInputStream(JMSConstants.JMS_SERVER_FILE_PATH);
		// Get the object of DataInputStream
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		
		//Read File Line By Line
		while ((strLine = br.readLine()) != null)   {
			serverList.add(strLine);
		}
		in.close();
		return serverList;
	}
	public static String saveAllToFiles(List<Message> messages, String dir){
	    TextMessage tm=null;
	    try {
	    	Date date = new Date();
	    	String tempDirNm = GenericMethods.milliSecsToDate(date.getTime()).toString();
	    	tempDirNm = tempDirNm.replace(JMSConstants.COLON, JMSConstants.SPACE);
	    	String dirNm=tempDirNm.replace(JMSConstants.SPACE, JMSConstants.UNDER_SCORE);
	    	dirNm = (JMSConstants.MESSAGES_TEXT).concat(dirNm);
	    	if(dir.trim().length()==0){
	    		dir = dirNm;
	    	}
	    	MonitorMain.logDetail+="\nSaving all to path: "+dir+"\n";
	    	
		    ZipOutputStream zipStream = new ZipOutputStream(new FileOutputStream(dir.concat(JMSConstants.EXTN_ZIP)));
		    for(int fInd=0;fInd<messages.size();fInd++){
		    	zipStream.putNextEntry(new ZipEntry(messages.get(fInd).getJMSMessageID().replace(JMSConstants.COLON, JMSConstants.SPACE).replace(JMSConstants.SPACE, JMSConstants.UNDER_SCORE).concat(JMSConstants.EXTN_XML)));
		    	tm = (TextMessage)messages.get(fInd);
		    	zipStream.write(tm.getText().getBytes());
		    	zipStream.closeEntry();
		    	
		    }
		    MonitorMain.logDetail+="\n"+messages.size()+" entries written to zip\n";
		    zipStream.close();
		    
	    } catch (Exception e) {
	    	MonitorMain.logDetail+="\nException Detail:\n"+e+"\n";
		}
	    return dir.concat(JMSConstants.EXTN_ZIP);
	}
	public static String saveAllLogsToFiles(List<String> logs, String dir){
	    try {
		    ZipOutputStream zipStream = new ZipOutputStream(new FileOutputStream(dir.concat(JMSConstants.EXTN_ZIP)));
		    for(int fInd=0;fInd<logs.size();fInd++){
		    	zipStream.putNextEntry(new ZipEntry(("Log_"+(fInd+1)).concat(".log")));
		    	zipStream.write(logs.get(fInd).getBytes());
		    	zipStream.closeEntry();   	
		    }
		    zipStream.close(); 
	    } catch (Exception e) {
	    	
		}
	    return dir.concat(JMSConstants.EXTN_ZIP);
	}
	public static void addServer(String srvr){
		FileWriter wr = null;
		try {
			MonitorMain.logDetail+="\nadding server: "+srvr+"\n";
			wr = new FileWriter(JMSConstants.JMS_SERVER_FILE_PATH, true);
			String LineSeparator = System.getProperty(JMSConstants.LINE_SEP);
			
			wr.write(LineSeparator);
			MonitorMain.logDetail+="\nwriting to file: "+JMSConstants.JMS_SERVER_FILE_PATH+"\n";
			wr.write(srvr, 0, srvr.length());
			wr.close();
			MonitorMain.logDetail+="\nWritten\n";
		} catch (Exception ex) {
			MonitorMain.logDetail+="\nException Detial: \n"+ex+"\n";
		} 
	}
}
