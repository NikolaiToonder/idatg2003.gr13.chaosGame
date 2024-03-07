package transformations;

import org.junit.jupiter.api.Test;
import vectors.Complex;
import vectors.Vector2D;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JuliaTransformTest {

  @Test
  void testTransformPositive() {
    Complex z = new Complex(0.4, 0.2);
    JuliaTransform transform = new JuliaTransform(new Complex(0.3, 0.6),
        1);
    Vector2D result = transform.transform(z);
    assertEquals(0.506, Math.round(result.getX0()*1000.0)*0.001);
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
  public void testSetSignValidPositive() {
    Complex constant = new Complex(1, 1);
    JuliaTransform juliaTransform = new JuliaTransform(constant, 1);
    juliaTransform.setSign(-1);
    assertEquals(-1, juliaTransform.getSign());
  }

  @Test
  public void testSetSignValidNegative() {
    Complex constant = new Complex(1, 1);
    JuliaTransform juliaTransform = new JuliaTransform(constant, -1);
    juliaTransform.setSign(1);
    assertNotEquals(2, juliaTransform.getSign());
  }

  @Test
  public void testSetSignInvalidPositive() {
    Complex constant = new Complex(1, 1);
    JuliaTransform juliaTransform = new JuliaTransform(constant, 1);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      juliaTransform.setSign(0);
    });
    assertEquals("Sign must be 1 or -1", exception.getMessage());
  }

  @Test
  public void testSetSignInvalidNegative() {
    Complex constant = new Complex(1, 1);
    JuliaTransform juliaTransform = new JuliaTransform(constant, 1);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      juliaTransform.setSign(2);
    });
    assertNotEquals("", exception.getMessage());
  }
}

