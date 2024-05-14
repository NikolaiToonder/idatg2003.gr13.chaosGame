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
    Scene startupScene = createStartupScene(primaryStage);
    primaryStage.setScene(startupScene);
    primaryStage.setMinWidth(MIN_WIDTH);
    primaryStage.setMinHeight(MIN_HEIGHT);
    primaryStage.setFullScreen(true);

    centerStage(primaryStage);
    primaryStage.setTitle("Chaos Game");

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
    ChaosGameView chaosGameView = new ChaosGameView();

    //Make chaosGameView to fit bounds to resize
    //chaosGameView
    Scene chaosGameScene = new Scene(chaosGameView.createContent(primaryStage));


    Screen screen = Screen.getPrimary();
    primaryStage.setScene(chaosGameScene);
  }

  private void centerStage(Stage primaryStage) {
    Screen screen = Screen.getPrimary();
    Rectangle2D bounds = screen.getVisualBounds();

    double centerX = bounds.getMinX() + (bounds.getWidth() - MIN_WIDTH) / 2;
    double centerY = bounds.getMinY() + (bounds.getHeight() - MIN_HEIGHT) / 2;

    // Set the position of the stage
    primaryStage.setX(centerX);
    primaryStage.setY(centerY);
  }


  public static void main(String[] args) {
    launch(args);
  }
}