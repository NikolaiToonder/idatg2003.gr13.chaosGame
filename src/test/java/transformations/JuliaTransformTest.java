package transformations;

import math.transformations.JuliaTransform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import math.vectors.Complex;
import math.vectors.Vector2D;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JuliaTransformTest {

  private Complex constant;
  private Complex z;
  private JuliaTransform transform;

  @BeforeEach
  void setUp() {
    // Initialize common test values here
    constant = new Complex(0.3, 0.6);
    z = new Complex(0.4, 0.2);
    transform = new JuliaTransform(constant, 1);
  }

  @AfterEach
  void tearDown() {
    // Cleanup after each test
    constant = null;
    z = null;
    transform = null;
  }

  @Test
  void testTransformPositive() {
    Vector2D result = transform.transform(z);
    assertEquals(0.51, Math.round(result.getX0() * 100.0) * 0.01);
    assertEquals(-0.395, Math.round(result.getX1() * 1000.0) * 0.001);
  }

  @Test
  void testTransformNegative() {
    transform = new JuliaTransform(constant, -1);
    Vector2D result = transform.transform(z);
    assertNotEquals(0.506 + 0.001, Math.round(result.getX0() * 1000.0) * 0.001);
    assertNotEquals(-0.395 - 0.001, Math.round(result.getX1() * 1000.0) * 0.001);
  }

  @Test
  void testSetSignValidPositive() {
    transform.setSign(-1);
    assertEquals(-1, transform.getSign());
  }

  @Test
  void testSetSignValidNegative() {
    transform.setSign(1);
    assertNotEquals(2, transform.getSign());
  }

  @Test
  void testSetSignInvalidPositive() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
        transform.setSign(0));
    assertEquals("Sign must be 1 or -1", exception.getMessage());
  }

  @Test
  void testSetSignInvalidNegative() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
        transform.setSign(2));
    assertNotEquals("This is not the exception message", exception.getMessage());
  }
}
