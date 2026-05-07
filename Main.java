//Main that brings the whole code together and brings up GUI after reading CSV
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        MNetwork Mlink = new MNetwork();
        Mlink.readCSV("Metrolink_times_linecolour.csv"); 

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MetroGUI GUI = new MetroGUI(Mlink);
                GUI.show();
            }
        });
    }
}