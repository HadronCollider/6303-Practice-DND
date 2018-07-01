package UI.GameWindow;

import javax.swing.*;
import java.awt.*;

public class FieldPanel extends JPanel {

    private int rows;
    private int columns;



    public FieldPanel(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        setLayout(new GridLayout(columns, rows));

        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {

                add(new Cell());

            }
        }
        setMinimumSize(new Dimension(400, 300));
    }

}
