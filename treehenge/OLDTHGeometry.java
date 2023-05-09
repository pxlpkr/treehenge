// package treehenge;

// import java.util.ArrayList;
// import java.util.List;

// import java.awt.Graphics;
// import java.awt.Color;

// public class THGeometryAny {
//     public List<THVector> vertices = new ArrayList<THVector>();
//     private double aveDist; public double getAveDist(){return this.aveDist;}

//     //Fill color
//     private Color fillColor;
//     public void setFillColor(Color c) {this.fillColor = c;}

//     //Outline
//     private boolean drawOutline = false;
//     public void setOutline(boolean b) {this.drawOutline = b;}
//     public boolean getOutline() {return this.drawOutline;}

//     public THGeometry(Color color, THVector... vertices) {
//         this.fillColor = color;
//         for (THVector vertex : vertices) {
//             this.vertices.add(vertex);
//         }
//     }
//     public THGeometry(THVector... vertices) {
//         for (THVector vertex : vertices) {
//             this.vertices.add(vertex);
//         }
//     }

//     public void add(THVector vector) {
//         for (THVector vertex : this.vertices) {
//             vertex.add(vector);
//         }
//     }

//     public double calculateAverageDistance(THCamera camera) {
//         double total = 0;
//         for (THVector v : this.vertices) {
//             THVector p = camera.getRelativeCoordinates(v);
//             total += Math.sqrt(Math.pow(p.x,2) + Math.pow(p.y,2) + Math.pow(p.z,2));
//         }
//         this.aveDist = total/this.vertices.size();
//         return this.aveDist;
//     }

//     public int[][] decompilePoints(THPoint[] points) {
//         int[] xPoints = new int[points.length];
//         int[] yPoints = new int[points.length];
//         for (int i = 0; i < points.length; i++) {
//             THPoint v = points[i];
//             xPoints[i] = v.x;
//             yPoints[i] = v.y;
//         }
//         return new int[][] {xPoints, yPoints};
//     }

//     public void draw(Graphics g, THCamera camera) {
//         if (this.getAveDist() >= camera.getRenderDistance()) {
//             return;
//         };
//         THVector[] v = camera.getRelativeCoordinates(this.vertices);
//         boolean inView = false;
//         for (THVector vertex: v) {
//             if (vertex.z > 0) {
//                 inView = true;
//                 break;
//             }
//         }
//         if (!inView) {
//             return;
//         }
//         THPoint[] p = camera.transform(v);
//         int[][] points = this.decompilePoints(p);
//         g.setColor(this.fillColor);
//         g.fillPolygon(points[0], points[1], this.vertices.size());
//         if (this.drawOutline) {
//             if (this.fillColor.getRed() == 0 && this.fillColor.getBlue() == 0 && this.fillColor.getGreen() == 0) {
//                 g.setColor(new Color(255,255,255));
//             } else {
//                 g.setColor(new Color(0,0,0));
//             }
//             g.drawPolygon(points[0], points[1], this.vertices.size());
//         }
//     }
// }
