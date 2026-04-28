//The class represents the connection between two stations
//Contains destination, time taken to get there and the line colour
public class Connection {
    private String destination;
    private float time; //Used float to save memory as not extremely precise time needed
    private String lineColour;


    public Connection(String destination, float time, String lineColour) {
        this.destination = destination;
        this.time = time;
        this.lineColour = lineColour;
    }

    public String getDestination() {
        return destination;
    }

    public float getTime() {
        return time;
    }

    public String getLineColour() {
        return lineColour;
    }

}
