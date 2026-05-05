//This class represents the Main Rain network
//The class adds connections with the values of From and To, with Time taken and Line colour.

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MNetwork {
    //Adjacency list used as it is for graph and allows effective connection storage
    private Map<String, List<Connection>> adjacencyList;
    
    public MNetwork(){
        //Main HashMap for the network
        this.adjacencyList = new HashMap<>();
    }

    public void addConnection(String fromN, String toN, float time, String colour){
        //Adding new stations into an empty array list
        adjacencyList.putIfAbsent(fromN, new ArrayList<>());
        adjacencyList.putIfAbsent(toN, new ArrayList<>());

        //Create connection and add to the "from" station
        Connection connection = new Connection(toN, time, colour);
        adjacencyList.get(fromN).add(connection);
    }


    //Method to read CSV and add connections
    public void readCSV(String filename){
        try (BufferedReader br = new BufferedReader(new FileReader(filename))){
            String line;

            //Used to ignore the header and prevent errors
            br.readLine();
        
            //Default line colourr set as Null
            String currentLinecolour = "Null";

            while ((line = br.readLine()) != null){
                //Splitting the CSV line into array parts is how we determine the difference between line colour and connection
                String[] parts = line.split(",");

                //If the array has 1 element it is a line colour
                if (parts.length == 1){
                    currentLinecolour = parts[0];
                    continue;
                }
                
                //If array has 3 elements it is a station connection
                if(parts.length == 3){
                    String startStation = parts[0];
                    String endStation = parts[1];
                    float time = Float.parseFloat(parts[2]);

                    addConnection(startStation, endStation, time, currentLinecolour);
                    addConnection(endStation, startStation, time, currentLinecolour);
                }
            }

        //Success and Error messages
            System.out.println("Loaded successfully");

        }

        //Java will throw error without IOException with reading csv
        catch (IOException exception){
            System.err.println("Error reading file");
        } 
    }
}
