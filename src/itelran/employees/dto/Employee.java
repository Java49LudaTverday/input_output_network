package itelran.employees.dto;//data transfer object

import java.io.Serializable;
import java.time.LocalDate;

public record Employee(long id, String name, 
		String department,int salary, 
		LocalDate birthDate) implements Serializable {

}// toString, equals, hashCode
