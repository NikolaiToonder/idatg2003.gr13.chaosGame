package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main stage of the program. Will always be displayed, only contents change depending on what
 * class is initialized into this main stage.
 */
public class MainApp extends Application {

  private static final double MIN_WIDTH = 760; // Minimum width
  private static final double MIN_HEIGHT = 500; // Minimum height

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setScene(createStartupScene(primaryStage));
    primaryStage.setMinWidth(MIN_WIDTH);
    primaryStage.setMinHeight(MIN_HEIGHT);
    primaryStage.show();

    // Add listener to stage width property
    primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.doubleValue() < MIN_WIDTH) {
        primaryStage.setWidth(MIN_WIDTH);
      }
    });

    // Add listener to stage height property
    primaryStage.heightProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.doubleValue() < MIN_HEIGHT) {
        primaryStage.setHeight(MIN_HEIGHT);
      }
    });
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
    Scene chaosGameScene = new Scene(chaosGameView.createContent(primaryStage));
    primaryStage.setScene(chaosGameScene);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
