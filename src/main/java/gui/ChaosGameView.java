package gui;

import chaosgameclasses.ChaosGame;
import controller.ChaosGameController;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;
import java.util.function.Consumer;
import matrix.Matrix2x2;
import vectors.Complex;
import vectors.Vector2D;

import java.util.function.UnaryOperator;

public class ChaosGameView {

  private static final UnaryOperator<Change> filter = change -> {
    String newText = change.getControlNewText();
    if (newText.matches("-?\\d*\\.?\\d*")) { // Allow digits and an optional decimal point
      return change; // Accept the change
    }
    return null; // Reject the change
  };
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
  private final ChaosGameController controller = new ChaosGameController(this);
  private final Separator separator = new Separator();


  public Parent createContent(Stage primaryStage) {
    initializeControls();
    configureLayout();
    configureStyle(primaryStage);
    return createRootPane();
  }

  public void initializeControls() {
    fractalChoiceBox.getItems().addAll("Julia", "Sierpinski", "Barnsley", "Custom");
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

  public void configureLayout() {
    HBox checkBoxes = new HBox(displayVector,displayHeatMap);
    checkBoxes.setSpacing(5);
    VBox textFieldsConfigBox = new VBox(textFieldsBox,matrixChoiceBox,checkBoxes);
    textFieldsConfigBox.setSpacing(5);
    controlsPane.getChildren().addAll(iterationsLabel, iterationSlider, zoomInLabel, zoomSlider,
        fractalChoiceBox, resetButton, popupButton, closeButton,drawButton,
        separator,textFieldsConfigBox);
    simulationView = new SimulationView();
    simulationAndInfoBox.getChildren().addAll(simulationView);
    VBox.setVgrow(simulationView, Priority.ALWAYS);
    VBox.setMargin(matrixChoiceBox, new Insets(5, 0, 0, 0)); // Top margin of 5 pixels
  }

  public void configureStyle(Stage primaryStage) {
    primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.doubleValue() < primaryStage.getMinWidth()) {
        primaryStage.setWidth(primaryStage.getMinWidth());
      }
    });

    primaryStage.heightProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.doubleValue() < primaryStage.getMinHeight()) {
        primaryStage.setHeight(primaryStage.getMinHeight());
      }
    });
  }

  public BorderPane createRootPane() {
    BorderPane root = new BorderPane();

    HBox mainContent = new HBox();

    // Wrap simulationAndInfoBox and controlsPane in HBox
    mainContent.getChildren().addAll(simulationAndInfoBox, controlsPane);

    mainContent.setSpacing(10); // Adjust the spacing as needed


    // Set the width proportions
    HBox.setHgrow(simulationAndInfoBox, Priority.ALWAYS);
    HBox.setHgrow(controlsPane, Priority.NEVER);

    // Adjust the preferred width to reflect the 70-30% distribution
    simulationAndInfoBox.setPrefWidth(0.6 * 1000); // Example value, adjust as needed
    controlsPane.setPrefWidth(0.15 * 1000);        // Example value, adjust as needed
    controlsPane.setMinWidth(0.14 * 1000);        // Example value, adjust as needed

    root.setCenter(mainContent);
    root.setStyle("-fx-background-color: #2b2d31;");

    return root;
  }

  public void addIterationSliderListener(ChangeListener<Number> listener) {
    iterationSlider.valueProperty().addListener(listener);
  }

  public void addZoomSliderListener(ChangeListener<Number> listener) {
    zoomSlider.valueProperty().addListener(listener);
  }

  public void addFractalChoiceBoxListener(ChangeListener<String> listener) {
    fractalChoiceBox.valueProperty().addListener(listener);
  }

  public void addMatrixChoiceBoxListener(ChangeListener<String> listener) {
    matrixChoiceBox.valueProperty().addListener(listener);
  }

  public void addDrawButtonListener(Consumer<Void> handler) {
    drawButton.setOnAction(e -> handler.accept(null));
  }

  public void addResetButtonListener(Consumer<Void> handler) {
    resetButton.setOnAction(e -> handler.accept(null));
  }

  public void addPopupButtonListener(Consumer<Void> handler) {
    popupButton.setOnAction(e -> handler.accept(null));
  }

  public void addCloseButtonListener(Consumer<Void> handler) {
    closeButton.setOnAction(e -> handler.accept(null));
  }
  public void addDisplayVectorListener(ChangeListener<Boolean> listener) {
    displayVector.selectedProperty().addListener(listener);
  }

  public void addDisplayHeatmapListener(ChangeListener<Boolean> listener) {
    displayHeatMap.selectedProperty().addListener(listener);
  }


  public double getIterationSliderValue() {
    return iterationSlider.getValue();
  }

  public double getZoomSliderValue() {
    return zoomSlider.getValue();
  }

  public String getFractalChoiceBoxValue() {
    return fractalChoiceBox.getValue();
  }

  public String getMatrixChoiceBoxValue() {
    return matrixChoiceBox.getValue();
  }
  public boolean getDisplayVectorValue(){
    return this.displayVector.isSelected();
  }

  public List<String> getTextFieldsValues() {
    return textFieldsBox.getChildren().stream()
        .filter(TextField.class::isInstance)
        .map(node -> ((TextField) node).getText())
        .toList();
  }

  public int getCurrentTransformation(){
    return Integer.parseInt(this.getMatrixChoiceBoxValue().split(",")[1]);
  }

  public void updateIterationsLabel(int value) {
    iterationsLabel.setText("Iterations: " + value);
  }

  public void updateZoomInLabel(double value) {
    zoomInLabel.setText("Zoom In: " + Math.round(value) + "x");
  }

  public void updateChoiceBoxMatrix(ChaosGame chaosGame) {
    matrixChoiceBox.getItems().clear();
    for (int i = 0; i < chaosGame.getDescription().getNumberOfTransforms(); i++) {
      matrixChoiceBox.getItems().add("Transformation " + (i + 1));
    }
    matrixChoiceBox.setValue("Transformation 1");
  }

  public void updateTextFieldsAffine(ChaosGame chaosGame, int currentTransformation, boolean showVector) {
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
            textField.setStyle("-fx-background-color: #449933;");
          } else if (textField.getPromptText().equals("a01")) {
            textField.setText(String.valueOf(matrix.getA01()));
            textField.setStyle("-fx-background-color: #449933;");
          } else if (textField.getPromptText().equals("a10")) {
            textField.disableProperty().set(false);
            textField.setText(String.valueOf(matrix.getA10()));
            textField.setStyle("-fx-background-color: #449933;");
          } else if (textField.getPromptText().equals("a11")) {
            textField.setText(String.valueOf(matrix.getA11()));
            textField.disableProperty().set(false);
            textField.setStyle("-fx-background-color: #449933;");
          }
        }
      });
    } else {
      textFieldsBox.getChildren().forEach(node -> {
        if (node instanceof TextField textField) {
          if (textField.getPromptText().equals("a00")) {
            textField.setText(String.valueOf(vector.getX0()));
            textField.setStyle("-fx-background-color: #5566AA;");
          } else if (textField.getPromptText().equals("a01")) {
            textField.setText(String.valueOf(vector.getX1()));
            textField.setStyle("-fx-background-color: #5566AA;");
          } else if (textField.getPromptText().equals("a10") || textField.getPromptText().equals("a11")) {
            textField.setText("-");
            textField.disableProperty().set(true);
            textField.setStyle(null);
          }
        }
      });
    }
  }



  public void updateTextFieldsJulia(ChaosGame chaosGame, int currentTransformation) {
    chaosGame.getDescription().setTypeOfTransformation("Julia");
    List<Complex> complexNumbers = chaosGame.getDescription().getComplexNumbers();

    Complex complex = complexNumbers.get(currentTransformation - 1);

    textFieldsBox.getChildren().forEach(node -> {
      if (node instanceof TextField textField) {
        if (textField.getPromptText().equals("a00")) {
          textField.setText(String.valueOf(complex.getReal()));
          textField.setStyle("-fx-background-color: #5566AA;");
        } else if (textField.getPromptText().equals("a01")) {
          textField.setText(String.valueOf(complex.getImaginary()));
          textField.setStyle("-fx-background-color: #5566AA;");
        } else if (textField.getPromptText().equals("a10") || textField.getPromptText().equals("a11")) {
          textField.setText("-");
          textField.disableProperty().set(true);
          textField.setStyle(null);
        }
      }
    });
  }

  public void updateSimulationView(ChaosGame chaosGame, int iterations) {
    simulationView.updateSimulationView(chaosGame,iterations, displayHeatMap.isSelected());
  }

  private TextField createTextField(String labelText) {
    TextField textField = new TextField();
    textField.setPromptText(labelText);
    textField.setMaxWidth(40); // Increase max width
    textField.setMaxHeight(40); // Increase max height
    applyTextFormatter(textField, filter);
    return textField;
  }

  private static void applyTextFormatter(TextField textField, UnaryOperator<Change> filter) {
    textField.setTextFormatter(new TextFormatter<>(filter));
  }


  private GridPane createTextFieldsBox() {
    TextField a00TextField = createTextField("a00");
    TextField a01TextField = createTextField("a01");
    TextField a10TextField = createTextField("a10");
    TextField a11TextField = createTextField("a11");

    a00TextField.setStyle("-fx-background-color: #449933;");
    a01TextField.setStyle("-fx-background-color: #449933;");
    a10TextField.setStyle("-fx-background-color: #449933;");
    a11TextField.setStyle("-fx-background-color: #449933;");

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

  public void updateTextFields(ChaosGame chaosGame, int currentTransformation){
    if(chaosGame.getDescription().getTypeOfTransformation().equals("Julia")){
      System.out.println("Julia");
      updateTextFieldsJulia(chaosGame, currentTransformation);
    } else {
      updateTextFieldsAffine(chaosGame,currentTransformation,getDisplayVectorValue());
    }
  }

  public ChoiceBox<String> getMatrixChoiceBox() {
    return matrixChoiceBox;
  }

  public void showErrorPopup(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

}

