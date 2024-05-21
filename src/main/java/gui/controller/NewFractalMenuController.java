package gui.controller;

import gui.view.NewFractalMenuView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Controller class for the NewFractalMenuView. Handles all the events in the program, and calls
 * appropriate methods.
 */
public class NewFractalMenuController {

  NewFractalMenuView newFractalMenuView;

  /**
   * Constructor for the NewFractalMenuController class. Takes a NewFractalMenuView as a parameter.
   * Initializes the NewFractalMenuView instance and sets up the view. Then it adds all the
   * listeners to the view.
   *
   * @param fractalMenuView The NewFractalMenuView instance.
   */
  public NewFractalMenuController(NewFractalMenuView fractalMenuView) {
    this.newFractalMenuView = fractalMenuView;
    initView();
  }

  /**
   * Method to add listeners to all elements that needs listeners.
   */
  public void initView() {
    newFractalMenuView.addNewLayerButtonListener(e -> handleNewLayerButton());
    newFractalMenuView.addChoiceBoxListener(createChoiceBoxListener());
  }

  /**
   * Method to handle an action from fractalChoiceBox. It updates the ChaosGame instance to the
   * selected fractal.
   *
   * @return ChangeListener for the fractalChoiceBox.
   */
  public ChangeListener<String> createChoiceBoxListener() {
    return (ObservableValue<? extends String> observableValue, String
        oldValue, String newValue) -> {
      newFractalMenuView.getMatrixBox().getChildren().clear();
      newFractalMenuView.getMatrixBox().getChildren().add(
          NewFractalMenuView.createMatrixBox(newValue));
    };
  }

  /**
   * Method to handle the new layer button. It checks if the text fields are empty, and if they are,
   * it shows a warning popup. If they are not, it parses the values.
   */
  public void handleNewLayerButton() {
    if (areTextFieldsEmpty()) {
      showWarningPopup("Please fill in all the text fields.");
    } else {
      newFractalMenuView.parseValues();
    }
  }

  /**
   * Method to check if the text fields are empty.
   *
   * @return True if the text fields are empty, false otherwise.
   */
  private boolean areTextFieldsEmpty() {
    return newFractalMenuView.getMatrixBox().getChildren().stream()
        .filter(GridPane.class::isInstance)
        .map(GridPane.class::cast)
        .flatMap(gridPane -> gridPane.getChildren().stream())
        .filter(TextField.class::isInstance)
        .map(TextField.class::cast)
        .anyMatch(textField -> textField.getText() == null || textField.getText().trim().isEmpty());
  }

  /**
   * Method to show a warning popup.
   *
   * @param message The message to show in the popup.
   */
  private void showWarningPopup(String message) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Warning");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
