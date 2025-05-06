package drawing.clipping;

import geometry.ArcCircle;
import geometry.Circle;
import geometry.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CircleClipperExplicit extends CircleClipperRectangular {
    public CircleClipperExplicit(List<Circle> circles, int xMin, int yMin, int xMax, int yMax) {
        super(circles, xMin, yMin, xMax, yMax);
    }

    @Override
    public void clip() {
        clippedCircles.clear();
        for (Circle circle : circles) {
            // Intersections
            List<Point> points = new ArrayList<>();
            double xc = circle.getXC();
            double yc = circle.getYC();
            double r = circle.getRadius();

            double xPotUp = Math.pow(r,2) - Math.pow(yMax-yc,2);
            double xPotDown = Math.pow(r,2) - Math.pow(yMin-yc,2);
            double yPotLeft = Math.pow(r,2) - Math.pow(xMin-xc,2);
            double yPotRight = Math.pow(r,2) - Math.pow(xMax-xc,2);

            // Left
            if (yPotLeft > 0) {
                double y1 =  (yc + Math.sqrt(yPotLeft));
                double y2 =  (yc - Math.sqrt(yPotLeft));
                if (isOnRegion(xMin, y1)) {
                    points.add(new Point(xMin, y1));
                }
                if (isOnRegion(xMin, y2)) {
                    points.add(new Point(xMin, y2));
                }
            }

            // Top
            if (xPotUp > 0) {
                double x1 =  (xc + Math.sqrt(xPotUp));
                double x2 =  (xc - Math.sqrt(xPotUp));
                if (isOnRegion(x1, yMax)) {
                    points.add(new Point(x1, yMax));
                }
                if (isOnRegion(x2, yMax)) {
                    points.add(new Point(x2, yMax));
                }
            }

            // Bottom
            if (xPotDown > 0) {
                double x1 =  (xc + Math.sqrt(xPotDown));
                double x2 =  (xc - Math.sqrt(xPotDown));
                if (isOnRegion(x1, yMin)) {
                    points.add(new Point(x1, yMin));
                }
                if (isOnRegion(x2, yMin)) {
                    points.add(new Point(x2, yMin));
                }
            }

            // Right
            if (yPotRight > 0) {
                double y1 =  (yc + Math.sqrt(yPotRight));
                double y2 =  (yc - Math.sqrt(yPotRight));
                if (isOnRegion(xMax, y1)) {
                    points.add(new Point(xMax, y1));
                }
                if (isOnRegion(xMax, y2)) {
                    points.add(new Point(xMax, y2));
                }
            }

            // No intersections within region
            if (points.isEmpty()) {
                if (isOnRegion((xc+r), yc) && isOnRegion(xc, (yc))) {
                    clippedCircles.add(new ArcCircle(xc, yc, r, 0, 2*Math.PI));
                }
                continue;
            }

            // Compute all arguments
            List<Double> ts = new ArrayList<>();
            for (Point point : points) {
                ts.add(arg(xc, yc, point.getX(), point.getY()));
            }

            // Sort intersections
            Collections.sort(ts);

            if (ts.size() == 1) {
                // Weird scenario but OK...
                // If at least one point inside, is whole circle, otherwise continue
                if (isOnRegion((xc+r), yc) && isOnRegion(xc, yc)) {
                    clippedCircles.add(new ArcCircle(xc, yc, r, 0, 2*Math.PI));
                }
                continue;
            }

            // Scape option... only point is touching and at least one point is outside region
            if (ts.size()==2 && ts.get(0).doubleValue() == ts.get(1).doubleValue()) {
                if (isOnRegion((xc+r), yc) && isOnRegion(xc, yc)) {
                    clippedCircles.add(new ArcCircle(xc, yc, r, 0, 2*Math.PI));
                }
                continue;
            }

            int i = 0;
            if (!isOnRegion((xc+r),yc) && ts.get(0).doubleValue() == ts.get(1).doubleValue()) {
                i += 1;
            }
            if (isOnRegion((xc+r),yc) || ts.get(0).doubleValue() == ts.get(1).doubleValue()) {
                i += 1;
            }

            // Workaround here...
            if ((int) (yc) == yMin && i > 0 && ts.size() == 2) {
                // Draw first arc and that was it
                clippedCircles.add(new ArcCircle(xc, yc, r, ts.get(0), ts.get(1)));
                continue;
            }

            /*
            System.out.println("Here i = " + i);
            System.out.println("Window here: [" + xMin + "," + xMax + "], [" + yMin + "," + yMax + "]");
             */

            while(i < points.size() - 1) {
                clippedCircles.add(new ArcCircle(xc, yc, r, ts.get(i), ts.get(i+1)));
                i++;
                if (i < points.size()-1 && ts.get(i).doubleValue() == ts.get(i+1).doubleValue()) {
                    i++;
                }
                i++;
            }

            // Reverse arc if needed
            if (isOnRegion((int) (xc+ r), (int) yc)) {
                clippedCircles.add(new ArcCircle(xc, yc, r, ts.get(points.size()-1), ts.get(0)));
            }
             /*
            System.out.println("Params so far: Circle(" + xc + "," + yc + "," + r + ").");
            System.out.println("Params so far: Points(" + points + ")");
            System.out.println("Params so far: Angles(" + ts + ")");
            System.out.println("Params so far: Arcs = " + clippedCircles.size());
            System.out.println("Params so far: i = " + i);
            System.out.println();
              */
        }
    }

    private boolean isOnRegion(double x, double y) {
        return xMin <= x && xMax >= x && yMin <= y && yMax >= y;
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
        double arcTan = Math.atan((y-yc) /(x-xc));
        if ((x-xc) > 0 && (y-yc) >= 0) {
            // Positive arc tan
            return arcTan;
        } else if ((x-xc) < 0 && (y-yc) >= 0) {
            // Negative arc tan
            return Math.PI + arcTan;
        } else if ((x-xc) < 0 && (y-yc) < 0) {
            // Positive arc tan
            return Math.PI + arcTan;
        } else {
            // Negative arc tan
            return 2*Math.PI + arcTan;
        }
    }
}
