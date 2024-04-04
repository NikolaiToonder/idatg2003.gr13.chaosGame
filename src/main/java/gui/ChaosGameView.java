package gui;


import chaosgameclasses.ChaosGame;
import controller.ChaosGameObserver;
import javafx.geometry.Pos;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import vectors.Vector2D;

public class ChaosGameView {


  private SimulationView simulationView;
  private DescriptionFactory descriptionFactory = new DescriptionFactory();
  private ChaosGameObserver chaosGameObserver = new ChaosGameObserver();
  private ChaosGame chaosGame = new ChaosGame(descriptionFactory.createAffine2D("Barnsley"), 500,
      500, new Vector2D(0.5, 0.5));
  private String backgroundColor;

  public ChaosGameView(String backgroundColor) {
    this.backgroundColor = backgroundColor;
  }

  public Parent createContent() {
    simulationView = new SimulationView(); // Initialize SimulationView

    // Wrap SimulationView in another pane for padding if necessary
    StackPane simulationWrapper = new StackPane(simulationView);
    simulationWrapper.setMaxHeight(500); // Set a maximum height for the simulation view (optional)
    simulationWrapper.setMaxWidth(500);

    // Setup sliders and controls
    Label iterationsLabel = new Label("Iterations");
    iterationsLabel.setStyle("-fx-text-fill: white;");
    Label zoomInLabel = new Label("Zoom In");
    zoomInLabel.setStyle("-fx-text-fill: white;");

    Slider iterationSlider = new Slider(100, 1000000, 1000);
    Slider zoomInSlider = new Slider(0.1, 1, 0.5);

    // Configure your slider and add listeners to update the fractal view
    iterationSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
      // Trigger fractal update based on slider value
      simulationView.updateSimulationView(chaosGame,
          newValue); // Call to update the simulation view
    });

    VBox controlsPane = new VBox(iterationsLabel, iterationSlider, zoomInLabel,
        zoomInSlider); // Add all controls here
    BorderPane root = new BorderPane();
    root.setCenter(simulationWrapper); // Use the wrapper as the center to maintain padding and static size

    root.setRight(controlsPane);

    // Back to Menu button
    Button backToMenuButton = new Button("Back to Menu");
    backToMenuButton.setOnAction(e -> {

      System.out.println("Back to menu button clicked!");
    });

    VBox backToMenuPane = new VBox(backToMenuButton); // VBox containing back to menu button
    backToMenuPane.setAlignment(Pos.BOTTOM_RIGHT); // Align the button to the center
    root.setBottom(backToMenuPane); // Set the back to menu button at the bottom of the BorderPane

    // Set background color
    root.setStyle("-fx-background-color: " + backgroundColor + ";");

    return root;
  }

}
