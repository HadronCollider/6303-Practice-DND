package UI.GameWindow;

import javax.swing.*;
import java.awt.*;

import static UI.StartWindow.StartWindow.isNumeric;

public class SettingsWindow extends JFrame {

    private GameWindow window;
    private int fieldSizeX, fieldSizeY, cellSizeX, cellSizeY;

    public SettingsWindow(GameWindow window, int fieldSizeX, int fieldSizeY, int cellSizeX, int cellSizeY) throws HeadlessException {
        this.window = window;
        this.fieldSizeX = fieldSizeX;
        this.fieldSizeY = fieldSizeY;
        this.cellSizeX = cellSizeX;
        this.cellSizeY = cellSizeY;

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
        setMinimumSize(new Dimension(screenSize / 15, screenSize / 50));
        JTextField cellSizeFieldX = new JTextField();
        cellSizeFieldX.setFont(font);
        JTextField tableSizeFieldY = new JTextField();
        tableSizeFieldY.setFont(font);
        JTextField cellSizeFieldY = new JTextField();
        cellSizeFieldY.setFont(font);


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
                SwingUtilities.invokeLater(() -> {

                });
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Введите корректные данные.", "Ошибка данных", JOptionPane.ERROR_MESSAGE, null);
                tableSizeFieldX.setText("6");
                tableSizeFieldY.setText("6");
                cellSizeFieldX.setText("150");
                cellSizeFieldY.setText("75");
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
