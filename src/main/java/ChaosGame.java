import java.util.Random;
import transformations.AffineTransform2D;
import vectors.Vector2D;
public class ChaosGame {
  private ChaosCanvas canvas;
  private ChaosGameDescription description;
  private Vector2D currentPoint;
  private Random random;

  public ChaosGame(ChaosGameDescription description, int width, int height) {
    setDescription(description);
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
      this.currentPoint = description.getTransforms().next().transform(currentPoint);
      canvas.putPixel(currentPoint);
    }
  }
}
