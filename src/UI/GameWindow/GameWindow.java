package UI.GameWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class GameWindow extends JFrame {

    public InfoPanel getInfoPanel() {
        return infoPanel;
    }

    private FieldPanel fieldPanel;
    private InfoPanel infoPanel;


    public GameWindow() {
        init();
        setMenuBars();
    }

    private void setMenuBars() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Файл");
        JMenu gameMenu = new JMenu("Игра");
        JMenuItem openMenuItem = new JMenuItem("Продолжить игру");
        openMenuItem.addActionListener(e -> {
            JFileChooser fileDialog = new JFileChooser();
            fileDialog.showOpenDialog(this);
        });
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        JMenuItem saveMenuItem = new JMenuItem("Coxранить игру");
        saveMenuItem.addActionListener(e -> {
            JFileChooser fileDialog = new JFileChooser();
            fileDialog.showSaveDialog(this);
        });

        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        JMenuItem saveMistakesMenuItem = new JMenuItem("Сохранить ошибки");
        JMenuItem exitMenuItem = new JMenuItem("Выйти");
        exitMenuItem.addActionListener(e -> dispose());
        JMenuItem loadLessonMenuItem = new JMenuItem("Загрузить словарь");
        loadLessonMenuItem.addActionListener(e -> fieldPanel.openDictionary());
        JMenuItem undoMenuItem = new JMenuItem("Отменить действие");
        JMenuItem mixMenuItem = new JMenuItem("Перемешать");
        JMenuItem settingsMenuItem = new JMenuItem("Настройки");
        gameMenu.add(loadLessonMenuItem);
        gameMenu.add(saveMistakesMenuItem);
        gameMenu.add(undoMenuItem);
        gameMenu.add(mixMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(settingsMenuItem);
        fileMenu.addSeparator();
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
        FieldPanel fieldPanel = new FieldPanel(this, 4, 4);
        this.fieldPanel = fieldPanel;
        panel.add(fieldPanel, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.weightx = 0;
        InfoPanel infoPanel = new InfoPanel(this);
        this.infoPanel = infoPanel;
        panel.add(infoPanel, gridBagConstraints);
        setContentPane(panel);
        setMinimumSize(new Dimension(600, 500));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Kana");
        setLocationRelativeTo(null);
    }

    FieldPanel getFieldPanel() {
        return fieldPanel;
    }

}
