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

    AffineTransform2D tranform1 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D(0, 0));
    AffineTransform2D tranform2 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D(0.5, 0));
    AffineTransform2D tranform3 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D(0.25, 0.5));

    List<Transform2D> transforms = List.of(tranform1, tranform2, tranform3);
    ChaosGameDescription description = new ChaosGameDescription(transforms, new Vector2D(0, 0), new Vector2D(1, 1));
    Vector2D startingPoint = new Vector2D(0, 0);
    ChaosGame app = new ChaosGame(description, 30, 60, startingPoint);
    app.runSteps(3000);


  }
}
