package src;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import net.datastructures.Vertex;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

@SuppressWarnings("unchecked")
public class ParisMetro {
    
    private int totalEdges = 0;
    private BufferedReader fileReader;
    private ArrayList<String> stopsInfo;
    private int[][] vertices = new int[377][377];
    private ArrayList<String> stopNameList = new ArrayList<String>(500);                   
    
    //hashmap for storing vertex objects
    private HashMap<String, Vertex<String> > verts = new HashMap<>();
    Graph<String, DefaultWeightedEdge> graph = 
            new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class); 
		
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
//            Vertex<String> new_vertex = new String();
            graph.addVertex(String.valueOf(stop_num));
//            verts.put(
//                    String.valueOf(stop_num), 
//                    new_vertex
//            );
        }
       
        //insert edges into graph
        for(int row_count = 0; row_count < vertices.length; row_count++){
           for(int col_count = 0; col_count < vertices[row_count].length; col_count++){
               String from_stop = String.valueOf(row_count);
               String to_stop = String.valueOf(col_count);
               int travel_time = vertices[row_count][col_count];
               
               
               if(from_stop != to_stop && travel_time != 0){  
                    if(travel_time < 0) travel_time = 0;
                    
                    DefaultWeightedEdge new_edge = graph.addEdge(from_stop, to_stop); 
                    graph.setEdgeWeight(new_edge, travel_time); 

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
     * @param remove_stop_num
     */
    protected void printShortestPathBetween(int from_stop_num, int to_stop_num, int remove_stop_num){
        String from_stop = String.valueOf(from_stop_num);
        String to_stop = String.valueOf(to_stop_num);
        String remove_stop = String.valueOf(remove_stop_num);
                
        DijkstraShortestPath<String, DefaultWeightedEdge> path_finder = 
                new DijkstraShortestPath<String, DefaultWeightedEdge>(graph);
        
        graph.removeVertex(remove_stop);
        
        GraphPath<String, DefaultWeightedEdge> shortest_path = 
                path_finder.getPath(from_stop, to_stop);
        
        List<DefaultWeightedEdge> sub_paths = shortest_path.getEdgeList();
        
        double total_travel_time = 0;
        
        System.out.print("Path: " + from_stop);
        for(DefaultWeightedEdge sub_path: sub_paths){
            
            if(sub_path.getWeight() == 0.0){ total_travel_time += 90; }
            else{total_travel_time += sub_path.getWeight();}
            
            System.out.print( " " + sub_path.getTarget());
            
//            System.out.println(
//                    sub_path.getSource().toString() + 
//                    ":" + 
//                    sub_path.getWeight() + 
//                    ":" +
//                    sub_path.getTarget().toString() + 
//                    " -> "
//                );
        }
        
        System.out.println();
        System.out.println("Time: " + total_travel_time);
        
    }

    protected void printShortestPathBetween(int from_stop_num, int to_stop_num){
        String from_stop = String.valueOf(from_stop_num);
        String to_stop = String.valueOf(to_stop_num);
                
        DijkstraShortestPath<String, DefaultWeightedEdge> path_finder = 
                new DijkstraShortestPath<String, DefaultWeightedEdge>(graph);
        
        GraphPath<String, DefaultWeightedEdge> shortest_path = 
                path_finder.getPath(from_stop, to_stop);
        
        List<DefaultWeightedEdge> sub_paths = shortest_path.getEdgeList();
        
        double total_travel_time = 0;
        
        System.out.print("Path: " + from_stop);
        for(DefaultWeightedEdge sub_path: sub_paths){
            
            if(sub_path.getWeight() == 0.0){ total_travel_time += 90; }
            else{total_travel_time += sub_path.getWeight();}
            
            System.out.print( " " + sub_path.getTarget());
            
//            System.out.println(
//                    sub_path.getSource().toString() + 
//                    ":" + 
//                    sub_path.getWeight() + 
//                    ":" +
//                    sub_path.getTarget().toString() + 
//                    " -> "
//                );
        }
        
        System.out.println();
        System.out.println("Time: " + total_travel_time);
        
    }
		

    public static void main (String[] args) throws Exception{
        ParisMetro PM = new ParisMetro("");
        
        String metroTxt_path = "C:/Users/Jack's acer/OneDrive/University/2017 - 2018/CSI 2110/assignments/Assignment 4/csi2110_assignment4_2017/a4/src/src/metro.txt";
        PM.analyzeFile(metroTxt_path);
        PM.generateGraph();
        
        System.out.print("Inputs: ");
        for(int input_counter = 0; input_counter < args.length; input_counter++){
            System.out.print(args[input_counter] + " ");
        }
        System.out.println();
        
        switch(args.length){
            case 1:
                System.out.println("Stop Name: " + PM.stopNameList.get(Integer.valueOf(args[0])));
            break;
            
            case 2:
                PM.printShortestPathBetween(
                        Integer.valueOf(args[0]), 
                        Integer.valueOf(args[1])
                );
            break;
            
            case 3:
                PM.printShortestPathBetween(
                        Integer.valueOf(args[0]), 
                        Integer.valueOf(args[1]),
                        Integer.valueOf(args[2])
                );
            break;
        }        
    }
}