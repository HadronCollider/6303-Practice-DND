package UI.GameWindow;

import javax.swing.*;
import java.awt.*;

public class ResultWindow extends JFrame {

    private GameWindow window;

    public ResultWindow() throws HeadlessException {
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        setLayout(layout);


        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.gridwidth = 3;
        JLabel resultInfo = new JLabel("kek kek kek kek kek kek kek kek kek kek ");
        add(resultInfo, constraints);
        
        constraints.gridwidth = 0;
        constraints.gridy = 1;
        constraints.gridx = 0;
        JButton saveMistakesButton = new JButton("Сохранить ошибки");
        add(saveMistakesButton, constraints);

        constraints.gridy = 1;
        constraints.gridx = 1;
        JButton startMistakesGameButton = new JButton("Работа над ошибками");
        add(startMistakesGameButton, constraints);

        constraints.gridy = 1;
        constraints.gridx = 2;
        JButton newGameButton = new JButton("Начать новую игру");
        add(newGameButton, constraints);


    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ResultWindow resultWindow = new ResultWindow();
            resultWindow.setVisible(true);
        });
    }
}
