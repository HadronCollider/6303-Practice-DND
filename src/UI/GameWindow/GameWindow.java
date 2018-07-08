package UI.GameWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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
        JMenu infoMenu = new JMenu("Справка");
        JMenu gameMenu = new JMenu("Игра");
        JMenuItem loadGameMenuItem = new JMenuItem("Продолжить игру");
        loadGameMenuItem.addActionListener(e -> {
            //JFileChooser fileChooser = new JFileChooser();
            //int approve = fileChooser.showOpenDialog(null);
            //if(approve != JFileChooser.APPROVE_OPTION) {
            //    return;
            //}
            continueGame("/data/ex3.savepr");
        });

        loadGameMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        JMenuItem saveGameMenuItem = new JMenuItem("Coxранить игру");
        saveGameMenuItem.addActionListener(e -> {
            fieldPanel.getGame().SaveProgress(infoPanel.getTimer().getTime());
        });

        saveGameMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        JMenuItem saveMistakesMenuItem = new JMenuItem("Сохранить ошибки");
        saveMistakesMenuItem.addActionListener(e -> {
            try {
                fieldPanel.getGame().SaveMistakes();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        JMenuItem exitMenuItem = new JMenuItem("Выйти");
        exitMenuItem.addActionListener(e -> dispose());

        JMenuItem newGameMenuItem = new JMenuItem("Новая игра");
        newGameMenuItem.addActionListener(e -> startGame());

        JMenuItem undoMenuItem = new JMenuItem("Отменить действие");
        undoMenuItem.addActionListener(e -> fieldPanel.undo());

        JMenuItem mixMenuItem = new JMenuItem("Перемешать");
        mixMenuItem.addActionListener(e -> fieldPanel.mixField());

        JMenuItem infoMenuItem = new JMenuItem("Об авторах");
        infoMenuItem.addActionListener(e -> JOptionPane.showMessageDialog(null, "Ну тут шото о нас"));


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
        setMinimumSize(new Dimension(600, 500));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Kana");
        Image image = Toolkit.getDefaultToolkit().createImage( getClass().getResource("/UI/StartWindow/icon.png") );
        setIconImage( image );
        setLocationRelativeTo(null);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                fieldPanel.displayField();
            }
        });
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
