package UI.GameWindow;

import Logic.Cell;
import Logic.Game;

import javax.swing.*;
import java.awt.*;

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
        init();
    }

    private void init() {
        setLayout(new GridLayout(rows, columns));

        field = new Square[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Square square = new Square(this, null);
                add(square);
                field[i][j] = square;
            }
        }
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
            window.getInfoPanel().getMistakeCounter().setNumberOfMistakes(game.getNumMistakes());
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

    void displayField() {
        numberOfCorrectCells = 0;
        Cell[][] cellField = game.getField();
        if(cellField == null) {
            return;
        }
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
        this.selected = null;
        JFileChooser fileDialog = new JFileChooser();
        int approval = fileDialog.showOpenDialog(this);
        if(approval != JFileChooser.APPROVE_OPTION) {
            return;
        }
        if (window.getInfoPanel().getMixCheckBox().isSelected()) {
            game.setMixFlag(true);
        }

        if(!game.newLesson(fileDialog.getSelectedFile())) {
            return;
        }

        window.getInfoPanel().startAll();
        window.getInfoPanel().getProgress().setNumberOfSteps(game.getNumberOfSteps());
        game.nextField();
        displayField();
    }

    public void continueGame(String filename) {
        this.selected = null;
        int timer = game.LoadProgress(filename);
        rows = game.getFieldSize().getVertical();
        columns = game.getFieldSize().getHorizontal();
        //removeAll();
        //init();
        window.getInfoPanel().startAll();
        window.getInfoPanel().getTimer().setTime(timer);
        window.getInfoPanel().getProgress().setNumberOfSteps(game.getNumberOfSteps());
        window.getInfoPanel().getMistakeCounter().setNumberOfMistakes(game.getNumMistakes());
        window.getInfoPanel().getProgress().setCurrentStep(game.getNumOfCurStep());
        //game.nextField();
        displayField();

        //undo();
        updateUI();
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
            window.getInfoPanel().getMistakeCounter().setNumberOfMistakes(game.getNumMistakes());
        } else {
            displayField();
        }
    }

    public GameWindow getWindow() {
        return window;
    }

}
