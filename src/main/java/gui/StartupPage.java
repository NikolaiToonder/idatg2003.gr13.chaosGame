package gui;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * Main Menu for the Chaos Game program.
 */
public class StartupPage {

    private Consumer<Stage> onStartGame;
    private Stage primaryStage;
    private Label label;
    private VBox vBox;
    private Color[] textColors = {Color.WHITE, Color.BLACK};
    private AtomicInteger currentIndex = new AtomicInteger(0);
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
        Button changeTheme = new Button("Change theme");

        styleButtons(startButton, exitButton, changeTheme);
        configureButtons(startButton, exitButton, changeTheme);

        vBox = new VBox(10, label, startButton, changeTheme, exitButton);
        vBox.setAlignment(Pos.CENTER);
        vBox.setStyle("-fx-background-color: " + DEFAULT_BACKGROUND_COLOR + ";");
    }

    public Parent createContent() {
        AnchorPane root = new AnchorPane();
        root.getChildren().add(vBox);

        // Set the anchor constraints
        AnchorPane.setTopAnchor(vBox, 0.0);
        AnchorPane.setRightAnchor(vBox, 0.0);
        AnchorPane.setBottomAnchor(vBox, 0.0);
        AnchorPane.setLeftAnchor(vBox, 0.0);

        return root;
    }

    /**
     * Takes in a list of buttons and styles them. The syntax in parameter could be written
     * differently.
     *
     * @param buttons list of buttons to style
     */
    private void styleButtons(Button... buttons) {
        buttons[0].setStyle("-fx-background-color: #58b719;"); // startButton
        buttons[1].setStyle("-fx-background-color: #f55353;"); // exitButton
        buttons[2].setStyle("-fx-background-color: #CCCCCC;"); // changeTheme
    }

    private void configureButtons(Button startButton, Button exitButton, Button changeTheme) {
        changeTheme.setOnAction(e -> toggleTheme());
        startButton.setOnAction(e -> onStartGame.accept(primaryStage));
        exitButton.setOnAction(e -> Platform.exit());
    }

    private void toggleTheme() {
        String[] backgroundColors = {"#2b2d31", "#F3F3F3"};
        currentIndex.set((currentIndex.get() + 1) % backgroundColors.length);
        String newColor = backgroundColors[currentIndex.get()];
        vBox.setStyle("-fx-background-color: " + newColor + ";");
        label.setTextFill(textColors[currentIndex.get()]);
    }
}