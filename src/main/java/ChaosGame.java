import java.util.Random;
import transformations.AffineTransform2D;
import transformations.Transform2D;
import vectors.Vector2D;
public class ChaosGame {
  private ChaosCanvas canvas;
  private ChaosGameDescription description;
  private Vector2D currentPoint;
  private Random random;

  public ChaosGame(ChaosGameDescription description, int width, int height) {
    setDescription(description);
    this.random = new Random();
    this.currentPoint = description.getMinCoords();
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
    for (int i = 0; i < steps; i++) {
      int random = this.random.nextInt(description.getTransforms().size());
      Transform2D transform = description.getTransforms().get(random);
      this.currentPoint = transform.transform(this.currentPoint);
      this.canvas.putPixel(currentPoint);
    }
    ChaosCanvas canvas = getCanvas();
    for (int i = 0; i < canvas.getWidth()-1; i++) {
      for (int j = 0; j < canvas.getHeight()-1; j++) {
        if (canvas.getPixel(new Vector2D(i, j)) == 1) {
          System.out.print("*");
        } else {
          System.out.print(" ");
        }
      }
      System.out.println();
    }
  }

}
