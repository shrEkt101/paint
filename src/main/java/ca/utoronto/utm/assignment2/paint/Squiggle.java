package ca.utoronto.utm.assignment2.paint;

import javafx.event.EventType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * This class's purpose is to create and draw a squiggle Drawable instance
 */
public class Squiggle extends Drawable{
    private ArrayList<Point> squigglePoints = new ArrayList<Point>();
    private Color color;
    private int thickness;

    /**
     * Initializes a squiggle Drawable
     * @param p the starting point of the squiggle
     * @param color the color of the squiggle
     * @param thickness the thickness of the squiggle
     * @param model the model that is linked to the squiggle
     */
    public Squiggle(Point p, Color color, int thickness, PaintModel model){
        super(model);
        this.squigglePoints.add(p);
        this.color = color;
        this.thickness = thickness;
    }

    private void addSquigglePoint(Point p){this.squigglePoints.add(p);}

    /**
     * Draws the squiggle with all the points
     * @param g2d the graphics context (actual canvas on which drawable is being
     *           drawn).
     */
    @Override
    public void draw(GraphicsContext g2d){
        g2d.setStroke(this.color);
        g2d.setLineWidth(this.thickness);
        ArrayList<Point> sPoints = this.squigglePoints;
        for(int i=0; i<  sPoints.size()-1; i++){
            Point p1 = sPoints.get(i);
            Point p2 = sPoints.get(i+1);
            g2d.strokeLine(p1.x,p1.y,p2.x,p2.y);
        }
    }

    /**
     * Uses the user's mouse events to trace the points of the squiggle
     * @param mouseEvent the user mouse events
     */
    @Override
    public void handle(MouseEvent mouseEvent){
        EventType<MouseEvent> mouseEventType = (EventType<MouseEvent>) mouseEvent.getEventType();

        // create a new squiggle object when clicked
        if (mouseEventType.equals(MouseEvent.MOUSE_PRESSED)){
            System.out.println("Started Squiggle");
        }
        // add points to the squiggle while dragged
        else if (mouseEventType.equals(MouseEvent.MOUSE_DRAGGED)) {
            // add a point to the squiggle
            this.addSquigglePoint(new Point(mouseEvent.getX(), mouseEvent.getY()));
            this.model.setBeingDrawn(this);
        }
        if (mouseEventType.equals(MouseEvent.MOUSE_RELEASED)){

                //finally add squiggle to model
                this.model.addDrawable(this);
        }
    }
}
