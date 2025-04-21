package drawing;

import canvas.Canvas;
import geometry.Circle;
import java.awt.Color;

public class CircleDrawerMidpoint implements CircleDrawer {
    @Override
    public void drawCircle(Circle circle, Canvas canvas, Color c) throws ArithmeticException {
        int xc = (int) circle.getXC();
        int yc = (int) circle.getYC();
        int r = (int) circle.getRadius();
        int pk = 1 - r;
        int xf = (int) (r/Math.sqrt(2));
        int yk = r;

        drawSymmetricPoints(xc, yc, 0, r, canvas, c);

        for (int xk = 0; xk <= xf; ++xk) {
            if (pk < 0) {
                pk += 2*xk + 3;
            } else {
                --yk;
                pk += 2*xk - 2*yk + 3;
            }

            drawSymmetricPoints(xc, yc, xk+1, yk, canvas, c);
        }
    }

    private void drawSymmetricPoints(int xc, int yc, int x, int y, Canvas canvas, Color c) {
        canvas.putPixel(xc + x, yc + y, c);
        canvas.putPixel(xc - x, yc + y, c);
        canvas.putPixel(xc + x, yc - y, c);
        canvas.putPixel(xc - x, yc - y, c);

        canvas.putPixel(xc + y, yc + x, c);
        canvas.putPixel(xc - y, yc + x, c);
        canvas.putPixel(xc + y, yc - x, c);
        canvas.putPixel(xc - y, yc - x, c);
    }
}
