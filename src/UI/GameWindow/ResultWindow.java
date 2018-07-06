package UI.GameWindow;

import Logic.Game;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ResultWindow extends JFrame {

    private FieldPanel panel;
    private int numberOfMistakes;

    public ResultWindow(FieldPanel panel, int numberOfMistakes) throws HeadlessException {
        this.panel = panel;

        setTitle("Победа!");
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        setLayout(layout);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(new Dimension((int)screenSize.getWidth() / 3, screenSize.height / 8));
        setResizable(false);

        constraints.weightx = 1;
        constraints.weighty = 1;

        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.gridwidth = 3;
        JLabel resultInfo = new JLabel("Поздравляем! Вы прошли игру и совершили " + numberOfMistakes + " ошибок.");
        resultInfo.setFont(new Font("Verdana", Font.PLAIN, 17));
        add(resultInfo, constraints);
        
        constraints.gridwidth = 1;
        constraints.gridy = 1;
        constraints.gridx = 0;
        JButton saveMistakesButton = new JButton("Сохранить ошибки");
        saveMistakesButton.addActionListener(e -> {
            try {
                panel.getGame().SaveErrors();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            //dispose();
        });
        add(saveMistakesButton, constraints);

        constraints.gridy = 1;
        constraints.gridx = 1;
        JButton startMistakesGameButton = new JButton("Работа над ошибками");
        startMistakesGameButton.addActionListener(e -> {
            try {
                panel.getGame().ErrorsToLesson(Game.NumErrorType.BOTH);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            panel.startMistakeGame();
            dispose();
        });

        add(startMistakesGameButton, constraints);

        constraints.gridy = 1;
        constraints.gridx = 2;
        JButton newGameButton = new JButton("Начать новую игру");
        newGameButton.addActionListener(e -> {
            panel.startGame();
            dispose();
        });
        add(newGameButton, constraints);

        if(numberOfMistakes == 0) {
            startMistakesGameButton.setEnabled(false);
            saveMistakesButton.setEnabled(false);
        }
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    }

}
