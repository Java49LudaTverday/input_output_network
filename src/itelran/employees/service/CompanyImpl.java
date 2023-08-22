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
	
	private <T> void addToIndex(Employee empl, T key, Map<T, Collection<Employee>> map) {
		map.computeIfAbsent(key, k -> new HashSet<>()).add(empl);
	}
	private <T> void removeFromIndex(Employee empl, T key, Map<T, Collection<Employee>> map) {

		Collection<Employee> employeesCol = map.get(key);
		employeesCol.remove(empl);
		if (employeesCol.isEmpty()) {
			map.remove(key);
		}
	}

	private void addEmployeesDep(Employee empl) {
		addToIndex(empl, empl.salary(), employeesSalary);

	}

	private void addEmployeesAge(Employee empl) {
		addToIndex(empl, empl.birthDate(), employeesAge);

	}

	private void addEmployeeSalary(Employee empl) {

		// if mapping no -> add;
		// if there is one -> no mapping;
		int salary = empl.salary();
		addToIndex(empl, empl.department(), employeesDep);

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
	private void removeEmployeeSalary(Employee empl) {
		int salary = empl.salary();
		removeFromIndex(empl, salary, employeesSalary);
	}

	private void removeEmployeeAge(Employee empl) {
		removeFromIndex(empl, empl.birthDate(), employeesAge);
	}

	private void removeEmployeeDep(Employee empl) {
		removeFromIndex(empl, empl.department(), employeesDep);
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
		Collection<Employee> employeesCol = employeesDep.get(department);
		ArrayList<Employee> res = new ArrayList<>();
		if (employeesCol != null) {
			res.addAll(employeesCol);
		}
		return res;
	}

	@Override
	public List<Employee> getEmployeesBySalary(int salaryFrom, int salaryTo) {

	    return employeesSalary.subMap(salaryFrom, true, salaryTo, true).values().stream()
				.flatMap(col -> col.stream().sorted((empl1, empl2) -> Long.compare(empl1.id(), empl2.id())))
				.toList();
	}

	@Override
	public List<Employee> getEmployeesByAge(int ageFrom, int ageTo) {
		LocalDate dateTo = LocalDate.now().minusYears(ageTo);
		LocalDate dateFrom = LocalDate.now().minusYears(ageFrom);
		return employeesAge.subMap(dateFrom, true, dateTo, true).values().stream()
				.flatMap(col -> col.stream()
				.sorted((empl1, empl2) -> Long.compare(empl1.id(), empl2.id())))
				.toList();

	}

	@Override
	public Employee updateSalary(long id, int newSalary) {
		Employee empl = removeEmployee(id);
		if(empl != null) {
			Employee newEmployee = new Employee(id, empl.name(),
					empl.department(), newSalary, empl.birthDate());
			addEmployee(newEmployee);
		}
		return empl;
	}

	@Override
	public Employee updateDepartment(long id, String newDepartment) {
		Employee empl = removeEmployee(id);
		if(empl != null) {
			Employee newEmployee = new Employee(id, empl.name(),
					newDepartment, empl.salary(), empl.birthDate());
			addEmployee(newEmployee);
		}
		return empl;
	}

}
