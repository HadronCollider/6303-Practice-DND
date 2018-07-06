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
        GridBagLayout gb = new GridBagLayout();
        panel.setLayout(gb);
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 10, 5, 10);
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 0.0;
        c.weighty = 0.0;
        gb.setConstraints(tableSizeLabel, c);
        tableSizeLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        panel.add(tableSizeLabel);
        c.anchor = GridBagConstraints.NORTH;
        c.gridx = 1;
        c.gridy = 0;


        gb.setConstraints(tableSizeFieldX, c);
        tableSizeFieldX.setFont(new Font("Verdana", Font.PLAIN, 20));
        tableSizeFieldX.setToolTipText("Укажите ширину поля");
        panel.add(tableSizeFieldX);

        c.anchor = GridBagConstraints.NORTH;
        c.gridx = 1;
        c.gridy = 1;
        c.insets = new Insets(0, 10, 20, 10);

        gb.setConstraints(tableSizeFieldY, c);
        tableSizeFieldY.setFont(new Font("Verdana", Font.PLAIN, 20));
        tableSizeFieldY.setToolTipText("Укажите высоту поля");
        panel.add(tableSizeFieldY);

        c.anchor = GridBagConstraints.NORTH;

        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(20, 10, 5, 10);

        gb.setConstraints(cellSizeLabel, c);
        cellSizeLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        panel.add(cellSizeLabel);

        c.anchor = GridBagConstraints.NORTH;
        c.gridx = 1;
        c.gridy = 2;
        c.insets = new Insets(20, 10, 5, 10);

        gb.setConstraints(cellSizeFieldX, c);
        cellSizeFieldX.setFont(new Font("Verdana", Font.PLAIN, 20));
        cellSizeFieldX.setToolTipText("Укажите ширину ячейки");
        panel.add(cellSizeFieldX);

        c.anchor = GridBagConstraints.NORTH;
        c.gridx = 1;
        c.gridy = 3;
        c.insets = new Insets(0, 10, 20, 10);

        gb.setConstraints(cellSizeFieldY, c);
        cellSizeFieldY.setFont(new Font("Verdana", Font.PLAIN, 20));
        cellSizeFieldY.setToolTipText("Укажите высоту ячейки");
        panel.add(cellSizeFieldY);

        c.anchor = GridBagConstraints.NORTH;
        c.gridx = 0;
        c.gridy = 4;
        c.insets = new Insets(40, 10, 0, 10);
        gb.setConstraints(saveButton, c);
        saveButton.setFont(new Font("Verdana", Font.PLAIN, 20));
        panel.add(saveButton);

        c.anchor = GridBagConstraints.NORTH;
        c.gridx = 1;
        c.gridy = 4;


        gb.setConstraints(returnButton, c);
        returnButton.setFont(new Font("Verdana", Font.PLAIN, 20));
        panel.add(returnButton);


        //panel.add(new JLabel(""));

        setResizable(false);

        add(panel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Для стартового окна используем 1/3 высоты экрана и 1/6 ширины, минимальный размер 300х100

        //setMinimumSize(new Dimension(300, 100)); //минимальный размер окна
        setSize(new Dimension(screenSize.width / 3, screenSize.height / 2));
        setPreferredSize(new Dimension(screenSize.width / 3, screenSize.height / 2));
        setMinimumSize(new Dimension(screenSize.width / 3, screenSize.height / 2));
        setMaximumSize(new Dimension(screenSize.width / 3, screenSize.height / 2));

        saveButton.addActionListener(e -> {
            if (isNumeric(tableSizeFieldX.getText()) &&  isNumeric(tableSizeFieldY.getText())) {
                startWindow.tableSizeX = Integer.parseInt(tableSizeFieldX.getText());
                //startWindow.cellSizeX = Integer.parseInt(cellSizeFieldX.getText());
                startWindow.tableSizeY = Integer.parseInt(tableSizeFieldY.getText());
                //startWindow.cellSizeY = Integer.parseInt(cellSizeFieldY.getText());
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
        Image image = Toolkit.getDefaultToolkit().createImage( getClass().getResource("icon_settings.png") );
        setIconImage( image );
    }

    public static boolean isNumeric(String str) {
        return str.matches("^?\\d+$");
    }
}
