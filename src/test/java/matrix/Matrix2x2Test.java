package matrix;

import org.junit.jupiter.api.Test;
import vectors.Vector2D;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class Matrix2x2Test {

  @Test
  void multiplyPositiveTest() {
    Matrix2x2 matrix = new Matrix2x2(1, 2, 3, 4);
    Vector2D vector = new Vector2D(2, 3);
    Vector2D result = matrix.multiply(vector);
    assertEquals(8, result.getX0());
    assertEquals(18, result.getX1());
  }

  @Test
  void multiplyNegativeTest() {
    Matrix2x2 matrix = new Matrix2x2(1, 2, 3, 4);
    Vector2D vector = new Vector2D(2, 3);
    Vector2D result = matrix.multiply(vector);
    assertNotEquals(9, result.getX0());
    assertNotEquals(18, result.getX1());

  }

  @Test
  void multiplyWithZeroMatrix() {
    Matrix2x2 matrix = new Matrix2x2(0, 0, 0, 0);
    Vector2D vector = new Vector2D(2, 3);
    Vector2D result = matrix.multiply(vector);
    assertEquals(0, result.getX0());
    assertEquals(0, result.getX1());
  }

  @Test
  void multiplyWithZeroVector() {
    Matrix2x2 matrix = new Matrix2x2(1, 2, 3, 4);
    Vector2D vector = new Vector2D(0, 0);
    Vector2D result = matrix.multiply(vector);
    assertEquals(0, result.getX0());
    assertEquals(0, result.getX1());
  }

  @Test
  void multiplyWithNegativeValuesPositiveTest() {
    Matrix2x2 matrix = new Matrix2x2(-1, -2, -3, -4);
    Vector2D vector = new Vector2D(-2, -3);
    Vector2D result = matrix.multiply(vector);
    assertEquals(8, result.getX0());
    assertEquals(18, result.getX1());
  }
  @Test
  void multiplyWithNegativeValuesNegativeTest() {
    Matrix2x2 matrix = new Matrix2x2(-1, -2, -3, -4);
    Vector2D vector = new Vector2D(-2, -3);
    Vector2D result = matrix.multiply(vector);
    assertNotEquals(9, result.getX0());
    assertNotEquals(18, result.getX1());
  }
}
