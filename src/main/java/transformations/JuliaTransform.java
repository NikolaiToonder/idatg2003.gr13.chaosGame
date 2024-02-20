package transformations;

import vectors.Complex;
import vectors.Vector2D;

public class JuliaTransform implements Transform2D{
  private final Complex point;
  private int sign;

  /**
   * Constructor for the transformations.JuliaTransform class.
   * The transformation is represented by a complex number and a sign.
   * @param point The complex number
   * @param sign The sign
   */
  public JuliaTransform(Complex point, int sign) {
    this.point = point;
    setSign(sign);
  }

  /**
   * Setter for sign. The sign must be 1 or -1.
   * @param sign The sign to set
   */
  public void setSign(int sign) {
    if (sign != 1 && sign != -1) {
      throw new IllegalArgumentException("Sign must be 1 or -1");
    }else {
      this.sign = sign;
    }
  }

  /**
   * Transforms a Complex vector using the Julia transformation.
   * @param vector
   * @return
   */
  public Vector2D transform(Vector2D vector) {
    Complex complexSubtraction = (Complex) vector.subtract(this.point);
    Complex complexSqrt = complexSubtraction.sqrt();
    return new Complex(complexSqrt.getX0(), this.sign * complexSqrt.getX1());
  }
}
