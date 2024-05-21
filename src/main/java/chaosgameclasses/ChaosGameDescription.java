package chaosgameclasses;

import java.util.ArrayList;
import java.util.List;
import math.matrix.Matrix2x2;
import math.transformations.AffineTransform2D;
import math.transformations.JuliaTransform;
import math.transformations.Transform2D;
import math.vectors.Complex;
import math.vectors.Vector2D;

/**
 * Class representing the description of a chaos game. The description includes the transforms to be
 * used and the bounds of the canvas. Fields of class may be changed by
 * chaosGameClasses.ChaosGameFileHandler class.
 *
 * @version 1.0
 */
public class ChaosGameDescription {

  private Vector2D minCoords;
  private Vector2D maxCoords;
  private List<Transform2D> transforms;
  private String path;
  private int numberOfTransforms;
  private List<Matrix2x2> matrixList;
  private List<Vector2D> vectorList;
  private String typeOfTransformation;
  private List<Complex> complexNumbers;
  private int sign;

  private boolean isBarnsley;

  /**
   * Constructs a chaosGameClasses.ChaosGameDescription object with the provided list of transforms
   * and canvas coordinates.
   *
   * @param transforms The list of transforms to be applied.
   * @param minCoords  The minimum coordinates of the canvas.
   * @param maxCoords  The maximum coordinates of the canvas.
   */
  public ChaosGameDescription(List<Transform2D> transforms, Vector2D minCoords,
                              Vector2D maxCoords) {
    setMaxCoords(maxCoords);
    setMinCoords(minCoords);
    setTransforms(transforms);
  }

  /**
   * Constructs a chaosGameClasses.ChaosGameDescription object by reading description from a file.
   * The file should contain information about canvas coordinates and transforms.
   *
   * @param path The path to the file containing the description.
   */

  public ChaosGameDescription(String path) {
    ChaosGameFileHandler fileHandler = new ChaosGameFileHandler();
    this.path = path;
    try {
      List<String> values = fileHandler.readFromFile(path);
      this.typeOfTransformation = values.get(0);
      if (this.typeOfTransformation.equals("Julia")) {
        setCanvasCoordsFromFile(values);
        setTransformsFromFileJulia(values);
      } else {
        setCanvasCoordsFromFile(values);
        setTransformsFromFileAffine(values);
      }
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException();
    }

  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }
  public int getNumberOfTransforms() {
    return numberOfTransforms;
  }

  /**
   * Sets the minimum coordinates of the canvas.
   *
   * @param values The values to set the minimum coordinates of the canvas
   */
  public void setCanvasCoordsFromFile(List<String> values) {
    try {
      String[] minCoords = values.get(1).split(",");
      String[] maxCoords = values.get(2).split(",");
      setMinCoords(new Vector2D(Double.parseDouble(minCoords[0]), Double.parseDouble(minCoords[1])));
      setMaxCoords(new Vector2D(Double.parseDouble(maxCoords[0]), Double.parseDouble(maxCoords[1])));
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Error parsing canvas coordinates: " + e.getMessage(), e);
    }
  }


  /**
   * Sets the transforms to be used from a file. The file should contain information about the
   * transforms. The first three lines of the file should contain the canvas coordinates. The rest
   * of the lines should contain the information about the transforms. Each line representing a
   * transform should be formatted as follows: "a,b,c,d,e,f", where: - a, b, c, d are the elements
   * of the 2x2 transformation matrix. - e, f are the translation values.
   *
   * @param values The values from the file to set the transforms to be used.
   */
  public void setTransformsFromFileAffine(List<String> values) {
    try {
      List<Transform2D> transformations = new ArrayList<>();
      List<Matrix2x2> matrix2x2List = new ArrayList<>();
      List<Vector2D> vector2DList = new ArrayList<>();
      this.numberOfTransforms = values.size() - 3;
      for (int i = 3; i < values.size(); i++) {
        String[] value = values.get(i).split(",");
        if (value.length > 0) {
          Matrix2x2 matrix = new Matrix2x2(Double.parseDouble(value[0]), Double.parseDouble(value[1]),
              Double.parseDouble(value[2]), Double.parseDouble(value[3]));
          Vector2D vector = new Vector2D(Double.parseDouble(value[4]), Double.parseDouble(value[5]));
          transformations.add(new AffineTransform2D(matrix, vector));
          matrix2x2List.add(matrix);
          vector2DList.add(vector);
        }
      }
      setTransforms(transformations);
      setMatrixList(matrix2x2List);
      setVectorList(vector2DList);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Error parsing transform values: " + e.getMessage(), e);
    }
  }

