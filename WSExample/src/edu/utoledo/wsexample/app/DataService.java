package edu.utoledo.wsexample.app;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.providers.jaxb.Wrapped;

import edu.utoledo.wsexample.dao.EmployeeDAO;
import edu.utoledo.wsexample.vo.EmployeeVO;
import edu.utoledo.wsexample.vo.EmployeesVO;
import edu.utoledo.wsexample.vo.ExceptionResponseVO;

/**
 * The Service Class
 * @author Anil Sehgal
 * @version 0.1
 */
@Path("/xml")
public class DataService {
	
	/**
	 * The logger variable to log debugging statements
	 * @see Logger for more details
	 */
	private static final Logger logger = Logger.getLogger(DataService.class);
	
	/**
	 * Gets all the employee data matching search criteria
	 * @param employeeName the employee name first or last
	 * @return the {@link EmployeesVO} record
	 */
	@GET
	@Path("/getEmployeeData/{employeeName}")
	@Produces(MediaType.APPLICATION_XML)
	@Wrapped(element = "employees")
	public Response getEmployeeData(@PathParam("employeeName") String employeeName) {

		logger.debug("getEmployeeData()");
		
		EmployeesVO employeesVO = new EmployeesVO();
		try {
			EmployeeDAO employeeDAO = new EmployeeDAO();
			List<EmployeeVO> list = employeeDAO.getEmployeesByName(employeeName);
			employeesVO.setEmployees(list);
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage(), e);
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( errResponse )
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build(); 
		}

		return Response.ok(employeesVO)
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build(); 
	}
	
	/**
	 * Gets all the employee data matching search criteria
	 * @param employeeName the employee name first or last
	 * @return the {@link EmployeesVO} record
	 */
	@GET
	@Path("/getEmployeeData")
	@Produces(MediaType.APPLICATION_XML)
	@Wrapped(element = "employees")
	public Response getAllEmployeeData() {

		logger.debug("getEmployeeData()");
		
		EmployeesVO employeesVO = new EmployeesVO();
		try {
			EmployeeDAO employeeDAO = new EmployeeDAO();
			List<EmployeeVO> list = employeeDAO.getEmployeesByName("");
			employeesVO.setEmployees(list);
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage(), e);
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( errResponse )
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build(); 
		}

		return Response.ok(employeesVO)
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build(); 
	}
	
	/**
	 * This method intercepts every POST method call and adds CORS registration for the service call responses
	 *  as otherwise, the web service calls are denied access in the client frame
	 * @param request the POST request 
	 * @see HttpServletRequest
	 * @return a blank response with the CORS headers added
	 */
	@OPTIONS
	@Path("")
	public Response CORSregisterInstallation(@Context HttpServletRequest request) {
		ResponseBuilder response = Response.ok();

		response.header("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		response.header("Access-Control-Allow-Origin", "*");
		response.header("Access-Control-Allow-Headers", "accept, origin, ag-mobile-variant, content-type");
		response.header("Content-Type", "text/plain; charset=us-ascii"); 
		return response.build();
	}




}