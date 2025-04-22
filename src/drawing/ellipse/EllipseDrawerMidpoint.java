package drawing.ellipse;

import canvas.Canvas;
import geometry.Ellipse;
import java.awt.Color;

public class EllipseDrawerMidpoint implements EllipseDrawer {
    @Override
    public void drawEllipse(Ellipse ellipse, Canvas canvas, Color c) throws ArithmeticException {
        // Assume x**2/a**2 + y**2/b**2 = 1, a >= b
        int xc = (int) ellipse.getXC();
        int yc = (int) ellipse.getYC();

        int ry = (int) ellipse.getRadiusY();
        int rx = (int) ellipse.getRadiusX();
        int rx_2 = rx*rx;
        int ry_2 = ry*ry;
        int xf = (int) (rx_2 / Math.sqrt(rx_2 + ry_2));
        int yf = (int) (ry_2 / Math.sqrt(rx_2 + ry_2));

        drawSymmetricPoints(xc, yc, 0, ry, canvas, c);
        // Approximation with b-1/4 = b
        int pk = ry*(ry-rx);
        int yk = ry;
        int xk = 0;
        for (; xk <= xf; ++xk) {
            if (pk < 0) {
                pk += ry_2*(2*xk + 3);
            } else {
                --yk;
                pk += ry_2*(2*xk + 3) - rx_2*(2*yk);
            }

            drawSymmetricPoints(xc, yc, xk+1, yk, canvas, c);
        }

        // Now swap x-axis with y-axis
        drawSymmetricPoints(xc, yc, rx, 0, canvas, c);
        // Approximation with b-1/4 = b
        pk = rx*(rx-ry);
        xk = rx;
        yk = 0;
        for (; yk <= yf; ++yk) {
            if (pk < 0) {
                pk += rx_2*(2*yk + 3);
            } else {
                --xk;
                pk += rx_2*(2*yk + 3) - ry_2*(2*xk);
            }

            drawSymmetricPoints(xc, yc, xk, yk+1, canvas, c);
        }
    }

    private void drawSymmetricPoints(int xc, int yc, int x, int y, Canvas canvas, Color c) {
        canvas.putPixel(xc + x, yc + y, c);
        canvas.putPixel(xc - x, yc + y, c);
        canvas.putPixel(xc + x, yc - y, c);
        canvas.putPixel(xc - x, yc - y, c);
    }
}
