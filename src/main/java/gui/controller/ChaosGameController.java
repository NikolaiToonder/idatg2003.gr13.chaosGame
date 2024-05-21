package gui.controller;

import chaosgameclasses.ChaosGame;
import gui.view.ChaosGameView;
import gui.view.NewFractalMenuView;
import factory.DescriptionFactory;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;

import java.util.List;
import math.vectors.Vector2D;

public class ChaosGameController {
  private final ChaosGameView view;
  private ChaosGame chaosGame;
  private final DescriptionFactory descriptionFactory = new DescriptionFactory();
  private final Vector2D standardizedView = new Vector2D(0.5, 0.5);
  private int currentTransformation = 1;

  public ChaosGameController(ChaosGameView view) {
    this.view = view;
    this.chaosGame = new ChaosGame(descriptionFactory.createAffine2D("Sierpinski"),
        600, 650, standardizedView);
    initView();
  }

  public void initView() {
    view.updateChoiceBoxMatrix(chaosGame);
    view.updateTextFieldsAffine(chaosGame, currentTransformation, view.getDisplayVectorValue());
    view.addIterationSliderListener(createIterationSliderListener());
    view.addZoomSliderListener(createZoomSliderListener());
    view.addFractalChoiceBoxListener(createFractalChoiceBoxListener());
    view.addMatrixChoiceBoxListener(createMatrixChoiceBoxListener());
    view.addDrawButtonListener(e -> handleDrawButton());
    view.addResetButtonListener(e -> handleResetButton());
    view.addPopupButtonListener(e -> handlePopupButton());
    view.addCloseButtonListener(e -> handleCloseButton());
    view.addDisplayHeatmapListener((observable, oldValue, newValue) ->
        view.updateSimulationView(chaosGame, (int) view.getIterationSliderValue()));

    view.addDisplayVectorListener((observable, oldValue, newValue) ->
        view.updateTextFields(chaosGame, currentTransformation));
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



    // Check if any text field value is null or empty
    if (textFieldsValues.stream().anyMatch(value -> value == null || value.isEmpty())) {
      view.showErrorPopup("Error","Please fill in all text fields");
      return;
    }


    // Update the existing description
    this.chaosGame.getDescription().writeToFile(choiceToEdit,
        view.getDisplayVectorValue(), textFieldsValues);

    updateChaosGameInstance(typeOfTransform);


  }


  public void handleResetButton() {
    this.chaosGame.getDescription().resetFractals();
    updateChaosGameInstance(view.getFractalChoiceBoxValue());
  }

  private void updateChaosGameInstance(String fractalType) {
    this.chaosGame = new ChaosGame(descriptionFactory.createAffine2D(fractalType), 600, 600, standardizedView);
    this.chaosGame.getDescription().setIsBarnsley(fractalType.equals("Barnsley"));
    currentTransformation = 1; // Reset the transformation index to the first transformation
    chaosGame.zoom(view.getZoomSliderValue());
    view.updateChoiceBoxMatrix(chaosGame); // Ensure the matrix choice box is updated
    view.updateSimulationView(chaosGame, (int) view.getIterationSliderValue());
    view.updateTextFields(chaosGame, currentTransformation); // Update text fields for the first transformation
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
          int newTransformation = Integer.parseInt(parts[1]);
          if (newTransformation <= chaosGame.getDescription().getNumberOfTransforms()) {
            this.currentTransformation = newTransformation;
            view.updateTextFieldsAffine(chaosGame, currentTransformation, view.getDisplayVectorValue());
          } else {
            // Handle the case where the selected transformation exceeds available transformations
            this.currentTransformation = 1;
            view.getMatrixChoiceBox().setValue("Transformation 1");
            view.updateTextFieldsAffine(chaosGame, currentTransformation, view.getDisplayVectorValue());
          }
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
