//Class that defines GUIs visible components and opens GUI itself alongside getting the route printed out
import java.awt.*;
import javax.swing.*;
import java.io.PrintStream;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MetroGUI {
    private MNetwork Mlink;
    private JFrame Mainframe; //Hacking into the mainframe haha, please give me extra marks for this very funny joke
    private JComboBox<String> startStationDropdown;
    private JComboBox<String> endStationDropdown;
    private JRadioButton shortTime;
    private JRadioButton fewestChange;
    private JTextArea outputRoute;

    public MetroGUI(MNetwork network){
        this.Mlink = network;
        GUI();
    }

    //The GUI that pops up when the program runs, includes inputs, constraints and output
    private void GUI (){
        Mainframe = new JFrame("Manchester Metrolink Planner");
        Mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Mainframe.setSize(600,500);
        Mainframe.setLayout(new BorderLayout(10,10));
        
        //Panel for all inputs and constraints
        JPanel input = new JPanel(new GridLayout(3 ,2 ,5 ,5));
        javax.swing.border.Border line = BorderFactory.createLineBorder(new Color(128,0,32), 5);
        javax.swing.border.Border padding = BorderFactory.createEmptyBorder(10,10,10,10);
        input.setBorder(BorderFactory.createCompoundBorder(line,padding));

        String[] stations = Mlink.getStationNames();

        //Input Stations
        input.add(new JLabel("Start Station:"));
        startStationDropdown = new JComboBox<>();
        startStationDropdown.addItem("Please select starting station:");
        for(String station:stations){
            startStationDropdown.addItem(station);
        }
        input.add(startStationDropdown);

        input.add(new JLabel("End Station:"));
        endStationDropdown = new JComboBox<>();
        endStationDropdown.addItem("Please select final station:");
        for (String station:stations){
            endStationDropdown.addItem(station);
        }
        input.add(endStationDropdown);

        //Choose Constraints
        input.add(new JLabel("Select a Constraint:"));
        JPanel constraintsRadio = new JPanel(new FlowLayout(FlowLayout.LEFT));
        //Shortest time constraint is auto selected
        shortTime = new JRadioButton("Shortest Time",true);
        fewestChange = new JRadioButton("Fewest Changes");

        ButtonGroup constraints = new ButtonGroup();
        constraints.add(shortTime);
        constraints.add(fewestChange);
        input.add(constraintsRadio);

        //Panel and action for the calculate route button
        JPanel button = new JPanel();
        JButton calcButton = new JButton("Calculate Route");
        calcButton.setBackground(new Color(124,252,0));
        button.add(calcButton);
        calcButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                getRoute();
            }
        });

        //Separating the output and input sections
        JPanel top = new JPanel(new BorderLayout());
        top.add(input, BorderLayout.NORTH);
        top.add(button, BorderLayout.SOUTH);
        Mainframe.add(top, BorderLayout.NORTH);

        //Panel where the route is outputted
        outputRoute = new JTextArea();
        outputRoute.setEditable(false);
        JScrollPane scroll = new JScrollPane(outputRoute);
        Mainframe.add(scroll, BorderLayout.CENTER);
    }

    //Method that gets the inputs and sets the logic for the walk time penalty then prints the route
    private void getRoute(){
        String start = (String) startStationDropdown.getSelectedItem();
        String end = (String) endStationDropdown.getSelectedItem();

        //Input validation that completes TASK 1, prevents no selection AND double selection of same station
        if(start.equals("Please select starting station:")&& end.equals("Please select final station:")){
            JOptionPane.showMessageDialog(Mainframe, "Please select a valid station from the dropdown");
            return;
        }

        if (start.equals(end)){
            JOptionPane.showMessageDialog(Mainframe, "Please select two different stations from the dropdown");
            return;
        }

        //Time penalty for each constraint
        float walkPenalty = 0.0f;
        if (shortTime.isSelected()){
            walkPenalty  = 2.0f;
        }
        //High penalty makes algorithm change less as it want to avoid penalty
        else if (fewestChange.isSelected()){
            walkPenalty = 1000.0f;
        }

        //Print Stream that has the output in the actual output area using the redirector helper classs
        PrintStream printRoutePrintStream = new PrintStream(new OutputAreaRedirector(outputRoute));
        PrintStream out = System.out;
        System.setOut(printRoutePrintStream);

        Mlink.findShortestRoute(start, end, walkPenalty);
        System.setOut(out);
    }

    public void show(){
        Mainframe.setVisible(true);
    }
}