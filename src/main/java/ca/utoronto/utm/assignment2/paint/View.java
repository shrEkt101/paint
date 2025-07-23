package ca.utoronto.utm.assignment2.paint;

import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Builds the GUI of the Paint application and updates the controller about the
 * user's interactions with the menu bar.
 */

public class View implements EventHandler<ActionEvent> {

        private PaintModel paintModel;
        private PaintPanel paintPanel;
        private ShapeChooserPanel shapeChooserPanel;

        public View(PaintModel model, Stage stage) {
            this.paintModel = model;

            this.paintPanel = new PaintPanel(this.paintModel);
            this.shapeChooserPanel = new ShapeChooserPanel(this);

            BorderPane root = new BorderPane();
            root.setTop(createMenuBar());
            root.setCenter(this.paintPanel);
            root.setLeft(this.shapeChooserPanel);

            // calculate the new width and height for the paint panel properties.
            // DoubleBinding type is used because that is the return type of widthProperty() and
            // HeightProperty().
            DoubleBinding new_width = root.widthProperty().subtract(this.shapeChooserPanel.widthProperty());
            DoubleBinding new_height = root.heightProperty().subtract(this.createMenuBar().heightProperty());
            // the new width and height are then bound to the width and height properties of paint panel.
            this.paintPanel.widthProperty().bind(new_width);
            this.paintPanel.heightProperty().bind(new_height);

            Scene scene = new Scene(root, 600, 400);
            stage.setScene(scene);
            stage.setTitle("Paint");
            stage.show();
        }

        /**
         * @return the paint model
         */
        public PaintModel getPaintModel() {
                return this.paintModel;
        }

        public PaintController getModelController() {return this.paintPanel.getController();}


        /**
         * creates the menubar for the application
         * @return a menubar
         */
        private MenuBar createMenuBar() {

                MenuBar menuBar = new MenuBar();
                Menu menu;
                MenuItem menuItem;

                // A menu for File

                menu = new Menu("File");

                menuItem = new MenuItem("New");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuItem = new MenuItem("Open");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuItem = new MenuItem("Save");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menu.getItems().add(new SeparatorMenuItem());

                menuItem = new MenuItem("Exit");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuBar.getMenus().add(menu);

                // Another menu for Edit

                menu = new Menu("Edit");

                menuItem = new MenuItem("Cut");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuItem = new MenuItem("Copy");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuItem = new MenuItem("Paste");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menu.getItems().add(new SeparatorMenuItem());
                menuItem = new MenuItem("Undo");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuItem = new MenuItem("Redo");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuBar.getMenus().add(menu);

                // Another menu for background color

                menu = new Menu("Background Color");

                String [] colors = {"blue", "white", "red", "yellow", "orange"};

                for (String color : colors) {
                        menuItem = new MenuItem(color);
                        menuItem.setOnAction(this);
                        menu.getItems().add(menuItem);
                }

                MenuItem customColor = new MenuItem("Custom Color...");
                customColor.setOnAction(this);
                menu.getItems().add(customColor);
                menuBar.getMenus().add(menu);

                // Another menu for outline color

                menu = new Menu("Line Color");
                MenuItem selectOutline = new MenuItem("Select Outline Color");
                selectOutline.setOnAction(this);
                menu.getItems().add(selectOutline);
                menuBar.getMenus().add(menu);

                // Another menu for fill color

                menu = new Menu("Fill Color");
                MenuItem solidFill = new MenuItem("Solid Fill Color");
                solidFill.setOnAction(this);
                menu.getItems().add(solidFill);

                MenuItem fillWithOutline = new MenuItem("Fill Color with Outline");
                fillWithOutline.setOnAction(this);
                menu.getItems().add(fillWithOutline);

                menuBar.getMenus().add(menu);

                //New menu for line thickness

                menu = new Menu("Line Thickness");

                String [] thicknessLevels = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

                for (String level:thicknessLevels) {
                        menuItem = new MenuItem(level);
                        menuItem.setOnAction(this);
                        menu.getItems().add(menuItem);
                }

                menuBar.getMenus().add(menu);

                return menuBar;
        }

