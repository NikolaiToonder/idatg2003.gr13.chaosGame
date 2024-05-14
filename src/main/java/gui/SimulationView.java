package gui;

import chaosgameclasses.ChaosGame;
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

  public SimulationView() {
    // Initialize the canvas with square dimensions
    canvas = new Canvas(550, 650); // Set initial size to a square
    this.getChildren().add(canvas); // Add canvas to pane

    // Apply styles to the SimulationView for border and background
    this.setStyle("-fx-background-color: #2b2d31;");

    // Ensure the SimulationView itself is prepared to maintain a square shape
    // Here, we simply start with a square configuration, but see Step 1 for dynamic resizing
    // Center the canvas within the pane; useful if the SimulationView's size changes but maintains square shape
    StackPane.setAlignment(canvas, Pos.CENTER);

    // Set initial current width and height based on the canvas size; these are kept for potential future use
    //double currentHeight = canvas.getHeight();
   // double currentWidth = canvas.getWidth();
  }


  public void updateSimulationView(ChaosGame chaosGame, Number iterations) {
    drawFractal(chaosGame, iterations.intValue());
  }

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