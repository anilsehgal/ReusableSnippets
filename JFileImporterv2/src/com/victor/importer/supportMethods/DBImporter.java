package com.victor.importer.supportMethods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.victor.importer.constants.DBTableColumnBean;

public class DBImporter{
	static Logger logger = Logger.getLogger(DBImporter.class.getName());
	static{
		DOMConfigurator.configure(DBImporter.class.getResource("/config/log4j.xml"));	
	}
	public static Connection connection;
	public static void connectToDatabase(String driverUrl, String dbUrl, String userName, String password){
		try{
			Class.forName(driverUrl);
			connection =  DriverManager.getConnection(dbUrl,userName,password);
			System.out.println("created connection to "+dbUrl);
		}catch(Exception e){
			logger.error("Exception in getting connection: ", e);
		}
	}
	public static String importRow(String updateQuery, String exceptionSetting) throws Exception{
		
		int rowsAffected = 0;
		String returnString = "success";
		Statement statement = null;
		try{		
			statement = connection.createStatement();
			logger.info("Firing update Query: "+updateQuery);
			rowsAffected = statement.executeUpdate(updateQuery);
			statement.close();
			if(rowsAffected==0 && exceptionSetting.equals("Fire Second")){
				returnString = "Fire Second";
				logger.info("No Rows Affected in firing: "+updateQuery);
			}
			logger.info("Fired. Returned: "+returnString);
		}catch(Exception e){
			logger.error("Exception in Importing Row: ",e);
			if(exceptionSetting.equals("Stop Import")){
				throw new Exception(e);
			}else if(exceptionSetting.equals("Reject Row")){
				returnString = "Reject Row";
			}else if(exceptionSetting.equals("Fire Second") && rowsAffected==0){				
				returnString = "Fire Second";
			}
		}
		return returnString;
	}
	public static void closeDatabaseConnection(){
		if(connection != null){
			try{
				if(!connection.isClosed()){
					System.out.println("closing connection");
					connection.close();
				}
			}catch(Exception e){
				logger.error("Exception in closing connection: ",e);
			}
		}
	}
	public static String createInsertQuery(String tableName, List<DBTableColumnBean> columnsToBeInserted, Object[] values, String dateFormat,String driverUrl, boolean truncateOverflowingValues){
		
		
		String insertQuery = "insert into "+tableName+"(";
		
		for(DBTableColumnBean column : columnsToBeInserted){
			if(driverUrl.equalsIgnoreCase("com.microsoft.jdbc.sqlserver.SQLServerDriver")){
				insertQuery = insertQuery + "["+column.getColumnName()+"]" +",";
			}else{
				insertQuery = insertQuery + column.getColumnName() +",";
			}
		}
		insertQuery = insertQuery.substring(0, insertQuery.lastIndexOf(","));
		insertQuery = insertQuery + ") values (";
		
		for(DBTableColumnBean column : columnsToBeInserted){
			
			if(column.getDataType().contains(ColumnDataTypes.ORACLE_CHAR)){
				if(truncateOverflowingValues){
					if(column.getCharacterMaximumLength() > 0 && values[Integer.parseInt(column.getFileColumn())].toString().length() > column.getCharacterMaximumLength()){
						logger.debug("ORACLE STRING: "+values[Integer.parseInt(column.getFileColumn())].toString()+"["+values[Integer.parseInt(column.getFileColumn())].toString().length()+"]"+" Col Max Length: "+column.getCharacterMaximumLength());
						insertQuery = insertQuery + "'"+values[Integer.parseInt(column.getFileColumn())].toString().substring(0, column.getCharacterMaximumLength()-1).replaceAll("'", "CHR(39)")+"'" +",";
						logger.debug("Output: "+insertQuery);
					}else{
						insertQuery = insertQuery + "'"+values[Integer.parseInt(column.getFileColumn())].toString().replaceAll("'", "CHR(39)")+"'" +",";
					}
				}else{	
					insertQuery = insertQuery + "'"+values[Integer.parseInt(column.getFileColumn())].toString().replaceAll("'", "CHR(39)")+"'" +",";
				}
			}else if(column.getDataType().contains(ColumnDataTypes.SQL_SERVER_CHAR) || column.getDataType().contains(ColumnDataTypes.SQL_SERVER_TIME) && !column.getDataType().contains(ColumnDataTypes.SQL_SERVER_DATE)){			
				if(truncateOverflowingValues){
					if(column.getCharacterMaximumLength() > 0 && values[Integer.parseInt(column.getFileColumn())].toString().length() > column.getCharacterMaximumLength()){
						insertQuery = insertQuery + "'"+values[Integer.parseInt(column.getFileColumn())].toString().substring(0,column.getCharacterMaximumLength()-1).replaceAll("'", "''")+"'" +",";
					}else{
						insertQuery = insertQuery + "'"+values[Integer.parseInt(column.getFileColumn())].toString().replaceAll("'", "''")+"'" +",";
					}					
				}else{	
					insertQuery = insertQuery + "'"+values[Integer.parseInt(column.getFileColumn())].toString().replaceAll("'", "''")+"'" +",";
				}
			}else if(column.getDataType().contains(ColumnDataTypes.ORACLE_DATE)){
				insertQuery = insertQuery + "to_date('"+values[Integer.parseInt(column.getFileColumn())]+"','"+getOracleDateFormat(dateFormat)+"'),";
			}
			else if(column.getDataType().contains(ColumnDataTypes.SQL_SERVER_DATE)){
				String date = values[Integer.parseInt(column.getFileColumn())].toString();
				Date corDate = null;
				try {
					corDate = (new SimpleDateFormat(dateFormat)).parse(date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				java.sql.Timestamp sqldate = new java.sql.Timestamp(corDate.getTime());
				insertQuery = insertQuery + "'"+sqldate+"',";
			}else{
				insertQuery = insertQuery + values[Integer.parseInt(column.getFileColumn())] +",";
			}
		}	
		insertQuery = insertQuery.substring(0, insertQuery.lastIndexOf(",")) + ")";
		return insertQuery;
	}



	public static String createUpdateQuery(String tableName, List<DBTableColumnBean> columnsToBeInserted, Object[] values, String dateFormat,String driverUrl, boolean truncateOverflowingValues){
		
		String updateQuery = "update "+tableName+" set ";
		String toBeSet = "", inWhere = "";
		for(DBTableColumnBean column : columnsToBeInserted){
			if(column.getPrimaryKey()==null || column.getPrimaryKey().equals("null")){
				if(driverUrl.equalsIgnoreCase("com.microsoft.jdbc.sqlserver.SQLServerDriver")){
					if(column.getDataType().contains(ColumnDataTypes.ORACLE_CHAR)){
						if(truncateOverflowingValues){
							if(column.getCharacterMaximumLength() > 0 && values[Integer.parseInt(column.getFileColumn())].toString().length() > column.getCharacterMaximumLength()){
								toBeSet = toBeSet +"["+column.getColumnName()+"]"+" = " + "'"+values[Integer.parseInt(column.getFileColumn())].toString().substring(0, column.getCharacterMaximumLength()-1).replaceAll("'", "CHR(39)")+"'" +",";
							}else{
								toBeSet = toBeSet +"["+column.getColumnName()+"]"+" = " + "'"+values[Integer.parseInt(column.getFileColumn())].toString().replaceAll("'", "CHR(39)")+"'" +",";
							}
						}else{
							toBeSet = toBeSet +"["+column.getColumnName()+"]"+" = " + "'"+values[Integer.parseInt(column.getFileColumn())].toString().replaceAll("'", "CHR(39)")+"'" +",";
						}
					}else if(column.getDataType().contains(ColumnDataTypes.SQL_SERVER_CHAR) || column.getDataType().contains(ColumnDataTypes.SQL_SERVER_TIME) && !column.getDataType().contains(ColumnDataTypes.SQL_SERVER_DATE)){
						if(truncateOverflowingValues){
							if(column.getCharacterMaximumLength() > 0 && values[Integer.parseInt(column.getFileColumn())].toString().length() > column.getCharacterMaximumLength()){
								toBeSet = toBeSet +"["+column.getColumnName()+"]"+" = " + "'"+values[Integer.parseInt(column.getFileColumn())].toString().substring(0, column.getCharacterMaximumLength()-1).replaceAll("'", "''")+"'" +",";
							}else{
								toBeSet = toBeSet +"["+column.getColumnName()+"]"+" = " + "'"+values[Integer.parseInt(column.getFileColumn())].toString().replaceAll("'", "''")+"'" +",";
							}
						}else{
							toBeSet = toBeSet +"["+column.getColumnName()+"]"+" = " + "'"+values[Integer.parseInt(column.getFileColumn())].toString().replaceAll("'", "''")+"'" +",";
						}
					}else if(column.getDataType().contains(ColumnDataTypes.ORACLE_DATE)){
						toBeSet = toBeSet +"["+column.getColumnName()+"]"+" = "+ "to_date('"+values[Integer.parseInt(column.getFileColumn())]+"','"+getOracleDateFormat(dateFormat)+"'),";
					}
					else if(column.getDataType().contains(ColumnDataTypes.SQL_SERVER_DATE)){
						toBeSet = toBeSet +"["+column.getColumnName()+"]"+" = "+ "'"+values[Integer.parseInt(column.getFileColumn())]+"',";
					}else{
						toBeSet = toBeSet +"["+column.getColumnName()+"]"+" = "+ values[Integer.parseInt(column.getFileColumn())] +",";
					}
				}else{
					if(column.getDataType().contains(ColumnDataTypes.ORACLE_CHAR)){
						if(truncateOverflowingValues){
							if(column.getCharacterMaximumLength() > 0 && values[Integer.parseInt(column.getFileColumn())].toString().length() > column.getCharacterMaximumLength()){
								toBeSet = toBeSet +column.getColumnName()+" = " + "'"+values[Integer.parseInt(column.getFileColumn())].toString().substring(0, column.getCharacterMaximumLength()-1).replaceAll("'", "CHR(39)")+"'" +",";
							}else{
								toBeSet = toBeSet +column.getColumnName()+" = " + "'"+values[Integer.parseInt(column.getFileColumn())].toString().replaceAll("'", "CHR(39)")+"'" +",";
							}
						}else{
							toBeSet = toBeSet +column.getColumnName()+" = " + "'"+values[Integer.parseInt(column.getFileColumn())].toString().replaceAll("'", "CHR(39)")+"'" +",";
						}
					}else if(column.getDataType().contains(ColumnDataTypes.SQL_SERVER_CHAR) || column.getDataType().contains(ColumnDataTypes.SQL_SERVER_TIME) && !column.getDataType().contains(ColumnDataTypes.SQL_SERVER_DATE)){
						if(truncateOverflowingValues){
							if(column.getCharacterMaximumLength() > 0 && values[Integer.parseInt(column.getFileColumn())].toString().length() > column.getCharacterMaximumLength()){
								toBeSet = toBeSet +column.getColumnName()+" = " + "'"+values[Integer.parseInt(column.getFileColumn())].toString().substring(0, column.getCharacterMaximumLength()-1).replaceAll("'", "''")+"'" +",";
							}else{
								toBeSet = toBeSet +column.getColumnName()+" = " + "'"+values[Integer.parseInt(column.getFileColumn())].toString().replaceAll("'", "''")+"'" +",";
							}
						}else{
							toBeSet = toBeSet +column.getColumnName()+" = " + "'"+values[Integer.parseInt(column.getFileColumn())].toString().replaceAll("'", "''")+"'" +",";
						}
					}else if(column.getDataType().contains(ColumnDataTypes.ORACLE_DATE)){
						toBeSet = toBeSet +column.getColumnName()+" = "+ "to_date('"+values[Integer.parseInt(column.getFileColumn())]+"','"+getOracleDateFormat(dateFormat)+"'),";
					}
					else if(column.getDataType().contains(ColumnDataTypes.SQL_SERVER_DATE)){
						toBeSet = toBeSet +column.getColumnName()+" = "+ "'"+values[Integer.parseInt(column.getFileColumn())]+"',";
					}else{
						toBeSet = toBeSet +column.getColumnName()+" = "+ values[Integer.parseInt(column.getFileColumn())] +",";
					}
				}
			}else if(column.getPrimaryKey().contains("P")){
				if(column.getDataType().contains(ColumnDataTypes.ORACLE_CHAR)){
					if(truncateOverflowingValues){	
						if(column.getCharacterMaximumLength() > 0 && values[Integer.parseInt(column.getFileColumn())].toString().length() > column.getCharacterMaximumLength()){
							inWhere = inWhere +column.getColumnName()+" = " + "'"+values[Integer.parseInt(column.getFileColumn())].toString().substring(0, column.getCharacterMaximumLength()-1).replaceAll("'", "CHR(39)")+"'" +" and ";
						}else{
							inWhere = inWhere +column.getColumnName()+" = " + "'"+values[Integer.parseInt(column.getFileColumn())].toString().replaceAll("'", "CHR(39)")+"'" +" and ";
						}
					}else{
						inWhere = inWhere +column.getColumnName()+" = " + "'"+values[Integer.parseInt(column.getFileColumn())].toString().replaceAll("'", "CHR(39)")+"'" +" and ";
					}
				}else if(column.getDataType().contains(ColumnDataTypes.SQL_SERVER_CHAR) || column.getDataType().contains(ColumnDataTypes.SQL_SERVER_TIME) && !column.getDataType().contains(ColumnDataTypes.SQL_SERVER_DATE)){
					if(truncateOverflowingValues){
						if(column.getCharacterMaximumLength() > 0 && values[Integer.parseInt(column.getFileColumn())].toString().length() > column.getCharacterMaximumLength()){
							inWhere = inWhere +"["+column.getColumnName()+"]"+" = " + "'"+values[Integer.parseInt(column.getFileColumn())].toString().substring(0, column.getCharacterMaximumLength()-1).replaceAll("'", "''")+"'" +" and ";
						}else{
							inWhere = inWhere +"["+column.getColumnName()+"]"+" = " + "'"+values[Integer.parseInt(column.getFileColumn())].toString().replaceAll("'", "''")+"'" +" and ";
						}
					}else{
						inWhere = inWhere +"["+column.getColumnName()+"]"+" = " + "'"+values[Integer.parseInt(column.getFileColumn())].toString().replaceAll("'", "''")+"'" +" and ";
					}
				}else if(column.getDataType().contains(ColumnDataTypes.ORACLE_DATE)){
					inWhere = inWhere +column.getColumnName()+" = "+ "to_date('"+values[Integer.parseInt(column.getFileColumn())]+"','"+getOracleDateFormat(dateFormat)+"')  and ";
				}
				else if(column.getDataType().contains(ColumnDataTypes.SQL_SERVER_DATE)){
					inWhere = inWhere +"["+column.getColumnName()+"]"+" = "+ "'"+values[Integer.parseInt(column.getFileColumn())]+"' and ";
				}else{
					inWhere = inWhere +column.getColumnName()+" = "+ values[Integer.parseInt(column.getFileColumn())] +" and ";
				}				
			}
		}
		toBeSet = toBeSet.substring(0, toBeSet.lastIndexOf(","));
		inWhere = inWhere.substring(0, inWhere.lastIndexOf("and"));
		if(inWhere.length() > 0){
			updateQuery = updateQuery + toBeSet + " where " + inWhere;
		}else{
			updateQuery = updateQuery + toBeSet;
		}
		return updateQuery;
	}




	public static int getSQLDateFormat(String format){
		int returnFormat = 110;
		if(format.equals("mm/dd/yy")){
			returnFormat = 1;
		}else if(format.equals("yy.mm.dd")){
			returnFormat = 2;
		}else if(format.equals("dd/mm/yy")){
			returnFormat = 3;
		}else if(format.equals("dd.mm.yy")){
			returnFormat = 4;
		}else if(format.equals("dd-mm-yy")){
			returnFormat = 5;
		}else if(format.equals("dd mmm yy")){
			returnFormat = 6;
		}else if(format.equals("mmm dd, yy")){
			returnFormat = 7;
		}else if(format.equals("mm-dd-yy")){
			returnFormat = 10;
		}else if(format.equals("yy/mm/dd")){
			returnFormat = 11;
		}else if(format.equals("mm/dd/yyyy")){
			returnFormat = 101;
		}else if(format.equals("yyyy.mm.dd")){
			returnFormat = 102;
		}else if(format.equals("dd/mm/yyyy")){
			returnFormat = 103;
		}else if(format.equals("dd.mm.yyyy")){
			returnFormat = 104;
		}else if(format.equals("dd-mm-yyyy")){
			returnFormat = 105;
		}else if(format.equals("dd mmm yyyy")){
			returnFormat = 106;
		}else if(format.equals("mmm dd, yyyy")){
			returnFormat = 107;
		}else if(format.equals("dd-mm-yyyy")){
			returnFormat = 110;
		}else if(format.equals("yyyy/mm/dd")){
			returnFormat = 111;
		}     
		return returnFormat;
	}
	public static String getOracleDateFormat(String javaformat){
		String returnFormat = javaformat.replaceAll("mm", "MI").toUpperCase().replaceAll("AA", "AM").replaceAll("MMM", "MON");
		return returnFormat;
	}
}
//