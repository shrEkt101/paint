package ca.paint;
import javafx.scene.canvas.Canvas;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * This class is specifically for redrawing the canvas. Also functions as a
 * controller in the MVC pattern.
 */
public class PaintPanel extends Canvas implements Observer {
//    private String mode="Circle";
    private PaintModel model;

    private PaintController controller;

    /**
     * Constructor for PaintPanel.
     * @param model Mode to be interacted with.
     */
    public PaintPanel(PaintModel model) {

        // adding listener to the width and height properties.
        // the parameters are empty because we are not redrawing the canvas
        // for any specific width or height.
        // We are redrawing it for any change in width and height.
        this.widthProperty().addListener((obs, oldWidth, newWidth) -> redraw());
        this.heightProperty().addListener((obs, oldHeight, newHeight) -> redraw());

        this.model=model;
        this.model.addObserver(this);
        this.controller = new PaintController(this.model);

        this.addEventHandler(MouseEvent.MOUSE_PRESSED, controller);
        this.addEventHandler(MouseEvent.MOUSE_RELEASED, controller);
        this.addEventHandler(MouseEvent.MOUSE_MOVED, controller);
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, controller);
        this.addEventHandler(MouseEvent.MOUSE_DRAGGED, controller);
    }

    /**
     *
     * @return This instance's PaintController
     */
    public PaintController getController(){return this.controller;}

    /**
     * Update the frame.
     * @param o     the observable object.
     * @param arg   an argument passed to the {@code notifyObservers}
     *                 method.
     */
    @Override
    public void update(Observable o, Object arg) {
        System.out.println("updating frame");
        redraw();
    }

    /**
     * Draws the entire canvas so the user can see it.
     */
    public void redraw() {

        GraphicsContext g2d = this.getGraphicsContext2D();
        g2d.clearRect(0, 0, this.getWidth(), this.getHeight());

        // draw temp background

        if (this.model.getTempBackground() != null){
            g2d.setFill(this.model.getTempBackground().getColor());

            g2d.fillRect(0, 0, g2d.getCanvas().getWidth(), g2d.getCanvas().getHeight());
        }

        // note that GraphicsContext (g2d) is the actual panel on which we draw.
         for (Drawable d: this.model.getDrawable()) {
             d.draw(g2d);
         }
        Drawable drawable = model.getBeingDrawn();

        if (drawable != null) {
            model.getBeingDrawn().draw(g2d);
        }

        g2d.setStroke(Color.BLACK);
        g2d.setLineWidth(5);
        g2d.strokeRect(0, 0, this.getWidth(), this.getHeight());

    }
}

