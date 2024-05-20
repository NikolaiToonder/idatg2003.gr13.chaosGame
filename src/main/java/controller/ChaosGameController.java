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
  private int currentTransformation = 1;

  public ChaosGameController(ChaosGameView view) {
    this.view = view;
    this.chaosGame = new ChaosGame(descriptionFactory.createAffine2D("Sierpinski"),
        650, 600, standardizedView);
    initView();
  }

  public void initView() {
    view.updateChoiceBoxMatrix(chaosGame);
    view.updateTextFieldsAffine(chaosGame, 1, view.getDisplayVectorValue());
    view.addIterationSliderListener(createIterationSliderListener());
    view.addZoomSliderListener(createZoomSliderListener());
    view.addFractalChoiceBoxListener(createFractalChoiceBoxListener());
    view.addMatrixChoiceBoxListener(createMatrixChoiceBoxListener());
    view.addDrawButtonListener(e -> handleDrawButton());
    view.addResetButtonListener(e -> handleResetButton());
    view.addPopupButtonListener(e -> handlePopupButton());
    view.addCloseButtonListener(e -> handleCloseButton());

    view.addDisplayVectorListener((observable, oldValue, newValue) -> {
      boolean showVector = newValue;
      if (view.getFractalChoiceBoxValue().equals("Julia")) {
        view.updateTextFieldsJulia(chaosGame, currentTransformation);
      } else {
        view.updateTextFieldsAffine(chaosGame, currentTransformation, showVector);
      }
    });
  }

  public ChangeListener<String> createFractalChoiceBoxListener() {
    return (observable, oldValue, newValue) -> {
      this.chaosGame.getDescription().setIsBarnsley(newValue.equals("Barnsley"));
      updateChaosGameInstance(newValue);
    };
  }

  public void handleDrawButton() {
    String typeOfTransform = view.getFractalChoiceBoxValue();
    String choiceToEdit = view.getMatrixChoiceBoxValue();
    List<String> textFieldsValues = view.getTextFieldsValues();

    // Update the existing description
    this.chaosGame.getDescription().writeToFile(typeOfTransform, choiceToEdit,
        view.getDisplayVectorValue(), textFieldsValues);

    updateChaosGameInstance(typeOfTransform);
  }

  public void handleResetButton() {
    this.chaosGame.getDescription().resetFractals();
    updateChaosGameInstance(view.getFractalChoiceBoxValue());
  }

  private void updateChaosGameInstance(String fractalType) {
    this.chaosGame = new ChaosGame(descriptionFactory.createAffine2D(fractalType), 500, 500, standardizedView);
    this.chaosGame.getDescription().setIsBarnsley(fractalType.equals("Barnsley"));
    chaosGame.zoom(view.getZoomSliderValue());
    view.updateSimulationView(chaosGame, (int) view.getIterationSliderValue());

    if (fractalType.equals("Julia")) {
      view.updateTextFieldsJulia(chaosGame, currentTransformation);
    } else {
      view.updateTextFieldsAffine(chaosGame, currentTransformation, view.getDisplayVectorValue());
    }
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

  public ChangeListener<String> createMatrixChoiceBoxListener() {
    return (observable, oldValue, newValue) -> {
      if (newValue != null) {
        String[] parts = newValue.split(" ");
        if (parts.length > 1 && chaosGame.getDescription().getTypeOfTransformation().equals("Affine")) {
          this.currentTransformation = Integer.parseInt(parts[1]);
          view.updateTextFieldsAffine(chaosGame, currentTransformation, view.getDisplayVectorValue());
        }
      }
    };
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
