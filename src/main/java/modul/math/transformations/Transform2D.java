package modul.math.transformations;

import modul.math.vectors.Vector2D;

/**
 * Interface for 2D transformations.
 *
 * @version 1.0
 * @author Nikolai Engelsen Tønder
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