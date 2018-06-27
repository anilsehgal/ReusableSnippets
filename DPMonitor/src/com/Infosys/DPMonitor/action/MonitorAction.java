package com.Infosys.DPMonitor.action;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.Infosys.DPMonitor.Impl.DPMMethods;
import com.Infosys.DPMonitor.Impl.GenericMethods;
import com.Infosys.DPMonitor.bean.DPMMonitorBean;
import com.Infosys.DPMonitor.constants.Constants;

public class MonitorAction {
	static String mailContent;
	static Logger logger = Logger.getLogger("MonitorAction.class");
	static Properties mainProps = new Properties();
	static Properties dpmProps = new Properties();
	static String mailSubject;
	/**
	 * This is the main method which calls all the methods as per requirements. This method is divided in four calls
	 * @param args
	 * @throws Exception 
	 */
    public static void main(String[] args) throws Exception{
		
		List payrollStatusList = new ArrayList();
		List payrollStatusList2 = new ArrayList();
		List payrollStatusList3 = new ArrayList();
		List payrollStatusList4 = new ArrayList();
		GenericMethods dpmActionMethods = new GenericMethods(Constants.PROPERTIES_PATH);
		DPMMethods dpmMethods = new DPMMethods();
		try{
			DOMConfigurator.configure(Constants.LOG_CONFIG_FILE);
			mainProps.load(MonitorAction.class.getClass().getResource(Constants.PROPERTIES_PATH).openStream());
			dpmProps.load(MonitorAction.class.getClass().getResource(Constants.DPM_PROPERTIES_PATH).openStream());
			logger.info(mainProps.getProperty(Constants.DPM_MONITOR_JOB_START_MSG));
			logger.debug("Entering main() in MonitorAction");
			Class.forName(dpmProps.getProperty(Constants.DPM_DB_DRIVER_URL));
			DriverManager.registerDriver(new com.microsoft.jdbc.sqlserver.SQLServerDriver());
			/* 
			 * Check the size of Cut off status file
			 * if the size of file is greater than zero, continue with call1 else terminate
			 * after logging appropriate message
			 */
			long criticalFlSize = dpmActionMethods.getfileSize(mainProps.getProperty(Constants.CRITICAL_FILE_NAME));		
			if(criticalFlSize==0){
				logger.info("Today is not the cut off date");
				System.exit(0);
			}else if(criticalFlSize==-1){
				logger.fatal("CutOffStatus.out not found");
			    System.exit(1);        	
			}
		}catch(Exception e){
			logger.error("Exception in Main() Initialization :",e);
			System.exit(1);
		}
		/******************************CALL 1**********************************************/
		/* CALL 1:
		 * Execute Query to get the Payroll Status
		 * Send Mail with the status
		 */
		try{
			ResultSet payrollStatusResult = dpmActionMethods.execSelQry(mainProps.getProperty(Constants.QRY_DPM_JOB_STATUS), dpmProps.getProperty(Constants.DPM_DB_DRIVER_URL), dpmProps.getProperty(Constants.DPM_DB_URL), dpmProps.getProperty(Constants.DPM_DB_USER_NAME),dpmProps.getProperty(Constants.DPM_DB_PASSWORD));		
			String mailCont=Constants.EMPTY_STRING;;				
			while(payrollStatusResult.next()){
			      payrollStatusList.add(payrollStatusResult.getString(1).concat(Constants.STRING_COLON).concat(payrollStatusResult.getInt(2)+""));
			      logger.info(mainProps.getProperty(Constants.STATUS_STRING).concat(Constants.STRING_SPACE).concat(payrollStatusResult.getString(1)).concat(Constants.STRING_SPACE).concat(Constants.STATUS).concat(Constants.STRING_COLON).concat(Constants.STRING_SPACE)+payrollStatusResult.getInt(2));
			}
			mailCont=dpmMethods.getPayrollStatusMailString(payrollStatusList);
			dpmActionMethods.sendMail(mainProps.getProperty(Constants.MAIL_FROM), mainProps.getProperty(Constants.MAIL_TO), mainProps.getProperty(Constants.MAIL_SUBJECT), mailCont,mainProps.getProperty(Constants.SMTP_HOST_NAME),mainProps.getProperty(Constants.SMTP_PORT_NAME));
			mailContent=Constants.EMPTY_STRING;
			if(payrollStatusResult!=null){
				payrollStatusResult.close();
			}
		}catch(Exception e){
			logger.error("Exception in Main() CALL1 :",e);
			System.exit(1);
		}
			
		/******************************WAIT***********************************************/
		
		dpmActionMethods.haltThread(mainProps.getProperty(Constants.DPM_WAIT_TIME_CALL1));
		
		/*****************************CALL 2**********************************************/
		/* CALL 2:
		 * Execute Query to get the Payroll Status
		 * Send Mail with the status to Support Team if no TC has Payroll Status as 'Open'
		 * else send a mail with status to 'DL - FLEET'
		 */
		try{
			ResultSet payrollStatusResult2 = dpmActionMethods.execSelQry(mainProps.getProperty(Constants.QRY_DPM_JOB_STATUS), dpmProps.getProperty(Constants.DPM_DB_DRIVER_URL), dpmProps.getProperty(Constants.DPM_DB_URL), dpmProps.getProperty(Constants.DPM_DB_USER_NAME),dpmProps.getProperty(Constants.DPM_DB_PASSWORD));
			while(payrollStatusResult2.next()){
			      payrollStatusList2.add(payrollStatusResult2.getString(1).concat(Constants.STRING_COLON).concat(payrollStatusResult2.getInt(2)+Constants.EMPTY_STRING));
			      logger.info(mainProps.getProperty(Constants.STATUS_STRING).concat(Constants.STRING_SPACE).concat(payrollStatusResult2.getString(1)).concat(Constants.STRING_SPACE).concat(Constants.STATUS).concat(Constants.STRING_COLON).concat(Constants.STRING_SPACE)+payrollStatusResult2.getInt(2));
			}
			mailContent=dpmMethods.getPayrollStatusMailString(payrollStatusList2);
			ResultSet payrollStatusResultTotal = dpmActionMethods.execSelQry(mainProps.getProperty(Constants.QRY_DPM_JOB_STATUS_CLOSED_INPROGRESS), dpmProps.getProperty(Constants.DPM_DB_DRIVER_URL), dpmProps.getProperty(Constants.DPM_DB_URL), dpmProps.getProperty(Constants.DPM_DB_USER_NAME),dpmProps.getProperty(Constants.DPM_DB_PASSWORD));
			String total = Constants.ZERO_STRING;
			if(payrollStatusResultTotal!=null && payrollStatusResultTotal.next()){        
			      total=payrollStatusResultTotal.getInt(1)+Constants.EMPTY_STRING;
			    }
			if(total.equals(mainProps.getProperty(Constants.TOTAL_TRAFFIC_CENTERS))){
			    dpmActionMethods.sendMail(mainProps.getProperty(Constants.MAIL_FROM), mainProps.getProperty(Constants.MAIL_TO), mainProps.getProperty(Constants.MAIL_SUBJECT), mailContent,mainProps.getProperty(Constants.SMTP_HOST_NAME),mainProps.getProperty(Constants.SMTP_PORT_NAME));
			}else{
			    dpmActionMethods.sendMail(mainProps.getProperty(Constants.MAIL_FROM), mainProps.getProperty(Constants.MAIL_TO_DL), mainProps.getProperty(Constants.MAIL_SUBJECT), mailContent,mainProps.getProperty(Constants.SMTP_HOST_NAME),mainProps.getProperty(Constants.SMTP_PORT_NAME));
			}
			mailContent=Constants.EMPTY_STRING;
			if(payrollStatusResult2!=null){
				payrollStatusResult2.close();
			}
		}catch(Exception e){
			logger.error("Exception in Main() CALL2 :",e);
			System.exit(1);
		}
		/******************************WAIT***********************************************/
		
		dpmActionMethods.haltThread(mainProps.getProperty(Constants.DPM_WAIT_TIME_CALL2));
		
		/*****************************CALL 3**********************************************/
		/* CALL 3:
		 * Execute Query to get the Payroll Status
		 * Check if Payroll file is present and has a timestamp of current date
		 * -if yes, add the sum of wage in payroll file to mail content
		 * -if no, append N/A for file data to mail content
		 * Append the sum of wage for the current open payweek from tempwagehistory and Input data in mail content
		 * if payroll file is present, append the difference <FA+I> - <PayrollFile> to the mail content
		 * send a mail with status and file status to DL FLEET
		 */
		try{
			ResultSet payrollStatusResult3 = dpmActionMethods.execSelQry(mainProps.getProperty(Constants.QRY_DPM_JOB_STATUS), dpmProps.getProperty(Constants.DPM_DB_DRIVER_URL), dpmProps.getProperty(Constants.DPM_DB_URL), dpmProps.getProperty(Constants.DPM_DB_USER_NAME),dpmProps.getProperty(Constants.DPM_DB_PASSWORD));
			while(payrollStatusResult3.next()){
			      payrollStatusList3.add(payrollStatusResult3.getString(1).concat(Constants.STRING_COLON).concat(payrollStatusResult3.getInt(2)+""));              logger.info(mainProps.getProperty(Constants.STATUS_STRING).concat(Constants.STRING_SPACE).concat(payrollStatusResult3.getString(1)).concat(Constants.STRING_SPACE).concat(Constants.STATUS).concat(Constants.STRING_COLON).concat(Constants.STRING_SPACE)+payrollStatusResult3.getInt(2));
			}
			mailContent=dpmMethods.getPayrollStatusMailString(payrollStatusList3)+Constants.NEW_LINE;
			boolean isPayrollFilePresent = false;
			try{			
				isPayrollFilePresent = dpmMethods.isPayrollFilePresent();
			}catch(Exception e){
				logger.error("Exception in Main() CALL3 :",e);
				isPayrollFilePresent = false;
			}
			mailContent=mailContent+Constants.SEN_PR_DATA_SNAP;
			if(isPayrollFilePresent){
			    String fileData = dpmMethods.runPayrollMethods();
			    mailContent=mailContent.concat(Constants.NEW_LINE);
			    mailContent=mailContent.concat(fileData);
			    logger.info(fileData);
			}else{
				mailContent=mailContent.concat(Constants.NEW_LINE);
				mailContent=mailContent+Constants.SEN_TC_IN_FILE_NA+Constants.NEW_LINE;
				mailContent=mailContent+Constants.SEN_AMT_IN_FILE_NA+Constants.NEW_LINE;  	
			    logger.info(mainProps.getProperty(Constants.PAYROLL_FILE_ABSENT));
			}
			mailContent=mailContent.concat(dpmMethods.getPayrollSum());
			if(isPayrollFilePresent){
				double diff = DPMMonitorBean.getWageFromInputs()+DPMMonitorBean.getWageFromWageHistoryTemp()-DPMMonitorBean.getWageFromFile();
				mailContent=mailContent+Constants.SEN_DIFF_IN_AMT+diff+Constants.STRING_SPACE+Constants.SEN_DIFF_OK_STR;
			}
			dpmActionMethods.sendMail(mainProps.getProperty(Constants.MAIL_FROM), mainProps.getProperty(Constants.MAIL_TO), mainProps.getProperty(Constants.MAIL_SUBJECT), mailContent,mainProps.getProperty(Constants.SMTP_HOST_NAME),mainProps.getProperty(Constants.SMTP_PORT_NAME));
			mailContent=Constants.EMPTY_STRING;
			if(payrollStatusResult3!=null){
				payrollStatusResult3.close();
			}
		}catch(Exception e){
			logger.error("Exception in Main() CALL3 :",e);
			System.exit(1);
		}
		/******************************WAIT***********************************************/
		
		dpmActionMethods.haltThread((String)mainProps.getProperty(Constants.DPM_WAIT_TIME_CALL3));
		
		/*****************************CALL 4**********************************************/
		/* CALL 4:
		 * Execute Query to get the Payroll Status
		 * Check if Payroll file is present and has a timestamp of current date
		 * -if yes, add the sum of wage in payroll file to mail content
		 * -if no, append N/A for file data to mail content, change the mail Subject accordingly
		 * Append the sum of wage for the current open payweek from tempwagehistory and Input data in mail content
		 * if payroll file is present, append the difference <FA+I> - <PayrollFile> to the mail content
		 * send a mail with status and file status to DL FLEET
		 */
		try{
			ResultSet payrollStatusResult4 = dpmActionMethods.execSelQry(mainProps.getProperty(Constants.QRY_DPM_JOB_STATUS), dpmProps.getProperty(Constants.DPM_DB_DRIVER_URL), dpmProps.getProperty(Constants.DPM_DB_URL), dpmProps.getProperty(Constants.DPM_DB_USER_NAME),dpmProps.getProperty(Constants.DPM_DB_PASSWORD));
//Payroll File not generated or missing traffic Center in Payroll file. Please contact Fleet BLT ASAP.
			boolean isPayrollFilePresent2 = false;
			try{
				isPayrollFilePresent2 = dpmMethods.isPayrollFilePresent();
			}catch(Exception e){
				logger.error("Exception in Main() CALL4 :",e);
				isPayrollFilePresent2 = false;
			}
			if(isPayrollFilePresent2){
				while(payrollStatusResult4.next()){
				      payrollStatusList4.add(payrollStatusResult4.getString(1).concat(Constants.STRING_COLON).concat(payrollStatusResult4.getInt(2)+""));
				      logger.info(mainProps.getProperty(Constants.STATUS_STRING).concat(Constants.STRING_SPACE).concat(payrollStatusResult4.getString(1)).concat(Constants.STRING_SPACE).concat(Constants.STATUS).concat(Constants.STRING_COLON).concat(Constants.STRING_SPACE)+payrollStatusResult4.getInt(2));
				}
			mailContent=dpmMethods.getPayrollStatusMailString(payrollStatusList4)+Constants.NEW_LINE;
			}else{
				mailContent=mailContent+Constants.PAYROLL_FILE_MISS+Constants.NEW_LINE+Constants.NEW_LINE;
			}
			mailContent=mailContent+Constants.SEN_PR_DATA_SNAP;
			
			String mailSubject = Constants.EMPTY_STRING;;
			if(isPayrollFilePresent2){
			    String fileData = dpmMethods.runPayrollMethods();
			    mailContent=mailContent.concat(Constants.NEW_LINE);
			    mailContent=mailContent.concat(fileData);
			    logger.info(fileData);
			    mailSubject=mainProps.getProperty(Constants.MAIL_SUBJECT);
			}else{
				mailContent=mailContent.concat(Constants.NEW_LINE);
				mailContent=mailContent+Constants.SEN_TC_IN_FILE_NA+Constants.NEW_LINE;
				mailContent=mailContent+Constants.SEN_AMT_IN_FILE_NA+Constants.NEW_LINE; 
				logger.info(mainProps.getProperty(Constants.PAYROLL_FILE_ABSENT));
				mailSubject=mainProps.getProperty(Constants.SEN_ERROR_MAIL_SUBJECT);
			}
			mailContent=mailContent.concat(dpmMethods.getPayrollSum());
			if(isPayrollFilePresent2){
				double diff2 = DPMMonitorBean.getWageFromInputs()+DPMMonitorBean.getWageFromWageHistoryTemp()-DPMMonitorBean.getWageFromFile();
				mailContent=mailContent+Constants.SEN_DIFF_IN_AMT+diff2+Constants.STRING_SPACE+Constants.SEN_DIFF_OK_STR;
			}
			dpmActionMethods.sendMail(mainProps.getProperty(Constants.MAIL_FROM), mainProps.getProperty(Constants.MAIL_TO_DL), mailSubject, mailContent,mainProps.getProperty(Constants.SMTP_HOST_NAME),mainProps.getProperty(Constants.SMTP_PORT_NAME));
			mailContent=Constants.EMPTY_STRING;
			if(payrollStatusResult4!=null){
				payrollStatusResult4.close();
			}
		}catch(Exception e){
			logger.error("Exception in Main() CALL4 :",e);
			System.exit(1);
		}
	logger.debug("Exiting main() in MonitorAction");
	System.exit(0);
	}
}


 
