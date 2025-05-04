package demos;

import canvas.Canvas;
import drawing.line.LineDrawerMidpoint;
import drawing.rectangle.RectangleDrawerManager;
import geometry.Rectangle;

import javax.swing.JFrame;
import java.awt.Color;

public class DemoApp06Rectangle {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Demo 06: Rectangle");
        Canvas canvas = new Canvas(500, 500);

        // Configure frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(canvas);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);

        Rectangle rectangle = new Rectangle(100, 100, 300, 200);
        RectangleDrawerManager.setLineDrawer(new LineDrawerMidpoint());
        RectangleDrawerManager.draw(rectangle, canvas, Color.WHITE);
    }
}
