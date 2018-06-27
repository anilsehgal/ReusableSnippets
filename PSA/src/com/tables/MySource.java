package com.tables;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.psa.entities.base.SourceTable;

public class MySource implements SourceTable {
	private int userId;
	private String userName;
	private String password;
	private Date createDate;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public List<Object[]> createData() {
		Object[] test1 = new Object[4];
		test1[0] = 1;
		test1[1] = "Anil";
		test1[2] = "Anil";
		test1[3] = new Date();
		Object[] test2 = new Object[4];
		test2[0] = 2;
		test2[1] = "Amit";
		test2[2] = "Amit";
		test2[3] = new Date();
		List<Object[]> list = new ArrayList<Object[]>();
		list.add(test1);
		list.add(test2);
		
		return list;
	}
}
