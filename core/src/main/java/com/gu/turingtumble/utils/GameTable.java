//package com.gu.turingtumble.utils;
//
//
//import javax.swing.*;
//import javax.swing.Timer;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.awt.geom.Line2D;
//import java.util.*;
//import java.util.List;
//
//
//import com.gu.turingtumble.gamecomponents.*;
//
//
//
//
//@Deprecated
//public class GameTable extends JPanel implements MouseListener, ActionListener {
//
//    private Timer timer;
//
//    public static final int TABLE_WIDTH = GameConstant.SLOT_NUMBER_WIDTH.getValue();
//    public static final int TABLE_HEIGHT = GameConstant.SLOT_NUMBER_HEIGHT.getValue();
//    public static final int CELL_SIZE = GameConstant.CELL_SIZE.getValue();
//    public static final int UPPER_SIDE_HEIGHT = GameConstant.UPPER_SIDE_HEIGHT.getValue();
//    public static final int BOTTOM_HEIGHT = GameConstant.BOTTOM_SIDE_HEIGHT.getValue();
//
//
//    private Map<Point, GameComponents> components;
//    private String selectedComponent;
//    private static final List<Line2D> tableLines = new ArrayList<>();
//
//
//    private List<Ball> redBalls;
//    private List<Ball> blueBalls;
//    private Set<Point> slots;
//    private Set<Point> slotsWithArc;
//
//
//
//
//
//
//
//    public GameTable() {
//
//
//
//        this.addMouseListener(this);
//        this.setPreferredSize(new Dimension(TABLE_WIDTH * CELL_SIZE,
//                TABLE_HEIGHT * CELL_SIZE + UPPER_SIDE_HEIGHT + BOTTOM_HEIGHT));
//        this.setBackground(Color.WHITE);
//
//        this.components = new HashMap<>();
//        this.redBalls = new ArrayList<>();
//        this.blueBalls = new ArrayList<>();
//        this.slots = new HashSet<>();
//        this.slotsWithArc = new HashSet<>();
//
//
//        createUI();
//
//        initialiseTableLines();
//
//
//        timer = new Timer(16, this);
//        timer.start();
//
//
//    }
//
//
//    private void createUI() {
//        JFrame frame = new JFrame("Turing Tumble");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setLayout(new BorderLayout());
//        frame.add(this, BorderLayout.CENTER);
//
//        JPanel panel = new JPanel();
//        JButton rampButton = new JButton("Ramp");
//        JButton crossoverButton = new JButton("Crossover");
//
//        rampButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                selectedComponent = "Ramp";
//            }
//        });
//
//        crossoverButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                selectedComponent = "Crossover";
//            }
//        });
//
//        panel.add(rampButton);
//        panel.add(crossoverButton);
//        frame.add(panel, BorderLayout.NORTH);
//
//        frame.pack();
//        frame.setVisible(true);
//    }
//
//
//    @Override
//    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
//
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//        drawTable(g2d);
//        drawComponents(g2d);
//
//        g2d.setColor(Color.BLACK);
//        for (Line2D line : tableLines) {
//            g2d.draw(line);
//        }
//
////        for (Ball ball : redBalls) {
////            ball.draw(g2d, ball.getX(), ball.getY());
////        }
////
////        for (Ball ball : blueBalls) {
////            ball.draw(g2d, ball.getX(), ball.getY());
////        }
//
//
//    }
//
//
//    public static void initialiseTableLines() {
//        int tableWidth = TABLE_WIDTH * CELL_SIZE;
//        int tableHeight = TABLE_HEIGHT * CELL_SIZE;
//        int upperSideHeight = UPPER_SIDE_HEIGHT;
//
//        // Centre vertical line
//        tableLines.add(new Line2D.Double(tableWidth / 2, 0, tableWidth / 2, upperSideHeight / 3));
//
//        // Left channel
//        tableLines.add(new Line2D.Double(tableWidth / 2, upperSideHeight / 3, 2.5 * GameConstant.CELL_SIZE.getValue(), upperSideHeight / 1.6));
//        tableLines.add(new Line2D.Double(0, 1.2 * GameConstant.CELL_SIZE.getValue(), 3 * GameConstant.CELL_SIZE.getValue(), 2 * GameConstant.CELL_SIZE.getValue()));
//
//        // Right channel
//        tableLines.add(new Line2D.Double(tableWidth / 2, upperSideHeight / 3, 8.5 * GameConstant.CELL_SIZE.getValue(), upperSideHeight / 1.6));
//        tableLines.add(new Line2D.Double(tableWidth, 1.2 * GameConstant.CELL_SIZE.getValue(), tableWidth - 3 * GameConstant.CELL_SIZE.getValue(), 2 * GameConstant.CELL_SIZE.getValue()));
//
//        // Bottom area lines
//        tableLines.add(new Line2D.Double(0, tableHeight + GameConstant.BOTTOM_SIDE_HEIGHT.getValue(), 4.5 * GameConstant.CELL_SIZE.getValue(), tableHeight + upperSideHeight + GameConstant.CELL_SIZE.getValue() / 1.5));
//        tableLines.add(new Line2D.Double(tableWidth, tableHeight + GameConstant.BOTTOM_SIDE_HEIGHT.getValue(), tableWidth / 2, tableHeight + upperSideHeight + GameConstant.CELL_SIZE.getValue() / 1.5));
//    }
//
//
//    private void drawTable(Graphics2D g) {
//        for (int row = 0; row < TABLE_HEIGHT; row++) {
//            for (int col = 0; col < TABLE_WIDTH; col++) {
//
//                int x = col * CELL_SIZE + CELL_SIZE / 2;
//                int y = row * CELL_SIZE + CELL_SIZE / 2 + GameTable.UPPER_SIDE_HEIGHT;
//                Point point = new Point(col, row);
//
//                if (booleanSlot(row, col)) {
//                    drawSlot(g, x, y);
//                    slots.add(point);
//                } else if (booleanSlotWithArc(row, col)) {
//                    drawSlotWithArc(g, x, y);
//                    slotsWithArc.add(point);
//                }
//
//                if (components.containsKey(point)) {
////                    components.get(point).draw(g, x, y);
//                }
//
//            }
//        }
//    }
//
//    private void drawSlotWithArc(Graphics2D g, int x, int y) {
//
//        g.setColor(Color.BLACK);
//        g.setStroke(new BasicStroke(2.5f));
//
//
//        g.drawOval(x - 9, y - 9, 18, 18);
//
//        g.setColor(Color.LIGHT_GRAY);
//        g.fillArc(x - 15, y + 2, 30, 20, 0, -180);
//
//        g.setColor(Color.WHITE);
//        g.fillArc(x - 10, y + 7, 20, 10, 0, -180);
//    }
//
//
//    private void drawSlot(Graphics2D g, int x, int y) {
//        g.setColor(Color.BLACK);
//        g.setStroke(new BasicStroke(2.5f));
//        g.drawOval(x - 9, y - 9, 18, 18);
//    }
//
//
//    /**
//     * @param row
//     * @param col
//     * @return
//     */
//    private boolean booleanSlotWithArc(int row, int col) {
//
//        if (row == 0) {
//            return (col == 3 || col == 7);
//        }
//        if (row == 1) {
//            return (col > 1 && col < 9 && col % 2 == 0);
//        }
//        if (row >= 2 && row <= 9) {
//            if ((row % 2 == 0 && col % 2 == 1) || (row % 2 == 1 && col % 2 == 0)) {
//                return true;
//            }
//        }
//        if (row == 10 && col == 5) {
//            return true;
//        }
//
//        return false;
//    }
//
//    private boolean booleanSlot(int row, int col) {
//
//        if (row == 0) {
//            return (col == 2 || col == 4 || col == 6 || col == 8);
//        }
//        if (row == 1) {
//            return (col <= 9 && col % 2 == 1);
//        }
//        if (row >= 2 && row <= 9) {
//            if ((row % 2 == 0 && col % 2 == 0) || (row % 2 == 1 && col % 2 == 1)) {
//                return true;
//            }
//        }
//
//        return false;
//
//    }
//
//
//    private void drawComponents(Graphics2D g) {
//
//        for (Map.Entry<Point, GameComponents> entry : components.entrySet()) {
//            Point point = entry.getKey();
//            GameComponents component = entry.getValue();
//            int x = point.x * CELL_SIZE + CELL_SIZE / 2;
//            int y = point.y * CELL_SIZE + CELL_SIZE / 2 + UPPER_SIDE_HEIGHT;
////            component.draw(g, x, y);
//        }
//
//    }
//
//
//
//    @Override
//    public void mouseClicked(MouseEvent e) {
//
//
//        int col = e.getX() / GameConstant.CELL_SIZE.getValue();
//        int row = (e.getY() - GameConstant.UPPER_SIDE_HEIGHT.getValue()) / GameConstant.CELL_SIZE.getValue();
//
//        if (row >= 0 && row < GameConstant.SLOT_NUMBER_HEIGHT.getValue() &&
//                col >= 0 && col < GameConstant.SLOT_NUMBER_WIDTH.getValue()) {
//            addComponentAt(col, row);
//        }
//
//
//    }
//
//
//    private void addComponentAt(int col, int row) {
//        if (selectedComponent != null) {
//            GameComponents component = null;
//            if ("Ramp".equals(selectedComponent)) {
//                component = new Ramp();
//            } else if ("Crossover".equals(selectedComponent)) {
//                component = new Crossover();
//            }
//            if (component != null) {
//                Point point = new Point(col, row);
//                components.put(point, component);
//                repaint();
//            }
//        }
//    }
//
//
//
//
//
//
//
//    @Override
//    public void mousePressed(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mouseReleased(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mouseEntered(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mouseExited(MouseEvent e) {
//
//    }
//
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
////        long currentTime = System.nanoTime();
////        double timeDelta = (currentTime - lastUpdateTime) / 1e9;
////        lastUpdateTime = currentTime;
////
////        GameLogic.moveBalls(redBalls, tableLines);
////        GameLogic.moveBalls(blueBalls, tableLines);
//
//        repaint();
//
//    }
//
//
//    @Deprecated
//    private void drawUpperSide(Graphics2D g) {
//
//        g.setStroke(new BasicStroke(3));
//        g.setColor(Color.BLACK);
//        int centreX = (TABLE_WIDTH * CELL_SIZE) / 2;
//        int storageMidY = UPPER_SIDE_HEIGHT / 3;
//
//        // Centre vertical line
//        g.drawLine(centreX, 0, centreX, storageMidY);
//
//        // Left channel
//        g.drawLine(centreX, storageMidY, (int) (CELL_SIZE * 2.5), (int) (UPPER_SIDE_HEIGHT / 1.6));
//        g.drawLine(0, (int) (CELL_SIZE * 1.2), CELL_SIZE * 3, CELL_SIZE * 2);
//
//        // Right channel
//        g.drawLine(centreX, storageMidY, (int) (CELL_SIZE * 8.5), (int) (UPPER_SIDE_HEIGHT / 1.6));
//        g.drawLine(TABLE_WIDTH * CELL_SIZE, (int) (CELL_SIZE * 1.2), TABLE_WIDTH * CELL_SIZE - CELL_SIZE * 3, CELL_SIZE * 2);
//
//    }
//
//    @Deprecated
//    private void drawBottomArea(Graphics2D g) {
//
//        g.setColor(Color.BLACK);
//        g.setStroke(new BasicStroke(3));
//
//        int leftStartX = 0;
//        int leftStartY = TABLE_HEIGHT * CELL_SIZE + BOTTOM_HEIGHT;
//        int leftEndX = (int) (4.5 * CELL_SIZE);
//        int leftEndY = (int) (TABLE_HEIGHT * CELL_SIZE + UPPER_SIDE_HEIGHT + CELL_SIZE / 1.5);
//
//        Line2D leftLine = new Line2D.Float(leftStartX, leftStartY, leftEndX, leftEndY);
//        g.draw(leftLine);
//        tableLines.add(leftLine);
//
//        // right side
//        int rightStartX = TABLE_WIDTH * CELL_SIZE;
//        int rightStartY = TABLE_HEIGHT * CELL_SIZE + BOTTOM_HEIGHT;
//        int rightEndX = TABLE_WIDTH * CELL_SIZE / 2;
//        int rightEndY = (int) (TABLE_HEIGHT * CELL_SIZE + UPPER_SIDE_HEIGHT + CELL_SIZE / 1.5);
//
//        Line2D rightLine = new Line2D.Float(rightStartX, rightStartY, rightEndX, rightEndY);
//        g.draw(rightLine);
//        tableLines.add(rightLine);
//
//
//    }
//
//}
