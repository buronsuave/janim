package drawing;

import canvas.Canvas;
import geometry.Line;
import java.awt.Color;

public class DDALineDrawer implements LineDrawer {
    @Override
    public void drawLine(Line line, Canvas canvas, Color c) throws ArithmeticException {
        double dx = line.getX1() - line.getX0();
        double dy = line.getY1() - line.getY0();
        double steps = Math.max(Math.abs(dx), Math.abs(dy));
        double x_inc = dx / steps;
        double y_inc = dy / steps;

        double x = line.getX0();
        double y = line.getY0();
        canvas.putPixel((int) Math.round(x), (int) Math.round(y), c);
        for (int k = 1; k <= steps; ++k) {
            x += x_inc;
            y += y_inc;
            canvas.putPixel((int) Math.round(x), (int) Math.round(y), c);
        }
    }
}
