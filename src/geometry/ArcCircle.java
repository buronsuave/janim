package geometry;

// CCW direction
public class ArcCircle {
    private Point center;
    private double radius;
    private double t0;
    private double t1;

    // Fix radius to x0, y0
    public ArcCircle(double cx, double cy, double x0, double y0, double x1, double y1) {
        center = new Point(cx, cy);
        radius = Math.sqrt(Math.pow(cx-x0, 2) + Math.pow(cy-y0, 2));
        t0 = arg(cx, cy, x0, y0);
        t1 = arg(cx, cy, x1, y1);
    }

    public ArcCircle(double cx, double cy, double radius, double t0, double t1) {
        this(new Point(cx, cy), radius, t0, t1);
    }

    public ArcCircle(Point center, double radius, double t0, double t1) {
        this.center = center;
        this.radius = radius;

        this.t0 = t0;
        this.t1 = t1;
    }

    private double arg(double xc, double yc, double x, double y) {
        // Avoid division by zero
        if (x == xc) {
            if (y > yc) {
                return Math.PI/2;
            } else {
                return 3*Math.PI/2;
            }
        }

        // Arc tangent is well-defined
        if ((x-xc) > 0 && (y-yc) >= 0) {
            // Positive arc tan
            return Math.atan((double) (y-yc) /(x-xc));
        } else if ((x-xc) < 0 && (y-yc) >= 0) {
            // Negative arc tan
            return Math.PI + Math.atan((double) (y-yc)/(x-xc));
        } else if ((x-xc) < 0 && (y-yc) < 0) {
            // Positive arc tan
            return Math.PI + Math.atan((double) (y-yc)/(x-xc));
        } else {
            // Negative arc tan
            return 2*Math.PI + Math.atan((double) (y-yc)/(x-xc));
        }
    }

    public double getXC() {
        return center.getX();
    }

    public double getYC() {
        return center.getY();
    }

    public double getRadius() {
        return radius;
    }

    public double getT0() {
        return t0;
    }

    public double getT1() {
        return t1;
    }
}
