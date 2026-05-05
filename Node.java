//This is a helper class for Dijsktras algorithm
//Stores station name, total time from the start and the line colour and gives structure for comparison

public class Node implements Comparable<Node>{
    private String stationName;
    private float totalTime;
    private String lineColour;

    //Getter and setter methods
    public Node(String stationName, float totalTime){
        this.stationName = stationName;
        this.totalTime = totalTime;
        this.lineColour = lineColour;
    }

    public String getStationName(){
        return stationName;
    }

    public float getTotalTime(){
        return totalTime;
    }

    public String getLineColour(){
        return lineColour;
    }

    //Telling PriorityQueue to put fastest time first
    @Override
    public int compareTo(Node other){
        return Float.compare(this.totalTime, other.totalTime);
    }
}
