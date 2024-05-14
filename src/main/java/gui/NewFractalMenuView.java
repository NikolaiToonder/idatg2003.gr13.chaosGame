package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NewFractalMenuView {
  private VBox matrixBox;

  public static void showPopupMenu() {
    // Create the pop-up stage
    Stage popupStage = new Stage();
    // Create the layout for the pop-up
    VBox popupLayout = new VBox(10);
    popupLayout.setAlignment(Pos.CENTER);
    popupLayout.setPadding(new Insets(20));
    popupLayout.setStyle("-fx-background-color: #2b2d31;");

    Label title = new Label("Create New Fractal");
    title.setTextFill(Color.WHITE);
    title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");


    ChoiceBox<String> choiceBox = new ChoiceBox<>();

    choiceBox.getItems().addAll("Complex", "Affine");
    choiceBox.setValue("Complex");

    VBox matrixBox = createMatrixBox(choiceBox.getValue());

    choiceBox.setOnAction(e -> {
      matrixBox.getChildren().clear();
      matrixBox.getChildren().add(createMatrixBox(choiceBox.getValue()));
    });

    Button saveButton = new Button("Save Fractal");
    saveButton.setStyle("-fx-background-color: #66bb66;");
    saveButton.setOnAction(e -> popupStage.close());

    popupLayout.getChildren().addAll(title,choiceBox,matrixBox, saveButton);

    // Create the scene for the pop-up
    Scene popupScene = new Scene(popupLayout, 500, 400);

    // Set the scene for the pop-up stage
    popupStage.setScene(popupScene);

    // Set modality to APPLICATION_MODAL to make the pop-up block interactions with other windows
    popupStage.initModality(Modality.APPLICATION_MODAL);

    // Set the title for the pop-up stage
    popupStage.setTitle("New Fractal Menu");

    // Show the pop-up stage
    popupStage.show();
  }

  private static VBox createMatrixBox(String value){
    VBox matrixBox = new VBox();
    GridPane gridPane = new GridPane();
    gridPane.setHgap(10);
    gridPane.setVgap(10);
    if(value.equals("Affine")) {

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

      gridPane.add(c0Field, 0, 0);
      gridPane.add(c1Field, 1, 0);

      matrixBox.getChildren().add(gridPane);
    }
    return matrixBox;
  }

}
