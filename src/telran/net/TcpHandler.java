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
//		socket = new Socket(host, port);
//		output = new ObjectOutputStream(socket.getOutputStream());
//		input = new ObjectInputStream(socket.getInputStream());
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
			output.writeObject(request);
			Response response = (Response)input.readObject();
			if(response.code() != ResponseCode.OK) {
				throw new RuntimeException(response.responseData().toString());
			}
		    res = (T) response.responseData();
		    
		} catch (SocketException e) {
			connect();			
			try {
				output.writeObject(request);
				Response response = (Response)input.readObject();
			if(response.code() != ResponseCode.OK) {
				throw new RuntimeException(response.responseData().toString());
			}
		    res = (T) response.responseData();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (Exception e) {	
			throw new RuntimeException(e.getMessage());				
		}
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
