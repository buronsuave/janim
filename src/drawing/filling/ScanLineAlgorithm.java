package drawing.filling;

import canvas.Canvas;
import java.awt.Color;
import java.util.LinkedList;
import java.util.Queue;

public class ScanLineAlgorithm extends FillingAlgorithm {
    public ScanLineAlgorithm(Color fillColor) {
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

        while(!queue.isEmpty()) {
            int[] point = queue.poll();
            int x = point[0];
            int y = point[1];

            if (!inBounds(x, y, width, height)) continue;

            int currentColor = canvas.readPixel(x, y).getRGB();
            if (!validFillPixel(currentColor, borderRGB, fillRGB)) continue;

            // Valid pixel, start scanning
            int left = x;
            int right = x+1;

            // Expand left first
            while (left >= 0 && validFillPixel(canvas.readPixel(left, y).getRGB(), borderRGB, fillRGB)) {
                canvas.putPixel(left, y, colorFill);
                left--;
            }
            left++; // Last valid pixel

            // Then expand right
            while (right < width && validFillPixel(canvas.readPixel(right, y).getRGB(), borderRGB, fillRGB)) {
                canvas.putPixel(right, y, colorFill);
                right++;
            }
            right--; // Last valid pixel

            // Scan lines above and below
            for (int i = left; i < right; ++i) {
                if (y > 0 && validFillPixel(canvas.readPixel(i, y-1).getRGB(), borderRGB, fillRGB)) {
                    queue.add(new int[]{i, y-1});
                }
                if (y < height-1 && validFillPixel(canvas.readPixel(i, y+1).getRGB(), borderRGB, fillRGB)) {
                    queue.add(new int[]{i, y+1});
                }
            }
        }
    }

    private static boolean validFillPixel(int pixelRGB, int borderRGB, int fillRGB) {
        return pixelRGB != borderRGB && pixelRGB != fillRGB;
    }

    private static boolean inBounds(int x, int y, int width, int height) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
}
