package itelran.employees.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import itelran.employees.dto.DepartmentSalary;
import itelran.employees.dto.Employee;
import itelran.employees.dto.SalaryDistribution;

public class CompanyImpl implements Company {
	//LinkedHashMap - in ordered view 
LinkedHashMap<Long, Employee> employees = new LinkedHashMap<>();
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
		
		return new ArrayList<>(employees.values());
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
		
		return employees.values().stream()
				.collect(Collectors.groupingBy(empl ->
		empl.salary()/interval)).entrySet().stream()
				.map(e -> new SalaryDistribution( getMinSalary(e),
								getMaxSalary(e), e.getValue().size()))
				.toList();
						
		
	}

	private int getMaxSalary(Entry<Integer, List<Employee>> e) {
		return e.getValue().stream()
				.mapToInt(en -> en.salary())
				.max().orElseThrow();
	}

	private int getMinSalary(Entry<Integer, List<Employee>> e) {
		return e.getValue().stream()
				.mapToInt(en -> en.salary())
				.min().orElseThrow();
	}

	@Override
	public void restore(String filePath) {
		try(ObjectInputStream input = 
				new ObjectInputStream(new FileInputStream(filePath))){
			while(true) {
				addEmployee((Employee)input.readObject());
			}			 
		} catch(Exception ex) {
			new RuntimeException(ex.toString());
		}
		
	}

	@Override
	public void save(String filePath) {
		try (ObjectOutputStream output = 
				new ObjectOutputStream(new FileOutputStream(filePath))){
			employees.values().stream().forEach(empl -> {
				try {
					output.writeObject(empl);
				} catch (IOException ex) {
					new RuntimeException(ex.toString());
				}
			});		
	} catch(IOException ex) {
		new RuntimeException(ex.toString());
	}
	}
	
}
