package itelran.employees.service;

import itelran.employees.dto.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public interface Company {
	//using HashMap<key: id, value: Employee>
	boolean addEmployee(Employee empl);
	Employee removeEmployee(long id);
	Employee getEmployee(long id);
	List<Employee> getEmployees();
	List<DepartmentSalary> getDepartmentSalaryDistribution();// returns list of departments with average salary
	List<SalaryDistribution> getSalaryDistribution(int interval);//returns salary values distribution: min,  max,  num of employee
	
	default void restore(String filePath) {;//restore from file//get employee and add to file
	if(Files.exists(Path.of(filePath))) {
		try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(filePath))) {
			List<Employee> employeesRestor = (List<Employee>) input.readObject();
			employeesRestor.forEach(this::addEmployee);
		} catch (Exception ex) {
			new RuntimeException(ex.toString());
		}
		}
	}
	default void save(String filePath) {
		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filePath))) {
			output.writeObject(getEmployees());
		} catch (IOException ex) {
			new RuntimeException(ex.toString());
		}
		//output.writeObject(new ArrayList<>(employees.values());
	};//writs to file// gets list of employee all
	//interval = 1000;[10000-11000];
	//new methods from homework #36
	List<Employee> getEmployeesByDepartment(String department);
	List<Employee> getEmployeesBySalary(int salaryFrom, int salaryTo);
	List<Employee> getEmployeesByAge(int ageFrom, int ageTo);
	Employee updateSalary(long id, int newSalary);//returns employee before updating
	Employee updateDepartment(long id, String newDepartment);
	
	default <T, K> List<T> getByCategory(Map<K,Collection<T>> map, K category, Comparator<T> comp ) {		
		 return map.get(category).stream()
			.sorted(comp).toList();
	 }
	
	default <T, K> List<T> getByCategory(TreeMap<K,Collection<T>> map,
			            K from, K to, Comparator<T> comp) {
		return  map.subMap(from, true, to, true).values().stream()
				 .flatMap(col -> col.stream().sorted(comp))
				 .toList();					
	}
	 
	
}

