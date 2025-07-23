package ca.utoronto.utm.assignment2.paint;


import javafx.scene.canvas.GraphicsContext;

/**
 * This interface must be implemented for the drawable to be drawn in the canvas
 */
public interface DrawableDraw {
    public void draw(GraphicsContext g2d);
}
