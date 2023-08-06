package telran.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CopyPerformanceTest extends PerformanceTest {

	// gets different between sizeBuffer and time of copy
	String pathToSource;
	String pathToDestination;
	CopyFile copyFile;

	public CopyPerformanceTest(String testName, int nRuns, String pathToSource, String pathToDestination, CopyFile copyFile)
			throws Exception {
		super(testName, nRuns);
		this.pathToDestination = pathToDestination;
		this.pathToSource = pathToSource;
		this.copyFile = copyFile;
	}

	@Override
	protected void runTest() {
		copyFile.copy(pathToSource, pathToDestination);
	}
	
	}


