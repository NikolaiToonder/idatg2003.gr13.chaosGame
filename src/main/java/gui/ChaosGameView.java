package gui;



import chaosgameclasses.ChaosGame;
import com.sun.tools.javac.Main;
import controller.ChaosGameObserver;
import java.util.List;
import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Parent;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import matrix.Matrix2x2;
import transformations.Transform2D;
import vectors.Vector2D;


/**
 * JavaFX class for viewing the main simulation after the login page.
 */
public class ChaosGameView {


  private SimulationView simulationView;
  private DescriptionFactory descriptionFactory = new DescriptionFactory();
  private Vector2D standardizedView = new Vector2D(0.5, 0.5);
  private ChaosGame chaosGame = new ChaosGame(descriptionFactory.createAffine2D("Sierpinski"), 500,
      500, standardizedView);
 // private String backgroundColor;
  private Consumer<Stage> backToMenuAction;
  ChoiceBox<String> choiceBoxMatrix = new ChoiceBox<>();
  private int currentTransformation = 1;
  HBox textFieldsBox = createTextFieldsBox();

  public ChaosGameView(Consumer<Stage> backToMenuAction) {
    this.backToMenuAction = backToMenuAction;
  }

  public Parent createContent(Stage primaryStage) {
    simulationView = new SimulationView();
    updateChoiceBoxMatrix();
    updateTextFields();
    simulationView.setStyle("-fx-background-color: #2b2d31;");

    // Setup sliders and controls
    Label iterationsLabel = new Label("Iterations: ");
    iterationsLabel.setStyle("-fx-text-fill: white;");
    Label zoomInLabel = new Label("Zoom In");
    zoomInLabel.setStyle("-fx-text-fill: white;");

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

    iterationsLabel.setStyle("-fx-text-fill: white;");
    zoomInLabel.setStyle("-fx-text-fill: white;");

    choiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
      this.chaosGame = new ChaosGame(descriptionFactory.createAffine2D(newValue), 500, 500,
          standardizedView);
      chaosGame.zoom(zoomSlider.getValue());
      updateChoiceBoxMatrix();
      simulationView.updateSimulationView(chaosGame, (int) iterationSlider.getValue());
    });

    choiceBoxMatrix.valueProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null) {
        String[] parts = newValue.split(" ");
        if (parts.length > 1) {
          this.currentTransformation = Integer.parseInt(parts[1]);
          updateTextFields();
        }
      }
    });

    // Back to Menu button
    Button backToMenuButton = new Button("Back to Menu");
    backToMenuButton.setOnAction(e -> backToMenuAction.accept(primaryStage));

    backToMenuButton.setStyle("-fx-background-color: #f4f4f4;");
    backToMenuButton.setStyle("-fx-text-fill: black;");

    VBox controlsPane = new VBox(10, iterationsLabel, iterationSlider, zoomInLabel, zoomSlider, choiceBox, backToMenuButton ); // Add all controls here
    controlsPane.setAlignment(Pos.CENTER); // Align controls to the right
    controlsPane.setPrefHeight(300);

    textFieldsBox.setPadding(new Insets(20, 20, 20, 20));

    BorderPane root = new BorderPane();
    AnchorPane anchor = new AnchorPane(root);
    root.setCenter(simulationView); // Set the simulation view in the center
    root.setRight(controlsPane); // Set the controls pane on the right
    root.setBottom(textFieldsBox);// Set the back to menu button at the bottom

    // Set background color for anchor pane
    anchor.setStyle("-fx-background-color: #2b2d31;");

    anchor.getChildren().add(controlsPane);

    AnchorPane.setBottomAnchor(textFieldsBox, 0.0);
    AnchorPane.setRightAnchor(controlsPane, 0.0);



    return anchor;
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

    // Create an HBox to hold the text fields
    HBox textFieldsBox = new HBox(10);
    textFieldsBox.getChildren().addAll(a00TextField, a01TextField, a10TextField, a11TextField, this.choiceBoxMatrix);
    textFieldsBox.setPadding(new Insets(0, 0, 20, 0));
    return textFieldsBox;
  }


  private void updateChoiceBoxMatrix() {
    this.choiceBoxMatrix.getItems().clear();
    for (int i = 0; i < this.chaosGame.getDescription().getNumberOfTransforms(); i++) {
      choiceBoxMatrix.getItems().add("Matrix " + (i+1));
    }
    choiceBoxMatrix.setValue("Matrix 1");
    this.currentTransformation = 1;
  }

  private void updateTextFields() {
    List<Matrix2x2> matrices = this.chaosGame.getDescription().getMatrixList();
    List<Vector2D> vectors = this.chaosGame.getDescription().getVectorList();

    Matrix2x2 matrix = matrices.get(currentTransformation - 1);
    Vector2D vector = vectors.get(currentTransformation - 1);

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


  public int getCurrentTransformation() {
    return currentTransformation;
  }

  public boolean isScreenResized() {
    return simulationView.isScreenResized();
  }
}