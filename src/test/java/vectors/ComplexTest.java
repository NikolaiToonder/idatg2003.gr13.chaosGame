package vectors;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ComplexTest {

  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void sqrt() {
    double x0 = 0.3;
    double x1 = 0.6;
    double sqrtr = Math.sqrt(x0 * x0 + x1 * x1);
    Complex complexTest = new Complex(x0, x1);
    Complex result = complexTest.sqrt();
    assertEquals(Math.sqrt(0.5 * (sqrtr + x0)), result.getX0());

  }

  @Test
  void add() {
    double x0 = 0.3;
    double x1 = 0.6;
    Complex complexTest = new Complex(x0, x1);
    Complex result = complexTest.add(complexTest);
    assertEquals(x0 + x0, result.getX0());

  }

  @Test
  void subtract() {
    double x0 = 0.3;
    double x1 = 0.6;
    Complex complexTest = new Complex(x0, x1);
    Complex result = complexTest.subtract(complexTest);
    assertEquals(0.0, result.getX0());
  }
}