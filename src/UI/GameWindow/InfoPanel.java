package UI.GameWindow;

import javax.swing.*;
import java.awt.*;


public class InfoPanel extends JPanel {
    private GameWindow window;
    private InfoTimer timer;
    private InfoMistakeCounter mistakeCounter;
    private InfoProgress progress;
    private JCheckBox mixCheckBox;
    private JButton undoButton;

    public JCheckBox getMixCheckBox() {
        return mixCheckBox;
    }

    public InfoPanel(GameWindow window) {
        this.window = window;

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        setLayout(layout);

        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.gridy = 0;
        constraints.weighty = 0;
        timer = new InfoTimer();
        add(timer, constraints);

        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.gridy++;
        constraints.weighty = 0.5;
        progress = new InfoProgress();
        progress.setVisible(true);
        add(progress, constraints);


        constraints.anchor = GridBagConstraints.PAGE_END;
        constraints.gridy++;
        constraints.weighty = 0.5;
        mistakeCounter = new InfoMistakeCounter();
        add(mistakeCounter, constraints);

        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.gridy++;
        constraints.weighty = 0.5;
        undoButton = new JButton("Отменить ход");
        undoButton.setVisible(false);
        undoButton.setFont(new Font("Arial", Font.BOLD, Toolkit.getDefaultToolkit().getScreenSize().height / 70));
        undoButton.addActionListener(e -> undo());
        add(undoButton, constraints);


        constraints.anchor = GridBagConstraints.PAGE_END;
        constraints.gridy++;
        constraints.weighty = 0.5;
        JCheckBox mixCheckBox = new JCheckBox("Перемешивать");
        this.mixCheckBox = mixCheckBox;
        mixCheckBox.setFont(new Font("Arial", Font.BOLD ,Toolkit.getDefaultToolkit().getScreenSize().height / 83));
        add(mixCheckBox, constraints);


        constraints.gridy++;
        constraints.weighty = 0;
        JButton mixButton = new JButton("Перемешать");
        mixButton.setFont(new Font("Arial", Font.BOLD, Toolkit.getDefaultToolkit().getScreenSize().height / 72));
        mixButton.addActionListener(e -> getWindow().getFieldPanel().mixField());
        add(mixButton, constraints);

        progress.setVisible(false);
        mistakeCounter.setVisible(false);
        setMinimumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().height / 8, Toolkit.getDefaultToolkit().getScreenSize().height / 2));
    }

    private GameWindow getWindow() {
        return window;
    }

    public InfoMistakeCounter getMistakeCounter() {
        return mistakeCounter;
    }

    public InfoProgress getProgress() {
        return progress;
    }

    public InfoTimer getTimer() {
        return timer;
    }

    public void startAll() {
        undoButton.setVisible(true);
        progress.setVisible(true);
        progress.setCurrentStep(1);
        mistakeCounter.setNumberOfMistakes(0);
        mistakeCounter.setVisible(true);
        timer.restart();
    }

    private void undo() {
        window.getFieldPanel().undo();
    }
}
