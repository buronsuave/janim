package drawing;

import canvas.Canvas;
import geometry.Circle;
import java.awt.Color;

public interface CircleDrawer {
    void drawCircle(Circle circle, Canvas canvas, Color c) throws ArithmeticException;
}
