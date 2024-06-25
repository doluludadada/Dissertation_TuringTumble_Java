import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameTable extends JPanel implements MouseListener {
    private static final int TABLE_WIDTH = GameConstant.TABLE_WIDTH.getValue();
    private static final int TABLE_HEIGHT = GameConstant.TABLE_HEIGHT.getValue();



    public GameTable() {
        this.addMouseListener(this);
        this.setPreferredSize(new Dimension(TABLE_WIDTH*100, TABLE_HEIGHT*100));
        this.setBackground(Color.WHITE);
    }


    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);


    }




    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
