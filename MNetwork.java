//This class represents the Main Rain network
//The class adds connections with the values of From and To, with Time taken and Line colour.

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

        //Create connection and add to "from" station
        Connection connection = new Connection(toN, time, colour);
        adjacencyList.get(fromN).add(connection);
    }

}

