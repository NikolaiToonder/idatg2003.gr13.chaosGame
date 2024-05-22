package gui.controller;

import chaosgameclasses.ChaosGame;
import factory.DescriptionFactory;
import gui.view.ChaosGameView;
import gui.view.NewFractalMenuView;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import math.vectors.Vector2D;

/**
 * Controller class for the Chaos Game. Handles all the events in the program, and calls appropriate
 * methods.
 *
 * @version 1.0
 * @author Nikolai Engelsen Tønder
 */
public class ChaosGameController {

  private final ChaosGameView view;
  private ChaosGame chaosGame;
  private final DescriptionFactory descriptionFactory = new DescriptionFactory();
  private final Vector2D standardizedView = new Vector2D(0.5, 0.5);
  private int currentTransformation = 1;

  /**
   * Constructor for the ChaosGameController class. Takes a ChaosGameView as a parameter.
   * Initializes the ChaosGame instance and sets up the view. Then it adds all the listeners to the
   * view.
   *
   * @param view The ChaosGameView instance.
   */
  public ChaosGameController(ChaosGameView view) {
    this.view = view;
    this.chaosGame = new ChaosGame(descriptionFactory.createAffine2D("Sierpinski"),
        600, 650, standardizedView);
    initView();
  }

  /**
   * Method to add listeners to all elements that needs listeners.
   */
  public void initView() {
    view.updateChoiceBoxMatrix(chaosGame);
    view.updateTextFieldsAffine(chaosGame, currentTransformation, view.getDisplayVectorValue());
    view.addIterationSliderListener(handleIterationSliderListener());
    view.addZoomSliderListener(createZoomSliderListener());
    view.addFractalChoiceBoxListener(handleFractalChoiceBoxListener());
    view.addMatrixChoiceBoxListener(handleMatrixChoiceBoxListener());
    view.addDrawButtonListener(e -> handleDrawButton());
    view.addResetButtonListener(e -> handleResetButton());
    view.addPopupButtonListener(e -> handlePopupButton());
    view.addCloseButtonListener(e -> handleCloseButton());
    view.addDisplayHeatmapListener((observable, oldValue, newValue) ->
        view.updateSimulationView(chaosGame, (int) view.getIterationSliderValue()));

    view.addDisplayVectorListener((observable, oldValue, newValue) ->
        view.updateTextFields(chaosGame, currentTransformation));
  }

  /**
   * Method to handle an action from fractalChoiceBox. It updates the ChaosGame instance to the
   * selected fractal.
   *
   * @return A ChangeListener for the fractal choice box.
   */
  public ChangeListener<String> handleFractalChoiceBoxListener() {
    return (observable, oldValue, newValue) -> {
      this.chaosGame.getDescription().setIsBarnsley(newValue.equals("Barnsley"));
      updateChaosGameInstance(newValue);
    };
  }

  /**
   * Method to handle the draw button. It updates the description of the current fractal with the
   * values from the text fields.
   */
  public void handleDrawButton() {
    String typeOfTransform = view.getFractalChoiceBoxValue();
    String choiceToEdit = view.getMatrixChoiceBoxValue();
    List<String> textFieldsValues = view.getTextFieldsValues();

    // Check if any text field value is null or empty
    if (textFieldsValues.stream().anyMatch(value -> value == null || value.isEmpty())) {
      view.showErrorPopup("Error", "Please fill in all text fields");
      return;
    }

    // Update the existing description
    this.chaosGame.getDescription().writeToFile(choiceToEdit,
        view.getDisplayVectorValue(), textFieldsValues);

    updateChaosGameInstance(typeOfTransform);
  }

  /**
   * Method to handle the reset button. It resets the fractal to its original state.
   */
  public void handleResetButton() {
    this.chaosGame.getDescription().resetFractals();
    updateChaosGameInstance(view.getFractalChoiceBoxValue());
  }

  /**
   * Method to update the ChaosGame instance to the selected fractal.
   *
   * @param fractalType The type of fractal to update to.
   */
  private void updateChaosGameInstance(String fractalType) {
    this.chaosGame = new ChaosGame(descriptionFactory.createAffine2D(fractalType), 600, 600,
        standardizedView);
    this.chaosGame.getDescription().setIsBarnsley(fractalType.equals("Barnsley"));
    currentTransformation = 1; // Reset the transformation index to the first transformation
    chaosGame.zoom(view.getZoomSliderValue());
    view.updateChoiceBoxMatrix(chaosGame); // Ensure the matrix choice box is updated
    view.updateSimulationView(chaosGame, (int) view.getIterationSliderValue());
    view.updateTextFields(chaosGame,
        currentTransformation); // Update text fields for the first transformation
  }

  /**
   * Method to handle a ChangeListener for the iteration slider. It updates the label and the
   * simulation view.
   *
   * @return A ChangeListener for the iteration slider.
   */
  public ChangeListener<Number> handleIterationSliderListener() {
    return (observable, oldValue, newValue) -> {
      view.updateIterationsLabel(newValue.intValue());
      view.updateSimulationView(chaosGame, newValue.intValue());
    };
  }

  /**
   * Method to handle a ChangeListener for the zoom slider. It updates the label and the simulation
   * view.
   *
   * @return A ChangeListener for the zoom slider.
   */
  public ChangeListener<Number> createZoomSliderListener() {
    return (observable, oldValue, newValue) -> {
      view.updateZoomInLabel(newValue.doubleValue());
      chaosGame.zoom(newValue.doubleValue());
      view.updateSimulationView(chaosGame, (int) view.getIterationSliderValue());
    };
  }

  /**
   * Method to handle a ChangeListener for the matrix choice box. It updates the text fields with
   * the values of the selected transformation.
   *
   * @return A ChangeListener for the matrix choice box.
   */
  public ChangeListener<String> handleMatrixChoiceBoxListener() {
    return (observable, oldValue, newValue) -> {
      if (newValue != null) {
        String[] parts = newValue.split(" ");
        if (parts.length > 1 && chaosGame.getDescription().getTypeOfTransformation()
            .equals("Affine")) {
          int newTransformation = Integer.parseInt(parts[1]);
          if (newTransformation <= chaosGame.getDescription().getNumberOfTransforms()) {
            this.currentTransformation = newTransformation;
            view.updateTextFieldsAffine(chaosGame, currentTransformation,
                view.getDisplayVectorValue());
          } else {
            // Handle the case where the selected transformation exceeds available transformations
            this.currentTransformation = 1;
            view.getMatrixChoiceBox().setValue("Transformation 1");
            view.updateTextFieldsAffine(chaosGame, currentTransformation,
                view.getDisplayVectorValue());
          }
        }
      }
    };
  }

  /**
   * Method to handle the popup button. It shows a popup menu where the user can input new values
   * for the fractal.
   */
  public void handlePopupButton() {
    NewFractalMenuView fractalMenuView = new NewFractalMenuView();
    fractalMenuView.showPopupMenu(this::handleNewFractalValues);
  }

  /**
   * Method to handle the new fractal values. It writes the new values to the description file.
   *
   * @param values The new values for the fractal.
   */
  public void handleNewFractalValues(List<String> values) {
    chaosGame.getDescription().writeToFileCustom(values);
  }

  /**
   * Method to handle the close button. It closes the program.
   */
  public void handleCloseButton() {
    Platform.exit();
  }
}