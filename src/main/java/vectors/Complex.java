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
  public Complex add(Complex other) {
    return new Complex(getReal() + other.getReal(), getImaginary() + other.getImaginary());
  }
  public double getReal() {
    return getX0();
  }
  public double getImaginary() {
    return getX1();
  }

  @Override
  public Complex subtract(Vector2D other) {
    return new Complex(this.getReal() - other.getX0(), getImaginary() -
        other.getX1());
  }
  @Override
  public Complex toComplex() {
    return this;
  }
}
