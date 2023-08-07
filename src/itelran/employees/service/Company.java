package itelran.employees.service;

import itelran.employees.dto.*;
import java.util.*;

public interface Company {
	//using HashMap<key: id, value: Employee>
	boolean addEmployee(Employee empl);
	Employee removeEmployee(long id);
	Employee getEmployee(long id);
	List<Employee> getEmployees();
	List<DepartmentSalary> getDepartmentSalaryDistribution();// returns list of departments with average salary
	List<SalaryDistribution> getSalaryDistribution(int interval);//returns salary values distribution: min,  max,  num of employee
	void restore(String filePath);//restore from file//get employee and add to file
	void save(String filePath);//writs to file// gets list of employee all
	//interval = 1000;[10000-11000];
}

