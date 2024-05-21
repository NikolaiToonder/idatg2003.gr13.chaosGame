package gui.view;

import gui.controller.NewFractalMenuController;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * JavaFX class for the new fractal menu view. Will be used to create a new custom fractal.
 *
 * @author Nikolai Engelsen Tønder
 * @version 1.0
 */
public class NewFractalMenuView {
  private static final UnaryOperator<Change> filter = change -> {
    //WITH CHATGPT, used to filter out unwanted characters.
    String newText = change.getControlNewText();
    if (newText.matches("-?\\d*\\.?\\d*")) { // Allow digits and an optional decimal point
      return change; // Accept the change
    }
    return null; // Reject the change
  };
  public final Stage popupStage = new Stage();
  private final VBox popupLayout = new VBox(10);
  Scene popupScene = new Scene(popupLayout, 500, 400);
  private final Label title = new Label("Create New Fractal");
  private final VBox minMaxBox = createMinMaxFields();
  private final ChoiceBox<String> choiceBox = configureChoiceBox();
  private final VBox matrixBox = createMatrixBox(choiceBox.getValue());
  private final Button saveButton = new Button("Save Fractal");
  private final Separator separator = new Separator();
  private final Button addNewLayer = new Button("New Transformation");
  private final HBox buttonBox = new HBox(saveButton, addNewLayer);
  NewFractalMenuController chaosGameController = new NewFractalMenuController(this);
  private final List<String> allHeaderValues;
  private final List<String> allMatrixValues;
  private final List<String> allValues;


  /**
   * Constructor for the NewFractalMenuView class. Initializes the lists for
   * the header values, matrix values, and all values.
   */
  public NewFractalMenuView() {
    this.allHeaderValues = new ArrayList<>();
    this.allMatrixValues = new ArrayList<>();
    this.allValues = new ArrayList<>();
  }


  /**
   * Method to show the popup menu. It will show the popup stage and configure the layout.
   *
   * @param callback The callback function to call when the save button is pressed.
   */
  public void showPopupMenu(Consumer<List<String>> callback) {
    styleElements();
    configureLayout();

    // This method, for some reason, did not work in the controller class.
    // This has to be here, even though it does not follow MWC principle.
    saveButton.setOnAction(actionEvent -> {
      if (areFieldsEmpty()) {
        showWarningPopup("Please fill in all the fields.");
      } else {
        parseHeaderValues();
        parseValues();
        combineAllValues();
        callback.accept(allValues);
        popupStage.close();
      }
    });

    // Show the popup stage
    popupStage.show();
  }

  /**
   * Method to check if the text fields are empty.
   *
   * @return True if the text fields are empty, false otherwise.
   */
  private boolean areFieldsEmpty() {
    boolean areMinMaxFieldsEmpty = minMaxBox.getChildren().stream()
        .filter(GridPane.class::isInstance)
        .map(GridPane.class::cast)
        .flatMap(gridPane -> gridPane.getChildren().stream())
        .filter(TextField.class::isInstance)
        .map(TextField.class::cast)
        .anyMatch(textField -> textField.getText() == null || textField.getText().trim().isEmpty());

    boolean areMatrixFieldsEmpty = matrixBox.getChildren().stream()
        .filter(GridPane.class::isInstance)
        .map(GridPane.class::cast)
        .flatMap(gridPane -> gridPane.getChildren().stream())
        .filter(TextField.class::isInstance)
        .map(TextField.class::cast)
        .anyMatch(textField -> textField.getText() == null || textField.getText().trim().isEmpty());

    return areMinMaxFieldsEmpty || areMatrixFieldsEmpty;
  }

  /**
   * Method to show a warning popup.
   *
   * @param message The message to display in the popup.
   */
  private void showWarningPopup(String message) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Warning");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  /**
   * Method to style the elements in the popup menu.
   */
  public void styleElements() {
    popupLayout.setAlignment(Pos.CENTER);
    popupLayout.setPadding(new Insets(20));
    popupLayout.setStyle("-fx-background-color: #2b2d31;");

    title.setTextFill(Color.WHITE);
    title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

    saveButton.setStyle("-fx-background-color: #66bb66;");

    separator.setMaxWidth(Double.MAX_VALUE);
    separator.setStyle("-fx-background-color: white;");

    addNewLayer.setStyle("-fx-background-color: #4488ff");

    buttonBox.setSpacing(10);
    buttonBox.setAlignment(Pos.CENTER);
  }

