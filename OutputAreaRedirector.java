//Helper class that makes it so that printRoute() in MNetwork prints the route into the GUI instead of the termianl
import javax.swing.*;
import java.io.OutputStream;

public class OutputAreaRedirector extends OutputStream{
    private JTextArea textArea;

    public OutputAreaRedirector(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void write(int b){
        textArea.append(String.valueOf((char)b));
    }
}

