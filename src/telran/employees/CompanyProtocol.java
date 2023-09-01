package telran.employees;

import java.io.Serializable;
import java.util.ArrayList;

import telran.employees.dto.Employee;
import telran.employees.dto.FromTo;
import telran.employees.dto.UpdateData;
import telran.employees.service.Company;
import telran.net.ApplProtocol;
import telran.net.Request;
import telran.net.Response;
import telran.net.ResponseCode;

public class CompanyProtocol implements ApplProtocol {

	private Company company;

	public CompanyProtocol(Company company) {
		this.company = company;
	}

	@Override
	public Response getResponse(Request request) {
		Response response = null;
		String requestType = request.requestType();
		Serializable data = request.requestData();
		try {
			Serializable responseData = switch(requestType) {
			case "employee/add" -> employee_add(data);
			case "employee/get" -> employee_get(data);
			case "employees/get" -> employees_get(data);
			case "employee/remove" -> employees_remove(data);
			case "salary/update" -> employee_salary_update(data);
			case "department/update" -> employee_department_update(data);
			case "department/salary/distribution" -> department_salary_distribution(data);
			case "salary/distribution" -> salary_distribution(data);
			case "employees/department" -> employees_department(data);
			case "employees/salary" -> employees_salary(data);
			case "employees/age" -> employees_age(data);
			// exclude save
			    default -> new Response(ResponseCode.WRONG_TYPE, requestType + " is unsupported in the Company Protocol");
			};
			response = (responseData instanceof Response) ? (Response) responseData :
						new Response(ResponseCode.OK, responseData);		
			} catch (Exception e) {
							
			response = new Response(ResponseCode.WRONG_DATE, e.toString());
		}
		return response;
	}

	private Serializable employees_age(Serializable data) {
		FromTo age = (FromTo) data;
		int from = age.from();
		int to = age.to();
		return new ArrayList<>(company.getEmployeesByAge(from, to));
	}

	private Serializable employees_salary(Serializable data) {
		FromTo salary = (FromTo) data;
		int from = salary.from();
		int to = salary.to();
		return new ArrayList<>(company.getEmployeesBySalary(from, to));
	}

	private Serializable employees_department(Serializable data) {
		String department = (String) data;
		return new ArrayList<>(company.getEmployeesByDepartment(department));
	}

	private Serializable salary_distribution(Serializable data) {
		int interval = (int) data;
		
		return new ArrayList<>(company.getSalaryDistribution(interval));
	}

	private Serializable department_salary_distribution(Serializable data) {
		
		return new ArrayList<>(company.getDepartmentSalaryDistribution());
	}

	private Serializable employee_department_update(Serializable data) {
		UpdateData idDepartment = (UpdateData) data;
		long id = idDepartment.id();
		String department = (String) idDepartment.data();
		return company.updateDepartment(id, department);
	}

	private Serializable employee_salary_update(Serializable data) {
		UpdateData idSalary = (UpdateData) data;
		long id = idSalary.id();
		int salary = (int) idSalary.data();
		return company.updateSalary(id, salary );
	}

	private Serializable employees_remove(Serializable data) {
		long id = (long) data;
		return company.removeEmployee(id);
	}

	private Serializable employees_get(Serializable data) {
		
		return new ArrayList<>(company.getEmployees());
	}

	private Serializable  employee_get(Serializable data) {
		long id = (long) data;
		return company.getEmployee(id);
	}

	private Serializable employee_add(Serializable data) {
		Employee empl = (Employee) data;
		return company.addEmployee(empl);
	}
	 

}
