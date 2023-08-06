package telran.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyFileStreams implements CopyFile {
	int bufferLength;

	public CopyFileStreams(int bufferLength) {
		this.bufferLength = bufferLength;
	}

	@Override
	public void copy(String pathToSource, String pathToDestination)  {
		
			
			try (InputStream input = new FileInputStream(pathToSource);
					OutputStream output = new FileOutputStream(pathToDestination)) {
				int length = 0;
				byte[] buffer = new byte[bufferLength];
				while ((length = input.read(buffer)) > 0) {
					output.write(buffer);
				}
			
			} catch (Exception e) {
			e.printStackTrace();
		}
	}	

}
