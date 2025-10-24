package ca.paint;

import javafx.event.EventType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * This class's purpose is to create and draw a polyline Drawable instance
 */
public class Polyline extends Drawable{
    ArrayList<Point> polylinePoints = new ArrayList<Point>();
    private Color color;
    private int thickness;

    /**
     * Initializes a Polyline Drawable
     * @param point the starting point of the polyline
     * @param color the color of the polyline
     * @param thickness the thickness of the polyline
     * @param model the model that is linked to the polyline
     */
    public Polyline(Point point, Color color, int thickness, PaintModel model) {
        super(model);
        this.polylinePoints.add(point);
        this.color = color;
        this.thickness = thickness;
    }

    private void addPolylinePoint(Point point){this.polylinePoints.add(point);}

    /**
     * Draws the polyline by connecting all its points.
     * @param g2d the graphics context (actual canvas on which drawable is being
     *           drawn).
     */
    @Override
    public void draw(GraphicsContext g2d){
        g2d.setStroke(this.color);
        g2d.setLineWidth(this.thickness);
        ArrayList<Point> sPoints = this.polylinePoints;
        System.out.println(sPoints);
        System.out.println(sPoints.size());

        for(int i=0; i<  sPoints.size()-1; i++){
            Point p1 = sPoints.get(i);
            Point p2 = sPoints.get(i+1);
            System.out.println(p1.x + " " + p1.y+ " " + p2.x+ " " + p2.y);
            g2d.strokeLine(p1.x,p1.y,p2.x,p2.y);
        }
    }

    /**
     * Uses the user's mouse events (left-clicks and right-clicks)
     * to collect points that will be used to draw the polyline.
     * @param mouseEvent the user mouse events (the left clicks and right clicks)
     */
    @Override
    public void handle(MouseEvent mouseEvent){

        // if left-clicked again after initialisation, reset.
        if (mouseEvent.getButton() == MouseButton.PRIMARY){
            System.out.println("left clicked");
        }
        // add points to the polyline when rightclicked
        else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
            System.out.println("right clicked");
            // add a point to the squiggle
            this.addPolylinePoint(new Point(mouseEvent.getX(), mouseEvent.getY()));
            this.model.addDrawable(this);
        }
    }

}
