package telran.io;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class inputOutputTest {

	@Test
	void pathTest() {
		Path pathParent = Path.of("../..");
		 System.out.println(pathParent.toAbsolutePath().normalize().getNameCount());

	}

	@Test
	void displayDirContentTest() {
		try {
			displayDirContent(Path.of("../.."), 3);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void displayDirContent(Path pathParent, int maxDepth) throws IOException {
		// display content dir by using method walk of the class Files
		// apply the method walk of the class Files and try to skip the files not having
		// permission of reading
		if (!Files.isDirectory(pathParent)) {
			throw new IllegalArgumentException("Path must be a directory");
		}
		int level = pathParent.toAbsolutePath().normalize().getNameCount();
		Files.walk(pathParent, maxDepth)
		 .map(p -> p.toAbsolutePath().normalize())
		 .filter(p -> Files.isReadable(p))
		 .forEach(p -> System.out.printf("%s%s -> %s\n", " ".repeat(p.getNameCount() - level),
						p.getName(p.getNameCount() - 1), Files.isDirectory(p) ? "dir" : "file"));
	}

}
