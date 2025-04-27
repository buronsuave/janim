package drawing.filling;

import canvas.Canvas;

import java.awt.Color;

public abstract class Filler {
    protected Color colorFill;
    public Filler(Color colorFill) {
        this.colorFill = colorFill;
    }

    public abstract void fill(int x0, int y0, Color colorBorder, Canvas canvas);
    public Color getColorFill() { return colorFill; }
}
