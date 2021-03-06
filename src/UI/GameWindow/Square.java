package UI.GameWindow;

import Logic.Cell;
import Logic.Position;
import Logic.StrTransform;

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
    private Timer timer;
    private Cell cell = null;


    public Square(FieldPanel parent, Cell cell) {
        this.parent = parent;
        setCell(cell);
        init();
    }

    public void setCell(Cell cell) {
        this.cell = cell;
        this.removeAll();
        if(cell != null) {
            this.position = cell.getPosition();
            if(cell.getFlag()) {
                this.type = SquareType.RIGHT;
                StrTransform.transform(this, cell.getPair().getSecond(), (int)(getHeight() * 0.9));
            } else {
                this.type = SquareType.LEFT;
                StrTransform.transform(this, cell.getPair().getFirst(), getHeight());
            }

        } else {
            this.type = SquareType.FINAL;
        }
        this.selected = false;
        this.pressed = false;
        redraw();
    }


    void redraw() {
        removeAll();
        if(type == SquareType.FINAL) {
            setBackground(new Color(34, 139, 34));
        } else if(type == SquareType.LEFT){
            setBackground(new Color(150, 238, 238));
            StrTransform.transform(this, cell.getPair().getFirst(), getHeight());
        } else {
            setBackground(Color.WHITE);
            StrTransform.transform(this, cell.getPair().getSecond(), (int)(getHeight() * 0.9));
        }

        updateUI();
        update(getGraphics());
    }


    private void init() {
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.getX() < 0 || e.getY() < 0 || e.getX() > getSize().width || e.getY() > getSize().height) {
                    return;
                }
                if (type == SquareType.FINAL || wrong) {
                    return;
                }
                clicked();
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
        if(type == SquareType.LEFT) {
            setBackground(new Color(150, 238, 238));
        } else {
            setBackground(Color.WHITE);
        }
    }


    public void setFinal() {
        type = SquareType.FINAL;
        removeAll();
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
        if(type == SquareType.LEFT) {
            setBackground(new Color(150, 238, 238));
        } else {
            setBackground(Color.WHITE);
        }
    }


    private enum SquareType{
        LEFT,
        RIGHT,
        FINAL
    }

}
