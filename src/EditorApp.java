import canvas.Canvas;
import drawing.BresenhamLineDrawer;
import drawing.CircleDrawerManager;
import drawing.DDALineDrawer;
import drawing.DavidLineDrawer;
import drawing.EightSymmetriesCircleDrawer;
import drawing.LineDrawerManager;
import drawing.MathCircleDrawer;
import drawing.MathLineDrawer;
import drawing.MidpointLineDrawer;
import drawing.PolarCircleDrawer;
import drawing.RectangleDrawerManager;
import geometry.Circle;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;

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

        Circle circle = new Circle(250, 250, 200);
        CircleDrawerManager.setDrawer(new MathCircleDrawer());
        CircleDrawerManager.draw(circle, canvas, Color.WHITE);
    }

}
