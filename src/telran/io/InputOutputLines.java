package telran.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class InputOutputLines {

	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Usage: must be two arguments(source, destination)");
		} else {
			try (BufferedReader reader = getInput(args[0]); 
					PrintStream output = getOutput(args[1])) {
				inputOutput(reader, output);
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}

	private static PrintStream getOutput(String destination) throws FileNotFoundException {
		PrintStream res = destination.equalsIgnoreCase("console") ? 
				System.out : new PrintStream(destination);
		return res;
	}

	private static BufferedReader getInput(String source) throws FileNotFoundException {
		BufferedReader res = source.equalsIgnoreCase("console") ? 
				new BufferedReader(new InputStreamReader(System.in))
				: new BufferedReader(new FileReader(source));
		return res;
	}

	private static void inputOutput(BufferedReader reader, PrintStream output) 
			throws IOException {
		String line = null;
		while ((line = reader.readLine()) != null) {
			output.println(line);
		}
	}

}
