package gui;
import chaosgameclasses.ChaosGame;
import controller.ChaosGameObserver;
import javafx.scene.Parent;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import gui.SimulationView; // Make sure to import your SimulationView
import vectors.Vector2D;

public class ChaosGameView {
  private SimulationView simulationView;
  DescriptionFactory descriptionFactory = new DescriptionFactory();
  ChaosGameObserver chaosGameObserver = new ChaosGameObserver();
  ChaosGame chaosGame = new ChaosGame(descriptionFactory.createAffine2D("Barnsley"),
      600, 400, new Vector2D(0,0));


  public Parent createContent() {
    simulationView = new SimulationView(); // Initialize SimulationView

    BorderPane root = new BorderPane();
    root.setCenter(simulationView); // Use SimulationView as the center

    // Setup sliders and controls
    Slider iterationSlider = new Slider(100, 1000, 1000);
    // Configure your slider and add listeners to update the fractal view
    iterationSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
      // Trigger fractal update based on slider value
      simulationView.updateSimulationView(chaosGame, newValue); // Call to update the simulation view
    });

    VBox controlsPane = new VBox(iterationSlider); // Add all controls here
    root.setRight(controlsPane);

    return root;
  }
}
