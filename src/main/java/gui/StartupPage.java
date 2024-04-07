package gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.concurrent.atomic.AtomicInteger;

public class StartupPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label label = new Label("Welcome to the Startup Page!");
        label.setTextFill(Color.WHITE);
        label.setTranslateY(-100);

        Button startButton = new Button("Start program");
        Button exitButton = new Button("Exit program");
        Button changeTheme = new Button("Change theme");


        startButton.setStyle("-fx-background-color: #CCCCCC;");
        exitButton.setStyle("-fx-background-color: #CCCCCC;");


        Color[] textColors = {Color.WHITE, Color.BLACK};
        AtomicInteger currentIndex = new AtomicInteger(0);


        startButton.setOnAction(e -> {
            /*    primaryStage.close();
            new ChaosGamePage().start(new Stage());
             */
            System.out.println("WORK IN PROGRESS");
        });

        exitButton.setOnAction(e -> {
            Platform.exit();
        });



        changeTheme.setOnAction(e -> {
            String[] backgroundColors = {"#2b2d31", "#F3F3F3"}; // <-- Hex values for the background colors :)

            currentIndex.set((currentIndex.get() + 1) % backgroundColors.length);
            String newColor = backgroundColors[currentIndex.get()];
            primaryStage.getScene().getRoot().setStyle("-fx-background-color: " + newColor + ";");
            label.setTextFill(textColors[currentIndex.get()]);
            startButton.setTextFill(textColors[currentIndex.get()]);
            exitButton.setTextFill(textColors[currentIndex.get()]);
        });

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(label, startButton, exitButton);

        VBox vBox2 = new VBox(10);
        vBox2.setAlignment(Pos.BOTTOM_LEFT);
        vBox2.getChildren().addAll(changeTheme);

        StackPane root = new StackPane();
        root.getChildren().addAll(vBox, vBox2);
        root.setStyle("-fx-background-color: #2b2d31;");
        Scene scene = new Scene(root, 800, 500);

        primaryStage.setScene(scene);
        primaryStage.setTitle("ChaosGame");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}