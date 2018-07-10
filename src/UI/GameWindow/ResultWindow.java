package UI.GameWindow;

import Logic.DictionaryPair;
import Logic.Game;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

public class ResultWindow extends JFrame {

    private FieldPanel panel;

    public ResultWindow(FieldPanel panel, int numberOfMistakes, String timer) throws HeadlessException {
        this.panel = panel;

        setTitle("Победа!");
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        setLayout(layout);

        int verticalBorder = Toolkit.getDefaultToolkit().getScreenSize().height / 200;
        int horizontalBorder = Toolkit.getDefaultToolkit().getScreenSize().height / 200;

        constraints.insets = new Insets(verticalBorder, horizontalBorder, verticalBorder ,horizontalBorder);

        constraints.weightx = 1;
        constraints.weighty = 1;

        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.gridwidth = 3;
        JLabel resultInfo = new JLabel("Поздравляем! Вы прошли игру за" + timer + "и совершили " + numberOfMistakes + " ошибок.");
        add(resultInfo, constraints);

        constraints.gridwidth = 1;
        constraints.gridy = 1;
        constraints.gridx = 0;
        JButton saveMistakesButton = new JButton("Сохранить ошибки");

        saveMistakesButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
            int approval = fileChooser.showSaveDialog(this);

            if(approval != JFileChooser.APPROVE_OPTION) {
                return;
            }
            panel.getGame().SaveMistakes(fileChooser.getCurrentDirectory() + File.separator + fileChooser.getSelectedFile().getName());

        });
        add(saveMistakesButton, constraints);

        constraints.gridy = 1;
        constraints.gridx = 1;
        JButton startMistakesGameButton = new JButton("Работа над ошибками");
        startMistakesGameButton.addActionListener(e -> {
            int choice = displayChooseWindow();

            switch (choice){
                case 0:
                    panel.getGame().MistakesToLesson(panel.getGame().getLessonMistakes1(), Game.NumMistakeType.values()[choice]);
                    panel.startMistakeGame(false, null);
                    break;
                case 1:
                    panel.getGame().MistakesToLesson(panel.getGame().getLessonMistakes2(), Game.NumMistakeType.values()[choice]);
                    panel.startMistakeGame(false, null);
                    break;
                case 2:
                    LinkedList<DictionaryPair> list = new LinkedList<>(panel.getGame().getLessonMistakes1());
                    list.addAll(panel.getGame().getLessonMistakes2());
                    panel.getGame().MistakesToLesson(list, Game.NumMistakeType.values()[choice]);
                    panel.startMistakeGame(false, null);
                    break;
                default:
                    LinkedList<DictionaryPair> list2 = (LinkedList<DictionaryPair>) panel.getGame().getLessonMistakes2().clone();
                    panel.getGame().MistakesToLesson(panel.getGame().getLessonMistakes1(), Game.NumMistakeType.FIRST);
                    panel.startMistakeGame(true, list2);
                    break;
            }
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
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    }

    private int displayChooseWindow() {
        Object[] options = {"Левый список", "Правый список", "Оба списка сразу", "Оба списка по очереди"};
        String ret = (String)JOptionPane.showInputDialog(panel.getWindow(), "Какой список ошибок запускать?", "Выбор", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        return Arrays.asList(options).indexOf(ret);

    }

}
