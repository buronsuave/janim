package drawing.ellipse;

import canvas.Canvas;
import drawing.Mask;
import drawing.Stroke;
import drawing.filling.FillingAlgorithm;
import geometry.Ellipse;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class EllipseDrawerStylized implements EllipseDrawer {
    private final Stroke stroke;
    private final Mask mask;
    private final ArrayList<int[]> points;
    private final FillingAlgorithm fillingAlgorithm;

    public EllipseDrawerStylized(int stroke, String mask) {
        this(new Stroke(stroke), new Mask(mask), null);
    }

    public EllipseDrawerStylized(int stroke, String mask, FillingAlgorithm fillAlgo) {
        this(new Stroke(stroke), new Mask(mask), fillAlgo);
    }

    public EllipseDrawerStylized(Stroke stroke, Mask mask, FillingAlgorithm fillAlgo) {
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

        // Filling Algorithm is null when no fill is required
        this.fillingAlgorithm = fillAlgo;

        points = new ArrayList<>();
        this.mask.scaleMask(this.stroke.getSize());
    }

    @Override
    public void drawEllipse(Ellipse ellipse, Canvas canvas, Color c) throws ArithmeticException {
        computeEllipse(ellipse);

        if (fillingAlgorithm != null) {
            // drawSymmetric points
            for (int[] point : points) {
                drawSymmetricPointsWithoutStyle(
                        point[0],
                        point[1],
                        point[2],
                        point[3],
                        canvas, fillingAlgorithm.getColorFill());
            }

            // Fill from center pixel
            fillingAlgorithm.fill((int) ellipse.getXC(), (int) ellipse.getYC(),
                    fillingAlgorithm.getColorFill(), canvas);
        }

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

    private void computeEllipse(Ellipse ellipse) {
        // Assume x**2/a**2 + y**2/b**2 = 1, a >= b
        int xc = (int) ellipse.getXC();
        int yc = (int) ellipse.getYC();

        int ry = (int) ellipse.getRadiusY();
        int rx = (int) ellipse.getRadiusX();
        int rx_2 = rx*rx;
        int ry_2 = ry*ry;
        int xf = (int) (rx_2 / Math.sqrt(rx_2 + ry_2));
        int yf = (int) (ry_2 / Math.sqrt(rx_2 + ry_2));

        points.add(new int[] {xc, yc, 0, ry});
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

            points.add(new int[] {xc, yc, xk+1, yk});
        }

        // Now swap x-axis with y-axis
        ArrayList<int[]> temp = new ArrayList<>();
        temp.add(new int[] {xc, yc, rx, 0});
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

            temp.add(new int[] {xc, yc, xk, yk+1});
        }

        Collections.reverse(temp);
        points.addAll(temp);

        // Remove duplicates
        Set<int[]> set = new LinkedHashSet<>(points);
        points.clear();
        points.addAll(set);
    }

    private void drawSymmetricPointsWithoutStyle(int xc, int yc, int x, int y, Canvas canvas, Color c) {
        canvas.putPixel(xc + x, yc + y, c);
        canvas.putPixel(xc - x, yc - y, c);
        canvas.putPixel(xc - x, yc + y, c);
        canvas.putPixel(xc + x, yc - y, c);

    }

    private void drawSymmetricPoints(int xc, int yc, int x, int y, Canvas canvas, Color c, int i) {
        if (mask.validate(i)) {
            drawPointWithStroke(xc + x, yc + y, canvas, c);
            drawPointWithStroke(xc - x, yc - y, canvas, c);
        }

        if (mask.validate(mask.length() - (i % mask.length()) - 1)) {
            drawPointWithStroke(xc - x, yc + y, canvas, c);
            drawPointWithStroke(xc + x, yc - y, canvas, c);
        }
    }

    private void drawPointWithStroke(int x, int y, Canvas canvas, Color c) {
        for (int[] pair : stroke.getScopeMatrix()) {
            canvas.putPixel(x + pair[0], y + pair[1], c);
        }
    }


}
