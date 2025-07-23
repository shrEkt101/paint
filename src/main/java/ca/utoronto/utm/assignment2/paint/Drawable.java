package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import java.io.Serializable;
import java.util.Observer;

/**
 * Abstract class for all drawable objects. A drawable object everything that
 * can be drawn onto the canvas.
 */
public abstract class Drawable implements DrawableDraw, DrawableUpdateModel, Serializable {
    protected PaintModel model;

    /**
     * Constructor for a generic drawable.
     * @param model canvas to be saved to.
     */
    public Drawable(PaintModel model){this.model = model;}

    /**
     * Getter method for model.
     * @return the model.
     */
    public PaintModel getModel(){return this.model;}

    /**
     * This method actually draws the drawable.
     * @param g2d the graphics context (actual canvas on which drawable is being
     *           drawn).
     */
    public void draw(GraphicsContext g2d){}

    /**
     * This method handles the mouse events to update the model.
     * @param mouseEvent mouse event to be handled.s
     */
    @Override
    public void handle(MouseEvent mouseEvent) {

    }
}
