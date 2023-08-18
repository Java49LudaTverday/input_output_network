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
		Menu menuNumberOperations = new Menu("Calculator", getItemsCalculatorMenu());
		Menu menuDateOperations = new Menu("Date calculator", getItemsDateMenu());
		Menu menuMain = new Menu("Main menu", getItemsMainMenu(menuNumberOperations, menuDateOperations));
		menuMain.perform(io);
	}

	static Item[] getItemsMainMenu(Menu submenu, Menu submenu1) {
		Item[] items = { Item.of("Number Operations", io -> submenu.perform(io)),
				Item.of("Date Operations", io -> submenu1.perform(io)), 
				Item.ofExit() };
		return items;
	}
	
	static Item[] getItemsCalculatorMenu() {
		Item[] items = { Item.of("Add numbers", io -> calculate(io, (a, b) -> a + b)),
				Item.of("Subtract numbers", io -> calculate(io, (a, b) -> a - b)),
				Item.of("Multiply numbers", io -> calculate(io, (a, b) -> a * b)),
				Item.of("Divide numbers", io -> calculate(io, (a, b) -> a / b)), 
				Item.ofExit() };
		return items;
	}

	static Item[] getItemsDateMenu() {
		Item[] items = { Item.of("Date after a given number of days", io -> calculateDate(io, (a, b) -> a.plusDays(b))),
				Item.of("Date before a given number of days", io -> calculateDate(io, (a, b) -> a.minusDays(b))),
				Item.of("Days between two days",
						io -> calculateDateBetween(io, (a, b) -> ChronoUnit.DAYS.between(a, b))),
				Item.ofExit() };
		return items;
	}	

	static void calculate(InputOutput io, BinaryOperator<Double> operator) {
		double first = io.readDouble("Enter first number: ", "Must be number: ");
		double second = io.readDouble("Enter second number: ", "Must be a number ");
		io.writeLine(operator.apply(first, second));
	}
	
	private static void calculateDate(InputOutput io, BiFunction<LocalDate, Long, LocalDate> operator) {
		LocalDate currentDate = io.readDate("Enter a date in format yyyy-MM-DD: ", "Wrong date ");
		long numDays = io.readLong("Enter a number of days: ", "Wrong number ");
		io.writeLine("Day after  " + numDays + " day is : " + operator.apply(currentDate, numDays));

	}
	private static void calculateDateBetween(InputOutput io, BiFunction<LocalDate, LocalDate, Long> operator) {
		LocalDate firstDate = io.readDate("Enter first date  in format yyyy-MM-DD: ", "Wrong date ");
		LocalDate secondDate = io.readDate("Enter second date in format yyyy-MM-DD: ", "Wrong date ");
		io.writeLine("Number of days between " + firstDate + " and " + secondDate + ": "
				+ operator.apply(firstDate, secondDate));
	}	

}
