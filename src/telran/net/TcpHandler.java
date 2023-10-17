package telran.net;

import java.io.*;
import java.net.*;

public class TcpHandler implements Closeable {
	// client
	private Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String host;
	private int port;
	

	public TcpHandler(String host, int port) throws Exception {
		this.host = host;
		this.port = port;		
		connect();
	}

	@Override
	public void close() throws IOException {
		socket.close();
	}
	
	public <T> T send(String requestType, Serializable requestData) {
		T res = null;
		Request request = new Request(requestType, requestData);		
		try {		
			res = getResponse(request);
		    
		} catch (SocketException e) {
			connect();			
			try {
				res = getResponse(request);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {	
			throw new RuntimeException(e.getMessage());				
		}
		return res;
	}

	private <T> T getResponse(Request request) throws IOException, ClassNotFoundException {
		T res;
		output.writeObject(request);
		Response response = (Response)input.readObject();
		if(response.code() != ResponseCode.OK) {
			throw new RuntimeException(response.responseData().toString());
		}
		res = (T) response.responseData();
		return res;
	}
	
	private void connect ()  {
		try {
			socket = new Socket(host, port);
			output = new ObjectOutputStream(socket.getOutputStream());
		    input = new ObjectInputStream(socket.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
		
	

}
