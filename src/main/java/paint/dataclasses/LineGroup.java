package paint.dataclasses;


import javafx.scene.shape.Line;
import paint.Canvas;

import java.util.ArrayList;


public class LineGroup {

    private ArrayList<Line> group;


    public LineGroup() {
        this.group = new ArrayList<>();
    }


    public void add(Line line) {
        this.group.add(line);
    }

    public void clear() {
        for (Line line : this.group) {
            Canvas.getCanvas().getChildren().remove(line);
        }
    }

}
