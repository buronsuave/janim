package drawing.clipping;

import geometry.Line;

import java.util.ArrayList;
import java.util.List;

public class LineClipperSutherland extends LineClipperRectangular {


    private static final int CENTER = 0;
    private static final int TOP = 8;
    private static final int BOTTOM = 4;
    private static final int LEFT = 2;
    private static final int RIGHT = 1;
    private static final int TOP_LEFT = TOP | LEFT;
    private static final int TOP_RIGHT = TOP | RIGHT;
    private static final int TOP_CENTER = TOP | CENTER;
    private static final int BOTTOM_LEFT = BOTTOM | LEFT;
    private static final int BOTTOM_CENTER = BOTTOM | CENTER;
    private static final int BOTTOM_RIGHT = BOTTOM | RIGHT;

    public LineClipperSutherland() {
        this(new ArrayList<>(), 0, 0, 0, 0);
    }

    public LineClipperSutherland(List<Line> lines, int xMin, int yMin, int xMax, int yMax) {
        super(lines, xMin, yMin, xMax, yMax);
    }

    private int getRegion(int x, int y) {
        int region = CENTER;

        if (x < xMin) {
            region |= LEFT;
        } else if(x > xMax) {
            region |= RIGHT;
        }

        if (y < yMin) {
            region |= BOTTOM;
        } else if (y > yMax) {
            region |= TOP;
        }

        return region;
    }

    @Override
    public void clip() {
        List<Line> clippedLines = new ArrayList<>();

        for (Line line : lines) {
            int x0 = (int) line.getX0();
            int y0 = (int) line.getY0();
            int x1 = (int) line.getX1();
            int y1 = (int) line.getY1();
            int region0 = -1;
            int region1 = -1;

            while ((region0 | region1) != CENTER) {
                region0 = getRegion(x0, y0);
                region1 = getRegion(x1, y1);

                // Both ends are inside
                if ((region0 | region1) == CENTER) {
                    clippedLines.add(new Line(x0, y0, x1, y1));
                    break;
                }

                // Some boundary bit shared. Not crossing the center box
                if ((region0 & region1) != CENTER) {
                    break;
                }

                // We need to actually clip
                // First check point 0
                // Check top and bottom intersections
                if ((region0 & TOP) != CENTER) {
                    // Vertical line
                    y0 = yMax;
                    if (line.getM() != null) {
                        x0 = (int) ((yMax - line.getB().getValue())/line.getM().getValue());
                    }
                    if ((getRegion(x0, y0) & getRegion(x1, y1)) != CENTER) break;
                } else if ((region0 & BOTTOM) != CENTER) {
                    // Vertical line
                    y0 = yMin;
                    if (line.getM() != null) {
                        x0 = (int) ((yMin - line.getB().getValue()) / line.getM().getValue());
                    }
                    if ((getRegion(x0, y0) & getRegion(x1, y1)) != CENTER) break;
                }
                if ((region0 & LEFT) != CENTER) {
                    x0 = xMin;
                    y0 = (int) (line.getM().getValue()*xMin + line.getB().getValue());
                    if ((getRegion(x0, y0) & getRegion(x1, y1)) != CENTER) break;

                } else if ((region0 & RIGHT) != CENTER) {
                    x0 = xMax;
                    y0 = (int) (line.getM().getValue()*xMax + line.getB().getValue());
                    if ((getRegion(x0, y0) & getRegion(x1, y1)) != CENTER) break;
                }

                // Then check point 1
                // Check top and bottom intersections
                if ((region1 & TOP) != CENTER) {
                    // Vertical line
                    y1 = yMax;
                    if (line.getM() != null) {
                        x1 = (int) ((yMax - line.getB().getValue())/line.getM().getValue());
                    }
                    if ((getRegion(x0, y0) & getRegion(x1, y1)) != CENTER) break;
                } else if ((region1 & BOTTOM) != CENTER) {
                    // Vertical line
                    y1 = yMin;
                    if (line.getM() != null) {
                        x1 = (int) ((yMin - line.getB().getValue()) / line.getM().getValue());
                    }
                    if ((getRegion(x0, y0) & getRegion(x1, y1)) != CENTER) break;
                }
                if ((region1 & LEFT) != CENTER) {
                    x1 = xMin;
                    y1 = (int) (line.getM().getValue()*xMin + line.getB().getValue());
                    if ((getRegion(x0, y0) & getRegion(x1, y1)) != CENTER) break;
                } else if ((region1 & RIGHT) != CENTER) {
                    x1 = xMax;
                    y1 = (int) (line.getM().getValue()*xMax + line.getB().getValue());
                    if ((getRegion(x0, y0) & getRegion(x1, y1)) != CENTER) break;
                }
                if ((getRegion(x0, y0) & getRegion(x1, y1)) != CENTER) break;
            }
        }

        this.lines = clippedLines;
    }
}
