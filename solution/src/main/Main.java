import javax.swing.*;
import java.awt.*;

public class Main {
    public Main() {

    }

    public static void main(String[] args) {
        JFrame win = new JFrame("Turning Tumble");
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setContentPane(new GameTable());
        win.pack();
        win.setLocationRelativeTo((Component)null);
        win.setVisible(true);
        win.setResizable(false);    //這行程式碼禁止用戶調整窗口的大小。
    }
}
