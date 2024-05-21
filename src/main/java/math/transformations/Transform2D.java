package math.transformations;

import math.vectors.Vector2D;

/**
 * Interface for 2D transformations.
 */
public interface Transform2D {
  /**
   * Transforms a 2D vector using the transformation.
   *
   * @param point The vector to transform
   * @return The transformed vector
   */
  Vector2D transform(Vector2D point);
}