package ca.utoronto.utm.assignment2.paint;

import javafx.event.EventType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * This class's purpose is to create and draw a Triangle Drawable instance
 * by calculating its dimensions.
 */

public class Triangle extends Drawable{

    private Point L;
    private Point T;
    private Point R;
    private Color color;
    private Color fillColor;
    private int thickness;

    private Point start;

    /**
     * Initializes a Triangle Drawable
     * @param Top the top point of the triangle
     * @param Left the left point of the triangle
     * @param Right the right point of the triangle
     * @param color the color of the triangle's outline
     * @param fillColor the color of the triangle's body
     * @param thickness the thickness of the triangle's outline
     * @param model the PaintModel that is linked to the triangle
     */
    public Triangle(Point Top, Point Left, Point Right, Color color, Color fillColor, int thickness, PaintModel model) {
        super(model);
        this.T = Top;
        this.L = Left;
        this.R = Right;
        this.color = color;
        this.fillColor = fillColor;
        this.thickness = thickness;
    }

    private Point getL() {
        return L;
    }
    private Point getT() {
        return T;
    }
    private Point getR() {
        return R;
    }

    private void setL(Point l) {
        this.L = l;
    }
    private void setT(Point t) {
        this.T = t;
    }
    private void setR(Point r) {
        this.R = r;
    }

    /**
     * Uses the triangle's dimensions to draw the triangle
     * in accordance to its outline color, fill color and thickness
     * @param g2d the GraphicsContext that displays the triangle
     */
    @Override
    public void draw(GraphicsContext g2d) {
        Point top = this.getT();
        Point bottom_left = this.getL();
        Point bottom_right = this.getR();

        g2d.setFill(this.fillColor);

        // Pass in the 3 vertices
        g2d.fillPolygon(
                new double[]{top.x, bottom_left.x, bottom_right.x},
                new double[]{top.y, bottom_left.y, bottom_right.y},
                3 // Number of vertices
        );
        g2d.setStroke(this.color);
        g2d.setLineWidth(this.thickness);
        g2d.strokePolygon(
                new double[]{top.x, bottom_left.x, bottom_right.x},
                new double[]{top.y, bottom_left.y, bottom_right.y},
                3 // Number of vertices
        );
    }

    /**
     * Uses the mouse events to calculate the dimensions of the triangle.
     * @param mouseEvent the user's mouse events on the canvas
     */
    @Override
    public void handle(MouseEvent mouseEvent) {
        EventType<MouseEvent> mouseEventType = (EventType<MouseEvent>) mouseEvent.getEventType();

        if (mouseEventType.equals(MouseEvent.MOUSE_PRESSED)) {
            System.out.println("Started Triangle");

            this.start = new Point(mouseEvent.getX(), mouseEvent.getY());

            // Initialize the triangle's points at the start position
            this.setT(new Point(mouseEvent.getX(), mouseEvent.getY()));
            this.setL(new Point(mouseEvent.getX(), mouseEvent.getY()));
            this.setR(new Point(mouseEvent.getX(), mouseEvent.getY()));


        } else if (mouseEventType.equals(MouseEvent.MOUSE_DRAGGED) || mouseEventType.equals(MouseEvent.MOUSE_RELEASED)) {

            double middle_point = this.start.x + (mouseEvent.getX() - this.start.x) / 2;

            double left_x = this.start.x;
            double right_x = mouseEvent.getX();

            // Ensure left_x is always the smaller one, allowing for flipping
            if (left_x > right_x) {
                double temp = left_x;
                left_x = right_x;
                right_x = temp;
            }

            double[] top = {middle_point, mouseEvent.getY()}; // top vertex of the triangle
            double[] bottom_left = {left_x, this.start.y};    // left vertex
            double[] bottom_right = {right_x, this.start.y};  // right vertex


            this.setT(new Point(top[0], top[1]));           // top
            this.setL(new Point(bottom_left[0], bottom_left[1])); // left
            this.setR(new Point(bottom_right[0], bottom_right[1])); // right

        }
        if (mouseEventType.equals(MouseEvent.MOUSE_DRAGGED)) {
            this.model.setBeingDrawn(this);

            // Update the rectangle
            //this.update(null, null);
        } else if (mouseEventType.equals(MouseEvent.MOUSE_RELEASED)) {
            System.out.println("Created Triangle");

            this.model.addDrawable(this);

        }
    }
}
