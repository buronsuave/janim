package drawing.line;

import canvas.Canvas;
import drawing.Mask;
import drawing.Stroke;
import geometry.Line;
import java.awt.Color;
import java.util.ArrayList;

public class LineDrawerStylized implements LineDrawer {
    private final Stroke stroke;
    private final Mask mask;
    private final ArrayList<int[]> points;

    public LineDrawerStylized(int stroke, String mask) {
        this(new Stroke(stroke), new Mask(mask));
    }

    public LineDrawerStylized(Stroke stroke, Mask mask) {
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
    public void drawLine(Line line, Canvas canvas, Color c) throws ArithmeticException {
        computeLine(line, canvas, c);

        // Adjust mask if needed
        if (mask.length() % points.size() != 0) {
            mask.fixToIterations(points.size());
        }
    }

    private void computeLine(Line line, Canvas canvas, Color c) {
        // Midpoint based approach
        if (line.getX0() > line.getX1()) line.swapPoints();

        // drawPointWithStroke((int) line.getX0(), (int) line.getY0(), canvas, c);
        points.add(new int[] {(int) line.getX0(), (int) line.getY0()});
        double dx = line.getX1() - line.getX0();
        double dy = line.getY1() - line.getY0();

        final int scenario = getScenario(line);

        int A, B, d, x0, y0, x1, y1, xk, yk;

        // For mask
        int index = 0;

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

                    points.add(new int[] {xk, yk});
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

                    points.add(new int[] {xk, yk});
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

                    points.add(new int[] {xk, yk});
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

                    points.add(new int[] {xk, yk});
                }
                break;
        }

        mask.fixToIterations(points.size());
        for (int i = 0; i < points.size(); ++i) {
            if (mask.validate(i)) {
                int[] point = points.get(i);
                drawPointWithStroke(point[0], point[1], canvas, c);
            }
        }
    }

    private void drawPointWithStroke(int x, int y, Canvas canvas, Color c) {
        for (int[] pair : stroke.getScopeMatrix()) {
            canvas.putPixel(x + pair[0], y + pair[1], c);
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
