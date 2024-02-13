package transformations;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vectors.Complex;
import vectors.Vector2D;

class JuliaTransformTest {

  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void transform() {
    Complex z = new Complex(0.4, 0.2);
    JuliaTransform transform = new JuliaTransform(new Complex(0.3, 0.6),
        1);
    Vector2D result = transform.transform(z);
    assertEquals(0.506, Math.round(result.getX0()*1000.0)*0.001);
    assertEquals(-0.395, Math.round(result.getX1()*1000.0)*0.001);
  }
}