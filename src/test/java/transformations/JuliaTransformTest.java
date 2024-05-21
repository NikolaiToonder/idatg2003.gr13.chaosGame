package transformations;

import math.transformations.JuliaTransform;
import org.junit.jupiter.api.Test;
import math.vectors.Complex;
import math.vectors.Vector2D;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JuliaTransformTest {

  @Test
  void testTransformPositive() {
    Complex z = new Complex(0.4, 0.2);
    JuliaTransform transform = new JuliaTransform(new Complex(0.3, 0.6),
        1);
    Vector2D result = transform.transform(z);
    assertEquals(0.51, Math.round(result.getX0()*100.0)*0.01);
    assertEquals(-0.395, Math.round(result.getX1()*1000.0)*0.001);
  }

  @Test
  void testTransformNegative() {
    Complex z = new Complex(0.4, 0.2);
    JuliaTransform transform = new JuliaTransform(new Complex(0.3, 0.6), -1);
    Vector2D result = transform.transform(z);
    assertNotEquals(0.506+0.001, Math.round(result.getX0() * 1000.0) * 0.001);
    assertNotEquals(-0.395-0.001, Math.round(result.getX1() * 1000.0) * 0.001);
  }


  @Test
   void testSetSignValidPositive() {
    Complex constant = new Complex(1, 1);
    JuliaTransform juliaTransform = new JuliaTransform(constant, 1);
    juliaTransform.setSign(-1);
    assertEquals(-1, juliaTransform.getSign());
  }

  @Test
   void testSetSignValidNegative() {
    Complex constant = new Complex(1, 1);
    JuliaTransform juliaTransform = new JuliaTransform(constant, -1);
    juliaTransform.setSign(1);
    assertNotEquals(2, juliaTransform.getSign());
  }

  @Test
  void testSetSignInvalidPositive() {
    Complex constant = new Complex(1, 1);
    JuliaTransform juliaTransform = new JuliaTransform(constant, 1);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
        juliaTransform.setSign(0));
    assertEquals("Sign must be 1 or -1", exception.getMessage());
  }

  @Test
  void testSetSignInvalidNegative() {
    Complex constant = new Complex(1, 1);
    JuliaTransform juliaTransform = new JuliaTransform(constant, 1);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
        juliaTransform.setSign(2));
    assertNotEquals("This is not the exception message", exception.getMessage());
  }

}

