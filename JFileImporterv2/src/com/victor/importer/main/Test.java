package com.victor.importer.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Test {
	public static void main(String[] args) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection con = DriverManager.getConnection("jdbc:sqlserver://EECS-NE2042-13A;instanceName=SQLEXPRESS;databaseName=PanoramaDB;","sa","utoledo@123");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from test1");
			while(rs.next()){
				System.out.println(rs.getString(1));
			}
			rs.close();
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
