package geometry;

public class Ellipse {
    private Point center;
    private double radiusX;
    private double radiusY;

    public Ellipse(double cx, double cy, double radiusX, double radiusY) {
        this(new Point(cx, cy), radiusX, radiusY);
    }

    public Ellipse(Point center, double radiusX, double radiusY) {
        this.center = center;
        this.radiusX = radiusX;
        this.radiusY = radiusY;
    }

    public double getXC() {
        return center.getX();
    }

    public double getYC() {
        return center.getY();
    }

    public double getRadiusX() {
        return radiusX;
    }

    public double getRadiusY() {
        return radiusY;
    }

    public void swapRadii() {
        double t = radiusX;
        radiusX = radiusY;
        radiusY = t;
    }
}
