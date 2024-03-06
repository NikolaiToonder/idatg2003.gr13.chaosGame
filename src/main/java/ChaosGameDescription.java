import java.util.List;
import matrix.Matrix2x2;
import transformations.AffineTransform2D;
import transformations.JuliaTransform;
import transformations.Transform2D;
import vectors.Complex;
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
  private String path;

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
    this.path = path;
    List<String> values = fileHandler.readFromFile(path);
    System.out.println(values.get(3));
    if (values.get(0).equals("Julia")) {
      setCanvasCoordsFromFile(values);
      setTransformsFromFileJulia(values);
    } else {
      setCanvasCoordsFromFile(values);
      setTransformsFromFileAffine(values);
    }
  }

  public void setCanvasCoordsFromFile(List<String> values) {
    String[] minCoords = values.get(1).split(",");
    String[] maxCoords = values.get(2).split(",");
    setMinCoords(new Vector2D(Double.parseDouble(minCoords[0]), Double.parseDouble(minCoords[1])));
    setMaxCoords(new Vector2D(Double.parseDouble(maxCoords[0]), Double.parseDouble(maxCoords[1])));
  }
  public void setTransformsFromFileAffine(List<String> values) {
    List<Transform2D> transformations = new java.util.ArrayList<>(List.of());
    for (int i = 3; i < values.size(); i++) {
      String[] value = values.get(i).split(",");

      Matrix2x2 matrix = new Matrix2x2(Double.parseDouble(value[0]), Double.parseDouble(value[1]),
          Double.parseDouble(value[2]), Double.parseDouble(value[3]));
      Vector2D vector = new Vector2D(Double.parseDouble(value[4]), Double.parseDouble(value[5]));
      transformations.add(new AffineTransform2D(matrix, vector));
    }
    setTransforms(transformations);
  }

  public void setTransformsFromFileJulia(List<String> values) {
    String[] value = values.get(3).split(",");
    Complex point = new Complex(Double.parseDouble(value[0]), Double.parseDouble(value[1]));
    int sign = 1;
    if (point.getImaginary() < 0) {
      sign = -1;
    }
    List<Transform2D> transformations = List.of(new JuliaTransform(point, sign));
    setTransforms(transformations);

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
  public void handleValuesForOutprint(int[][] values) {
    ChaosGameFileHandler.writeToFile(values, this.path);
  }
}
