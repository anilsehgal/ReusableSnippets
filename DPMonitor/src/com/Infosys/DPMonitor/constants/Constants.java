package com.Infosys.DPMonitor.constants;
/**
 * @author Anil_Sehgal01
 * Class where all constants and property variables are defined
 */
public class Constants {
	public static final String CMD_UNIX_FILE_SEARCH = "cmdSearchUnixFile";
	public static final String CMD_UNIX_FILE_CONTENTS = "cmdGetUnixFileContents";
	public static final String QRY_DPM_JOB_STATUS = "qryGetDPMJobStatus";
	public static final String QRY_DPM_WAGE_SUM = "qryGetWageSum";
	public static final String QRY_DPM_MAX_PAYPD_DATE = "qryGetMaxPayPeriodDate";  
	public static final String QRY_DPM_JOB_STATUS_CLOSED_INPROGRESS = "qryGetSumClosedInProgTCs";
	public static final String JDBC_URL = "databaseUrl";
	public static final String JDBC_USER_NAME = "databaseUserName";
    public static final String JDBC_PASSWORD = "databasePassword";
    public static final String JDBC_DRIVER_URL = "jdbcDriverUrl";
    public static final String LOG_CONFIG_FILE = "/etlapps/dpm/81/dev/JavaBatch/Lib/DPM/conf/log4j.xml";
    public static final String MAIL_SMTP_HOST = "mail.smtp.host";
    public static final String MAIL_SMTP_PORT = "mail.smtp.port";
    public static final String SMTP_HOST_NAME = "smtpHost";
    public static final String SMTP_PORT_NAME = "smtpPort";
    public static final String PROPERTIES_PATH = "/conf/dpmMonitor.properties";
    public static final String DPM_PROPERTIES_PATH = "/conf/email.properties";
    public static final String UNIX_CMD_LOG_SUCCESS_MSG = "unixSuccessMessage";
    public static final String UNIX_CMD_LOG_FAIL_MSG = "unixFailureMessage";
    public static final String MAIL_LOG_MSG_BEFORE_SEND = "mailLogBeforeSend";
    public static final String MAIL_LOG_MSG_AFTER_SEND = "mailLogAfterSend";
    public static final String MAIL_TO = "mailTo";
    public static final String MAIL_TO_DL = "mailToDL";
    public static final String MAIL_FROM = "mailFrom";
    public static final String DATA_SEP = ";";
    public static final int NUMBER_25 = 25;
    public static final int NUMBER_2 = 2;
    public static final int NUMBER_20 = 20;
    public static final int NUMBER_7 = 7;
    public static final int NUMBER_15 = 15;
    public static final String STRING_SPACE = " ";
    public static final String EMPTY_STRING = "";
    public static final String TOTAL_TRAFFIC_CENTERS = "totalTrafficCenters";
	public static final String DPM_DB_URL = "DBURL";
	public static final String DPM_DB_USER_NAME = "USERNAME";
    public static final String DPM_DB_PASSWORD = "USERPWD";
    public static final String DPM_DB_DRIVER_URL = "DRIVERNAME";
    public static final String DPM_MONITOR_JOB_START_MSG = "startMsg";
    public static final String DPM_WAIT_TIME_CALL1 = "waitTime1";
    public static final String DPM_WAIT_TIME_CALL2 = "waitTime2";
    public static final String DPM_WAIT_TIME_CALL3 = "waitTime3";
    public static final String PAYROLL_FILE_TC_BEGIN_INDEX = "tcBeginIndex";
    public static final String PAYROLL_FILE_TC_END_INDEX = "tcEndIndex";
    public static final String STATUS_STRING = "statusString";
    public static final String STATUS = "status";
    public static final String STRING_COLON = ":";
    public static final String STRING_6_SPACES = "      ";
    public static final String STRING_3_SPACES = "   ";
    public static final String STRING_8_SPACES = "        ";
    public static final String STRING_7_SPACES = "       ";
    public static final String STRING_13_SPACES = "             ";
    public static final String STRING_14_SPACES = "              ";
    public static final String NEW_LINE = "\n";
    public static final int NUMBER_19 = 19;
    public static final String MAIL_SUBJECT = "mailSubject";
    public static final String SEN_SUM_IN_PAYROLL_FILE = "senSumInPayrollFile";
    public static final String PAYROLL_FILE_PRESENT = "payrollFilePresent";
    public static final String PAYROLL_FILE_ABSENT = "payrollFileAbsent";
    public static final String PAYROLL_FILE_ERROR_STREAM = "payrollFileError";
    public static final String TC_MSG_STRING = "tcMsgStr";
    public static final String CMD_CRITICAL_DATE_CHECK = "cmdCriticalDateChk";
    public static final String QRY_DPM_TEMP_WAGE_HISTORY_SUM = "qryTempWageHistSum";
    public static final String QRY_DPM_INPUTS_SUM = "qryInputSum";
    public static final String SEN_DPM_TEMP_WH_SUM = "amtTempWageHist";
    public static final String SEN_DPM_INPUTS_SUM = "amtInputs";
    public static final String SEN_FILE_TIME_STAMP = "senFlTmStamp";
    public static final String PAYROLL_FILE_NAME = "payrollFileName";
    public static final String CRITICAL_FILE_NAME = "criticalFile";
    public static final String CST = " CST";
    public static final String SIMPLE_DATE_FORMAT = "MMM dd HH:mm:ss yyyy";
    public static final String DAY_BEGIN_TIME = "00:00:00";
    public static final String PAYWEEK_END_DATE = "Payweek End Date - ";
    public static final String CURRENT_TIME_STR = "Time - ";
    public static final String PAYROLL_STATUS_STR = "Payroll Status:";
    public static final String STATUS_APPROVED = "Approved";
    public static final String STATUS_IN_PROGRESS = "In Progress";
    public static final String STATUS_CLOSED = "Closed";
    public static final String SEN_TC_IN_FILE_NA = "Number of Traffic Centers in Payroll File : N/A";
    public static final String SEN_AMT_IN_FILE_NA = "Total Amount in Payroll File              : N/A";
    public static final String SEN_ERROR_MAIL_SUBJECT = "senErrorMailSubject";
    public static final String SEN_DIFF_IN_AMT = "Difference in Amount                      : ";
    public static final String SEN_DIFF_OK_STR = "(It is ok to have some difference in amount)";
    public static final String SEN_PR_DATA_SNAP = "Payroll Data Snapshot:";
    public static final String ZERO_STRING = "0";
    public static final String PAYROLL_FILE_MISS="Payroll File not generated or missing traffic Center in Payroll file. Please contact Fleet BLT ASAP.";
}
