package src;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.IOException;

@SuppressWarnings("unchecked")
public class ParisMetro {
    
    private int totalVertices = 0;
    private ArrayList<String> stopsInfo;
    private BufferedReader fileReader;
    
    private int[][] vertices = new int[377][377];
		
    public ParisMetro (String fileName) throws Exception, IOException {
        getStopInfoArray(fileName); //Allows one to read the .txt file
    }
        
    /** 
     * Read metro.txt and retun an array containning all the stops 
     * @param filePath
     * @return StringArray
     * @throws java.io.IOException
     */
    protected ArrayList<String> getStopInfoArray(String filePath) throws IOException {

        ArrayList<String> stopNameList = new ArrayList<String>(500);                   
        String readInLine = "";

        try{

            fileReader = new BufferedReader(new FileReader(filePath));
            fileReader.readLine(); //Skips first line of .txt

            while (true) //Iterates til the "$"
            {
                if(readInLine.contains("$")) break;
                String[] Name_chunk = readInLine.split("\\s");
                Name_chunk[0] = "";
                String stopName = String.join(" ", Name_chunk);
                                
                stopNameList.add(stopName); //Adds the 2nd half of the string into the arraylist. For example, if text is 0000 hello, then only "hello" is stored in the ArrayList
                
                System.out.println(stopName);
                
                readInLine = fileReader.readLine(); //String reading
            }

        }catch (IOException exception){
            System.out.println("Error occur: " + exception.toString());
        }
        
        totalVertices = stopNameList.size(); //save nums of stop
        return stopNameList;
    }
    
    protected void popularVerticesArray() throws IOException{
        String readInLine = "";

        while(true){
            readInLine = fileReader.readLine();
            if(readInLine == null) break;
            
            String[] edgeInfo = readInLine.split("\\s");
            int from = Integer.parseInt(edgeInfo[0]);
            int to = Integer.parseInt(edgeInfo[1]);
            int weight = Integer.parseInt(edgeInfo[2]);
            
            //populate it
            vertices[from][to] = weight;
            
            System.out.println(from + " -> " + to + " | " + weight);
        }
    }
    
    protected void analyzeFile(String filePath) throws IOException{
        stopsInfo = this.getStopInfoArray(filePath);
        this.popularVerticesArray();        
    }
		

        
    public static void main (String[] args) throws Exception{
        ParisMetro PM = new ParisMetro("");
        
        String metroTxt_path = "C:/Users/Jack's acer/OneDrive/University/2017 - 2018/CSI 2110/assignments/Assignment 4/csi2110_assignment4_2017/a4/src/src/metro.txt";
        System.out.println("reading: " + metroTxt_path);
        
        PM.analyzeFile(metroTxt_path);
    }
}