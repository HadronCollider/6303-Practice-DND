package UI.GameWindow;

import Logic.Game;

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


    public GameWindow(int vertical, int horizontal, int cellSizeX, int cellSizeY) {
        this.vertical = vertical;
        this.horizontal = horizontal;
        this.cellSizeX = cellSizeX;
        this.cellXizeY = cellSizeY;
        init();
        setMenuBars();
    }

    private void setMenuBars() {
        System.setProperty("file.encoding", "UTF-8");
        JMenuBar menuBar = new JMenuBar();
        JMenu infoMenu = new JMenu("Справка");
        JMenu gameMenu = new JMenu("Игра");
        JMenuItem loadGameMenuItem = new JMenuItem("Продолжить игру");
        loadGameMenuItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Save files", "savepr");
            fileChooser.addChoosableFileFilter(filter);
            int approval = fileChooser.showOpenDialog(null);
            if(approval != JFileChooser.APPROVE_OPTION) {
                return;
            }
            Game game = new Game(2, 2);
            game.LoadProgress(fileChooser.getCurrentDirectory() + File.separator + fileChooser.getSelectedFile().getName());
            if(game.getFieldSize().getHorizontal() == horizontal && game.getFieldSize().getVertical() == vertical) {
                continueGame(fileChooser.getCurrentDirectory() + File.separator + fileChooser.getSelectedFile().getName());
            } else {
                JOptionPane.showMessageDialog(null, "Размер поля сохранения не совпадает с текущим.\nИзмените рамер поля на " + game.getFieldSize().getHorizontal() + " x " + game.getFieldSize().getVertical(), "Несоответсвие размера поля", JOptionPane.ERROR_MESSAGE);
            }

        });
        loadGameMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));

        JMenuItem saveGameMenuItem = new JMenuItem("Coxранить игру");
        saveGameMenuItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
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
            JFileChooser fileChooser;
            while (true) {
                fileChooser = new JFileChooser(System.getProperty("user.dir"));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
                fileChooser.addChoosableFileFilter(filter);
                int approval = fileChooser.showSaveDialog(null);
                if (approval != JFileChooser.APPROVE_OPTION) {
                    return;
                }
                File file1 = new File(fileChooser.getCurrentDirectory() + File.separator + fileChooser.getSelectedFile().getName() + "-" + fieldPanel.getGame().getCurLesson().getLessonName().subSequence(0, fieldPanel.getGame().getCurLesson().getLessonName().length() - 4) + "(err1).txt");
                File file2 = new File(fileChooser.getCurrentDirectory() + File.separator + fileChooser.getSelectedFile().getName() + "-" + fieldPanel.getGame().getCurLesson().getLessonName().subSequence(0, fieldPanel.getGame().getCurLesson().getLessonName().length() - 4) + "(err2).txt");
                if (file1.exists() || file2.exists()) {
                    Object[] options = {"Да",
                            "Нет"};
                    int n = JOptionPane.showOptionDialog(this,
                            "Вы действительно хотите перезаписать файлы?",
                            "Подтверждение",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[1]);
                    if(n == 0) {
                        break;
                    }

                }
                else
                    break;
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

        JMenuItem stopGameMenuItem = new JMenuItem("Прервать игру");
        stopGameMenuItem.addActionListener(e -> {
            Object[] options = {"Да",
                    "Отмена"};
            int n = JOptionPane.showOptionDialog(this,
                    "Вы действительно хотите прервать игру?",
                    "Подтверждение",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[1]);
            if(n == 0) {
                stopGame();
            }

        });

        JMenuItem infoMenuItem = new JMenuItem("Об авторах");
        infoMenuItem.addActionListener(e -> JOptionPane.showMessageDialog(null, "Игра создана студентами гр. 6303 СПбГЭТУ \"ЛЭТИ\": \n Иванов Д.В. \n Ваганов Н.А \n Ильяшук Д.И. \n 2018г."));

        JMenuItem settingsMenuItem = new JMenuItem("Настройки");
        settingsMenuItem.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                SettingsWindow settingsWindow = new SettingsWindow(this, horizontal, vertical, cellSizeX, cellXizeY);
                settingsWindow.setVisible(true);
            });
        });

        gameMenu.add(newGameMenuItem);
        gameMenu.add(loadGameMenuItem);
        gameMenu.add(saveGameMenuItem);
        gameMenu.add(settingsMenuItem);
        gameMenu.addSeparator();
        gameMenu.add(saveMistakesMenuItem);
        gameMenu.add(undoMenuItem);
        gameMenu.add(mixMenuItem);
        gameMenu.add(stopGameMenuItem);
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
        setMinimumSize(new Dimension(cellSizeX * horizontal + Toolkit.getDefaultToolkit().getScreenSize().height / 8, cellXizeY * vertical));
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Угадайка");
        Image image = Toolkit.getDefaultToolkit().createImage( getClass().getResource("/UI/StartWindow/icon.png") );
        setIconImage( image );
        setLocationRelativeTo(null);
    }

    void resize(int fieldSizeX, int fieldSizeY, int cellSizeX, int cellSizeY) {
        boolean fieldSizeChanged = (vertical != fieldSizeY || horizontal != fieldSizeX);
        if(fieldPanel.isInProcess() && fieldSizeChanged) {
            JOptionPane.showMessageDialog(null, "Нельзя изменить параметры размер поля пока идет игра!", "Ошибка", JOptionPane.ERROR_MESSAGE, null);
            return;
        }

        this.vertical = fieldSizeY;
        this.horizontal = fieldSizeX;
        this.cellSizeX = cellSizeX;
        this.cellXizeY = cellSizeY;
        setResizable(true);
        setMinimumSize(new Dimension(cellSizeX * horizontal + Toolkit.getDefaultToolkit().getScreenSize().height / 8, cellXizeY * vertical));

        setSize(new Dimension(cellSizeX * horizontal + Toolkit.getDefaultToolkit().getScreenSize().height / 8, cellXizeY * vertical));
        setResizable(false);
        if(fieldSizeChanged) {
            fieldPanel.resizeField(fieldSizeX, fieldSizeY);
        }
        fieldPanel.redraw();
    }

    public FieldPanel getFieldPanel() {
        return fieldPanel;
    }

    public void startGame() {
        fieldPanel.startGame();
    }
    private void stopGame() {
        fieldPanel.stopGame();
    }

    public void continueGame(String filename) {
        fieldPanel.continueGame(filename);
    }

}
