package telran.net;

import java.net.*;
import java.io.*;
import java.util.*;
import telran.view.*;

public class TcpClientExample {

	private static final String HOST = "local host";
	private static final int PORT = 5000;

	public static void main(String[] args) throws Exception {
		try (Socket socket = new Socket(HOST, PORT);
				PrintStream writer = new PrintStream(socket.getOutputStream());
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

			InputOutput io = new ConsoleInputOutput();

			Menu menu = new Menu("TCP client example", Item.of("sent request", io1 -> {
				HashSet<String> requests = new HashSet<>(Arrays.asList("length", "reverse"));
				String requestType = io1.readString("Enter request type " + requests, HOST, requests);
				String string = io1.readString("Enter any string");
				writer.println(String.format("%s#%s", requestType, string));

				try {
					String response = reader.readLine();
					io.writeLine(response);
				} catch (IOException e) {
					throw new RuntimeException(e.toString());
				}
			}), Item.ofExit());
			menu.perform(io);
		}
	}
}
