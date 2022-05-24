package fixed;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MyFrame extends JFrame implements ActionListener {

    JButton t_BUTTON;
    JButton a_BUTTON;
    JButton solve_BUTTON;
    JButton clear_BUTTON;
    JButton save_BUTTON;
    JButton rTriangle_BUTTON;
    JButton rAngle_BUTTON;

    JTextField t_FIELD;
    JTextField a_FIELD;

    public boolean flag = false;
    public double maxSquare = -10;
    public Shape mainShape;

    public static List<Shape> shapes = new ArrayList<>();
    public static List<Point> points = new ArrayList<>();
    public static List<Triangle> triangles = new ArrayList<>();
    public static List<Angle> angles = new ArrayList<>();

    public MyFrame(String title) {
        super(title);
        initializationForButtonAndFields();
        addButtonsAndFields();
        initialization();
    }

    private void initializationForButtonAndFields() {
        save_BUTTON = new JButton("save to new File");
        save_BUTTON.setBounds(10, 300, 150, 50);
        save_BUTTON.addActionListener(this);
        save_BUTTON.setBackground(Color.ORANGE);

        solve_BUTTON = new JButton("solve");
        solve_BUTTON.setBounds(10, 500, 150, 50);
        solve_BUTTON.addActionListener(this);
        solve_BUTTON.setBackground(Color.CYAN);

        clear_BUTTON = new JButton("clear Desk");
        clear_BUTTON.setBounds(10, 400, 150, 50);
        clear_BUTTON.addActionListener(this);
        clear_BUTTON.setBackground(Color.PINK);

        t_BUTTON = new JButton("add triangle");
        t_BUTTON.setBounds(10, 10, 150, 50);
        t_BUTTON.addActionListener(this);
        t_BUTTON.setBackground(Color.GREEN);

        t_FIELD = new JTextField("// 1000, 1000, 1200, 200, 800, 600\n");
        t_FIELD.setBounds(200, 10, 250, 50);

        a_BUTTON = new JButton("add angle");
        a_BUTTON.setBounds(10, 100, 150, 50);
        a_BUTTON.addActionListener(this);
        a_BUTTON.setBackground(Color.YELLOW);

        rTriangle_BUTTON = new JButton("RTriangle");
        rTriangle_BUTTON.setBounds(10, 200, 150, 50);
        rTriangle_BUTTON.addActionListener(this);
        rTriangle_BUTTON.setBackground(Color.LIGHT_GRAY);

        rAngle_BUTTON = new JButton("RAngle");
        rAngle_BUTTON .setBounds(200, 200, 150, 50);
        rAngle_BUTTON .addActionListener(this);
        rAngle_BUTTON .setBackground(Color.magenta);

        a_FIELD = new JTextField("// 1400, 400, 20, 20, 20, 600");
        a_FIELD.setBounds(200, 100, 250, 50);
    }

    private void initialization() {
        setLayout(null);
        setSize(1400, 900);
        getContentPane().setBackground(Color.LIGHT_GRAY);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void addButtonsAndFields() {
        add(t_FIELD);
        add(a_FIELD);
        add(solve_BUTTON);
        add(a_BUTTON);
        add(save_BUTTON);
        add(clear_BUTTON);
        add(t_BUTTON);
        add(rAngle_BUTTON);
        add(rTriangle_BUTTON);
    }

    private static void drawAngle(Graphics g, Angle angle) {
        g.setColor(Color.green);
        g.drawLine(angle.top.x, angle.top.y, angle.v1.x, angle.v1.y); // рисуем линию между вершиной угла и v1
        g.drawLine(angle.top.x, angle.top.y, angle.v2.x, angle.v2.y); // рисуем линию между вершиной угла и v2
    }

    private static void drawTriangle(Graphics g, Triangle triangle) {
        g.setColor(Color.red);
        g.drawLine(triangle.a.x, triangle.a.y, triangle.b.x, triangle.b.y); // рисуем линию между A и В
        g.drawLine(triangle.a.x, triangle.a.y, triangle.c.x, triangle.c.y); // рисуем линию между A и С
        g.drawLine(triangle.b.x, triangle.b.y, triangle.c.x, triangle.c.y); // рисуем линию между В и С
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (Point point : points) {
            g.drawOval(point.x, point.y, 5, 5);
        }

        for (Angle angle : angles) {
            drawAngle(g, angle);
        }

        for (Triangle triangle : triangles) {
            drawTriangle(g, triangle);
        }

        for (Triangle triangle : triangles) {
            for (Angle angle : angles) {
                Shape shape = new Shape(triangle, angle);
                shapes.add(shape);
                if (shape.getSquare() >= maxSquare) {
                    maxSquare = shape.getSquare();
                    mainShape = shape;
                }
            }
        }

        if (flag) {
            Graphics2D g1 = (Graphics2D) g;
            BasicStroke pen1 = new BasicStroke(5);
            g1.setStroke(pen1);
            g1.setColor(Color.magenta);

            for (Point allPoint : mainShape.result) {
                g.drawOval((int) allPoint.x - 3, (int) allPoint.y - 3, 10, 10);
                g.fillOval((int) allPoint.x - 3, (int) allPoint.y - 3, 10, 10);
            }

            for (int i = 1; i < mainShape.result.size(); i++) {
                Point p1 = mainShape.result.get(i - 1);
                Point p2 = mainShape.result.get(i);
                drawTriangle(g, new Triangle(mainShape.result.get(0), p1, p2));
            }
            for (int i = 1; i < mainShape.result.size(); i++) {
                Point p1 = mainShape.result.get(i - 1);
                Point p2 = mainShape.result.get(i);
                drawTriangle(g, new Triangle(mainShape.result.get(mainShape.result.size() - 1), p1, p2));
            }

        }
    }

            @Override
    public void actionPerformed (ActionEvent e) {
        Pattern pat = Pattern.compile("[-]?[0-9]+(.[0-9]+)?");
        Matcher matcher;

        if (e.getSource() == t_BUTTON) {
            matcher = pat.matcher(t_FIELD.getText());

            int[] coordinates = new int[6];
            int i = 0;

            while (matcher.find()) {
                coordinates[i] = Integer.parseInt(matcher.group());
                i++;
            }

            triangles.add(new Triangle(
                    new Point(coordinates[0], coordinates[1]),
                    new Point(coordinates[2], coordinates[3]),
                    new Point(coordinates[4], coordinates[5])
            ));

            points.add(new Point(coordinates[0], coordinates[1]));
            points.add(new Point(coordinates[2], coordinates[3]));
            points.add(new Point(coordinates[4], coordinates[5]));

            repaint();
        }

        if (e.getSource() == a_BUTTON) {
            matcher = pat.matcher(a_FIELD.getText());

            int[] coordinates = new int[6];
            int i = 0;

            while (matcher.find()) {
                coordinates[i] = Integer.parseInt(matcher.group());
                i++;
            }

            angles.add(new Angle(
                    new Point(coordinates[0], coordinates[1]),
                    new Point(coordinates[2], coordinates[3]),
                    new Point(coordinates[4], coordinates[5])
            ));

            points.add(new Point(coordinates[0], coordinates[1]));
            points.add(new Point(coordinates[2], coordinates[3]));
            points.add(new Point(coordinates[4], coordinates[5]));

            repaint();
        }

        if (e.getSource() == clear_BUTTON) {
            points.clear();
            shapes.clear();
            mainShape = new Shape();
            triangles.clear();
            angles.clear();
        }

        if (e.getSource() == solve_BUTTON) {
            flag = true;
        }

        if (e.getSource() == save_BUTTON) {
            File file = new File("file.txt");
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                bw.write(mainShape.triangle + "\n" + mainShape.angle + "\n" + "Square: " + mainShape.getSquare());
                bw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == rTriangle_BUTTON) {
            Point a = new Point((int)(Math.random()* 900), (int)(Math.random()* 900));
            Point b = new Point((int)(Math.random()* 900), (int)(Math.random()* 900));
            Point c = new Point((int)(Math.random()* 900), (int)(Math.random()* 900));
            points.add(a);
            points.add(b);
            points.add(c);
            triangles.add(new Triangle(a,b,c));

        }

        if (e.getSource() == rAngle_BUTTON) {
            Point a = new Point((int)(Math.random()* 900), (int)(Math.random()* 900));
            Point b = new Point((int)(Math.random()* 900), (int)(Math.random()* 900));
            Point c =new Point((int)(Math.random()* 900), (int)(Math.random()* 900));
            points.add(a);
            points.add(b);
            points.add(c);
            angles.add(new Angle(a,b,c));

        }

        repaint();
    }
}