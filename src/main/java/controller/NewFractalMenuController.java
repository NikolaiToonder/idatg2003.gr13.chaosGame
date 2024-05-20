package controller;

import gui.NewFractalMenuView;
import java.util.List;

public class NewFractalMenuController {
  private NewFractalMenuView newFractalMenuView;

  public NewFractalMenuController(NewFractalMenuView fractalMenuView){
    this.newFractalMenuView = fractalMenuView;
    initView();
  }

  public void initView() {
    newFractalMenuView.setCallback(this::handleSaveButton);
    newFractalMenuView.addSaveButtonListener(e -> handleSaveButton());
  }

  private void handleSaveButton(List<String> strings) {
  }

  public void handleSaveButton(){
    newFractalMenuView.parseHeaderValues();
    newFractalMenuView.parseValues();
    newFractalMenuView.combineAllValues();
    newFractalMenuView.getCallback().accept(newFractalMenuView.getAllv());
    newFractalMenuView.popupStage.close();
  }
}
