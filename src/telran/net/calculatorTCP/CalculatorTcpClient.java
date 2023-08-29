package telran.net.calculatorTCP;

import telran.net.*;
import telran.view.*;

import java.io.Serializable;
import java.util.*;

public class CalculatorTcpClient {

	private static final String HOST = "localhost";
	private static final int PORT = 6000;
	public static HashSet<String> typesRequest = 
			new HashSet<>(Arrays.asList("add", "minus", "multiply", "divide"));

	public static void main(String[] args) throws Exception {
		try (TcpHandler client = new TcpHandler(HOST, PORT)) {
			InputOutput io = new ConsoleInputOutput();
			Menu menu = new Menu("Calculator Application", getItems(io, client));
			menu.perform(io);
		}
		;
	}

	private static ArrayList<Item> getItems(InputOutput io, TcpHandler client) {

		return new ArrayList<>(List.of(Item.of("Send request", io1 -> {
			
			String requestType = getOperand(io, typesRequest);
			Double op1 = getOperand(io, "Enter first operand: ");
			Double op2 = getOperand(io, "Enter second number: ");
			
			Double res = client.send(requestType, new Double[] { op1, op2 });
			io.writeLine(String.format("Result: %.2f ", res));
		}), Item.ofExit()));
	}

	private static String getOperand(InputOutput io, HashSet<String> typesRequest) {
		return io.readString("Enter operation type " + typesRequest, "Wrong operation", typesRequest);
	}

	private static Double getOperand(InputOutput io, String prompt) {
		
		return io.readDouble(prompt, "Wrong number");
	}

}
