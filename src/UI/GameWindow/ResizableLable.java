package UI.GameWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ResizableLable extends JLabel {

    //public static final int MIN_FONT_SIZE = 5;
    //public static final int MAX_FONT_SIZE = 30;
    Graphics graphics;

    public ResizableLable(String text) {
        super(text);
        init();
    }


    protected void init() {
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                adaptLabelFont(ResizableLable.this);
            }
        });
    }

    protected void adaptLabelFont(JLabel label) {
        if (graphics == null) {
            return;
        }
        Rectangle rectangle = label.getBounds();

        int fontSize = rectangle.height / 3;
        Font font = label.getFont();
        System.out.println(fontSize);

        Rectangle rectangle1 = new Rectangle();
        Rectangle rectangle2 = new Rectangle();
        while (fontSize < rectangle.height) {
            rectangle1.setSize(getTextSize(label, font.deriveFont(font.getStyle(), fontSize)));
            rectangle2.setSize(getTextSize(label, font.deriveFont(font.getStyle(), fontSize + 1)));
            if (rectangle.contains(rectangle1) && !rectangle.contains(rectangle2)) {
                break;
            }
            fontSize++;
        }

        setFont(font.deriveFont(font.getStyle(), fontSize));
        repaint();
    }

    private Dimension getTextSize(JLabel label, Font font) {
        Dimension size = new Dimension();
        graphics.setFont(font);
        FontMetrics fm = graphics.getFontMetrics(font);
        size.width = fm.stringWidth(label.getText());
        size.height = fm.getHeight();
        return size;
    }

    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        this.graphics = graphics;
    }

}
