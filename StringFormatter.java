import java.io.*;
import java.util.ArrayList;

public class StringFormatter
{
	String fileName = "NoteFrames.txt";
	String line = null;
	public StringFormatter()
	{
		try {
		    // FileReader reads text files in the default encoding.
		    FileReader fileReader = new FileReader(fileName);

		    // Always wrap FileReader in BufferedReader.
		    BufferedReader bufferedReader = new BufferedReader(fileReader);

		    while((line = bufferedReader.readLine()) != null) {
				String segment = line.substring(line.indexOf(","));
				ArrayList<Integer> lanes = getIntsFromStringList(segment);
				for(Integer i : lanes)
					System.out.println("notes.add(new Note(" + line.substring(2, line.indexOf(",")) + ", " + i + "));");
		    }

		    // Always close files.
		    bufferedReader.close();
		}
		catch(FileNotFoundException ex) {
		    System.out.println("Unable to open file '" + fileName + "'");
		}
		catch(IOException ex) {
		    System.out.println(
			"Error reading file '"
			+ fileName + "'");
		    // Or we could just do this:
		    // ex.printStackTrace();
		}
	}
	public ArrayList<Integer> getIntsFromStringList(String nums)
	{
		ArrayList<Integer> output = new ArrayList<>();
		for(int i = 0; i < nums.length(); i++)
		{
			char c = nums.charAt(i);
			int ascii = c;
			if(c >= 48 && c <= 57)
				output.add(c - 48);
		}
		return output;
	}
	public static void main(String args[])
	{
		StringFormatter app = new StringFormatter();
	}
}