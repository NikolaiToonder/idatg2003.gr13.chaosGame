package gui;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * The main stage of the program. Will always be displayed, only contents change depending on what
 * class is initialized into this main stage.
 */
public class MainApp extends Application {

  private static final double MIN_WIDTH = 710; // Minimum width
  private static final double MIN_HEIGHT = 600; // Minimum height

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setScene(createStartupScene(primaryStage));
    primaryStage.setMinWidth(MIN_WIDTH);
    primaryStage.setMinHeight(MIN_HEIGHT);
    centerStage(primaryStage); // Center the stage
    primaryStage.show();
  }

  public Scene createStartupScene(Stage primaryStage) {
    StartupPage startupPage = new StartupPage(this::changeToChaosGameView, primaryStage);
    return new Scene(startupPage.createContent());
  }

  public void changeToStartupScene(Stage primaryStage) {
    primaryStage.setScene(createStartupScene(primaryStage));
  }

  private void changeToChaosGameView(Stage primaryStage) {
    ChaosGameView chaosGameView = new ChaosGameView(this::changeToStartupScene);
    Scene chaosGameScene = new Scene(chaosGameView.createContent(primaryStage), 760, 600); // Set initial size
    primaryStage.setScene(chaosGameScene);
    centerStage(primaryStage); // Center the stage
  }

  private void centerStage(Stage stage) {
    Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
    stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
    stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
  }

  public static void main(String[] args) {
    launch(args);
  }
}