  /**
   * Setter for the matrixList field.
   * @param matrixList list of matrix2x2
   */
  public void setMatrixList(List<Matrix2x2> matrixList) {
    this.matrixList = matrixList;
  }

  /**
   * Setter for the vectorList field.
   * @param vectorList list of vector2d
   */
  public void setVectorList(List<Vector2D> vectorList) {
    this.vectorList = vectorList;
  }

  /**
   * Getter for the matrixList field
   * @return matrixList, a list of matricies
   */
  public List<Matrix2x2> getMatrixList() {
    return matrixList;
  }

  /**
   * Getter for the vectorList field.
   * @return vectorList, a list of vectors.
   */
  public List<Vector2D> getVectorList() {
    return vectorList;
  }

  /**
   * Sets the transforms to be used from a file for generating Julia sets. The file should contain
   * information about the transforms. The first three lines of the file should contain the canvas
   * coordinates. The fourth line should contain the complex point for the Julia set generation. The
   * rest of the lines should contain additional information about the transforms, if any.
   *
   * @param values The values from the file to set the transforms to be used.
   */
  public void setTransformsFromFileJulia(List<String> values) {
    String[] value = values.get(3).split(",");
    this.numberOfTransforms = 1;
    Complex point = new Complex(Double.parseDouble(value[0]), Double.parseDouble(value[1]));
    int sign = 1;
    if (point.getImaginary() < 0) {
      sign = -1;
    }

    List<Transform2D> transformations = List.of(new JuliaTransform(point, sign), new JuliaTransform(point, -sign));
    this.complexNumbers = List.of(point);
    setTransforms(transformations);

  }

  /**
   * Setter for the typeOfTransformation field.
   * @param typeOfTransformation type of transformation the user wants.
   *                             Can either be Affine2D or Complex
   */
  public void setTypeOfTransformation(String typeOfTransformation) {
    this.typeOfTransformation = typeOfTransformation;
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
   * Returns the sign of the complex fractal.
   * @return
   */
  public int getSign() {
    return sign;
  }

  public List<Complex> getComplexNumbers() {
    return this.complexNumbers;
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

  /**
   * Sets the path to the file containing the description.
   *
   * @param values The values to set the path to the file containing the description
   */
  public void handleValuesForOutprint(int[][] values) {
    ChaosGameFileHandler.writeToFile(values);
  }

  /**
   * Method to handle values from a list of textFields, and then pass it onto a fileWriter class.
   * @param typeOfTransform What transformation the user wants.
   * @param choiceString String of what line the user wants to edit,
   *                    and if it is a vector or matrix.
   * @param values all textFields in the program.
   */
  public void writeToFile(String typeOfTransform, String choiceString, boolean isMatrix, List<String> values){
    boolean isJulia = typeOfTransform.equals("Julia");
    System.out.println("isJulia: " + isJulia);
    if (isJulia) {
      List<String> juliaValues = List.of(values.get(0), values.get(1));

      String row = choiceString.split(" ")[1];
      ChaosGameFileHandler.changeLine(this.path, juliaValues, Integer.parseInt(row) + 2);
    } else {
      String row = choiceString.split(" ")[1];

      if (!isMatrix) {
        ChaosGameFileHandler.changeLine(this.path, values, Integer.parseInt(row) + 2);
      } else {
        List<String> vectorValues = List.of(values.get(0), values.get(1));

        ChaosGameFileHandler.changeLine(this.path, vectorValues, Integer.parseInt(row) + 2);
      }
    }
  }

  /**
   * Getter for the typeOfTransformation field.
   * @return typeOfTransformation String
   */
  public String getTypeOfTransformation() {
    return this.typeOfTransformation;
  }

  /**
   * Used to reset the fractal chosen into a standard template included in the program.
   *
   */
  public void resetFractals(){
    ChaosGameFileHandler.resetFractals(this.path);
  }

  /**
   * If the user wishes to create a custom fractal, this is the method the program uses to write
   * the users chosen values to a file. Will take in a list of strings, where the lines corresponds
   * to what you would see in the txt files containing the templates.
   * @param values values the program wants to handle.
   */
  public void writeToFileCustom(List<String> values){
   ChaosGameFileHandler.writeCustomFractal(values);
  }
  public boolean getIsBarnsley(){
    return isBarnsley;
  }

  public void setIsBarnsley(boolean barnsley) {
    isBarnsley = barnsley;
  }
}