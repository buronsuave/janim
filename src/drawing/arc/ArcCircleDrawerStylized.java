package drawing.arc;

import canvas.Canvas;
import drawing.Mask;
import drawing.Stroke;
import geometry.ArcCircle;
import geometry.Circle;

import java.awt.Color;
import java.util.ArrayList;

public class ArcCircleDrawerStylized implements ArcCircleDrawer {
    private final Stroke stroke;
    private final Mask mask;
    private final ArrayList<int[]> points;

    public ArcCircleDrawerStylized(int stroke, String mask) {
        this(new Stroke(stroke), new Mask(mask));
    }

    public ArcCircleDrawerStylized(Stroke stroke, Mask mask) {
        if (stroke == null) {
            this.stroke = new Stroke(1);
        } else {
            this.stroke = stroke;
        }

        if (mask == null) {
            this.mask = new Mask("1");
        } else {
            this.mask = mask;
        }

        points = new ArrayList<>();
        this.mask.scaleMask(this.stroke.getSize());
    }

    @Override
    public void drawArcCircle(ArcCircle arc, Canvas canvas, Color c) throws ArithmeticException {
        computeCircle(new Circle(arc.getXC(), arc.getYC(), arc.getRadius()));
        double t0 = arc.getT0();
        double t1 = arc.getT1();

        // Adjust mask if needed
        if (mask.length() % points.size() != 0) {
            mask.fixToIterations(points.size());
        }

        // drawSymmetric points
        for (int i = 0; i < points.size(); ++i) {
            int[] point = points.get(i);
            drawSymmetricPoints(
                    point[0],
                    point[1],
                    point[2],
                    point[3],
                    t0, t1, canvas, c, i);
        }
    }

    private void computeCircle(Circle circle) {
        // Midpoint approach
        int xc = (int) circle.getXC();
        int yc = (int) circle.getYC();
        int r = (int) circle.getRadius();
        int pk = 1 - r;
        int xf = (int) (r/Math.sqrt(2));
        int yk = r;

        points.add(new int[] {xc, yc, 0, r});
        for (int xk = 0; xk <= xf; ++xk) {
            if (pk < 0) {
                pk += 2*xk + 3;
            } else {
                --yk;
                pk += 2*xk - 2*yk + 3;
            }

            points.add(new int[] {xc, yc, (xk+1), yk});
        }
    }

    private void drawSymmetricPoints(int xc, int yc, int x, int y, double t0, double t1, Canvas canvas, Color c, int i) {
        if (mask.validate(i)) {
            if (isOnArc(arg(xc, yc, xc + x, yc + y), t0, t1) ^ (t0>t1))
                drawPointWithStroke(xc + x, yc + y, canvas, c);

            if (isOnArc(arg(xc, yc, xc - x, yc + y), t0, t1) ^ (t0>t1))
                drawPointWithStroke(xc - x, yc + y, canvas, c);

            if (isOnArc(arg(xc, yc, xc + x, yc - y), t0, t1) ^ (t0>t1))
                drawPointWithStroke(xc + x, yc - y, canvas, c);

            if (isOnArc(arg(xc, yc, xc - x, yc - y), t0, t1) ^ (t0>t1))
                drawPointWithStroke(xc - x, yc - y, canvas, c);
        }

        if (mask.validate(mask.length() - (i%mask.length()) - 1)) {
            if (isOnArc(arg(xc, yc, xc + y, yc + x), t0, t1) ^ (t0>t1))
                drawPointWithStroke(xc + y, yc + x, canvas, c);

            if (isOnArc(arg(xc, yc, xc - y, yc + x), t0, t1) ^ (t0>t1))
                drawPointWithStroke(xc - y, yc + x, canvas, c);

            if (isOnArc(arg(xc, yc, xc + y, yc - x), t0, t1) ^ (t0>t1))
                drawPointWithStroke(xc + y, yc - x, canvas, c);

            if (isOnArc(arg(xc, yc, xc - y, yc - x), t0, t1) ^ (t0>t1))
                drawPointWithStroke(xc - y, yc - x, canvas, c);
        }
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

    private void drawPointWithStroke(int x, int y, Canvas canvas, Color c) {
        for (int[] pair : stroke.getScopeMatrix()) {
            canvas.putPixel(x + pair[0], y + pair[1], c);
        }
    }
}
