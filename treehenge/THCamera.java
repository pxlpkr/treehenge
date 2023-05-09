package treehenge;

import java.util.List;

import java.awt.Dimension;

public class THCamera {
    private double renderDistance = Double.MAX_VALUE;
    public double getRenderDistance() {return this.renderDistance;}
    public void setRenderDistance(double d) {
        if (d > 0) {
            this.renderDistance = d;
        }
    }

    private double x = 0; public double getX() {return this.x;}
    private double y = 0; public double getY() {return this.y;}
    private double z = 0; public double getZ() {return this.z;}
    public void setX(double d) {this.x = d;}
    public void setY(double d) {this.y = d;}
    public void setZ(double d) {this.z = d;}
    private double yaw = 0; public double getYaw() {return this.yaw;}
    private double pitch = 0; public double getPitch() {return this.pitch;}
    private int[] dim;

    public THCamera(Dimension dim) {
        this.setDim(dim);
    }

    public void setDim(Dimension dim) {
        this.dim = new int[] {dim.width,dim.height};
    }

    public int[] getDim() {
        return this.dim;
    }

    public double getZScale(double z) {
        return Math.pow(0.25,z/this.dim[0]);
    }

    public THVector getRelativeCoordinates(THVector a) {
        double x = a.x-this.x;
        double y = a.y-this.y;
        double z = a.z-this.z;
        double xFinal = Math.cos(Math.toRadians(this.yaw))*x - Math.sin(Math.toRadians(this.yaw))*z;
        double zSubFinal = Math.cos(Math.toRadians(this.yaw))*z + Math.sin(Math.toRadians(this.yaw))*x;
        double zFinal = Math.sin(Math.toRadians(90+this.pitch))*zSubFinal - Math.cos(Math.toRadians(90+this.pitch))*y;
        double yFinal = Math.sin(Math.toRadians(90+this.pitch))*y + Math.cos(Math.toRadians(90+this.pitch))*zSubFinal;
        return new THVector(xFinal, yFinal, zFinal);
    }

    public THVector[] getRelativeCoordinates(THVector... v) {
        THVector[] vertices = new THVector[v.length];
        for (int i = 0; i < v.length; i++) {
            vertices[i] = this.getRelativeCoordinates(v[i]);
        }
        return vertices;
    }

    public THVector[] getRelativeCoordinates(List<THVector> v) {
        return this.getRelativeCoordinates(v.toArray(new THVector[0]));
    }

    public THPoint transform(THVector a) {
        int mainDim = Math.min(dim[0],dim[1]);
        double xFinal;
        double yFinal;
        if (a.z <= 0.0001) {
            xFinal = dim[0]/2+(mainDim/2)*a.x;
            yFinal = dim[1]/2+(mainDim/2)*a.y;
        } else {
            xFinal = dim[0]/2+(mainDim/2)*a.x/a.z;
            yFinal = dim[1]/2+(mainDim/2)*a.y/a.z;
        }
        return new THPoint((int) xFinal, (int) yFinal);
    }

    public THPoint[] transform(THVector... v) {
        THPoint[] pts = new THPoint[v.length];
        for (int i = 0; i < v.length; i++) {
            pts[i] = this.transform(v[i]);
        }
        return pts;
    }

    public THPoint[] transform(List<THVector> v) {
        return this.transform(v.toArray(new THVector[0]));
    }

    public void move(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public void moveRelative(double x, double y, double z) {
        this.x += x * Math.cos(Math.toRadians(this.yaw));
        this.x += z * Math.sin(Math.toRadians(this.yaw));
        this.z += z * Math.cos(Math.toRadians(this.yaw));
        this.z -= x * Math.sin(Math.toRadians(this.yaw));
        this.y += y;
    }

    public void rotateYaw(double q) {
        this.yaw += q;
        if (this.yaw < 0) {
            this.yaw += 360;
        }
        this.yaw %= 360;
    }

    public void setYaw(double q) {
        this.yaw = q % 360;
        if (this.yaw < 0) {
            this.yaw += 360;
        }
        this.yaw %= 360;
    }

    public void rotatePitch(double q) {
        this.pitch += q;
        this.pitch = Math.max(Math.min(this.pitch,90),-90);
    }

    public boolean inFrontOf(THVector p) {
        p = getRelativeCoordinates(p);
        return p.z < 0;
    }

    public void setLocation(THVector v) {
        this.setLocation(v.x, v.y, v.z);
    }

    public void setLocation(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public THVector getLocation() {
        return new THVector(this.x, this.y, this.z);
    }

    public String formatStats() {
        return String.format("X: %s\nY: %s\nZ: %s\nYaw: %s\n",this.x,this.y,this.z,this.yaw);
    }
}
