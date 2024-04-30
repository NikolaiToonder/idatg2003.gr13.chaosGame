package gui;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.util.function.Consumer;

/**
 * Main Menu for the Chaos Game program.
 */
public class StartupPage {

    private final Consumer<Stage> onStartGame;
    private final Stage primaryStage;
    private VBox vBox;
    private static final String DEFAULT_BACKGROUND_COLOR = "#2b2d31"; // Default background color

    public StartupPage(Consumer<Stage> onStartGame, Stage primaryStage) {
        this.onStartGame = onStartGame;
        this.primaryStage = primaryStage;
        setupUI();

        setScreenResolution(840, 720); // Set to your desired resolution
    }

    private void setupUI() {
        Label label = new Label("Welcome to the Chaos Game!");
        label.setTextFill(Color.WHITE);
        label.setTranslateY(-100);
        label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");





        Button startButton = new Button("Start program");
        Button exitButton = new Button("Exit program");


        styleButtons(startButton, exitButton);
        configureButtons(startButton, exitButton);

        vBox = new VBox(10, label, startButton, exitButton);
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
        buttons[0].setStyle("-fx-background-color: #58b719; -fx-font-weight: bold;"); // startButton
        buttons[0].setTextFill(Color.WHITE);
        buttons[1].setTextFill(Color.WHITE);
        buttons[1].setStyle("-fx-background-color: #f55353;-fx-font-weight: bold;"); // exitButton
    }

    private void configureButtons(Button startButton, Button exitButton) {
        startButton.setOnAction(e -> onStartGame.accept(primaryStage));
        exitButton.setOnAction(e -> Platform.exit());
    }

    private void setScreenResolution(double width, double height) {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primaryScreenBounds.getWidth() - width) / 2);
        primaryStage.setY((primaryScreenBounds.getHeight() - height) / 2);
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);
    }



}