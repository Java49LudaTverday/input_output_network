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
	
	// gets list of employees 
	List<Employee> getEmployees();
	
	// returns list of departments with average salary
	List<DepartmentSalary> getDepartmentSalaryDistribution();
	
	//returns salary values distribution: min,  max,  num of employee
	//interval = 1000;[10000-11000];
	List<SalaryDistribution> getSalaryDistribution(int interval);
	
	//restores from file
	default  void restore(String filePath) {;
	if(Files.exists(Path.of(filePath))) {
		try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(filePath))) {
			List<Employee> employeesRestor = (List<Employee>) input.readObject();
			employeesRestor.forEach(this::addEmployee);
		} catch (Exception ex) {
			new RuntimeException(ex.toString());
		}
		}
	}
	//writs to file
	default void save(String filePath) {
		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filePath))) {
			output.writeObject(getEmployees());
		} catch (IOException ex) {
			new RuntimeException(ex.toString());
		}
	};
	
	//new methods from homework #36
	List<Employee> getEmployeesByDepartment(String department);
	List<Employee> getEmployeesBySalary(int salaryFrom, int salaryTo);
	List<Employee> getEmployeesByAge(int ageFrom, int ageTo);
	Employee updateSalary(long id, int newSalary);//returns employee before updating
	Employee updateDepartment(long id, String newDepartment);
	
}

