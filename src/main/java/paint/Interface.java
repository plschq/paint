package paint;


import javafx.collections.FXCollections;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.scene.text.Font;
import paint.dataclasses.Mode;

import java.util.Arrays;


public final class Interface {

    public static boolean isHidden = false;

    private static final ColorPicker strokeColorSelector = new ColorPicker();
    private static final ColorPicker fillColorSelector = new ColorPicker();
    private static final ColorPicker backgroundColorSelector = new ColorPicker();
    private static final ComboBox<String> drawThicknessSelector = new ComboBox<>(
            FXCollections.observableArrayList(
                    "1",
                    "3",
                    "5",
                    "10",
                    "15",
                    "20",
                    "30",
                    "40",
                    "50"
            )
    );
    private static final ComboBox<String> drawModeSelector = new ComboBox<>();
    private static final Button undoButton = new Button("Undo (Ctrl+Z)");
    private static final Button fullscreenButton = new Button("Fullscreen (F11)");
    private static final Button clearButton = new Button("Clear");
    private static final Button exitButton = new Button("Exit");
    private static final Button hideButton = new Button("Hide menu (Shift)");
    private static final VBox box = new VBox(
            new HBox(
                    undoButton,
                    fullscreenButton,
                    clearButton,
                    exitButton,
                    hideButton
            ),
            new HBox(
                    new Label("Stroke color:"),
                    strokeColorSelector
            ),
            new HBox(
                    new Label("Fill color:"),
                    fillColorSelector
            ),
            new HBox(
                    new Label("Background color:"),
                    backgroundColorSelector
            ),
            new HBox(
                    new Label("Thickness:"),
                    drawThicknessSelector
            ),
            new HBox(
                    new Label("Draw mode:"),
                    drawModeSelector
            )
    );


    public static void init() {
        String[] modesStr = new String[Mode.ALL.length];
        for (int i = 0; i < Mode.ALL.length; i++) {modesStr[i] = Mode.ALL[i].getString();}
        Interface.drawModeSelector.setItems(FXCollections.observableList(Arrays.stream(modesStr).toList()));

        Interface.box.setSpacing(5);
        Interface.box.setTranslateX(10);
        Interface.box.setTranslateY(10);
        Interface.box.getChildren().forEach(node -> {
            if (node instanceof HBox) {
                ((HBox) node).setSpacing(5);
                ((HBox) node).setAlignment(Pos.CENTER_LEFT);
                ((HBox) node).getChildren().forEach(child -> {
                    if (child instanceof Label) {
                        ((Label) child).setFont(Font.font(20));
                    } else {
                        child.setCursor(Cursor.HAND);
                    }
                });
            }
        });

        Interface.strokeColorSelector.setValue(App.strokeColor);
        Interface.strokeColorSelector.setOnAction(event -> App.strokeColor = Interface.strokeColorSelector.getValue());

        Interface.fillColorSelector.setValue(App.fillColor);
        Interface.fillColorSelector.setOnAction(event -> App.fillColor = Interface.fillColorSelector.getValue());

        Interface.backgroundColorSelector.setValue(App.backgroundColor);
        Interface.backgroundColorSelector.setOnAction(event -> {
            App.backgroundColor = Interface.backgroundColorSelector.getValue();
            App.getStage().getScene().setFill(App.backgroundColor);
            Canvas.erasers.forEach(eraser -> eraser.setStroke(App.backgroundColor));
        });

        Interface.drawThicknessSelector.setValue(String.valueOf(App.drawThickness));
        Interface.drawThicknessSelector.setOnAction(event -> App.drawThickness = Integer.parseInt(Interface.drawThicknessSelector.getValue()));

        Interface.drawModeSelector.setValue("Brush");
        Interface.drawModeSelector.setOnAction(event -> {
            if (Interface.drawModeSelector.getValue().equals("Brush")) {Mode.current = Mode.BRUSH;}
            else if (Interface.drawModeSelector.getValue().equals("Eraser")) {Mode.current = Mode.ERASER;}
            else if (Interface.drawModeSelector.getValue().equals("Line")) {Mode.current = Mode.LINE;}
            else if (Interface.drawModeSelector.getValue().equals("Rectangle")) {Mode.current = Mode.RECTANGLE;}
            else if (Interface.drawModeSelector.getValue().equals("Square")) {Mode.current = Mode.SQUARE;}
            else if (Interface.drawModeSelector.getValue().equals("Circle")) {Mode.current = Mode.CIRCLE;}
            else if (Interface.drawModeSelector.getValue().equals("Ellipse")) {Mode.current = Mode.ELLIPSE;}
        });

        Interface.undoButton.setOnAction(event -> Canvas.undo());
        Interface.fullscreenButton.setOnAction(event -> App.getStage().setFullScreen(!App.getStage().fullScreenProperty().get()));
        Interface.clearButton.setOnAction(event -> Canvas.clear());
        Interface.exitButton.setOnAction(event -> App.getStage().close());
        Interface.hideButton.setOnAction(event -> Interface.hide());

        Interface.show();
    }

    public static void hide() {
        new AnchorPane().getChildren().add(Interface.box);
        Interface.isHidden = true;
    }

    public static void show() {
        App.getRoot().getChildren().add(Interface.box);
        Interface.isHidden = false;
    }

}
