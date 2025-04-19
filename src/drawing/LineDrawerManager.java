package drawing;

import canvas.Canvas;
import geometry.Line;
import java.awt.Color;

public class LineDrawerManager {
    private static LineDrawer currentDrawer;

    public static void setDrawer(LineDrawer drawer) {
        currentDrawer = drawer;
    }

    public static void draw(Line line, Canvas canvas, Color c) {
        if (currentDrawer == null) throw new IllegalStateException("No line drawer selected");
        try {
            currentDrawer.drawLine(line, canvas, c);
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        }
    }
}
