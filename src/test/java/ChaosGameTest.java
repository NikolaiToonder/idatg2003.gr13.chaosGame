import static org.junit.jupiter.api.Assertions.*;

import chaosgameclasses.ChaosGame;
import chaosgameclasses.ChaosGameDescription;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import transformations.Transform2D;
import vectors.Vector2D;

/**
 * Class responsible for testing the chaosGameClasses.ChaosGame class.
 *
 */

class ChaosGameTest {
  private ChaosGameDescription description;
  private Vector2D startPoint;
  private ChaosGame chaosGame;

  /**
   * Sets up the test fixture.
   * Called before every test case method.
   *
   *
   */
  @BeforeEach
  public void setUp() {
    List<Transform2D> transforms = new ArrayList<>();
    Vector2D minCoords = new Vector2D(0, 0);
    Vector2D maxCoords = new Vector2D(10, 10);
    description = new ChaosGameDescription(transforms, minCoords, maxCoords);
    startPoint = new Vector2D(5, 5);
    chaosGame = new ChaosGame(description, 800, 600, startPoint);
  }

  /**
   * Positive tests for the initialization of the chaosGameClasses.ChaosGame class.

   */

  @Test
  void testInitialization_Positive() {
    assertNotNull(chaosGame.getCanvas(), "Canvas should not be null");
    assertEquals(600, chaosGame.getCanvas().getWidth(), "Canvas width should be 800");
    assertEquals(800, chaosGame.getCanvas().getHeight(), "Canvas height should be 600");
    assertEquals(description, chaosGame.getDescription(), "Description should match");
    assertEquals(startPoint, chaosGame.getCurrentPoint(), "Start point should match");
  }

  /**
   * Negative tests for the initialization of the chaosGameClasses.ChaosGame class.
   *
   */

  @Test
  void testInitialization_Negative() {
    assertNotEquals(700, chaosGame.getCanvas().getWidth(), "Canvas width should not be 700");
    assertNotEquals(500, chaosGame.getCanvas().getHeight(), "Canvas height should not be 500");
    assertNotEquals(new ChaosGameDescription(new ArrayList<>(), new Vector2D(1, 1), new Vector2D(10, 10)),
        chaosGame.getDescription(), "Description should not match");
    assertNotEquals(new Vector2D(1, 1), chaosGame.getCurrentPoint(), "Start point should not match");
  }

  /**
   * Positive tests for the setting of the description of the chaosGameClasses.ChaosGame class.
   */
  @Test
  void testSetDescription_Positive() {
    ChaosGameDescription originalDescription = chaosGame.getDescription();
    List<Transform2D> newTransforms = new ArrayList<>();
    newTransforms.add(point -> new Vector2D(0, 0));
    Vector2D newMinCoords = new Vector2D(1, 1);
    Vector2D newMaxCoords = new Vector2D(11, 11);
    ChaosGameDescription newDescription = new ChaosGameDescription(newTransforms, newMinCoords, newMaxCoords);
    chaosGame.setDescription(newDescription);
    assertNotSame(originalDescription, chaosGame.getDescription(), "Description should be different after setting");
    assertEquals(newDescription, chaosGame.getDescription(), "Description should match the new description");
  }

  /**
   * Negative tests for the setting of the description of the chaosGameClasses.ChaosGame class.
   */
  @Test
  void testSetDescription_Negative() {
    ChaosGameDescription originalDescription = chaosGame.getDescription();
    List<Transform2D> newTransforms = new ArrayList<>();
    newTransforms.add(point -> new Vector2D(0, 0));
    Vector2D newMinCoords = new Vector2D(1, 1);
    Vector2D newMaxCoords = new Vector2D(11, 11);
    ChaosGameDescription newDescription = new ChaosGameDescription(newTransforms, newMinCoords, newMaxCoords);
    // Setting the same description again
    chaosGame.setDescription(originalDescription);
    assertSame(originalDescription, chaosGame.getDescription(), "Description should remain the same after setting the same description");
    assertNotEquals(newDescription, chaosGame.getDescription(), "Description should not match the new description after setting the same description");
  }

}
