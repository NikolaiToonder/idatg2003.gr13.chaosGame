package gui;

<<<<<<< HEAD

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

=======
>>>>>>> b55ce8dd8723a47b1d629e6f0b715c7ce72d4250

import chaosgameclasses.ChaosGameDescription;
import javafx.application.Platform;
<<<<<<< HEAD

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Parent;

=======
>>>>>>> b55ce8dd8723a47b1d629e6f0b715c7ce72d4250
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
<<<<<<< HEAD

import javafx.stage.Stage;

import matrix.Matrix2x2;
import vectors.Complex;
import vectors.Vector2D;

=======
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
>>>>>>> b55ce8dd8723a47b1d629e6f0b715c7ce72d4250

/**
 * JavaFX class for viewing the main simulation after the login page.
 */
public class ChaosGameView {

<<<<<<< HEAD

=======
>>>>>>> b55ce8dd8723a47b1d629e6f0b715c7ce72d4250
  private SimulationView simulationView;
  private final DescriptionFactory descriptionFactory = new DescriptionFactory();
  private final Vector2D standardizedView = new Vector2D(0.5, 0.5);
  private ChaosGameDescription.ChaosGame chaosGame = new ChaosGameDescription.ChaosGame(descriptionFactory.createAffine2D("Sierpinski"), 500,
<<<<<<< HEAD
          500, standardizedView);


  ChoiceBox<String> choiceBoxMatrix = new ChoiceBox<>();
  private int currentTransformation = 1;
  private boolean showVector = false;

  HBox textFieldsBox = createTextFieldsBox();



=======
      500, standardizedView);
  ChoiceBox<String> choiceBoxMatrix = new ChoiceBox<>();
  private int currentTransformation = 1;
  HBox textFieldsBox = createTextFieldsBox();

>>>>>>> b55ce8dd8723a47b1d629e6f0b715c7ce72d4250
  String whiteColor = "-fx-text-fill: white;";


  public ChaosGameView(Consumer<Stage> backToMenuAction) {
<<<<<<< HEAD

=======
>>>>>>> b55ce8dd8723a47b1d629e6f0b715c7ce72d4250
  }

  public Parent createContent(Stage primaryStage) {
    simulationView = new SimulationView();
    updateChoiceBoxMatrix();
<<<<<<< HEAD
=======
    updateTextFields();
>>>>>>> b55ce8dd8723a47b1d629e6f0b715c7ce72d4250
    simulationView.setStyle("-fx-background-color: #2b2d31;");

    // Setup sliders and controls
    Label iterationsLabel = new Label("Iterations: ");
    iterationsLabel.setStyle(whiteColor);
    Label zoomInLabel = new Label("Zoom In");
    zoomInLabel.setStyle(whiteColor);

    Slider iterationSlider = new Slider(100, 100000, 50000);
    Slider zoomSlider = new Slider(1, 10, 1);
    ChoiceBox<String> choiceBox = new ChoiceBox<>();

    // Add items to the ChoiceBox
    choiceBox.getItems().addAll("Julia", "Sierpinski", "Barnsley");

    // Set a default selection
    choiceBox.setValue("Sierpinski");

    simulationView.updateSimulationView(chaosGame, (int) iterationSlider.getValue());

    // Configure your slider and add listeners to update the fractal view
    iterationSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
      iterationsLabel.setText("Iterations: " + newValue.intValue());
      simulationView.updateSimulationView(chaosGame, newValue.intValue()); // Call to update the simulation view
    });

    zoomSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
      zoomInLabel.setText("Zoom In: " + Math.round(newValue.doubleValue()) + "x");
      chaosGame.zoom(newValue.doubleValue());
      simulationView.updateSimulationView(chaosGame, (int) iterationSlider.getValue());
    });

    iterationSlider.setStyle("-fx-control-inner-background: #2b2d31;");
    iterationSlider.setStyle("-fx-background-color: #32e816;");
<<<<<<< HEAD
=======


>>>>>>> b55ce8dd8723a47b1d629e6f0b715c7ce72d4250
    iterationsLabel.setStyle(whiteColor);
    zoomInLabel.setStyle(whiteColor);

    choiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
<<<<<<< HEAD
      this.chaosGame = new ChaosGameDescription.ChaosGame(descriptionFactory.createAffine2D(newValue), 500, 500, standardizedView);
      chaosGame.zoom(zoomSlider.getValue());
      updateChoiceBoxMatrix();
      if (newValue.equals("Julia")) {
        updateTextFieldsJulia();
      } else {
        updateTextFieldsAffine();
      }
