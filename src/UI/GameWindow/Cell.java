package UI.GameWindow;



import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class Cell extends JButton {

    private boolean selected;
    private boolean wrong;
    private boolean correct;
    private FieldPanel parent;


    public Cell(FieldPanel parent) {
        super();
        this.parent = parent;
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((char)(20094 + random.nextInt(1000)));
        setFont(new Font("APJapanesefont", Font.BOLD, 40));
        setText(stringBuilder.toString());
        init();
    }


    public Cell(Image image) {
        super(new ImageIcon(image));
        init();
    }

    private void init() {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(correct) {
                    return;
                }
                if(!selected) {
                    setSelected(true);

                }
                else {
                    setSelected(false);
                }

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

    public void setSelected(boolean selected) {
        this.selected = selected;
        if(selected) {
            setBackground(Color.GRAY);
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            if(parent.isSelectedExists()) {
                parent.getSelected().setCorrect();
                parent.setSelected(null);
                setCorrect();
            }
            else {

                parent.setSelected(this);
            }
        }
        else {
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
            parent.setSelected(null);
        }
    }


    public void setCorrect() {
        this.correct = true;
        setBackground(Color.GREEN);
        setText("");
    }

}
