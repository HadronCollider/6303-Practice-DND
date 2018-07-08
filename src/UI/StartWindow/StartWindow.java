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

    public int tableSizeX = 6;
    public int tableSizeY = 6;
    public int cellSizeX = 0;
    public int cellSizeY = 0;

    private void init() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //получаем разрешение экрана
        JButton startButton = new JButton("Начать игру");
        JButton continueButton = new JButton("Продолжить игру");
        JButton exitButton = new JButton("Выйти");
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
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 10, 5, 10);
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 0.0;
        c.weighty = 0.0;
        gb.setConstraints(startButton, c);
        startButton.setFont(new Font("Verdana", Font.PLAIN, 20));
        panel.add(startButton);
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 0.0;
        c.weighty = 0.0;
        gb.setConstraints(tableSizeLabel, c);
        tableSizeLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        panel.add(tableSizeLabel);

        c.anchor = GridBagConstraints.NORTH;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 1;
        gb.setConstraints(tableSizeFieldX, c);
        tableSizeFieldX.setFont(new Font("Verdana", Font.PLAIN, 20));
        tableSizeFieldX.setText("6");
        tableSizeFieldX.setToolTipText("Укажите ширину поля");
        panel.add(tableSizeFieldX);

        c.anchor = GridBagConstraints.NORTH;
        c.gridx = 2;
        c.gridy = 1;
        gb.setConstraints(tableSizeFieldY, c);
        tableSizeFieldY.setFont(new Font("Verdana", Font.PLAIN, 20));
        tableSizeFieldY.setText("6");
        tableSizeFieldY.setToolTipText("Укажите высоту поля");
        panel.add(tableSizeFieldY);

        c.anchor = GridBagConstraints.NORTH;
        c.gridx = 0;
        c.gridy = 2;
        gb.setConstraints(cellSizeLabel, c);
        cellSizeLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        panel.add(cellSizeLabel);

        c.anchor = GridBagConstraints.NORTH;
        c.gridx = 1;
        c.gridy = 2;
        c.ipadx = 20;
        gb.setConstraints(cellSizeFieldX, c);
        cellSizeFieldX.setFont(new Font("Verdana", Font.PLAIN, 20));
        cellSizeFieldX.setText("0");
        cellSizeFieldX.setToolTipText("Укажите ширину ячейки");
        panel.add(cellSizeFieldX);

        c.anchor = GridBagConstraints.NORTH;
        c.gridx = 2;
        c.gridy = 2;
        gb.setConstraints(cellSizeFieldY, c);
        cellSizeFieldY.setFont(new Font("Verdana", Font.PLAIN, 20));
        cellSizeFieldY.setText("0");
        cellSizeFieldY.setToolTipText("Укажите высоту ячейки");
        panel.add(cellSizeFieldY);

        c.gridheight = 1;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 3;
        gb.setConstraints(continueButton, c);
        continueButton.setFont(new Font("Verdana", Font.PLAIN, 20));
        panel.add(continueButton);

        c.gridheight = 1;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 4;
        gb.setConstraints(exitButton, c);
        exitButton.setFont(new Font("Verdana", Font.PLAIN, 20));
        panel.add(exitButton);

        setResizable(false);
        //panel.add(new JFormattedTextField());
        add(panel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Для стартового окна используем 1/3 высоты экрана и 1/6 ширины, минимальный размер 300х100

        //setMinimumSize(new Dimension(300, 100)); //минимальный размер окна
        setSize(new Dimension(screenSize.width / 5, screenSize.height / 3));
        setPreferredSize(new Dimension(screenSize.width / 5, screenSize.height / 3));
        setMinimumSize(new Dimension(screenSize.width / 5, screenSize.height / 3));
        setMaximumSize(new Dimension(screenSize.width / 5, screenSize.height / 3));

        startButton.addActionListener(e -> {
            if (isNumeric(tableSizeFieldX.getText()) &&  isNumeric(tableSizeFieldY.getText())) {
                tableSizeX = Integer.parseInt(tableSizeFieldX.getText());
                //cellSizeX = Integer.parseInt(cellSizeFieldX.getText());
                tableSizeY = Integer.parseInt(tableSizeFieldY.getText());
                //cellSizeY = Integer.parseInt(cellSizeFieldY.getText());
                SwingUtilities.invokeLater(() ->{
                    GameWindow gameWindow = new GameWindow(tableSizeY, tableSizeX);
                    gameWindow.setVisible(true);
                    //gameWindow.startGame();
                });
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Введите корректные данные.", "Ошибка данных", JOptionPane.ERROR_MESSAGE, null);
                tableSizeFieldX.setText("6");
                tableSizeFieldY.setText("6");
                cellSizeFieldX.setText("0");
                cellSizeFieldY.setText("0");
            }
        });

        continueButton.addActionListener(e -> {
            JFileChooser fileopen = new JFileChooser();
            int ret = fileopen.showDialog(null, "Открыть файл");
        });

        exitButton.addActionListener(e -> this.dispose());
        setTitle("Kana");

        Image image = Toolkit.getDefaultToolkit().createImage( getClass().getResource("icon.png") );
        setIconImage( image );
    }


    public static void main(String[] args) {
        /*UIManager.put( "control", new Color( 128, 128, 128) );
        UIManager.put( "info", new Color(128,128,128) );
        UIManager.put( "nimbusBase", new Color( 18, 30, 49) );
        UIManager.put( "nimbusAlertYellow", new Color( 248, 187, 0) );
        UIManager.put( "nimbusDisabledText", new Color( 128, 128, 128) );
        UIManager.put( "nimbusFocus", new Color(115,164,209) );
        UIManager.put( "nimbusGreen", new Color(176,179,50) );
        UIManager.put( "nimbusInfoBlue", new Color( 66, 139, 221) );
        UIManager.put( "nimbusLightBackground", new Color( 18, 30, 49) );
        UIManager.put( "nimbusOrange", new Color(191,98,4) );
        UIManager.put( "nimbusRed", new Color(169,46,34) );
        UIManager.put( "nimbusSelectedText", new Color( 255, 255, 255) );
        UIManager.put( "nimbusSelectionBackground", new Color( 104, 93, 156) );
        UIManager.put( "text", new Color( 230, 230, 230) );
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (javax.swing.UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }*/



        SwingUtilities.invokeLater(() -> {
            StartWindow startWindow = new StartWindow();
            startWindow.setVisible(true);
        });




    }

    public static boolean isNumeric(String str) {
        return str.matches("^?\\d+$");
    }

}
