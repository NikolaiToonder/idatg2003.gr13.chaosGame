package controller;

import gui.NewFractalMenuView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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
