import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Path2D;


/**
 *
 */

public class GameTable extends JPanel implements MouseListener {
    private static final int TABLE_WIDTH = GameConstant.TABLE_WIDTH.getValue();
    private static final int TABLE_HEIGHT = GameConstant.TABLE_HEIGHT.getValue();
    private static final int CELL_SIZE = GameConstant.CELL_SIZE.getValue();


    public GameTable() {
        this.addMouseListener(this);
        this.setPreferredSize(new Dimension(TABLE_WIDTH * CELL_SIZE, TABLE_HEIGHT * CELL_SIZE));
        this.setBackground(Color.WHITE);
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawGrid(g2d);
        drawStorage(g2d);
        drawBottomArea(g2d);
    }

    private void drawGrid(Graphics2D g) {

        //
        for (int row = 0; row < TABLE_HEIGHT; row++) {
            for (int col = 0; col < TABLE_WIDTH; col++) {
                int x = col * CELL_SIZE + CELL_SIZE / 2;
                int y = row * CELL_SIZE + CELL_SIZE / 2;

                if (booleanSlot(row, col)) {
                    drawSlot(g, x, y); //
                } else if (booleanSlotWithArc(row, col)) {
                    drawSlotWithArc(g, x, y);
                }
            }
        }
    }

    private void drawSlotWithArc(Graphics2D g, int x, int y) {
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2.5f));


        g.drawOval(x - 10, y - 10, 20, 20);

        g.setColor(Color.LIGHT_GRAY);
        g.fillArc(x - 15, y + 2, 30, 20, 0, -180);

        g.setColor(Color.WHITE);
        g.fillArc(x - 10, y + 7, 20, 10, 0, -180);
    }

    private void drawSlot(Graphics2D g, int x, int y) {
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2.5f));
        g.drawOval(x - 10, y - 10, 20, 20);
    }



    /**
     * @param row
     * @param col
     * @return
     */
    private boolean booleanSlotWithArc(int row, int col) {
        if (row == 0) {
            return (col == 3 || col == 7);
        }
        if (row == 1) {
            return (col > 1 && col < 9 && col % 2 == 0);
        }
        if (row >= 2 && row <= 9) {
            if ((row % 2 == 0 && col % 2 == 1) || (row % 2 == 1 && col % 2 == 0)) {
                return true;
            }
        }
        if (row == 10 && col == 5) {
            return true;
        }
        return false;
    }

    private boolean booleanSlot(int row, int col) {
        // Adjusting the logic based on your pattern
        if (row == 0) {
            return (col == 2 || col == 4 || col == 6 || col == 8);
        }
        if (row == 1) {
            return (col <= 9 && col % 2 == 1);
        }
        if (row >= 2 && row <= 9) {
            if ((row % 2 == 0 && col % 2 == 0) || (row % 2 == 1 && col % 2 == 1)) {
                return true;
            }
        }
        return false;
    }

    private void drawStorage(Graphics2D g) {

    }

    private void drawBottomArea(Graphics2D g) {

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
