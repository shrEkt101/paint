package ca.utoronto.utm.assignment2.paint;

import javafx.scene.paint.Color;

/**
 * DrawableFactory uses the Factory Design Pattern to create
 * Drawable instances of shapes and lines.
 */

public class DrawableFactory {
    private PaintModel model;

    // the constructor takes in the model for which this factory is used, so that
    // each drawable instance is attached to that specific model.
    public DrawableFactory(PaintModel model){
        this.model = model;
    }

    // returns an instance of a drawable given the point at which the mouse is clicked.
    // used in paintController

    /**
     * Factory Design Pattern that creates Drawable instances of shapes and lines
     * @param creationPoint the starting point of the shape/line
     * @param color the outline color of the shape/line
     * @param fillColor the color of the shape body
     * @param thickness the thickness level of the outline of the shape/line
     * @param mode the PaintModel that is linked to these Drawable instances
     * @return
     */
    public Drawable createDrawable(Point creationPoint, Color color, Color fillColor, int thickness, String mode){
        if (mode == "Circle") {return new Circle(creationPoint, color, fillColor, thickness, this.model);}
        else if (mode == "Rectangle") {return new Rectangle(creationPoint, creationPoint, color, fillColor, thickness, this.model);}
        else if (mode == "Square") {return new Square(creationPoint, creationPoint, color, fillColor, thickness, this.model);}
        else if (mode == "Triangle") {return new Triangle(creationPoint, creationPoint, creationPoint, color, fillColor, thickness, this.model);}
        else if (mode == "Squiggle") {return new Squiggle(creationPoint, color, thickness, this.model);}
        else if (mode == "Oval") {return new Oval(creationPoint, creationPoint, color, fillColor, thickness, this.model);}
        else if (mode == "Polyline") {return new Polyline(creationPoint, color, thickness, this.model);}

        else return null;
    }
}
