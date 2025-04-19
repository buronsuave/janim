import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Test extends JPanel {

    private final BufferedImage canvas;

    public Test(int width, int height) {
        this.setPreferredSize(new Dimension(width, height));
        // TYPE_INT_RGB is fast and enough for most pixel drawing
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        clear(Color.WHITE);
    }

    public void putPixel(int x, int y, Color color) {
        if (x >= 0 && y >= 0 && x < canvas.getWidth() && y < canvas.getHeight()) {
            canvas.setRGB(x, y, color.getRGB());
        }
    }

    public void clear(Color color) {
        Graphics2D g2d = canvas.createGraphics();
        g2d.setColor(color);
        g2d.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        g2d.dispose();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(canvas, 0, 0, null);
    }

    // Optional: call this after many pixel changes to refresh display
    public void refresh() {
        repaint();
    }

    // For testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Pixel Renderer");
            Test panel = new Test(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Example: Draw a red diagonal line
            new Timer(1000, e -> {
                for (int i = 0; i < 500; i++) {
                    panel.putPixel(i, i, Color.RED);
                }
                panel.refresh();  // Only refresh once after drawing
                ((Timer) e.getSource()).stop();
            }).start();
        });
    }
}
