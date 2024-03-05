import java.util.List;
import matrix.Matrix2x2;
import transformations.AffineTransform2D;
import transformations.Transform2D;
import vectors.Vector2D;

/**
 * Main class for the program.
 */
public class Main {

  public static void main(String[] args) {
    ChaosGameDescription description = new ChaosGameDescription(
        "C:/Users/nikol/Desktop/NTNU/IDATG1005/Test/affineTransform.txt");
    Vector2D startingPoint = new Vector2D(1, 1);
    ChaosGame app = new ChaosGame(description, 70, 140, startingPoint);
    app.runSteps(1000000);
  }
}
