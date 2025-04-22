package drawing.line;

import canvas.Canvas;
import geometry.Line;
import java.awt.Color;

public class LineDrawerMath implements LineDrawer{
    @Override
    public void drawLine(Line line, Canvas canvas, Color c) throws ArithmeticException {
        if (line.getM() == null) {
            // m is not defines = division by zero not handled in this algorithm
            throw new ArithmeticException("m = dy/dx failed since dx = 0 is not supported by MathLineDrawer");
        }

        int x0 = (int) line.getX0();
        int x1 = (int) line.getX1();
        double m = line.getM().getValue();
        double b = line.getB().getValue();

        for (int x = x0; x <= x1; ++x) {
            double y = (int) (m*x + b);
            canvas.putPixel(x, (int) Math.round(y), c);
        }
    }
}