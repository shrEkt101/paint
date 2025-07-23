package ca.utoronto.utm.assignment2.paint;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This is part of the GUI that handles selection of modes and menu bar items.
 */
public class ShapeChooserPanel extends GridPane implements EventHandler<ActionEvent> {

        private final View view;
        private Button selectedModeButton = null; // Keeps track of the currently selected button
        private final PaintController controller;

        /**
         * sets up the buttons of the panel
         * @param view the view of the program
         */
        public ShapeChooserPanel(View view) {

                this.view = view;
                this.controller = this.view.getModelController();

                String[] buttonLabels = { "Circle", "Rectangle", "Square", "Squiggle", "Polyline", "Oval", "Triangle"};
                // file path string for each button image/icon
                String[] imagePaths = {
                        getClass().getResource("/ca/utoronto/utm/assignment2/images/circle.png").toExternalForm(),
                        getClass().getResource("/ca/utoronto/utm/assignment2/images/rectangle.png").toExternalForm(),
                        getClass().getResource("/ca/utoronto/utm/assignment2/images/square.png").toExternalForm(),
                        getClass().getResource("/ca/utoronto/utm/assignment2/images/squiggle.png").toExternalForm(),
                        getClass().getResource("/ca/utoronto/utm/assignment2/images/polyline.png").toExternalForm(),
                        getClass().getResource("/ca/utoronto/utm/assignment2/images/oval.png").toExternalForm(),
                        getClass().getResource("/ca/utoronto/utm/assignment2/images/Triangle.png").toExternalForm()
                };
                int row = 0;
                for (String label : buttonLabels) {
                        String imagePath = imagePaths[row];

                        // load image for current button
                        Image icon = new Image(imagePath);
                        ImageView imageView = new ImageView(icon);

                        // set size of the image
                        imageView.setFitWidth(32);
                        imageView.setFitHeight(32);
                        imageView.setPreserveRatio(true); // maintains aspect ratio

                        // create button with text and an icon
                        Button button = new Button(label, imageView);
                        button.setMinWidth(120);

                        // set default color for the button
                        button.setStyle("-fx-background-color: lightgray;");
                        // DOESNT WORK if(label == "Circle") button.setDisable(true); // by default circle is selected when program starts
                        this.add(button, 0, row);
                        row++;

                        // set on action for the button
                        button.setOnAction(this);
                }
        }

        /**
         * reenables every button in the shape chooser panel
         */
        public void reenableChooserPanelButtons() {
                // re-enable all buttons first
                for(Node node : this.getChildren()) {
                        // reset previously selected button if exists
                        if(selectedModeButton != null) {
                                selectedModeButton.setDisable(false); // re-enable the button
                                selectedModeButton.setStyle("-fx-background-color: lightgray;");
                        }
                }
        }

        /**
         * handles all actions of the buttons on the panel
         * @param event action event of the buttons
         */
        @Override
        public void handle(ActionEvent event) {
                Button clickedButton = (Button) event.getSource();
                String mode = clickedButton.getText();
                controller.setMode(mode);

                System.out.println(mode);

                // re-enable all buttons first
                for(Node node : this.getChildren()) {
                        // reset previously selected button if exists
                        if(selectedModeButton != null) {
                                selectedModeButton.setDisable(false); // re-enable the button
                                selectedModeButton.setStyle("-fx-background-color: lightgray;");
                        }
                }

                // highlight and disable the clicked button
                clickedButton.setDisable(true); // Disable the selected button
                clickedButton.setStyle("-fx-background-color: lightblue;");
                selectedModeButton = clickedButton; // update selected button
        }
}


