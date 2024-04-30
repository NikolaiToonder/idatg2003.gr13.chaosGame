package transformations;

import java.util.List;
import matrix.Matrix2x2;
import vectors.Vector2D;

/**
 * Class representing an affine transformation in 2D. The transformation is represented by a 2x2
 * matrix and a 2D vector.
 */

public class AffineTransform2D implements Transform2D {

  private final Matrix2x2 matrix;
  private final Vector2D vector;

  /**
   * Constructor for the transformations.AffineTransform2D class.
   * The transformation is represented by a 2x2 matrix
   *
   * @param matrix The 2x2 matrix
   * @param vector The 2D vector
   */
  public AffineTransform2D(Matrix2x2 matrix, Vector2D vector) {
    this.matrix = matrix;
    this.vector = vector;
  }

  public Matrix2x2 getMatrix() {
    return matrix;
  }


  /**
   * Transforms a 2D vector using the affine transformation.
   *
   * @param point The vector to transform
   * @return The transformed vector
   */
  public Vector2D transform(Vector2D point) {
    return matrix.multiply(point).add(this.vector);
  }


}