=======
      this.chaosGame = new ChaosGameDescription.ChaosGame(descriptionFactory.createAffine2D(newValue), 500, 500,
          standardizedView);
      chaosGame.zoom(zoomSlider.getValue());
      updateChoiceBoxMatrix();
>>>>>>> b55ce8dd8723a47b1d629e6f0b715c7ce72d4250
      simulationView.updateSimulationView(chaosGame, (int) iterationSlider.getValue());
    });

    choiceBoxMatrix.valueProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null) {
        String[] parts = newValue.split(" ");
        if (parts.length > 1) {
          this.currentTransformation = Integer.parseInt(parts[1]);
<<<<<<< HEAD
          if (this.chaosGame.getDescription().getTypeOfTransformation().equals("Julia")) {
            updateTextFieldsJulia();
          } else {
            updateTextFieldsAffine();
          }
=======
          updateTextFields();
>>>>>>> b55ce8dd8723a47b1d629e6f0b715c7ce72d4250
        }
      }
    });

<<<<<<< HEAD
    // Draw button
    Button draw = new Button("Draw");
    draw.setOnAction(e -> {
      String typeOfTransform = choiceBox.getValue();
      String choiceToEdit = choiceBoxMatrix.getValue();
      List<TextField> textFieldsList = textFieldsBox.getChildren().stream()
              .filter(node -> node instanceof TextField)
              .map(node -> (TextField) node)
              .collect(Collectors.toList());
      this.chaosGame.getDescription().writeToFile(typeOfTransform, choiceToEdit, textFieldsList);
      this.chaosGame = new ChaosGameDescription.ChaosGame(descriptionFactory.createAffine2D(choiceBox.getValue()), 500, 500, standardizedView);
      if (typeOfTransform.equals("Julia")) {
        updateTextFieldsJulia();
      } else {
        updateTextFieldsAffine();
      }
      simulationView.updateSimulationView(chaosGame, (int) iterationSlider.getValue());
    });
    draw.setStyle("-fx-background-color: #32e816;");
    draw.setTextFill(javafx.scene.paint.Color.WHITE);

=======
>>>>>>> b55ce8dd8723a47b1d629e6f0b715c7ce72d4250
    // Back to Menu button
    Button backToMenuButton = new Button("Close application");
    backToMenuButton.setOnAction(e -> Platform.exit());
    backToMenuButton.setStyle("-fx-background-color: #f55353;");
    backToMenuButton.setTextFill(javafx.scene.paint.Color.WHITE);

<<<<<<< HEAD
    VBox controlsPane = new VBox(10, iterationsLabel, iterationSlider, zoomInLabel, zoomSlider, choiceBox, draw, backToMenuButton);
    controlsPane.setAlignment(Pos.CENTER);
=======

    VBox controlsPane = new VBox(10, iterationsLabel, iterationSlider, zoomInLabel, zoomSlider, choiceBox, backToMenuButton); // Add all controls here
    controlsPane.setAlignment(Pos.CENTER); // Align controls to the right
>>>>>>> b55ce8dd8723a47b1d629e6f0b715c7ce72d4250
    controlsPane.setPrefHeight(300);

    textFieldsBox.setPadding(new Insets(20, 20, 20, 20));

    VBox simulationAndInfoBox = new VBox(simulationView, textFieldsBox);
<<<<<<< HEAD
    simulationAndInfoBox.setVgrow(simulationView, Priority.ALWAYS);

    BorderPane root = new BorderPane();
    root.setRight(controlsPane);
    root.setCenter(simulationAndInfoBox);
    root.setStyle("-fx-background-color: #2b2d31;");

=======
    simulationAndInfoBox.setVgrow(simulationView, Priority.ALWAYS); // Allow simulationView to grow

    BorderPane root = new BorderPane();
    root.setRight(controlsPane); // Set the controls pane on the right
    root.setCenter(simulationAndInfoBox); // Set simulationView and textFieldsBox wrapper in the center

    // Set background color for anchor pane
    root.setStyle("-fx-background-color: #2b2d31;");

    // Add listeners to prevent resizing smaller than specified minimum
>>>>>>> b55ce8dd8723a47b1d629e6f0b715c7ce72d4250
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

    return root;
  }

  private TextField createTextField(String labelText) {
    TextField textField = new TextField();
    textField.setPromptText(labelText);
    return textField;
  }

  private HBox createTextFieldsBox() {
    TextField a00TextField = createTextField("a00");
    TextField a01TextField = createTextField("a01");
    TextField a10TextField = createTextField("a10");
    TextField a11TextField = createTextField("a11");

<<<<<<< HEAD

=======
>>>>>>> b55ce8dd8723a47b1d629e6f0b715c7ce72d4250
    // Create an HBox to hold the text fields
    HBox textFieldsBox = new HBox(10);
    textFieldsBox.getChildren().addAll(a00TextField, a01TextField, a10TextField, a11TextField, this.choiceBoxMatrix);
    textFieldsBox.setPadding(new Insets(0, 0, 20, 0));
    return textFieldsBox;
  }

