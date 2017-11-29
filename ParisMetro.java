
import java.io.BufferedReader;
import java.io.FileReader;

public class ParisMetro {

		private String tempName;
		private String[] tempNames;
		
		public ParisMetro (String fileName) throws Exception, IOException {
			read(fileName); //Allows one to read the .txt file
		}
		
		protected void read(String fileName) throws Exception, IOExpection{
			BufferedReader mapFile = new BufferedReader(new FileReader(fileName));
		}
		
		public static void main (String[] args){
			BufferReader stationName = new BufferReader(new File(fileName));
			stationName.readLine() //Skips first line of .txt
			String name = stationName.readLine(); //Start iterating on 2nd line
			ArrayList<String> stopName = new ArrayList(); //Store everything in ArrayList
			while (name != "$") //Iterates til the "$"
			{
				tempNames = name.split("\\s"); //Split the String, store into array of strings called tempNames[]	
				stopName.add(tempNames[1]); //Adds the 2nd half of the string into the arraylist. For example, if text is 0000 hello, then only "hello" is stored in the ArrayList
			}
		}
}