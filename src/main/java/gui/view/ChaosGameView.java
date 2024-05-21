package gui.view;

import modul.chaosgameclasses.ChaosGame;
import gui.controller.ChaosGameController;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import modul.math.matrix.Matrix2x2;
import modul.math.vectors.Complex;
import modul.math.vectors.Vector2D;


/**
 * JavaFX class for the Chaos Game view. Will be used with ChaosGameController to view the chaos
 * game.
 *
 * @author Nikolai Engelsen Tønder, Mustafa Kesen
 * @version 1.0
 */
public class ChaosGameView {


  private static final UnaryOperator<Change> filter = change -> {
    //Made with ChatGPT, only allows the user to type in valid numbers and decimals.
    String newText = change.getControlNewText();
    if (newText.matches("-?\\d*\\.?\\d*")) { // Allow digits and an optional decimal point
      return change; // Accept the change
    }
    return null; // Reject the change
  };
  private final String greenColor = "-fx-background-color: #449933;";
  private final String blueColor = "-fx-background-color: #5566AA;";
  private final ChoiceBox<String> fractalChoiceBox = new ChoiceBox<>();
  private final ChoiceBox<String> matrixChoiceBox = new ChoiceBox<>();
  private final Slider iterationSlider = new Slider(100, 300000, 50000);
  private final Slider zoomSlider = new Slider(1, 10, 1);
  private final Label iterationsLabel = new Label("Iterations: ");
  private final Label zoomInLabel = new Label("Zoom In");
  private final Button drawButton = new Button("Draw");
  private final Button resetButton = new Button("Reset Fractal");
  private final Button popupButton = new Button("Edit Custom Fractal");
  private final Button closeButton = new Button("Close application");
  private final CheckBox displayVector = new CheckBox("Vector");
  private final CheckBox displayHeatMap = new CheckBox("Heat Map");
  private final GridPane textFieldsBox = createTextFieldsBox();
  private final VBox controlsPane = new VBox(10);
  private final VBox simulationAndInfoBox = new VBox();
  private SimulationView simulationView = new SimulationView();
  //Controller instanced in the view to apply listeners to all the controls
  private final ChaosGameController controller = new ChaosGameController(this);

  private final Separator separator = new Separator();
  /**
   * Method will create, initialize, prepare and send the content back to MainApp.
   *
   * @param primaryStage The stage to be displayed.
   * @return The root pane of the view.
   */
  public Parent createContent(Stage primaryStage) {
    initializeControls();
    configureLayout();
    return createRootPane();
  }

  /**
   * Method to initialize all the controls in the view.
   */
  public void initializeControls() {
    fractalChoiceBox.getItems().addAll("Julia", "Sierpinski", "Barnsley", "Snowflake", "Custom");
    fractalChoiceBox.setValue("Sierpinski");

    iterationsLabel.setStyle("-fx-text-fill: white;");
    zoomInLabel.setStyle("-fx-text-fill: white;");
    resetButton.setStyle("-fx-background-color: #ffbb00;");
    resetButton.setTextFill(javafx.scene.paint.Color.BLACK);
    popupButton.setStyle("-fx-background-color: #339922;");
    closeButton.setStyle("-fx-background-color: #f55353;");
    closeButton.setTextFill(javafx.scene.paint.Color.BLACK);

    separator.setMaxWidth(Double.MAX_VALUE);
    separator.setStyle("-fx-background-color: white");

    displayVector.setStyle("-fx-text-fill: white");
    displayHeatMap.setStyle("-fx-text-fill: white");

    textFieldsBox.setPadding(new Insets(0, 0, 0, 15));
    controlsPane.setAlignment(Pos.CENTER);
    controlsPane.setPrefHeight(300);
  }

  /**
   * Method to place all contents on the chaosGameView to the right place.
   */
  public void configureLayout() {
    HBox checkBoxes = new HBox(displayVector, displayHeatMap);
    checkBoxes.setSpacing(5);
    VBox textFieldsConfigBox = new VBox(textFieldsBox, matrixChoiceBox, checkBoxes);
    textFieldsConfigBox.setSpacing(5);
    controlsPane.getChildren().addAll(iterationsLabel, iterationSlider, zoomInLabel, zoomSlider,
        fractalChoiceBox, resetButton, popupButton, closeButton, drawButton,
        separator, textFieldsConfigBox);
    simulationView = new SimulationView();
    simulationAndInfoBox.getChildren().addAll(simulationView);
    VBox.setVgrow(simulationView, Priority.ALWAYS);
    VBox.setMargin(matrixChoiceBox, new Insets(5, 0, 0, 0)); // Top margin of 5 pixels
  }

