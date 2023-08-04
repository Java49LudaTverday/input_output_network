package telran.io;
import java.io.*;
import java.util.stream.Collectors;
public class TextCommentsSeparation {

	public static void main(String[] args) {
		if(args.length < 3) {
			System.out.println(args[0]+ " "+ args[1] +" "+ args[2]);
			System.out.println("Usage: must be three arguments(source, destinationText, destinationComments)");
		} else {			
			try (BufferedReader reader = getBufferedReader(args[0]);
					PrintWriter writerText = getWriter(args[1]);
					PrintWriter writerComments = getWriter(args[2])
				){				
				reader.lines()
				.forEach(line -> getSourceWriter(writerText, writerComments, line).println(line));
			} catch (FileNotFoundException e) {				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}		
	}

	private static PrintWriter getSourceWriter(PrintWriter writerText,
			PrintWriter writerComments, String line) {
		return line.strip().startsWith("//") ? writerComments : writerText;
	}
	
	private static BufferedReader getBufferedReader(String source) throws FileNotFoundException {
		return new BufferedReader(new FileReader(source));
	}
	private static PrintWriter getWriter(String source) throws FileNotFoundException {
		return new  PrintWriter(source);
	}
	
//String regexComm = "\s*\t*(//).*";
}
