import canvas.Canvas;
import drawing.circle.CircleDrawerManager;
import drawing.circle.CircleDrawerMidpoint;
import drawing.ellipse.EllipseDrawerManager;
import drawing.ellipse.EllipseDrawerMidpoint;
import drawing.line.LineDrawerManager;
import drawing.line.LineDrawerMidpoint;
import drawing.rectangle.RectangleDrawerManager;
import geometry.*;
import javax.swing.JFrame;
import java.awt.Color;

public class DemoApp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("JANIM Demo App");
        Canvas canvas = new Canvas(1050, 700);

        // Configure frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(canvas);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);

        // Lines
        Line line1 = new Line(100, 100, 200, 200);
        Line line2 = new Line(300, 150, 400, 150);
        Line line3 = new Line(600, 100, 500, 200);
        Line line4 = new Line(800, 150, 700, 150);

        LineDrawerManager.setDrawer(new LineDrawerMidpoint());
        LineDrawerManager.draw(line1, canvas, Color.WHITE);
        LineDrawerManager.draw(line2, canvas, Color.WHITE);
        LineDrawerManager.draw(line3, canvas, Color.WHITE);
        LineDrawerManager.draw(line4, canvas, Color.WHITE);

        // Circles
        Circle circle1 = new Circle(150, 500, 100);
        Circle circle2 = new Circle(150, 500, 70);
        Circle circle3 = new Circle(150, 500, 40);
        Circle circle4 = new Circle(150, 500, 10);

        CircleDrawerManager.setDrawer(new CircleDrawerMidpoint());
        CircleDrawerManager.draw(circle1, canvas, Color.WHITE);
        CircleDrawerManager.draw(circle2, canvas, Color.WHITE);
        CircleDrawerManager.draw(circle3, canvas, Color.WHITE);
        CircleDrawerManager.draw(circle4, canvas, Color.WHITE);

        // Rectangles
        Rectangle rectangle1 = new Rectangle(new Point(300, 430), new Point(550, 580));
        Rectangle rectangle2 = new Rectangle(new Point(500, 530), new Point(350, 480));

        RectangleDrawerManager.setLineDrawer(new LineDrawerMidpoint());
        RectangleDrawerManager.draw(rectangle1, canvas, Color.WHITE);
        RectangleDrawerManager.draw(rectangle2, canvas, Color.WHITE);

        // Ellipses
        Ellipse ellipse1 = new Ellipse(800, 500, 200, 130);
        Ellipse ellipse2 = new Ellipse(800, 500, 170, 100);
        Ellipse ellipse3 = new Ellipse(800, 500, 140, 70);
        Ellipse ellipse4 = new Ellipse(800, 500, 110, 30);


        EllipseDrawerManager.setDrawer(new EllipseDrawerMidpoint());
        EllipseDrawerManager.draw(ellipse1, canvas, Color.WHITE);
        EllipseDrawerManager.draw(ellipse2, canvas, Color.WHITE);
        EllipseDrawerManager.draw(ellipse3, canvas, Color.WHITE);
        EllipseDrawerManager.draw(ellipse4, canvas, Color.WHITE);
    }
}
