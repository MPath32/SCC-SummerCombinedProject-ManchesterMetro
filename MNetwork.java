//This class represents the Main Rain network
//The class adds connections with the values of From and To, with Time taken and Line colour.

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

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

        //Creatin connection and add to the "from" station
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

            System.out.println("Loaded successfully");
        }

        //Java will throw error without IOException with reading csv
        catch (IOException exception){
            System.err.println("Error reading file");
        } 
    }

    //Getter method that converts key of adjcency list into array so GUI dropdown menu contains names of all statiosn
    public String[] getStationNames() {
        return adjacencyList.keySet().stream().sorted().toArray(String[]::new);
    }

    //Method to find the shortest route
    public void findShortestRoute(String start, String end, float walkPenalty){
        //Tracking maps that keep minimum time, previous station and previous line colour
        Map<String, Float> minTimes = new HashMap<>();
        Map<String, String> previousStation = new HashMap<>();
        Map<String, String> previousLine = new HashMap<>();

        PriorityQueue<Node> queue = new PriorityQueue<>();

        minTimes.put(start, 0.0f);
        queue.add(new Node (start, 0.0f, null));

        //Main algo
        while(!queue.isEmpty()){
            Node current = queue.poll();
            String currentStation = current.getStationName();
            String currentLine = current.getLineColour();

            //Stop search if reach destination and skipping if faster route found
            if(currentStation.equals(end)){
                break;
            }

            if (current.getTotalTime() > minTimes.getOrDefault(currentStation, Float.MAX_VALUE)) {
                continue;
            }

            //Checking all connections and if new path is faster choose, sets walking time penalty as variable
            if(adjacencyList.containsKey(currentStation)){
                for(Connection conn : adjacencyList.get(currentStation)){
                    String neighbour = conn.getDestination();
                    String nextLine = conn.getLineColour();
                    float newTime = current.getTotalTime() + conn.getTime();

                    if (currentLine != null && !currentLine.equals(nextLine)){
                        newTime += walkPenalty;
                    }

                    if (newTime < minTimes.getOrDefault(neighbour, Float.MAX_VALUE)){
                        minTimes.put(neighbour, newTime);
                        previousStation.put(neighbour, currentStation);
                        previousLine.put(neighbour, conn.getLineColour());
                        queue.add(new Node(neighbour, newTime, nextLine));
                    }
                }
            }
        }
        printRoute(start, end, previousStation, previousLine, minTimes);
    }

    //Method to print the route by backtracking the path taken
    private void printRoute(String start, String end, Map<String, String> previousStation, Map<String, String> previousLine, Map<String, Float> minTimes) {
            if (!minTimes.containsKey(end)) {
                System.out.println("No route found from " + start + " to " + end);
                return;
            }

            //Building the path ting 
            List<String> route = new ArrayList<>();
            List<String> lines = new ArrayList<>();
            String current = end;

            while (current != null && !current.equals(start)) {
                route.add(current);
                lines.add(previousLine.get(current));
                current = previousStation.get(current);
            }
            route.add(start);

            Collections.reverse(route);
            Collections.reverse(lines);

            //Main format for printin the route FOR TASK 3
            System.out.println("--- Best Route ---\n");
            System.out.println("From: " + start + " To: " + end);
            System.out.println("Total Time: " + minTimes.get(end) + " minutes\n");
            String currentLine = lines.get(0);
            System.out.println("Board the " + currentLine + " line at " + route.get(0));

            for (int i = 1; i < route.size(); i++) {
                System.out.println(" - " + route.get(i));
                
                if (i < lines.size() && !lines.get(i).equals(currentLine)) {
                    currentLine = lines.get(i);
                    System.out.println("\n*** Change to the " + currentLine + " line ***\n");
                }
            }
            System.out.println("Arrive at " + end);
            System.out.println("------------------\n");
        }

}
