package drawing.circle;

import canvas.Canvas;
import geometry.Circle;
import java.awt.Color;

public class CircleDrawerEightSymmetries implements CircleDrawer {
    @Override
    public void drawCircle(Circle circle, Canvas canvas, Color c) throws ArithmeticException {
        // Polar implementation
        int xc = (int) circle.getXC();
        int yc = (int) circle.getYC();
        int r = (int) circle.getRadius();
        double step = 0.01;

        // 8 symmetries here
        for (double t = 0; t <= Math.PI/4; t += step) {
            int dx = (int) (r*Math.cos(t));
            int dy = (int) (r*Math.sin(t));
            canvas.putPixel(xc + dx, yc + dy, c);
            canvas.putPixel(xc + dx, yc - dy, c);
            canvas.putPixel(xc - dx, yc - dy, c);
            canvas.putPixel(xc - dx, yc + dy, c);

            canvas.putPixel(xc + dy, yc + dx, c);
            canvas.putPixel(xc + dy, yc - dx, c);
            canvas.putPixel(xc - dy, yc - dx, c);
            canvas.putPixel(xc - dy, yc + dx, c);
        }
    }
}
