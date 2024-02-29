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

  public Iterator<Transform2D> getTransforms() {
    return transforms.iterator();
  }

  public void setMaxCoords(Vector2D maxCoords) {
    if (maxCoords.getX0() > 0 || maxCoords.getX1() > 0) {
      this.maxCoords = maxCoords;
    }
  }
  public void setMinCoords(Vector2D minCoords) {
    if (minCoords.getX0() > 0 || minCoords.getX1() > 0) {
      this.minCoords = minCoords;
    }
  }
  public void setTransforms(List<Transform2D> transforms) {
    this.transforms = transforms;
  }
  public void addTransforms(Transform2D transform) {
    this.transforms.add(transform);
  }
}
