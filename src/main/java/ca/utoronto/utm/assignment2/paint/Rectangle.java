package ca.utoronto.utm.assignment2.paint;

import javafx.event.EventType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * This class's purpose is to create and draw a rectangle Drawable instance
 */
public class Rectangle extends Drawable{
    protected Point start;
    protected Point dim;

    protected final Point startpt;
    protected GraphicsContext g2d;
    private Color color;
    private Color fillColor;
    private int thickness;

    /**
     * Initializes a Rectangle Drawable
     * @param start the starting point of the rectangle
     * @param dim the ending point of the rectangle
     * @param color the color of the rectangle's outline
     * @param fillColor the color of the rectangle's body
     * @param thickness the thickness of the rectangle's outline
     * @param model the PaintModel that is linked to the rectangle
     */
    public Rectangle(Point start, Point dim, Color color, Color fillColor, int thickness, PaintModel model) {
        super(model);
        this.start = start;
        this.dim = dim;
        this.startpt = start;
        this.color = color;
        this.fillColor = fillColor;
        this.thickness = thickness;
    }

    private double getX() {
        return start.x;
    }
    private double getY() {
        return start.y;
    }

    private double getWidth() { return dim.x; }
    private double getHeight() {
        return dim.y;
    }

    /**
     * Sets the starting point of the rectangle
     * @param x the x-value of the starting point
     * @param y the y-value of the starting point
     */
    public void setStart(double x, double y) { this.start = new Point(x, y);}

    /**
     * Sets the ending point of the rectangle
     * @param x the x-value of the ending point
     * @param y the y-value of the ending point
     */
    public void setDim(double x, double y) { this.dim = new Point(x, y);}

    /**
     * Uses the rectangle's width and height to draw the rectangle
     * in accordance to its outline color, fill color and thickness
     * @param g2d the GraphicsContext that displays the oval
     */
    @Override
    public void draw(GraphicsContext g2d) {
        g2d.setFill(this.fillColor);
        double x = this.getX();
        double y = this.getY();
        double width = this.getWidth();
        double height = this.getHeight();

        g2d.fillRect(x, y, width, height);

        g2d.setStroke(this.color);
        g2d.setLineWidth(this.thickness);
        g2d.strokeRect(x, y, width, height);

    }

    /**
     * Uses the mouse events to calculate the height and width of the rectangle.
     * @param mouseEvent the user's mouse events on the canvas
     */
    @Override
    public void handle(MouseEvent mouseEvent) {
        EventType<MouseEvent> mouseEventType = (EventType<MouseEvent>) mouseEvent.getEventType();

        if (mouseEventType.equals(MouseEvent.MOUSE_PRESSED)) {
            System.out.println("Started Rectangle");

            this.setStart(mouseEvent.getX(), mouseEvent.getY());
            this.setDim(mouseEvent.getX(), mouseEvent.getY());

        } else if (mouseEventType.equals(MouseEvent.MOUSE_DRAGGED)  || mouseEventType.equals(MouseEvent.MOUSE_RELEASED)) {
            double startX = this.startpt.x;
            double startY = this.startpt.y;

            double endX = mouseEvent.getX();
            double endY = mouseEvent.getY();

            // Calculate top-left corner
            double x = Math.min(startX, endX);
            double y = Math.min(startY, endY);

            // Calculate width and height
            double width = Math.abs(endX - startX);

            double height = Math.abs(endY - startY);
            // Update the rectangle
            this.setStart(x, y);

            this.setDim(width, height);


        }
        if (mouseEventType.equals(MouseEvent.MOUSE_DRAGGED)) {
            this.model.setBeingDrawn(this);

        } else if (mouseEventType.equals(MouseEvent.MOUSE_RELEASED)) {
            System.out.println("Created Rectangle");

            this.model.addDrawable(this);

        }
    }

}
