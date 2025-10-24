package ca.paint;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.ArrayList;

/**
 * Controller class for the MVC model. Handles all mouse events, as well as all
 * menu selection events such as undo.
 */
public class PaintController implements EventHandler<MouseEvent> {
    private PaintModel model;
    private String mode = "Circle";
    private String redoneMode = "Circle"; // for redo
    private String lastMode = null;
    private Drawable temp = null;
    private DrawableFactory factory;
    private Background bg_color;
    private Color color = Color.BLACK;
    private Color fillColor = Color.TRANSPARENT;
    private int thickness = 5;

    /**
     *
     * Creates a new controller
     * @param model the model to be manipulated.
     */
    public PaintController(PaintModel model){
        this.model = model;
        this.factory = new DrawableFactory(model);
    }

    /**
     * Changes the draw mode to mode.
     * @param mode a string indicating which type of drawable to be drawn.
     */
    public void setMode(String mode){
        // for updating the mode when drawing something new
        this.lastMode = this.mode; // for undo
        this.mode=mode;
        System.out.println(this.mode);
    }

    /**
     * changes outline color to color.
     * @param color color to be changed to.
     */
    public void setOutlineColor(String color){this.color = Color.web(color);}

    /**
     * Changes fill color to fillColor.
     * @param fillColor color to be changed to.
     */
    public void setFillColor(String fillColor){this.fillColor = Color.web(fillColor);}

    /**
     * Changes thickness to thickness.
     * @param thickness thickness number e.g. 1.
     */
    public void setThickness(String thickness){this.thickness = Integer.parseInt(thickness);}


    /**
     * relegates the handling of mouse event to the specific drawable (mode)
     * that has been created.
     * @param mouseEvent mouse event to be handled by each drawable.
     */
    public void handle(MouseEvent mouseEvent) {
        // Later when we learn about inner classes...
        // https://docs.oracle.com/javafx/2/events/DraggablePanelsExample.java.htm

        EventType<MouseEvent> mouseEventType = (EventType<MouseEvent>) mouseEvent.getEventType();

        Point start = new Point(mouseEvent.getX(), mouseEvent.getY());

        //left click to start new polyline
        //right click to add to current polyline
        //drag to show current line.

        // we have a separate case here for polyline as we need to detect both
        // left and right click, as opposed to click, drag, and release.
        if (mode == "Polyline"){
            if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEventType.equals(MouseEvent.MOUSE_PRESSED)){
                System.out.println("primary");
                temp = factory.createDrawable(start, this.color, this.fillColor, this.thickness, mode);
                model.clearRedoCanvas();
            }
            // add points to the polyline when rightclicked
            else if (mouseEvent.getButton() == MouseButton.SECONDARY && mouseEventType.equals(MouseEvent.MOUSE_PRESSED)) {
                if (temp != null) temp.handle(mouseEvent);
                temp = factory.createDrawable(start, this.color, this.fillColor, this.thickness, mode);
                model.setBeingDrawn(null);
            }
        }

        else {
            if (mouseEventType.equals(MouseEvent.MOUSE_PRESSED)) {
                System.out.println("pressed");

                temp = this.factory.createDrawable(start, this.color, this.fillColor, this.thickness, mode);
                if (temp != null) temp.handle(mouseEvent);
                model.clearRedoCanvas();

            } else if (mouseEventType.equals(MouseEvent.MOUSE_DRAGGED)) {
                if (temp != null) temp.handle(mouseEvent);

            } else if (mouseEventType.equals(MouseEvent.MOUSE_RELEASED)) {
                if (temp != null) {
                    System.out.println("released");
                    temp.handle(mouseEvent);
                    temp = null;
                    model.setBeingDrawn(null);
                }
            }
        }

    }

    /**
     * Will change the color of the background based off color chosen.
     *
     * @param command Given from pressing a menu bar item.
     */
    public void change_background(String command) {
        this.bg_color = new Background(command);

        this.model.setTempBackground(bg_color);
    }

    /**
     * Makes the model undo the last made drawable.
     *
     * @param command Given from pressing a menu bar item.
     */
    public void undoController(String command) {
        if(command.equals("Undo")) {
            this.model.undo();
            if(lastMode != null) this.redoneMode = mode; // set the undone mode based on mode before the undo
            this.setMode(lastMode); // sets the mode to the previous mode

        }
    }

    /**
     * Makes the model redo the undone drawable.
     *
     * @param command given from pressing a menu bar item.
     */
    public void redoController(String command) {
        if(command.equals("Redo")) {
            this.model.redo();
            this.setMode(lastMode); // need a way of returning mode to the redone mode
        }
    }

    /**
     * Removes all drawables form the models canvas.
     */
    public void clearDrawings() {
        this.model.clearDrawables();
    }


    /**
     * Will prompt the user to open a previously saved file of .canvas type to return to where they last left off.
     */
    public void openCanvasFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Canvas");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Canvas Files", "*.canvas"));

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){
                //create the loadedCanvas array from opening the desired .canvas file
                ArrayList<Drawable> loadedCanvas = (ArrayList<Drawable>) ois.readObject();
                this.model.clearDrawables();
                this.model.setDrawable(loadedCanvas);
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Failed to load canvas: " + e.getMessage());
            }

        }
    }

    /**
     * Will prompt the user to save a new file of .canvas type to their computer in order to open it later.
     */
    public void saveCanvasFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Canvas");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Canvas Files", "*.canvas"));

        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(model.getDrawable());
                System.out.println("Canvas saved successfully.");
            } catch (IOException e) {
                System.out.println("Failed to save canvas: " + e.getMessage());
            }
        }
    }

}
