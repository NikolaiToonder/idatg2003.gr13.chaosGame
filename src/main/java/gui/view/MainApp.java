package gui.view;


import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;


/**
 * The main stage of the program. Will always be displayed, only contents change depending on what
 * class is initialized into this main stage.
 *
 * @author Mustafa Kesen
 */
public class MainApp extends Application {

  private static final double MIN_WIDTH = 500; // Minimum width
  private static final double MIN_HEIGHT = 500; // Minimum height


  /**
   * Main method of the program. Will launch the program.
   *
   * @param primaryStage The main stage of the program.
   */
  @Override
  public void start(Stage primaryStage) {
    Scene startupScene  = createStartupScene(primaryStage);
    primaryStage.setScene(startupScene);
    primaryStage.setMinWidth(MIN_WIDTH);
    primaryStage.setMinHeight(MIN_HEIGHT);


    centerStage(primaryStage);
    primaryStage.setTitle("Chaos Game");

    primaryStage.show();
  }

  /**
   * Method to create the startup scene. Will create the startup page and return the scene.
   *
   * @param primaryStage The main stage of the program.
   * @return The scene of the startup page.
   */
  public Scene createStartupScene(Stage primaryStage) {
    StartupPage startupPage = new StartupPage(this::changeToChaosGameView, primaryStage);
    return new Scene(startupPage.createContent());
  }

  /**
   * Method to change the scene to the ChaosGameView.
   *
   * @param primaryStage The main stage of the program.
   */
  private void changeToChaosGameView(Stage primaryStage) {
    ChaosGameView chaosGameView = new ChaosGameView();
    Scene chaosGameScene = new Scene(chaosGameView.createContent(primaryStage));
    primaryStage.setScene(chaosGameScene);
  }

  /**
   * Method to center the stage on the screen.
   *
   * @param primaryStage The main stage of the program.
   */
  private void centerStage(Stage primaryStage) {
    Screen screen = Screen.getPrimary();
    Rectangle2D bounds = screen.getVisualBounds();

    double centerX = bounds.getMinX() + (bounds.getWidth() - MIN_WIDTH) / 2;
    double centerY = bounds.getMinY() + (bounds.getHeight() - MIN_HEIGHT) / 2;

    // Set the position of the stage
    primaryStage.setX(centerX);
    primaryStage.setY(centerY);
  }

  /**
   * Method to launch the program.
   *
   * @param args The arguments of the program.
   */
  public static void main(String[] args) {
    launch(args);
  }
}