import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.function.Supplier;

import treehenge.THGeometry;

public class Test {
    // private static void drawTriangle(int[][] matrix, Point p1, Point p2, Point p3) {
    //     drawLine(matrix, p1, p2);
    //     drawLine(matrix, p2, p3);
    //     drawLine(matrix, p3, p1);
    // }

    // private static void drawLine(int[][] matrix, Point p1, Point p2) {
    //     double slope;
    //     if (p2.getX() != p1.getX()) {
    //         slope = (p2.getY()-p1.getY())/(p2.getX()-p1.getX());
    //     } else {
    //         slope = Integer.MAX_VALUE;
    //     }
    //     if (slope < 1 && slope > -1) {
    //         Point min = getMin(p1, p2, p1::getX, p2::getX);
    //         Point max = getMax(p1, p2, p1::getX, p2::getX);
    //         for (int i = min.x; i <= max.x; i++) {
    //             matrix[i][(int) (min.y+slope*(i-min.x))] = new Color(255, 255, 255).getRGB();
    //         }
    //     } else {
    //         if (slope == Integer.MAX_VALUE) {
    //             slope = 0;
    //         } else {
    //             slope = (p2.getX()-p1.getX())/(p2.getY()-p1.getY());
    //         }
    //         Point min = getMin(p1, p2, p1::getY, p2::getY);
    //         Point max = getMax(p1, p2, p1::getY, p2::getY);
    //         for (int i = min.y; i <= max.y; i++) {
    //             matrix[(int) (min.x+slope*(i-min.y))][i] = new Color(255, 255, 255).getRGB();
    //         }
    //     }
    // }

    // private static Point getMin(Point p1, Point p2, Supplier<Double> g1, Supplier<Double> g2) {
    //     if (g2.get() > g1.get()) {
    //         return p1;
    //     }
    //     return p2;
    // }

    // private static Point getMax(Point p1, Point p2, Supplier<Double> g1, Supplier<Double> g2) {
    //     if (g2.get() > g1.get()) {
    //         return p2;
    //     }
    //     return p1;
    // }

    // private static BufferedImage Array2DToImage(int[][] matrix) {
    //     BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
    //     for (int x = 0; x < 200; x++) {
    //         for (int y = 0; y < 200; y++) {
    //             image.setRGB(x, y, matrix[x][y]);
    //         }
    //     }
    //     return image;
    // }
    private static float sign(Point p1, Point p2, Point p3)
    {
        return (p1.x - p3.x) * (p2.y - p3.y) - (p2.x - p3.x) * (p1.y - p3.y);
    }

    private static boolean PointInTriangle(Point pt, Point v1, Point v2, Point v3)
    {
        float d1 = sign(pt, v1, v2);
        float d2 = sign(pt, v2, v3);
        float d3 = sign(pt, v3, v1);

        boolean has_neg = (d1 < 0) || (d2 < 0) || (d3 < 0);
        boolean has_pos = (d1 > 0) || (d2 > 0) || (d3 > 0);

        return !(has_neg && has_pos);
    }

    private static void drawToBuffer(int[][] colormap, double[][] zmap, Point a, Point b, Point c) {
        int maxX = Math.max(a.x, Math.max(b.x, c.x));
        int minX = Math.min(a.x, Math.min(b.x, c.x));
        int maxY = Math.max(a.y, Math.max(b.y, c.y));
        int minY = Math.min(a.x, Math.min(b.x, c.x));
        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                if (PointInTriangle(new Point(x, y), a, b, c)) {
                    colormap[x][y] = new Color(255, 0, 0).getRGB();
                }
            }
        }
    }

    public static void main(String[] args) {
        int[][] i = new int[200][200];
        double[][] zbuffer = new double[200][200];

        drawToBuffer(i, zbuffer, new Point(40, 40), new Point(160, 120), new Point(60, 160));
        
        JFrame frame = new JFrame();
        frame.setSize(200, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new JPanel() {
            public void paintComponent(Graphics g) {
                BufferedImage img = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
                for (int x = 0; x < 200; x++) {
                    for (int y = 0; y < 200; y++) {
                        img.setRGB(x, y, i[x][y]);
                    }
                }

                g.drawImage(img, 0, 0, null);
            }
        });
        frame.setVisible(true);
    }
}