package fixed;

import java.util.ArrayList;
import java.util.List;


public class Shape {
    public Triangle triangle;
    public Angle angle;

    List<Point> result = new ArrayList<>();

    public Shape(Triangle t, Angle a) {
        this.triangle = t;
        this.angle = a;

        getShape();
    }
    public Shape(){

    }

    private void getShape() {
        Line AB = new Line(triangle.a, triangle.b);
        Line BC = new Line(triangle.b, triangle.c);
        Line AC = new Line(triangle.a, triangle.c);

        Line firstSide = new Line(angle.top, new Point(angle.v1.x, angle.v1.y));
        Line secondSide = new Line(angle.top, new Point(angle.v2.x, angle.v2.y));

        if (isInsideTheTriangle(angle.top, triangle)) {
            result.add(angle.top);
        }

        if (isInsideTheTriangle(triangle.a, new Triangle(angle))) {
            result.add(triangle.a);
        }

        if (isInsideTheTriangle(triangle.b, new Triangle(angle))) {
            result.add(triangle.b);
        }

        if (isInsideTheTriangle(triangle.c, new Triangle(angle))) {
            result.add(triangle.c);
        }

        addPoint(AB, firstSide);
        addPoint(BC, firstSide);
        addPoint(AC, firstSide);

        addPoint(AB, secondSide);
        addPoint(BC, secondSide);
        addPoint(AC, secondSide);
    }

    private void addPoint(Line line2, Line line1) {
        Point point = pointOfIntersection(line2, line1); // Получаем точку пересения отрезков l1, l2

        if (point != null) {
            result.add(point);
        }
    }

    private static Point pointOfIntersection(Line l1, Line l2) {
        double x1 = l1.p1.x;
        double y1 = l1.p1.y;

        double x2 = l1.p2.x;
        double y2 = l1.p2.y;

        double x3 = l2.p1.x;
        double y3 = l2.p1.y;

        double x4 = l2.p2.x;
        double y4 = l2.p2.y;

        if ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4) == 0) return null;

        double x, y;

        double a = (y2 - y1) / (x2 - x1);
        double b = (y4 - y3) / (x4 - x3);

        double c = y1 - a * x1;
        double d = y3 - b * x3;

        x = (d - c) / (a - b);
        y = a * x + c;

        if ((x >= Math.min(x1, x2) && x >= Math.min(x3, x4)) && (x <= Math.max(x1, x2) && x <= Math.max(x3, x4))
                && (y >= Math.min(y1, y2) && y >= Math.min(y3, y4)) && (y <= Math.max(y1, y2) && y <= Math.max(y3, y4))) {
            return new Point((int) x, (int) y);
        } else return null;
    }

    // Метод, проверяющий лежит ли данная точка внутри треугольника

    private static boolean isInsideTheTriangle(Point p, Triangle t) {
        double x1, x2, x3, x0;
        double y1, y2, y3, y0;

        x0 = p.x;
        y0 = p.y;

        x1 = t.a.x;
        x2 = t.b.x;
        x3 = t.c.x;

        y1 = t.a.y;
        y2 = t.b.y;
        y3 = t.c.y;

        double q1 = (x1 - x0) * (y2 - y1) - (x2 - x1) * (y1 - y0);
        double q2 = (x2 - x0) * (y3 - y2) - (x3 - x2) * (y2 - y0);
        double q3 = (x3 - x0) * (y1 - y3) - (x1 - x3) * (y3 - y0);

        return ((q1 >= 0 && q2 >= 0 && q3 >= 0) || (q1 <= 0 && q2 <= 0 && q3 <= 0));
    }

    public double getSquare() {
        double s = 0;

        for (int i = 0; i < result.size() - 1; i++) {
            s += (result.get(i).x + result.get(i + 1).y);
            s -= (result.get(i + 1).x + result.get(i).y);
        }

        return Math.abs(s);
    }
}
