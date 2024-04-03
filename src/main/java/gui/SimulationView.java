package gui;

import chaosgameclasses.ChaosGame;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import vectors.Vector2D;

public class SimulationView extends Pane {
  private Canvas canvas;

  public SimulationView() {
    canvas = new Canvas(600, 400); // Set initial size
    this.getChildren().add(canvas); // Add canvas to pane
  }

  public void updateSimulationView(ChaosGame chaosGame, Number iterations) {
    // Placeholder for method to update fractal based on new parameters
    drawInitialFractal(chaosGame, iterations.intValue());
  }

  private void drawInitialFractal(ChaosGame chaosGame, int iterations) {
    GraphicsContext gc = canvas.getGraphicsContext2D();
    // Clear previous drawing
    gc.setFill(Color.LIGHTGRAY); // Set background color
    gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

    // Initialize chaosGame with vertices of an equilateral triangle
    // ...

    Vector2D point = chaosGame.getCurrentPoint(); // Random initial point within the triangle
    gc.setFill(Color.BLACK);
    for (int i = 0; i < iterations; i++) {
      chaosGame.runStep(); // This should move the point halfway towards a randomly chosen vertex
      point = chaosGame.getCanvas().getTransformed(); // Get the new point
      gc.fillOval(point.getX0(), point.getX1(), 1, 1); // Draw a small dot at the new point
    }
  }


  // Resize canvas with SimulationView
  @Override
  protected void layoutChildren() {
    super.layoutChildren();
    canvas.setWidth(this.getWidth());
    canvas.setHeight(this.getHeight());
  }
}
