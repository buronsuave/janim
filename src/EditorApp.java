import canvas.Canvas;
import drawing.*;
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

        Ellipse ellipse = new Ellipse(250, 250, 150, 200);
        EllipseDrawerManager.setDrawer(new EllipseDrawerMidpoint());
        EllipseDrawerManager.draw(ellipse, canvas, Color.WHITE);
    }

}
