package UI.GameWindow;

import javax.swing.*;
import java.awt.*;

public class InfoErrorCounter extends JLabel {
    private int errorCounter;

    public InfoErrorCounter() {
        setFont(new Font("Arial", Font.BOLD, 20));
        display();
    }

    private void display() {
        setText("Ошибок: " + errorCounter);
    }

    public void increase() {
        errorCounter++;
        display();
    }
}
