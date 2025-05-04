package drawing.arc;

import canvas.Canvas;
import geometry.ArcCircle;

import java.awt.*;

public class ArcCircleDrawerManager {
    private static ArcCircleDrawer currentDrawer;

    public static void setDrawer(ArcCircleDrawer drawer) {
        currentDrawer = drawer;
    }

    public static void draw(ArcCircle arc, Canvas canvas, Color c) {
        if (currentDrawer == null) throw new IllegalStateException("No arc circle drawer selected");
        try {
            currentDrawer.drawArcCircle(arc, canvas, c);
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        }
    }
}
