package modul.math.vectors;

/**
 * Class to represent a complex number, used in this case to also respresent a complex number in the
 * julia transformation.
 */
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
   *
   * @return new complex number that's the square root of the original
   */
  public Complex sqrt() {
    double magnitude = Math.sqrt(Math.pow(getReal(), 2) + Math.pow(getImaginary(), 2));
    double realPart = Math.sqrt((magnitude + getReal()) / 2);
    double imaginaryPart = Math.signum(getImaginary()) * Math.sqrt((magnitude - getReal()) / 2);
    return new Complex(realPart, imaginaryPart);
  }

  /**
   * Adds a complex number to this complex number.
   *
   * @param other The other complex number to multiply with.
   * @return The product of this complex number and the other complex number.
   */
  public Complex add(Complex other) {
    return new Complex(getReal() + other.getReal(),
        getImaginary() + other.getImaginary());
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
   * Subtracts a vector from this complex number, treating the real and imaginary parts as
   * coordinates.
   *
   * @param other The vector to subtract.
   * @return A new complex number representing the result of the subtraction.
   */
  @Override
  public Complex subtract(Vector2D other) {
    return new Complex(this.getReal() - other.getX0(), getImaginary()
        - other.getX1());
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

  /**
   * Multiplies this complex number with a scalar.
   *
   * @param scalar The scalar to multiply with.
   */
  public Complex multiply(int scalar) {
    return new Complex(scalar * getReal(), scalar * getImaginary());
  }
}