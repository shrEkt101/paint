package ca.utoronto.utm.assignment2.paint;


import javafx.event.EventType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import java.util.Observable;

/**
 * This class's purpose is to create and draw a circle Drawable instance.
 */
public class Circle extends Drawable{
        private Point centre;
        private double radius;
        private Color color;
        private Color fillColor;
        private int thickness;

        /**
         * Initializes a Circle Drawable
         * @param centre the starting point of the circle
         * @param color the color of the circle's outline
         * @param fillColor the color of the circle's body
         * @param thickness the thickness of the circle's outline
         * @param model the PaintModel that is linked to the circle
         */
        public Circle(Point centre, Color color, Color fillColor, int thickness, PaintModel model){

                super(model);
                this.centre = centre;
                this.color = color;
                this.fillColor = fillColor;
                this.thickness = thickness;
                this.radius = 0;
        }

        // basic getter and setter methods
        private Point getCentre() {return centre;}
        private void setCentre(Point centre) {this.centre = centre;}
        private double getRadius() {return radius; }
        private void setRadius(double radius) {this.radius = radius;}


        // required method for DrawableDraw interface
        // note that GraphicsContext is the actual panel on which we draw.

        /**
         * Uses the circle's diameter to draw the circle
         * in accordance to its outline color, fill color and thickness
         * @param g2d the graphics context (actual canvas on which drawable is being
         *           drawn).
         */
        @Override
        public void draw(GraphicsContext g2d) {

                //change colour
                g2d.setFill(this.fillColor);
                double x = this.getCentre().x-this.getRadius();
                double y = this.getCentre().y-this.getRadius();
                double diameter = 2*this.getRadius();
                g2d.fillOval(x, y, diameter, diameter);
                g2d.setStroke(this.color);
                g2d.setLineWidth(this.thickness);
                g2d.strokeOval(x, y, diameter, diameter);

        }

        // required method for DrawableUpdateModel interface

        /**
         * Uses the mouse events to calculate the diameter of the circle.
         * @param mouseEvent the user's mouse events on the canvas
         */
        @Override
        public void handle(MouseEvent mouseEvent) {
                EventType<MouseEvent> mouseEventType = (EventType<MouseEvent>) mouseEvent.getEventType();

                if(mouseEventType.equals(MouseEvent.MOUSE_PRESSED)) {

                        //when this is called, circle does not have radius. check paintcontroller.
                        System.out.println("Started Circle");
                        Point centre = new Point(mouseEvent.getX(), mouseEvent.getY());
                        this.setCentre(centre);
                        System.out.println(centre.x);

                } else if (mouseEventType.equals(MouseEvent.MOUSE_DRAGGED)) {

                        double circleX = this.getCentre().x-mouseEvent.getX();
                        double circleY = this.getCentre().y-mouseEvent.getY();
                        double radius = Math.sqrt(Math.pow(circleX,2)+Math.pow(circleY,2));

                        this.setRadius(radius);
                        this.model.setBeingDrawn(this);

                } else if (mouseEventType.equals(MouseEvent.MOUSE_RELEASED)) {

                        this.model.addDrawable(this);
                }
        }
}
