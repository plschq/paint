module paint.paintfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens paint to javafx.fxml;
    exports paint;
}
