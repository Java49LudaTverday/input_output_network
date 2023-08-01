package telran.io;

import java.nio.file.Files;
import java.nio.file.Path;

public class CopyPerformanceAppl {
	private static final int N_RUNS = 4;
	private static String pathToSource = "bigFile";
	private static String pathToDestination = "new_bigFile";	
	private static int[] bufferLength = {10_000, 100_000, 1_000_000, 100_000_000};

	public static void main(String[] args) {
		try {
		long size =	Files.size(Path.of(pathToSource));
			for(int bl: bufferLength) {
				System.out.println("File: " + pathToSource + "; Size: " + size + "; Buffer: " + bl);
				CopyFileStreams copyFile = new CopyFileStreams(bl);
			CopyPerformanceTest copyTest = new CopyPerformanceTest("buffer_"+bl, N_RUNS,
					pathToSource , pathToDestination, copyFile);
			copyTest.run();
			System.out.println();
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
