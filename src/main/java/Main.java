import javafx.application.Application;
import transformations.JuliaTransform;
import vectors.Complex;
import vectors.Vector2D;
import javafx.stage.Stage;

/**
 * Main class for the program.
 */
public class Main extends Application{
  @Override
  public void start(Stage primaryStage) {
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
