package UI.StartWindow;

import javax.swing.*;
import java.awt.*;

public class SettingsWindow extends JFrame {
    public SettingsWindow(StartWindow some) {
        startWindow = some;
        init();
    }

    private StartWindow startWindow;

    private void init() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //получаем разрешение экрана
        JButton saveButton = new JButton("Сохранить");
        JButton returnButton = new JButton("Назад");
        JLabel tableSizeLabel = new JLabel("Размер поля:");
        JLabel cellSizeLabel = new JLabel("Размер ячейки:");
        JTextField tableSizeFieldX = new JTextField();
        JTextField cellSizeFieldX = new JTextField();
        JTextField tableSizeFieldY = new JTextField();
        JTextField cellSizeFieldY = new JTextField();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 3, 20, 20));
        panel.add(tableSizeLabel);
        panel.add(tableSizeFieldX);
        panel.add(tableSizeFieldY);
        panel.add(cellSizeLabel);
        panel.add(cellSizeFieldX);
        panel.add(cellSizeFieldY);
        panel.add(saveButton);
        panel.add(new JLabel(""));
        panel.add(returnButton);
        setResizable(false);

        add(panel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Для стартового окна используем 1/3 высоты экрана и 1/6 ширины, минимальный размер 300х100

        //setMinimumSize(new Dimension(300, 100)); //минимальный размер окна
        setSize(new Dimension(screenSize.width / 6, screenSize.height / 3));
        setPreferredSize(new Dimension(screenSize.width / 6, screenSize.height / 3));
        setMinimumSize(new Dimension(screenSize.width / 6, screenSize.height / 3));
        setMaximumSize(new Dimension(screenSize.width / 6, screenSize.height / 3));

        saveButton.addActionListener(e -> {
            if (isNumeric(tableSizeFieldX.getText()) && isNumeric(cellSizeFieldX.getText()) && isNumeric(tableSizeFieldY.getText()) && isNumeric(cellSizeFieldY.getText())) {
                startWindow.tableSizeX = Integer.parseInt(tableSizeFieldX.getText());
                startWindow.cellSizeX = Integer.parseInt(cellSizeFieldX.getText());
                startWindow.tableSizeY = Integer.parseInt(tableSizeFieldY.getText());
                startWindow.cellSizeY = Integer.parseInt(cellSizeFieldY.getText());
                startWindow.setVisible(true);
                this.dispose();
            } else {
                InfoWindow infowindow = new InfoWindow("Ошибка введенных данных! Введите корректные числа.");
                infowindow.setVisible(true);
                tableSizeFieldX.setText("");
                tableSizeFieldY.setText("");
                cellSizeFieldX.setText("");
                cellSizeFieldY.setText("");
            }
        });

        returnButton.addActionListener(e -> {
            startWindow.setVisible(true);
            this.dispose();
        });
        setTitle("Настройки");
    }

    public static boolean isNumeric(String str) {
        return str.matches("^?\\d+$");
    }
}
