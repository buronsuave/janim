package drawing.circle;

import canvas.Canvas;
import geometry.Circle;
import java.awt.Color;

public class CircleDrawerPolar implements CircleDrawer {
    @Override
    public void drawCircle(Circle circle, Canvas canvas, Color c) throws ArithmeticException {
        int xc = (int) circle.getXC();
        int yc = (int) circle.getYC();
        int r = (int) circle.getRadius();
        double step = 0.01;

        for (double t = 0; t <= 2*Math.PI; t += step) {
            int x = (int) (xc + r*Math.cos(t));
            int y = (int) (yc + r*Math.sin(t));
            canvas.putPixel(x, y, c);
        }
    }
}
