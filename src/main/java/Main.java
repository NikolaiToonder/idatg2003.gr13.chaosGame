import modul.chaosgameclasses.ChaosGame;
import modul.chaosgameclasses.ChaosGameDescription;
import modul.math.vectors.Vector2D;

/**
 * Main class for the program.
 */
public class Main {

  /**
   * Main method for the program.
   *
   * @param args Command line arguments
   */
  public static void main(String[] args) {
    ChaosGameDescription description = new ChaosGameDescription(
        "src/main/resources/sierpinskiTriangle.txt");
    Vector2D startingPoint = new Vector2D(0, 0);
    ChaosGame app = new ChaosGame(description, 140, 70, startingPoint);
    app.runGame();
  }
}