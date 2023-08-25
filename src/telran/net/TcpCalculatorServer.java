package telran.net;
import java.io.*;

import java.net.*;
public class TcpCalculatorServer {
public static final int PORT = 6000;
private static final String DELIMITER = "#";
	public static void main(String[] args) throws Exception {
		ServerSocket server = new ServerSocket(PORT);
		System.out.println("Server is listening to port: " + PORT);
		while(true) {
			Socket socket = server.accept();
			clientRun(socket);
		}
	}
	private static void clientRun(Socket socket) {
		try(BufferedReader reader = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));
				PrintStream writer = new PrintStream(socket.getOutputStream())){			
			while(true) {
				String line = reader.readLine();
				if(line == null) {
					System.out.println("client closed normally connection");
					break;
				}
				String response = getResponse(line);
				writer.println(response);				
			}
			
		} catch (Exception e){
			System.out.println("client closed abnormally connection");
		}		
	}
	private static String getResponse(String line) {
		//<operator>#<firstOperand>#<secondOperand>
		// operators: + ; - ; / ; *
		//operators : any double number
		String response = "Wrong request structure, usage: <operand>#<firstOperator>#<secondOperator>";
		String[] tokens = line.split(DELIMITER); 
		if(tokens.length == 3) {
			double firstOper = Double.parseDouble(tokens[1]);
			double secondOper = Double.parseDouble(tokens[2]);
			response = switch (tokens[0]) {
			case "+" -> Double.toString(firstOper + secondOper);
			case "-" -> Double.toString(firstOper - secondOper);
			case "*" -> Double.toString(firstOper * secondOper);
			case "/" -> Double.toString(firstOper / secondOper);
			default -> "wrong request type";
			};
		}
		return response;
	}

}
