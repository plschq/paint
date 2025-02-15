package paint;


import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import paint.controls.KeyboardListener;
import paint.controls.MouseListener;
import paint.dataclasses.XY;


public final class App extends Application {

    private static Stage stage;
    private static Group root;

    public static Color strokeColor = Color.web("#000000ff");
    public static Color fillColor = Color.web("#ffffff00");
    public static Color backgroundColor = Color.web("#ffffffff");

    public static int drawThickness = 5;

    public static XY drawOffset = new XY(0, 0);


    public static Stage getStage() {
        return App.stage;
    }

    public static Group getRoot() {
        return App.root;
    }

    private static Group createBasicRoot() {
        App.root = new Group(); return App.root;
    }

    private static Scene createBasicScene() {
        return new Scene(App.createBasicRoot(), 600, 400, true, SceneAntialiasing.BALANCED);
    }

    private static void setup(Stage stage) {
        App.stage = stage;
        App.stage.setTitle("JavaFX paint [v1.0.1]");
        App.stage.setScene(App.createBasicScene());
        App.stage.getScene().setFill(App.backgroundColor);
        App.stage.getScene().setCamera(new ParallelCamera());
        App.stage.setFullScreen(false);
        App.stage.setAlwaysOnTop(false);
        App.stage.setResizable(true);
        App.stage.setMinWidth(500);
        App.stage.setMinHeight(400);
        App.stage.getScene().setCursor(Cursor.CROSSHAIR);

        Canvas.init();
        Interface.init();
        MouseListener.init();
        KeyboardListener.init();

        App.stage.show();
    }

    @Override
    public void start(Stage stage) {
        App.setup(stage);
    }

    @Override
    public void stop() {
        App.stage.close();
    }

    public static void main(String[] args) {
        App.launch(args);
    }

}
