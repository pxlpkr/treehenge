import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;

public class DrawSurface extends JPanel {
    long[] times = new long[60];
    int c = 0;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        long tStart = System.nanoTime();

        g.setColor(new Color(255, 255, 255));
        g.fillRect(0, 0, (int) g.getClipBounds().getWidth(), (int) g.getClipBounds().getHeight());

        Application.getRenderer().render(g);

        long tEnd = System.nanoTime();

        //Timing logic
        times[c] = tEnd-tStart;
        c++; if (c >= 60) c = 0;
        long t = 0; for (long l : times) t += l;

        g.setColor(new Color(0, 0, 0));
        if (Application.DEBUG_FLAG) {
            g.drawString(String.format("Other: %.3f ms", (double) Application.getLoop().aOther / 1000000), 2, 12);
            g.drawString(String.format("Physi: %.3f ms", (double) Application.getLoop().aPhysics / 1000000), 2, 24);
            g.drawString(String.format("Rendr: %.3f ms", (double) t / 60 / 1000000), 2, 36);
            g.drawString(String.format("X: %s", Application.getRenderer().getCamera().getX()), 2, 60);
            g.drawString(String.format("Y: %s", Application.getRenderer().getCamera().getY()), 2, 72);
            g.drawString(String.format("Z: %s", Application.getRenderer().getCamera().getZ()), 2, 84);
        }
    }
}