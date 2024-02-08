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

  public Complex sqrt() {
    double x0 = getX0();
    double x1 = getX1();
    double sqrtr = Math.sqrt(x0 * x0 + x1 * x1);
    return new Complex(Math.sqrt(0.5 * (sqrtr + x0)), Math.signum(x1)
        * Math.sqrt(0.5 * (sqrtr - x0)));
  }

  public Complex subtract(Complex other) {
    return new Complex(getX0() - other.getX0(), getX1() - other.getX1());
  }
}
