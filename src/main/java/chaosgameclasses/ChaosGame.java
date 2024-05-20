package chaosgameclasses;

import controller.ChaosGameObserver;
import controller.Observer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import transformations.Transform2D;
import utilities.Printer;
import utilities.UserInput;
import vectors.Vector2D;

/**
 * Class responsible for running the chaos game.
 *
 * @version 1.0
 */
public class ChaosGame {

    private ChaosCanvas canvas;
    private ChaosGameDescription description;
    private Vector2D currentPoint;
    private final Random random;
    private final UserInput userInput = new UserInput();
    private boolean closeApp = false;
    private final Printer printer = new Printer();
    private List<Observer> observers = new ArrayList<>();


    /**
     * Constructor for the chaosGameClasses.ChaosGame class.
     *
     * @param description  The description of the chaos game
     * @param width        The width of the canvas
     * @param height       The height of the canvas
     * @param currentPoint The starting point & the current point for the chaos game (will be updated
     *                     as the game iterates)
     */
    public ChaosGame(ChaosGameDescription description, int height, int width, Vector2D currentPoint) {
        setDescription(description);
        this.random = new Random();
        this.currentPoint = currentPoint;
        setCanvas(new ChaosCanvas(height, width,
            description.getMinCoords(), description.getMaxCoords()));
    }

    /**
     * Runs the chaos game. Uses a switch case.
     */
    public void runGame() {
        while (!closeApp) {
            printer.printMainMenu(description.getPath());

            String input = userInput.getInput();
            switch (input) {
                case "1" -> runChaosGame();
                case "2" -> changePath();
                case "3" -> closeApp = true;
                default -> printer.errorMessage();
            }
        }
    }

    /**
     * Runs the chaos game for a given number of steps.
     */
    public void runChaosGame() {
        canvas.clear();
        printer.printGetNrOfSteps();
        runSteps(userInput.getNrOfSteps());
    }

    /**
     * Changes the path of the chaos game description.
     */
    public void changePath() {
        printer.transformationChanging();
        String input = userInput.getInput();
        switch (input) {
            case "1" -> {
                this.description = new ChaosGameDescription("src/resources/sierpinskiTriangle.txt");
                this.canvas = new ChaosCanvas(canvas.getHeight(), canvas.getWidth(),
                    description.getMinCoords(), description.getMaxCoords());
            }
            case "2" -> {
                this.description = new ChaosGameDescription("src/resources/barnsleyTransform.txt");
                this.canvas = new ChaosCanvas(canvas.getHeight(), canvas.getWidth(),
                    description.getMinCoords(), description.getMaxCoords());
            }
            case "3" -> {
                this.description = new ChaosGameDescription("src/resources/juliaTransform.txt");
                this.canvas = new ChaosCanvas(canvas.getHeight(), canvas.getWidth(),
                    description.getMinCoords(), description.getMaxCoords());
            }
            default -> printer.invalidPath();
        }
    }


    /**
     * Sets the description of the chaos game.
     *
     * @param description The description of the chaos game
     */
    public void setDescription(ChaosGameDescription description) {
        this.description = description;
    }

    /**
     * Gets the description of the chaos game. Used for test classes
     *
     * @return The description of the chaos game
     */
    public ChaosGameDescription getDescription() {
        return this.description;
    }

    /**
     * Gets the current point of the chaos game. Used for test classes
     *
     * @return The current point of the chaos game
     */
    public Vector2D getCurrentPoint() {
        return this.currentPoint;
    }

    /**
     * Sets the canvas for the chaos game.
     *
     * @param canvas The canvas for the chaos game
     */
    public void setCanvas(ChaosCanvas canvas) {
        this.canvas = canvas;
    }

    /**
     * Gets the canvas for the chaos game.
     *
     * @return The canvas for the chaos game
     */
    public ChaosCanvas getCanvas() {
        return this.canvas;
    }

    /**
     * Runs the chaos game for a given number of steps.
     *
     * @param steps The number of steps to run the chaos game
     */
    public void runSteps(int steps) {
        if (steps <= 0) {
            printer.errorMessage();

        } else {
            //default starting point for this program.
            this.currentPoint = new Vector2D(0.5,0.5);
            for (int i = 0; i < steps; i++) {
                int randomIndex = this.random.nextInt(description.getTransforms().size());
                Transform2D transform = description.getTransforms().get(randomIndex);
                this.currentPoint = transform.transform(this.currentPoint);
                this.canvas.putPixel(currentPoint);
            }
            this.description.handleValuesForOutprint(this.canvas.getCanvasArray());
        }
    }

    /**
     * Used to run 1 singular step. Useful for drawing in the gui.
     */
    public void runStep() {
        if (this.description.getIsBarnsley()) {
            List<Double> cumulativeProbabilities = List.of(0.01, 0.85, 0.07, 0.07);
            double rand = random.nextDouble();
            int selectedTransformIndex;
            if(rand <=0.01){
                selectedTransformIndex = 0;
            } else if (rand <= 0.07) {
                selectedTransformIndex = 2;
            } else if (rand <= 0.18) {
                selectedTransformIndex = 3;
            } else {
                selectedTransformIndex = 1;
            }
            Transform2D transform = description.getTransforms().get(selectedTransformIndex);
            this.currentPoint = transform.transform(this.currentPoint);
            this.canvas.putPixel(currentPoint);
        } else {
            int randomIndex = this.random.nextInt(description.getTransforms().size());
            Transform2D transform = description.getTransforms().get(randomIndex);
            this.currentPoint = transform.transform(this.currentPoint);
            this.canvas.putPixel(currentPoint);
        }
    }


    /**
     * Method to zoom, will just pass on a scalar to the canvas, done this way to decrease coupling.
     * @param scalar
     */
    public void zoom(double scalar) {
        this.canvas.zoom(scalar);
    }
}