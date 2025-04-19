package geometry;

public class Rectangle {
    private Line[] sides;
    private Point[] vertices;
    private double width;
    private double height;

    public Rectangle(Point p0, Point p1) {
        this(p0.getX(), p0.getY(),
                p1.getX() - p0.getX(),
                p1.getY() - p0.getY());
    }

    public Rectangle(double x0, double y0, double width, double height) {
        this.width = width;
        this.height = height;

        Point p1 = new Point(x0, y0);
        Point p2 = new Point(x0+width, y0);
        Point p3 = new Point(x0+width, y0+height);
        Point p4 = new Point(x0, y0+height);

        this.vertices = new Point[4];
        vertices[0] = p1;
        vertices[1] = p2;
        vertices[2] = p3;
        vertices[3] = p4;

        this.sides = new Line[4];
        this.sides[0] = new Line(p1, p2);
        this.sides[1] = new Line(p2, p3);
        this.sides[2] = new Line(p3, p4);
        this.sides[3] = new Line(p4, p1);
    }

    public Point[] getVertices() {
        return vertices;
    }

    public Line[] getSides() {
        return sides;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }
}