<<<<<<< HEAD

=======
>>>>>>> b55ce8dd8723a47b1d629e6f0b715c7ce72d4250
  private void updateChoiceBoxMatrix() {
    this.choiceBoxMatrix.getItems().clear();
    for (int i = 0; i < this.chaosGame.getDescription().getNumberOfTransforms(); i++) {
      choiceBoxMatrix.getItems().add("Matrix " + (i + 1));
<<<<<<< HEAD
      choiceBoxMatrix.getItems().add("Vector " + (i + 1));
=======
>>>>>>> b55ce8dd8723a47b1d629e6f0b715c7ce72d4250
    }
    choiceBoxMatrix.setValue("Matrix 1");
    this.currentTransformation = 1;
  }

<<<<<<< HEAD
  private void updateTextFieldsAffine() {
    this.chaosGame.getDescription().setTypeOfTransformation("Affine");
=======
  private void updateTextFields() {
>>>>>>> b55ce8dd8723a47b1d629e6f0b715c7ce72d4250
    List<Matrix2x2> matrices = this.chaosGame.getDescription().getMatrixList();
    List<Vector2D> vectors = this.chaosGame.getDescription().getVectorList();

    Matrix2x2 matrix = matrices.get(currentTransformation - 1);
    Vector2D vector = vectors.get(currentTransformation - 1);

<<<<<<< HEAD
    String chosenText = this.choiceBoxMatrix.getValue().split(" ")[0];
    this.showVector = chosenText.equals("Vector");
    if (!showVector) {
      textFieldsBox.getChildren().forEach(node -> {
        if (node instanceof TextField textField) {
          if (textField.getPromptText().equals("a00")) {
            textField.setText(String.valueOf(matrix.getA00()));
          } else if (textField.getPromptText().equals("a01")) {
            textField.setText(String.valueOf(matrix.getA01()));
          } else if (textField.getPromptText().equals("a10")) {
            textField.disableProperty().set(false);
            textField.setText(String.valueOf(matrix.getA10()));
          } else if (textField.getPromptText().equals("a11")) {
            textField.setText(String.valueOf(matrix.getA11()));
            textField.disableProperty().set(false);
          }
        }
      });
    } else {
      textFieldsBox.getChildren().forEach(node -> {
        if (node instanceof TextField textField) {
          if (textField.getPromptText().equals("a00")) {
            textField.setText(String.valueOf(vector.getX0()));
          } else if (textField.getPromptText().equals("a01")) {
            textField.setText(String.valueOf(vector.getX1()));
          } else if (textField.getPromptText().equals("a10") || textField.getPromptText().equals("a11")) {
            textField.setText("-");
            textField.disableProperty().set(true);
          }
        }
      });
    }
  }

  private void updateTextFieldsJulia() {
    this.chaosGame.getDescription().setTypeOfTransformation("Julia");
    List<Complex> complexNumbers = this.chaosGame.getDescription().getComplexNumbers();

    Complex complex = complexNumbers.get(currentTransformation - 1);

    textFieldsBox.getChildren().forEach(node -> {
      if (node instanceof TextField textField) {
        if (textField.getPromptText().equals("a00")) {
          textField.setText(String.valueOf(complex.getReal()));
        } else if (textField.getPromptText().equals("a01")) {
          textField.setText(String.valueOf(complex.getImaginary()));
        } else if (textField.getPromptText().equals("a10") || textField.getPromptText().equals("a11")) {
          textField.setText("-");
          textField.disableProperty().set(true);
        }
      }
    });
  }

}
=======
    textFieldsBox.getChildren().forEach(node -> {
      if (node instanceof TextField) {
        TextField textField = (TextField) node;
        if (textField.getPromptText().equals("a00")) {
          textField.setText(String.valueOf(matrix.getA00()));
        } else if (textField.getPromptText().equals("a01")) {
          textField.setText(String.valueOf(matrix.getA01()));
        } else if (textField.getPromptText().equals("a10")) {
          textField.setText(String.valueOf(matrix.getA10()));
        } else if (textField.getPromptText().equals("a11")) {
          textField.setText(String.valueOf(matrix.getA11()));
        }
      }
    });

  }


}
>>>>>>> b55ce8dd8723a47b1d629e6f0b715c7ce72d4250
