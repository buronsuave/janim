package drawing.ellipse;

import canvas.Canvas;
import geometry.Ellipse;
import java.awt.Color;

public class EllipseDrawerManager {
    private static EllipseDrawer currentDrawer;

    public static void setDrawer(EllipseDrawer drawer) {
        currentDrawer = drawer;
    }

    public static void draw(Ellipse ellipse, Canvas canvas, Color c) {
        if (currentDrawer == null) throw new IllegalStateException("No ellipse drawer selected");
        try {
            currentDrawer.drawEllipse(ellipse, canvas, c);
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        }
    }
}
