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

    ChaosGameDescription description = new ChaosGameDescription(List.of(new AffineTransform2D(new
        Matrix2x2(0.5,0.2,0.2,0.5),new Vector2D(0.2,0.5)) ), new Vector2D(0, 0), new Vector2D(3, 5));
    ChaosGame app = new ChaosGame(description, 70, 70);
    app.runSteps(1000);


  }
}
