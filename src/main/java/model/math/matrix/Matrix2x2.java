package model.math.matrix;

import model.math.vectors.Vector2D;

/**
 * Class representing a 2x2 matrix. Has the functionality to multiply a 2D vector with a
 * 2x2-matrix.
 */
public class Matrix2x2 {

  private double a00;
  private double a01;
  private double a10;
  private double a11;


  /**
   * Constructor for the 2x2 matrix. The matrix is represented as follows:
   *
   * @param a00 The value in the first row and first column
   * @param a01 The value in the first row and second column
   * @param a10 The value in the second row and first column
   * @param a11 The value in the second row and second column
   */
  public Matrix2x2(double a00, double a01, double a10, double a11) {
    this.a00 = a00;
    this.a01 = a01;
    this.a10 = a10;
    this.a11 = a11;
  }

  /**
   * Getter for the field a00.
   * @return double a00
   */
  public double getA00() {
    return a00;
  }

  /**
   * Getter for the field a01
   * @return double a01
   */
  public double getA01() {
    return a01;
  }

  /**
   * Getter for the field a10.
   * @return double a10
   */
  public double getA10() {
    return a10;
  }

  /**
   * Getter for the field a11
   * @return double a11
   */
  public double getA11() {
    return a11;
  }

  /**
   * Multiplies the 2x2-matrix by a 2x1 vector. The vector is brought from the Vector2D class.
   *
   * @param vector The 2D vector to multiply the matrix by
   * @return The result of the multiplication
   */
  public Vector2D multiply(Vector2D vector) {
    return new Vector2D(a00 * vector.getX0() + a01 * vector.getX1(),
            a10 * vector.getX0() + a11 * vector.getX1());
  }
}