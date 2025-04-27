package drawing.clipping;

import geometry.Line;

import java.util.ArrayList;
import java.util.List;

public class LineClipperExplicit extends LineClipperRectangular {
    public LineClipperExplicit(List<Line> lines, int xMin, int yMin, int xMax, int yMax) {
        super(lines, xMin, yMin, xMax, yMax);
    }

    @Override
    public void clip() {
        List<Line> newLines = new ArrayList<>();

        for (Line line : lines) {
            // Compute new intersections
            int x0 = (int) line.getX0();
            int x1 = (int) line.getX1();
            int y0 = (int) line.getY0();
            int y1 = (int) line.getY1();
            int yc = y0;

            // Case for vertical lines
            if (line.getM() == null || Math.abs(line.getM().getValue()) > 10_000) {
                // Line out of region
                if (x0 < xMin || x0 > xMax) {
                    continue;
                }

                // Swap point if needed (required y0 >= y1)
                if (y1 > y0) {
                    line.swapPoints();
                    x0 = (int) line.getX0();
                    x1 = (int) line.getX1();
                    y0 = (int) line.getY0();
                    y1 = (int) line.getY1();
                }

                if (y0 > yMax && y1 > yMax) continue; // All line is above region
                if (y0 > yMax && y1 < yMax) y0 = yMax; // Crosses top border
                if (y0 > yMin && y1 < yMin) y1 = yMin; // Crosses bottom border
                if (y0 < yMin && y1 < yMin) continue; // All line is below region

                newLines.add(new Line(x0, y0, x1, y1));
                continue;
            }

            // Case for horizontal lines
            if (Math.abs(line.getM().getValue()) < 0.0001) {
                // Line out of region
                if (y0 < yMin || y0 > yMax) {
                    continue;
                }

                // Swap point if needed (required x1 >= x0)
                if (x0 > x1) {
                    line.swapPoints();
                    x0 = (int) line.getX0();
                    x1 = (int) line.getX1();
                    y0 = (int) line.getY0();
                    y1 = (int) line.getY1();
                }

                if (x1 > xMax && x0 > xMax) continue; // All line is next to the region
                if (x1 > xMax && x0 < xMax) x1 = xMax; // Crosses right border
                if (x1 > xMin && x0 < xMin) x0 = xMin; // Crosses left border
                if (x1 < xMin && x0 < xMin) continue; // All line is next to the region

                newLines.add(new Line(x0, y0, x1, y1));
                continue;
            }

            // General case
            // Swap points if needed (required x0 < x1)
            if (x0 > x1) {
                line.swapPoints();
                x0 = (int) line.getX0();
                x1 = (int) line.getX1();
                y0 = (int) line.getY0();
                y1 = (int) line.getY1();
            }

            // Scape options...
            if (x1 < xMin || x0 > xMax) {
                continue;
            }

            double m = line.getM().getValue();
            int A0, A1, B0, B1;
            A0 = (int) ((1/m) * (yMin-y0) + x0);
            A1 = (int) ((1/m) * (yMax-y0) + x0);
            B0 = (int) (m * (xMin-x0) + y0);
            B1 = (int) (m * (xMax-x0) + y0);

            if (!isInside(x0, y0)) {
                if (isInside(A0, yMin) && x0 <= A0 && x1 >= A0) {
                    if (isInside(A1, yMax) && x0 <= A1 && x1 >= A1) {
                        if (A0 < A1) {
                            x0 = A0;
                            y0 = yMin;
                        } else {
                            x0 = A1;
                            y0 = yMax;
                        }
                    } else {
                        x0 = A0;
                        y0 = yMin;
                    }
                } else if (isInside(A1, yMax) && x0 <= A1 && x1 >= A1) {
                    x0 = A1;
                    y0 = yMax;
                } else if (isInside(xMin, B0) && Math.min(yc, y1) <= B0 && Math.max(yc, y1) >= B0) {
                    x0 = xMin;
                    y0 = B0;
                } else {
                    // Never cuts, impossible case
                    continue;
                }
            }

            if (!isInside(x1, y1)) {
                if (isInside(A0, yMin) && x0 <= A0 && x1 >= A0) {
                    if (isInside(A1, yMax) && x0 <= A1 && x1 >= A1) {
                        if (A0 < A1) {
                            x1 = A1;
                            y1 = yMax;
                        } else {
                            x1 = A0;
                            y1 = yMin;
                        }
                    } else {
                        if (isInside(xMax, B1) && Math.min(yc, y1) <= B1 && Math.max(yc, y1) >= B1) {
                            x1 = xMax;
                            y1 = B1;
                        } else {
                            x1 = A0;
                            y1 = yMin;
                        }
                    }
                } else if (isInside(A1, yMax) && x0 <= A1 && x1 >= A1) {
                    if (isInside(xMax, B1) && Math.min(yc, y1) <= B1 && Math.max(yc, y1) >= B1) {
                        x1 = xMax;
                        y1 = B1;
                    } else {
                        x1 = A1;
                        y1 = yMax;
                    }
                } else if (isInside(xMax, B1) && Math.min(yc, y1) <= B1 && Math.max(yc, y1) >= B1) {
                    x1 = xMax;
                    y1 = B1;
                } else {
                    // Never cuts, impossible case
                    continue;
                }
            }

            newLines.add(new Line(x0, y0, x1, y1));
        }

        this.lines = newLines;
    }

    private boolean isInside(int x, int y) {
        return (x >= xMin && x <= xMax && y >= yMin && y <= yMax);
    }
}