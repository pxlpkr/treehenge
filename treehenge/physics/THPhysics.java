package treehenge.physics;

import java.util.ArrayList;
import java.util.List;

import treehenge.THGeometry;
import treehenge.THVector;

public class THPhysics {
    private double simulationDistance = Double.MAX_VALUE;
    public double getSimulationDistance() {return this.simulationDistance;}
    public void setSimulationDistance(double d) {
        if (d > 0) {
            this.simulationDistance = d;
        }
    }

    private boolean paused = true;
    public void pause() {this.paused = true;}
    public void start() {this.paused = false;}
    public void toggle() {this.paused = !this.paused;}

    private Double globalGround;
    public void setGlobalGround(double d) {this.globalGround = d;}
    public void removeGlobalGround() {this.globalGround = null;}
    public Double getGlobalGround() {return this.globalGround;}

    private List<THVector> globalForces = new ArrayList<THVector>();
    public void addGlobalForce(THVector f) {this.globalForces.add(f);}
    public void removeGlobalForce(THVector f) {this.globalForces.remove(f);}
    public List<THVector> getGlobalForces() {return this.globalForces;}

    private List<THPObject> objects = new ArrayList<THPObject>();
    private List<THGeometry> staticObjects = new ArrayList<THGeometry>();
    
    public THPhysics() {}

    public void addObject(THPObject obj) {
        this.objects.add(obj);
    }

    public void addStaticObject(THGeometry obj) {
        this.staticObjects.add(obj);
    }

    public void tick() {
        if (this.paused) {
            return;
        } else {
            this.tickPhysicsObjects();
        }
    }

    public void tickPhysicsObjects() {
        for (final THPObject obj : this.objects) {
            for (final THVector force : obj.getForces()) {
                obj.applyForce(force);
            }
            for (final THVector force : this.globalForces) {
                obj.applyForce(force);
            }
            THVector velocity = obj.getVelocityVector();
            boolean collisionFound = false;
            for (THVector vertex : obj.getConstruct().getVertices()) {
                THVector vertexNext = new THVector(vertex, velocity);
                if (this.checkCollision(vertex, vertexNext)) {
                    collisionFound = true;
                    break;
                };
            }
            if (!collisionFound) {
                obj.tick();
            } {
                //whoo yay collision handling!
            }
        }
    }

    public boolean checkCollision(THVector a, THVector b) {
        for (THGeometry geom : this.staticObjects) {
            if (geom.getAverageDistance() <= this.simulationDistance) {
                
            }
        }
        return false;
    }
}
