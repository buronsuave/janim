package drawing.arc;

import canvas.Canvas;
import geometry.ArcCircle;
import java.awt.Color;

public interface ArcCircleDrawer {
    void drawArcCircle(ArcCircle arc, Canvas canvas, Color c) throws ArithmeticException;
}
