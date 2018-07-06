package UI.GameWindow;

import Logic.Game;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class GameWindow extends JFrame {

    public InfoPanel getInfoPanel() {
        return infoPanel;
    }

    private FieldPanel fieldPanel;
    private InfoPanel infoPanel;
    private int vertical, horizontal;


    public GameWindow(int vertical, int horizontal) {
        this.vertical = vertical;
        this.horizontal = horizontal;
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
            FileFilter filter = new FileNameExtensionFilter("MP3 File","mp3");
            fileDialog.addChoosableFileFilter(filter);
            fileDialog.showSaveDialog(this);
        });

        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        JMenuItem saveMistakesMenuItem = new JMenuItem("Сохранить ошибки");
        saveMistakesMenuItem.addActionListener(e -> {
            try {
                fieldPanel.getGame().SaveErrors();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        JMenuItem exitMenuItem = new JMenuItem("Выйти");

        exitMenuItem.addActionListener(e -> dispose());

        JMenuItem loadLessonMenuItem = new JMenuItem("Загрузить словарь");
        loadLessonMenuItem.addActionListener(e -> fieldPanel.startGame());

        JMenuItem undoMenuItem = new JMenuItem("Отменить действие");
        undoMenuItem.addActionListener(e -> fieldPanel.undo());

        JMenuItem mixMenuItem = new JMenuItem("Перемешать");
        mixMenuItem.addActionListener(e -> fieldPanel.mixField());

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
        FieldPanel fieldPanel = new FieldPanel(this, vertical, horizontal);
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
        Image image = Toolkit.getDefaultToolkit().createImage( getClass().getResource("/UI/StartWindow/icon.png") );
        setIconImage( image );
        setLocationRelativeTo(null);
    }

    public FieldPanel getFieldPanel() {
        return fieldPanel;
    }

    public void startGame() {
        fieldPanel.startGame();
    }

}
