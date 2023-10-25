package telran.net;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class TCPServer implements Runnable {

	public static final int IDLE_TIMEOUT = 100;
	private int port;
	private ApplProtocol protocol;
	private ServerSocket serverSocket;
    int nThreads = Runtime.getRuntime().availableProcessors();
//	int nThreads = 2;
	private ExecutorService threadPool = Executors.newFixedThreadPool(nThreads);
	boolean isShutdown = false;
	AtomicInteger clientsCounter = new AtomicInteger(0);

	public TCPServer(int port, ApplProtocol protocol) throws IOException {
		this.port = port;
		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(IDLE_TIMEOUT);
		this.protocol = protocol;
	}

	public void shutdown() {
		threadPool.shutdown();
		isShutdown = true;
	}
	@Override
	public void run() {
		System.out.println("Server is listening on port" + port);

		while (!isShutdown) {
			try {
				Socket socket = serverSocket.accept();
				TcpClientServer clientServer = new TcpClientServer(socket, protocol, this);
				clientsCounter.incrementAndGet();
				if (!isShutdown) {
					threadPool.execute(clientServer);
				}
			} catch (SocketTimeoutException e) {

			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
	}

}
