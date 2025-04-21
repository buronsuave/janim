package drawing;

import canvas.Canvas;
import geometry.Line;
import geometry.Rectangle;
import java.awt.Color;

public class RectangleDrawerManager {
    private static LineDrawer currentDrawer;

    public static void setDrawer(LineDrawer drawer) {
        currentDrawer = drawer;
    }

    public static void draw(Rectangle r, Canvas canvas, Color c) {
        if (currentDrawer == null) throw new IllegalStateException("No line drawer selected");
        try {
            for (Line side : r.getSides()) {
                currentDrawer.drawLine(side, canvas, c);
            }

        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        }
    }
}
