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

    boolean isBackgroundDark;
    static String startupBackgroundColor = "#2b2d31"; // Default background color for StartupPage

    Scene chaosGameScene = new Scene(new ChaosGameView(startupBackgroundColor).createContent(), 800, 500);

    @Override
    public void start(Stage primaryStage) {
        Label label = new Label("Welcome to the Chaos Game!");
        label.setTextFill(Color.WHITE);
        label.setTranslateY(-100);


        label.setTranslateY(-100);

        Button startButton = new Button("Start program");
        Button exitButton = new Button("Exit program");
        Button changeTheme = new Button("Change theme");



        startButton.setStyle("-fx-background-color: #58b719;");
        exitButton.setStyle("-fx-background-color: #f55353;");
        changeTheme.setStyle("-fx-background-color: #CCCCCC;");

        Color[] textColors = {Color.WHITE, Color.BLACK};
        AtomicInteger currentIndex = new AtomicInteger(0);

        // Set onAction for changeTheme button
        changeTheme.setOnAction(e -> {
            String[] backgroundColors = {"#2b2d31", "#F3F3F3"};
            currentIndex.set((currentIndex.get() + 1) % backgroundColors.length);
            String newColor = backgroundColors[currentIndex.get()];
            primaryStage.getScene().getRoot().setStyle("-fx-background-color: " + newColor + ";");
            label.setTextFill(textColors[currentIndex.get()]);

            isBackgroundDark = newColor.equals("#2b2d31");
        });

        // Set onAction for startButton
        startButton.setOnAction(e -> {
            System.out.println("Starting the program...");

            primaryStage.setScene(chaosGameScene);
            primaryStage.show();

            System.out.println("PROGRAM RUNNING...");
        });

        // Set onAction for exitButton
        exitButton.setOnAction(e -> Platform.exit());

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(label, startButton, changeTheme, exitButton); // Added changeTheme here for simplicity
        StackPane root = new StackPane();
        root.getChildren().addAll(vBox);
        root.setStyle("-fx-background-color: " + startupBackgroundColor + ";"); // Set the same theme as StartupPage
        Scene scene = new Scene(root, 800, 500);

        primaryStage.setScene(scene);
        primaryStage.setTitle("ChaosGame");
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
