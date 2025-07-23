package ca.utoronto.utm.assignment2.paint;

import javafx.event.EventType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * This class's purpose is to create and draw an Oval Drawable instance
 * by calculating its height and width.
 */

public class Oval extends Drawable{

    private Point startPoint;
    private Point endPoint;
    private final Point ogStart;
    private Color color;
    private Color fillColor;
    private int thickness;

    /**
     * Initializes a Circle Drawable
     * @param startPoint the starting point of the oval
     * @param endPoint the ending point of the oval
     * @param color the color of the oval's outline
     * @param fillColor the color of the oval's body
     * @param thickness the thickness of the oval's outline
     * @param model the PaintModel that is linked to the oval
     */
    public Oval(Point startPoint, Point endPoint, Color color, Color fillColor, int thickness, PaintModel model){
        super(model);
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.ogStart = startPoint;
        this.color = color;
        this.fillColor = fillColor;
        this.thickness = thickness;
    }

    private void setStartPoint(Point startPoint) {this.startPoint = startPoint;}

    private void setEndPoint(Point endPoint) {this.endPoint = endPoint;}

    private double getWidth() {return endPoint.x;}

    private double getHeight() {return endPoint.y;}

    /**
     * Uses the oval's width and height to draw the oval
     * in accordance to its outline color, fill color and thickness
     * @param g2d the GraphicsContext that displays the oval
     */
    @Override
    public void draw(GraphicsContext g2d) {
        g2d.setFill(this.fillColor);
        double x = startPoint.x;
        double y = startPoint.y;
        double width = this.getWidth();
        double height = this.getHeight();

        g2d.fillOval(x, y, width, height);
        g2d.setFill(this.color);
        g2d.setLineWidth(this.thickness);
        g2d.strokeOval(x, y, width, height);
    }

    /**
     * Uses the mouse events to calculate the height and width of the oval.
     * @param mouseEvent the user's mouse events on the canvas
     */
    @Override
    public void handle(MouseEvent mouseEvent) {

        EventType<MouseEvent> mouseEventType = (EventType<MouseEvent>) mouseEvent.getEventType();

        if (mouseEventType.equals(MouseEvent.MOUSE_PRESSED)) {
            System.out.println("Started Oval");
            this.setStartPoint(new Point(mouseEvent.getX(), mouseEvent.getY()));
            this.setEndPoint(new Point(mouseEvent.getX(), mouseEvent.getY()));
        }

        else if (mouseEventType.equals(MouseEvent.MOUSE_DRAGGED) || mouseEventType.equals(MouseEvent.MOUSE_RELEASED)) {
            double ogX = this.ogStart.x;
            double ogY = this.ogStart.y;

            double currX = mouseEvent.getX();
            double currY = mouseEvent.getY();

            double width = Math.abs(ogX - currX);
            double height = Math.abs(ogY - currY);

            double correctX = Math.min(ogX, currX);
            double correctY = Math.min(ogY, currY);

            this.setStartPoint(new Point(correctX, correctY));
            this.setEndPoint(new Point(width, height));
        }
        if (mouseEventType.equals(MouseEvent.MOUSE_DRAGGED)) {this.model.setBeingDrawn(this);}

        else if (mouseEventType.equals(MouseEvent.MOUSE_RELEASED)) {
            System.out.println("Created Oval");

            this.model.addDrawable(this);

        }

    }

}
