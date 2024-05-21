package gui.controller;

import gui.view.SimulationView;
import javafx.beans.value.ObservableValue;
import javafx.scene.Cursor;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

/**
 * Controller class for the SimulationView. Handles all events that happen in simulationView.
 */
public class SimulationViewController {

  private final SimulationView simulationView;

  private double prevMouseX;
  private double prevMouseY;
  private boolean isScalingEnabled;
  private final double minScaleX = 0.1;
  private final double maxScaleX = 1.3;
  private final double minScaleY = 0.1;
  private final double maxScaleY = 1.5;

  /**
   * Constructor for the SimulationViewController class. Takes a SimulationView as a parameter.
   * Initializes the SimulationView instance and sets up the view. Then it adds all the listeners to
   * the view.
   *
   * @param simulationView The SimulationView instance.
   */
  public SimulationViewController(SimulationView simulationView) {
    this.simulationView = simulationView;
    setListeners();
  }

  /**
   * Method to add listeners to all elements that needs listeners.
   */
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

  /**
   * Method to handle the scroll event. It scales the canvas based on the scroll event.
   *
   * @param event The scroll event.
   */
  private void handleScroll(ScrollEvent event) {
    double scaleFactor = 1.05;
    if (event.getDeltaY() < 0) {
      scaleFactor = 1 / scaleFactor;
    }
    double newScaleX = simulationView.getScaleTransform().getX() * scaleFactor;
    double newScaleY = simulationView.getScaleTransform().getY() * scaleFactor;
    if (newScaleX >= minScaleX && newScaleX <= maxScaleX
        && newScaleY >= minScaleY && newScaleY <= maxScaleY) {
      simulationView.getScaleTransform().setX(newScaleX);
      simulationView.getScaleTransform().setY(newScaleY);
    }
    event.consume();
  }

  /**
   * Method to handle the mouse pressed event. It enables scaling if the alt key is pressed.
   *
   * @param event The mouse pressed event.
   */
  private void handleMousePressed(MouseEvent event) {
    if (event.isAltDown()) {
      isScalingEnabled = true;
      prevMouseX = event.getX();
      prevMouseY = event.getY();
    }
  }

  /**
   * Method to handle the mouse dragged event. It scales the canvas based on the mouse drag event.
   *
   * @param event The mouse dragged event.
   */
  private void handleMouseDragged(MouseEvent event) {
    if (isScalingEnabled) {
      double deltaX = event.getX() - prevMouseX;
      double deltaY = event.getY() - prevMouseY;

      double scaleX = simulationView.getScaleTransform().getX();
      double scaleY = simulationView.getScaleTransform().getY();

      double newScaleX = scaleX + deltaX / 100;
      double newScaleY = scaleY + deltaY / 100;

      if (newScaleX >= minScaleX && newScaleX <= maxScaleX
          && newScaleY >= minScaleY && newScaleY <= maxScaleY) {
        simulationView.getScaleTransform().setX(newScaleX);
        simulationView.getScaleTransform().setY(newScaleY);
      }

      prevMouseX = event.getX();
      prevMouseY = event.getY();
    }
  }

  /**
   * Method to handle the mouse released event. It disables scaling.
   *
   * @param event The mouse released event.
   */
  private void handleMouseReleased(MouseEvent event) {
    isScalingEnabled = false;
  }

  /**
   * Method to handle the key pressed event. It sets isAltPressed to true if the alt key is pressed.
   *
   * @param event The key pressed event.
   */
  private void handleKeyPressed(KeyEvent event) {
    if (event.getCode().equals(KeyCode.ALT)) {
      simulationView.setCursor(Cursor.OPEN_HAND);
    }
  }

  /**
   * Method to handle the key released event. It sets isAltPressed to false if the alt key is
   * released.
   *
   * @param event The key released event.
   */
  private void handleKeyReleased(KeyEvent event) {
    if (event.getCode().equals(KeyCode.ALT)) {
      simulationView.setCursor(Cursor.DEFAULT);
    }
  }

  /**
   * Handles the scaling of the canvas based on the size change of the SimulationView.
   *
   * @param observable The ObservableValue being observed (in this case, the width or height
   *                   property)
   * @param oldValue   The old value of the property
   * @param newValue   The new value of the property
   */
  private void handleSizeChange(ObservableValue<? extends Number> observable, Number oldValue,
      Number newValue) {
    double newScaleX = simulationView.getWidth() / simulationView.getStartingWidth();
    double newScaleY = simulationView.getHeight() / simulationView.getStartingHeight();

    // Ensure new scales are within allowed limits
    newScaleX = Math.max(minScaleX, Math.min(maxScaleX, newScaleX));
    newScaleY = Math.max(minScaleY, Math.min(maxScaleY, newScaleY));

    simulationView.getScaleTransform().setX(newScaleX);
    simulationView.getScaleTransform().setY(newScaleY);
  }

}
