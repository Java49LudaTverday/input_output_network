package telran.employees.controller;

import java.time.LocalDate;
import java.util.*;

import itelran.employees.dto.DepartmentSalary;
import itelran.employees.dto.Employee;
import itelran.employees.dto.SalaryDistribution;
import itelran.employees.service.Company;
import telran.view.InputOutput;
import telran.view.Item;

public class CompanyController {

	private static final long MIN_ID = 100000;
	private static final long MAX_ID = 999999;
	private static final int MIN_SALARY = 6000;
	private static final int MAX_SALARY = 50000;
	private static final int MAX_AGE = 75;
	private static final int MIN_AGE = 20;
	private static final int MIN_INTERVAL = 1;
	static private Set<String> departments = new HashSet<>(
			Arrays.asList(new String[] { "QA", "Development", "Audit", "Management", "Accounting" }));
	
	static Company company;

	public static ArrayList<Item> getCompanyItems(Company company) {
		CompanyController.company = company;
		ArrayList<Item> res = new ArrayList<>(Arrays.asList(getItems()));
		return res;
	}

	private static Item[] getItems() {

		return new Item[] {
				Item.of("Add new Employee",CompanyController::addEmployeeItem ),
				Item.of("Remove new Employee",CompanyController::removeEmployeeItem ),
				Item.of("All Employees ",CompanyController::getEmployeesItem ),
				Item.of("Data about Employee",CompanyController::getEmployeeItem ),
				Item.of("Employees by Salary",CompanyController::getEmployeesBySalaryItem ),
				Item.of("Employees by Department",CompanyController::getEmployeesByDepartmentItem ),
				Item.of("Update salary Employee",CompanyController::updateSalaryItem ),
				Item.of("Departments and Salary",CompanyController::getEmployeesDepartmentSalaryDistributionItem ),
				Item.of("Distribution by Salary",CompanyController::getSalaryDistribution ),
				Item.of("Employee by Age",CompanyController::getEmployeesByAgeItem ),
				Item.of("Update department",CompanyController::updateDepartmentItem )

		};
	}

	

	static void addEmployeeItem(InputOutput io) {
		long id = io.readLong("Enter Employee identity: ", "Wrong identity value ", MIN_ID, MAX_ID);
		String name = io.readString("Enter Employee`s name: ", "Wrong name: ", str -> str.matches("[A-Z][a-z]+"));

		String department = io.readString("Enter department: ", "Wrong department", departments);
		int salary = io.readInt("Enter Employee`s salary: ", "Wrong salary ", MIN_SALARY, MAX_SALARY);
		LocalDate birthDate = io.readDate("Enter Employee`s birth date: ", "Wrong birth Date entered ", getBirthDate(MAX_AGE), getBirthDate(MIN_AGE));
		boolean res = company.addEmployee(new Employee(id, name, department, salary, birthDate));
		io.writeLine(res ? String.format("Employee with id %d has been added", id)
				: String.format("Employee with id %d already exists", id));
	}

	private static LocalDate getBirthDate(int age) {
		
		return LocalDate.now().minusYears(age);
	}
	
	static void removeEmployeeItem(InputOutput io) {
		long id = io.readLong("Enter Employee`s id: ", "Wrong id ", MIN_ID, MAX_ID);
		Employee res = company.removeEmployee(id);
		io.writeLine(res != null ? String.format("Employee with id %d has been removed", id) :
			String.format("Employee with id %d doesn`t exist", id));
	}
	
	static void getEmployeeItem(InputOutput io) {
		long id = io.readLong("Enter Employee`s id: ", "Wrong id ", MIN_ID, MAX_ID);
		Employee res = company.getEmployee(id);
		io.writeLine(res != null ?  res  :
			String.format("Employee with id %d doesn`t exist", id));
	}
	
	static void getEmployeesItem(InputOutput io) {
		List<Employee> res = company.getEmployees();
		res.forEach(io::writeLine);
	}
	
	static void getEmployeesDepartmentSalaryDistributionItem(InputOutput io) {
		company.getDepartmentSalaryDistribution().forEach(io::writeLine);
		
	}
	
	static void getEmployeesByDepartmentItem(InputOutput io) {
		String department = io.readString("Enter department: ", "Wrong department ", departments);
		List<Employee> res = company.getEmployeesByDepartment(department);
		io.writeLine(String.format("Employees by department %s: ", department));
		res.forEach(io::writeLine);
	}
	
	static void getEmployeesBySalaryItem(InputOutput io) {
		int salaryFrom = io.readInt("Enter salary from: ", "Wrong salary ", MIN_SALARY, MAX_SALARY);
		int salaryTo = io.readInt("Enter salary to: ", "Wrong salary ", MIN_SALARY, MAX_SALARY);
		List<Employee> res = company.getEmployeesBySalary(salaryFrom, salaryTo);
		io.writeLine(String.format("Employees with salary %d - %d: ", salaryFrom, salaryTo));
		res.forEach(io::writeLine);
	}
	
	static void  getEmployeesByAgeItem(InputOutput io) {
		int ageFrom = io.readInt("Enter age from: ", "Wrong age ", MIN_AGE, MAX_AGE);
		int ageTo = io.readInt("Enter age to: ", "Wrong age ", MIN_AGE, MAX_AGE);
		List<Employee> res = company.getEmployeesByAge(ageFrom, ageTo);
		io.writeLine(String.format("Employees with age %d - %d: \n%s", ageFrom, ageTo, res));
	}
	
	static void updateSalaryItem(InputOutput io) {
		long id = io.readLong("Enter Employee`s id: ", "Wrong id ", MIN_ID, MAX_ID);
		int newSalary = io.readInt("Enter new salary : ", "Wrong salary ", MIN_SALARY, MAX_SALARY);
		Employee empl = company.updateSalary(id, newSalary);
		io.writeLine(String.format("Employee with id %d has been updated", id));
	}
	
	static void updateDepartmentItem(InputOutput io) {
		long id = io.readLong("Enter Employee`s id: ", "Wrong id ", MIN_ID, MAX_ID);
		String newDepartment = io.readString("Enter new department : ", "Wrong department ", departments);
		Employee empl = company.updateDepartment(id, newDepartment);
		io.writeLine(String.format("Employee with id %d has been updated", id));
	}
	
	static void getSalaryDistribution(InputOutput io) {
		int interval = io.readInt("Enter interval: ", "Wrong interval ", MIN_INTERVAL, MAX_SALARY);
		List<SalaryDistribution> res = company.getSalaryDistribution(interval);
		res.forEach(io::writeLine);
	}

}
