package geometry;

import utils.NullableDouble;

public class Line {
    private Point p0;
    private Point p1;
    private final NullableDouble m;
    private final NullableDouble b;

    public Line(double x0, double y0, double x1, double y1) {
        this(new Point(x0, y0), new Point(x1, y1));
    }

    public Line(Point p0, Point p1) {
        this.p0 = p0;
        this.p1 = p1;
        if (p0.getX() == p1.getX()) {
            m = null;
            b = null;
        } else {
            m = new NullableDouble((p1.getY() - p0.getY()) / (p1.getX() - p0.getX()));
            b = new NullableDouble(p0.getY() - (this.m.getValue() * p0.getX()));
        }
    }
    
    public double getX0() {
        return p0.getX();
    }

    public double getY0() {
        return p0.getY();
    }

    public double getX1() {
        return p1.getX();
    }

    public double getY1() {
        return p1.getY();
    }

    public NullableDouble getM() {
        return m;
    }

    public NullableDouble getB() {
        return b;
    }

    public void swapPoints() {
        Point p = p0;
        p0 = p1;
        p1 = p;
    }
}
