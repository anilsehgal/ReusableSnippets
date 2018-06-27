package edu.utoledo.wsexample.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.utoledo.wsexample.vo.EmployeeVO;

public class EmployeeDAO {

	public List<EmployeeVO> getEmployeesByName(String name) {
		
		List<EmployeeVO> employeeVOs = new ArrayList<>();
		try {
			
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:jtds:sqlserver://Victors-Legacy:1433/EmpDB;instance=SQLEXPRESS", "sa", "");
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery("Select * from Employees where First_Name like '%" + name + "%' or Last_Name like '%" + name + "%'");
			while (result.next()) {

				EmployeeVO employeeVO = new EmployeeVO();
				int employeeId = result.getInt(1);
				String firstName = result.getString(2);
				String lastName = result.getString(3);
				float salary = result.getFloat(4);
				boolean active = result.getInt(5) == 1;
				
				employeeVO.setEmployeeId(employeeId);
				employeeVO.setFirstName(firstName);
				employeeVO.setLastName(lastName);
				employeeVO.setSalary(salary);
				employeeVO.setActive(active);
				employeeVOs.add(employeeVO);
			}
			result.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return employeeVOs;
	}
}
