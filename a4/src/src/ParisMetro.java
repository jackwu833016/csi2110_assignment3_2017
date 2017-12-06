package src;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import net.datastructures.AdjacencyMapGraph;
import net.datastructures.Entry;
import net.datastructures.Graph;
import net.datastructures.GraphAlgorithms;
import net.datastructures.Map;
import net.datastructures.Vertex;

@SuppressWarnings("unchecked")
public class ParisMetro {
    
    private int totalEdges = 0;
    private BufferedReader fileReader;
    private ArrayList<String> stopsInfo;
    private int[][] vertices = new int[377][377];
    
    //hashmap for storing vertex objects
    private HashMap<String, Vertex<String> > verts = new HashMap<>();
    Graph<String,Integer> graph = new AdjacencyMapGraph<>(true);

		
    public ParisMetro (String fileName) throws Exception, IOException {
        getStopInfoArray(fileName); //Allows one to read the .txt file
    }
        
    // --- Graph generation ---
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
                               
                readInLine = fileReader.readLine(); //String reading
                
//                System.out.println(stopName);
            }

        }catch (IOException exception){
//            System.out.println("Error occur: " + exception.toString());
        }
        
        return stopNameList;
    }
    
    /**
     * read in edges and save in int 2D array
     * @throws IOException 
     */
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
//            System.out.println(from + " -> " + to + " | " + weight);
        }
    }
    
    protected void analyzeFile(String filePath) throws IOException{
        stopsInfo = this.getStopInfoArray(filePath);
        this.popularVerticesArray();        
    }
    
    /**
     * printout our 2D array in human readable format
     */
    protected void printTable(){
       for(int row_count = 0; row_count < vertices.length; row_count++){
           for(int col_count = 0; col_count < vertices[row_count].length; col_count++){
               System.out.print(vertices[row_count][col_count] + "|");
           }
           System.out.println();
       }
    }
    
    // --- Graph operation ---
    /**
     * generate weight graph from int 2D array
     * @return 
     */
    protected void generateGraph(){
               
        //insert all vertices into graph
        for (int stop_num = 0; stop_num < vertices.length; stop_num++){
            verts.put(
                    String.valueOf(stop_num), 
                    graph.insertVertex(String.valueOf(stop_num))
            );
        }
       
        //insert edges into graph
        for(int row_count = 0; row_count < vertices.length; row_count++){
           for(int col_count = 0; col_count < vertices[row_count].length; col_count++){
               String from_stop = String.valueOf(row_count);
               String to_stop = String.valueOf(col_count);
               int travel_time = vertices[row_count][col_count];
               
               if(!(from_stop == to_stop || travel_time == 0)){        
                    graph.insertEdge(verts.get(from_stop), 
                                     verts.get(to_stop), 
                                     travel_time
                    );

                    totalEdges++;
               }
        
           }
        }
    }
    
    /**
     * run through all vertices, and return all the lines (stop numbers) as int array
     * @return allLines
     */
    protected int[] lineAnalyze(Graph graph){
        int[] foundLines = new int[10];
        
        //waiting to be impletemented
        
        return foundLines;
    }
    
    /**
     * find the shortest path between two input stops
     * print out path and total travel time
     * @param from_num
     * @param to_num 
     */
    protected void printShortestPathBetween(int from_num, int to_num){
        Vertex target_stop = verts.get(String.valueOf(to_num));
        
        Map<Vertex<String>, Integer> path = GraphAlgorithms.shortestPathLengths(
            graph, verts.get(String.valueOf(from_num))
        );
        
        for(Entry<Vertex<String>,Integer> stop: path.entrySet()){
            Vertex temp_vertex = stop.getKey();
            
            if(target_stop.equals(temp_vertex)) //found the target stop
                System.out.println("Travel time: " + stop.getValue());

        }
    }
		

    public static void main (String[] args) throws Exception{
        ParisMetro PM = new ParisMetro("");
        Graph<String,Integer> metroGraph;
        
        String metroTxt_path = "C:/Users/Jack's acer/OneDrive/University/2017 - 2018/CSI 2110/assignments/Assignment 4/csi2110_assignment4_2017/a4/src/src/metro.txt";
        System.out.println("reading: " + metroTxt_path);
        
        PM.analyzeFile(metroTxt_path);
        PM.generateGraph();
        System.out.println("Graph generated");
        
        //print out all vertexies
//        System.out.println(metroGraph);
        
//        PM.lineAnalyze(metroGraph);
        
        //find shortest path between
        PM.printShortestPathBetween(10, 200);

    }
}