This is a Journey Planner for the Manchester Metrolink. The language used to code this is Java and all libraries are in JDK.
The code is split for best practices with code modularity.
The following explains each file.

Connection.java:
This class represents the connection between two different stations. Its purpose is to provide the information:
- Where the connection is going
- How long it'll take to get there
- The linecolour of the connection.

MNetwork.java:
This class is the Main rail network. It creates the methods for creating the Hashmap, reading the csv, finding the shortest route and printing the route.

Node.java:
This is a helper class for the Dijsktras algorithm in the MNetwork class. Its purpose is also to store the station name, line colour and total time its taken to get there.
I made this class separate from the connection class because it tells the PriorityQueue in the MNetwork class to put the fastest time first and also because I forgot that the connection class existed until it was too late.

MetroGUI.java:
This class creates the main visible GUI, where users choose two stations and journey constraint and then recieve an output of their route. 
The class also contains the method for getting the route fromMNetwork class.
The class uses the OutputAreaRedirector.java helper class to get the output to be in the main output text area rather than the terminal.

OutputAreaRedirector.java:
As just mentioned, is helper class that I created because I had an error where the output kept going into the terminal rather than the main output text area.

Thank you for reading.
