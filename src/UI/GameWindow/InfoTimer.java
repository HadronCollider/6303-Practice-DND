package UI.GameWindow;

import javax.swing.*;
import java.awt.*;


public class InfoTimer extends JLabel {
    private int seconds = -1;
    private int minutes;

    public InfoTimer() {
        setFont(new Font("Arial", Font.BOLD, 30));
        Timer timer = new Timer(1000, e -> increase());
        increase();
        timer.start();

    }

    private void increase() {

        if(seconds < 59) {
            seconds++;
        }
        else {
            seconds = 0;
            minutes++;
        }
        rewrite();
    }

    private void rewrite() {
        StringBuilder out = new StringBuilder();
        out.append("   ");
        if(minutes < 10) {
            out.append('0');
        }
        out.append(minutes);
        out.append(':');
        if(seconds < 10) {
            out.append('0');
        }
        out.append(seconds);
        setText(out.toString());
    }

}
