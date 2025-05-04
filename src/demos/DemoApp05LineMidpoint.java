package demos;

import canvas.Canvas;
import drawing.line.LineDrawerManager;
import drawing.line.LineDrawerMidpoint;
import geometry.Line;

import javax.swing.JFrame;
import java.awt.Color;

public class DemoApp05LineMidpoint {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Demo 05: Line Midpoint");
        Canvas canvas = new Canvas(500, 500);

        // Configure frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(canvas);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);

        int r = 50;
        int R = 200;

        for (double t = 0; t < 2*Math.PI; t += Math.PI/8) {
            LineDrawerManager.setDrawer(new LineDrawerMidpoint());
            LineDrawerManager.draw(new Line(
                    250 + r*Math.cos(t), 250 + r*Math.sin(t),
                    250 + R*Math.cos(t), 250 + R*Math.sin(t)
            ), canvas, Color.WHITE);
        }
    }
}
