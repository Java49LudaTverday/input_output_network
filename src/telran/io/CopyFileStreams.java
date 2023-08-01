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
		byte[] buffer = new byte[bufferLength];
		try {
			readFrom(pathToSource, buffer);
			writeTo(pathToDestination, buffer);
			} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	private void readFrom(String nameFile, byte[] buffer) throws Exception {
		long totalSize = 0;
		int length = 0;
		try (InputStream input = new FileInputStream(nameFile)) {
			while ((length = input.read(buffer)) > 0) {
				totalSize += length;
			}
			input.close();
		}
	}

	private void writeTo(String nameFile, byte[] buffer) throws Exception {
		try (OutputStream output = new FileOutputStream(nameFile)) {
			StringBuilder builder = new StringBuilder(buffer.length);
			for (int i = 0; i < buffer.length; i++) {
				builder.append(buffer[i]);
			}
			output.write(builder.toString().getBytes());
			output.close();
		}

	}

}
