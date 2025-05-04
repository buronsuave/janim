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

        /*
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


        int viewPortX0 = 0;
        int viewPortY0 = 20;
        int viewPortWidth = 100;
        int viewPortHeight = 460;

        for (; viewPortX0 < 450; viewPortX0++) {
            /*
            for (Line line : lines) {
                LineDrawerManager.setDrawer(new LineDrawerStylized(1, "1"));
                LineDrawerManager.draw(line, canvas, Color.WHITE);
            }


            Rectangle rect = new Rectangle(viewPortX0, viewPortY0, viewPortWidth, viewPortHeight);
            RectangleDrawerManager.setRectangleDrawer(new RectangleDrawerStylized(3, "10", null));
            RectangleDrawerManager.draw(rect,canvas, Color.RED);

            LineClipperExplicit clipper = new LineClipperExplicit(
                    lines, viewPortX0, viewPortY0, viewPortX0+viewPortWidth, viewPortY0+viewPortHeight
            );
            clipper.clip();

            for (Line line : clipper.getClippedLines()) {
                LineDrawerManager.setDrawer(new LineDrawerStylized(3, "1"));
                LineDrawerManager.draw(line, canvas, Color.YELLOW);
            }

            frame.repaint();
            try {
                Thread.sleep(30);
                RectangleDrawerManager.setRectangleDrawer(new RectangleDrawerStylized(3, "1", null));
                RectangleDrawerManager.draw(rect,canvas, Color.BLACK);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        */

        int vx = 10;
        int vy = 0;
        int vw = 200;
        int vh = 200;

        Circle circle1 = new Circle(100, 100, 100);
        Circle circle2 = new Circle(300, 100, 50);
        Circle circle3 = new Circle(300, 400, 100);
        Circle circle4 = new Circle(300, 250, 200);

        List<Circle> circles = new ArrayList<>();
        circles.add(circle1);
        circles.add(circle2);
        circles.add(circle3);
        circles.add(circle4);

        /*
        for (; vy + vh < canvas.getHeight(); vy+= 20) {
            vx = 0;
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
                    Thread.sleep(30);
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
        */

        List<Circle> customCircles = new ArrayList<>();
        customCircles.add(circle1);
        customCircles.add(circle2);
        customCircles.add(circle3);
        customCircles.add(circle4);

        CircleDrawerManager.setDrawer(new CircleDrawerStylized(1, "1"));
        CircleDrawerManager.draw(circle1, canvas, Color.WHITE);
        CircleDrawerManager.draw(circle2, canvas, Color.WHITE);
        CircleDrawerManager.draw(circle3, canvas, Color.WHITE);
        CircleDrawerManager.draw(circle4, canvas, Color.WHITE);

        Rectangle rectangle = new Rectangle(vx, vy, vw, vh);
        RectangleDrawerManager.setRectangleDrawer(new RectangleDrawerStylized(3, "10"));
        RectangleDrawerManager.draw(rectangle, canvas, Color.RED);

        CircleClipperExplicit clipper = new CircleClipperExplicit(customCircles, vx, vy, vx+vw, vy+vh);
        clipper.clip();
        ArcCircleDrawerManager.setDrawer(new ArcCircleDrawerStylized(3, "1"));
        for (ArcCircle arc : clipper.getClippedCircles()) {
            ArcCircleDrawerManager.draw(arc, canvas, Color.YELLOW);
        }
    }
}
