package treehenge;

public class THVector implements Comparable<THVector> {
    public double x;
    public double y;
    public double z;

    public THVector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public THVector(THVector... vectors) {
        for (THVector v : vectors) {
            this.x += v.x;
            this.y += v.z;
            this.z += v.z;
        }
    }

    public double[] toDoubleArray() {
        return new double[] {x, y, z};
    }

    @Override
    public int compareTo(THVector o) {
        if (o.x > this.x && o.y > this.y && o.z > this.z) {
            return 1;
        } else if (o.x < this.x && o.y < this.y && o.z < this.z) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * Linearly adds the components of another vector to this vector
     * @param        o Vector to be added
     */
    public void add(THVector o) {
        this.x += o.x;
        this.y += o.y;
        this.z += o.z;
    }

    /**
     * Method for vector dot product multiplication
     * @param        a First vector
     * @param        b Second vector
     * @return       Scalar result of the dot product method
     */
    public static double dot(THVector a, THVector b) {
        return a.x*b.x + a.y*b.y + a.z*b.z;
    }

    public double getMagnitude() {
        return Math.sqrt(Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2));
    }
}