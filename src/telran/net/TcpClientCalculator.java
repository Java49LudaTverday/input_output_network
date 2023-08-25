package telran.net;

import java.net.*;
import java.util.ArrayList;
import java.io.*;
import telran.view.*;
import java.util.*;

public class TcpClientCalculator {
	
	 static String HOST = "localhost";
	 static int PORT = 6000;
	 static final String NAME_ITEM = "Send response ";
	 static HashSet<String> operators = new HashSet<>(List.of("+", "-", "*", "/"));

	public static void main(String[] args) throws Exception {
		try (Socket socket = new Socket(HOST, PORT);
				PrintStream writer = new PrintStream(socket.getOutputStream());
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) 
		      {
			InputOutput io = new ConsoleInputOutput();
			Menu menu = new Menu("Client calculator menu", getItems(io, writer, reader));
			menu.perform(io);
		}
	}

	private static ArrayList<Item> getItems(InputOutput io, PrintStream writer, BufferedReader reader) {

		return new ArrayList<>(List.of(Item.of(NAME_ITEM, io1 -> {
			
			String requestOperator = getOperator(io);
			Double firstOperand = getOperand(io, "Enter first operand: ");
			Double secondOperand = getOperand(io, "Enter second operand: ");
			writer.println(String.format("%s#%f#%f", requestOperator, firstOperand, secondOperand));
			displayRequest(io, reader);
			
		}), Item.ofExit()));
	}

	private static String getOperator(InputOutput io) {		
		return io.readString("Enter operator: " + operators, "Wrong operator ", operators );
	}
	
	private static double getOperand(InputOutput io, String prompt) {
		return io.readDouble(prompt, "Operand must be any double number");
	}
	
	private static void displayRequest(InputOutput io, BufferedReader reader) {
		try {
			String request = reader.readLine();
			io.writeLine("Result: " + request);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	

}