        /**
         * Handles the action events of the view
         * @param event the action event type
         */
        @Override
        public void handle(ActionEvent event) {
                System.out.println();
                System.out.println(((MenuItem) event.getSource()).getText());
                String command = ((MenuItem) event.getSource()).getText();
                System.out.println(command);
                if (command.equals("Exit")) {
                        Platform.exit();
                } else {
                        PaintController controller = getModelController();

                        // set mode to whatever action whether its create new file or change bg color
                        controller.setMode(command);

                        /** IMPORTANT:
                         * What I did here is I accessed the buttons corresponding dropdown
                         * menu button to check what action I am performing whether it's
                         * related to "File", "edit" or "Background Color"
                         * **/

                        if (((MenuItem) event.getSource()).getParentMenu().getText().equals("Background Color")) {
                                if (command.equals("Custom Color...")) {
                                        // Show a ColorPicker dialog
                                        ColorPicker colorPicker = new ColorPicker();

                                        // Set up a small dialog to display the ColorPicker
                                        Stage colorStage = new Stage();
                                        VBox vbox = new VBox(colorPicker);
                                        Scene scene = new Scene(vbox, 75, 50);
                                        colorStage.setScene(scene);
                                        colorStage.setTitle("Choose Background Color");
                                        colorStage.show();

                                        // Listen for color selection
                                        colorPicker.setOnAction(colorEvent -> {
                                                Color selectedColor = colorPicker.getValue();
                                                controller.change_background(selectedColor.toString());
                                                colorStage.close(); // Close dialog after selection
                                                System.out.println("Background color changed to: " + selectedColor);
                                        });
                                } else {

                                        controller.change_background(command);
                                }
                                shapeChooserPanel.reenableChooserPanelButtons();
                        } else if (((MenuItem) event.getSource()).getParentMenu().getText().equals("Line Color")) {
                                // Show a ColorPicker dialog
                                ColorPicker colorPicker = new ColorPicker();

                                // Set up a small dialog to display the ColorPicker
                                Stage colorStage = new Stage();
                                VBox vbox = new VBox(colorPicker);
                                Scene scene = new Scene(vbox, 75, 50);
                                colorStage.setScene(scene);
                                colorStage.setTitle("Choose Line Color");
                                colorStage.show();

                                // Listen for color selection
                                colorPicker.setOnAction(colorEvent -> {
                                        Color selectedColor = colorPicker.getValue();
                                        controller.setOutlineColor(selectedColor.toString());
                                        colorStage.close(); // Close dialog after selection
                                        System.out.println("Line color changed to: " + selectedColor);
                                });
                                shapeChooserPanel.reenableChooserPanelButtons();
                        } else if (((MenuItem) event.getSource()).getParentMenu().getText().equals("Fill Color")) {
                                // Show a ColorPicker dialog
                                ColorPicker colorPicker = new ColorPicker();

                                // Set up a small dialog to display the ColorPicker
                                Stage colorStage = new Stage();
                                VBox vbox = new VBox(colorPicker);
                                Scene scene = new Scene(vbox, 75, 50);
                                colorStage.setScene(scene);
                                colorStage.setTitle("Choose Color");
                                colorStage.show();

                                // Listen for color selection
                                colorPicker.setOnAction(colorEvent -> {
                                        Color selectedColor = colorPicker.getValue();
                                        controller.setFillColor(selectedColor.toString());
                                        if (command.equals("Solid Fill Color")) {
                                                controller.setOutlineColor(selectedColor.toString());
                                        }
                                        colorStage.close(); // Close dialog after selection
                                        System.out.println("Fill color changed to: " + selectedColor);
                                });
                                shapeChooserPanel.reenableChooserPanelButtons();
                        } else if (((MenuItem) event.getSource()).getParentMenu().getText().equals("Line Thickness")) {
                                controller.setThickness(command);
                                shapeChooserPanel.reenableChooserPanelButtons();
                        } else if (((MenuItem) event.getSource()).getParentMenu().getText().equals("Edit")) {
                                controller.undoController(command);
                                controller.redoController(command);
                                shapeChooserPanel.reenableChooserPanelButtons();
                        } else if (((MenuItem) event.getSource()).getParentMenu().getText().equals("File")) {
                                switch (((MenuItem) event.getSource()).getText()) {
                                        case ("New"):
                                                shapeChooserPanel.reenableChooserPanelButtons();
                                                controller.clearDrawings();
                                                break;
                                        case ("Open"):
                                                //opens a given .canvas file and loads it into the model
                                                shapeChooserPanel.reenableChooserPanelButtons();
                                                controller.openCanvasFile();
                                                break;
                                        case ("Save"):
                                                shapeChooserPanel.reenableChooserPanelButtons();
                                                controller.saveCanvasFile();
                                                //saves the current canvas to a file
                                                break;

                                }
                        }
                }
        }
}
