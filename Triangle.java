package fixed;



public class Triangle {
    public Point a;
    public Point b;
    public Point c;

    // Конструктор, создает треугольник по 3-м точкам, которые были переданы в параметры
    public Triangle(Point p1, Point p2, Point p3) {
        this.a = p1;
        this.b = p2;
        this.c = p3;
    }

    public Triangle(Angle angle) {
        this.a = angle.top;
        this.b = angle.v1;
        this.c = angle.v2;
    }

    @Override
    public String toString() {
        return "Треугольник{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                '}';
    }
}
