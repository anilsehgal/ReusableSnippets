package com.victor.multiUtilityToolkit.importer.supportMethods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.victor.multiUtilityToolkit.importer.constants.DBTableColumnBean;


public class DBSettingMethods {
	public List<String> executeQueryforTables(String query,String driverUrl,String dBUrl, String dBUser, String dBPass) throws Exception{
		List<String> returnList = new ArrayList<String>();
		Connection connection =  null;
		Statement statement = null;
		ResultSet resultSet = null;
		try{
			Class.forName(driverUrl);
			connection =  DriverManager.getConnection(dBUrl,dBUser,dBPass);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while(resultSet.next()){
				returnList.add(resultSet.getString(1));
			}
		}catch(Exception e){
			throw new Exception(e);
		}finally{
			if(resultSet!=null){
				resultSet.close();
			}
			if(statement!=null){
				statement.close();
			}
			if(connection!=null){
				connection.close();
			}
		}
		return returnList;
	}
	public List<DBTableColumnBean> executeQueryForColumns(String query,String driverUrl,String dBUrl, String dBUser, String dBPass) throws Exception{
		List<DBTableColumnBean> returnList = new ArrayList<DBTableColumnBean>();
		Connection connection =  null;
		Statement statement = null;
		ResultSet resultSet = null;
		DBTableColumnBean columnBean = null;
		try{
			Class.forName(driverUrl);
			connection =  DriverManager.getConnection(dBUrl,dBUser,dBPass);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			int index = 0;
			while(resultSet.next()){
				columnBean = new DBTableColumnBean();
				columnBean.setColumnPosition(resultSet.getInt(1));
				columnBean.setColumnName(resultSet.getString(2));
				columnBean.setDataType(resultSet.getString(3));
				columnBean.setCharacterMaximumLength(resultSet.getInt(4));
				columnBean.setIsNullable(resultSet.getString(5));
				returnList.add(columnBean);
				index++;
			}
		}catch(Exception e){
			throw new Exception(e);
		}finally{
			if(resultSet!=null){
				resultSet.close();
			}
			if(statement!=null){
				statement.close();
			}
			if(connection!=null){
				connection.close();
			}
		}
		return returnList;
	}	
}
