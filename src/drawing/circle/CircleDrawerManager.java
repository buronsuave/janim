package drawing.circle;

import canvas.Canvas;
import geometry.Circle;
import java.awt.Color;

public class CircleDrawerManager {
    private static CircleDrawer currentDrawer;

    public static void setDrawer(CircleDrawer drawer) {
        currentDrawer = drawer;
    }

    public static void draw(Circle circle, Canvas canvas, Color c) {
        if (currentDrawer == null) throw new IllegalStateException("No circle drawer selected");
        try {
            currentDrawer.drawCircle(circle, canvas, c);
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        }
    }
}
