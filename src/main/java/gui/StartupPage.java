package gui;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.function.Consumer;

/**
 * Main Menu for the Chaos Game program.
 */
public class StartupPage {

    private Consumer<Stage> onStartGame;
    private Stage primaryStage;
    private Label label;
    private VBox vBox;
    private static final String DEFAULT_BACKGROUND_COLOR = "#2b2d31"; // Default background color

    public StartupPage(Consumer<Stage> onStartGame, Stage primaryStage) {
        this.onStartGame = onStartGame;
        this.primaryStage = primaryStage;
        setupUI();
    }

    private void setupUI() {
        label = new Label("Welcome to the Chaos Game!");
        label.setTextFill(Color.WHITE);
        label.setTranslateY(-100);

        Button startButton = new Button("Start program");
        Button exitButton = new Button("Exit program");

        styleButtons(startButton, exitButton);
        configureButtons(startButton, exitButton);

        vBox = new VBox(10, label, startButton, exitButton);
        vBox.setAlignment(Pos.CENTER);

        vBox.setStyle("-fx-background-color: " + DEFAULT_BACKGROUND_COLOR + ";");
    }

    public Parent createContent() {
        StackPane root = new StackPane(); // Change AnchorPane to StackPane
        root.getChildren().add(vBox);

        // Center the vBox within the StackPane
        StackPane.setAlignment(vBox, Pos.CENTER);

        return root;
    }

    /**
     * Takes in a list of buttons and styles them.
     *
     * @param buttons list of buttons to style
     */
    private void styleButtons(Button... buttons) {
        buttons[0].setStyle("-fx-background-color: #58b719;"); // startButton
        buttons[0].setTextFill(Color.WHITE); // startButton
        buttons[1].setStyle("-fx-background-color: #f55353;"); // exitButton
    }

    private void configureButtons(Button startButton, Button exitButton) {
        startButton.setOnAction(e -> onStartGame.accept(primaryStage));
        exitButton.setOnAction(e -> Platform.exit());
    }
}