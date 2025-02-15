package paint.controls;


import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

import paint.App;
import paint.Canvas;
import paint.Interface;


public final class KeyboardListener {

    public static void init() {
        App.getStage().getScene().addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().equals(KeyCode.F11)) {
                App.getStage().setFullScreen(!App.getStage().fullScreenProperty().get());
            } else if (event.getCode().equals(KeyCode.SHIFT)) {
                if (Interface.isHidden) {
                    Interface.show();
                } else {
                    Interface.hide();
                }
            } else if (KeyCombination.keyCombination("Ctrl+Z").match(event)) {
                Canvas.undo();
            }
        });
    }

}
