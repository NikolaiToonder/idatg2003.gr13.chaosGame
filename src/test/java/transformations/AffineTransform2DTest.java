package transformations;

import static org.junit.jupiter.api.Assertions.*;

import modul.math.matrix.Matrix2x2;
import modul.math.transformations.AffineTransform2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import modul.math.vectors.Vector2D;

class AffineTransform2DTest {

  private Matrix2x2 matrix;
  private Vector2D vector;
  private AffineTransform2D transform;
  private Vector2D point;

  @BeforeEach
  void setUp() {
    matrix = new Matrix2x2(0.5, 1, 1, 0.5);
    vector = new Vector2D(3, 1);
    transform = new AffineTransform2D(matrix, vector);
    point = new Vector2D(1, 2);
  }

  @AfterEach
  void tearDown() {
    matrix = null;
    vector = null;
    transform = null;
    point = null;
  }

  @Test
  void testTransformPositive() {
    Vector2D result = transform.transform(point);
    assertEquals(5.5, result.getX0());
    assertEquals(3, result.getX1());
  }

  @Test
  void testTransformNegative() {
    Vector2D result = transform.transform(point);
    assertNotEquals(5.6, result.getX0());
    assertNotEquals(3.1, result.getX1());
    assertNotEquals(0, result.getX0());
    assertNotEquals(0, result.getX1());
  }
}
