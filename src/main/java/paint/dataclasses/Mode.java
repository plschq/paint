package paint.dataclasses;


public class Mode {

    private final int id;

    public static final Mode BRUSH = new Mode(0);
    public static final Mode ERASER = new Mode(1);
    public static final Mode LINE = new Mode(2);
    public static final Mode RECTANGLE = new Mode(3);
    public static final Mode SQUARE = new Mode(4);
    public static final Mode CIRCLE = new Mode(5);
    public static final Mode ELLIPSE = new Mode(6);

    public static Mode current = Mode.BRUSH;

    public static final Mode[] ALL = new Mode[] {
            Mode.BRUSH,
            Mode.ERASER,
            Mode.LINE,
            Mode.RECTANGLE,
            Mode.SQUARE,
            Mode.CIRCLE,
            Mode.ELLIPSE
    };


    private Mode(int id) {this.id = id;}


    public boolean isBrush() {return this.id == 0;}
    public boolean isEraser() {return this.id == 1;}
    public boolean isLine() {return this.id == 2;}
    public boolean isRectangle() {return this.id == 3;}
    public boolean isSquare() {return this.id == 4;}
    public boolean isCircle() {return this.id == 5;}
    public boolean isEllipse() {return this.id == 6;}

    public String getString() {return Mode.getString(this);}
    public static String getString(Mode mode) {
        if (mode.isBrush()) {return "Brush";}
        else if (mode.isEraser()) {return "Eraser";}
        else if (mode.isLine()) {return "Line";}
        else if (mode.isRectangle()) {return "Rectangle";}
        else if (mode.isSquare()) {return "Square";}
        else if (mode.isCircle()) {return "Circle";}
        else if (mode.isEllipse()) {return "Ellipse";}
        return "";
    }

}
