package chaosgameclasses;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import controller.ChaosGameObserver;
import controller.Observer;
import javafx.scene.control.TextField;
import matrix.Matrix2x2;
import transformations.AffineTransform2D;
import transformations.JuliaTransform;
import transformations.Transform2D;
import utilities.Printer;
import utilities.UserInput;
import vectors.Complex;
import vectors.Vector2D;

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
    List<String> values = fileHandler.readFromFile(path);
    this.typeOfTransformation = values.get(0);
    if (this.typeOfTransformation.equals("Julia")) {
      setCanvasCoordsFromFile(values);
      setTransformsFromFileJulia(values);
    } else {
      setCanvasCoordsFromFile(values);
      setTransformsFromFileAffine(values);
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
    String[] minCoords = values.get(1).split(",");
    String[] maxCoords = values.get(2).split(",");
    setMinCoords(new Vector2D(Double.parseDouble(minCoords[0]), Double.parseDouble(minCoords[1])));
    setMaxCoords(new Vector2D(Double.parseDouble(maxCoords[0]), Double.parseDouble(maxCoords[1])));
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
    List<Transform2D> transformations = new java.util.ArrayList<>(List.of());
    List<Matrix2x2> matrix2x2List = new java.util.ArrayList<>(List.of());
    List<Vector2D> vector2DList = new java.util.ArrayList<>(List.of());
    this.numberOfTransforms = values.size() - 3;
    for (int i = 3; i < values.size(); i++) {
      String[] value = values.get(i).split(",");
      Matrix2x2 matrix = new Matrix2x2(Double.parseDouble(value[0]), Double.parseDouble(value[1]),
              Double.parseDouble(value[2]), Double.parseDouble(value[3]));
      Vector2D vector = new Vector2D(Double.parseDouble(value[4]), Double.parseDouble(value[5]));
      transformations.add(new AffineTransform2D(matrix, vector));
      matrix2x2List.add(matrix);
      vector2DList.add(vector);
    }
    setTransforms(transformations);
    setMatrixList(matrix2x2List);
    setVectorList(vector2DList);
  }

  public void setMatrixList(List<Matrix2x2> matrixList) {
    this.matrixList = matrixList;
  }

  public void setVectorList(List<Vector2D> vectorList) {
    this.vectorList = vectorList;
  }

  public List<Matrix2x2> getMatrixList() {
    return matrixList;
  }

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

  public void writeToFile(String typeOfTransform, String choiceString, List<TextField> values){
    boolean isJulia = typeOfTransform.equals("Julia");
    if (isJulia) {
      List<String> juliaValues = List.of(values.get(0).getText(), values.get(1).getText());


      String row = choiceString.split(" ")[1];
      ChaosGameFileHandler.changeLine(this.path, juliaValues, Integer.parseInt(row) + 2);
    } else {
      boolean isMatrix = choiceString.split(" ")[0].equals("Matrix");
      String row = choiceString.split(" ")[1];

      if (isMatrix) {
        List<String> matrixValues = values.stream()
                .map(TextField::getText)
                .collect(Collectors.toList());

        ChaosGameFileHandler.changeLine(this.path, matrixValues, Integer.parseInt(row) + 2);
      } else {
        List<String> vectorValues = List.of(values.get(0).getText(), values.get(1).getText());

        ChaosGameFileHandler.changeLine(this.path, vectorValues, Integer.parseInt(row) + 2);
      }
    }
  }
  public String getTypeOfTransformation() {
    return this.typeOfTransformation;
  }

  /**
   * Class responsible for running the chaos game.
   *
   * @version 1.0
   */
  public static class ChaosGame {

    private ChaosCanvas canvas;
    private ChaosGameDescription description;
    private Vector2D currentPoint;
    private final Random random;
    private final UserInput userInput = new UserInput();
    private boolean closeApp = false;
    private final Printer printer = new Printer();
    private List<Observer> observers = new ArrayList<>();


    /**
     * Constructor for the chaosGameClasses.ChaosGame class.
     *
     * @param description  The description of the chaos game
     * @param width        The width of the canvas
     * @param height       The height of the canvas
     * @param currentPoint The starting point & the current point for the chaos game (will be updated
     *                     as the game iterates)
     */
    public ChaosGame(ChaosGameDescription description, int height, int width, Vector2D currentPoint) {
      setDescription(description);
      this.random = new Random();
      this.currentPoint = currentPoint;
      setCanvas(new ChaosCanvas(height, width,
              description.getMinCoords(), description.getMaxCoords()));
    }

    /**
     * Runs the chaos game. Uses a switch case.
     */
    public void runGame() {
      while (!closeApp) {
        printer.printMainMenu(description.getPath());

        String input = userInput.getInput();
        switch (input) {
          case "1" -> runChaosGame();
          case "2" -> changePath();
          case "3" -> closeApp = true;
          default -> printer.errorMessage();
        }
      }
    }

    /**
     * Runs the chaos game for a given number of steps.
     */
    public void runChaosGame() {
      canvas.clear();
      printer.printGetNrOfSteps();
      runSteps(userInput.getNrOfSteps());
    }

    /**
     * Changes the path of the chaos game description.
     */
    public void changePath() {
      printer.transformationChanging();
      String input = userInput.getInput();
      switch (input) {
        case "1" -> {
          this.description = new ChaosGameDescription("src/resources/sierpinskiTriangle.txt");
          this.canvas = new ChaosCanvas(canvas.getHeight(), canvas.getWidth(),
                  description.getMinCoords(), description.getMaxCoords());
        }
        case "2" -> {
          this.description = new ChaosGameDescription("src/resources/barnsleyTransform.txt");
          this.canvas = new ChaosCanvas(canvas.getHeight(), canvas.getWidth(),
                  description.getMinCoords(), description.getMaxCoords());
        }
        case "3" -> {
          this.description = new ChaosGameDescription("src/resources/juliaTransform.txt");
          this.canvas = new ChaosCanvas(canvas.getHeight(), canvas.getWidth(),
                  description.getMinCoords(), description.getMaxCoords());
        }
        default -> printer.invalidPath();
      }
    }


    /**
     * Sets the description of the chaos game.
     *
     * @param description The description of the chaos game
     */
    public void setDescription(ChaosGameDescription description) {
      this.description = description;
    }

    /**
     * Gets the description of the chaos game. Used for test classes
     *
     * @return The description of the chaos game
     */
    public ChaosGameDescription getDescription() {
      return this.description;
    }

    /**
     * Gets the current point of the chaos game. Used for test classes
     *
     * @return The current point of the chaos game
     */
    public Vector2D getCurrentPoint() {
      return this.currentPoint;
    }

    /**
     * Sets the canvas for the chaos game.
     *
     * @param canvas The canvas for the chaos game
     */
    public void setCanvas(ChaosCanvas canvas) {
      this.canvas = canvas;
    }

    /**
     * Gets the canvas for the chaos game.
     *
     * @return The canvas for the chaos game
     */
    public ChaosCanvas getCanvas() {
      return this.canvas;
    }

    /**
     * Runs the chaos game for a given number of steps.
     *
     * @param steps The number of steps to run the chaos game
     */
    public void runSteps(int steps) {
      if (steps <= 0) {
        printer.errorMessage();

      } else {

        this.currentPoint = new Vector2D(0.5,0.5);
        for (int i = 0; i < steps; i++) {
          int randomIndex = this.random.nextInt(description.getTransforms().size());
          Transform2D transform = description.getTransforms().get(randomIndex);
          this.currentPoint = transform.transform(this.currentPoint);
          this.canvas.putPixel(currentPoint);
        }
        this.description.handleValuesForOutprint(this.canvas.getCanvasArray());
      }
    }
    public void runStep() {
      int randomIndex = this.random.nextInt(description.getTransforms().size());
      Transform2D transform = description.getTransforms().get(randomIndex);
      this.currentPoint = transform.transform(this.currentPoint);
      this.canvas.putPixel(currentPoint);
    }
    public void addSubscriber(ChaosGameObserver observer) {
      this.observers.add(observer);
    }
    public void clearCanvas() {
      this.canvas.clear();
    }

  }
}