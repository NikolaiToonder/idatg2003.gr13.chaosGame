package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * The main stage of the program. Will always be displayed, only contents change depending on what
 * class is initialized into this main stage.
 */
public class MainApp extends Application {

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setScene(createStartupScene(primaryStage));
    primaryStage.setHeight(750);
    primaryStage.setWidth(760);
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
    ChaosGameView chaosGameView = new ChaosGameView("#2b2d31", this::changeToStartupScene);
    Scene chaosGameScene = new Scene(chaosGameView.createContent(primaryStage));
    primaryStage.setScene(chaosGameScene);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
