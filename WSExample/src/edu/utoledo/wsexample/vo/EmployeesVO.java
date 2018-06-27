package edu.utoledo.wsexample.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "employees")
public class EmployeesVO {
	
	private List<EmployeeVO> employees;

	/**
	 * @return the employees
	 */
	public List<EmployeeVO> getEmployees() {
		return employees;
	}

	/**
	 * @param employees the employees to set
	 */
	@XmlElement(name="employee")
	public void setEmployees(List<EmployeeVO> employees) {
		this.employees = employees;
	}
	
	
}
