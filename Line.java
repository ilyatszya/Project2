package fixed;


// Класс Line - описывает объект типа "Отрезок"
// Линию задаем по двум точкам
public class Line {
    public Point p1;
    public Point p2;

    // Создаем новую линию по двум точкам,
    // которые были переданы в параметрах
    public Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

}

