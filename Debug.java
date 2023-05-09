import java.awt.Color;

import java.util.ArrayList;
import java.util.List;

import treehenge.*;

public class Debug {
    public static double[][] createHeights() {
        double[][] heights = new double[201][201];
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                if (i == 0) {
                    if (j == 0) {
                        heights[i][j] = 0;
                    } else {
                        heights[i][j] = heights[i][j-1]+Math.random()-0.5;
                    }
                } else {
                    if (j == 0) {
                        heights[i][j] = heights[i-1][j]+Math.random()-0.5;
                    } else {
                        heights[i][j] = (heights[i][j-1]+heights[i-1][j])/2+Math.random()-0.5;
                    }
                }
            }
        }
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                heights[i][j] += 5;
            }
        }
        return heights;
    }

    public static void createGroundMesh(double[][] heights) {
        List<THConstruct> planes = new ArrayList<THConstruct>();
        for (int i = -100; i < 100; i++) {
            for (int j = -100; j < 100; j++) {
                THConstruct geometry = THConstruct.Quadrilateral(
                    new THVector(i  , heights[i+100][j+100], j),
                    new THVector(i+1, heights[i+101][j+100], j),
                    new THVector(i+1, heights[i+101][j+101], j+1),
                    new THVector(i  , heights[i+100][j+101], j+1)
                );
                geometry.setFillColor(new Color((int)(Math.random()*180),120,50));
                // THGeometry geometry = new THGeometry(
                //     new Color((int)(Math.random()*180),120,50),
                //     new THVector(i  , heights[i+50][j+50], j),
                //     new THVector(i+1, heights[i+51][j+50], j),
                //     new THVector(i+1, heights[i+51][j+51], j+1),
                //     new THVector(i  , heights[i+50][j+51], j+1)
                // );
                planes.add(geometry);
                // Application.getPhysics().addStaticObject(geometry);
            }
        }

        Application.getRenderer().addGeometry(planes.toArray(new THConstruct[planes.size()]));
        Application.getRenderer().getCamera().setLocation(0,heights[50][50]-2,0);
    }

    // public static void createAxisDisplay() {
    //     THConstruct a = THConstruct.RectangularPrism(new THVector(0,-0.2,-0.2), new THVector(5, 0.2, 0.2));
    //     a.setColor(new Color(255,0,0));
    //     THConstruct b = THConstruct.RectangularPrism(new THVector(-0.2,0,-0.2), new THVector(0.2, -5, 0.2));
    //     b.setColor(new Color(0,255,0));
    //     THConstruct c = THConstruct.RectangularPrism(new THVector(-0.2,-0.2,0), new THVector(0.2, 0.2, 5));
    //     c.setColor(new Color(0,0,255));
    //     Application.getRenderer().addGeometry(new THConstruct[] {a,b,c});
    // }
}
