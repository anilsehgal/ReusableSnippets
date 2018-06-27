package com.Infosys.DPMonitor.Impl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.Infosys.DPMonitor.constants.Constants;
/**
 * @author Anil_Sehgal01
 * Class with generic public methods 
 */
public class GenericMethods {
	Logger logger = Logger.getLogger("GenericMethods.class");
	Properties prop = new Properties();
	Properties logProperties = new Properties();
	Connection con;
	Statement s;
	
	
    /**
     * Parameterized constructor which takes the properties file path as Input
     * @param propertiesFile
     * @throws Exception
     */
	public GenericMethods(String propertiesFile){
	    try{
		      // load our log4j properties configuration file
			DOMConfigurator.configure(Constants.LOG_CONFIG_FILE);
			prop.load(this.getClass().getResource(propertiesFile).openStream());
		//	PropertyConfigurator.configure(Constants.LOG_CONFIG_FILE);
	    }catch(Exception e){
	    	logger.fatal("Error Initializing Class "+this.getClass().getName(),e);
	    }
	}
	/**
	 * This method sends a mail to the recipients as defined in the call
	 * @author Anil_Sehgal01
	 * @return void
	 * @param 
	 * from - MailId from which the mail is sent,
	 * to - MailId to which the mail is being sent,
	 * subject - Mail Subject,
	 * text - Mail Content
	 * hostName - SMTP Host
	 * port - SMTP Port
	 * @throws Exception 
	 */
	public void sendMail(String from, String to, String subject, String text, String hostName, String port){
		try{
			logger.debug("Entering Method sendMail() in "+getClass().getName());
			Properties mailProps = new Properties();
			mailProps.put(Constants.MAIL_SMTP_HOST, hostName);
			mailProps.put(Constants.MAIL_SMTP_PORT, port);
			Session mailSession = Session.getDefaultInstance(mailProps);
			Message simpleMessage = new MimeMessage(mailSession);		
			InternetAddress fromAddress = null;
			InternetAddress toAddress = null;
			fromAddress = new InternetAddress(from);
			simpleMessage.setFrom(fromAddress);
			List toList = new ArrayList();
			  StringTokenizer st = new StringTokenizer (to,";");
			  while (st.hasMoreTokens ()) {
			    toList.add(st.nextToken ());
			  }
			logger.debug("Receipients:");
			for(int rec=0;rec<toList.size();rec++){
				logger.debug((String) toList.get(rec));
				toAddress = new InternetAddress((String) toList.get(rec));
				simpleMessage.addRecipient(RecipientType.TO, toAddress);
			}
			simpleMessage.setSubject(subject);
			simpleMessage.setText(text);
			logger.debug(prop.getProperty(Constants.MAIL_LOG_MSG_BEFORE_SEND));
			logger.debug("Sending Mail:");
			logger.debug("to:"+to);
			logger.debug("from:"+from);
			logger.debug("Subject:"+subject);
			logger.debug("Content:"+text);
			Transport.send(simpleMessage);	
			logger.info(prop.getProperty(Constants.MAIL_LOG_MSG_AFTER_SEND));
			logger.debug("Exiting Method sendMail() in "+getClass().getName());
		}catch(Exception e){
			logger.error("Exception in SendMail:",e);
		}
	}
	/**
	 * This method executes a Unix command as a process and reads and stores the result line by line
	 * in a String List
	 * @author Anil_Sehgal01
	 * @return java.util.List
	 * @param unixCommand - String command to be executed
	 * @throws Exception 
	 */
	public List runUnixCommand(String unixCommand){
		try{
		logger.debug("Entering Method runUnixCommand() in "+getClass().getName());
		List contentList = new ArrayList();
		List errorList = new ArrayList();
		logger.debug("Executing Command:"+unixCommand);
		Process process = Runtime.getRuntime().exec(unixCommand);
		int i = process.waitFor();
		logger.debug("Executed Command:"+unixCommand);
		if (i == 0){
			BufferedReader is = new BufferedReader(new InputStreamReader(process.getInputStream()));		
			String n=null;
			while ((n = is.readLine()) != null) {
				contentList.add(n);
			}
			logger.debug("Unix Command Success: Filling InputStream");
			logger.info(prop.getProperty(Constants.UNIX_CMD_LOG_SUCCESS_MSG));
		}else{
			BufferedReader stdErr = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			// read the output from the command
			String s = null;
			while ((s = stdErr.readLine()) != null) {
				errorList.add(s);
			}
			logger.debug("Unix Command Failure: Filling ErrorStream");
			logger.info(prop.getProperty(Constants.UNIX_CMD_LOG_FAIL_MSG));
		}
		logger.debug("Exiting Method runUnixCommand() in "+getClass().getName());
		if(contentList.size()>0){
			return contentList;
		}else{
			return errorList;
		}
		}catch(Exception e){
			logger.error("Exception in running unix command:",e);
			return null;
		}
	}
	/**
	 * This method reads a file and returnd the inputstream as a list containing the line contents of the file
	 * @author Anil_Sehgal01
	 * @return java.sql.ResultSet
	 * @param filename - String
	 * @throws Exception 
	 */
	public List readFile(String fileName){
		List returnList = new ArrayList();
		File f = new File(fileName);
		if(!f.exists()){
			logger.info("Payroll File not found!!");
		}else{
			try {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
				String readLine = null;
				while((readLine = bufferedReader.readLine())!=null){
					returnList.add(readLine);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("Payroll File not found!!",e);
			}
		}
		return returnList;
	}
	/**
	 * This method executes a Database query and returns the ResultSet obtained
	 * @author Anil_Sehgal01
	 * @return java.sql.ResultSet
	 * @param query - String SQL select query to be executed
	 * @throws Exception 
	 */
	public ResultSet execSelQry(String query,String driverUrl,String dBUrl, String dBUser, String dBPass){
		ResultSet rs=null;
		try{
			logger.debug("Entering Method execSelQry() in "+getClass().getName());
			Class.forName(driverUrl);
			DriverManager.registerDriver(new com.microsoft.jdbc.sqlserver.SQLServerDriver());
			logger.debug("Driver Loaded in Memory:"+driverUrl);
			Connection con=DriverManager.getConnection(dBUrl,dBUser,dBPass);
			Statement s=con.createStatement();
			logger.debug("Executing Query: "+query);
			rs = s.executeQuery(query);
			logger.debug("Query Executed: "+query);
			int cols = rs.getMetaData().getColumnCount();
			logger.debug("Columns in ResultSet: "+cols);
			String colNames = Constants.EMPTY_STRING;
			for(int colIndx=1;colIndx<=cols;colIndx++){
				colNames = colNames+rs.getMetaData().getColumnLabel(colIndx) + Constants.DATA_SEP;
			}
			logger.info("Data table:");
			if(cols==Constants.NUMBER_2){
				logger.info(tabularFormat(colNames));
			}else{
				logger.info(colNames);
			}
			return rs;
		}catch(Exception e){
			logger.error("Exception in ExecSelQry:",e);
			return null;
		}finally{
			try{
				if(con!=null){
					con.close();
				}
				if(s!=null){
					s.close();
				}
				logger.debug("Exiting Method execSelQry() in "+getClass().getName());
			}catch(Exception e){
				logger.error("Exception in ExecSelQry:",e);
			}
		}
	}
	/**
	 * This method takes a string of two values saparated by a ; (sas;asas) as input and 
	 * returns a string with relative spaces between two values
	 * @author Anil_Sehgal01
	 * @return String
	 * @param inputString - String input
	 * @throws Exception 
	 */
	public String tabularFormat(String inputString){
		try{
			logger.debug("Entering Method tabularFormat() in "+getClass().getName());
			String outputString=Constants.EMPTY_STRING;
			int totalLength = Constants.NUMBER_25;
			int sepIndex = inputString.indexOf(Constants.DATA_SEP);
			String name = inputString.substring(0, sepIndex);
			String value = inputString.substring(sepIndex+1, inputString.length());
			int noOfSpaces = totalLength - name.length() - value.length();
			String spaceString = Constants.EMPTY_STRING;
			for(int z=0;z<=noOfSpaces;z++){
				spaceString=spaceString.concat(Constants.STRING_SPACE);
			}
			outputString = name+spaceString+value;
			logger.debug("Returning String: "+outputString);
			logger.debug("Exiting Method tabularFormat() in "+getClass().getName());
			return outputString;
		}catch(Exception e){
			logger.error("Exception in tabularFormat",e);
			return null;
		}
	}
	/**
	 * This method takes a long value as input and returns a corresponding date
	 * calculated with reference to January 1, 1970  
	 * @author Anil_Sehgal01
	 * @return Date
	 * @param milliSecs - long input
	 * @throws Exception 
	 */
	public Date milliSecsToDate(long milliSecs) {
		try{
			logger.debug("Entering Method milliSecsToDate() in "+getClass().getName());
			Date resultdate = new Date(milliSecs);
			logger.debug("Returning Date: "+resultdate+" <=> "+milliSecs);
			logger.debug("Exiting Method milliSecsToDate() in "+getClass().getName());
			return resultdate;
		}catch(Exception e){
			logger.error("Exception in milliSecsToDate:",e);
			return null;
		}
	} 
	/**
	 * This method takes a Date String as input and returns a corresponding long (millisecs)
	 * calculated with reference to January 1, 1970  
	 * @author Anil_Sehgal01
	 * @return long
	 * @param String - Date input
	 * @throws Exception 
	 */
	public long dateToMillisec(String date){
		try{
			logger.debug("Entering Method dateToMillisec() in "+getClass().getName());
		    DateFormat formatter;
		    Date dat=null;
		    formatter = new SimpleDateFormat("dd-MMM-yy");
		    dat = (Date)formatter.parse(date);
			long longDate=dat.getTime();
			logger.debug("Returning MilliSecs: "+longDate+" <=> "+date);
			logger.debug("Entering Method dateToMillisec() in "+getClass().getName());
			return longDate;
		}catch(Exception e){
			logger.error("Exception in dateToMillisec:",e);
			return 0;
		}
	}
	/**
	 * This method returns system date
	 * @author Anil_Sehgal01
	 * @return Date
	 * @param none
	 * @throws Exception 
	 */
	public Date getSysDate(){
		try{
			logger.debug("Entering Method getSysDate() in "+getClass().getName());
			Date currentDate = new Date();
			long currentDateMilliSecs = currentDate.getTime();
			logger.info("Current Date: "+milliSecsToDate(currentDateMilliSecs));
			logger.debug("Exiting Method getSysDate() in "+getClass().getName());
			return milliSecsToDate(currentDateMilliSecs);
		}catch(Exception e){
			logger.error("Exception in getSysdate:",e);
			return null;
		}
	}
	/**
	 * This method causes the current program execution to wait for an input time in milliseconds
	 * @author Anil_Sehgal01
	 * @return void
	 * @param milliSecs - Long
	 * @throws Exception 
	 */
	public void haltThread(String milliSecs){
		try{
			logger.debug("Entering Method haltThread() in "+getClass().getName());
			Long milliSecObj = new Long(milliSecs);
			long milliSec = milliSecObj.longValue();
			Date initialDateObj = new Date();
			long initialTime = initialDateObj.getTime();
			while((new Date()).getTime()<(initialTime+milliSec)){
				logger.debug("wait for "+(milliSec/5000)+" seconds");// 1 sec=1000 millisecs
				Thread.sleep(milliSec/5);//5 intervals
			}
			logger.debug("Exiting Method haltThread() in "+getClass().getName());
		}catch(Exception e){
			logger.error("Exception in haltThread:",e);
		}
	}
	/**
	 * This method removes duplicate elements from a List and returns a List with unique elements only
	 * @author Anil_Sehgal01
	 * @return List
	 * @param listWithDuplicates - List
	 * @throws Exception 
	 */
	public List eliminateDuplicatesFromList(List listWithDuplicates){
		try{
			logger.debug("Entering Method eliminateDuplicatesFromList() in "+getClass().getName());	
			List listWithoutDuplicates = new ArrayList();
			for(int i=0;i<listWithDuplicates.size();i++){
				if(!listWithoutDuplicates.contains(listWithDuplicates.get(i))){
					listWithoutDuplicates.add(listWithDuplicates.get(i));
				}
			}
			logger.info("TCs in file: "+listWithoutDuplicates.size()+" sample TC: "+listWithoutDuplicates.get(0));
			
			logger.debug("Exiting Method eliminateDuplicatesFromList() in "+getClass().getName());	
			return listWithoutDuplicates;
		}catch(Exception e){
			logger.error("Exception in eliminateDuplicatesFromList:",e);
			return null;
		}
	}
	/**
	 * This method returns the fileTimeStamp of a unix file in a defined format
	 * @author Anil_Sehgal01
	 * @return Date
	 * @param fileCompleteName - input file path and name String
	 * @throws Exception 
	 */
	public Date getFileModTimeStamp(String fileCompleteName){
		try{
			logger.debug("Entering Method getFileModTimeStamp() in "+getClass().getName());
			String command = prop.getProperty(Constants.CMD_UNIX_FILE_SEARCH).concat(Constants.STRING_SPACE).concat(fileCompleteName);
			List cmdList = runUnixCommand(command);
			java.util.Date dt = null;
			String modDateStr = Constants.EMPTY_STRING;
			for(int ind=0;ind<cmdList.size();ind++){
				if(cmdList.get(ind).toString().indexOf((String)prop.getProperty(Constants.SEN_FILE_TIME_STAMP))!=-1){
					modDateStr=cmdList.get(ind).toString().substring(Constants.NUMBER_19); 
					modDateStr = modDateStr.replaceAll(Constants.CST, Constants.EMPTY_STRING);
					SimpleDateFormat sdf = new SimpleDateFormat(Constants.SIMPLE_DATE_FORMAT);
					dt = sdf.parse(modDateStr);
					break;
				}
			}
			logger.debug("Exiting Method getFileModTimeStamp() in "+getClass().getName());
			return dt;
		}catch(Exception e){
			logger.error("Exception in getFileModTimeStamp:",e);
			return null;
		}
	}
	/**
	 * This method returns the size of a unix file in bytes
	 * @author Anil_Sehgal01
	 * @return Date
	 * @param fileName - String name and path of file
	 * @throws Exception 
	 */
	public long getfileSize(String fileName){
		try{
			logger.debug("Entering Method getfileSize() in "+getClass().getName());
		    File file = new File(fileName);
			    
		    if (!file.exists() || !file.isFile()) {
		    	logger.error("File "+fileName+" doesn\'t exist");
		    	return -1;
			}
		    logger.debug("Exiting Method getfileSize() in "+getClass().getName());
			//Here we get the actual size
		    return file.length();
		}catch(Exception e){
			logger.error("Exception in getFileSize:",e);
			return -1;
		}
	}
}