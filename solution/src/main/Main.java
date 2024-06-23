import javax.swing.*;
import java.awt.*;

public class Main {
    public Main() {

    }

    public static void main(String[] args) {
        JFrame win = new JFrame("Snake");
        win.setDefaultCloseOperation(3);
        win.setContentPane(new GameTable());
        win.pack();
        win.setLocationRelativeTo((Component)null);
        win.setVisible(true);
        win.setResizable(false);
    }
}
