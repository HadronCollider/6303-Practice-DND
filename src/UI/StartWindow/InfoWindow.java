package UI.StartWindow;

import javax.swing.*;
import java.awt.*;

public class InfoWindow extends JFrame {
    public InfoWindow(String text) {

        init(text);
    }


    private void init(String text) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //получаем разрешение экрана
        JButton returnButton = new JButton("Ясно");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(screenSize.height / 35));
        label.setFont(new Font("Verdana", Font.PLAIN, 16));
        panel.add(label);
        returnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(screenSize.height / 25));
        returnButton.setFont(new Font("Verdana", Font.PLAIN, 16));
        panel.add(returnButton);
        setResizable(false);

        add(panel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Для стартового окна используем 1/3 высоты экрана и 1/6 ширины, минимальный размер 300х100

        //setMinimumSize(new Dimension(300, 100)); //минимальный размер окна
        setSize(new Dimension(screenSize.width / 4, screenSize.height / 6));
        setPreferredSize(new Dimension(screenSize.width / 4, screenSize.height / 6));
        setMinimumSize(new Dimension(screenSize.width / 4, screenSize.height / 6));
        setMaximumSize(new Dimension(screenSize.width / 4, screenSize.height / 6));


        returnButton.addActionListener(e -> {
            this.dispose();
        });
        setTitle("Информация");
        Image image = Toolkit.getDefaultToolkit().createImage( getClass().getResource("icon_info.png") );
        setIconImage( image );
    }

}
