package vectors;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the Complex class.
 * TODO: Add negative and positive tests for the methods in the Complex class.
 */

class ComplexTest {

  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  /**
   * Test the sqrt method of the Complex class.
   * The method should return a new complex number that's the square root of the original.
   */

  @Test
  void sqrt() {
    double x0 = 0.3;
    double x1 = 0.6;
    double sqrtr = Math.sqrt(x0 * x0 + x1 * x1);
    Complex complexTest = new Complex(x0, x1);
    Complex result = complexTest.sqrt();
    System.out.println(result.getX0() + " " + result.getX1());
    assertEquals(Math.sqrt(0.5 * (sqrtr + x0)), result.getX0());

  }

  /**
   * Test the add method of the Complex class.
   * The method should return a new complex number that's the sum of the original and the parameter.
   */

  @Test
  void add() {
    double x0 = 0.3;
    double x1 = 0.6;
    Complex complexTest = new Complex(x0, x1);
    Complex result = complexTest.add(complexTest);
    assertEquals(x0 + x0, result.getX0());

  }

  /**
   * Test the subtract method of the Complex class.
   * The method should return a new complex number that's the difference of the original and the parameter.
   */

  @Test
  void subtract() {
    double x0 = 0.3;
    double x1 = 0.6;
    Complex complexTest = new Complex(x0, x1);
    Complex result = complexTest.subtract(complexTest);
    assertEquals(0.0, result.getX0());
  }
}