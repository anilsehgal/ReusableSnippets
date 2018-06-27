package com.tables;

import java.util.List;

import com.psa.entities.base.TargetTable;


public class MyTarget implements TargetTable {
	private String message;
	private int id;
	private String name;
	private String pass;
	private String crDt;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getCrDt() {
		return crDt;
	}

	public void setCrDt(String crDt) {
		this.crDt = crDt;
	}

	@Override
	public Object useRows(List<Object[]> resultantRows) {

		for ( Object[] resultRow : resultantRows ) {
			
			System.out.println(resultRow[0] + " " + resultRow[1] + " " + resultRow[2] + " " + resultRow[3] + " " + resultRow[4]);
		}
		return null;
	}
	
	
}
