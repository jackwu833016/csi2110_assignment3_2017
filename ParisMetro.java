
import java.io.BufferedReader;
import java.io.FileReader;
import net.datastructures.ArrayList;
import java.io.IOException;

@SuppressWarnings("unchecked")
public class ParisMetro {

		private String tempName;
		private String[] tempNames;
		
		public ParisMetro (String fileName) throws Exception, IOException {
			read(fileName); //Allows one to read the .txt file
		}
		
		protected void read(String fileName) throws Exception, IOException {
			BufferedReader mapFile = new BufferedReader(new FileReader(fileName));
		}
		
		public static void main (String[] args){

            ArrayList<String> stopName = new ArrayList(); //Store everything in ArrayList
            String name = "";

            try{

                BufferedReader stationName = new BufferedReader(new FileReader("docs/metro.txt"));
                stationName.readLine(); //Skips first line of .txt
                
                while (name != "$") //Iterates til the "$"
                {
                    name = stationName.readLine(); //String reading
                    String[] tempNames = name.split("\\s"); //Split the String, store into array of strings called tempNames[]	
                    stopName.add(stopName.size(), (tempNames[1])); //Adds the 2nd half of the string into the arraylist. For example, if text is 0000 hello, then only "hello" is stored in the ArrayList
                    
                    System.out.println(tempNames[0] + ", " + tempNames[1]);
                }

            }catch (IOException exception){
                System.out.println("Error occur: " + exception.toString());
            }

            return;

		}
}