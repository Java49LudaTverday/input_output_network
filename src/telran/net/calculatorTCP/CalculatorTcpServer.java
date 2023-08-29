package telran.net.calculatorTCP;
import java.io.IOException;

import telran.net.*;

public class CalculatorTcpServer {
	
	private static final int PORT = 6000;

	public static void main(String[] args) throws IOException {
		ApplProtocol protocol = new CalculatorProtocol();
		TCPServer server = new TCPServer(PORT, protocol);
		server.run();
	}
}
