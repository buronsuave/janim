package drawing.ellipse;

import canvas.Canvas;
import geometry.Ellipse;
import java.awt.Color;

public interface EllipseDrawer {
    void drawEllipse(Ellipse ellipse, Canvas canvas, Color c) throws ArithmeticException;
}
