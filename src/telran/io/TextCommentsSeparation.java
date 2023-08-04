package telran.io;
import java.io.*;
import java.util.stream.Collectors;
public class TextCommentsSeparation {

	public static void main(String[] args) {
		if(args.length < 3) {
			System.out.println(args[0]+ " "+ args[1] +" "+ args[2]);
			System.out.println("Usage: must be three arguments(source, destinationText, destinationComments)");
		} else {
			String regexComm = "[ ]*(//).*";
			try (BufferedReader reader = new BufferedReader(new FileReader(args[0]));
					PrintWriter writerText = new PrintWriter(args[1]);
					PrintWriter writerComments = new PrintWriter(args[2])){		
				
				reader.lines().filter(l -> !l.matches(regexComm)).forEach(l -> writerText.println(l));
				reader.close();
				BufferedReader reader1 = new BufferedReader(new FileReader(args[0]));
				reader1.lines().filter(l -> l.matches(regexComm)).forEach(l -> writerComments.println(l) );
				reader1.close();
			} catch (FileNotFoundException e) {				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
	}

}
