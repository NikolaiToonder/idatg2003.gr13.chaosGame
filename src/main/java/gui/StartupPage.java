package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class StartupPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a label
        Label label = new Label("Welcome to the Startup Page!");

        // Create a StackPane layout
        StackPane root = new StackPane();
        root.getChildren().add(label);

        // Create a Scene
        Scene scene = new Scene(root, 400, 300);

        // Set the Scene on the Stage
        primaryStage.setScene(scene);

        // Set the title of the Stage
        primaryStage.setTitle("Startup Page");

        // Show the Stage
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
