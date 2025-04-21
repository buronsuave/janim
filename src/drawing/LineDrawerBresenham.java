package drawing;

import canvas.Canvas;
import geometry.Line;
import java.awt.Color;

public class LineDrawerBresenham implements LineDrawer {
    @Override
    public void drawLine(Line line, Canvas canvas, Color c) throws ArithmeticException {
        // Paint starting point
        canvas.putPixel((int) line.getX0(), (int) line.getY0(), c);
        // Swap points if necessary
        if (line.getX0() > line.getX1())
            line.swapPoints();

        double dx = line.getX1() - line.getX0();
        double dy = line.getY1() - line.getY0();

        // Scenario 1 : 0 <= m <= 1,  x0 <= x1
        // Scenario 2 : 0 > m >= -1, x0 <= x1
        // Scenario 3 : m > 1, x0 <= x1 (includes vertical)
        // Scenario 4 : m < -1, x0 <= x1 (excludes vertical)
        final int scenario = getScenario(line);

        int A, B, pk, x0, y0, x1, y1, xk, yk;
        switch (scenario) {
            case 1:
                A = (int) (2*dy);
                B = (int) (2*dy - 2*dx);
                pk = (int) (2*dy - dx);
                x0 = (int) line.getX0();
                x1 = (int) line.getX1();
                yk = (int) line.getY0();

                for (xk = x0; xk <= x1; ++xk) {
                    if (pk < 0) {
                        canvas.putPixel(xk+1, yk, c);
                        pk += A;
                    } else {
                        canvas.putPixel(xk+1, yk++, c);
                        pk += B;
                    }
                }
                break;

            case 2:
                A = (int) (2*dy);
                B = (int) (2*dy + 2*dx);
                pk = (int) (-2*dy - dx);
                x0 = (int) line.getX0();
                x1 = (int) line.getX1();
                yk = (int) line.getY0();

                for (xk = x0; xk <= x1; ++xk) {
                    if (pk < 0) {
                        canvas.putPixel(xk+1, yk, c);
                        pk -= A;
                    } else {
                        canvas.putPixel(xk+1, yk--, c);
                        pk -= B;
                    }
                }
                break;

            case 3:
                // Swap case of vertical line in the opposite direction
                if (line.getM() == null && line.getY0() > line.getY1()) {
                    line.swapPoints();
                    dy *= -1;
                }

                A = (int) (2*dx);
                B = (int) (2*dx - 2*dy);
                pk = (int) (2*dx - dy);
                y0 = (int) line.getY0();
                y1 = (int) line.getY1();
                xk = (int) line.getX0();

                for (yk = y0; yk <= y1; ++yk) {
                    if (pk < 0) {
                        canvas.putPixel(xk, yk+1, c);
                        pk += A;
                    } else {
                        canvas.putPixel(xk++, yk+1, c);
                        pk += B;
                    }
                }
                break;

            case 4:
                A = (int) (2*dx);
                B = (int) (2*dx + 2*dy);
                pk = (int) (-2*dx - dy);
                y0 = (int) line.getY0();
                y1 = (int) line.getY1();
                xk = (int) line.getX0();

                for (yk = y0; yk >= y1; --yk) {
                    if (pk >= 0) {
                        canvas.putPixel(xk, yk-1, c);
                        pk -= A;
                    } else {
                        canvas.putPixel(xk++, yk-1, c);
                        pk -= B;
                    }
                }
                break;
        }
    }

    private static int getScenario(Line line) {
        if (line.getM() == null || line.getM().getValue() > 1) {
            return 3;
        } else if (line.getM().getValue() >= 0 && line.getM().getValue() <= 1) {
            return 1;
        } else if (line.getM().getValue() < 0 && line.getM().getValue() >= -1) {
            return 2;
        } else {
            return 4;
        }
    }
}
