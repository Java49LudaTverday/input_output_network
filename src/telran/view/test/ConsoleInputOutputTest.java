package telran.view.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import telran.view.console.ConsoleInputOutput;

class ConsoleInputOutputTest {
	ConsoleInputOutput consoleInOut = new ConsoleInputOutput();

	@Test
	@Disabled
	void readStringTest() {
		String hello = consoleInOut.readString("Write a greeting: ");
		consoleInOut.write("You say: " + hello);
	}
	@Test
	@Disabled
	void writeTest() {
		consoleInOut.write("Hello ");
	    consoleInOut.writeLine("Hello world!");
	}
	
	@Test
	@Disabled
	void readObjectTest() {
		//TODO
	}
	
	@Test
	@Disabled
	void readIntTest() {
		String prompt = "Enter any integer number : ";
		String errorPrompt = "must be an integer number ";
		int number = consoleInOut.readInt(prompt, errorPrompt);
		consoleInOut.writeLine("Your integer number -> " + number);		
	}
	
	@Test
	@Disabled
	void readIntInRangeTest() {
		String prompt = "Enter any integer number in range  ";
		String errorPrompt = "must be an integer number  ";
		int number = consoleInOut.readInt(prompt, errorPrompt, 10, 100);
		consoleInOut.writeLine("Your integer number -> " + number);	
	}
	
	
	@Test
	@Disabled
	void readLongTest() {
		
		String prompt = "Enter a long number:  ";
		String errorPrompt = "must be a long number ";
		long number = consoleInOut.readLong(prompt, errorPrompt);
		consoleInOut.writeLine("Your number -> " + number);	
	}
	
	@Test
	@Disabled
	void readLongInRangeTest() {
			
		String prompt = "Enter a long number in range  ";
		String errorPrompt = "must be a long number ";
		long number = consoleInOut.readInt(prompt, errorPrompt, 0, 10);
		consoleInOut.writeLine("Your number -> " + number);	
	}
	
	@Test
	@Disabled
	void readStringPredTest() {
		String prompt = "Enter a country which you live in lower case:  ";
		String errorPrompt = "Enter another country ";
		String country = consoleInOut.readString(prompt, errorPrompt, Predicate.isEqual("israel"));
		consoleInOut.writeLine("Your country -> " + country);
		
	}
	
	@Test
	@Disabled
	void readStringOptionsTest() {
		String prompt = "Enter one of primitive types in java:   ";
		String errorPrompt = "Wrong answer. ";
		String[] strOptions = {"int", "short", "long", "double", "float", "byte", "boolean"}; 
		Set<String> options = Arrays.stream(strOptions).collect(Collectors.toSet());
		String type = consoleInOut.readString(prompt, errorPrompt, options);
		consoleInOut.writeLine("All right. Your answer -> " + type);
 	}
	
	@Test
	@Disabled
	void readDateTest() {
 		String prompt = "Enter a date of next NEW YEAR in format: YYYY-MM-DD ";
		String errorPrompt = "Wrong FORMAT DATE ";
		LocalDate ld = consoleInOut.readDate(prompt, errorPrompt);
		consoleInOut.writeLine("Your answer -> " + ld);
	}
	@Test
	@Disabled
	void readDateInRange() {
		String prompt = "Enter a day of august in format: YYYY-MM-DD ";
		String errorPrompt = "Wrong DATE ";		
		LocalDate ld = consoleInOut.readDate(prompt, errorPrompt, LocalDate.parse("2023-08-01"), LocalDate.parse("2023-08-31"));
		consoleInOut.writeLine("Your answer -> " + ld);
	}	
	@Test
	@Disabled
	void readDoubleTest() {
		String prompt = "Enter a double number 0.1:   ";
		String errorPrompt = "Number must be a double. ";
		double number = consoleInOut.readDouble(prompt, errorPrompt);
		consoleInOut.writeLine("Your number -> " + number);
	}

}
