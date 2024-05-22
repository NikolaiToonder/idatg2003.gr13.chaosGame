package gui.view;

import modul.chaosgameclasses.ChaosGame;
import gui.controller.SimulationViewController;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import modul.math.vectors.Vector2D;

/**
 * JavaFX class for the actual simulation view. Will be used with ChaosGameView to view the chaos
 * game.
 *
 * @author Nikolai Engelsen Tønder
 */
public class SimulationView extends Pane {

  private final Canvas canvas;
  private final Scale scaleTransform;


  private final double startingHeight = 605;
  private final double startingWidth = 605;

  /**
   * Constructor of the class. Used to initialize and set up the class.
   */
  public SimulationView() {
    canvas = new Canvas(startingWidth, startingHeight);
    this.getChildren().add(canvas);

    this.setStyle("-fx-background-color: #2b2d31;");

    scaleTransform = new Scale(1, 1);
    canvas.getTransforms().add(scaleTransform);

    SimulationViewController controller = new SimulationViewController(this);
  }

  /**
   * Gets the starting height of the simulation view.
   *
   * @return The starting height of the simulation view.
   */
  public double getStartingHeight() {
    return startingHeight;
  }

  /**
   * Gets the starting width of the simulation view.
   *
   * @return The starting width of the simulation view.
   */
  public double getStartingWidth() {
    return startingWidth;
  }

  /**
   * Method called to update the simulation view. Will only use the canvas to draw the fractal
   * again. Depending on what displayHeatMap checkbox is selected, it will draw the fractal with or
   * without a heat map.
   *
   * @param chaosGame  chaosGame class to use
   * @param iterations How many iterations the user wants.
   * @param displayHeatMap If the user wants to display a heat map or not.
   */
  public void updateSimulationView(ChaosGame chaosGame, Number iterations, boolean displayHeatMap) {
    if (!displayHeatMap) {
      drawFractal(chaosGame, iterations.intValue());
    } else {
      drawFractalHeatMap(chaosGame, iterations.intValue());
    }
  }

  /**
   * Method to draw the fractals. Uses the graphics context of the canvas, and fills a pixel in at
   * the coordinate needed.
   *
   * @param chaosGame The chaosGame instance to use.
   * @param iterations The number of iterations to run.
   */
  private void drawFractal(ChaosGame chaosGame, int iterations) {
    chaosGame.getCanvas().clear();
    GraphicsContext gc = canvas.getGraphicsContext2D();

    gc.setFill(Color.WHITE);
    gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    gc.setFill(Color.BLACK);

    for (int i = 0; i < iterations; i++) {
      chaosGame.runStep();
      Vector2D point = chaosGame.getCanvas().getTransformed();
      gc.fillOval(point.getX1(), point.getX0(), 1, 1);
    }
  }

  /**
   * Method to draw the fractal with a heat map. Will run through all iterations of the program
   * so a heat map can be formed in ChaosCanvas. Then it will draw the heat map based on the values
   * of the heatMapCanvas
   *
   * @param chaosGame The chaosGame instance to use.
   * @param iterations The number of iterations to run.
   */
  private void drawFractalHeatMap(ChaosGame chaosGame, int iterations) {
    GraphicsContext gc = canvas.getGraphicsContext2D();

    chaosGame.getCanvas().clear();

    for (int i = 0; i < iterations; i++) {
      chaosGame.runStep();
    }

    gc.setFill(Color.WHITE);
    gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    gc.setFill(Color.BLACK);

    int[][] canvasHeatMap = chaosGame.getCanvas().getCanvasHeatMap();

    for (int y = 0; y < canvasHeatMap.length; y++) {
      for (int x = 0; x < canvasHeatMap[y].length; x++) {
        int intensity = canvasHeatMap[y][x];

        // Map intensity to hue in the range [0, 360] (HSB color space)
        double hue = mapIntensityToHue(intensity);

        if (intensity == 0) {
          // Skip drawing pixels with intensity 0
          continue;
        }

        // Set the color with the mapped hue
        gc.setFill(Color.hsb(hue, 1.0, 1.0));
        gc.fillOval(x, y, 1, 1);
      }
    }
  }

  /**
   * Method to map the intensity of a pixel to a hue value in the HSB color space.
   *
   * @param intensity The intensity of the pixel.
   * @return The hue value in the range [0, 360].
   */
  private double mapIntensityToHue(int intensity) {
    return 240 - (intensity * (240.0 / 5)); // Assuming intensity ranges from 0 to 25
  }


  /**
   * Method to get the scale transform of the canvas.
   *
   * @return The scale transform of the canvas.
   */
  public Scale getScaleTransform() {
    return this.scaleTransform;
  }
}
