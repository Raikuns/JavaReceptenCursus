package Template;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class RoundedBorder extends JPanel implements Border {

    private final int radius; // De straal voor de afgeronde hoeken
    private final Color color; // Kleur van de rand

    public RoundedBorder(int radius, Color color) {
        this.radius = radius;
        this.color = color;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);
        g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(10, 10, 10, 10); // Stel de ruimte binnen de border in
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}
