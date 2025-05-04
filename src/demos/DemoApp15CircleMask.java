package demos;

import canvas.Canvas;
import drawing.circle.CircleDrawerManager;
import drawing.circle.CircleDrawerStylized;
import geometry.Circle;

import javax.swing.JFrame;
import java.awt.Color;

public class DemoApp15CircleMask {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Demo 15: Circle Mask");
        Canvas canvas = new Canvas(500, 500);

        // Configure frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(canvas);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);

        int r = 160;
        int R = 20;

        for (double t = 0; t < 2*Math.PI; t += Math.PI/3) {
            CircleDrawerManager.setDrawer(new CircleDrawerStylized(1, "000010000"));
            CircleDrawerManager.draw(new Circle(
                    250 + r*Math.cos(t), 250 + r*Math.sin(t),
                    R
            ), canvas, Color.WHITE);

            R += 10;
        }
    }
}
