package UI.StartWindow;

import UI.GameWindow.GameWindow;

import javax.swing.*;
import java.awt.*;


public class StartWindow extends JFrame{

    public StartWindow() {
        init();
    }

    private void init() {

        JButton startButton = new JButton("Начать игру");
        JButton continueButton = new JButton("Продолжить игру");
        JButton exitButton = new JButton("Выйти");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(startButton);
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(continueButton);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(exitButton);
        add(panel);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(300, 100));
        setSize(new Dimension(200,300));


        startButton.addActionListener(e -> {
            GameWindow gameWindow = new GameWindow();
            gameWindow.setVisible(true);
            this.dispose();
        });

        continueButton.addActionListener(e -> {
            JFileChooser fileopen = new JFileChooser();
            int ret = fileopen.showDialog(null, "Открыть файл");
        });

        exitButton.addActionListener(e -> this.dispose());
        setTitle("Kana");
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StartWindow startWindow = new StartWindow();
            startWindow.setVisible(true);
        });
    }

}
