package UI.GameWindow;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {
    public InfoPanel() {
        setLayout(new BorderLayout());
        add(new InfoTimer(), BorderLayout.PAGE_START);
        JButton mixButton   = new JButton("Перемешать");
        mixButton.setFont(new Font("Arial", Font.BOLD, 15));
        add(mixButton, BorderLayout.PAGE_END);
        //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setMinimumSize(new Dimension(150, 300));

    }
}
