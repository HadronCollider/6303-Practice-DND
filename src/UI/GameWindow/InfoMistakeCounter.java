package UI.GameWindow;

import javax.swing.*;
import java.awt.*;

public class InfoMistakeCounter extends JLabel {
    private int mistakeCounter;

    public InfoMistakeCounter() {
        setFont(new Font("Arial", Font.BOLD, 20));
        display();
    }

    private void display() {
        setText("Ошибок: " + mistakeCounter);
    }

    public void setNumberOfMistakes(int numberOfMistakes) {
        this.mistakeCounter = numberOfMistakes;
        display();
    }
}
