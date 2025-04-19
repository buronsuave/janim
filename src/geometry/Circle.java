package geometry;

public class Circle {
    private Point center;
    private double radius;

    public Circle(double cx, double cy, double radius) {
        this(new Point(cx, cy), radius);
    }

    public Circle(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public double getXC() {
        return center.getX();
    }

    public double getYC() {
        return center.getY();
    }
}
