import canvas.Canvas;
import drawing.rectangle.RectangleDrawerManager;
import drawing.rectangle.RectangleStylizedDrawer;
import geometry.*;
import javax.swing.JFrame;
import java.awt.Color;

public class EditorApp {

    public static void main(String[] args) {
        JFrame frame = new JFrame("JANIM Editor's App");
        Canvas canvas = new Canvas(500, 500);

        // Configure frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(canvas);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);

        Rectangle rectangle = new Rectangle(100, 100, 200, 300);
        RectangleDrawerManager.setRectangleDrawer(new RectangleStylizedDrawer(3, "1100"));
        RectangleDrawerManager.draw(rectangle, canvas, Color.YELLOW);
    }
}
