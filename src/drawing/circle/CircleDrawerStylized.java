package drawing.circle;

import canvas.Canvas;
import drawing.Mask;
import drawing.Stroke;
import geometry.Circle;
import java.awt.Color;
import java.util.ArrayList;

public class CircleDrawerStylized implements CircleDrawer {
    private final Stroke stroke;
    private final Mask mask;
    private final ArrayList<int[]> points;

    public CircleDrawerStylized(int stroke, String mask) {
        this(new Stroke(stroke), new Mask(mask));
    }

    public CircleDrawerStylized(Stroke stroke, Mask mask) {
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
    public void drawCircle(Circle circle, Canvas canvas, Color c) throws ArithmeticException {
        computeCircle(circle);

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
                    canvas, c, i);
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

    private void drawSymmetricPoints(int xc, int yc, int x, int y, Canvas canvas, Color c, int i) {
        if (mask.validate(i)) {
            drawPointWithStroke(xc + x, yc + y, canvas, c);
            drawPointWithStroke(xc - y, yc + x, canvas, c);
            drawPointWithStroke(xc + y, yc - x, canvas, c);
            drawPointWithStroke(xc - x, yc - y, canvas, c);
        }

        if (mask.validate(mask.length() - (i%mask.length()) - 1)) {
            drawPointWithStroke(xc + y, yc + x, canvas, c);
            drawPointWithStroke(xc - x, yc + y, canvas, c);
            drawPointWithStroke(xc + x, yc - y, canvas, c);
            drawPointWithStroke(xc - y, yc - x, canvas, c);
        }
    }

    private void drawPointWithStroke(int x, int y, Canvas canvas, Color c) {
        for (int[] pair : stroke.getScopeMatrix()) {
            canvas.putPixel(x + pair[0], y + pair[1], c);
        }
    }
}
