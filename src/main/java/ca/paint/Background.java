package ca.paint;

import javafx.scene.paint.Color;

import java.io.Serializable;

/**
 * Background class in charge of everything background.
 */
public class Background implements Serializable {


    // string representation of color(represented by words, i.e. "white"
    // but we can use hex strings like "FA1219" or so to return a color object
    // )
    private String color;

    /**
     * Sets the color of the background.
     * @param c The color.
     */
    public Background(String c) {
        this.color = c;

    }

    /**
     * Color object representation of color, its accessed when replacing bg.
     * @return The color.
     */
    public Color getColor() {
        return Color.web(color);
    }
}
