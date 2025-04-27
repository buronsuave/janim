import canvas.Canvas;
import drawing.circle.CircleDrawerManager;
import drawing.circle.CircleDrawerStylized;
import drawing.ellipse.EllipseDrawerManager;
import drawing.ellipse.EllipseDrawerStylized;
import drawing.filling.FloodFillAlgorithm;
import drawing.filling.ScanLineAlgorithm;
import drawing.line.LineDrawerManager;
import drawing.line.LineDrawerStylized;
import drawing.rectangle.RectangleDrawerManager;
import drawing.rectangle.RectangleDrawerStylized;
import geometry.*;
import javax.swing.JFrame;
import java.awt.Color;

public class EditorApp {

    public static void main(String[] args) {
        JFrame frame = new JFrame("JANIM Editor's App");
        Canvas canvas = new Canvas(500, 500);

        // Configure frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(canvas);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);

        Line line1 = new Line(100, 100, 100, 400);
        Line line2 = new Line(100, 400, 250, 270);
        Line line3 = new Line(250, 270, 430, 450);
        Line line4 = new Line(430, 450, 300, 30);
        Line line5 = new Line(300, 30, 100, 100);
        LineDrawerManager.setDrawer(new LineDrawerStylized(3, "1"));
        LineDrawerManager.draw(line1, canvas, Color.YELLOW);
        LineDrawerManager.draw(line2, canvas, Color.YELLOW);
        LineDrawerManager.draw(line3, canvas, Color.YELLOW);
        LineDrawerManager.draw(line4, canvas, Color.YELLOW);
        LineDrawerManager.draw(line5, canvas, Color.YELLOW);

        FloodFillAlgorithm algo = new FloodFillAlgorithm(Color.YELLOW);
        algo.fill(250, 250, Color.YELLOW, canvas);

        frame.repaint();
    }
}
