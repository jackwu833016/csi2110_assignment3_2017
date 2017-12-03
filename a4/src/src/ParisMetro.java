package src;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.IOException;

@SuppressWarnings("unchecked")
public class ParisMetro {
    
    private String tempName;
    private String[] tempNames;
		
    public ParisMetro (String fileName) throws Exception, IOException {
        getStopInfoArray(fileName); //Allows one to read the .txt file
    }
        
    /**
     * @param fileName
     * @return StringArray
     * @throws java.io.IOException
     */
    protected ArrayList<String> getStopInfoArray(String fileName) throws IOException {

        ArrayList<String> stopNameList = new ArrayList<String>(376);                   
        String readInLine = "";

        try{

            BufferedReader stationName = new BufferedReader(new FileReader(fileName));
            stationName.readLine(); //Skips first line of .txt

            while (readInLine != "$") //Iterates til the "$"
            {
                readInLine = stationName.readLine(); //String reading

                String stopName = readInLine.split("\\s")[1];
                stopNameList.add(stopName); //Adds the 2nd half of the string into the arraylist. For example, if text is 0000 hello, then only "hello" is stored in the ArrayList

            }

        }catch (IOException exception){
            System.out.println("Error occur: " + exception.toString());
        }

        return stopNameList;
    }
		
    public static void main (String[] args) throws Exception{
        ParisMetro PM = new ParisMetro("");
        PM.getStopInfoArray("metro.txt");
    }
}