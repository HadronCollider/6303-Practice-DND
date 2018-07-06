package UI.StartWindow;

import UI.GameWindow.GameWindow;
import sun.reflect.generics.tree.FormalTypeParameter;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.text.Format;


public class StartWindow extends JFrame {

    public StartWindow() {
        init();
    }

    public int tableSizeX = 0;
    public int tableSizeY = 0;
    public int cellSizeX = 0;
    public int cellSizeY = 0;

    private void init() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //получаем разрешение экрана
        JButton startButton = new JButton("Начать игру");
        JButton continueButton = new JButton("Продолжить игру");
        JButton settingsButton = new JButton("Настройки");
        JButton exitButton = new JButton("Выйти");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalStrut(screenSize.height / 12));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setFont(new Font("Verdana", Font.PLAIN, 20));
        panel.add(startButton);
        /*panel.add(Box.createVerticalStrut(screenSize.height / 100));
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        continueButton.setFont(new Font("Verdana", Font.PLAIN, 20));
        panel.add(continueButton);*/
        panel.add(Box.createVerticalStrut(screenSize.height / 100));
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        settingsButton.setFont(new Font("Verdana", Font.PLAIN, 20));
        panel.add(settingsButton);
        panel.add(Box.createVerticalStrut(screenSize.height / 100));
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setFont(new Font("Verdana", Font.PLAIN, 20));
        panel.add(exitButton);
        setResizable(false);
        //panel.add(new JFormattedTextField());
        add(panel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Для стартового окна используем 1/3 высоты экрана и 1/6 ширины, минимальный размер 300х100

        //setMinimumSize(new Dimension(300, 100)); //минимальный размер окна
        setSize(new Dimension(screenSize.width / 6, screenSize.height / 3));
        setPreferredSize(new Dimension(screenSize.width / 6, screenSize.height / 3));
        setMinimumSize(new Dimension(screenSize.width / 6, screenSize.height / 3));
        setMaximumSize(new Dimension(screenSize.width / 6, screenSize.height / 3));

        startButton.addActionListener(e -> {
            GameWindow gameWindow = new GameWindow();
            gameWindow.setVisible(true);
            this.dispose();
        });

        continueButton.addActionListener(e -> {
            JFileChooser fileopen = new JFileChooser();
            int ret = fileopen.showDialog(null, "Открыть файл");
        });

        settingsButton.addActionListener(e -> {
            SettingsWindow settingsWindow = new SettingsWindow(this);
            settingsWindow.setVisible(true);
            this.setVisible(false);
        });

        exitButton.addActionListener(e -> this.dispose());
        setTitle("Kana");

        Image image = Toolkit.getDefaultToolkit().createImage( getClass().getResource("icon.png") );
        setIconImage( image );
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StartWindow startWindow = new StartWindow();
            startWindow.setVisible(true);
        });
    }

}
