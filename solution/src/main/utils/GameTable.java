package utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;


import gamecomponents.*;


/**
 *
 */

public class GameTable extends JPanel implements MouseListener, ActionListener {
    private static final int TABLE_WIDTH = GameConstant.TABLE_WIDTH.getValue();
    private static final int TABLE_HEIGHT = GameConstant.TABLE_HEIGHT.getValue();
    private static final int CELL_SIZE = GameConstant.CELL_SIZE.getValue();
    private static final int UPPER_SIDE_HEIGHT = 2 * GameConstant.CELL_SIZE.getValue();
    private static final int BOTTOM_HEIGHT = 2 * GameConstant.CELL_SIZE.getValue();


    private Map<Point, GameComponents> components;
    private String selectedComponent;

    private List<Ball> redBalls;
    private List<Ball> blueBalls;


    public GameTable() {

        this.addMouseListener(this);
        this.setPreferredSize(new Dimension(TABLE_WIDTH * CELL_SIZE, TABLE_HEIGHT * CELL_SIZE + UPPER_SIDE_HEIGHT + BOTTOM_HEIGHT));
        this.setBackground(Color.WHITE);
        this.components = new HashMap<>();


        createUI();

        GameLogic.initialiseBalls(redBalls, blueBalls);
        this.redBalls = new ArrayList<>();
        this.blueBalls = new ArrayList<>();


    }


    private void createUI() {
        JFrame frame = new JFrame("Turing Tumble");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        JButton rampButton = new JButton("Ramp");
        JButton crossoverButton = new JButton("Crossover");

        rampButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedComponent = "Ramp";
            }
        });

        crossoverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedComponent = "Crossover";
            }
        });

        panel.add(rampButton);
        panel.add(crossoverButton);
        frame.add(panel, BorderLayout.NORTH);

        frame.pack();
        frame.setVisible(true);
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawUpperSide(g2d);
        drawTable(g2d);
        drawBottomArea(g2d);
        drawComponents(g2d);
    }

    private void drawUpperSide(Graphics2D g) {
        g.setStroke(new BasicStroke(3));
        g.setColor(Color.BLACK);
        int centreX = (TABLE_WIDTH * CELL_SIZE) / 2;
        int storageMidY = UPPER_SIDE_HEIGHT / 3;

        // Centre vertical line
        g.drawLine(centreX, 0, centreX, storageMidY);

        // Left channel
        g.drawLine(centreX, storageMidY, (int) (CELL_SIZE * 2.5), (int) (UPPER_SIDE_HEIGHT / 1.6));
        g.drawLine(0, (int) (CELL_SIZE * 1.2), CELL_SIZE * 3, CELL_SIZE * 2);

        // Right channel
        g.drawLine(centreX, storageMidY, (int) (CELL_SIZE * 8.5), (int) (UPPER_SIDE_HEIGHT / 1.6));
        g.drawLine(TABLE_WIDTH * CELL_SIZE, (int) (CELL_SIZE * 1.2), TABLE_WIDTH * CELL_SIZE - CELL_SIZE * 3, CELL_SIZE * 2);

        // Draw two red circles
        g.setColor(Color.RED);
        int circleSize = 30;
//        g.fillOval((int) (CELL_SIZE * 2.5) - circleSize / 2, (int) (UPPERSIDE_HEIGHT / 1.6) - circleSize / 2, circleSize, circleSize);
//        g.fillOval((int) (CELL_SIZE * 8.5) - circleSize / 2, (int) (UPPERSIDE_HEIGHT / 1.6) - circleSize / 2, circleSize, circleSize);

    }


    private void drawTable(Graphics2D g) {

        for (int row = 0; row < TABLE_HEIGHT; row++) {
            for (int col = 0; col < TABLE_WIDTH; col++) {
                int x = col * CELL_SIZE + CELL_SIZE / 2;
                int y = row * CELL_SIZE + CELL_SIZE / 2 + GameTable.UPPER_SIDE_HEIGHT;

                if (booleanSlot(row, col)) {
                    drawSlot(g, x, y);
                } else if (booleanSlotWithArc(row, col)) {
                    drawSlotWithArc(g, x, y);
                }

                Point point = new Point(col, row);
                if (components.containsKey(point)) {
                    components.get(point).draw(g, x, y);
                }

            }


        }

    }

    private void drawSlotWithArc(Graphics2D g, int x, int y) {

        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2.5f));


        g.drawOval(x - 9, y - 9, 18, 18);

        g.setColor(Color.LIGHT_GRAY);
        g.fillArc(x - 15, y + 2, 30, 20, 0, -180);

        g.setColor(Color.WHITE);
        g.fillArc(x - 10, y + 7, 20, 10, 0, -180);
    }

    private void drawSlot(Graphics2D g, int x, int y) {
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2.5f));
        g.drawOval(x - 9, y - 9, 18, 18);
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


    private void drawBottomArea(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(3));

        // Left slanted line
        int leftStartX = 0;
        int leftStartY = TABLE_HEIGHT * CELL_SIZE + BOTTOM_HEIGHT;
        int leftEndX = (int) (4.5 * CELL_SIZE);
        int leftEndY = (int) (TABLE_HEIGHT * CELL_SIZE + UPPER_SIDE_HEIGHT + CELL_SIZE / 1.5);
        g.drawLine(leftStartX, leftStartY, leftEndX, leftEndY);

        // Right slanted line
        int rightStartX = TABLE_WIDTH * CELL_SIZE;
        int rightStartY = TABLE_HEIGHT * CELL_SIZE + BOTTOM_HEIGHT;
        int rightEndX = TABLE_WIDTH * CELL_SIZE / 2;
        int rightEndY = (int) (TABLE_HEIGHT * CELL_SIZE + UPPER_SIDE_HEIGHT + CELL_SIZE / 1.5);
        g.drawLine(rightStartX, rightStartY, rightEndX, rightEndY);

    }

    private void drawComponents(Graphics2D g) {
        for (Map.Entry<Point, GameComponents> entry : components.entrySet()) {
            Point point = entry.getKey();
            GameComponents component = entry.getValue();
            int x = point.x * CELL_SIZE + CELL_SIZE / 2;
            int y = point.y * CELL_SIZE + CELL_SIZE / 2 + UPPER_SIDE_HEIGHT;
            component.draw(g, x, y);
        }
    }


    private void drawBalls(Graphics2D g) {
        for (Ball ball : redBalls) {

        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        int col = x / CELL_SIZE;
        int row = (y - UPPER_SIDE_HEIGHT) / CELL_SIZE;

        if (row >= 0 && row < TABLE_HEIGHT && col >= 0 && col < TABLE_WIDTH) {
            if (selectedComponent != null) {
                GameComponents component = null;
                if ("Ramp".equals(selectedComponent)) {
                    component = new Ramp();
                } else if ("Crossover".equals(selectedComponent)) {
                    component = new Crossover();
                }
                if (component != null) {
                    Point point = new Point(col, row);
                    components.put(point, component);
                    repaint();
                }
            }
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

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        GameLogic.moveBalls(redBalls, components);
        GameLogic.moveBalls(blueBalls, components);
    }
}
