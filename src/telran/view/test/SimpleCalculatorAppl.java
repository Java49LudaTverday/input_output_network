package telran.view.test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import telran.view.*;

public class SimpleCalculatorAppl {

	public static void main(String[] args) {
		InputOutput io = new ConsoleInputOutput();
		Menu menuNumberOperations = new Menu("Calculator", getItemsCalculator());
		Menu menuDateOperations = new Menu("Date calculator",getItemsDate() );
		Menu menuMain = new Menu("Main menu", getItemsSubmenu(menuNumberOperations, menuDateOperations));		
		menuMain.perform(io);
	}

private static Item[] getItemsDate() {
	Item[] items = { Item.of("Date after a given number of days", io -> calculateDate(io, (a, b) -> a.plusDays(b))),
			Item.of("Date before a given number of days", io -> calculateDate(io, (a, b) -> a.minusDays(b))),
			Item.of("Days between two days", io -> calculateDateBetween(io, (a, b) -> ChronoUnit.DAYS.between(a, b))),
			Item.ofExit() };
	return items;
	}

private static void calculateDateBetween(InputOutput io, BiFunction<LocalDate,LocalDate, Long> operator) {
	LocalDate currentDate = io.readDate("Enter a first date: ", "Wrong date ");
	LocalDate currentDate1 = io.readDate("Enter a second date: ", "Wrong date ");
	io.writeLine(operator.apply(currentDate1, currentDate1));
}

private static void calculateDate(InputOutput io, BiFunction<LocalDate, Long, LocalDate> operator) {
	LocalDate currentDate = io.readDate("Enter a given date: ", "Wrong date ");
	long numDays = io.readLong("Enter a number of days: ", "Wrong number ");
	io.writeLine("Day after  " + numDays + " day is : " + operator.apply(currentDate, numDays));
     
}

private static Item[] getItemsSubmenu(Menu submenu, Menu submenu1 ) {
	Item[] items = { Item.of("Number Operations", io -> submenu.perform(io)),
			Item.of("Date Operations", io ->  submenu1.perform(io)),			
			Item.ofExit() };
	return items;
	}

//	static double[] getTwoNumbers(InputOutput io) {
//		double first = io.readDouble("Enter first number: ", "Must be number: ");
//		double second = io.readDouble("Enter second number: ", "Must be a number ");
//		return new double[] { first, second };
//	}
	static void calculate(InputOutput io, BinaryOperator<Double> operator) {
		double first = io.readDouble("Enter first number: ", "Must be number: ");
		double second = io.readDouble("Enter second number: ", "Must be a number ");
		 io.writeLine(operator.apply(first, second));
	}

//	static Item[] getItems(InputOutput io) {
//		Item[] items = { Item.of("Add numbers", SimpleCalculatorAppl::add),
//				Item.of("Subtract numbers", SimpleCalculatorAppl::subtract),
//				Item.of("Multoply numbers", SimpleCalculatorAppl::multiply),
//				Item.of("Divide numbers", SimpleCalculatorAppl::divide), Item.ofExit() };
//		return items;
//	}
	static Item[] getItemsCalculator() {
		Item[] items = { Item.of("Add numbers", io -> calculate(io, (a,b) -> a + b)),
				Item.of("Subtract numbers", io -> calculate(io, (a,b) -> a - b)),
				Item.of("Multiply numbers", io -> calculate(io, (a,b) -> a * b)),
				Item.of("Divide numbers", io -> calculate(io, (a,b) -> a / b)), 
				Item.ofExit() };
		return items;
	}

//	static void add(InputOutput io) {
//		double[] numbers = getTwoNumbers(io);
//		io.writeLine(numbers[0] + numbers[1]);
//	}
//
//	static void subtract(InputOutput io) {
//		double[] numbers = getTwoNumbers(io);
//		io.writeLine(numbers[0] - numbers[1]);
//	}
//
//	static void multiply(InputOutput io) {
//		double[] numbers = getTwoNumbers(io);
//		io.writeLine(numbers[0] * numbers[1]);
//	}
//
//	static void divide(InputOutput io) {
//		double[] numbers = getTwoNumbers(io);
//		io.writeLine(numbers[0] / numbers[1]);
//	}
}
