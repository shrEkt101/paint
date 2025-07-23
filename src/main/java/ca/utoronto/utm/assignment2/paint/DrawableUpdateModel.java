package ca.utoronto.utm.assignment2.paint;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * This interface must be implemented for every drawable to handle mouse events.
 */
public interface DrawableUpdateModel extends EventHandler<MouseEvent> {

    // this class simply breaks off all the mouseEvents originally handled in PaintController.
    @Override
    public void handle(MouseEvent mouseEvent);
    // must handle MouseEvent.MOUSE_PRESSED, MouseEvent.MOUSE_DRAGGED, MouseEvent.MOUSE_RELEASED
}
