package chaosGameClasses;

import chaosGameClasses.ChaosCanvas;
import chaosGameClasses.ChaosGameDescription;
import java.util.Random;
import transformations.Transform2D;
import utilities.Printer;
import utilities.UserInput;
import vectors.Vector2D;

/**
 * Class responsible for running the chaos game.
 *
 * @version 1.0
 */
public class ChaosGame {

  private ChaosCanvas canvas;
  private ChaosGameDescription description;
  private Vector2D currentPoint;
  private final Random random;
  private final UserInput userInput = new UserInput();
  private boolean closeApp = false;
  private final Printer printer = new Printer();
  private String folder;

  /**
   * Constructor for the chaosGameClasses.ChaosGame class.
   *
   * @param description  The description of the chaos game
   * @param width        The width of the canvas
   * @param height       The height of the canvas
   * @param currentPoint The starting point & the current point for the chaos game (will be updated
   *                     as the game iterates)
   */
  public ChaosGame(ChaosGameDescription description, int width, int height, Vector2D currentPoint, String folder) {
    setDescription(description);
    this.random = new Random();
    this.currentPoint = currentPoint;
    this.folder = folder;
    setCanvas(new ChaosCanvas(width, height,
        description.getMinCoords(), description.getMaxCoords()));
  }


  public void runGame() {
    while (!closeApp) {
      printer.printMainMenu(folder);

      String input = userInput.getInput();
      switch (input) {
        case "1" -> runChaosGame();
        case "2" -> changePath();
        case "3" -> closeApp = true;
        default -> printer.errorMessage();
      }
    }
  }

  public void runChaosGame() {
    printer.printGetNrOfSteps();
    runSteps(userInput.getNrOfSteps());
  }
  public void changePath() {
    printer.transformationChanging();
    String input = userInput.getInput();
    switch (input) {
      case "1" -> {
        this.description = new ChaosGameDescription("src/resources/sierpinskiTriangle.txt");
        this.canvas = new ChaosCanvas(canvas.getWidth(), canvas.getHeight(), description.getMinCoords(), description.getMaxCoords());
      }
      case "2" -> {
        this.description = new ChaosGameDescription("src/resources/barnsleyTransform.txt");
        this.canvas = new ChaosCanvas(canvas.getWidth(), canvas.getHeight(), description.getMinCoords(), description.getMaxCoords());
      }
      case "3" -> {
        this.description = new ChaosGameDescription("src/resources/juliaTransform.txt");
        this.canvas = new ChaosCanvas(canvas.getWidth(), canvas.getHeight(), description.getMinCoords(), description.getMaxCoords());
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
      for (int i = 0; i < steps; i++) {
        int randomIndex = this.random.nextInt(description.getTransforms().size());
        Transform2D transform = description.getTransforms().get(randomIndex);
        this.currentPoint = transform.transform(this.currentPoint);
        this.canvas.putPixel(currentPoint);
      }
      printer.printCanvasToTerminal(this.canvas);
      this.description.handleValuesForOutprint(this.canvas.getCanvasArray());
    }
  }
}
