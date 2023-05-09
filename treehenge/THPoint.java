package treehenge;

public class THPoint implements Comparable<THPoint> {
    public int x;
    public int y;
    public THPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(THPoint o) {
        if (o.x > this.x && o.y > this.y) {
            return 1;
        } else if (o.x < this.x && o.y < this.y) {
            return -1;
        } else {
            return 0;
        }
    }
}
