package drawing.line;

import canvas.Canvas;
import geometry.Line;
import java.awt.Color;

public interface LineDrawer {
    void drawLine(Line line, Canvas canvas, Color c) throws ArithmeticException;
}
