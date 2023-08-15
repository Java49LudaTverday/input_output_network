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
	ConsoleInputOutput conInOut;

	@BeforeEach
	void setUp() throws Exception {
		conInOut = new ConsoleInputOutput();
	}

	@Test
	@Disabled
	void readStringTest() {
		String hello = conInOut.readString("Say HELLO");
		assertEquals("hello", hello);
	}
	@Test
	@Disabled
	void writeTest() {
		conInOut.write("Hello");
	
	}
	@Test
	@Disabled
	void writeLineTest() {
		conInOut.writeLine("Say hello world!");
	}
	@Test
	@Disabled
	void readObjectTest() {
		//TODO
	}
	
	@Test
	@Disabled
	void readIntTest() {
		String prompt = "Enter a number 10 ";
		String errorPrompt = "must be a number ";
		int number = conInOut.readInt(prompt, errorPrompt);
		assertEquals(10, number);
		
	}
	
	@Test
	@Disabled
	void readLongTest() {
		
		String prompt = "Enter a number 10 ";
		String errorPrompt = "must be a number ";
		long number = conInOut.readInt(prompt, errorPrompt);
		assertEquals(10, number);
	}
	
	@Test
	
	void readLongInRangeTest() {
			
		String prompt = "Enter a number from 0 to 10:  ";
		String errorPrompt = "must be a number ";
		long number = conInOut.readInt(prompt, errorPrompt, 0, 10);
		assertEquals(0, number);
	}
	
	@Test
	@Disabled
	void readStringPredTest() {
		String prompt = "Enter your country:  ";
		String errorPrompt = "doesn`t mutch ";
		String country = conInOut.readString(prompt, errorPrompt, Predicate.isEqual("israel"));
		assertEquals("israel", country);
		
	}
	
	@Test
	@Disabled
	void readStringOptionsTest() {
		String prompt = "Enter written number:   ";
		String errorPrompt = "Number must be written. ";
		String[] strOptions = {"one", "two", "three", "four", "five"}; 
		Set<String> options = Arrays.stream(strOptions).collect(Collectors.toSet());
		String number = conInOut.readString(prompt, errorPrompt, options);
		assertEquals("one",  number);
 	}
	
	@Test
	@Disabled
	void readDateTest() {
 		String prompt = "Enter a date of next NEW YEAR in format: YYYY-MM-DD ";
		String errorPrompt = "Wrong DATE ";
		LocalDate ld = conInOut.readDate(prompt, errorPrompt);
		assertEquals(LocalDate.parse("2024-01-01"), ld);
	}
	
	@Test
	@Disabled
	void readDateInRange() {
		String prompt = "Enter a day of august: YYYY-MM-DD ";
		String errorPrompt = "Wrong DATE ";		
		LocalDate ld = conInOut.readDate(prompt, errorPrompt, LocalDate.parse("2023-08-01"), LocalDate.parse("2023-08-31"));
		assertEquals(LocalDate.parse("2023-08-08"), ld);
	}
	@Test
	@Disabled
	void readDoubleTest() {
		String prompt = "Enter a double number 0.1:   ";
		String errorPrompt = "Number must be a double. ";
		double number = conInOut.readDouble(prompt, errorPrompt);
		assertEquals(0.1, number);
	}

}
