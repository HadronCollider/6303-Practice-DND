package UI.GameWindow;

import javax.swing.*;
import java.awt.*;


public class InfoPanel extends JPanel {
    private GameWindow window;
    private InfoTimer timer;
    private InfoErrorCounter errorCounter;
    private InfoProgress progress;

    public InfoPanel(GameWindow window) {
        this.window = window;

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        setLayout(layout);

        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.gridy = 0;
        constraints.weighty = 0.5;
        timer = new InfoTimer();
        add(timer, constraints);


        constraints.anchor = GridBagConstraints.PAGE_END;
        constraints.gridy++;
        constraints.weighty = 0.5;
        errorCounter = new InfoErrorCounter();
        add(errorCounter, constraints);

        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.gridy++;
        constraints.weighty = 0.5;
        progress = new InfoProgress();
        progress.setVisible(true);
        add(progress, constraints);

        constraints.anchor = GridBagConstraints.PAGE_END;
        constraints.gridy++;
        constraints.weighty = 0.5;
        JCheckBox mixCheckBox = new JCheckBox("Премешивать");
        add(mixCheckBox, constraints);


        constraints.gridy++;
        constraints.weighty = 0;
        JButton mixButton = new JButton("Перемешать");
        mixButton.setFont(new Font("Arial", Font.BOLD, 15));
        mixButton.addActionListener(e -> getWindow().getField().mixField());
        add(mixButton, constraints);


        setMinimumSize(new Dimension(150, 300));
    }

    private GameWindow getWindow() {
        return window;
    }

    public InfoTimer getTimer() {
        return timer;
    }
}
