package treehenge;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class THConstruct {
    private List<THGeometry> geometry = new ArrayList<THGeometry>();
    private List<THVector> vertices = new ArrayList<THVector>();

    public THConstruct(THGeometry... g) {
        for (THGeometry i : g) {
            this.geometry.add(i);
        }
        this.organizeVertices();
    }

    public THConstruct(THConstruct... c) {
        for (THConstruct i : c) {
            this.geometry.addAll(i.getGeometry());
        }
        this.organizeVertices();
    }

    public static THConstruct Quadrilateral(THVector a, THVector b, THVector c, THVector d) {
        return new THConstruct(
            new THGeometry(a,b,c),
            new THGeometry(a,d,c)
        );
    }

    private void organizeVertices() {
        for (THGeometry g : this.geometry) {
            for (THVector v : g.getVertices()) {
                if (!this.vertices.contains(v)) {
                    this.vertices.add(v);
                }
            }
        }
    }

    public void moveRelative(THVector movementVector) {
        for (THVector vertex : vertices) {
            vertex.add(movementVector);
        }
    }

    public void setFillColor(Color c) {
        for (THGeometry g : geometry) {
            g.setFillColor(c);
        }
    }

    public void setBorderColor(Color c) {
        for (THGeometry g : geometry) {
            g.setBorderColor(c);
        }
    }

    public List<THGeometry> getGeometry() {return this.geometry;}
    public List<THVector> getVertices() {return this.vertices;}
}