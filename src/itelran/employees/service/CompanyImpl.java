package itelran.employees.service;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import itelran.employees.dto.DepartmentSalary;
import itelran.employees.dto.Employee;
import itelran.employees.dto.SalaryDistribution;

public class CompanyImpl implements Company {
	//LinkedHashMap if in ordered view 
HashMap<Long, Employee> employees = new LinkedHashMap<>();
	@Override
	public boolean addEmployee(Employee empl) {
		//if not includes adds employee and returns null
		// otherwise return referents to source value;
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
		
		return new ArrayList(employees.values());
	}

	@Override
	public List<DepartmentSalary> getDepartmentSalaryDistribution() {
		
		return employees.values().stream()
				.collect(Collectors.groupingBy(Employee::department,
						Collectors.averagingInt(Employee::salary)))
				.entrySet().stream()
				.map(entry -> new DepartmentSalary(entry.getKey(), entry.getValue()))
				.toList();
	}

	@Override
	public List<SalaryDistribution> getSalaryDistribution(int interval) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void restore(String filePath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(String filePath) {
		// TODO Auto-generated method stub
		
	}

	
}
