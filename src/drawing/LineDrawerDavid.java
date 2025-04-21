package drawing;

import canvas.Canvas;
import geometry.Line;
import java.awt.Color;

/**
 * This implementation fixes the following issues regarding MathLineDrawer:
 * 1. Draws vertical lines
 * 2. Draws lines with x1 < x0
 * 3. Enhance lines with m > 1 avoiding scattered plot
 */
public class LineDrawerDavid implements LineDrawer {
    @Override
    public void drawLine(Line line, Canvas canvas, Color c) throws ArithmeticException {

        // Slope very big, this avoids scattered plot: Switches x-axis with y-axis in a logical fashion
        // This guarantees the max number iterations occurs over the axis with maximum range
        if (line.getM() == null || Math.abs(line.getM().getValue()) > 1) {
            int y0, y1;

            // Swap points if needed
            if (line.getY0() <= line.getY1()) {
                y0 = (int) line.getY0();
                y1 = (int) line.getY1();
            } else {
                y0 = (int) line.getY1();
                y1 = (int) line.getY0();
            }

            // Purely vertical line, m and b are not defined but not required
            if (line.getM() == null) {
                int x = (int) line.getX0();
                for (int y = y0; y <= y1; ++y) {
                    canvas.putPixel(x, y, c);
                }
            } else {
                // Not purely vertical line, but with high slop.
                // Solve y = mx+b for x
                double b = line.getB().getValue();
                double m = line.getM().getValue();
                for (int y = y0; y <= y1; ++y) {
                    double x = (y - b) / m;
                    canvas.putPixel((int) Math.round(x), y, c);
                }
            }

        } else {
            // Mostly horizontal line
            int x0, x1;
            double b = line.getB().getValue();
            double m = line.getM().getValue();

            // Switches points if needed
            if (line.getX0() <= line.getX1()) {
                x0 = (int) line.getX0();
                x1 = (int) line.getX1();
            } else {
                x0 = (int) line.getX1();
                x1 = (int) line.getX0();
            }

            // Straightforward drawing, as in MathLineDrawer implementation
            for (int x = x0; x <= x1; ++x) {
                double y = m*x + b;
                canvas.putPixel(x, (int) Math.round(y), c);
            }
        }
    }
}
