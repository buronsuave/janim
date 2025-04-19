package drawing;

import canvas.Canvas;
import geometry.Circle;
import java.awt.Color;

public class MathCircleDrawer implements CircleDrawer {
    @Override
    public void drawCircle(Circle circle, Canvas canvas, Color c) throws ArithmeticException {
        int xc = (int) circle.getXC();
        int yc = (int) circle.getYC();
        int r = (int) circle.getRadius();

        for (int x = xc-r; x <= xc+r; ++x) {
            int y = (int) Math.sqrt(r*r - (x-xc)*(x-xc));
            canvas.putPixel(x, yc + y, c);
            canvas.putPixel(x, yc - y, c);
        }
    }
}
