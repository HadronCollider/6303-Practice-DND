package UI.GameWindow;

import Logic.Cell;
import Logic.Position;

import javax.swing.*;
import java.awt.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Square extends JButton {

    private boolean pressed;
    private boolean selected;
    private boolean wrong;
    private SquareType type;
    private FieldPanel parent;
    private Position position;
    private String content;
    private Timer timer;


    public Square(FieldPanel parent, Cell cell) {
        //super();
        this.parent = parent;
        setFont(new Font("APJapanesefont", Font.BOLD, 20));
        setCell(cell);
        init();
    }

    public void setCell(Cell cell) {
        if(cell != null) {
            this.position = cell.getPosition();
            this.type = cell.getFlag() ? SquareType.RIGHT : SquareType.LEFT;
            this.content = cell.getFlag() ? cell.getPair().getSecond() : cell.getPair().getFirst();
        } else {
            this.type = SquareType.FINAL;
            this.content = "";
        }
        this.selected = false;
        this.pressed = false;
        setText(content);
        redraw();
    }


    private void redraw() {
        if(type == SquareType.FINAL) {
            setBackground(new Color(34, 139, 34));
        } else {
            setBackground(Color.WHITE);
        }
    }

    public Square(Image image) {
        super(new ImageIcon(image));
        init();
    }

    private void init() {
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (type == SquareType.FINAL || wrong) {
                    return;
                }
                clicked();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
            }
        });
    }

    public void clicked() {
        if(selected) {
            unselect();
            parent.setSelected(null);
        } else {
            if(parent.isSelectedExists()) {
                if(parent.getSelected().getType() == this.type) {
                    parent.getSelected().unselect();
                    parent.setSelected(this);
                    select();
                } else {
                    parent.checkTwoSquares(this);
                }
            } else {
                parent.setSelected(this);
                select();
            }
        }
    }

    private SquareType getType() {
        return type;
    }

    private void select() {
        selected = true;
        setBackground(Color.YELLOW);
    }

    private void unselect() {
        selected = false;
        setBackground(Color.WHITE);
    }


    public void setFinal() {
        type = SquareType.FINAL;
        setBackground(new Color(34, 139, 34));
        setText("");
    }

    public Position getPosition() {
        return position;
    }

    void setWrong() {
        setBackground(Color.RED);
        wrong = true;
        selected = false;
        timer = new Timer(1000, e -> setNormal());
        timer.start();
    }

    private void setNormal() {
        timer.stop();
        wrong = false;
        setBackground(Color.WHITE);
    }


    private enum SquareType{
        LEFT,
        RIGHT,
        FINAL
    }

}
