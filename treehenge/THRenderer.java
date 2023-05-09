package treehenge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Color;
import java.awt.Dimension;

public class THRenderer {
    private List<THGeometry> geometry = new ArrayList<THGeometry>();
    private THCamera camera;
    private THZBuffer buffer;
    
    public THRenderer(Dimension dim) {
        this.camera = new THCamera(dim);
        this.buffer = new THZBuffer(dim, camera.getRenderDistance());
    }

    public THCamera getCamera() {
        return this.camera;
    }

    public void addGeometry(THGeometry... geometry) {
        for (THGeometry g : geometry) {
            this.geometry.add(g);
        }
    }

    public void addGeometry(THConstruct... constructs) {
        for (THConstruct c : constructs) {
            for (THGeometry g : c.getGeometry()) {
                this.geometry.add(g);
            }
        }
    }

    public void render(Graphics g) {
        // buffer.clearImage();
        // buffer.clearDepthMap();
        // for (THGeometry e : this.geometry) {
        //     THVector[] location3D = camera.getRelativeCoordinates(e.getVertices());
        //     if (!THGeometry.zPositive(location3D)) {
        //         continue;
        //     }
        //     THPoint[] location2D = camera.transform(location3D);
        //     buffer.feedGeometry(location3D, location2D, e.getFillColor());
        // }
        // BufferedImage image = buffer.getImage();

        // g.drawImage(image, 0, 0, null);

        // THPoint[] points = new THPoint[]{
        //     new THPoint(80, 200),
        //     new THPoint(180, 360),
        //     new THPoint(140, 30)
        // };
        // for (THPoint p : points) {
        //     double distY_0_2 = (points[2].y - points[0].y);
        //     double distX_0_2 = (points[2].x - points[0].x);
        //     double slope_0_2 = (Math.min(distY_0_2,distX_0_2) / Math.max(distY_0_2,distX_0_2));
        // }

        for (THGeometry e : this.geometry) {
            e.calculateAverageDistance(this.camera);
        }
        Collections.sort(this.geometry, new Comparator<THGeometry>() {
            @Override
            public int compare(THGeometry g1, THGeometry g2) {
                return -Double.valueOf(g1.getAverageDistance()).compareTo(g2.getAverageDistance());
            }
        });
        for (THGeometry e : this.geometry) {
            e.draw(g, this.camera);
        }
    }
}