//This is a helper class for Dijsktras algorithm
//Stores station name and its total time from the start and gives structure for comparison

public class Node implements Comparable<Node>{
    private String stationName;
    private float totalTime;

    //Getter and setter methods
    public Node(String stationName, float totalTime){
        this.stationName = stationName;
        this.totalTime = totalTime;
    }

    public String getStationName(){
        return stationName;
    }

    public float getTotalTime(){
        return totalTime;
    }

    //Telling PriorityQueue to put fastest time first
    @Override
    public int compareTo(Node other){
        return Float.compare(this.totalTime, other.totalTime);
    }
}
