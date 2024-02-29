import java.util.Iterator;
import java.util.List;
import transformations.Transform2D;
import vectors.Vector2D;

public class ChaosGameDescription {
  private Vector2D minCoords;
  private Vector2D maxCoords;
  private List<Transform2D> transforms;

  public ChaosGameDescription(List<Transform2D> transforms, Vector2D minCoords,
      Vector2D maxCoords) {
    setMaxCoords(maxCoords);
    setMinCoords(minCoords);
    setTransforms(transforms);
  }

  public Vector2D getMinCoords() {
    return minCoords;
  }

  public Vector2D getMaxCoords() {
    return maxCoords;
  }

  public List<Transform2D> getTransforms() {
    return transforms;
  }

  public void setMaxCoords(Vector2D maxCoords) {
      this.maxCoords = maxCoords;

  }
  public void setMinCoords(Vector2D minCoords) {
      this.minCoords = minCoords;

  }
  public void setTransforms(List<Transform2D> transforms) {
    this.transforms = transforms;
  }
  public void addTransforms(Transform2D transform) {
    this.transforms.add(transform);
  }
}
