package UI.GameWindow;

import javax.swing.*;
import java.awt.*;


public class InfoPanel extends JPanel {
    private GameWindow window;

    public InfoPanel(GameWindow window) {
        this.window = window;
        setLayout(new BorderLayout());
        add(new InfoTimer(), BorderLayout.PAGE_START);
        JButton mixButton = new JButton("Перемешать");
        mixButton.setFont(new Font("Arial", Font.BOLD, 15));
        mixButton.addActionListener(e -> getWindow().getField().mixField());
        add(mixButton, BorderLayout.PAGE_END);
        setMinimumSize(new Dimension(150, 300));
    }

    private GameWindow getWindow() {
        return window;
    }
}
