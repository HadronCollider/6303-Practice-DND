package UI.GameWindow;

import javax.swing.*;
import java.awt.*;


public class InfoTimer extends JLabel {
    private int seconds;
    private int minutes;
    private Timer timer;

    public InfoTimer() {
        setFont(new Font("Arial", Font.BOLD, 30));
        timer = new Timer(1000, e -> increase());
        display();
    }

    void start() {
        timer.start();
    }

    void stop() {
        timer.stop();
    }

    void restart() {
        stop();
        seconds = 0;
        minutes = 0;
        display();
        start();
    }

    private void increase() {

        if (seconds < 59) {
            seconds++;
        } else {
            seconds = 0;
            minutes++;
        }
        display();
    }

    private void display() {
        StringBuilder out = new StringBuilder();
        if (minutes < 10) {
            out.append('0');
        }
        out.append(minutes);
        out.append(':');
        if (seconds < 10) {
            out.append('0');
        }
        out.append(seconds);
        setText(out.toString());
    }

    int getTime() {
        return minutes * 60 + seconds;
    }

    void setTime(int timeInSeconds) {
        minutes = timeInSeconds / 60;
        seconds = timeInSeconds % 60;
        display();
    }

}
