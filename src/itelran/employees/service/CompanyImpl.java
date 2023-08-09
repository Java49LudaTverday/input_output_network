package itelran.employees.service;

import java.util.stream.Collectors;
import java.util.*;
import itelran.employees.dto.DepartmentSalary;
import itelran.employees.dto.Employee;
import itelran.employees.dto.SalaryDistribution;

public class CompanyImpl implements Company {
	// LinkedHashMap - in ordered view
	LinkedHashMap<Long, Employee> employees = new LinkedHashMap<>();

	@Override
	public boolean addEmployee(Employee empl) {
		// if does not includes -> adds employee and returns null
		// otherwise return referents to object;
		return employees.putIfAbsent(empl.id(), empl) == null;
	}

	@Override
	public Employee removeEmployee(long id) {

		return employees.remove(id);
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
				.entrySet().stream()
				.map(entry -> new DepartmentSalary(entry.getKey(), entry.getValue())).toList();
	}

	@Override
	public List<SalaryDistribution> getSalaryDistribution(int interval) {

		return employees.values().stream()
				.collect(Collectors.groupingBy(e -> e.salary()/interval,
						Collectors.counting()))
				.entrySet().stream()
				.map(e -> new SalaryDistribution(e.getKey() * interval,
						e.getKey() * interval + interval - 1, e.getValue().intValue()))
				.sorted((sd1, sd2) -> Integer.compare(sd1.minSalary(), sd2.minSalary()))
						.toList();
	}	

}
