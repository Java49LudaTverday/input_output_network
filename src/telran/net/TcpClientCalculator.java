package telran.net;
import java.net.*;
import java.util.ArrayList;
import java.io.*;
import telran.view.*;
import java.util.*;
public class TcpClientCalculator {
	public static String HOST = "localhost";
	public static int PORT = 6000;
	public static HashSet<String> operators = new HashSet<>(List.of("+", "-", "*", "/"));

	public static void main(String[] args) throws Exception {
		try(Socket socket = new Socket(HOST, PORT);
				PrintStream writer = new PrintStream(socket.getOutputStream());
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
			InputOutput io = new ConsoleInputOutput();
			Menu menu = new Menu("Client calculator menu", getItems(io, writer, reader));
			menu.perform(io);
		}
	}

	private static ArrayList<Item> getItems(InputOutput io, PrintStream writer, BufferedReader reader) {
		
		return new ArrayList<>(List.of(Item.of("Send response ", 
				io1 -> {
					String requestOperator = io.readString("Enter operator: " + operators);
					Double firstOperand = io.readDouble("Enter first operand: ", "Operand must be any double number");
					Double secondOperand = io.readDouble("Enter second operand: ", "Operand must be any double number");
					writer.println(String.format("%s#%f#%f", requestOperator, firstOperand, secondOperand));
					try {
					String request = reader.readLine();
					io.writeLine("Result: " + request);
					} catch (Exception e) {
						System.out.println(e.toString());
					}
				} ),
				Item.ofExit()));
	}

}
