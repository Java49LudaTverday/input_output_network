package telran.io;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecurityPermission;

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
		pathParent = pathParent.toAbsolutePath().normalize();
		if(!Files.exists(pathParent)) {
			throw new IllegalArgumentException("doesn`t exist");
		}
		if (!Files.isDirectory(pathParent)) {
			throw new IllegalArgumentException("no directory");
		}
		int level = pathParent.getNameCount();
		Files.walk(pathParent, maxDepth)
		.filter(p -> {
			try {
				return !Files.isHidden(p);
			} catch (IOException e) {
				return false;
			}
		})
		 .forEach(p -> System.out.printf("%s%s -> %s\n", " ".repeat(p.getNameCount() - level),
						p.getName(p.getNameCount() - 1), Files.isDirectory(p) ? "dir" : "file"));
	}

}
