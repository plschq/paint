package paint.controls;


import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import paint.App;
import paint.Canvas;
import paint.dataclasses.LineGroup;
import paint.dataclasses.Mode;
import paint.dataclasses.XY;


public final class MouseListener {

    private static XY lastPMBPressed;
    private static XY lastPMBDragged;

    // private static XY lastSMBPressed;
    // private static XY lastSMBDragged;


    public static void init() {
        App.getStage().getScene().addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseEvent -> {
            if (!mouseEvent.getButton().equals(MouseButton.PRIMARY)) {return;}
            if (MouseListener.lastPMBDragged == null) {
                MouseListener.lastPMBDragged = new XY(mouseEvent.getSceneX(), mouseEvent.getSceneY());
            }
        });
        /* App.getStage().getScene().addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseEvent -> {
            if (!mouseEvent.getButton().equals(MouseButton.SECONDARY)) {return;}
            if (MouseListener.lastSMBDragged == null) {
                MouseListener.lastSMBDragged = new XY(mouseEvent.getSceneX(), mouseEvent.getSceneY());
            }
        }); */

        MouseListener.handlePMBPressed();
        MouseListener.handlePMBDragged();
        // MouseListener.handleSMBPressed();
        // MouseListener.handleSMBDragged();
        MouseListener.handleMouseReleased();
    }

    private static void handlePMBPressed() {
        App.getStage().getScene().addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            if (!mouseEvent.getButton().equals(MouseButton.PRIMARY)) {return;}

            XY cPos = new XY(mouseEvent.getSceneX(), mouseEvent.getSceneY());

            if (Mode.current.isBrush()) {
                LineGroup lineGroup = new LineGroup();

                Line line = new Line(cPos.x, cPos.y, cPos.x, cPos.y);

                line.setStrokeWidth(App.drawThickness);
                line.setStroke(App.strokeColor);
                line.setStrokeLineCap(StrokeLineCap.ROUND);

                lineGroup.add(line);

                Canvas.lineGroups.add(lineGroup);
                Canvas.actions.add(lineGroup);
            } else if (Mode.current.isEraser()) {
                LineGroup lineGroup = new LineGroup();

                Line line = new Line(cPos.x, cPos.y, cPos.x, cPos.y);

                line.setStrokeWidth(App.drawThickness);
                line.setStroke(App.backgroundColor);
                line.setStrokeLineCap(StrokeLineCap.ROUND);

                Canvas.erasers.add(line);
                Canvas.actions.add(line);

                lineGroup.add(line);

                Canvas.lineGroups.add(lineGroup);
                Canvas.actions.add(lineGroup);
            } else if (Mode.current.isLine()) {
                Canvas.lastLine = Canvas.addLine(cPos, cPos);
            } else if (Mode.current.isRectangle()) {
                Canvas.lastRectangle = Canvas.addRectangle(cPos);
            } else if (Mode.current.isSquare()) {
                Canvas.lastSquare = Canvas.addRectangle(cPos);
            } else if (Mode.current.isCircle()) {
                Canvas.lastCircle = Canvas.addCircle(cPos);
            } else if (Mode.current.isEllipse()) {
                Canvas.lastEllipse = Canvas.addEllipse(cPos);
            }

            MouseListener.lastPMBPressed = cPos;
        });
    }

    private static void handlePMBDragged() {
        App.getStage().getScene().addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseEvent -> {
            if (!mouseEvent.getButton().equals(MouseButton.PRIMARY)) {return;}

            XY cPos = new XY(mouseEvent.getSceneX(), mouseEvent.getSceneY());

            if (Mode.current.isBrush()) {
                Line line = new Line(
                        MouseListener.lastPMBDragged.x + App.drawOffset.x,
                        MouseListener.lastPMBDragged.y + App.drawOffset.y,
                        cPos.x + App.drawOffset.x,
                        cPos.y + App.drawOffset.y
                );
                Canvas.lineGroups.get(Canvas.lineGroups.toArray().length - 1).add(Canvas.addLine(line));
            } else if (Mode.current.isEraser()) {
                Line line = new Line(
                        MouseListener.lastPMBDragged.x + App.drawOffset.x,
                        MouseListener.lastPMBDragged.y + App.drawOffset.y,
                        cPos.x + App.drawOffset.x,
                        cPos.y + App.drawOffset.y
                );
                Canvas.lineGroups.get(Canvas.lineGroups.toArray().length - 1).add(Canvas.addEraser(line));
            } else if (Mode.current.isLine()) {
                Canvas.changeLine(Canvas.lastLine, cPos);
            } else if (Mode.current.isRectangle()) {
                Canvas.changeRectangle(Canvas.lastRectangle, MouseListener.lastPMBPressed, cPos);
            } else if (Mode.current.isSquare()) {
                Canvas.changeSquare(Canvas.lastSquare, MouseListener.lastPMBPressed, Canvas.pointDistance(MouseListener.lastPMBPressed, cPos) * 2);
            } else if (Mode.current.isCircle()) {
                Canvas.changeCircle(Canvas.lastCircle, Canvas.pointDistance(cPos, MouseListener.lastPMBPressed));
            } else if (Mode.current.isEllipse()) {
                double rX = Math.abs(cPos.x - MouseListener.lastPMBPressed.x);
                double rY = Math.abs(cPos.y - MouseListener.lastPMBPressed.y);
                Canvas.changeEllipse(Canvas.lastEllipse, rX, rY);
            }

            MouseListener.lastPMBDragged = cPos;
        });
    }

    /* private static void handleSMBPressed() {
        App.getStage().getScene().addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            if (!mouseEvent.getButton().equals(MouseButton.SECONDARY)) {return;}

            MouseListener.lastSMBPressed = new XY(mouseEvent.getSceneX(), mouseEvent.getSceneY());
        });
    }

    private static void handleSMBDragged() {
        App.getStage().getScene().addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseEvent -> {
            if (!mouseEvent.getButton().equals(MouseButton.SECONDARY)) {return;}

            XY cPos = new XY(mouseEvent.getSceneX(), mouseEvent.getSceneY());

            App.drawOffset.x += MouseListener.lastSMBDragged.x - cPos.x;
            App.drawOffset.y += MouseListener.lastSMBDragged.y - cPos.y;

            App.getStage().getScene().getCamera().setTranslateX(App.drawOffset.x);
            App.getStage().getScene().getCamera().setTranslateY(App.drawOffset.y);

            Interface.getBox().setTranslateX(App.drawOffset.x + 10);
            Interface.getBox().setTranslateY(App.drawOffset.y + 10);

            MouseListener.lastSMBDragged = cPos;
        });
    } */

    private static void handleMouseReleased() {
        App.getStage().getScene().addEventHandler(MouseEvent.MOUSE_RELEASED, mouseEvent -> {
            MouseListener.lastPMBPressed = null;
            MouseListener.lastPMBDragged = null;
            // MouseListener.lastSMBPressed = null;
            // MouseListener.lastSMBDragged = null;
            Canvas.lastLine = null;
            Canvas.lastRectangle = null;
            Canvas.lastSquare = null;
            Canvas.lastCircle = null;
            Canvas.lastEllipse = null;
        });
    }

}
