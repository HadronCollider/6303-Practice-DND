package UI.GameWindow;

import Logic.Cell;
import Logic.Game;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class FieldPanel extends JPanel {

    private int rows;
    private int columns;
    private Square selected = null;
    private Game game;
    private Square[][] field;
    private int numberOfCorrectCells;


    public FieldPanel(int rows, int columns){
        this.rows = rows;
        this.columns = columns;
        setLayout(new GridLayout(columns, rows));
        this.game = new Game(rows, columns);


        field = new Square[rows][columns];
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                Square square = new Square(this, null);
                add(square);
                field[i][j] = square;
            }
        }

       // displayField();
        setMinimumSize(new Dimension(400, 300));
    }

    public void setSelected(Square selected) {
        this.selected = selected;
    }

    public boolean isSelectedExists() {
        return selected != null;
    }

    public Square getSelected() {
        return selected;
    }

    public void checkTwoSquares(Square checked) {
        if(game.compareCell(selected.getPosition(), checked.getPosition())) {
            selected.setFinal();
            checked.setFinal();
            numberOfCorrectCells+=2;
        } else {
            selected.dispalayWrong();
            checked.dispalayWrong();
        }
        setSelected(null);
        if(numberOfCorrectCells == rows * columns) {
            if(game.nextField()) {
                displayField();
            } else {
                JOptionPane.showMessageDialog(this, "Победа");
            }
        }
    }

    private void displayField() {
        numberOfCorrectCells = 0;
        Cell[][] cellField = game.getField();
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                if(cellField[i][j] == null) {
                    numberOfCorrectCells++;
                }
                field[i][j].setCell(cellField[i][j]);
            }
        }
    }

    public void mixField() {
        game.mixField();
        displayField();
    }

    public void openDictionary() {
        JFileChooser fileDialog = new JFileChooser();
        fileDialog.showOpenDialog(this);
        try {
            game.newLesson(fileDialog.getSelectedFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        game.nextField();
        displayField();
    }

}
