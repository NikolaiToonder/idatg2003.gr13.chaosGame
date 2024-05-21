package gui.controller;

import gui.view.NewFractalMenuView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class NewFractalMenuController {

  NewFractalMenuView newFractalMenuView;

  public NewFractalMenuController(NewFractalMenuView fractalMenuView) {
    this.newFractalMenuView = fractalMenuView;
    initView();
  }

  public void initView() {
    newFractalMenuView.addNewLayerButtonListener(e -> handleNewLayerButton());
    newFractalMenuView.addChoiceBoxListener(createChoiceBoxListener());
  }

  public ChangeListener<String> createChoiceBoxListener() {
    return (ObservableValue<? extends String> observableValue, String oldValue, String newValue) -> {
      newFractalMenuView.getMatrixBox().getChildren().clear();
      newFractalMenuView.getMatrixBox().getChildren().add(
          newFractalMenuView.createMatrixBox(newValue));
    };
  }

  public void handleNewLayerButton() {
    if (areTextFieldsEmpty()) {
      showWarningPopup("Please fill in all the text fields.");
    } else {
      newFractalMenuView.parseValues();
    }
  }


  private boolean areTextFieldsEmpty() {
    return newFractalMenuView.getMatrixBox().getChildren().stream()
        .filter(node -> node instanceof GridPane)
        .map(GridPane.class::cast)
        .flatMap(gridPane -> gridPane.getChildren().stream())
        .filter(node -> node instanceof TextField)
        .map(TextField.class::cast)
        .anyMatch(textField -> textField.getText() == null || textField.getText().trim().isEmpty());
  }

  private void showWarningPopup(String message) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Warning");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
