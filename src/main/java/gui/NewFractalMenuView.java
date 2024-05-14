package gui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NewFractalMenuView {

  private VBox matrixBox;
  private VBox minMaxBox;
  private ChoiceBox<String> choiceBox;
  private List<String> allValues;

  public NewFractalMenuView() {
    this.allValues = new ArrayList<>();
  }


  public void showPopupMenu() {
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

    this.choiceBox = new ChoiceBox<>();

    choiceBox.getItems().addAll("Julia", "Affine2D");
    choiceBox.setValue("Affine2D");

    this.minMaxBox = createMinMaxFields();
    this.matrixBox = createMatrixBox(choiceBox.getValue());

    choiceBox.setOnAction(e -> {
      matrixBox.getChildren().clear();
      matrixBox.getChildren().add(createMatrixBox(choiceBox.getValue()));
    });

    Button saveButton = new Button("Save Fractal");
    saveButton.setStyle("-fx-background-color: #66bb66;");

    Separator separator = new Separator();
    separator.setMaxWidth(Double.MAX_VALUE);
    separator.setStyle("-fx-background-color: white;");

    popupLayout.getChildren().addAll(title, choiceBox, minMaxBox, separator, matrixBox, saveButton);

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

    // Inside the saveButton.setOnAction method, call the callback
    saveButton.setOnAction(e -> {
      parseValues();
      popupStage.close();
    });
  }

  public List<String> getAllValues(){
    this.allValues.forEach(System.out::println);
    System.out.println("hallo" + allValues.size());
    return this.allValues;
  }


  private static VBox createMatrixBox(String value){
    VBox matrixBox = new VBox();
    GridPane gridPane = new GridPane();
    gridPane.setHgap(10);
    gridPane.setVgap(10);
    if(value.equals("Affine2D")) {

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

  private static VBox createMinMaxFields(){
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

    GridPane gridPane = new GridPane();
    gridPane.setHgap(10);
    gridPane.setVgap(10);
    gridPane.add(minx0, 0, 0);
    gridPane.add(minx1, 1, 0);
    gridPane.add(maxx0, 3, 0);
    gridPane.add(maxx1, 4, 0);

    return new VBox(gridPane);
  }

  private void parseValues(){
    // Get the selected value from the choiceBox
    String transformationType = choiceBox.getValue();
    allValues.add(transformationType);

    GridPane minMaxGridPane = (GridPane) minMaxBox.getChildren().get(0); // Assuming there's only one child (GridPane)
    TextField minx0 = (TextField) minMaxGridPane.getChildren().get(0);
    TextField minx1 = (TextField) minMaxGridPane.getChildren().get(1);
    TextField maxx0 = (TextField) minMaxGridPane.getChildren().get(2);
    TextField maxx1 = (TextField) minMaxGridPane.getChildren().get(3);
    allValues.add(minx0.getText() + "," + minx1.getText());
    allValues.add(maxx0.getText()+","+ maxx1.getText());

    // Get values from matrixBox
    for (int i = 0; i < matrixBox.getChildren().size(); i++) {
      GridPane gridPane = (GridPane) matrixBox.getChildren().get(i);
      StringBuilder line = new StringBuilder();
      for (int j = 0; j < gridPane.getChildren().size(); j++) {
        TextField textField = (TextField) gridPane.getChildren().get(j);
        line.append(textField.getText()).append(",");
      }
      allValues.add(line.toString());
    }
  }


}
