package UI.GameWindow;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileFilter;

public class GameWindow extends JFrame {

    public InfoPanel getInfoPanel() {
        return infoPanel;
    }

    private FieldPanel fieldPanel;
    private InfoPanel infoPanel;
    private int vertical, horizontal, cellSizeX, cellXizeY;


    public GameWindow(int vertical, int horizontal, int cellSizeX, int cellXizeY) {
        this.vertical = vertical;
        this.horizontal = horizontal;
        this.cellSizeX = cellSizeX;
        this.cellXizeY = cellXizeY;
        init();
        setMenuBars();
    }

    private void setMenuBars() {
        JMenuBar menuBar = new JMenuBar();
        JMenu infoMenu = new JMenu("Справка");
        JMenu gameMenu = new JMenu("Игра");
        JMenuItem loadGameMenuItem = new JMenuItem("Продолжить игру");
        loadGameMenuItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Save files", "savepr");
            fileChooser.addChoosableFileFilter(filter);
            int approval = fileChooser.showOpenDialog(null);
            if(approval != JFileChooser.APPROVE_OPTION) {
                return;
            }
            continueGame(fileChooser.getCurrentDirectory() + File.separator + fileChooser.getSelectedFile().getName());
        });
        loadGameMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));

        JMenuItem saveGameMenuItem = new JMenuItem("Coxранить игру");
        saveGameMenuItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Save files", "savepr");
            fileChooser.addChoosableFileFilter(filter);
            int approval = fileChooser.showSaveDialog(null);
            if(approval != JFileChooser.APPROVE_OPTION) {
                return;
            }
            if(!fieldPanel.getGame().SaveProgress(fileChooser.getCurrentDirectory() + File.separator + fileChooser.getSelectedFile().getName(), infoPanel.getTimer().getTime())) {
                JOptionPane.showMessageDialog(null, "Не удалось сохранить файл", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
        saveGameMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));

        JMenuItem saveMistakesMenuItem = new JMenuItem("Сохранить ошибки");
        saveMistakesMenuItem.addActionListener(e -> {

            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
            fileChooser.addChoosableFileFilter(filter);
            int approval = fileChooser.showSaveDialog(null);
            if(approval != JFileChooser.APPROVE_OPTION) {
                return;
            }
            fieldPanel.getGame().SaveMistakes(fileChooser.getCurrentDirectory() + File.separator + fileChooser.getSelectedFile().getName());

        });

        JMenuItem exitMenuItem = new JMenuItem("Выйти");
        exitMenuItem.addActionListener(e -> dispose());

        JMenuItem newGameMenuItem = new JMenuItem("Новая игра");
        newGameMenuItem.addActionListener(e -> startGame());

        JMenuItem undoMenuItem = new JMenuItem("Отменить ход");
        undoMenuItem.addActionListener(e -> fieldPanel.undo());

        JMenuItem mixMenuItem = new JMenuItem("Перемешать");
        mixMenuItem.addActionListener(e -> fieldPanel.mixField());

        JMenuItem infoMenuItem = new JMenuItem("Об авторах");
        infoMenuItem.addActionListener(e -> JOptionPane.showMessageDialog(null, "Игра создана студентами гр. 6303 СПбГЭТУ \"ЛЭТИ\": \n Иванов Д.В. \n Ваганов Н.А \n Ильяшук Д.И. \n 2018г."));


        gameMenu.add(newGameMenuItem);
        gameMenu.add(loadGameMenuItem);
        gameMenu.add(saveGameMenuItem);
        gameMenu.addSeparator();
        gameMenu.add(saveMistakesMenuItem);
        gameMenu.add(undoMenuItem);
        gameMenu.add(mixMenuItem);
        gameMenu.addSeparator();
        gameMenu.add(exitMenuItem);

        infoMenu.add(infoMenuItem);

        menuBar.add(gameMenu);
        menuBar.add(infoMenu);
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
        setMinimumSize(new Dimension(cellSizeX * vertical + Toolkit.getDefaultToolkit().getScreenSize().height / 8, cellXizeY * horizontal));
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Угадайка");
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

    public void continueGame(String filename) {
        fieldPanel.continueGame(filename);
    }

}
