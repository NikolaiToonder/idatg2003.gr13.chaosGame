import java.util.Random;
import transformations.AffineTransform2D;
import transformations.Transform2D;
import vectors.Vector2D;
public class ChaosGame {
  private ChaosCanvas canvas;
  private ChaosGameDescription description;
  private Vector2D currentPoint;
  private Random random;

  public ChaosGame(ChaosGameDescription description, int width, int height, Vector2D currentPoint) {
    setDescription(description);
    this.random = new Random();
    this.currentPoint = currentPoint;
    setCanvas(new ChaosCanvas(width, height,
        description.getMinCoords(), description.getMaxCoords()));
  }
  public void setDescription(ChaosGameDescription description) {
    this.description = description;
  }
  public void setCanvas(ChaosCanvas canvas) {
    this.canvas = canvas;
  }
  public ChaosCanvas getCanvas() {
    return this.canvas;
  }
  public void runSteps(int steps) {
    // Perform the specified number of steps of the Chaos Game
    for (int i = 0; i < steps; i++) {
      // Select a random transformation
      int randomIndex = this.random.nextInt(description.getTransforms().size());
      Transform2D transform = description.getTransforms().get(randomIndex);
      // Apply the transformation to the current point
      this.currentPoint = transform.transform(this.currentPoint);
      System.out.println(this.currentPoint.getX0() + " " + this.currentPoint.getX1());
      // Plot the new point on the canvas
      this.canvas.putPixel(currentPoint);
    }

    // Print the results of the Chaos Game
    for (int i = 0; i < canvas.getWidth(); i++) {
      for (int j = 0; j < canvas.getHeight(); j++) {
        if (canvas.getPixel(i, j) == 1) {
          System.out.print("*");
        } else {
          System.out.print(" ");
        }
    }
      System.out.println();
  }

  }}
