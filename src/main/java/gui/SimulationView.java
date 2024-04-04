package gui;

import chaosgameclasses.ChaosGame;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import vectors.Vector2D;

public class SimulationView extends Pane {
  private Canvas canvas;
  private GraphicsContext gc;
  private double currentHeight;
  private double currentWidth;

  public SimulationView() {
    // Initialize the canvas with square dimensions
    canvas = new Canvas(500,500); // Set initial size to a square
    this.getChildren().add(canvas); // Add canvas to pane

    // Apply styles to the SimulationView for border and background
    this.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: white;");

    // Ensure the SimulationView itself is prepared to maintain a square shape
    // Here, we simply start with a square configuration, but see Step 1 for dynamic resizing
    // Center the canvas within the pane; useful if the SimulationView's size changes but maintains square shape
    StackPane.setAlignment(canvas, Pos.CENTER);

    // Set initial current width and height based on the canvas size; these are kept for potential future use
    this.currentHeight = canvas.getHeight();
    this.currentWidth = canvas.getWidth();
  }


  public void updateSimulationView(ChaosGame chaosGame, Number iterations) {
    System.out.println(chaosGame.getCanvas().getHeight());
    drawFractal(chaosGame, iterations.intValue());
  }

  private void drawFractal(ChaosGame chaosGame, int iterations) {
    gc = canvas.getGraphicsContext2D();

    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // Clear the canvas
    Vector2D point; // Random initial point within the triangle
    gc.setFill(Color.BLACK);

    for (int i = 0; i < iterations; i++) {
      chaosGame.runStep();
      point = chaosGame.getCanvas().getTransformed();
      gc.fillOval(point.getX0(), point.getX1(), 1, 1);
    }
  }

  public Canvas getCanvas() {
    return canvas;
  }

  public GraphicsContext getGraphicsContext() {
    return gc;
  }


}
