import canvas.Canvas;
import drawing.arc.ArcCircleDrawerManager;
import drawing.arc.ArcCircleDrawerMidpoint;
import drawing.arc.ArcCircleDrawerStylized;
import drawing.circle.CircleDrawerManager;
import drawing.circle.CircleDrawerStylized;
import drawing.clipping.CircleClipperExplicit;
import drawing.clipping.LineClipperExplicit;
import drawing.clipping.LineClipperSutherland;
import drawing.ellipse.EllipseDrawerManager;
import drawing.ellipse.EllipseDrawerStylized;
import drawing.filling.FillerFloodFill;
import drawing.line.LineDrawerManager;
import drawing.line.LineDrawerStylized;
import drawing.rectangle.RectangleDrawerManager;
import drawing.rectangle.RectangleDrawerStylized;
import geometry.*;

import javax.swing.JFrame;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

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

        int vx;
        int vy = 100;
        int vw = 200;
        int vh = 200;

        Circle circle1 = new Circle(100, 110, 90);
        Circle circle2 = new Circle(300, 100, 50);
        Circle circle3 = new Circle(300, 400, 90);
        Circle circle4 = new Circle(300, 250, 190);

        List<Circle> circles = new ArrayList<>();
        circles.add(circle1);
        circles.add(circle2);
        circles.add(circle3);
        circles.add(circle4);

        for (; vy + vh < canvas.getHeight(); vy+= 20) {
            vx = 150;
            while (vx + vw < canvas.getWidth()) {
                Rectangle rectangle = new Rectangle(vx, vy, vw, vh);
                RectangleDrawerManager.setRectangleDrawer(new RectangleDrawerStylized(3, "10"));
                RectangleDrawerManager.draw(rectangle, canvas, Color.RED);

                CircleClipperExplicit clipper = new CircleClipperExplicit(circles, vx, vy, vx + vw, vy + vh);
                clipper.clip();

                for (ArcCircle arc : clipper.getClippedCircles()) {
                    ArcCircleDrawerManager.setDrawer(new ArcCircleDrawerStylized(3, "1"));
                    ArcCircleDrawerManager.draw(arc, canvas, Color.YELLOW);
                }

                frame.repaint();

                try {
                    // Erase
                    Thread.sleep(10000);
                    RectangleDrawerManager.setRectangleDrawer(new RectangleDrawerStylized(3, "1"));
                    RectangleDrawerManager.draw(rectangle, canvas, Color.BLACK);
                    for (ArcCircle arc : clipper.getClippedCircles()) {
                        ArcCircleDrawerManager.setDrawer(new ArcCircleDrawerStylized(3, "1"));
                        ArcCircleDrawerManager.draw(arc, canvas, Color.BLACK);
                    }

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                vx++;
            }
        }
    }
}
