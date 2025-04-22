package drawing.rectangle;

import canvas.Canvas;
import drawing.line.LineDrawer;
import geometry.Line;
import geometry.Rectangle;
import java.awt.Color;

public class RectangleDrawerManager {
    private static LineDrawer currentLineDrawer;
    private static RectangleDrawer currentRectangleDrawer;

    public static void setLineDrawer(LineDrawer drawer) {
        currentLineDrawer = drawer;
        currentRectangleDrawer = null;
    }

    public static void setRectangleDrawer(RectangleDrawer drawer) {
        currentRectangleDrawer = drawer;
        currentLineDrawer = null;
    }

    public static void draw(Rectangle r, Canvas canvas, Color c) {
        // No rectangle drawer, use line drawer instead
        if (currentRectangleDrawer == null) {
            // No drawer is selected
            if (currentLineDrawer == null) {
                throw new IllegalStateException("No line nor rectangle drawer selected");
            } else {
                // Continue with line drawer
                try {
                    for (Line l : r.getSides()) {
                        currentLineDrawer.drawLine(l, canvas, c);
                    }
                } catch (ArithmeticException e) {
                    System.out.println(e.getMessage());
                }
            }
        } else {
            // Continue with rectangle drawer
            try {
                currentRectangleDrawer.drawRectangle(r, canvas, c);
            } catch (ArithmeticException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
