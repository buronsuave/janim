package drawing.filling;

import canvas.Canvas;
import java.awt.Color;
import java.util.LinkedList;
import java.util.Queue;

public class FloodFillAlgorithm extends FillingAlgorithm {

    public FloodFillAlgorithm(Color fillColor) {
        super(fillColor);
    }

    @Override
    public void fill(int x0, int y0, Color colorBorder, Canvas canvas) {
        int borderRGB = colorBorder.getRGB();
        int fillRGB = colorFill.getRGB();
        int startRGB = canvas.readPixel(x0, y0).getRGB();
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        if (!validFillPixel(startRGB, borderRGB, fillRGB)) return;

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] {x0, y0});

        while (!queue.isEmpty()) {
            int[] point = queue.poll();
            int x = point[0];
            int y = point[1];

            if (!inBounds(x, y, width, height)) continue;

            int currentRGB = canvas.readPixel(x, y).getRGB();
            if (!validFillPixel(currentRGB, borderRGB, fillRGB)) continue;

            canvas.putPixel(x, y, colorFill);

            // Add 4 neighboring pixels
            queue.add(new int[] {x + 1, y});
            queue.add(new int[] {x - 1, y});
            queue.add(new int[] {x, y + 1});
            queue.add(new int[] {x, y - 1});
        }
    }

    private static boolean validFillPixel(int pixelRGB, int borderRGB, int fillRGB) {
        return pixelRGB != borderRGB && pixelRGB != fillRGB;
    }

    private static boolean inBounds(int x, int y, int width, int height) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
}
