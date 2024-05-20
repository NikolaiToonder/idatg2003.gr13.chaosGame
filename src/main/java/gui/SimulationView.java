package gui;

import chaosgameclasses.ChaosGame;
import controller.SimulationViewController;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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

  /**
   * Constructor of the class. Used to initialize and set up the class.
   */
  public SimulationView() {
    canvas = new Canvas(550, 650);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    gc.setFill(Color.rgb(43, 45, 49));
    gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

    this.getChildren().add(canvas);
    this.setPrefSize(550, 650);

    scaleTransform = new Scale(1, 1);
    canvas.getTransforms().add(scaleTransform);

    controller = new SimulationViewController(this);
  }

  /**
   * Method called to update the simulation view. Will only use the canvas to draw the fractal
   * again.
   *
   * @param chaosGame  chaosGame class to use
   * @param iterations How many iterations the user wants.
   */
  public void updateSimulationView(ChaosGame chaosGame, Number iterations) {
    drawFractal(chaosGame, iterations.intValue());
  }

  /**
   * Method to draw the fractals. Uses the graphics context of the canvas, and fills a pixel in at
   * the coordinate needed.
   *
   * @param chaosGame
   * @param iterations
   */
  private void drawFractal(ChaosGame chaosGame, int iterations) {
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

  public Scale getScaleTransform(){
    return this.scaleTransform;
  }
}
