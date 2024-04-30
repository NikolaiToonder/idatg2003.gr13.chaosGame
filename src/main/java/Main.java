import chaosgameclasses.ChaosGameDescription;
import vectors.Vector2D;

/**
 * Main class for the program.
 */
public class Main {

  public static void main(String[] args) {
    ChaosGameDescription description = new ChaosGameDescription(
        "src/resources/sierpinskiTriangle.txt");
    Vector2D startingPoint = new Vector2D(0, 0);
    ChaosGameDescription.ChaosGame app = new ChaosGameDescription.ChaosGame(description, 140,70, startingPoint);
    app.runGame();


/*
    public static void main(String[] args) {
      ChaosGameDescription description = new ChaosGameDescription(
          "C:/Users/musta/Downloads/Mappe2Prog2/affineTransform.txt");
      Vector2D startingPoint = new Vector2D(0, 0);
      ChaosGame app = new ChaosGame(description, 70,140, startingPoint);
      app.runSteps(30000);

*/
    /*
    Matrix2x2 transformationMatrix1 = new Matrix2x2(0, 0, 0, 0.16);
    Matrix2x2 transformationMatrix2 = new Matrix2x2(0.85, 0.04, -0.04, 0.85);
    Matrix2x2 transformationMatrix3 = new Matrix2x2(0.2, -0.26, 0.23, 0.22);
    Matrix2x2 transformationMatrix4 = new Matrix2x2(-0.15, 0.28, 0.26, 0.24);
    Vector2D transformationVector1 = new Vector2D(0, 0);
    Vector2D transformationVector2 = new Vector2D(0, 1.6);
    Vector2D transformationVector3 = new Vector2D(0, 0.44);

    AffineTransform2D transformation1 = new AffineTransform2D(transformationMatrix1, transformationVector1);
    AffineTransform2D transformation2 = new AffineTransform2D(transformationMatrix2, transformationVector2);
    AffineTransform2D transformation3 = new AffineTransform2D(transformationMatrix3, transformationVector2);
    AffineTransform2D transformation4 = new AffineTransform2D(transformationMatrix4, transformationVector3);
    List<Transform2D> affineTransforms = List.of(transformation1, transformation2, transformation3, transformation4);
    chaosGameClasses.ChaosGameDescription description = new chaosGameClasses.ChaosGameDescription(affineTransforms, new Vector2D(-2, -2), new Vector2D(10, 10));
    Vector2D startingPoint = new Vector2D(0, 0);
    chaosGameClasses.ChaosGame app = new chaosGameClasses.ChaosGame(description, 100, 300, startingPoint);
    app.runSteps(3000000);
     */

    /*
    Complex point = new Complex(-0.8, 0.156);

    int sign = -1;
    JuliaTransform juliaTransform = new JuliaTransform(point, sign);
    List<Transform2D> juliaTransforms = List.of(juliaTransform);
    chaosGameClasses.ChaosGameDescription description = new chaosGameClasses.ChaosGameDescription(juliaTransforms, new Vector2D(-1.6,-1), new Vector2D(1.6, 1));
    Vector2D startingPoint = new Vector2D(0, 0);
    chaosGameClasses.ChaosGame app = new chaosGameClasses.ChaosGame(description, 70, 140, startingPoint);
    app.runSteps(1000000);

     */

  }
}
