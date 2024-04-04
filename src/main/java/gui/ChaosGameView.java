package gui;
import chaosgameclasses.ChaosCanvas;
import chaosgameclasses.ChaosGame;
import controller.ChaosGameObserver;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import gui.SimulationView; // Make sure to import your SimulationView
import vectors.Vector2D;

public class ChaosGameView {
  private SimulationView simulationView;
  DescriptionFactory descriptionFactory = new DescriptionFactory();
  ChaosGameObserver chaosGameObserver = new ChaosGameObserver();
  ChaosGame chaosGame = new ChaosGame(descriptionFactory.createAffine2D("Barnsley"),
      100,300, new Vector2D(0.5,0.5));


  public Parent createContent() {
    simulationView = new SimulationView(); // Initialize SimulationView

    // Wrap SimulationView in another pane for padding if necessary
    StackPane simulationWrapper = new StackPane(simulationView);
    simulationWrapper.setPadding(new Insets(10)); // Adds padding around the simulation view
    simulationWrapper.setMaxHeight(500); // Set a maximum height for the simulation view (optional)
    simulationWrapper.setMaxWidth(500);

    BorderPane root = new BorderPane();
    root.setCenter(simulationWrapper); // Use the wrapper as the center to maintain padding and static size

    // Setup sliders and controls as before
    Slider iterationSlider = new Slider(100, 100000, 1000);
    // Configuration and listener setup remains the same
    iterationSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
      if (chaosGame.getCanvas().getHeight() != simulationView.getCanvas().getHeight() || chaosGame.getCanvas().getWidth() != simulationView.getCanvas().getWidth()) {
        chaosGame.getCanvas().setHeight((int) simulationView.getCanvas().getHeight());
        chaosGame.getCanvas().setWidth((int) simulationView.getCanvas().getWidth());
      }
      simulationView.updateSimulationView(chaosGame, newValue);
    });


    VBox controlsPane = new VBox(iterationSlider); // Add all controls here
    root.setRight(controlsPane);

    return root;
  }

}
