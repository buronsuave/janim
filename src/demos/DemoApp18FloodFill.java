package demos;

import canvas.Canvas;
import drawing.circle.CircleDrawerManager;
import drawing.circle.CircleDrawerStylized;
import drawing.ellipse.EllipseDrawerManager;
import drawing.ellipse.EllipseDrawerStylized;
import drawing.filling.FillerFloodFill;
import drawing.filling.FillerScanLine;
import drawing.line.LineDrawerManager;
import drawing.line.LineDrawerStylized;
import drawing.rectangle.RectangleDrawerManager;
import drawing.rectangle.RectangleDrawerStylized;
import geometry.Circle;
import geometry.Ellipse;
import geometry.Line;
import geometry.Rectangle;

import javax.swing.JFrame;
import java.awt.Color;

public class DemoApp18FloodFill {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Demo 18: Flood Fill");
        Canvas canvas = new Canvas(500, 500);

        // Configure frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(canvas);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);

        // Convex figure
        Line line1 = new Line(30, 30, 30, 250);
        Line line2 = new Line(30, 250, 200, 100);
        Line line3 = new Line(200, 100, 300, 250);
        Line line4 = new Line(300, 250, 250, 10);
        Line line5 = new Line(250, 10, 30, 30);
        LineDrawerManager.setDrawer(new LineDrawerStylized(3, "1"));
        LineDrawerManager.draw(line1, canvas, Color.YELLOW);
        LineDrawerManager.draw(line2, canvas, Color.YELLOW);
        LineDrawerManager.draw(line3, canvas, Color.YELLOW);
        LineDrawerManager.draw(line4, canvas, Color.YELLOW);
        LineDrawerManager.draw(line5, canvas, Color.YELLOW);
        FillerFloodFill scanner = new FillerFloodFill(Color.RED);
        scanner.fill(100, 100, Color.YELLOW, canvas);

        // Primitive figures
        Circle circle = new Circle(100, 350, 70);
        CircleDrawerManager.setDrawer(new CircleDrawerStylized(2, "100",
                new FillerFloodFill(Color.BLUE)));
        CircleDrawerManager.draw(circle, canvas, Color.YELLOW);

        geometry.Rectangle rectangle = new Rectangle(200, 280, 120, 70);
        RectangleDrawerManager.setRectangleDrawer(new RectangleDrawerStylized(2, "1",
                new FillerFloodFill(Color.GREEN)));
        RectangleDrawerManager.draw(rectangle, canvas, Color.YELLOW);

        Ellipse ellipse = new Ellipse(400, 350, 50, 90);
        EllipseDrawerManager.setDrawer(new EllipseDrawerStylized(2, "1",
                new FillerFloodFill(Color.GRAY)));
        EllipseDrawerManager.draw(ellipse, canvas, Color.YELLOW);

        frame.repaint();
    }
}
