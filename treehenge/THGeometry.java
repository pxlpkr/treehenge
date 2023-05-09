package treehenge;

import java.awt.Color;
import java.awt.Graphics;

public class THGeometry {
    private THVector[] vertices = new THVector[3];
    private Color fillColor;
    private Color borderColor;
    private double averageDistance;

    public THGeometry(THVector a, THVector b, THVector c) {
        this.vertices = new THVector[] {a, b, c};
    }

    public void moveRelative(THVector movementVector) {
        for (THVector vertex : vertices) {
            vertex.add(movementVector);
        }
    }

    public double calculateAverageDistance(THCamera camera) {
        double total = 0;
        for (THVector v : this.vertices) {
            total += camera.getRelativeCoordinates(v).getMagnitude();
        }
        averageDistance = total/3;
        return averageDistance;
    }

    private int[][] decompilePoints(THPoint[] points) {
        int[] xPoints = new int[points.length];
        int[] yPoints = new int[points.length];
        for (int i = 0; i < points.length; i++) {
            THPoint v = points[i];
            xPoints[i] = v.x;
            yPoints[i] = v.y;
        }
        return new int[][] {xPoints, yPoints};
    }

    public static boolean zPositive(THVector[] v) {
        for (THVector vertex: v) {
            if (vertex.z > 0) {
                return true;
            }
        }
        return false;
    }

    public void draw(Graphics g, THCamera camera) {
        if (averageDistance >= camera.getRenderDistance()) {
            return;
        };
        THVector[] v = camera.getRelativeCoordinates(this.vertices);
        boolean inView = false;
        for (THVector vertex: v) {
            if (vertex.z > 0) {
                inView = true;
                break;
            }
        }
        if (!inView) {
            return;
        }
        THPoint[] p = camera.transform(v);
        int[][] points = this.decompilePoints(p);
        if (fillColor != null){
            g.setColor(fillColor);
            g.fillPolygon(points[0], points[1], 3);
        }
        if (borderColor != null) {
            g.setColor(borderColor);
            g.drawPolygon(points[0], points[1], 3);
        }
    }

    public THVector[] getVertices() {return this.vertices;}
    public Color getFillColor() {return this.fillColor;}
    public Color getBorderColor() {return this.borderColor;}
    public double getAverageDistance() {return this.averageDistance;}

    public void setFillColor(Color c) {this.fillColor = c;}
    public void setBorderColor(Color c) {this.borderColor = c;}
}
