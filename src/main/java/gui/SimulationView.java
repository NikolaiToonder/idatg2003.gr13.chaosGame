package gui;

import chaosgameclasses.ChaosGame;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import vectors.Vector2D;


/**
 * JavaFX class for the actual simulation view. Will be used with ChaosGameView to view the
 * chaos game.
 */
public class SimulationView extends Pane {
  private final Canvas canvas;

  /**
   * Constructor of the class. Used to initialize and set up the class.
   *
   */
  public SimulationView() {
    canvas = new Canvas(550, 650);
    this.getChildren().add(canvas);

    this.setStyle("-fx-background-color: #2b2d31;");

    this.setPadding(new Insets(10,10,10,10));

    StackPane.setAlignment(canvas, Pos.CENTER);

  }


  /**
   * Method called to update the simulation view.
   * Will only use the canvas to draw the fractal again.
   * @param chaosGame chaosGame class to use
   * @param iterations How many iterations the user wants.
   */
  public void updateSimulationView(ChaosGame chaosGame, Number iterations) {
    drawFractal(chaosGame, iterations.intValue());
  }

  /**
   * Method to draw the fractals. Uses the graphicscontext of the canvas, and fills
   * a pixel in at the coordinate needed.
   * @param chaosGame
   * @param iterations
   */
  private void drawFractal(ChaosGame chaosGame, int iterations) {
    GraphicsContext gc = canvas.getGraphicsContext2D();

    gc.setFill(Paint.valueOf("#2b2d31"));
    gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    Vector2D point; // Random initial point within the triangle
    int[][] heatMap;
    gc.setFill(Color.WHITE);


    for (int i = 0; i < iterations; i++) {
      chaosGame.runStep();
      point = chaosGame.getCanvas().getTransformed();
      gc.fillOval(point.getX1(), point.getX0(), 1, 1);
    }


    /*
    heatMap = chaosGame.getCanvas().getCanvasHeatMap();
    for (int i = 0; i < heatMap.length; i++) {
      for (int j = 0; j < heatMap[i].length; j++) {
        if (heatMap[i][j] > 0) {
          // Normalize the intensity value between 0 and 1
          double normalizedIntensity = (double) heatMap[i][j] / 100;
          // Set hue value based on the normalized intensity
          double hue = normalizedIntensity * 360; // Hues range from 0 to 360 degrees
          System.out.println(hue);

          // Set saturation and brightness to 100% to ensure full color saturation
          double saturation = 1.0;
          double brightness = 1.0;

          // Convert HSB values to RGB color
          Color color = Color.hsb(hue, saturation, brightness);

          // Set the fill color and draw the pixel
          gc.setFill(color);
          gc.fillRect(i, j, 1, 1);
        }

     */
  }
}