/**
 * A 2D vector class. Functionality includes addition and subtraction of vectors.
 * Requires x and y coordinate.
 */
public class Vector2D {
  private double x0;
  private double x1;

  /**
   * Constructor for Vector2D, takes two doubles as parameters.
   * @param x0 x-coordinate
   * @param x1 y-coordinate
   */
  public Vector2D(double x0, double x1) {
    setX0(x0);
    setX1(x1);
  }

  /**
   * Set x0 to a new value.
   * @param x0 new x0 value
   */
  public void setX0(double x0) {
    this.x0 = x0;
  }
  /**
   * Set x1 to a new value.
   * @param x1 new x1 value
   */
  public void setX1(double x1) {
    this.x1 = x1;
  }
  /**
   * Get the x0 value.
   * @return x0 value
   */
  public double getX0() {
    return x0;
  }
  /**
   * Get the x1 value.
   * @return x1 value
   */
  public double getX1() {
    return x1;
  }
  /**
   * Add two vectors together.
   * @param other vector to add
   * @return new vector
   */
  public Vector2D add(Vector2D other) {
    return new Vector2D(x0 + other.x0, x1 + other.x1);
  }
  /**
   * Subtract one vector from another.
   * @param other vector to subtract
   * @return new vector
   */
  public Vector2D subtract(Vector2D other) {
    return new Vector2D(x0 - other.x0, x1 - other.x1);
  }
}
