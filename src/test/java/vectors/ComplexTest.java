package vectors;

import static org.junit.jupiter.api.Assertions.*;

import model.math.vectors.Complex;
import org.junit.jupiter.api.Test;
import java.lang.Math;

class ComplexTest {

  @Test
  void sqrtPositive() {
    double x0 = 0.3;
    double x1 = 0.6;
    double sqrtr = Math.sqrt(x0 * x0 + x1 * x1);
    Complex complexTest = new Complex(x0, x1);
    Complex result = complexTest.sqrt();
    assertEquals(Math.sqrt(0.5 * (sqrtr + x0)), result.getX0());
  }

  @Test
  void addPositive() {
    double x0 = 0.3;
    double x1 = 0.6;
    Complex complexTest = new Complex(x0, x1);
    Complex result = complexTest.add(complexTest);
    assertEquals(x0 + x0, result.getX0());
  }

  @Test
  void subtractPosivite() {
    double x0 = 0.3;
    double x1 = 0.6;
    Complex complexTest = new Complex(x0, x1);
    Complex result = complexTest.subtract(complexTest);
    assertEquals(0.0, result.getX0());
  }
  @Test
  void testSqrt_Positive() {
    Complex complexTest = new Complex(0.1, -0.4);
    Complex result = complexTest.sqrt();
    assertEquals(0.5, Math.round(result.getReal()*10.0)/10.0);
    assertEquals(-0.4, Math.round(result.getImaginary()*10.0)/10.0);
  }


  @Test
  void testSqrt_Negative() {
    Complex complexTest = new Complex(-4.0, -3.0);
    Complex result = complexTest.sqrt();
    assertEquals(0.7, Math.round(result.getReal()*10.0)/10.0);
    assertEquals(-2.1, Math.round(result.getImaginary()*10.0)/10.0);
  }

  @Test
  void testAdd_Positive() {
    Complex complex1 = new Complex(2.0, 3.0);
    Complex complex2 = new Complex(1.0, 2.0);
    Complex result = complex1.add(complex2);
    assertEquals(3.0, result.getReal());
    assertEquals(5.0, result.getImaginary());
  }

  @Test
  void testAdd_Negative() {
    Complex complex1 = new Complex(2.0, 3.0);
    Complex complex2 = new Complex(-1.0, -2.0);
    Complex result = complex1.add(complex2);
    assertEquals(1.0, result.getReal());
    assertEquals(1.0, result.getImaginary());
  }

  @Test
  void testSubtract_Positive() {
    Complex complex1 = new Complex(5.0, 4.0);
    Complex complex2 = new Complex(2.0, 1.0);
    Complex result = complex1.subtract(complex2);
    assertEquals(3.0, result.getReal());
    assertEquals(3.0, result.getImaginary());
  }

  @Test
  void testSubtract_Negative() {
    Complex complex1 = new Complex(5.0, 4.0);
    Complex complex2 = new Complex(2.0, 1.0);
    Complex result = complex1.subtract(complex2);
    assertNotEquals(2.0, result.getReal());
    assertNotEquals(1.0, result.getImaginary());
  }
}