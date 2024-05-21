package transformations;

import static org.junit.jupiter.api.Assertions.*;

import math.matrix.Matrix2x2;
import math.transformations.AffineTransform2D;
import org.junit.jupiter.api.Test;
import math.vectors.Vector2D;

class AffineTransform2DTest {

  @Test
  void testTransformPositive() {
    Matrix2x2 matrix = new Matrix2x2(0.5, 1, 1, 0.5);
    Vector2D vector = new Vector2D(3, 1);
    AffineTransform2D transform = new AffineTransform2D(matrix, vector);
    Vector2D point = new Vector2D(1, 2);
    Vector2D result = transform.transform(point);
    assertEquals(5.5, result.getX0());
    assertEquals(3, result.getX1());
  }

  @Test
  void testTransformNegative() {
    Matrix2x2 matrix = new Matrix2x2(0.5, 1, 1, 0.5);
    Vector2D vector = new Vector2D(3, 1);
    AffineTransform2D transform = new AffineTransform2D(matrix, vector);
    Vector2D point = new Vector2D(1, 2);
    Vector2D result = transform.transform(point);
    assertNotEquals(5.6, result.getX0());
    assertNotEquals(3.1, result.getX1());
    assertNotEquals(0,result.getX0());
    assertNotEquals(0,result.getX1());
  }
}