  /**
   * Method to create the root pane of the view.
   *
   * @return The root pane of the view.
   */
  public BorderPane createRootPane() {
    HBox mainContent = new HBox();

    mainContent.getChildren().addAll(simulationAndInfoBox, controlsPane);

    mainContent.setSpacing(10);

    HBox.setHgrow(simulationAndInfoBox, Priority.ALWAYS);
    HBox.setHgrow(controlsPane, Priority.NEVER);

    simulationAndInfoBox.setPrefWidth(0.6 * 1000);
    controlsPane.setPrefWidth(0.15 * 1000);
    controlsPane.setMinWidth(0.14 * 1000);

    BorderPane root = new BorderPane();

    root.setCenter(mainContent);
    root.setStyle("-fx-background-color: #2b2d31;");

    return root;
  }

  /**
   * Method to add a listener to the iterationSlider.
   *
   * @param listener The listener to add.
   */
  public void addIterationSliderListener(ChangeListener<Number> listener) {
    iterationSlider.valueProperty().addListener(listener);
  }

  /**
   * Method to add a listener to the zoomSlider.
   *
   * @param listener The listener to add.
   */
  public void addZoomSliderListener(ChangeListener<Number> listener) {
    zoomSlider.valueProperty().addListener(listener);
  }

  /**
   * Method to add a listener to the fractalChoiceBox.
   *
   * @param listener The listener to add.
   */
  public void addFractalChoiceBoxListener(ChangeListener<String> listener) {
    fractalChoiceBox.valueProperty().addListener(listener);
  }

  /**
   * Method to add a listener to the matrixChoiceBox.
   *
   * @param listener The listener to add.
   */
  public void addMatrixChoiceBoxListener(ChangeListener<String> listener) {
    matrixChoiceBox.valueProperty().addListener(listener);
  }

  /**
   * Method to add a listener to the drawButton.
   *
   * @param handler The handler to add.
   */
  public void addDrawButtonListener(Consumer<Void> handler) {
    drawButton.setOnAction(e -> handler.accept(null));
  }

  /**
   * Method to add a listener to the resetButton.
   *
   * @param handler The handler to add.
   */
  public void addResetButtonListener(Consumer<Void> handler) {
    resetButton.setOnAction(e -> handler.accept(null));
  }

  /**
   * Method to add a listener to the popupButton.
   *
   * @param handler The handler to add.
   */
  public void addPopupButtonListener(Consumer<Void> handler) {
    popupButton.setOnAction(e -> handler.accept(null));
  }

  /**
   * Method to add a listener to the closeButton.
   *
   * @param handler The handler to add.
   */
  public void addCloseButtonListener(Consumer<Void> handler) {
    closeButton.setOnAction(e -> handler.accept(null));
  }

  /**
   * Method to add a listener to the displayVector CheckBox.
   *
   * @param listener The listener to add.
   */
  public void addDisplayVectorListener(ChangeListener<Boolean> listener) {
    displayVector.selectedProperty().addListener(listener);
  }

  /**
   * Method to add a listener to the displayHeatMap CheckBox.
   *
   * @param listener The listener to add.
   */
  public void addDisplayHeatmapListener(ChangeListener<Boolean> listener) {
    displayHeatMap.selectedProperty().addListener(listener);
  }

  /**
   * Method to get the value of the iterationSlider.
   *
   * @return The value of the iterationSlider.
   */
  public double getIterationSliderValue() {
    return iterationSlider.getValue();
  }

  /**
   * Method to get the value of the zoomSlider.
   *
   * @return The value of the zoomSlider.
   */
  public double getZoomSliderValue() {
    return zoomSlider.getValue();
  }

  /**
   * Method to get the value of the fractalChoiceBox.
   *
   * @return The value of the fractalChoiceBox.
   */
  public String getFractalChoiceBoxValue() {
    return fractalChoiceBox.getValue();
  }

