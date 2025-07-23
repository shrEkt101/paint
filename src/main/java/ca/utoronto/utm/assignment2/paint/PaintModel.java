package ca.utoronto.utm.assignment2.paint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Model of the MVC pattern. Contains all drawables on the canvas and updates
 * them.
 */
public class PaintModel extends Observable implements Serializable {
    private static final long serialVersionUID = 1L;
        // instead of having an arraylist for each specific drawable (e.g. circle),
        // we have a master arraylist called canvas which stores everything.
        // this makes it so that drawings are added in order, and in paintPanel,
        // they are drawn in order.

        // canvas contains all drawables on the canvas in order
        private ArrayList<Drawable> canvas = new ArrayList<Drawable>();
        private ArrayList<Drawable> redoCanvas = new ArrayList<Drawable>();
        private Drawable beingDrawn;

        // bg - background
        private ArrayList<Background> backgrounds = new ArrayList<Background>();
        private Background tempBackground;

    /**
     * adds a drawable d to canvas
      * @param d drawable to be added
     */
    public void addDrawable(Drawable d){
                canvas.add(d);
                setChanged();
                notifyObservers();
        }

    /**
     * Returns an arraylist of drawable instances in the canvas
     * @return
     */
    public ArrayList<Drawable> getDrawable(){return this.canvas;}

    /**
     * To start a new canvas by resetting the canvas
      * @param canvas the arraylist of drawables populating the canvas
     */
    public void setDrawable(ArrayList<Drawable> canvas){
            //sets the canvas to a new canvas
            this.canvas = canvas;
            this.setChanged();
            this.notifyObservers();
        }

        // this keeps track of the temporary shape/line being drawn.
        // when this is updated, it is drawn at the bottom of paintPanel

    /**
     * Informs the observer about the Drawable that is being drawn
     * @param beingDrawn the Drawable instance that is being drawn in the canvas
     */
        public void setBeingDrawn(Drawable beingDrawn){
            this.beingDrawn = beingDrawn;
            setChanged();
            notifyObservers();
        }

    /**
     * Returns the Drawable that is being drawn in the canvas
     * @return
     */
    public Drawable getBeingDrawn() {
            return beingDrawn;
        }

    /**
     * Informs the observer about the change in background color
     * @param b the background color
     */
    public void setTempBackground(Background b) {
            this.tempBackground = b;
            this.setChanged();
            this.notifyObservers();
        }

    /**
     * Clears the Drawable arraylist for the start of a new canvas
     */
    public void clearDrawables(){
            this.tempBackground = null;
            this.backgrounds.clear();
            this.canvas.clear();
            this.setBeingDrawn(null);

            this.setChanged();
            this.notifyObservers();
        }

    /**
     * Returns the current background color
     * @return
     */
    public Background getTempBackground(){ return this.tempBackground; }

        // undo
        // pop the last Drawable from the canvas in model

    /**
     * Undoes the most recent Drawable drawn on the canvas
     */
    public void undo(){
            if(canvas.size()>0){
                Drawable lastElement = canvas.remove(canvas.size() - 1);
                redoCanvas.add(lastElement);
                this.setChanged();
                this.notifyObservers();
            }
        }

        // redo
        // add the last element from the redoCanvas list to the end of canvas list

    /**
     * Redoes the most recent undo onto the canvas
     */
        public void redo(){
            if(redoCanvas.size()>0){
                canvas.add(redoCanvas.remove(redoCanvas.size() - 1));
                this.setChanged();
                this.notifyObservers();
            }
        }

        public void clearRedoCanvas(){this.redoCanvas.clear();}

}
