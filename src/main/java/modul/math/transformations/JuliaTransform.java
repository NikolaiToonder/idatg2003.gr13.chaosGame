package modul.math.transformations;

import modul.math.vectors.Complex;
import modul.math.vectors.Vector2D;

/**
 * Class representing a Julia transformation in 2D. The transformation is represented by a complex
 * number and a sign.
 *
 * @version 1.0
 * @author Nikolai Engelsen Tønder
 */


public class JuliaTransform implements Transform2D {
  private final Complex constant;
  private int sign;

  /**
   * Constructor for the transformations.JuliaTransform class.
   * The transformation is represented by a complex number and a sign.
   *
   * @param constant The complex constant
   * @param sign The sign
   */
  public JuliaTransform(Complex constant, int sign) {
    this.constant = constant;
    setSign(sign);
  }


  /**
   * Setter for sign. The sign must be 1 or -1.
   *
   * @param sign The sign to set
   */
  public void setSign(int sign) {
    if (sign != 1 && sign != -1) {
      throw new IllegalArgumentException("Sign must be 1 or -1");
    } else {
      this.sign = sign;
    }
  }

  /**
   * Transforms a Complex vector using the Julia transformation.
   *
   * @param vector  The vector to transform
   * @return The transformed vector
   */
  public Vector2D transform(Vector2D vector) {
    return vector.toComplex().subtract(this.constant).sqrt().multiply(this.sign);
  }

  /**
   * Made for testing purposes.
   *
   * @return The sign
   */
  public int getSign() {
    return this.sign;
  }
}