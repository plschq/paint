package paint;


import javafx.scene.Group;
import javafx.scene.shape.*;

import paint.dataclasses.LineGroup;
import paint.dataclasses.XY;

import java.util.ArrayList;


public final class Canvas {

    private static final Group canvas = new Group();

    public static Line lastLine;
    public static Rectangle lastRectangle;
    public static Rectangle lastSquare;
    public static Circle lastCircle;
    public static Ellipse lastEllipse;

    public static ArrayList<Line> erasers = new ArrayList<>();
    public static ArrayList<LineGroup> lineGroups = new ArrayList<>();
    public static ArrayList<Object> actions = new ArrayList<>();


    public static void init() {
        App.getRoot().getChildren().add(Canvas.canvas);
    }


    public static Group getCanvas() {
        return canvas;
    }

    public static double pointDistance(XY point1, XY point2) {
        return Math.sqrt(Math.pow(point1.x - point2.x, 2) + Math.pow(point1.y - point2.y, 2));
    }

    public static void undo() {
        if (!(Canvas.actions.toArray().length == 0)) {
            int lastIndex = Canvas.actions.toArray().length - 1;
            try {
                ((LineGroup) Canvas.actions.get(lastIndex)).clear();
            } catch (Exception e) {
                Canvas.canvas.getChildren().remove(Canvas.actions.get(lastIndex));
            } Canvas.actions.remove(lastIndex);
        }
    }

    public static void clear() {
        Canvas.canvas.getChildren().clear();
        Canvas.erasers.clear();
        Canvas.actions.clear();
    }

    public static Line addLine(XY point1, XY point2) {
        Line line = new Line(
                point1.x + App.drawOffset.x,
                point1.y + App.drawOffset.y,
                point2.x + App.drawOffset.x,
                point2.y + App.drawOffset.y
        );

        line.setStrokeWidth(App.drawThickness);
        line.setStroke(App.strokeColor);
        line.setStrokeLineCap(StrokeLineCap.ROUND);

        Canvas.canvas.getChildren().add(line);
        Canvas.actions.add(line);
        return line;
    }

    public static Line addLine(Line line) {
        line.setStrokeWidth(App.drawThickness);
        line.setStroke(App.strokeColor);
        line.setStrokeLineCap(StrokeLineCap.ROUND);

        Canvas.canvas.getChildren().add(line);
        return line;
    }

    public static Line addEraser(XY point1, XY point2) {
        Line line = new Line(
                point1.x + App.drawOffset.x,
                point1.y + App.drawOffset.y,
                point2.x + App.drawOffset.x,
                point2.y + App.drawOffset.y
        );

        line.setStrokeWidth(App.drawThickness);
        line.setStroke(App.backgroundColor);
        line.setStrokeLineCap(StrokeLineCap.ROUND);

        Canvas.canvas.getChildren().add(line);
        Canvas.erasers.add(line);
        Canvas.actions.add(line);
        return line;
    }

    public static Line addEraser(Line line) {
        line.setStrokeWidth(App.drawThickness);
        line.setStroke(App.backgroundColor);
        line.setStrokeLineCap(StrokeLineCap.ROUND);

        Canvas.canvas.getChildren().add(line);
        Canvas.erasers.add(line);
        return line;
    }

    public static Rectangle addRectangle(XY pos) {
        Rectangle rectangle = new Rectangle(pos.x + App.drawOffset.x, pos.y + App.drawOffset.y, 0, 0);

        rectangle.setStrokeWidth(App.drawThickness);
        rectangle.setStroke(App.strokeColor);
        rectangle.setStrokeLineCap(StrokeLineCap.ROUND);
        rectangle.setFill(App.fillColor);

        Canvas.canvas.getChildren().add(rectangle);
        Canvas.actions.add(rectangle);
        return rectangle;
    }

    public static Circle addCircle(XY pos) {
        Circle circle = new Circle(pos.x + App.drawOffset.x, pos.y + App.drawOffset.y, 0);

        circle.setStrokeWidth(App.drawThickness);
        circle.setStroke(App.strokeColor);
        circle.setStrokeLineCap(StrokeLineCap.ROUND);
        circle.setFill(App.fillColor);

        Canvas.canvas.getChildren().add(circle);
        Canvas.actions.add(circle);
        return circle;
    }

    public static Ellipse addEllipse(XY pos) {
        Ellipse ellipse = new Ellipse(pos.x + App.drawOffset.x, pos.y + App.drawOffset.y, 0, 0);

        ellipse.setStrokeWidth(App.drawThickness);
        ellipse.setStroke(App.strokeColor);
        ellipse.setStrokeLineCap(StrokeLineCap.ROUND);
        ellipse.setFill(App.fillColor);

        Canvas.canvas.getChildren().add(ellipse);
        Canvas.actions.add(ellipse);
        return ellipse;
    }

    public static void changeLine(Line line, XY point2) {
        line.setEndX(point2.x + App.drawOffset.x);
        line.setEndY(point2.y + App.drawOffset.y);
    }

    public static void changeRectangle(Rectangle rectangle, XY point1, XY point2) {
        double x, y, w, h;

        if (point1.x < point2.x && point1.y < point2.y) {
            x = point1.x; y = point1.y; w = point2.x - point1.x; h = point2.y - point1.y;
        } else if (point1.x > point2.x && point1.y < point2.y) {
            x = point2.x; y = point1.y; w = point1.x - point2.x; h = point2.y - point1.y;
        } else if (point1.x < point2.x && point1.y > point2.y) {
            x = point1.x; y = point2.y; w = point2.x - point1.x; h = point1.y - point2.y;
        } else {
            x = point2.x; y = point2.y; w = point1.x - point2.x; h = point1.y - point2.y;
        } rectangle.setX(x + App.drawOffset.x); rectangle.setY(y + App.drawOffset.y);
        rectangle.setWidth(w); rectangle.setHeight(h);
    }

    public static void changeSquare(Rectangle rectangle, XY point1, double size) {
        rectangle.setX(point1.x + App.drawOffset.x - size * 0.5);
        rectangle.setY(point1.y + App.drawOffset.y - size * 0.5);
        rectangle.setWidth(size);
        rectangle.setHeight(size);
    }

    public static void changeCircle(Circle circle, double radius) {
        circle.setRadius(radius);
    }

    public static void changeEllipse(Ellipse ellipse, double rX, double rY) {
        ellipse.setRadiusX(rX);
        ellipse.setRadiusY(rY);
    }

}
