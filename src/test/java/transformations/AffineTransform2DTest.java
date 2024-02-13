package transformations;

import static org.junit.jupiter.api.Assertions.*;

import matrix.Matrix2x2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vectors.Vector2D;

class AffineTransform2DTest {

  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void testTransform() {
    Matrix2x2 matrix = new Matrix2x2(0.5, 1, 1, 0.5);
    Vector2D vector = new Vector2D(3, 1);
    AffineTransform2D transform = new AffineTransform2D(matrix, vector);
    Vector2D point = new Vector2D(1, 2);
    Vector2D result = transform.transform(point);
    assertEquals(5.5, result.getX0());
    assertEquals(3, result.getX1());
  }
}