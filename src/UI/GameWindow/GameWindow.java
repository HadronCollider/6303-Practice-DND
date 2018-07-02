package UI.GameWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class GameWindow extends JFrame {
    public GameWindow() {
        init();
        setMenuBars();
    }

    private void setMenuBars() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Файл");
        JMenu gameMenu = new JMenu("Игра");
        JMenuItem openMenuItem = new JMenuItem("Открыть");
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        JMenuItem saveMenuItem = new JMenuItem("Coxранить");
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        JMenuItem saveMistakesMenuItem = new JMenuItem("Сохранить ошибки");
        JMenuItem exitMenuItem = new JMenuItem("Выйти");
        JMenuItem undoMenuItem = new JMenuItem("Отменить действие");
        JMenuItem mixMenuItem = new JMenuItem("Перемешать");
        JMenuItem settingsMenuItem = new JMenuItem("Настройки поля");
        gameMenu.add(undoMenuItem);
        gameMenu.add(mixMenuItem);
        gameMenu.add(settingsMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveMistakesMenuItem);
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        menuBar.add(gameMenu);
        setJMenuBar(menuBar);
    }

    private void init() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.1;
        panel.add(new FieldPanel(6,6), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.weightx = 0;
        panel.add(new InfoPanel(), gridBagConstraints);
        setContentPane(panel);
        setMinimumSize(new Dimension(600, 500));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Kana");
        setLocationRelativeTo(null);
    }

}
