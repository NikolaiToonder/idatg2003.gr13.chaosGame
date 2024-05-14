package gui;


import chaosgameclasses.ChaosGame;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Parent;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import javafx.stage.Modality;
import javafx.stage.Stage;

import matrix.Matrix2x2;
import vectors.Complex;
import vectors.Vector2D;


/**
 * JavaFX class for viewing the main simulation after the login page.
 */
public class ChaosGameView {


    private SimulationView simulationView;
    private final DescriptionFactory descriptionFactory = new DescriptionFactory();
    private final Vector2D standardizedView = new Vector2D(0.5, 0.5);
    private ChaosGame chaosGame = new ChaosGame(descriptionFactory.createAffine2D("Sierpinski"), 500,
            500, standardizedView);
    ChoiceBox<String> choiceBoxMatrix = new ChoiceBox<>();
    private int currentTransformation = 1;
    private boolean showVector = false;
    HBox textFieldsBox = createTextFieldsBox();

    public ChaosGameView() {

    }

    public Parent createContent(Stage primaryStage) {
        simulationView = new SimulationView();
        updateChoiceBoxMatrix();
        //runs because the first transformation is always affine
        updateTextFieldsAffine();

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

        Button draw = new Button("Draw");

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

        choiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.chaosGame = new ChaosGame(descriptionFactory.createAffine2D(newValue), 500, 500,
                    standardizedView);
            chaosGame.zoom(zoomSlider.getValue());
            simulationView.updateSimulationView(chaosGame, (int) iterationSlider.getValue());
          if (!newValue.equals("Julia")) {
              updateChoiceBoxMatrix();
              updateTextFieldsAffine();
          } else {
              this.showVector = false;
              updateChoiceBoxMatrix();
              updateTextFieldsJulia();
          }
          updateChoiceBoxMatrix();
        });

        choiceBoxMatrix.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String[] parts = newValue.split(" ");
                if (parts.length > 1 && this.chaosGame.getDescription().getTypeOfTransformation().equals("Affine")) {
                    this.currentTransformation = Integer.parseInt(parts[1]);
                    updateTextFieldsAffine();
                }
            }
        });
        draw.setOnAction(e -> {
            String typeOfTransform = choiceBox.getValue();
            String choiceToEdit = choiceBoxMatrix.getValue();
            List<TextField> textFieldsList = textFieldsBox.getChildren().stream()
                    .filter(node -> node instanceof TextField)
                    .map(node -> (TextField) node)
                    .collect(Collectors.toList());
            this.chaosGame.getDescription().writeToFile(typeOfTransform, choiceToEdit, textFieldsList);
            this.chaosGame = new ChaosGame(descriptionFactory.createAffine2D(choiceBox.getValue()), 500, 500,
                    standardizedView);
            if(typeOfTransform.equals("Julia")){
                updateTextFieldsJulia();
            } else {
                updateTextFieldsAffine();
            }
            simulationView.updateSimulationView(chaosGame, (int) iterationSlider.getValue());
        });


        // New button for the pop-up menu
        Button popupButton = new Button("Show Pop-up Menu");
        popupButton.setOnAction(e -> showPopupMenu(primaryStage));


        // Back to Menu button
        Button backToMenuButton = new Button("Close application");
        backToMenuButton.setOnAction(e -> Platform.exit());
        backToMenuButton.setStyle("-fx-background-color: #f55353;");
        backToMenuButton.setTextFill(javafx.scene.paint.Color.WHITE);


        VBox controlsPane = new VBox(10, iterationsLabel, iterationSlider, zoomInLabel, zoomSlider, choiceBox,draw, backToMenuButton, popupButton); // Add all controls here
        controlsPane.setAlignment(Pos.CENTER); // Align controls to the right
        controlsPane.setPrefHeight(300);

        textFieldsBox.setPadding(new Insets(20, 20, 20, 20));

        VBox simulationAndInfoBox = new VBox(simulationView, textFieldsBox);
        VBox.setVgrow(simulationView, Priority.ALWAYS); // Allow simulationView to grow

        BorderPane root = new BorderPane();
        root.setRight(controlsPane); // Set the controls pane on the right
        root.setCenter(simulationAndInfoBox); // Set simulationView and textFieldsBox wrapper in the center

        // Set background color for anchor pane
        root.setStyle("-fx-background-color: #2b2d31;");

        // Add listeners to prevent resizing smaller than specified minimum
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
            choiceBoxMatrix.getItems().add("Vector " + (i+1));
        }
        choiceBoxMatrix.setValue("Matrix 1");
        this.currentTransformation = 1;
    }

    private void updateTextFieldsAffine() {
        this.chaosGame.getDescription().setTypeOfTransformation("Affine");
        List<Matrix2x2> matrices = this.chaosGame.getDescription().getMatrixList();
        List<Vector2D> vectors = this.chaosGame.getDescription().getVectorList();

        Matrix2x2 matrix = matrices.get(currentTransformation - 1);
        Vector2D vector = vectors.get(currentTransformation - 1);

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
            });} else {
            textFieldsBox.getChildren().forEach(node -> {
                if (node instanceof TextField textField) {
                    if (textField.getPromptText().equals("a00")) {
                        textField.setText(String.valueOf(vector.getX0()));
                    } else if (textField.getPromptText().equals("a01")) {
                        textField.setText(String.valueOf(vector.getX1()));
                    }else if (textField.getPromptText().equals("a10")||textField.getPromptText().equals("a11")){
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
                }else if (textField.getPromptText().equals("a10")||textField.getPromptText().equals("a11")){
                    textField.setText("-");
                    textField.disableProperty().set(true);
                }
            }
        });
    }
    private void showPopupMenu(Stage primaryStage) {
        // Create the pop-up stage
        Stage popupStage = new Stage();
        // Create the layout for the pop-up
        VBox popupLayout = new VBox(10);
        popupLayout.setAlignment(Pos.CENTER);
        popupLayout.setPadding(new Insets(20));

        // Add content to the pop-up layout
        // Example: Add labels, buttons, or any other controls you want in the pop-up menu
        Label popupLabel = new Label("This is a pop-up menu!");
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> popupStage.close());

        popupLayout.getChildren().addAll(popupLabel, closeButton);

        // Create the scene for the pop-up
        Scene popupScene = new Scene(popupLayout, 200, 150);

        // Set the scene for the pop-up stage
        popupStage.setScene(popupScene);

        // Set modality to APPLICATION_MODAL to make the pop-up block interactions with other windows
        popupStage.initModality(Modality.APPLICATION_MODAL);

        // Set the title for the pop-up stage
        popupStage.setTitle("Pop-up Menu");

        // Show the pop-up stage
        popupStage.show();
    }
}