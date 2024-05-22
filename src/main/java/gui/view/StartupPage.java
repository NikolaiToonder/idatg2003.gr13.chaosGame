package gui.view;

import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/**
 * Startup page for the ChaosGame gui.
 *
 * @author Mustafa Kesen
 */
public class StartupPage {

  private final Consumer<Stage> onStartGame;
  private final Stage primaryStage;
  private VBox mainContents;
  private static final String DEFAULT_BACKGROUND_COLOR = "#2b2d31"; // Default background color

  /**
   * Creates the startup page for the ChaosGame gui.
   *
   * @param onStartGame The consumer to start the game.
   * @param primaryStage The primary stage of the program.
   */
  public StartupPage(Consumer<Stage> onStartGame, Stage primaryStage) {
    this.onStartGame = onStartGame;
    this.primaryStage = primaryStage;
    setupUi();
  }

  /**
   * Method to set up the UI of the startup page.
   */
  private void setupUi() {
    Label label = new Label("Welcome to the Chaos Game!");
    label.setTextFill(Color.WHITE);
    label.setTranslateY(-100);
    label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

    Button startButton = new Button("Start program");
    Button exitButton = new Button("Exit program");

    styleButtons(startButton, exitButton);
    configureButtons(startButton, exitButton);

    mainContents = new VBox(10, label, startButton, exitButton);
    mainContents.setAlignment(Pos.CENTER);
    mainContents.setStyle("-fx-background-color: " + DEFAULT_BACKGROUND_COLOR + ";");
  }

  /**
   * Method to create the content of the startup page.
   *
   * @return The content of the startup page.
   */
  public Parent createContent() {
    AnchorPane root = new AnchorPane();
    root.getChildren().add(mainContents);

    // Set the anchor constraints
    AnchorPane.setTopAnchor(mainContents, 0.0);
    AnchorPane.setRightAnchor(mainContents, 0.0);
    AnchorPane.setBottomAnchor(mainContents, 0.0);
    AnchorPane.setLeftAnchor(mainContents, 0.0);

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

  /**
   * Configures the buttons to perform the correct actions when clicked.
   *
   * @param startButton The start button.
   * @param exitButton  The exit button.
   */
  private void configureButtons(Button startButton, Button exitButton) {
    startButton.setOnAction(e -> onStartGame.accept(primaryStage));
    exitButton.setOnAction(e -> Platform.exit());
  }


}