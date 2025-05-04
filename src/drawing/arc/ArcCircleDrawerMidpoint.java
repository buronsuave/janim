package drawing.arc;

import canvas.Canvas;
import geometry.ArcCircle;
import java.awt.Color;

public class ArcCircleDrawerMidpoint implements ArcCircleDrawer {
    @Override
    public void drawArcCircle(ArcCircle arc, Canvas canvas, Color c) throws ArithmeticException {
        int xc = (int) arc.getXC();
        int yc = (int) arc.getYC();
        int r = (int) arc.getRadius();
        int pk = 1 - r;
        int xf = (int) (r/Math.sqrt(2));
        int yk = r;

        double t0 = arc.getT0();
        double t1 = arc.getT1();
        drawSymmetricPoints(xc, yc, 0, r, t0, t1, canvas, c);

        for (int xk = 0; xk <= xf; ++xk) {
            if (pk < 0) {
                pk += 2*xk + 3;
            } else {
                --yk;
                pk += 2*xk - 2*yk + 3;
            }

            drawSymmetricPoints(xc, yc, xk+1, yk, t0, t1, canvas, c);
        }
    }

    private void drawSymmetricPoints(int xc, int yc, int x, int y, double t0, double t1, Canvas canvas, Color c) {
        if (isOnArc(arg(xc, yc, xc+x, yc+y), t0, t1) ^ (t0>t1))
            canvas.putPixel(xc + x, yc + y, c);

        if (isOnArc(arg(xc, yc, xc-x, yc+y), t0, t1) ^ (t0>t1))
            canvas.putPixel(xc - x, yc + y, c);

        if (isOnArc(arg(xc, yc, xc+x, yc-y), t0, t1) ^ (t0>t1))
            canvas.putPixel(xc + x, yc - y, c);

        if (isOnArc(arg(xc, yc, xc-x, yc-y), t0, t1) ^ (t0>t1))
            canvas.putPixel(xc - x, yc - y, c);

        if (isOnArc(arg(xc, yc, xc+y, yc+x), t0, t1) ^ (t0>t1))
            canvas.putPixel(xc + y, yc + x, c);

        if (isOnArc(arg(xc, yc, xc-y, yc+x), t0, t1) ^ (t0>t1))
            canvas.putPixel(xc - y, yc + x, c);

        if (isOnArc(arg(xc, yc, xc+y, yc-x), t0, t1) ^ (t0>t1))
            canvas.putPixel(xc + y, yc - x, c);

        if (isOnArc(arg(xc, yc, xc-y, yc-x), t0, t1) ^ (t0>t1))
            canvas.putPixel(xc - y, yc - x, c);
    }

    private boolean isOnArc(double arg, double t0, double t1) {
        return arg >= Math.min(t0, t1) && arg <= Math.max(t0, t1);
    }

    private double arg(int xc, int yc, int x, int y) {
        // Avoid division by zero
        if (x == xc) {
            if (y > yc) {
                return Math.PI/2;
            } else {
                return 3*Math.PI/2;
            }
        }

        // Arc tangent is well-defined
        if ((x-xc) > 0 && (y-yc) >= 0) {
            // Positive arc tan
            return Math.atan((double) (y-yc) /(x-xc));
        } else if ((x-xc) < 0 && (y-yc) >= 0) {
            // Negative arc tan
            return Math.PI + Math.atan((double) (y-yc)/(x-xc));
        } else if ((x-xc) < 0 && (y-yc) < 0) {
            // Positive arc tan
            return Math.PI + Math.atan((double) (y-yc)/(x-xc));
        } else {
            // Negative arc tan
            return 2*Math.PI + Math.atan((double) (y-yc)/(x-xc));
        }
    }
}
