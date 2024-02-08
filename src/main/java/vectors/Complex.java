package vectors;
import java.lang.Math;

public class Complex extends Vector2D {
  /**
   * Constructor for Complex class, takes two doubles as parameters.
   * One for real part one for imaginary part.
   *
   * @param realPart x-coordinate
   * @param imaginaryPart y-coordinate
   */
  public Complex(double realPart, double imaginaryPart) {
    super(realPart, imaginaryPart);
  }

  public Complex sqrt(){
    double r = Math.sqrt(Math.sqrt(getX0()*getX0() + getX1()*getX1()));
    double theta = Math.atan2(getX1(), getX0());
    return new Complex(r*Math.cos(theta/2), r*Math.sin(theta/2));
  }
}
