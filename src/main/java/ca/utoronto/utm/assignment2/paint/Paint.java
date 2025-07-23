package ca.utoronto.utm.assignment2.paint;


import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Class that launches the application itself.
 */
public class Paint extends Application {

        PaintModel model; // Model
        View view; // View

        public static void main(String[] args) {
                launch(args);
        }

        @Override
        public void start(Stage stage) throws Exception {

                this.model = new PaintModel();

                // View + Controller
                this.view = new View(model, stage);
        }
}
