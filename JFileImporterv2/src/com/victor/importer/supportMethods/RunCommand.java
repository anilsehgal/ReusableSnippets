package com.victor.importer.supportMethods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.victor.importer.constants.StringConstants;

public class RunCommand {
	public String runSQL(String driverUrl, String dbUrl, String userName, String password, String sql)
	throws Exception{
		int rowsAffected = 0;
		String returnString = StringConstants.SUCCESS;
		Connection connection = null;
		try{
			Class.forName(driverUrl);
			connection = DriverManager.getConnection(dbUrl,userName,password);
			Statement statement = null;
			statement = connection.createStatement();
			
			rowsAffected = statement.executeUpdate(sql);
			statement.close();
			if(rowsAffected==0){
				returnString = StringConstants.FAILED;
				
			}
		}catch(Exception e){
			e.printStackTrace();
			returnString = StringConstants.FAILED;
		}finally{
			if(connection!=null){
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return returnString;		
	}
	public String runSystemCommand(String command) throws Exception{
		String returnString = StringConstants.SUCCESS;
		Runtime runtime = Runtime.getRuntime();
		Process process = runtime.exec(command);
		if(process.exitValue()==0){
			returnString = StringConstants.SUCCESS;
		}else{
			returnString = StringConstants.FAILED;
		}
		return returnString;
	}
	public String executeCommand(String type, String driverUrl, String dbUrl, String userName, String password, String sql, String command) throws Exception{
		String returnString = null;
		if(type.contains(StringConstants.SQL)){
			returnString = runSQL(driverUrl, dbUrl, userName, password, sql);
		}else if(type.contains(StringConstants.RUNTIME)){
			returnString = runSystemCommand(command);
		}
		return returnString;
	}
}
