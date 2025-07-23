package ca.utoronto.utm.assignment2.paint;

import javafx.event.EventType;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * This class's purpose is to create and draw a square Drawable instance
 */
public class Square extends Rectangle{

    /**
     * Initializes a Square Drawable
     * @param start the starting point of the square
     * @param dim the ending point of the square
     * @param color the color of the square's outline
     * @param fillColor the color of the square's body
     * @param thickness the thickness of the square's outline
     * @param model the PaintModel that is linked to the square
     */
    public Square(Point start, Point dim, Color color, Color fillColor, int thickness, PaintModel model){
        super(start, dim, color, fillColor, thickness, model);
    }

    /**
     * Uses the mouse events to calculate the side length of the square.
     * @param mouseEvent the user's mouse events on the canvas
     */
    @Override
    public void handle(MouseEvent mouseEvent) {
        EventType<MouseEvent> mouseEventType = (EventType<MouseEvent>) mouseEvent.getEventType();

        if (mouseEventType.equals(MouseEvent.MOUSE_PRESSED)) {
            System.out.println("Started Rectangle");

            this.setStart(mouseEvent.getX(), mouseEvent.getY());
            this.setDim(mouseEvent.getX(), mouseEvent.getY());
//                    System.out.println("Press point: (" + start.x + ", " + start.y + ")");
//            this.setStart(start.x, start.y);
//                    System.out.println("Press point: (" + start.x + ", " + start.y + ")");

        } else if (mouseEventType.equals(MouseEvent.MOUSE_DRAGGED)  || mouseEventType.equals(MouseEvent.MOUSE_RELEASED)) {
            double startX = this.startpt.x;
            double startY = this.startpt.y;

            double endX = mouseEvent.getX();
            double endY = mouseEvent.getY();

            // Calculate the size (width) of the square
            double width = Math.abs(endX - startX);

            // If dragging upwards or to the left, adjust the top-left corner
            double x = (endX < startX) ? startX - width : startX;
            double y = (endY < startY) ? startY - width : startY;

            // Update the square with the correct top-left corner and dimensions
            this.setStart(x, y);
            this.setDim(width, width);


        }
        if (mouseEventType.equals(MouseEvent.MOUSE_DRAGGED)) {
            this.model.setBeingDrawn(this);

            // Update the rectangle
            //this.update(null, null);
        } else if (mouseEventType.equals(MouseEvent.MOUSE_RELEASED)) {
            System.out.println("Created Rectangle");

            this.model.addDrawable(this);

        }
    }
}


