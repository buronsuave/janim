package demos;

import canvas.Canvas;
import drawing.clipping.LineClipperExplicit;
import drawing.line.LineDrawerManager;
import drawing.line.LineDrawerStylized;
import drawing.rectangle.RectangleDrawerManager;
import drawing.rectangle.RectangleDrawerStylized;
import geometry.Line;
import geometry.Rectangle;

import javax.swing.JFrame;
import java.awt.Color;
import java.util.ArrayList;

public class DemoApp19LineClipper {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Demo 19: Line Clipper");
        Canvas canvas = new Canvas(500, 500);

        // Configure frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(canvas);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);

        ArrayList<Line> lines = new ArrayList<>();
        int r = 70;
        int R = 200;
        for (double t = 0; t < 2*Math.PI; t += Math.PI/6) {
            Line line1 = new Line(
                    250 + r*Math.cos(t),
                    250 + r*Math.sin(t),
                    250 + R*Math.cos(t + Math.PI/12),
                    250 + R*Math.sin(t + Math.PI/12));
            Line line2 = new Line(
                    250 + r*Math.cos(t),
                    250 + r*Math.sin(t),
                    250 + R*Math.cos(t - Math.PI/12),
                    250 + R*Math.sin(t - Math.PI/12));
            lines.add(line1);
            lines.add(line2);
        }


        int viewPortX0;
        int viewPortY0 = 0;
        int viewPortWidth = 120;
        int viewPortHeight = 60;

        for (; viewPortY0 < canvas.getHeight(); viewPortY0+= viewPortHeight) {
            for (viewPortX0 = 0; viewPortX0 < canvas.getWidth(); viewPortX0++) {
                for (Line line : lines) {
                    LineDrawerManager.setDrawer(new LineDrawerStylized(1, "1"));
                    LineDrawerManager.draw(line, canvas, Color.GRAY);
                }

                Rectangle rect = new Rectangle(viewPortX0, viewPortY0, viewPortWidth, viewPortHeight);
                RectangleDrawerManager.setRectangleDrawer(new RectangleDrawerStylized(3, "10"));
                RectangleDrawerManager.draw(rect, canvas, Color.RED);

                LineClipperExplicit clipper = new LineClipperExplicit(
                        lines, viewPortX0, viewPortY0, viewPortX0 + viewPortWidth, viewPortY0 + viewPortHeight
                );
                clipper.clip();

                for (Line line : clipper.getClippedLines()) {
                    LineDrawerManager.setDrawer(new LineDrawerStylized(3, "1"));
                    LineDrawerManager.draw(line, canvas, Color.YELLOW);
                }

                frame.repaint();
                try {
                    Thread.sleep(15);
                    RectangleDrawerManager.setRectangleDrawer(new RectangleDrawerStylized(3, "1"));
                    RectangleDrawerManager.draw(rect, canvas, Color.BLACK);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
