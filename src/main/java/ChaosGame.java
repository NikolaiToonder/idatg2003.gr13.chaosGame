import java.util.Random;
import transformations.Transform2D;
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

  /**
   * Constructor for the ChaosGame class.
   *
   * @param description  The description of the chaos game
   * @param width        The width of the canvas
   * @param height       The height of the canvas
   * @param currentPoint The starting point & the current point for the chaos game (will be updated
   *                     as the game iterates)
   */
  public ChaosGame(ChaosGameDescription description, int width, int height, Vector2D currentPoint) {
    setDescription(description);
    this.random = new Random();
    this.currentPoint = currentPoint;
    setCanvas(new ChaosCanvas(width, height,
        description.getMinCoords(), description.getMaxCoords()));
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
    for (int i = 0; i < steps; i++) {
      int randomIndex = this.random.nextInt(description.getTransforms().size());
      Transform2D transform = description.getTransforms().get(randomIndex);
      this.currentPoint = transform.transform(this.currentPoint);
      System.out.println(this.currentPoint.getX0() + " " + this.currentPoint.getX1());
      this.canvas.putPixel(currentPoint);
    }
    // Print the results of the Chaos Game
    //MOVE THIS TO PRINTER CLASS
    for (int i = 0; i < canvas.getWidth() - 1; i++) {
      for (int j = 0; j < canvas.getHeight(); j++) {
        if (canvas.getPixel(new Vector2D(i,j)) == 1) {
          System.out.print("*");
        } else {
          System.out.print(" ");
        }
      }
      System.out.println();
    }
    this.description.handleValuesForOutprint(this.canvas.getCanvasArray());
  }
}
