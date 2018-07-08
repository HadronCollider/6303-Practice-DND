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
    private GameWindow window;

    public Game getGame() {
        return game;
    }

    public FieldPanel(GameWindow window, int rows, int columns) {
        this.window = window;
        this.rows = rows;
        this.columns = columns;
        this.game = new Game(rows, columns);
        setLayout(new GridLayout(rows, columns));


        field = new Square[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Square square = new Square(this, null);
                add(square);
                field[i][j] = square;
            }
        }

        setMinimumSize(new Dimension(400, 300));
    }

    public boolean isSelectedExists() {
        return selected != null;
    }

    public Square getSelected() {
        return selected;
    }

    public void setSelected(Square selected) {
        this.selected = selected;
    }

    public void checkTwoSquares(Square checked) {
        if (game.compareCell(selected.getPosition(), checked.getPosition())) {
            selected.setFinal();
            checked.setFinal();
            numberOfCorrectCells += 2;
        } else {
            selected.setWrong();
            checked.setWrong();
            window.getInfoPanel().getErrorCounter().setNumberOfMistakes(game.getNumMistakes());
        }
        setSelected(null);
        if (numberOfCorrectCells == rows * columns) {
            if (game.nextField()) {
                displayField();
                window.getInfoPanel().getProgress().increase();
            } else {
                window.getInfoPanel().getTimer().stop();

                SwingUtilities.invokeLater(() -> {
                    ResultWindow resultWindow = new ResultWindow(this, game.getNumMistakes(), window.getInfoPanel().getTimer().getTextTime());
                    resultWindow.setVisible(true);

                });
            }
        }
    }

    private void displayField() {
        numberOfCorrectCells = 0;
        Cell[][] cellField = game.getField();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (cellField[i][j] == null) {
                    numberOfCorrectCells++;
                }
                field[i][j].setCell(cellField[i][j]);
            }
        }
    }

    public void mixField() {
        game.mixField();
        if (game.getField() == null) {
            return;
        }
        displayField();
    }

    public void startGame() {
        JFileChooser fileDialog = new JFileChooser();
        int approval = fileDialog.showOpenDialog(this);
        if(approval != JFileChooser.APPROVE_OPTION) {
            return;
        }
        if (window.getInfoPanel().getMixCheckBox().isSelected()) {
            game.setMixFlag(true);
        }

        try {
            game.newLesson(fileDialog.getSelectedFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        window.getInfoPanel().startAll();
        window.getInfoPanel().getProgress().setNumberOfSteps(game.getNumberOfSteps());
        game.nextField();
        displayField();
    }

    public void continueGame(String filename) {
        window.getInfoPanel().startAll();
        window.getInfoPanel().getTimer().setTime(game.LoadProgress(filename));
        window.getInfoPanel().getProgress().setNumberOfSteps(game.getNumberOfSteps());
        window.getInfoPanel().getErrorCounter().setNumberOfMistakes(game.getNumMistakes());
        window.getInfoPanel().getProgress().setCurrentStep(game.getNumOfCurStep());
        game.nextField();
        displayField();
    }

    public void startMistakeGame() {
        window.getInfoPanel().startAll();
        window.getInfoPanel().getProgress().setNumberOfSteps(game.getNumberOfSteps());
        game.nextField();
        displayField();
    }

    public void undo() {
        int numberBefore = game.getNumMistakes();
        game.undo();
        if(game.getNumMistakes() < numberBefore) {
            window.getInfoPanel().getErrorCounter().setNumberOfMistakes(game.getNumMistakes());
        } else {
            displayField();
        }
    }



}
