package telran.net;

import java.net.*;
import java.io.*;

public class TcpClientServer implements Runnable {
	Socket socket;
	ObjectInputStream input;
	ObjectOutputStream output;
	ApplProtocol protocol;
	TCPServer tcpServer;
	final static int TOTAL_IDLE_TIMEOUT = 30000;
	int idleTime = 0;

	public TcpClientServer(Socket socket, ApplProtocol protocol, TCPServer tcpServer) throws IOException {
		this.socket = socket;
		this.socket.setSoTimeout(TCPServer.IDLE_TIMEOUT);
		input = new ObjectInputStream(socket.getInputStream());
		output = new ObjectOutputStream(socket.getOutputStream());
		this.protocol = protocol;
		this.tcpServer = tcpServer;
	}

	@Override
	public void run() {

		while (!tcpServer.isShutdown) {
			try {
				Request request = (Request) input.readObject();
				Response response = protocol.getResponse(request);
				output.writeObject(response);
			} catch (SocketTimeoutException e) {
				idleTime += TCPServer.IDLE_TIMEOUT;
				if(idleTime > TOTAL_IDLE_TIMEOUT && 
						tcpServer.clientsCounter.get() > tcpServer.nThreads) {
					try {
						socket.close();
						System.out.println("Socket closed - idle time exits total timeout");
						break;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				if(tcpServer.isShutdown) {
					try {
						socket.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				System.out.println("Socket closed - server has been shutdown");
				break;
				}
			} catch (EOFException e) {
				System.out.println("client closed normally connection");
				break;
			} catch (Exception e) {
				System.out.println("client closed abnormally connection" + e.getMessage());
				break;
			}
			
		}
		tcpServer.clientsCounter.decrementAndGet();
	}

}
