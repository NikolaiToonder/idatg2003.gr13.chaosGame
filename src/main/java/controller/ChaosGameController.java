package controller;

import chaosgameclasses.ChaosGame;
import gui.ChaosGameView;
import gui.DescriptionFactory;
import gui.NewFractalMenuView;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;

import java.util.List;
import vectors.Vector2D;

public class ChaosGameController {
  private final ChaosGameView view;
  private ChaosGame chaosGame;
  private final DescriptionFactory descriptionFactory = new DescriptionFactory();
  private final Vector2D standardizedView = new Vector2D(0.5, 0.5);

  public ChaosGameController(ChaosGameView view) {
    this.view = view;
    this.chaosGame = new ChaosGame(descriptionFactory.createAffine2D("Sierpinski"), 500, 500, standardizedView);
    initView();
  }

  public void initView() {
    view.updateChoiceBoxMatrix(chaosGame);
    view.updateTextFieldsAffine(chaosGame, 1, false);
    view.addIterationSliderListener(createIterationSliderListener());
    view.addZoomSliderListener(createZoomSliderListener());
    view.addFractalChoiceBoxListener(createFractalChoiceBoxListener());
    view.addMatrixChoiceBoxListener(createMatrixChoiceBoxListener());
    view.addDrawButtonListener(e -> handleDrawButton());
    view.addResetButtonListener(e -> handleResetButton());
    view.addPopupButtonListener(e -> handlePopupButton());
    view.addCloseButtonListener(e -> handleCloseButton());
  }

  public ChangeListener<Number> createIterationSliderListener() {
    return (observable, oldValue, newValue) -> {
      view.updateIterationsLabel(newValue.intValue());
      view.updateSimulationView(chaosGame, newValue.intValue());
    };
  }

  public ChangeListener<Number> createZoomSliderListener() {
    return (observable, oldValue, newValue) -> {
      view.updateZoomInLabel(newValue.doubleValue());
      chaosGame.zoom(newValue.doubleValue());
      view.updateSimulationView(chaosGame, (int) view.getIterationSliderValue());
    };
  }

  public ChangeListener<String> createFractalChoiceBoxListener() {
    return (observable, oldValue, newValue) -> {
      chaosGame = new ChaosGame(descriptionFactory.createAffine2D(newValue), 500, 500, standardizedView);
      chaosGame.zoom(view.getZoomSliderValue());
      view.updateSimulationView(chaosGame, (int) view.getIterationSliderValue());
      boolean isJulia = newValue.equals("Julia");
      view.updateChoiceBoxMatrix(chaosGame);
      if (isJulia) {
        view.updateTextFieldsJulia(chaosGame, 1);
      } else {
        view.updateTextFieldsAffine(chaosGame, 1, false);
      }
    };
  }

  public ChangeListener<String> createMatrixChoiceBoxListener() {
    return (observable, oldValue, newValue) -> {
      if (newValue != null) {
        String[] parts = newValue.split(" ");
        if (parts.length > 1 && chaosGame.getDescription().getTypeOfTransformation().equals("Affine")) {
          int currentTransformation = Integer.parseInt(parts[1]);
          view.updateTextFieldsAffine(chaosGame, currentTransformation, parts[0].equals("Vector"));
        }
      }
    };
  }

  public void handleDrawButton() {
    String typeOfTransform = view.getFractalChoiceBoxValue();
    String choiceToEdit = view.getMatrixChoiceBoxValue();
    List<String> textFieldsValues = view.getTextFieldsValues();
    chaosGame.getDescription().writeToFile(typeOfTransform, choiceToEdit, textFieldsValues);
    chaosGame = new ChaosGame(descriptionFactory.createAffine2D(typeOfTransform), 500, 500, standardizedView);
    if (typeOfTransform.equals("Julia")) {
      view.updateTextFieldsJulia(chaosGame, 1);
    } else {
      view.updateTextFieldsAffine(chaosGame, 1, false);
    }
    view.updateSimulationView(chaosGame, (int) view.getIterationSliderValue());
  }

  public void handleResetButton() {
    chaosGame.getDescription().resetFractals();
    chaosGame = new ChaosGame(descriptionFactory.createAffine2D(view.getFractalChoiceBoxValue()), 500, 500, standardizedView);
    if (view.getFractalChoiceBoxValue().equals("Julia")) {
      view.updateTextFieldsJulia(chaosGame, 1);
    } else {
      view.updateTextFieldsAffine(chaosGame, 1, false);
    }
    view.updateSimulationView(chaosGame, (int) view.getIterationSliderValue());
  }

  public void handlePopupButton() {
    NewFractalMenuView fractalMenuView = new NewFractalMenuView();
    fractalMenuView.showPopupMenu(this::handleNewFractalValues);
  }

  public void handleNewFractalValues(List<String> values) {
    chaosGame.getDescription().writeToFileCustom(values);
  }

  public void handleCloseButton() {
    Platform.exit();
  }
}