  /**
   * Method to get the value of the matrixChoiceBox.
   *
   * @return The value of the matrixChoiceBox.
   */
  public String getMatrixChoiceBoxValue() {
    return matrixChoiceBox.getValue();
  }

  /**
   * Method to get the value of the displayVector CheckBox.
   *
   * @return The value of the displayVector CheckBox.
   */
  public boolean getDisplayVectorValue() {
    return this.displayVector.isSelected();
  }

  /**
   * Method to get all values of the textFields. Will collect everything into a stream.
   *
   * @return List of all values in the textFields.
   */
  public List<String> getTextFieldsValues() {
    return textFieldsBox.getChildren().stream()
        .filter(TextField.class::isInstance)
        .map(node -> ((TextField) node).getText())
        .toList();
  }

  /**
   * Method to update the iteration label over the iteration slider.
   *
   * @param value The value to update the label with.
   */
  public void updateIterationsLabel(int value) {
    iterationsLabel.setText("Iterations: " + value);
  }

  /**
   * Method to update the zoomIn label over the zoom slider.
   *
   * @param value The value to update the label with.
   */
  public void updateZoomInLabel(double value) {
    zoomInLabel.setText("Zoom In: " + Math.round(value) + "x");
  }

  /**
   * Method to update the choiceBoxMatrix to the correct number of transformations.
   *
   * @param chaosGame The chaosGame instance to get the number of transformations from.
   */
  public void updateChoiceBoxMatrix(ChaosGame chaosGame) {
    matrixChoiceBox.getItems().clear();
    for (int i = 0; i < chaosGame.getDescription().getNumberOfTransforms(); i++) {
      matrixChoiceBox.getItems().add("Transformation " + (i + 1));
    }
    matrixChoiceBox.setValue("Transformation 1");
  }

  /**
   * Method to update the textFields if the fractal is based on affine transformations.
   *
   * @param chaosGame The chaosGame instance to get the values from.
   * @param currentTransformation The current transformation to show the values for.
   * @param showVector If the vector should be shown.
   */
  public void updateTextFieldsAffine(ChaosGame chaosGame, int currentTransformation,
      boolean showVector) {
    chaosGame.getDescription().setTypeOfTransformation("Affine");
    List<Matrix2x2> matrices = chaosGame.getDescription().getMatrixList();
    List<Vector2D> vectors = chaosGame.getDescription().getVectorList();

    Matrix2x2 matrix = matrices.get(currentTransformation - 1);
    Vector2D vector = vectors.get(currentTransformation - 1);

    if (!showVector) {
      textFieldsBox.getChildren().forEach(node -> {
        if (node instanceof TextField textField) {
          if (textField.getPromptText().equals("a00")) {
            textField.setText(String.valueOf(matrix.getA00()));
            textField.setStyle(greenColor);
          } else if (textField.getPromptText().equals("a01")) {
            textField.setText(String.valueOf(matrix.getA01()));
            textField.setStyle(greenColor);
          } else if (textField.getPromptText().equals("a10")) {
            textField.disableProperty().set(false);
            textField.setText(String.valueOf(matrix.getA10()));
            textField.setStyle(greenColor);
          } else if (textField.getPromptText().equals("a11")) {
            textField.setText(String.valueOf(matrix.getA11()));
            textField.disableProperty().set(false);
            textField.setStyle(greenColor);
          }
        }
      });
    } else {
      textFieldsBox.getChildren().forEach(node -> {
        if (node instanceof TextField textField) {
          if (textField.getPromptText().equals("a00")) {
            textField.setText(String.valueOf(vector.getX0()));
            textField.setStyle(blueColor);
          } else if (textField.getPromptText().equals("a01")) {
            textField.setText(String.valueOf(vector.getX1()));
            textField.setStyle(blueColor);
          } else if (textField.getPromptText().equals("a10") || textField.getPromptText()
              .equals("a11")) {
            textField.setText("-");
            textField.disableProperty().set(true);
            textField.setStyle(null);
          }
        }
      });
    }
  }


