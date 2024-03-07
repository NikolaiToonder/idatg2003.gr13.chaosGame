package vectors;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.lang.Math;

class ComplexTest {

  @Test
  void testSqrt_Positive() {
    Complex complexTest = new Complex(4.0, 3.0);
    Complex result = complexTest.sqrt();
    assertEquals(2.1, Math.round(result.getReal()*10.0)/10.0);
    assertEquals(0.7, Math.round(result.getImaginary()*10.0)/10.0);
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