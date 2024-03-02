import java.util.List;
import matrix.Matrix2x2;
import transformations.AffineTransform2D;
import transformations.JuliaTransform;
import transformations.Transform2D;
import vectors.Complex;
import vectors.Vector2D;

/**
 * Main class for the program.
 */
public class Main {

  public static void main(String[] args) {
    AffineTransform2D transform1 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5),
        new Vector2D(0, 0));
    AffineTransform2D transform2 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5),
        new Vector2D(0.5, 0));
    AffineTransform2D transform3 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5),
        new Vector2D(0.25, 0.5));

    List<Transform2D> transforms = List.of(transform1, transform2, transform3);
    ChaosGameDescription description = new ChaosGameDescription(transforms, new Vector2D(0, 0),
        new Vector2D(1, 1));
    Vector2D startingPoint = new Vector2D(0, 0);
    ChaosGame app = new ChaosGame(description, 30, 60, startingPoint);
    app.runSteps(3000);
    /*
        // Set the value of c to generate a particular Julia set
    List<Complex> juliaConstants = List.of(
        new Complex(-0.7269, 0.1889),
        new Complex(-0.8, 0.156),
        new Complex(0.285, 0),
        new Complex(0.45, 0.1428)
        // ... other constants
    );


     ChaosGameDescription description = new ChaosGameDescription(juliaConstants, new Vector2D(0, 0),
        new Vector2D(1, 1));
        // Set up the chaos game with complex number support
        ChaosGame app = new ChaosGame(description, 30, 60, new Vector2D(0, 0));
        app.runSteps(3000); // This method will need to be adapted for complex numbers
     */
  }
}
