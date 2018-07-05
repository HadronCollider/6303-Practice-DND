package UI.GameWindow;

import javax.swing.*;
import java.awt.*;

public class InfoProgress extends JLabel {

    private int numberOfStages = 1;
    private int currentStage;

    public InfoProgress() {
        currentStage = 1;
        setFont(new Font("Arial", Font.BOLD, 20));
        display();
    }

    void increase() {
        if(currentStage != numberOfStages) {
            currentStage++;
        }
        display();
    }

    public void setNumberOfStages(int numberOfStages) {
        this.numberOfStages = numberOfStages;
    }

    void display() {
        setText("Этап " + currentStage + "/" + numberOfStages);
    }
}
