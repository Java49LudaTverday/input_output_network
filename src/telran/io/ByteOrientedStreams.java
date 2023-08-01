package telran.io;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.io.*;

class ByteOrientedStreams {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	@Disabled
	void smallFileOututStreamTest() throws Exception {
		// if exist will be rewrite
		// adding with constructor, true to add, false rewrite
		try (OutputStream output = new FileOutputStream("smallFile.txt", true)) {
			byte[] array = "Hello world".getBytes();
			output.write(array);
		}
	}

	@Test
	//@Disabled
	void bigFileOutputStream() throws Exception {
		try (OutputStream output = new FileOutputStream("bigFile")) {

			for (int i = 0; i < 4; i++) {
				StringBuilder builder = new StringBuilder(100_000_000);
				for (int j = 0; j < 50_000_000; j++) {
					builder.append("Hello worl");
				}
				output.write(builder.toString().getBytes());
			}
		}
	}

	@Test
	void bigFileInputStream() throws Exception {
		try (InputStream input = new FileInputStream("bigFile")) {
			byte[] buffer = new byte[1_000_000];
			long totalSize = 0;
			int length = 0;
			while((length = input.read(buffer)) > 0) {
				totalSize += length;
			}
			assertEquals(2_000_000_000, totalSize);
		}
	}
}
