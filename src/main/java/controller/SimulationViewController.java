package controller;

import gui.SimulationView;
import javafx.beans.value.ObservableValue;
import javafx.scene.Cursor;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class SimulationViewController {

  private final SimulationView simulationView;

  private double prevMouseX;
  private double prevMouseY;
  private boolean isScalingEnabled;
  private boolean isAltPressed;

  private final double MIN_SCALE_X = 0.1;
  private final double MAX_SCALE_X = 1.3;
  private final double MIN_SCALE_Y = 0.1;
  private final double MAX_SCALE_Y = 1.5;

  public SimulationViewController(SimulationView simulationView) {
    this.simulationView = simulationView;
    setListeners();
  }

  private void setListeners() {
    simulationView.setOnScroll(this::handleScroll);
    simulationView.setOnMousePressed(this::handleMousePressed);
    simulationView.setOnMouseDragged(this::handleMouseDragged);
    simulationView.setOnMouseReleased(this::handleMouseReleased);
    simulationView.setOnKeyPressed(this::handleKeyPressed);
    simulationView.setOnKeyReleased(this::handleKeyReleased);

    simulationView.widthProperty().addListener(this::handleSizeChange);
    simulationView.heightProperty().addListener(this::handleSizeChange);
  }

  private void handleScroll(ScrollEvent event) {
    double scaleFactor = 1.05;
    if (event.getDeltaY() < 0) {
      scaleFactor = 1 / scaleFactor;
    }
    double newScaleX = simulationView.getScaleTransform().getX() * scaleFactor;
    double newScaleY = simulationView.getScaleTransform().getY() * scaleFactor;
    if (newScaleX >= MIN_SCALE_X && newScaleX <= MAX_SCALE_X &&
        newScaleY >= MIN_SCALE_Y && newScaleY <= MAX_SCALE_Y) {
      simulationView.getScaleTransform().setX(newScaleX);
      simulationView.getScaleTransform().setY(newScaleY);
    }
    event.consume();
  }

  private void handleMousePressed(MouseEvent event) {
    if (event.isAltDown()) {
      isScalingEnabled = true;
      prevMouseX = event.getX();
      prevMouseY = event.getY();
    }
  }

  private void handleMouseDragged(MouseEvent event) {
    if (isScalingEnabled) {
      double deltaX = event.getX() - prevMouseX;
      double deltaY = event.getY() - prevMouseY;

      double scaleX = simulationView.getScaleTransform().getX();
      double scaleY = simulationView.getScaleTransform().getY();

      double newScaleX = scaleX + deltaX / 100;
      double newScaleY = scaleY + deltaY / 100;

      if (newScaleX >= MIN_SCALE_X && newScaleX <= MAX_SCALE_X &&
          newScaleY >= MIN_SCALE_Y && newScaleY <= MAX_SCALE_Y) {
        simulationView.getScaleTransform().setX(newScaleX);
        simulationView.getScaleTransform().setY(newScaleY);
      }

      prevMouseX = event.getX();
      prevMouseY = event.getY();
    }
  }

  private void handleMouseReleased(MouseEvent event) {
    isScalingEnabled = false;
  }

  private void handleKeyPressed(KeyEvent event) {
    if (event.getCode().equals(KeyCode.ALT)) {
      isAltPressed = true;
      simulationView.setCursor(Cursor.OPEN_HAND);
    }
  }

  private void handleKeyReleased(KeyEvent event) {
    if (event.getCode().equals(KeyCode.ALT)) {
      isAltPressed = false;
      simulationView.setCursor(Cursor.DEFAULT);
    }
  }

  /**
   * Handles the scaling of the canvas based on the size change of the SimulationView.
   *
   * @param observable The ObservableValue being observed (in this case, the width or height property)
   * @param oldValue   The old value of the property
   * @param newValue   The new value of the property
   */
  private void handleSizeChange(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
    double newScaleX = simulationView.getWidth() / simulationView.getSTARTING_WIDTH();
    double newScaleY = simulationView.getHeight() / simulationView.getSTARTING_HEIGHT();

    // Ensure new scales are within allowed limits
    newScaleX = Math.max(MIN_SCALE_X, Math.min(MAX_SCALE_X, newScaleX));
    newScaleY = Math.max(MIN_SCALE_Y, Math.min(MAX_SCALE_Y, newScaleY));

    simulationView.getScaleTransform().setX(newScaleX);
    simulationView.getScaleTransform().setY(newScaleY);
  }

}
