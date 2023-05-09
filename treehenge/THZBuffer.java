package treehenge;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class THZBuffer {
    private double[][] depthMap;
    private BufferedImage image;
    private double MAX_DEPTH;

    public THZBuffer(Dimension dim, double MAX_DEPTH) {
        int xMax, yMax;

        xMax = (int) dim.getWidth();
        yMax = (int) dim.getHeight();

        this.MAX_DEPTH = MAX_DEPTH;

        this.depthMap = new double[xMax][yMax];
        this.image = new BufferedImage(xMax, yMax, BufferedImage.TYPE_INT_RGB);
        Graphics imageGraphics = image.createGraphics();
        imageGraphics.setColor(new Color(255, 255, 255));
        imageGraphics.fillRect(0, 0, xMax, yMax);
        imageGraphics.dispose();
    }

    public void clearDepthMap() {
        for (double[] row: this.depthMap) {
            Arrays.fill(row, MAX_DEPTH);
        }
    }

    public void clearImage() {
        Graphics imageGraphics = image.createGraphics();
        imageGraphics.setColor(new Color(255, 255, 255));
        imageGraphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        imageGraphics.dispose();
    }

    private static float sign(THPoint p1, THPoint p2, THPoint p3) {
        return (p1.x - p3.x) * (p2.y - p3.y) - (p2.x - p3.x) * (p1.y - p3.y);
    }

    private static boolean isPointInTriangle(THPoint pt, THPoint v1, THPoint v2, THPoint v3) {
        float d1 = sign(pt, v1, v2);
        float d2 = sign(pt, v2, v3);
        float d3 = sign(pt, v3, v1);

        boolean has_neg = (d1 < 0) || (d2 < 0) || (d3 < 0);
        boolean has_pos = (d1 > 0) || (d2 > 0) || (d3 > 0);

        return !(has_neg && has_pos);
    }

    private static double getZ(THPoint pt, THVector[] vertices, THPoint points) {
        return 1;
    }

    public void feedGeometry(THVector[] vertices, THPoint[] points, Color color) {
        assert vertices.length == 3;
        assert points.length == 3;

        THPoint v1 = points[0];
        THPoint v2 = points[1];
        THPoint v3 = points[2];

        THVector V1 = vertices[0];
        THVector V2 = vertices[1];
        THVector V3 = vertices[2];

        if (v1.y > v2.y) {
            THPoint q = v1; v1 = v2; v2 = q;
            THVector q2 = V1; V1 = V2; V2 = q2;
        }

        if (v2.y > v3.y) {
            THPoint q = v2; v2 = v3; v3 = q;
            THVector q2 = V2; V2 = V3; V3 = q2;
        }

        if (v1.y > v2.y) {
            THPoint q = v1; v1 = v2; v2 = q;
            THVector q2 = V1; V1 = V2; V2 = q2;
        }

        for (int y = (int) Math.max(v1.y + 1, 0); y <= Math.min(v2.y, image.getHeight()); y++) {
            double num1 = (y - v1.y) / (v2.y - v1.y);
            double num2 = (y - v1.y) / (v3.y - v1.y);
            double x1 = (1 - num1) * v1.x + num1 * v2.x;
            double x2 = (1 - num2) * v1.x + num2 * v3.x;
            double z1 = (1 - num1) * V1.z + num1 * V2.z;
            double z2 = (1 - num2) * V1.z + num2 * V3.z;

            fill(y, x1, x2, z1, z2, color);
        }

        for (int y = (int) Math.max(0, v2.y + 1); y <= Math.min(v3.y, image.getHeight() - 1); y++) {
            double num1 = (y - v3.y) / (v2.y - v3.y);
            double num2 = (y - v3.y) / (v1.y - v3.y);
            double x1 = num1 * v2.x + (1 - num1) * v3.x;
            double x2 = (1 - num2) * v3.x + num2 * v1.x;
            double z1 = (1 - num1) * V3.z + num1 * V2.z;
            double z2 = (1 - num2) * V3.z + num2 * V1.z;

            fill(y, x1, x2, z1, z2, color);
        }
    }

    private void fill(int y, double x1, double x2, double z1, double z2, Color color) {
        System.out.printf("FILL FROM %s TO %s ON %s\n", x1, x2, y);
        if (x1 > x2) {
            double p = x1; x1 = x2; x2 = p;
            double q = z1; z1 = z2; z2 = q;
        }
        for (int x = (int) Math.max(x1 + 1, 0); x <= Math.min(x2, image.getHeight() - 1); x++) {
            double g = (x - x1) / (x2 - x1);
            double z = (1 - g) * z1 + g * z2;
            drawPixel(x, y, z, color);
        }
    }

    private void drawPixel(int x, int y, double z, Color color) {
        if (x > 0 && x < image.getWidth() && y > 0 && y < image.getHeight()) {
            if (z < depthMap[x][y]) {
                depthMap[x][y] = z;
                image.setRGB(x, y, color.getRGB());
            }
        }
    }

    /**
     * Considers a polygon in terms of its 3d and 2d points and
     * adds it to the Z-Buffer if necessary.
     * @param        vertices Vectors that represent the geometry in a 3D context
     * @param        points Points that represent the geometry in a 2D context
     * @param        color Color of the geometry
     */
    public void feedGeometryOriginal(THVector[] vertices, THPoint[] points, Color color) {
        assert vertices.length == 3;
        assert points.length == 3;

        int maxX, minX, maxY, minY;

        maxX = Math.max(points[0].x, Math.max(points[1].x, points[2].x));
        minX = Math.min(points[0].x, Math.min(points[1].x, points[2].x));
        maxY = Math.max(points[0].y, Math.max(points[1].y, points[2].y));
        minY = Math.min(points[0].x, Math.min(points[1].x, points[2].x));

        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                if (x > 0 && x < image.getWidth() && y > 0 && y < image.getHeight()
                && isPointInTriangle(new THPoint(x, y), points[0], points[1], points[2])) {
                    // double z = getZ(new THPoint(x, y), vertices, points);
                    image.setRGB(x, y, color.getRGB());
                }
            }
        }

        // final int MAX_ITERATION_DEPTH = Math.max(vertices.length, points.length);

        // for (int i = 0; i < MAX_ITERATION_DEPTH; i++) {
        //     final int j = (i+1) % MAX_ITERATION_DEPTH;

        //     final THVector[] v = new THVector[]{vertices[i], vertices[j]};
        //     final THPoint[] p = new THPoint[]{points[i], points[j]};

        //     double slope2D;
        //     if (p[1].x - p[0].x == 0) {
        //         slope2D = Math.signum(p[1].y - p[0].y);
        //     } else {
        //         slope2D = (p[1].y - p[0].y) / (p[1].x - p[0].x);
        //     }
        //     // double depthV = v[1].z - v[0].z;

        //     if (Math.abs(slope2D) > 1) {
        //         double signum = Math.signum(p[1].y - p[0].y);
        //         double nSlope = slope2D;
        //         if (p[1].x < p[0].x) {
        //             nSlope = -nSlope;
        //         }
        //         double x = p[0].x;
        //         for (int y = p[0].y; y < p[1].y * signum; y += signum) {
        //             if (x > depthMap.length || x < 0 || y > depthMap[0].length || y < 0) {
        //                 continue;
        //             }
        //             image.setRGB((int) x, y, new Color(255, 0, 0).getRGB());
        //             x += 1 / nSlope;
        //         }
        //     } else {
        //         double signum = Math.signum(p[1].x - p[0].x);
        //         double nSlope = slope2D;
        //         if (p[1].y < p[0].y) {
        //             nSlope = -nSlope;
        //         }
        //         double y = p[0].y;
        //         for (int x = p[0].x; x < p[1].x * signum; x += signum) {
        //             if (x > depthMap.length || x < 0 || y > depthMap[0].length || y < 0) {
        //                 continue;
        //             }
        //             image.setRGB(x, (int) y, new Color(255, 0, 0).getRGB());
        //             y += nSlope;
        //         }
        //     }

            // double z = v[0].getMagnitude();

            // if (depthMap[p[0].x][p[0].y] > z) {
            //     depthMap[p[0].x][p[0].y] = z;
            //     colorMap[p[0].x][p[0].y] = color;
            // }

            // double realY = v[0].y;
            // for (int x = p[0].x; x < p[1].x; x++) {
                
            // }
        }
    // }

    public BufferedImage getImage() {return this.image;}
}
