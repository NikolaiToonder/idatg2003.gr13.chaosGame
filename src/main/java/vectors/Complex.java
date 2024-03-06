package vectors;

public class Complex extends Vector2D {

  /**
   * Constructor for Complex class, takes two doubles as parameters. One for real part one for
   * imaginary part.
   *
   * @param realPart      x-coordinate
   * @param imaginaryPart y-coordinate
   */
  public Complex(double realPart, double imaginaryPart) {
    super(realPart, imaginaryPart);
  }

  /**
   * Calculates the square root of a complex number.
   * @return new complex number that's the square root of the original
   */
  public Complex sqrt() {
    double x0 = getX0();
    double x1 = getX1();
    double sqrtr = Math.sqrt(x0 * x0 + x1 * x1);
    return new Complex(Math.sqrt(0.5 * (sqrtr + x0)), Math.signum(x1)
        * Math.sqrt(0.5 * (sqrtr - x0)));
  }

  /**
   * Multiplies this complex number with another complex number.
   * The multiplication operation is defined as: (a + bi) * (c + di) = (ac - bd) + (ad + bc)i
   *
   * @param other The other complex number to multiply with.
   * @return The product of this complex number and the other complex number.
   */
  public Complex add(Complex other) {
    return new Complex(getReal() + other.getReal(), getImaginary() + other.getImaginary());
  }

  /**
   * Retrieves the real part of this complex number.
   *
   * @return The real part of this complex number.
   */
  public double getReal() {
    return getX0();
  }

  /**
   * Retrieves the imaginary part of this complex number.
   *
   * @return The imaginary part of this complex number.
   */

  public double getImaginary() {
    return getX1();
  }

  /**
   * Subtracts a vector from this complex number, treating the real and imaginary parts as coordinates.
   *
   * @param other The vector to subtract.
   * @return A new complex number representing the result of the subtraction.
   */

  @Override
  public Complex subtract(Vector2D other) {
    return new Complex(this.getReal() - other.getX0(), getImaginary() -
        other.getX1());
  }

  /**
   * Converts this vector to a complex number.
   *
   * @return This complex number itself.
   */
  @Override
  public Complex toComplex() {
    return this;
  }
}
