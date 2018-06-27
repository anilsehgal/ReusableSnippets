package com.Infosys.DPMonitor.Impl;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.Infosys.DPMonitor.bean.DPMMonitorBean;
import com.Infosys.DPMonitor.constants.Constants;


/**
 * @author Anil_Sehgal01
 * This class contains the implementation for all DPM Monitor specific methods
 */
public class DPMMethods {
	Logger logger = Logger.getLogger("DPMMethods.class");
	Properties logProperties = new Properties();
	GenericMethods dpmMethods = new GenericMethods(Constants.PROPERTIES_PATH);
	java.util.Properties monitorProps = new Properties();
	java.util.Properties dpmProps = new Properties();
	
	public DPMMethods(){
		try{
			DOMConfigurator.configure(Constants.LOG_CONFIG_FILE);
			monitorProps.load(DPMMethods.class.getClass().getResource(Constants.PROPERTIES_PATH).openStream());
			dpmProps.load(DPMMethods.class.getClass().getResource(Constants.DPM_PROPERTIES_PATH).openStream());
		}catch(Exception e){
			logger.error("Exception in Initializing DPMMethods:",e);
		}
	}
	/**
	 * This method reads a unix file and gets all the distinct TCs present in the file 
	 * @author Anil_Sehgal01
	 * @return int
	 * @param 
	 * payrollFileContentList - list having elements consisting of all the lines in the file
	 * beginIndex - index from where the TC Divloc begins in the line
	 * endIndex - index from where the TC Divloc ends in the line
	 */
	public int totalTCsInFile(List payrollFileContentList,String beginIndex, String endIndex){
		try{
			logger.debug("Entering Method totalTCsInFile() in "+getClass().getName());
			List tcsInFileList = new ArrayList();
			Integer strtIndexObj = new Integer(beginIndex);
			Integer endIndexObj = new Integer(endIndex);
			for(int index=1;index<payrollFileContentList.size()-1;index++){
				String tcCode = payrollFileContentList.get(index).toString().substring(strtIndexObj.intValue(), endIndexObj.intValue());
				tcsInFileList.add(tcCode);
			}
			List uniqueTCList = dpmMethods.eliminateDuplicatesFromList(tcsInFileList);
			logger.debug("Exiting Method totalTCsInFile() in "+getClass().getName());
			logger.info("totalTCsInFile() returned: "+uniqueTCList.size());
		return uniqueTCList.size();
		}catch(Exception e){
			logger.error("EXception in totalTCsInFile:",e);
			return 0;
		}
	}
	/**
	 * This method compares if the payroll file is present and has time stamp greater than tody's date and time - 00:00:00
	 * @author Anil_Sehgal01
	 * @return boolean
	 */
	public boolean isPayrollFilePresent(){
		try{
			logger.debug("Entering Method isPayrollFilePresent() in "+getClass().getName());
			Date today = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat(Constants.SIMPLE_DATE_FORMAT);
			String todayStartTime = sdf.format(today);
			todayStartTime = todayStartTime.replaceAll(todayStartTime.substring(Constants.NUMBER_7,Constants.NUMBER_15), Constants.DAY_BEGIN_TIME);
			logger.debug("current date: "+todayStartTime);
			today = sdf.parse(todayStartTime);
			boolean isFilePresent = false;
			Date fileTimeStamp = dpmMethods.getFileModTimeStamp(monitorProps.getProperty(Constants.PAYROLL_FILE_NAME));
			logger.info("filetimestamp in isPayrollFilePresent() is: "+fileTimeStamp.toString());
			logger.info("currentdate in isPayrollFilePresent() is: "+todayStartTime.toString());
			if(fileTimeStamp==null){
				return false;
			}
			if(fileTimeStamp.after(today)){
				logger.debug("isFilePresent is: true");
				isFilePresent = true;
			}
			logger.debug("Exiting Method isPayrollFilePresent() in "+getClass().getName());
			logger.debug("isFilePresent() returned: "+isFilePresent);
			return isFilePresent;
		}catch(Exception e){
			logger.error("Exception in isPayrollFilePresent:",e);
			return false;
		}
	}
	/**
	 * This method calls two methods, 1 to get the total number of TCs in Payroll file<totalTCsInFile>
	 * second to get the total sum in the payroll file<sumOfWageInFile>
	 * @author Anil_Sehgal01
	 * @return String - results appended in a defined format
	 */
	public String runPayrollMethods(){
		try{
			logger.debug("Entering Method runPayrollMethods() in "+getClass().getName());
			String returnString = Constants.EMPTY_STRING;
	    	List fileContentList = dpmMethods.runUnixCommand(monitorProps.getProperty(Constants.CMD_UNIX_FILE_CONTENTS));
	    	int totalTCsInFl = totalTCsInFile(fileContentList, monitorProps.getProperty(Constants.PAYROLL_FILE_TC_BEGIN_INDEX), monitorProps.getProperty(Constants.PAYROLL_FILE_TC_END_INDEX));
	    	returnString=returnString.concat(monitorProps.getProperty(Constants.TC_MSG_STRING)+ Constants.STRING_SPACE+totalTCsInFl);
	    	returnString=returnString.concat(Constants.NEW_LINE);
	    	logger.debug("Adding to returnString: "+monitorProps.getProperty(Constants.TC_MSG_STRING)+totalTCsInFl);
	    	returnString=returnString.concat(monitorProps.getProperty(Constants.SEN_SUM_IN_PAYROLL_FILE)+Constants.STRING_14_SPACES+Constants.STRING_COLON+Constants.STRING_SPACE+sumOfWageInFile(fileContentList));
	    	returnString=returnString.concat(Constants.NEW_LINE);
	    	logger.debug("Adding to returnString: "+"Total Amount in PayrollFile: "+sumOfWageInFile(fileContentList));
	    	logger.debug("runPayrollMethods() returned: "+returnString);
	    	logger.debug("Exiting Method runPayrollMethods() in "+getClass().getName());
	    	return returnString;
		}catch(Exception e){
			logger.error("Exception in runPayrollMethods:",e);
			return null;
		}
	}
	/**
	 * This method gets the sum of wage for the current open payperiod from tempwagehistory and inputs data
	 * @author Anil_Sehgal01
	 * @return String - results appended in a defined format
	 */
	public String getPayrollSum(){
		try{
			logger.debug("Entering Method getPayrollSum() in "+getClass().getName());
			String returnString=Constants.EMPTY_STRING;
	    	ResultSet tempWageHistSum = dpmMethods.execSelQry(monitorProps.getProperty(Constants.QRY_DPM_TEMP_WAGE_HISTORY_SUM), dpmProps.getProperty(Constants.DPM_DB_DRIVER_URL), dpmProps.getProperty(Constants.DPM_DB_URL), dpmProps.getProperty(Constants.DPM_DB_USER_NAME),dpmProps.getProperty(Constants.DPM_DB_PASSWORD));
	    	if(tempWageHistSum!=null && tempWageHistSum.next()){
	    		DPMMonitorBean.setWageFromWageHistoryTemp(tempWageHistSum.getDouble(1));
	    		returnString=returnString.concat(monitorProps.getProperty(Constants.SEN_DPM_TEMP_WH_SUM)+Constants.STRING_7_SPACES+Constants.STRING_COLON+Constants.STRING_SPACE+tempWageHistSum.getDouble(1));
	    		returnString=returnString.concat(Constants.NEW_LINE);
			}
	    	ResultSet inputWageSum = dpmMethods.execSelQry(monitorProps.getProperty(Constants.QRY_DPM_INPUTS_SUM), dpmProps.getProperty(Constants.DPM_DB_DRIVER_URL), dpmProps.getProperty(Constants.DPM_DB_URL), dpmProps.getProperty(Constants.DPM_DB_USER_NAME),dpmProps.getProperty(Constants.DPM_DB_PASSWORD));
	    	if(inputWageSum!=null && inputWageSum.next()){
	    		DPMMonitorBean.setWageFromInputs(inputWageSum.getDouble(1));
	    		returnString=returnString.concat(monitorProps.getProperty(Constants.SEN_DPM_INPUTS_SUM)+Constants.STRING_13_SPACES+Constants.STRING_COLON+Constants.STRING_SPACE+inputWageSum.getDouble(1));
	    		returnString=returnString.concat(Constants.NEW_LINE);
	    	}
	    	logger.debug("Exiting Method getPayrollSum() in "+getClass().getName());
	    	return returnString;
		}catch(Exception e){
			logger.error("Exception in getPayrollSum:",e);
			return null;
		}
	}
	/**
	 * This method gets the sum of wage in the payroll file (given in the last column of the last row)
	 * @author Anil_Sehgal01
	 * @return String - results appended in a defined format
	 * @param inputList - contents of the payroll file as String elements of a List
	 */
	public double sumOfWageInFile(List inputList){
		try{
			logger.debug("Entering Method sumOfWageInFile() in "+getClass().getName());
			String lastLine = (String) inputList.get(inputList.size()-1);
			Double totalWageObj = new Double(lastLine.toString().substring(47).trim());
			double totalWage = totalWageObj.doubleValue();
			logger.debug("Exiting Method sumOfWageInFile() in "+getClass().getName());
			logger.info("sumOfWageInFile() returned: "+totalWage);
			DPMMonitorBean.setWageFromFile(totalWage);
			return totalWage;
		}catch(Exception e){
			logger.error("Exception in sumOfWageInFile:",e);
			return -1;
		}
	}
	/**
	 * This method gets the sum of wage in the payroll file (given in the last column of the last row)
	 * @author Anil_Sehgal01
	 * @return String - results appended in a defined format along with the pay period end date and the current time
	 * @param contentList - result of the status query with columns saparated by ":"
	 */
	public String getPayrollStatusMailString(List contentList){
		try{
			logger.debug("Entering Method getPayrollStatusMailString() in "+getClass().getName());
			String statusValue = null;
			String statusName = null;
			String rowComb = Constants.EMPTY_STRING;
			Date currentDate = new Date();
			ResultSet payPdEndDate = dpmMethods.execSelQry(monitorProps.getProperty(Constants.QRY_DPM_MAX_PAYPD_DATE),dpmProps.getProperty(Constants.DPM_DB_DRIVER_URL),dpmProps.getProperty(Constants.DPM_DB_URL),dpmProps.getProperty(Constants.DPM_DB_USER_NAME),dpmProps.getProperty(Constants.DPM_DB_PASSWORD));
			if(payPdEndDate!=null && payPdEndDate.next()){
				rowComb = rowComb+Constants.PAYWEEK_END_DATE+payPdEndDate.getDate(1)+Constants.NEW_LINE;
			}	
			rowComb = rowComb+Constants.CURRENT_TIME_STR+currentDate.getHours()+Constants.STRING_COLON+currentDate.getMinutes()+Constants.NEW_LINE+Constants.NEW_LINE;
			rowComb = rowComb+Constants.PAYROLL_STATUS_STR.concat(Constants.NEW_LINE);
			String spaces = Constants.EMPTY_STRING;
			for(int i=0;i<contentList.size();i++){
				int endIndex = contentList.get(i).toString().indexOf(Constants.STRING_COLON);
				statusName = contentList.get(i).toString().substring(0, endIndex);
				statusValue = contentList.get(i).toString().substring(endIndex+1, contentList.get(i).toString().length());
				if(statusValue.length()==1){
					statusValue=Constants.STRING_SPACE.concat(statusValue);
				}
				if(statusName.equals(Constants.STATUS_APPROVED)){
					spaces = Constants.STRING_6_SPACES;
				}else if(statusName.equals(Constants.STATUS_IN_PROGRESS)){
					spaces = Constants.STRING_3_SPACES;
				}else if(statusName.equals(Constants.STATUS_CLOSED)){
					spaces = Constants.STRING_8_SPACES;
				}
				rowComb=rowComb.concat(statusName).concat(Constants.STRING_SPACE).concat(monitorProps.getProperty(Constants.STATUS_STRING)).concat(spaces).concat(Constants.STRING_COLON).concat(Constants.STRING_SPACE).concat(statusValue).concat(Constants.NEW_LINE);
			}
			logger.debug("Exiting Method getPayrollStatusMailString() in "+getClass().getName());
			return rowComb;
		}catch(Exception e){
			logger.error("Exception in getPayrollStatusMailString:s",e);
			return null;
		}
	}
}