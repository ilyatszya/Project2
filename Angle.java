package fixed;


public class Angle {
    public Point top;
    public Point v1;
    public Point v2;

    // Конструктор, создающий острый угол по 3-м точкам, указанным в параметрах
    public Angle(Point p1, Point p2, Point p3) {
        this.top = p1;
        this.v1 = p2;
        this.v2 = p3;
    }

    @Override
    public String toString() {
        return "Угол(" +
                "вершина=" + top +
                ", v1=" + v1 +
                ", v2=" + v2 +
                '}';
    }
}
