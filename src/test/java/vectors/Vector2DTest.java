package vectors;

import static org.junit.jupiter.api.Assertions.*;

import modul.math.vectors.Vector2D;

class Vector2DTest {


  @org.junit.jupiter.api.Test
  void positiveAddTest() {
    Vector2D v1 = new Vector2D(1, 2);
    Vector2D v2 = new Vector2D(3, 4);
    Vector2D v3 = v1.add(v2);
    assertEquals(4, v3.getX0());
    assertEquals(6, v3.getX1());

  }
  @org.junit.jupiter.api.Test
  void negativeAddTest() {
    Vector2D v1 = new Vector2D(5, 2);
    Vector2D v2 = new Vector2D(6, 4);
    Vector2D v3 = v1.add(v2);
    assertNotEquals(4, v3.getX0());
    assertNotEquals(9, v3.getX1());

  }

  @org.junit.jupiter.api.Test
  void positiveSubtractTest() {
    Vector2D v1 = new Vector2D(1, 2);
    Vector2D v2 = new Vector2D(3, 4);
    Vector2D v3 = v1.subtract(v2);
    assertEquals(-2, v3.getX0());
    assertEquals(-2, v3.getX1());
  }
  @org.junit.jupiter.api.Test
  void negativeSubtractTest() {
    Vector2D v1 = new Vector2D(1, 2);
    Vector2D v2 = new Vector2D(3, 4);
    Vector2D v3 = v1.subtract(v2);
    assertNotEquals(2, v3.getX0());
    assertNotEquals(2, v3.getX1());
  }

}