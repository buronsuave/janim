package drawing.clipping;

import geometry.ArcCircle;
import geometry.Circle;

import java.util.ArrayList;
import java.util.List;

public abstract class CircleClipperRectangular {
    protected List<Circle> circles;
    protected List<ArcCircle> clippedCircles;
    protected final int xMax, xMin, yMax, yMin;

    public CircleClipperRectangular(List<Circle> circles, int xMin, int yMin, int xMax, int yMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.circles = circles;
        clippedCircles = new ArrayList<>();
    }

    public List<Circle> getCircles() {
        return circles;
    }

    public List<ArcCircle> getClippedCircles() {
        return clippedCircles;
    }

    public abstract void clip();
}
