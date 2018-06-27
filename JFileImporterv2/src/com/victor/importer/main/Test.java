package com.victor.importer.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Test {
	public static void main(String[] args) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//pni6w1629:1521/FileImportDB","SYSTEM","1234");
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