  /**
   * Method to update the textFields if the fractal is based on Julia transformations.
   *
   * @param chaosGame The chaosGame instance to get the values from.
   * @param currentTransformation The current transformation to show the values for.
   */
  public void updateTextFieldsJulia(ChaosGame chaosGame, int currentTransformation) {
    chaosGame.getDescription().setTypeOfTransformation("Julia");
    List<Complex> complexNumbers = chaosGame.getDescription().getComplexNumbers();

    Complex complex = complexNumbers.get(currentTransformation - 1);

    textFieldsBox.getChildren().forEach(node -> {
      if (node instanceof TextField textField) {
        if (textField.getPromptText().equals("a00")) {
          textField.setText(String.valueOf(complex.getReal()));
          textField.setStyle(blueColor);
        } else if (textField.getPromptText().equals("a01")) {
          textField.setText(String.valueOf(complex.getImaginary()));
          textField.setStyle(blueColor);
        } else if (textField.getPromptText().equals("a10") || textField.getPromptText()
            .equals("a11")) {
          textField.setText("-");
          textField.disableProperty().set(true);
          textField.setStyle(null);
        }
      }
    });
  }

  /**
   * Method to update the simulation view.
   *
   * @param chaosGame The chaosGame instance to get the values from.
   * @param iterations The number of iterations to run.
   */
  public void updateSimulationView(ChaosGame chaosGame, int iterations) {
    simulationView.updateSimulationView(chaosGame, iterations, displayHeatMap.isSelected());
  }

  /**
   * Create a text field. Applies a formatter so the user can only type in numbers.
   */
  private TextField createTextField(String labelText) {
    TextField textField = new TextField();
    textField.setPromptText(labelText);
    textField.setMaxWidth(40); // Increase max width
    textField.setMaxHeight(40); // Increase max height
    applyTextFormatter(textField, filter);
    return textField;
  }

  /**
   * Method to apply a text formatter to a text field.
   */
  private static void applyTextFormatter(TextField textField, UnaryOperator<Change> filter) {
    textField.setTextFormatter(new TextFormatter<>(filter));
  }


  /**
   * Method to create a GridPane with text fields.
   */
  private GridPane createTextFieldsBox() {
    TextField a00TextField = createTextField("a00");
    a00TextField.setStyle(greenColor);

    TextField a01TextField = createTextField("a01");
    a01TextField.setStyle(greenColor);

    TextField a10TextField = createTextField("a10");
    a10TextField.setStyle(greenColor);

    TextField a11TextField = createTextField("a11");
    a11TextField.setStyle(greenColor);

    GridPane textFieldsBox = new GridPane();
    textFieldsBox.add(a00TextField, 0, 0);
    textFieldsBox.add(a01TextField, 1, 0);
    textFieldsBox.add(a10TextField, 0, 1);
    textFieldsBox.add(a11TextField, 1, 1);

    // Set equal sizes for all cells
    ColumnConstraints columnConstraints = new ColumnConstraints();
    columnConstraints.setPercentWidth(40); // 40% width for each column

    RowConstraints rowConstraints = new RowConstraints();
    rowConstraints.setPercentHeight(40); // 40% height for each row

    textFieldsBox.getColumnConstraints().addAll(columnConstraints, columnConstraints);
    textFieldsBox.getRowConstraints().addAll(rowConstraints, rowConstraints);

    textFieldsBox.setHgap(5);
    textFieldsBox.setVgap(5);
    textFieldsBox.setMaxWidth(200); // Increased max width to accommodate larger text fields

    return textFieldsBox;
  }

  /**
   * Method to update the text fields based on the current transformation.
   *
   * @param chaosGame The chaosGame instance to get the values from.
   * @param currentTransformation The current transformation to show the values for.
   */
  public void updateTextFields(ChaosGame chaosGame, int currentTransformation) {
    if (chaosGame.getDescription().getTypeOfTransformation().equals("Julia")) {
      updateTextFieldsJulia(chaosGame, currentTransformation);
    } else {
      updateTextFieldsAffine(chaosGame, currentTransformation, getDisplayVectorValue());
    }
  }

  /**
   * Method to get the matrixChoiceBox.
   *
   * @return The matrixChoiceBox.
   */
  public ChoiceBox<String> getMatrixChoiceBox() {
    return matrixChoiceBox;
  }

  /**
   * Method to show a warning popup.
   *
   * @param title The title of the popup.
   * @param message The message to show in the popup.
   */
  public void showErrorPopup(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

}
