package itelran.employees.service;

import java.util.stream.Collectors;
import java.util.*;
import itelran.employees.dto.DepartmentSalary;
import itelran.employees.dto.Employee;
import itelran.employees.dto.SalaryDistribution;
import java.time.LocalDate;
import java.time.Month;

public class CompanyImpl implements Company {
	// LinkedHashMap - in ordered view
	LinkedHashMap<Long, Employee> employees = new LinkedHashMap<>();
	TreeMap<Integer, Collection<Employee>> employeesSalary = new TreeMap<>();
	TreeMap<LocalDate, Collection<Employee>> employeesAge = new TreeMap<>(Comparator.reverseOrder());
	HashMap<String, Collection<Employee>> employeesDep = new HashMap<>();

	@Override
	public boolean addEmployee(Employee empl) {
		// if does not includes -> adds employee and returns null
		// otherwise return referents to object;
		boolean res = false;
		Employee emplRes = employees.putIfAbsent(empl.id(), empl);
		if (emplRes == null) {
			res = true;
			addEmployeeSalary(empl);
			addEmployeesAge(empl);
			addEmployeesDep(empl);
		}
		return res;
	}

	private void addEmployeesDep(Employee empl) {
		String dep = empl.department();
		employeesDep.computeIfAbsent(dep, k -> new HashSet<>()).add(empl);

	}

	private void addEmployeesAge(Employee empl) {
		LocalDate birthDate = empl.birthDate();
		employeesAge.computeIfAbsent(birthDate, k -> new HashSet<>()).add(empl);

	}

	private void addEmployeeSalary(Employee empl) {

		// if mapping no -> add;
		// if there is one -> no mapping;
		int salary = empl.salary();
		employeesSalary.computeIfAbsent(salary, k -> new HashSet<>()).add(empl);

	}

	@Override
	public Employee removeEmployee(long id) {
		Employee empl = employees.remove(id);
		if (empl != null) {
			removeEmployeeSalary(empl);
			removeEmployeeAge(empl);
			removeEmployeeDep(empl);
		}
		return empl;
	}

	private void removeEmployeeDep(Employee empl) {
		String dep = empl.department();
		Collection<Employee> employeesCol = employeesDep.get(dep);
		employeesCol.remove(empl);
		if (employeesCol.isEmpty()) {
			employeesDep.remove(employeesCol);
		}
	}

	private void removeEmployeeAge(Employee empl) {
		LocalDate birthDate = empl.birthDate();
		Collection<Employee> employeesCol = employeesAge.get(birthDate);
		employeesCol.remove(empl);
		if (employeesCol.isEmpty()) {
			employeesAge.remove(birthDate);
		}
	}

	private void removeEmployeeSalary(Employee empl) {
		int salary = empl.salary();
		Collection<Employee> employeesCol = employeesSalary.get(salary);
		employeesCol.remove(empl);
		if (employeesCol.isEmpty()) {
			employeesSalary.remove(salary);
		}
	}

	@Override
	public Employee getEmployee(long id) {

		return employees.get(id);
	}

	@Override
	public List<Employee> getEmployees() {

		return new ArrayList<>(employees.values());
	}

	@Override
	public List<DepartmentSalary> getDepartmentSalaryDistribution() {

		return employees.values().stream()
				.collect(Collectors.groupingBy(Employee::department, Collectors.averagingInt(Employee::salary)))
				.entrySet().stream().map(entry -> new DepartmentSalary(entry.getKey(), entry.getValue())).toList();
	}

	@Override
	public List<SalaryDistribution> getSalaryDistribution(int interval) {

		return employees.values().stream()
				.collect(Collectors.groupingBy(e -> e.salary() / interval, Collectors.counting())).entrySet().stream()
				.map(e -> new SalaryDistribution(e.getKey() * interval, e.getKey() * interval + interval - 1,
						e.getValue().intValue()))
				.sorted((sd1, sd2) -> Integer.compare(sd1.minSalary(), sd2.minSalary())).toList();
	}

	@Override
	public List<Employee> getEmployeesByDepartment(String department) {

		return getByCategory(employeesDep, department, Comparator.comparing(Employee::id) );
	}

	@Override
	public List<Employee> getEmployeesBySalary(int salaryFrom, int salaryTo) {

		return getByCategory(employeesSalary, salaryFrom,
				salaryTo, Comparator.comparing(Employee::id));
	}

	@Override
	public List<Employee> getEmployeesByAge(int ageFrom, int ageTo) {
		LocalDate from = getLocalDate(ageFrom);
		LocalDate to = getLocalDate(ageTo);
		return getByCategory(employeesAge, from, 
				to, Comparator.comparing(Employee::id));

	}

	private LocalDate getLocalDate(int ageDestination) {
		int year = LocalDate.now().getYear();
		Month monthNow = LocalDate.now().getMonth();
		int dayOfMonth = LocalDate.now().getDayOfMonth();
		return LocalDate.of(year - ageDestination, monthNow, dayOfMonth);
	}

	@Override
	public Employee updateSalary(long id, int newSalary) {
		Employee res = employees.get(id);
		Employee emplUpdated = new Employee(res.id(), res.name(), res.department(), newSalary, res.birthDate());
		updateEmployee(id, emplUpdated);
		return res;
	}

	@Override
	public Employee updateDepartment(long id, String newDepartment) {
		Employee res = employees.get(id);
		Employee emplUpdated = new Employee(res.id(), res.name(), newDepartment, res.salary(), res.birthDate());
		updateEmployee(id, emplUpdated);
		return res;
	}

	private void updateEmployee(long id, Employee emplUpdated) {
		removeEmployee(id);
		addEmployee(emplUpdated);
	}
}
