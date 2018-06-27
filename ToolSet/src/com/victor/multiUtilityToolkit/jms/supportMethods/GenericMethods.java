package com.victor.multiUtilityToolkit.jms.supportMethods;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jms.Message;

import com.victor.multiUtilityToolkit.jms.constants.JMSConstants;
import com.victor.multiUtilityToolkit.jms.ui.MonitorMain;


public class GenericMethods {
	public static Object[][] getDataAsArray(List<Message> inputList) throws Exception{
		Object[][] returnArray = null;
		if(inputList!=null){
			returnArray = new Object[inputList.size()][3];
			for(int i=0;i<inputList.size();i++){
				returnArray[i][0] = i+1;
				returnArray[i][1] = inputList.get(i).getJMSMessageID();
				returnArray[i][2] = milliSecsToDate(inputList.get(i).getJMSTimestamp());
			}
		}else{
			System.out.println("no messages found");
		}
		return returnArray;
	}
	public static Object[] getTitlesAsArray() throws Exception{
		Object[] returnArray = new Object[3];
		returnArray[0] = JMSConstants.S_NO;
		returnArray[1] = JMSConstants.JMS_MESSAGE_ID;
		returnArray[2] = JMSConstants.JMS_TIME_STAMP;
		return returnArray;
	}
	public static Object[] getHeaderNamesAsArray() throws Exception{
		Object[] returnArray = new Object[2];
		returnArray[0] = JMSConstants.HEADER;
		returnArray[1] = JMSConstants.VALUE;
		return returnArray;
	}
	public static Date milliSecsToDate(long milliSecs)throws Exception {
		Date resultdate = new Date(milliSecs); 
		return resultdate;
	} 
	public static Object[] getLogTitlesAsArray() throws Exception{
		Object[] returnArray = new Object[3];
		returnArray[0] = "Date";
		returnArray[1] = "Operation";
		returnArray[2] = "Status";
		return returnArray;
	}
	public static Object[][] getHeadersAsArray(Message message) throws Exception{
		Object[][] returnArray = new Object[10][2];
		returnArray[0][0] = JMSConstants.JMS_MESSAGE_ID;
		returnArray[0][1] = message.getJMSMessageID();
		returnArray[1][0] = JMSConstants.JMS_TIME_STAMP;
		returnArray[1][1] = milliSecsToDate(message.getJMSTimestamp());
		returnArray[2][0] = JMSConstants.JMS_CORRELATION_ID;
		returnArray[2][1] = message.getJMSCorrelationID();
		returnArray[3][0] = JMSConstants.JMS_DELIVERY_MODE;
		returnArray[3][1] = message.getJMSDeliveryMode();
		returnArray[4][0] = JMSConstants.JMS_DESTINATION;
		returnArray[4][1] = message.getJMSDestination();
		returnArray[5][0] = JMSConstants.JMS_EXPIRATION;
		returnArray[5][1] = message.getJMSExpiration();
		returnArray[6][0] = JMSConstants.JMS_PRIORITY;
		returnArray[6][1] = message.getJMSPriority();
		returnArray[7][0] = JMSConstants.JMS_REDELIVERED;
		returnArray[7][1] = message.getJMSRedelivered();
		returnArray[8][0] = JMSConstants.JMS_REPLY_TO;
		returnArray[8][1] = message.getJMSReplyTo();
		returnArray[9][0] = JMSConstants.JMS_TYPE;
		returnArray[9][1] = message.getJMSType();
		return returnArray;
	}
	public static Object[][] getEmptyHeadersAsArray() throws Exception{
		Object[][] returnArray = new Object[10][2];
		returnArray[0][0] = JMSConstants.JMS_MESSAGE_ID;
		returnArray[0][1] = JMSConstants.EMPTY_STRING;
		returnArray[1][0] = JMSConstants.JMS_TIME_STAMP;
		returnArray[1][1] = JMSConstants.EMPTY_STRING;
		returnArray[2][0] = JMSConstants.JMS_CORRELATION_ID;
		returnArray[2][1] = JMSConstants.EMPTY_STRING;
		returnArray[3][0] = JMSConstants.JMS_DELIVERY_MODE;
		returnArray[3][1] = JMSConstants.EMPTY_STRING;
		returnArray[4][0] = JMSConstants.JMS_DESTINATION;
		returnArray[4][1] = JMSConstants.EMPTY_STRING;
		returnArray[5][0] = JMSConstants.JMS_EXPIRATION;
		returnArray[5][1] = JMSConstants.EMPTY_STRING;
		returnArray[6][0] = JMSConstants.JMS_PRIORITY;
		returnArray[6][1] = JMSConstants.EMPTY_STRING;
		returnArray[7][0] = JMSConstants.JMS_REDELIVERED;
		returnArray[7][1] = JMSConstants.EMPTY_STRING;
		returnArray[8][0] = JMSConstants.JMS_REPLY_TO;
		returnArray[8][1] = JMSConstants.EMPTY_STRING;
		returnArray[9][0] = JMSConstants.JMS_TYPE;
		returnArray[9][1] = JMSConstants.EMPTY_STRING;
		return returnArray;
	}
/*	public static Object[][] getTestAsArray() throws Exception{
		Object[][] returnArray = new Object[2][3];
		returnArray[0][0] = "1";
		returnArray[0][1] = "2";
		returnArray[0][2] = "3";
		returnArray[1][0] = "4";
		returnArray[1][1] = "5";
		returnArray[1][2] = "6";
		return returnArray;
	}*/
	public static List<String> getSelectorOptions(){
		List<String> returnList = new ArrayList<String>();
		returnList.add(JMSConstants.JMS_MESSAGE_ID);
		returnList.add(JMSConstants.JMS_TIME_STAMP);
		returnList.add(JMSConstants.JMS_CORRELATION_ID);
		returnList.add(JMSConstants.JMS_DELIVERY_MODE);
		returnList.add(JMSConstants.JMS_DESTINATION);
		returnList.add(JMSConstants.JMS_EXPIRATION);
		returnList.add(JMSConstants.JMS_PRIORITY);
		returnList.add(JMSConstants.JMS_REDELIVERED);
		returnList.add(JMSConstants.JMS_REPLY_TO);
		returnList.add(JMSConstants.JMS_TYPE);
		return returnList;
	}
	public static long getTimeInMilliSecs(String dateStr) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat(JMSConstants.DATE_PATTERN_1);
		Date date = sdf.parse(dateStr);
		return date.getTime();
	}
	public static String buildSelector(String criterion, String operator, String value) throws Exception{
		String selector = JMSConstants.EMPTY_STRING;
		MonitorMain.logDetail+="\nBuilding selector with "+criterion+", "+operator+", "+value+"\n";
		if(criterion.equals(JMSConstants.JMS_MESSAGE_ID)){
			selector = criterion+operator+JMSConstants.SINGLE_QUOTE+value+JMSConstants.SINGLE_QUOTE;
		}else{
			selector = criterion+operator+value;
		}
		MonitorMain.logDetail+="\nSelector built: \n"+selector+"\n";
		return selector;
	}
	public static String buildSelector(String criterion, String operator, Date value1, Date value2) throws Exception{
		String selector = JMSConstants.EMPTY_STRING;
		MonitorMain.logDetail+="\nBuilding selector with "+criterion+", "+operator+", "+value1+", "+value2+"\n";
		if(criterion.equals(JMSConstants.JMS_TIME_STAMP)){
			selector = JMSConstants.JMS_TIME_STAMP;
			if(operator.equals(JMSConstants.SPACE+JMSConstants.BETWEEN+JMSConstants.SPACE)){
				selector = selector + JMSConstants.SPACE+JMSConstants.BETWEEN+JMSConstants.SPACE + value1.getTime() + JMSConstants.SPACE+JMSConstants.AND+JMSConstants.SPACE+ (value2.getTime());
			}else{
				selector = selector + operator + value1.getTime();
			}
		}
		MonitorMain.logDetail+="\nSelector built: \n"+selector+"\n";
		return selector;
	}
	public static void saveContentsToFile(String filePath, String contents) throws Exception{
		MonitorMain.logDetail+="\nSaving contents: \n"+contents+" to file: "+filePath+"\n";
		File file = new File(filePath);
		if(!file.exists()){
			MonitorMain.logDetail+="\ncreating new file: "+filePath+"\n";
			file.createNewFile();
		}else{
			MonitorMain.logDetail+="\nfile already present: "+filePath+"\n";
		}
		FileOutputStream outputStream = new FileOutputStream(file, false);
		byte[] byteArray = contents.getBytes();
		outputStream.write(byteArray);
		MonitorMain.logDetail+="\nwritten to file" +
				"\n"+contents+"\n";
		outputStream.close();
	}
	public static boolean srvrExists(String serverUrl) throws Exception{
		List<String> serverList = new ArrayList<String>();
		boolean returnValue = false;
		FileInputStream fstream;
		int srvrCnt=0;

		fstream = new FileInputStream(JMSConstants.JMS_SERVER_FILE_PATH);
		
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		MonitorMain.logDetail+="\nChecking if server already exists in the file: "+JMSConstants.JMS_SERVER_FILE_PATH+"\n";
		while ((strLine = br.readLine()) != null)   {
		  serverList.add(strLine);
		}
		in.close();
		for(int indx=0;indx<serverList.size();indx++){
			if(serverUrl.toString().trim().equalsIgnoreCase(serverList.get(indx).trim())){
				srvrCnt++;
				MonitorMain.logDetail+="\n"+serverUrl+" server already present!!\n";
			}
		}
		if(srvrCnt>0){
			returnValue =  true;
		}else{
			returnValue =  false;
			MonitorMain.logDetail+="\nServer Not Found in File: "+JMSConstants.JMS_SERVER_FILE_PATH+"\n";
		}
		return returnValue;
	}
}
