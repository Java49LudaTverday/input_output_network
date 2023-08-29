package telran.net;

import java.io.IOException;
import java.net.*;

public class TCPServer implements Runnable {
private int port;
private ApplProtocol protocol;
private ServerSocket serverSocket;

public TCPServer (int port, ApplProtocol protocol) throws IOException {
	this.port = port;
	serverSocket = new ServerSocket(port);
	this.protocol = protocol;
}
	@Override
	public void run() {
		System.out.println("Server is listening on port" + port);
		try {
			while(true) {
			Socket socket =	serverSocket.accept();
			TcpClientServer clientServer = new TcpClientServer(socket, protocol);
			clientServer.run();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
