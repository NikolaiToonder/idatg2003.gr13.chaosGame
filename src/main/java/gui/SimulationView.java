package gui;

import chaosgameclasses.ChaosGame;
import controller.SimulationViewController;
import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import vectors.Vector2D;

/**
 * JavaFX class for the actual simulation view. Will be used with ChaosGameView to view the
 * chaos game.
 */
public class SimulationView extends Pane {

  private final Canvas canvas;
  private final Scale scaleTransform;
  private final SimulationViewController controller;

  private final double STARTING_HEIGHT = 605;
  private final double STARTING_WIDTH = 605;

  /**
   * Constructor of the class. Used to initialize and set up the class.
   */
  public SimulationView() {
    canvas = new Canvas(STARTING_WIDTH, STARTING_HEIGHT);
    this.getChildren().add(canvas);

    this.setStyle("-fx-background-color: #2b2d31;");

    scaleTransform = new Scale(1, 1);
    canvas.getTransforms().add(scaleTransform);


    controller = new SimulationViewController(this);
  }

  public double getSTARTING_HEIGHT() {
    return STARTING_HEIGHT;
  }
  public double getSTARTING_WIDTH() {
    return STARTING_WIDTH;
  }

  /**
   * Method called to update the simulation view. Will only use the canvas to draw the fractal
   * again.
   *
   * @param chaosGame  chaosGame class to use
   * @param iterations How many iterations the user wants.
   */
  public void updateSimulationView(ChaosGame chaosGame, Number iterations, boolean heatMap) {
    if(!heatMap)
      drawFractal(chaosGame, iterations.intValue());
    else
      drawFractalHeatMap(chaosGame, iterations.intValue());
  }

  /**
   * Method to draw the fractals. Uses the graphics context of the canvas, and fills a pixel in at
   * the coordinate needed.
   *
   * @param chaosGame
   * @param iterations
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

  private double mapIntensityToHue(int intensity) {
    return 240 - (intensity * (240.0 / 5)); // Assuming intensity ranges from 0 to 25
  }


  public Scale getScaleTransform(){
    return this.scaleTransform;
  }
}
