module ca {
    requires javafx.controls;
    requires javafx.fxml;

    opens ca.paint to javafx.fxml;
    opens ca.scribble to javafx.fxml;

    exports ca.paint;
}
