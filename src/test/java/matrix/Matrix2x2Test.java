package matrix;

import modul.math.matrix.Matrix2x2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import modul.math.vectors.Vector2D;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class Matrix2x2Test {

  private Matrix2x2 matrix;
  private Vector2D vector;

  @BeforeEach
  void setUp() {
    matrix = new Matrix2x2(1, 2, 3, 4);
    vector = new Vector2D(2, 3);
  }

  @AfterEach
  void tearDown() {
    matrix = null;
    vector = null;
  }

  @Test
  void multiplyPositiveTest() {
    Vector2D result = matrix.multiply(vector);
    assertEquals(8, result.getX0());
    assertEquals(18, result.getX1());
  }

  @Test
  void multiplyNegativeTest() {
    Vector2D result = matrix.multiply(vector);
    assertNotEquals(9, result.getX0());
    assertNotEquals(19, result.getX1());
  }

  @Test
  void multiplyWithZeroMatrix() {
    Matrix2x2 matrix2 = new Matrix2x2(0, 0, 0, 0);
    Vector2D result = matrix2.multiply(vector);
    assertEquals(0, result.getX0());
    assertEquals(0, result.getX1());
  }

  @Test
  void multiplyWithZeroVector() {
    matrix = new Matrix2x2(1, 2, 3, 4);
    Vector2D vector2 = new Vector2D(0, 0);
    Vector2D result = matrix.multiply(vector2);
    assertEquals(0, result.getX0());
    assertEquals(0, result.getX1());
  }

  @Test
  void multiplyWithNegativeValuesPositiveTest() {
    matrix = new Matrix2x2(-1, -2, -3, -4);
    vector = new Vector2D(-2, -3);
    Vector2D result = matrix.multiply(vector);
    assertEquals(8, result.getX0());
    assertEquals(18, result.getX1());
  }

  @Test
  void multiplyWithNegativeValuesNegativeTest() {
    matrix = new Matrix2x2(-1, -2, -3, -4);
    vector = new Vector2D(-2, -3);
    Vector2D result = matrix.multiply(vector);
    assertNotEquals(9, result.getX0());
    assertNotEquals(19, result.getX1());
  }
}
