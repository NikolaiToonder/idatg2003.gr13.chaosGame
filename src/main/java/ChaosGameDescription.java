import java.util.List;
import transformations.Transform2D;
import vectors.Vector2D;

/**
 * Class representing the description of a chaos game. The description includes the transforms to be
 * used and the bounds of the canvas. Fields of class may be changed by ChaosGameFileHandler class.
 *
 * @version 1.0
 */
public class ChaosGameDescription {

  private Vector2D minCoords;
  private Vector2D maxCoords;
  private List<Transform2D> transforms;

  /**
   * Constructor for the ChaosGameDescription class.
   *
   * @param transforms The list of transforms to be used
   * @param minCoords  The minimum coordinates of the canvas
   * @param maxCoords  The maximum coordinates of the canvas
   */
  public ChaosGameDescription(List<Transform2D> transforms, Vector2D minCoords,
      Vector2D maxCoords) {
    setMaxCoords(maxCoords);
    setMinCoords(minCoords);
    setTransforms(transforms);
  }

  public ChaosGameDescription(String path) {
    ChaosGameFileHandler fileHandler = new ChaosGameFileHandler();
    List<String> values = fileHandler.readFromFile(path);
    values = setCanvasCoordsFromFile(values);
    System.out.println(values.get(0));
  }

  public List<String> setCanvasCoordsFromFile(List<String> values) {
    String[] minCoords = values.get(1).split(",");
    String[] maxCoords = values.get(2).split(",");
    setMinCoords(new Vector2D(Double.parseDouble(minCoords[0]), Double.parseDouble(minCoords[1])));
    setMaxCoords(new Vector2D(Double.parseDouble(maxCoords[0]), Double.parseDouble(maxCoords[1])));
    return values;
  }

  /**
   * Gets the minimum coordinates of the canvas.
   *
   * @return The minimum coordinates of the canvas
   */
  public Vector2D getMinCoords() {
    return minCoords;
  }

  /**
   * Gets the maximum coordinates of the canvas.
   *
   * @return The maximum coordinates of the canvas
   */
  public Vector2D getMaxCoords() {
    return maxCoords;
  }

  /**
   * Gets the list of transforms to be used.
   *
   * @return The list of transforms to be used
   */
  public List<Transform2D> getTransforms() {
    return transforms;
  }

  /**
   * Sets the minimum coordinates of the canvas.
   *
   * @param maxCoords The minimum coordinates of the canvas
   */
  public void setMaxCoords(Vector2D maxCoords) {
    this.maxCoords = maxCoords;
  }

  /**
   * Sets the maximum coordinates of the canvas.
   *
   * @param minCoords The maximum coordinates of the canvas
   */
  public void setMinCoords(Vector2D minCoords) {
    this.minCoords = minCoords;
  }

  /**
   * Sets the list of transforms to be used.
   *
   * @param transforms The list of transforms to be used
   */
  public void setTransforms(List<Transform2D> transforms) {
    this.transforms = transforms;
  }

  /**
   * Adds a transform to the list of transforms to be used.
   *
   * @param transform The transform to add
   */
  public void addTransforms(Transform2D transform) {
    this.transforms.add(transform);
  }
}
