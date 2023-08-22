package telran.employees.controller;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

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

		return new Item[] { Item.of("Add new Employee", CompanyController::addEmployeeItem),
				Item.of("Remove new Employee", CompanyController::removeEmployeeItem),
				Item.of("All Employees ", CompanyController::getEmployeesItem),
				Item.of("Data about Employee", CompanyController::getEmployeeItem),
				Item.of("Employees by Salary", CompanyController::getEmployeesBySalaryItem),
				Item.of("Employees by Department", CompanyController::getEmployeesByDepartmentItem),
				Item.of("Update salary Employee", CompanyController::updateSalaryItem),
				Item.of("Departments and Salary", CompanyController::getEmployeesDepartmentSalaryDistributionItem),
				Item.of("Distribution by Salary", CompanyController::getSalaryDistribution),
				Item.of("Employee by Age", CompanyController::getEmployeesByAgeItem),
				Item.of("Update department", CompanyController::updateDepartmentItem) };
	}

	static void addEmployeeItem(InputOutput io) {
		long id = getID(io, false);
		String name = io.readString("Enter Employee`s name: ", "Wrong name: ", str -> str.matches("[A-Z][a-z]+"));

		String department = io.readString("Enter department: ", "Wrong department", departments);
		int salary = io.readInt("Enter Employee`s salary: ", "Wrong salary ", MIN_SALARY, MAX_SALARY);
		LocalDate birthDate = io.readDate("Enter Employee`s birth date: ", "Wrong birth Date entered ",
				getBirthDate(MAX_AGE), getBirthDate(MIN_AGE));
		supplyAndDisplayResult(io, () -> company.addEmployee(new Employee(id, name, department, salary, birthDate)),
				String.format("Employee with id %d has been added", id));
	}

	private static LocalDate getBirthDate(int age) {

		return LocalDate.now().minusYears(age);
	}

	static void removeEmployeeItem(InputOutput io) {
		long id = getID(io, true);
		supplyAndDisplayResult(io, () -> company.removeEmployee(id),
				String.format("Employee with id %d has been removed", id));
	}

	static void getEmployeeItem(InputOutput io) {
		long id = getID(io, true);
		Employee res = company.getEmployee(id);
		io.writeLine(res);
	}

	static void getEmployeesItem(InputOutput io) {

		displayListObjects(io, () -> company.getEmployees(), "");
	}

	static void getEmployeesDepartmentSalaryDistributionItem(InputOutput io) {

		displayListObjects(io, () -> company.getDepartmentSalaryDistribution(), "");

	}

	static void getEmployeesByDepartmentItem(InputOutput io) {
		String department = io.readString("Enter department: ", "Wrong department ", departments);
		displayListObjects(io, () -> company.getEmployeesByDepartment(department),
				String.format("Employees by department %s: ", department));
	}

	static void getEmployeesBySalaryItem(InputOutput io) {
	
		int[] range = getRangeInt(io, "Enter salary", "Wrong salary ", MIN_SALARY, MAX_SALARY);
		displayListObjects(io, () -> company.getEmployeesBySalary(range[0], range[1]),
				String.format("Employees with salary %d - %d: ", range[0], range[1]));
	}

	static void getEmployeesByAgeItem(InputOutput io) {
		int[] range = getRangeInt(io, "Enter age", "Wrong age ", MIN_AGE, MAX_AGE);
		displayListObjects(io, () -> company.getEmployeesByAge(range[0], range[1]),
				String.format("Employees with age %d - %d:", range[0], range[1]));
	}

	

	static void updateSalaryItem(InputOutput io) {
		long id = getID(io, true);
		int newSalary = io.readInt("Enter new salary : ", "Wrong salary ", MIN_SALARY, MAX_SALARY);
		supplyAndDisplayResult(io, () -> company.updateSalary(id, newSalary),
				String.format("Employee with id %d has been updated", id));
	}

	static void updateDepartmentItem(InputOutput io) {
		long id = getID(io, true);
		String newDepartment = io.readString("Enter new department : ", "Wrong department ", departments);
		supplyAndDisplayResult(io, () -> company.updateDepartment(id, newDepartment),
				String.format("Employee with id %d has been updated", id));
	}

	static void getSalaryDistribution(InputOutput io) {
		int interval = io.readInt("Enter interval: ", "Wrong interval ", MIN_INTERVAL, MAX_SALARY);
		displayListObjects(io, () -> company.getSalaryDistribution(interval), "");
	}

	private static long getID(InputOutput io, boolean isExist) {
		long id;
		boolean res = false;
		do {
			id = io.readLong("Enter Employee identity: ", "Wrong identity value  ", MIN_ID, MAX_ID);
			Employee empl = company.getEmployee(id);
			if (empl != null) {
				io.writeLine(String.format("Employee with ID %d is exists", id));
				res = isExist == true ? false : true;
			} else {
				io.writeLine(String.format("Employee with ID %d don`t exists", id));
				res = isExist == true ? true : false;
			}
		} while (res);
		return id;
	}

	private static <T> void displayListObjects(InputOutput io, Supplier<List<T>> supplier, String prompt) {
		List<T> employees = supplier.get();
		io.writeLine(prompt);
		employees.forEach(io::writeLine);
	}

	private static <T> void supplyAndDisplayResult(InputOutput io, Supplier<T> supplier, String result) {
		supplier.get();
		io.writeLine(result);
	}
	
	private static int[] getRangeInt(InputOutput io, String prompt, String wrongPrompt, int minValue, int maxValue) {
		boolean res = false;
		int[] minMax = new int[2];
		do {
			int from = io.readInt(prompt + " from: ", wrongPrompt, minValue, maxValue);
			int to = io.readInt(prompt + " to: ", wrongPrompt, minValue, maxValue);
			if (from < to) {
				res = true;
				minMax[0] = from;
				minMax[1] = to;
			} else {
				io.writeLine(wrongPrompt + " to must be greater than from");
			}			
		} while (!res);

		return minMax;
	}
}
