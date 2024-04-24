package gui;


import chaosgameclasses.ChaosGame;
import controller.ChaosGameObserver;
import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.geometry.Pos;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import vectors.Vector2D;


/**
 * JavaFX class for viewing the main simulation after the login page.
 */
public class ChaosGameView {


  private SimulationView simulationView;
  private DescriptionFactory descriptionFactory = new DescriptionFactory();
  private ChaosGameObserver chaosGameObserver = new ChaosGameObserver();
  private Vector2D standardizedView = new Vector2D(0.5, 0.5);
  private ChaosGame chaosGame = new ChaosGame(descriptionFactory.createAffine2D("Sierpinski"), 500,
      500, standardizedView);
  private String backgroundColor;
  private Consumer<Stage> backToMenuAction;

  public ChaosGameView(String backgroundColor, Consumer<Stage> backToMenuAction) {
    this.backgroundColor = backgroundColor;
    this.backToMenuAction = backToMenuAction;
  }

  public Parent createContent(Stage primaryStage) {
    simulationView = new SimulationView();

    // Setup sliders and controls
    Label iterationsLabel = new Label("Iterations: ");
    iterationsLabel.setStyle("-fx-text-fill: white;");
    Label zoomInLabel = new Label("Zoom In");
    zoomInLabel.setStyle("-fx-text-fill: white;");

    Slider iterationSlider = new Slider(100, 100000, 50000);
    Slider zoomSlider = new Slider(1, 10, 1);
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


    VBox controlsPane = new VBox(10, iterationsLabel, iterationSlider, zoomInLabel, zoomSlider); // Add all controls here
    controlsPane.setAlignment(Pos.CENTER); // Align controls to the right

    // Back to Menu button
    Button backToMenuButton = new Button("Back to Menu");
    backToMenuButton.setOnAction(e -> backToMenuAction.accept(primaryStage));


    VBox backToMenuPane = new VBox(backToMenuButton); // VBox containing back to menu button
    backToMenuPane.setAlignment(Pos.BOTTOM_RIGHT); // Align the button to the bottom right

    BorderPane root = new BorderPane();
    AnchorPane anchor = new AnchorPane(root);
    root.setCenter(simulationView); // Set the simulation view in the center
    root.setRight(controlsPane); // Set the controls pane on the right
    root.setBottom(backToMenuPane); // Set the back to menu button at the bottom


    // Set background color
    root.setStyle("-fx-background-color: " + backgroundColor + ";");

    return anchor;
  }

}