package UI.GameWindow;

import javax.swing.*;
import java.awt.*;

public class InfoProgress extends JLabel {

    private int numberOfSteps = 1;
    private int currentStep;

    public InfoProgress() {
        currentStep = 1;
        setFont(new Font("Arial", Font.BOLD, 20));
        display();
    }

    void increase() {
        if(currentStep != numberOfSteps) {
            currentStep++;
        }
        display();
    }

    public void setNumberOfSteps(int numberOfSteps) {
        this.numberOfSteps = numberOfSteps;
        display();
    }

    void display() {
        setText("Этап " + currentStep + "/" + numberOfSteps);
    }
}
