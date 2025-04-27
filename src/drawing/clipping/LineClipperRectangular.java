package drawing.clipping;

import geometry.Line;

import java.util.List;

public abstract class LineClipperRectangular {
    protected List<Line> lines;
    protected final int xMax, xMin, yMax, yMin;

    public LineClipperRectangular(List<Line> lines, int xMin, int yMin, int xMax, int yMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.lines = lines;
    }

    public List<Line> getLines() {
        return lines;
    }
    public abstract void clip();
}
