package drawing.line;

import canvas.Canvas;
import geometry.Line;
import java.awt.Color;

public class LineDrawerMidpoint implements LineDrawer {

    @Override
    public void drawLine(Line line, Canvas canvas, Color c) throws ArithmeticException {
        canvas.putPixel((int) line.getX0(), (int) line.getY0(), c);

        if (line.getX0() > line.getX1()) line.swapPoints();

        double dx = line.getX1() - line.getX0();
        double dy = line.getY1() - line.getY0();

        final int scenario = getScenario(line);

        int A, B, d, x0, y0, x1, y1, xk, yk;
        switch (scenario) {
            case 1: // 0 <= m <= 1
                x0 = (int) line.getX0();
                y0 = (int) line.getY0();
                x1 = (int) line.getX1();
                yk = y0;
                d = (int)(dy - dx / 2.0);
                A = (int) dy;
                B = (int) (dy - dx);

                for (xk = x0 + 1; xk <= x1; ++xk) {
                    if (d < 0) {
                        d += A;
                    } else {
                        d += B;
                        yk++;
                    }
                    canvas.putPixel(xk, yk, c);
                }
                break;

            case 2: // -1 <= m < 0
                x0 = (int) line.getX0();
                y0 = (int) line.getY0();
                x1 = (int) line.getX1();
                yk = y0;
                d = (int)(dy + dx / 2.0);
                A = (int) dy;
                B = (int) (dy + dx);

                for (xk = x0 + 1; xk <= x1; ++xk) {
                    if (d > 0) {
                        d += A;
                    } else {
                        d += B;
                        yk--;
                    }
                    canvas.putPixel(xk, yk, c);
                }
                break;

            case 3: // m > 1
                // Swap case of vertical line in the opposite direction
                if (line.getM() == null && line.getY0() > line.getY1()) {
                    line.swapPoints();
                    dy *= -1;
                }

                x0 = (int) line.getX0();
                y0 = (int) line.getY0();
                y1 = (int) line.getY1();
                xk = x0;
                d = (int)(dx - dy / 2.0);
                A = (int) dx;
                B = (int) (dx - dy);

                for (yk = y0 + 1; yk <= y1; ++yk) {
                    if (d < 0) {
                        d += A;
                    } else {
                        d += B;
                        xk++;
                    }
                    canvas.putPixel(xk, yk, c);
                }
                break;

            case 4: // m < -1
                x0 = (int) line.getX0();
                y0 = (int) line.getY0();
                y1 = (int) line.getY1();
                xk = x0;
                d = (int) (dx + dy / 2);
                A = (int) dx;
                B = (int) (dx + dy);

                for (yk = y0 - 1; yk >= y1; --yk) {
                    if (d < 0) {
                        d += A;
                    } else {
                        d += B;
                        xk++;
                    }
                    canvas.putPixel(xk, yk, c);
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