  /**
   * Method to configure the layout of the popup menu.
   */
  public void configureLayout() {
    popupLayout.getChildren().addAll(title, choiceBox, minMaxBox, separator, matrixBox, buttonBox);

    popupStage.setScene(popupScene);

    popupStage.initModality(Modality.APPLICATION_MODAL);

    // Set the title for the pop-up stage
    popupStage.setTitle("New Fractal Menu");

    // Show the pop-up stage
    popupStage.show();
  }

  /**
   * Method to create a choice box for the transformation type.
   *
   * @return A choice box for the transformation type.
   */
  public ChoiceBox<String> configureChoiceBox() {
    ChoiceBox<String> choiceBox = new ChoiceBox<>();
    choiceBox.getItems().addAll("Julia", "Affine2D");
    choiceBox.setValue("Affine2D");
    return choiceBox;
  }

  /**
   * Method to create a matrix box for the transformation matrix.
   *
   * @param value The value of the choice box.
   * @return A VBox containing the matrix box.
   */
  public static VBox createMatrixBox(String value) {

    VBox matrixBox = new VBox();
    GridPane gridPane = new GridPane();
    gridPane.setHgap(10);
    gridPane.setVgap(10);
    if (value.equals("Affine2D")) {

      // Creating text fields for the matrix
      TextField aField = new TextField();
      TextField bField = new TextField();
      TextField cField = new TextField();
      TextField dField = new TextField();

      TextField aVector = new TextField();
      TextField bVector = new TextField();

      aField.setStyle("-fx-background-color: #449933;");
      bField.setStyle("-fx-background-color: #449933;");
      cField.setStyle("-fx-background-color: #449933;");
      dField.setStyle("-fx-background-color: #449933;");

      aVector.setStyle("-fx-background-color: #444499;");
      bVector.setStyle("-fx-background-color: #444499;");

      // Setting prompt text for the text fields
      aField.setPromptText("a00");
      bField.setPromptText("a01");
      cField.setPromptText("a10");
      dField.setPromptText("a11");

      aVector.setPromptText("b0");
      bVector.setPromptText("b1");

      // Apply text formatter to all text fields
      applyTextFormatter(aField, filter);
      applyTextFormatter(bField, filter);
      applyTextFormatter(cField, filter);
      applyTextFormatter(dField, filter);
      applyTextFormatter(aVector, filter);
      applyTextFormatter(bVector, filter);

      // Creating a grid pane to organize the text fields in a square layout
      gridPane.add(aField, 0, 0);
      gridPane.add(bField, 1, 0);
      gridPane.add(cField, 0, 1);
      gridPane.add(dField, 1, 1);
      gridPane.add(aVector, 2, 0);
      gridPane.add(bVector, 2, 1);

      // Adding grid pane to the matrix box
      matrixBox.getChildren().add(gridPane);

    } else {
      TextField c0Field = new TextField();
      TextField c1Field = new TextField();
      c0Field.setPromptText("c0");
      c0Field.setStyle("-fx-background-color: #449933;");
      c1Field.setPromptText("c1");
      c1Field.setStyle("-fx-background-color: #449933;");

      // Apply text formatter to text fields
      applyTextFormatter(c0Field, filter);
      applyTextFormatter(c1Field, filter);

      gridPane.add(c0Field, 0, 0);
      gridPane.add(c1Field, 1, 0);

      matrixBox.getChildren().add(gridPane);
    }
    return matrixBox;
  }


