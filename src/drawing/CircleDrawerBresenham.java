package drawing;

import canvas.Canvas;
import geometry.Circle;
import java.awt.Color;

public class CircleDrawerBresenham implements CircleDrawer {
    @Override
    public void drawCircle(Circle circle, Canvas canvas, Color c) throws ArithmeticException {
        int xc = (int) circle.getXC();
        int yc = (int) circle.getYC();
        int r = (int) circle.getRadius();
        int xk = 0;
        int yk = r;
        int pk = 3 - 2 * r;

        drawSymmetricPoints(xc, yc, xk, yk, canvas, c);

        while (xk < yk) {
            xk++;
            if (pk < 0) {
                pk = pk + 4 * xk + 6;
            } else {
                --yk;
                pk = pk + 4 * (xk - yk) + 10;
            }
            drawSymmetricPoints(xc, yc, xk, yk, canvas, c);
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