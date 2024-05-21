package gui.controller;

import gui.view.NewFractalMenuView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class NewFractalMenuController {

  NewFractalMenuView newFractalMenuView;

  public NewFractalMenuController(NewFractalMenuView fractalMenuView){
    this.newFractalMenuView = fractalMenuView;
    initView();
  }
  public void initView() {
    newFractalMenuView.addNewLayerButtonListener(e->handleNewLayerButton());
    newFractalMenuView.addChoiceBoxListener(createChoiceBoxListener()); // Update this line
  }

  public ChangeListener<String> createChoiceBoxListener() {
    return (ObservableValue<? extends String> observableValue, String oldValue, String newValue) -> {
      newFractalMenuView.getMatrixBox().getChildren().clear();
      newFractalMenuView.getMatrixBox().getChildren().add(
          newFractalMenuView.createMatrixBox(newValue));
    };
  }


  public void handleNewLayerButton() {
    newFractalMenuView.parseValues();
  }



}
