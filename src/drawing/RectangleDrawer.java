package drawing;

import canvas.Canvas;
import geometry.Rectangle;
import java.awt.Color;

public interface RectangleDrawer {
    void drawRectangle(Rectangle rectangle, Canvas canvas, Color c) throws ArithmeticException;
}