  /**
   * Method to create a VBox containing the min and max fields.
   *
   * @return A VBox containing the min and max fields.
   */
  private VBox createMinMaxFields() {
    TextField minx0 = new TextField("");
    TextField minx1 = new TextField("");

    TextField maxx0 = new TextField("");
    TextField maxx1 = new TextField("");

    minx0.setStyle("-fx-background-color: #8888ff");
    minx0.setPromptText("Min x0");
    minx1.setStyle("-fx-background-color: #8888ff");
    minx1.setPromptText("Min x1");

    maxx0.setStyle("-fx-background-color: #ff8888");
    maxx0.setPromptText("Max x0");
    maxx1.setStyle("-fx-background-color: #ff8888");
    maxx1.setPromptText("Max x1");

    applyTextFormatter(minx0, filter);
    applyTextFormatter(minx1, filter);
    applyTextFormatter(maxx1, filter);
    applyTextFormatter(maxx0, filter);

    GridPane gridPane = new GridPane();
    gridPane.setHgap(10);
    gridPane.setVgap(10);
    gridPane.add(minx0, 0, 0);
    gridPane.add(minx1, 1, 0);
    gridPane.add(maxx0, 3, 0);
    gridPane.add(maxx1, 4, 0);

    return new VBox(gridPane);
  }

  /**
   * Method to parse the values from the text fields.
   */
  public void parseValues() {
    if (choiceBox.getValue().equalsIgnoreCase("Affine2D")) {
      parseValuesAffine();
    } else {
      parseValuesJulia();
    }
  }

  /**
   * Method to parse the values from the affine transformation text fields.
   */
  private void parseValuesAffine() {
    for (int i = 0; i < matrixBox.getChildren().size(); i++) {
      GridPane gridPane = (GridPane) matrixBox.getChildren().get(i);
      StringBuilder line = new StringBuilder();
      for (int j = 0; j < gridPane.getChildren().size(); j++) {
        TextField textField = (TextField) gridPane.getChildren().get(j);
        line.append(textField.getText()).append(",");
        textField.setText("");
      }
      line.deleteCharAt(line.length() - 1);
      allMatrixValues.add(line.toString());
    }
  }

  /**
   * Method to parse the values from the Julia transformation text fields.
   */
  private void parseValuesJulia() {
    for (int i = 0; i < matrixBox.getChildren().size(); i++) {
      VBox gridPane = (VBox) matrixBox.getChildren().get(i);
      StringBuilder line = new StringBuilder();
      GridPane gridPane1 = (GridPane) gridPane.getChildren().get(0);
      for (int j = 0; j < gridPane1.getChildren().size(); j++) {
        TextField textField = (TextField) gridPane1.getChildren().get(j);
        line.append(textField.getText()).append(",");
        textField.setText("");
      }
      line.deleteCharAt(line.length() - 1);
      allMatrixValues.add(line.toString());
    }
  }

  /**
   * Method to apply a text formatter to a text field.
   *
   * @param textField The text field to apply the text formatter to.
   * @param filter    The filter to apply to the text field.
   */
  private static void applyTextFormatter(TextField textField, UnaryOperator<Change> filter) {
    textField.setTextFormatter(new TextFormatter<>(filter));
  }

  /**
   * Method to parse the header values from the text fields.
   */
  public void parseHeaderValues() {
    String transformationType = choiceBox.getValue();
    allHeaderValues.add(transformationType);

    GridPane minMaxGridPane = (GridPane) minMaxBox.getChildren().get(0);
    TextField minx0 = (TextField) minMaxGridPane.getChildren().get(0);
    TextField minx1 = (TextField) minMaxGridPane.getChildren().get(1);
    TextField maxx0 = (TextField) minMaxGridPane.getChildren().get(2);
    TextField maxx1 = (TextField) minMaxGridPane.getChildren().get(3);
    allHeaderValues.add(minx0.getText() + "," + minx1.getText());
    allHeaderValues.add(maxx0.getText() + "," + maxx1.getText());
  }

  /**
   * Method to combine all values into one list.
   */
  public void combineAllValues() {
    this.allValues.addAll(allHeaderValues);
    this.allValues.addAll(allMatrixValues);
  }

  /**
   * Method to add a new layer to the transformation matrix.
   *
   * @param handler The handler to add.
   */
  public void addNewLayerButtonListener(Consumer<Void> handler) {
    addNewLayer.setOnAction(e -> handler.accept(null));
  }

  /**
   * Method to add a listener to the choice box.
   *
   * @param listener The listener to add.
   */
  public void addChoiceBoxListener(ChangeListener<String> listener) {
    choiceBox.valueProperty().addListener(listener);
  }

  /**
   * Method to get the matrix box.
   *
   * @return The matrix box.
   */
  public VBox getMatrixBox() {
    return matrixBox;
  }
}
