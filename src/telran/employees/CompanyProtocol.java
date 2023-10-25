package telran.employees;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.logging.ErrorManager;

import telran.employees.dto.Employee;
import telran.employees.dto.FromTo;
import telran.employees.dto.UpdateData;
import telran.employees.service.Company;
import telran.net.ApplProtocol;
import telran.net.Request;
import telran.net.Response;
import telran.net.ResponseCode;

@SuppressWarnings("unused")
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
			requestType = requestType.replace('/', '_');
			Method method = this.getClass().getDeclaredMethod(requestType, Serializable.class);
			Serializable responseData = (Serializable) method.invoke(this, data);
			response = new Response(ResponseCode.OK, responseData);
		} catch (NoSuchMethodException e) {
			response = new Response(ResponseCode.WRONG_TYPE, requestType + " is unsupported in the Company Protocol");
		} catch (InvocationTargetException e) {
			Throwable ce = e.getCause();
			String errorMessage = ce instanceof ClassCastException ? 
					"Mismatchinge of received data type": ce.getMessage();
			response = new Response(ResponseCode.WRONG_DATE, errorMessage);
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

	private Serializable department_update(Serializable data) {
		UpdateData idDepartment = (UpdateData) data;
		long id = idDepartment.id();
		String department = (String) idDepartment.data();
		return company.updateDepartment(id, department);
	}

	private Serializable salary_update(Serializable data) {
		UpdateData idSalary = (UpdateData) data;
		long id = idSalary.id();
		int salary = (int) idSalary.data();
		return company.updateSalary(id, salary);
	}

	private Serializable employees_remove(Serializable data) {
		long id = (long) data;
		return company.removeEmployee(id);
	}

	private Serializable employees_get(Serializable data) {

		return new ArrayList<>(company.getEmployees());
	}

	private Serializable employee_get(Serializable data) {
		long id = (long) data;
		return company.getEmployee(id);
	}

	private Serializable employee_add(Serializable data) {
		Employee empl = (Employee) data;
		return company.addEmployee(empl);
	}

}
