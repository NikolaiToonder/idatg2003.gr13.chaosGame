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
  private Canvas canvas;
  private GraphicsContext gc;
  private double currentHeight;
  private double currentWidth;
  private boolean isDarkMode;

  public SimulationView(boolean isDarkMode) {
    // Initialize the canvas with square dimensions
    canvas = new Canvas(550, 650); // Set initial size to a square
    this.getChildren().add(canvas); // Add canvas to pane



    if (isDarkMode) {
      this.setStyle("-fx-background-color: #2b2d31;");
    } else {
      this.setStyle("-fx-background-color: #f0f0f0;");
    }

    // Ensure the SimulationView itself is prepared to maintain a square shape
    // Here, we simply start with a square configuration, but see Step 1 for dynamic resizing
    // Center the canvas within the pane; useful if the SimulationView's size changes but maintains square shape
    StackPane.setAlignment(canvas, Pos.CENTER);

    // Set initial current width and height based on the canvas size; these are kept for potential future use
    this.currentHeight = canvas.getHeight();
    this.currentWidth = canvas.getWidth();


  }

  public void updateSimulationView(ChaosGame chaosGame, Number iterations) {
    drawFractal(chaosGame, iterations.intValue());
  }

  private void drawFractal(ChaosGame chaosGame, int iterations) {
    gc = canvas.getGraphicsContext2D();


    if (isDarkMode) {
      gc.setFill(Paint.valueOf("#2b2d31"));
    } else {
      gc.setFill(Paint.valueOf("#f4f4f4"));
    }


    gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    Vector2D point; // Random initial point within the triangle
    if (isDarkMode) {
      gc.setFill(Color.WHITE);
    } else {
      gc.setFill(Color.BLACK);
    }

    for (int i = 0; i < iterations; i++) {
      chaosGame.runStep();
      point = chaosGame.getCanvas().getTransformed();
      gc.fillOval(point.getX1(), point.getX0(), 1, 1);
    }
  }

  public Canvas getCanvas() {
    return canvas;
  }

  public GraphicsContext getGraphicsContext() {
    return gc;
  }
}