package UI.GameWindow;

import javax.swing.*;
import java.awt.*;

import static UI.StartWindow.StartWindow.isNumeric;

public class SettingsWindow extends JFrame {

    private GameWindow window;

    public SettingsWindow(GameWindow window, int fieldSizeX, int fieldSizeY, int cellSizeX, int cellSizeY) throws HeadlessException {
        this.window = window;


        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        setTitle("Настройки");

        constraints.weightx = 0;

        JPanel panel = new JPanel();
        panel.setLayout(layout);
        constraints.gridx = 0;
        constraints.gridy = 0;

        Font font = new Font("Default", Font.PLAIN, Toolkit.getDefaultToolkit().getScreenSize().height / 50);

        int screenSize = Toolkit.getDefaultToolkit().getScreenSize().height;
        constraints.insets = new Insets(screenSize / 200, screenSize / 100, screenSize / 200, screenSize / 100);
        JLabel fieldLabel = new JLabel("Размер поля");
        fieldLabel.setFont(font);

        panel.add(fieldLabel, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;

        JLabel cellLabel = new JLabel("Размер ячеек");
        cellLabel.setFont(font);
        panel.add(cellLabel, constraints);

        constraints.weightx = 1;

        JTextField tableSizeFieldX = new JTextField();
        tableSizeFieldX.setFont(font);
        tableSizeFieldX.setText(String.valueOf(fieldSizeX));
        tableSizeFieldX.setToolTipText("Укажите ширину поля (>=3) ");

        JTextField cellSizeFieldX = new JTextField();
        cellSizeFieldX.setFont(font);
        cellSizeFieldX.setText(String.valueOf(cellSizeX));
        cellSizeFieldX.setToolTipText("Укажите ширину ячейки (>=0) ");

        JTextField tableSizeFieldY = new JTextField();
        tableSizeFieldY.setFont(font);
        tableSizeFieldY.setText(String.valueOf(fieldSizeY));
        tableSizeFieldY.setToolTipText("Укажите высоту поля (>=3) ");

        JTextField cellSizeFieldY = new JTextField();
        cellSizeFieldY.setFont(font);
        cellSizeFieldY.setText(String.valueOf(cellSizeY));
        cellSizeFieldY.setToolTipText("Укажите высоту ячейки (>=0) ");


        constraints.fill = GridBagConstraints.HORIZONTAL;

        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(tableSizeFieldX, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        panel.add(tableSizeFieldY, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(cellSizeFieldX, constraints);

        constraints.gridx = 2;
        constraints.gridy = 1;
        panel.add(cellSizeFieldY, constraints);


        JButton button = new JButton("Ок");
        button.setMaximumSize(new Dimension(screenSize / 9, screenSize / 9));
        button.addActionListener(e -> {
            if (isNumeric(tableSizeFieldX.getText()) &&  isNumeric(tableSizeFieldY.getText())) {
                int nfieldSizeX = Integer.parseInt(tableSizeFieldX.getText());
                int ncellSizeX = Integer.parseInt(cellSizeFieldX.getText());
                int nfieldSizeY = Integer.parseInt(tableSizeFieldY.getText());
                int ncellSizeY = Integer.parseInt(cellSizeFieldY.getText());
                if(nfieldSizeX < 3 || nfieldSizeY < 3 || cellSizeX <= 0 || cellSizeY <= 0) {
                    JOptionPane.showMessageDialog(null, "Введите корректные данные.", "Ошибка данных", JOptionPane.ERROR_MESSAGE, null);
                    return;
                }
                window.resize(nfieldSizeX, nfieldSizeY, ncellSizeX, ncellSizeY);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Введите корректные данные.", "Ошибка данных", JOptionPane.ERROR_MESSAGE, null);
                tableSizeFieldX.setText(String.valueOf(fieldSizeX));
                tableSizeFieldY.setText(String.valueOf(fieldSizeY));
                cellSizeFieldX.setText(String.valueOf(cellSizeX));
                cellSizeFieldY.setText(String.valueOf(cellSizeY));
            }
        });

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weightx = 2;
        constraints.gridwidth = 3;
        panel.add(button, constraints);


        setContentPane(panel);
        setLocationRelativeTo(null);
        setSize(new Dimension(screenSize / 3, screenSize / 7));
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        Image image = Toolkit.getDefaultToolkit().createImage( getClass().getResource("/UI/StartWindow/icon_settings.png") );
        setIconImage( image );
    }


}
