package treehenge.physics;

import java.util.ArrayList;
import java.util.List;

import treehenge.THCamera;
import treehenge.THConstruct;
import treehenge.THVector;

public class THPObject {
    private THConstruct construct;
    private List<THVector> forces = new ArrayList<THVector>();
    private THVector vertex;
    private THVector velocity = new THVector(0, 0, 0);
    private THCamera camera; public THCamera getCamera() {return this.camera;}
    private THVector cameraOffset;

    public THPObject(THConstruct construct) {
        this.construct = construct;
        this.vertex = new THVector(0,0,0);
    }

    public void tick() {
        this.moveRelative(this.getVelocityVector());
        if (this.getCamera() != null) {
            this.updateCamera();
        }
    }




    //Construct
    public THConstruct getConstruct() {return this.construct;}
    public void updateConstruct(THVector v) {
        this.construct.moveRelative(v);
    }

    //Forces
    public List<THVector> getForces() {return this.forces;}

    //Vertex
    public THVector getVertex() {return this.vertex;}
    public void moveRelative(double x, double y, double z) {
        this.moveRelative(new THVector(x, y, z));
    }
    public void moveRelative(THVector v) {
        this.vertex.add(v);
        this.updateConstruct(v);
    }

    //Velocity
    public THVector getVelocityVector() {return this.velocity;}
    public void applyForce(THVector force) {this.velocity.add(force);}

    //Camera
    public void linkCamera(THCamera camera) {this.camera = camera;}
    public void offsetCamera(THVector v) {this.cameraOffset = v;}
    public void updateCamera() {
        this.camera.setLocation(new THVector(this.getVertex(), this.cameraOffset));
    }
}
